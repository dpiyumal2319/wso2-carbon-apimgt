/*
 * Copyright (c) 2026, WSO2 LLC. (https://www.wso2.com/).
 *
 * WSO2 LLC. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.wso2.carbon.apimgt.impl.notifier;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.apimgt.api.APIManagementException;
import org.wso2.carbon.apimgt.api.FederatedApiKeyConnector;
import org.wso2.carbon.apimgt.api.model.Application;
import org.wso2.carbon.apimgt.api.model.Environment;
import org.wso2.carbon.apimgt.api.model.FederatedApiKeyContext;
import org.wso2.carbon.apimgt.api.model.FederatedApiKeyCreationResult;
import org.wso2.carbon.apimgt.api.model.SubscribedAPI;
import org.wso2.carbon.apimgt.api.model.policy.SubscriptionPolicy;
import org.wso2.carbon.apimgt.impl.APIConstants;
import org.wso2.carbon.apimgt.impl.dao.ApiMgtDAO;
import org.wso2.carbon.apimgt.impl.factory.GatewayHolder;
import org.wso2.carbon.apimgt.impl.notifier.events.APIKeyAssociationEvent;
import org.wso2.carbon.apimgt.impl.notifier.events.APIKeyEvent;
import org.wso2.carbon.apimgt.impl.notifier.events.APIKeyRegenerationEvent;
import org.wso2.carbon.apimgt.impl.notifier.events.Event;
import org.wso2.carbon.apimgt.impl.notifier.exceptions.NotifierException;
import org.wso2.carbon.apimgt.impl.utils.APIUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Notifier (event handler) for federated API key operations.
 * Processes async events to push API key changes to external gateways.
 */
public class FederatedApiKeyNotifier implements Notifier {

    private static final Log log = LogFactory.getLog(FederatedApiKeyNotifier.class);

    @Override
    public boolean publishEvent(Event event) throws NotifierException {
        if (log.isDebugEnabled()) {
            log.debug("Processing federated API key event: " + event);
        }

        try {
            if (event instanceof APIKeyEvent) {
                handleAPIKeyEvent((APIKeyEvent) event);
            } else if (event instanceof APIKeyAssociationEvent) {
                handleAPIKeyAssociationEvent((APIKeyAssociationEvent) event);
            } else if (event instanceof APIKeyRegenerationEvent) {
                handleRegenerate((APIKeyRegenerationEvent) event);
            }
            return true;
        } catch (APIManagementException e) {
            log.error("Failed to process federated API key event: " + event, e);
            throw new NotifierException("Failed to process federated API key event", e);
        }
    }

    private void handleAPIKeyEvent(APIKeyEvent apiKeyEvent) throws APIManagementException {

        APIConstants.EventType eventType = resolveEventType(apiKeyEvent.getType());
        if (eventType == null) {
            log.warn("Unknown federated API key event type: " + apiKeyEvent.getType());
            return;
        }

        switch (eventType) {
            case API_KEY_CREATE:
                handleCreate(apiKeyEvent);
                break;
            case API_KEY_DELETE:
                handleRevoke(apiKeyEvent);
                break;
            default:
                log.warn("Unsupported federated API key event type: " + eventType.name());
        }
    }

    private void handleAPIKeyAssociationEvent(APIKeyAssociationEvent associationEvent) throws APIManagementException {

        APIConstants.EventType eventType = resolveEventType(associationEvent.getType());
        if (eventType == null) {
            log.warn("Unknown federated API key association event type: " + associationEvent.getType());
            return;
        }

        switch (eventType) {
            case API_KEY_ASSOCIATION_CREATE:
                handleApplyRateLimitPolicy(associationEvent);
                break;
            case API_KEY_ASSOCIATION_DELETE:
                handleRemoveRateLimitPolicy(associationEvent);
                break;
            default:
                log.warn("Unsupported federated API key association event type: " + eventType.name());
        }
    }

    private APIConstants.EventType resolveEventType(String type) {

        try {
            return APIConstants.EventType.valueOf(type);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    @Override
    public String getType() {
        return APIConstants.NotifierType.API_KEY.name();
    }

    /**
     * Creates remote API-key credentials in each mapped external gateway and stores connector-owned reference artifacts.
     */
    private void handleCreate(APIKeyEvent event) throws APIManagementException {
        String apiUuid = event.getApiUUId();
        if (!isFederatedApi(apiUuid)) {
            return;
        }
        String organization = resolveOrganization(event);
        String apiKeyValue = event.getApiKey();
        if (StringUtils.isBlank(apiKeyValue)) {
            throw new APIManagementException("Federated API key create event is missing the generated key value");
        }

        List<GatewayEnvironmentContext> gatewayEnvironments =
                resolveGatewayEnvironments(organization, resolveApiExternalMappingsByEnvironmentName(apiUuid));
        Map<String, String> newlyCreatedReferenceArtifacts = new LinkedHashMap<>();
        try {
            for (GatewayEnvironmentContext gatewayEnvironment : gatewayEnvironments) {
                FederatedApiKeyConnector connector = resolveConnector(organization, gatewayEnvironment);
                FederatedApiKeyContext context = buildFederatedApiKeyContext(apiUuid, event.getUuid(), event.getName(),
                        apiKeyValue, null, event.getUser(), event.getApplicationUUId(),
                        organization, gatewayEnvironment, event.getValidityPeriod(), event.getPermittedIP(),
                        event.getPermittedReferer(), null);
                FederatedApiKeyCreationResult result = connector.createApiKey(context);
                if (result == null || StringUtils.isBlank(result.getReferenceArtifact())) {
                    throw new APIManagementException("Federated API key creation did not return a reference artifact "
                            + "for environment: " + gatewayEnvironment.getEnvironmentId());
                }
                newlyCreatedReferenceArtifacts.put(gatewayEnvironment.getEnvironmentId(), result.getReferenceArtifact());
            }
        } catch (APIManagementException e) {
            rollbackCreatedApiKeys(apiUuid, organization, event, gatewayEnvironments, newlyCreatedReferenceArtifacts);
            throw e;
        }

        try {
            for (Map.Entry<String, String> referenceArtifactEntry : newlyCreatedReferenceArtifacts.entrySet()) {
                getApiMgtDAO().addOrUpdateApiKeyExternalApiKeyMapping(event.getUuid(),
                        referenceArtifactEntry.getKey(), referenceArtifactEntry.getValue());
            }
        } catch (APIManagementException e) {
            getApiMgtDAO().deleteApiKeyExternalApiKeyMappings(event.getUuid());
            rollbackCreatedApiKeys(apiUuid, organization, event, gatewayEnvironments, newlyCreatedReferenceArtifacts);
            throw e;
        }
        log.info("Successfully created federated API key on " + newlyCreatedReferenceArtifacts.size()
                + " gateway environment(s). KeyUuid: " + event.getUuid());
    }

    /**
     * Revokes previously created remote API-key credentials using stored connector-owned reference artifacts.
     */
    private void handleRevoke(APIKeyEvent event) throws APIManagementException {
        Map<String, String> apiKeyReferenceArtifacts = getApiMgtDAO().getApiKeyExternalApiKeyMappings(event.getUuid());
        if (apiKeyReferenceArtifacts.isEmpty()) {
            log.warn("No per-environment remote API key reference artifacts found for federated API key UUID: "
                    + event.getUuid()
                    + ". Skipping remote revocation.");
            return;
        }

        String organization = resolveOrganization(event);
        List<GatewayEnvironmentContext> gatewayEnvironments =
                resolveGatewayEnvironments(organization, apiKeyReferenceArtifacts.keySet());
        for (GatewayEnvironmentContext gatewayEnvironment : gatewayEnvironments) {
            String apiKeyReferenceArtifact = apiKeyReferenceArtifacts.get(gatewayEnvironment.getEnvironmentId());
            if (StringUtils.isBlank(apiKeyReferenceArtifact)) {
                log.warn("Remote API key reference artifact is missing for federated API key UUID: " + event.getUuid()
                        + " in environment: " + gatewayEnvironment.getEnvironmentId() + ". Skipping remote revocation.");
                continue;
            }

            FederatedApiKeyConnector connector = resolveConnector(organization, gatewayEnvironment);
            FederatedApiKeyContext context = buildFederatedApiKeyContext(null, event.getUuid(), event.getName(),
                    null, apiKeyReferenceArtifact, event.getUser(), event.getApplicationUUId(), organization,
                    gatewayEnvironment, null, null, null, null);
            connector.revokeApiKey(context);
        }
        getApiMgtDAO().deleteApiKeyExternalApiKeyMappings(event.getUuid());

        log.info("Successfully revoked federated API key on " + apiKeyReferenceArtifacts.size()
                + " gateway environment(s). KeyUuid: " + event.getUuid());
    }

    /**
     * Applies the mapped remote plan to an existing remote credential when a local key is associated to an application.
     */
    private void handleApplyRateLimitPolicy(APIKeyAssociationEvent event) throws APIManagementException {
        if (StringUtils.isBlank(event.getApiKeyUUId())) {
            throw new APIManagementException("Federated API key association event is missing API key UUID context");
        }
        Map<String, String> apiKeyReferenceArtifacts =
                getApiMgtDAO().getApiKeyExternalApiKeyMappings(event.getApiKeyUUId());
        if (apiKeyReferenceArtifacts.isEmpty()) {
            log.warn("No per-environment remote API key reference artifacts found for federated API key UUID: "
                    + event.getApiKeyUUId() + ". Skipping remote policy application.");
            return;
        }

        String apiUuid = event.getApiUUId();
        String applicationUuid = resolveApplicationUuid(event);
        String organization = resolveOrganization(event);
        String localPolicyId = resolveSubscriptionPolicyId(applicationUuid, apiUuid);
        Map<String, String> currentGatewayMappings = resolveApiExternalMappingsByEnvironmentName(apiUuid);
        List<GatewayEnvironmentContext> gatewayEnvironments =
                resolveGatewayEnvironments(organization, apiKeyReferenceArtifacts.keySet(),
                        currentGatewayMappings);
        for (GatewayEnvironmentContext gatewayEnvironment : gatewayEnvironments) {
            String apiKeyReferenceArtifact = apiKeyReferenceArtifacts.get(gatewayEnvironment.getEnvironmentId());
            if (StringUtils.isBlank(apiKeyReferenceArtifact)) {
                log.warn("Remote API key reference artifact is missing for federated API key UUID: "
                        + event.getApiKeyUUId()
                        + " in environment: " + gatewayEnvironment.getEnvironmentId()
                        + ". Skipping rate limit policy application.");
                continue;
            }

            FederatedApiKeyConnector connector = resolveConnector(organization, gatewayEnvironment);
            FederatedApiKeyContext context = buildFederatedApiKeyContext(apiUuid, event.getApiKeyUUId(),
                    null, null, apiKeyReferenceArtifact, null, applicationUuid, organization, gatewayEnvironment,
                    null, null, null, localPolicyId);
            connector.applyRateLimitPolicy(context);
        }

        log.info("Successfully applied rate limit policy to federated API key across "
                + gatewayEnvironments.size() + " gateway environment(s). KeyUuid: " + event.getApiKeyUUId());
    }

    /**
     * Removes the currently mapped remote plan from an existing remote credential when a local association is removed.
     */
    private void handleRemoveRateLimitPolicy(APIKeyAssociationEvent event) throws APIManagementException {
        if (StringUtils.isBlank(event.getApiKeyUUId())) {
            throw new APIManagementException("Federated API key association event is missing API key UUID context");
        }
        Map<String, String> apiKeyReferenceArtifacts =
                getApiMgtDAO().getApiKeyExternalApiKeyMappings(event.getApiKeyUUId());
        if (apiKeyReferenceArtifacts.isEmpty()) {
            log.warn("No per-environment remote API key reference artifacts found for federated API key UUID: "
                    + event.getApiKeyUUId()
                    + ". Skipping remote policy removal.");
            return;
        }

        String apiUuid = event.getApiUUId();
        String applicationUuid = resolveApplicationUuid(event);
        String organization = resolveOrganization(event);
        String localPolicyId = resolveSubscriptionPolicyId(applicationUuid, apiUuid);
        Map<String, String> currentGatewayMappings = resolveApiExternalMappingsByEnvironmentName(apiUuid);
        List<GatewayEnvironmentContext> gatewayEnvironments =
                resolveGatewayEnvironments(organization, apiKeyReferenceArtifacts.keySet(),
                        currentGatewayMappings);
        for (GatewayEnvironmentContext gatewayEnvironment : gatewayEnvironments) {
            String apiKeyReferenceArtifact = apiKeyReferenceArtifacts.get(gatewayEnvironment.getEnvironmentId());
            if (StringUtils.isBlank(apiKeyReferenceArtifact)) {
                log.warn("Remote API key reference artifact is missing for federated API key UUID: "
                        + event.getApiKeyUUId()
                        + " in environment: " + gatewayEnvironment.getEnvironmentId()
                        + ". Skipping remote policy removal.");
                continue;
            }

            FederatedApiKeyConnector connector = resolveConnector(organization, gatewayEnvironment);
            FederatedApiKeyContext context = buildFederatedApiKeyContext(apiUuid, event.getApiKeyUUId(),
                    null, null, apiKeyReferenceArtifact, null, applicationUuid, organization, gatewayEnvironment,
                    null, null, null, localPolicyId);
            connector.removeRateLimitPolicy(context);
        }

        log.info("Successfully removed rate limit policy from federated API key across "
                + gatewayEnvironments.size() + " gateway environment(s). KeyUuid: " + event.getApiKeyUUId());
    }

    /**
     * Revokes remote credentials created during a partially failed create operation.
     */
    private void rollbackCreatedApiKeys(String apiUuid, String organization, APIKeyEvent event,
                                        List<GatewayEnvironmentContext> gatewayEnvironments,
                                        Map<String, String> apiKeyReferenceArtifacts) {
        if (apiKeyReferenceArtifacts.isEmpty()) {
            return;
        }

        Map<String, GatewayEnvironmentContext> gatewayEnvironmentMap = new HashMap<>();
        for (GatewayEnvironmentContext gatewayEnvironment : gatewayEnvironments) {
            gatewayEnvironmentMap.put(gatewayEnvironment.getEnvironmentId(), gatewayEnvironment);
        }

        for (Map.Entry<String, String> referenceArtifactEntry : apiKeyReferenceArtifacts.entrySet()) {
            GatewayEnvironmentContext gatewayEnvironment = gatewayEnvironmentMap.get(referenceArtifactEntry.getKey());
            if (gatewayEnvironment == null) {
                continue;
            }

            try {
                FederatedApiKeyConnector connector = resolveConnector(organization, gatewayEnvironment);
                FederatedApiKeyContext context = buildFederatedApiKeyContext(apiUuid, event.getUuid(), event.getName(),
                        null, referenceArtifactEntry.getValue(), event.getUser(), event.getApplicationUUId(),
                        organization, gatewayEnvironment, null, null, null, null);
                connector.revokeApiKey(context);
            } catch (APIManagementException e) {
                log.error("Failed to rollback federated API key creation in environment: "
                        + gatewayEnvironment.getEnvironmentId() + " for key UUID: " + event.getUuid(), e);
            }
        }
    }

    /**
     * Replaces remote API-key credentials during local API-key regeneration and persists returned reference artifacts.
     */
    private void handleRegenerate(APIKeyRegenerationEvent event) throws APIManagementException {
        if (StringUtils.isBlank(event.getApiKey()) || StringUtils.isBlank(event.getOldApiKeyUuid())
                || StringUtils.isBlank(event.getNewApiKeyUuid()) || StringUtils.isBlank(event.getApiUuid())) {
            throw new APIManagementException("Federated API key regenerate event is missing UUID/API context");
        }
        Map<String, String> apiKeyReferenceArtifacts =
                getApiMgtDAO().getApiKeyExternalApiKeyMappings(event.getOldApiKeyUuid());
        if (apiKeyReferenceArtifacts.isEmpty()) {
            log.warn("No per-environment remote API key reference artifacts found for federated API key UUID: "
                    + event.getOldApiKeyUuid() + ". Skipping remote replacement.");
            return;
        }
        String apiUuid = event.getApiUuid();
        String applicationUuid = event.getApplicationUuid();
        String organization = resolveOrganization(event);

        Map<String, String> currentGatewayMappings = resolveApiExternalMappingsByEnvironmentName(apiUuid);
        List<GatewayEnvironmentContext> gatewayEnvironments =
                resolveGatewayEnvironments(organization, apiKeyReferenceArtifacts.keySet(),
                        currentGatewayMappings);
        Map<String, String> replacementReferenceArtifacts = new LinkedHashMap<>();
        String localPolicyId = StringUtils.isNotBlank(applicationUuid) ?
                resolveSubscriptionPolicyId(applicationUuid, apiUuid) : null;
        for (GatewayEnvironmentContext gatewayEnvironment : gatewayEnvironments) {
            String apiKeyReferenceArtifact = apiKeyReferenceArtifacts.get(gatewayEnvironment.getEnvironmentId());
            if (StringUtils.isBlank(apiKeyReferenceArtifact)) {
                log.warn("Remote API key reference artifact is missing for federated API key UUID: "
                        + event.getOldApiKeyUuid()
                        + " in environment: " + gatewayEnvironment.getEnvironmentId()
                        + ". Skipping remote replacement.");
                continue;
            }

            FederatedApiKeyConnector connector = resolveConnector(organization, gatewayEnvironment);
            FederatedApiKeyContext context = buildFederatedApiKeyContext(apiUuid, event.getNewApiKeyUuid(),
                    null, event.getApiKey(), apiKeyReferenceArtifact, null, applicationUuid, organization,
                    gatewayEnvironment, null, null, null, localPolicyId);
            FederatedApiKeyCreationResult result = connector.replaceApiKey(context);
            if (result == null || StringUtils.isBlank(result.getReferenceArtifact())) {
                throw new APIManagementException("Federated API key replacement did not return a reference artifact "
                        + "for environment: " + gatewayEnvironment.getEnvironmentId());
            }
            replacementReferenceArtifacts.put(gatewayEnvironment.getEnvironmentId(), result.getReferenceArtifact());
        }

        try {
            for (Map.Entry<String, String> referenceArtifactEntry : replacementReferenceArtifacts.entrySet()) {
                getApiMgtDAO().addOrUpdateApiKeyExternalApiKeyMapping(event.getNewApiKeyUuid(),
                        referenceArtifactEntry.getKey(), referenceArtifactEntry.getValue());
            }
            getApiMgtDAO().deleteApiKeyExternalApiKeyMappings(event.getOldApiKeyUuid());
        } catch (APIManagementException e) {
            getApiMgtDAO().deleteApiKeyExternalApiKeyMappings(event.getNewApiKeyUuid());
            throw e;
        }
        log.info("Successfully regenerated federated API key on " + replacementReferenceArtifacts.size()
                + " gateway environment(s). OldKeyUuid: " + event.getOldApiKeyUuid() + ", NewKeyUuid: "
                + event.getNewApiKeyUuid());
    }

    private Map<String, String> resolveApiExternalMappingsByEnvironmentName(String apiUuid)
            throws APIManagementException {
        return new LinkedHashMap<>(APIUtil.getApiExternalApiMappingReferenceByApiId(apiUuid));
    }

    /**
     * Used by create flows that start from AM_API_EXTERNAL_API_MAPPING, where the source data is keyed by
     * environment name. This bridges the existing name-based API mapping model to the UUID-based context used for
     * API-key persistence and connector calls.
     */
    private List<GatewayEnvironmentContext> resolveGatewayEnvironments(String organization,
                                                                       Map<String, String> gatewayMappings)
            throws APIManagementException {
        if (gatewayMappings.isEmpty()) {
            throw new APIManagementException("No external gateway environment mappings found for the federated API");
        }
        List<GatewayEnvironmentContext> gatewayEnvironments = new ArrayList<>();
        Map<String, Environment> environments = APIUtil.getEnvironments(organization);
        for (Map.Entry<String, String> gatewayMapping : gatewayMappings.entrySet()) {
            Environment environment = environments.get(gatewayMapping.getKey());
            if (environment == null) {
                throw new APIManagementException("Gateway environment not found for external API mapping: "
                        + gatewayMapping.getKey());
            }
            gatewayEnvironments.add(new GatewayEnvironmentContext(environment.getUuid(), gatewayMapping.getValue()));
        }
        return gatewayEnvironments;
    }

    /**
     * Used by apply/remove/replace flows that start from API-key mappings keyed by environment UUID. These flows
     * still need the API reference artifact from AM_API_EXTERNAL_API_MAPPING, so this helper resolves UUID back to
     * environment name and then attaches the corresponding API reference artifact for connector operations.
     */
    private List<GatewayEnvironmentContext> resolveGatewayEnvironments(String organization,
                                                                       Set<String> environmentIds,
                                                                       Map<String, String> gatewayMappings)
            throws APIManagementException {
        List<GatewayEnvironmentContext> gatewayEnvironments = new ArrayList<>();
        Map<String, Environment> environments = APIUtil.getEnvironments(organization);
        for (String environmentId : new LinkedHashSet<>(environmentIds)) {
            if (StringUtils.isBlank(environmentId)) {
                continue;
            }
            Environment environment = resolveEnvironmentById(environments, environmentId);
            if (environment == null) {
                throw new APIManagementException("Gateway environment not found: " + environmentId);
            }
            String referenceArtifact = gatewayMappings.get(environment.getName());
            if (referenceArtifact == null) {
                throw new APIManagementException("API external mapping not found for environment: "
                        + environment.getName());
            }
            gatewayEnvironments.add(new GatewayEnvironmentContext(environmentId, referenceArtifact));
        }
        return gatewayEnvironments;
    }

    /**
     * Used by revoke flows that only need to locate the gateway environment by UUID. No API reference artifact is
     * required here because remote revocation is driven only by the stored API-key reference artifact.
     */
    private List<GatewayEnvironmentContext> resolveGatewayEnvironments(String organization, Set<String> environmentIds)
            throws APIManagementException {
        List<GatewayEnvironmentContext> gatewayEnvironments = new ArrayList<>();
        Map<String, Environment> environments = APIUtil.getEnvironments(organization);
        for (String environmentId : new LinkedHashSet<>(environmentIds)) {
            if (StringUtils.isBlank(environmentId)) {
                continue;
            }
            Environment environment = resolveEnvironmentById(environments, environmentId);
            if (environment == null) {
                throw new APIManagementException("Gateway environment not found: " + environmentId);
            }
            gatewayEnvironments.add(new GatewayEnvironmentContext(environmentId, null));
        }
        return gatewayEnvironments;
    }

    /**
     * Creates the connector instance for the selected external gateway environment.
     */
    private FederatedApiKeyConnector resolveConnector(String organization, GatewayEnvironmentContext environment)
            throws APIManagementException {
        return GatewayHolder.getTenantApiKeyConnectorInstance(organization, environment.getEnvironmentId());
    }

    private Environment resolveEnvironmentById(Map<String, Environment> environments, String environmentId) {
        for (Environment environment : environments.values()) {
            if (StringUtils.equals(environmentId, environment.getUuid())) {
                return environment;
            }
        }
        return null;
    }

    /**
     * Builds the connector operation context shared by create, revoke, and plan association operations.
     */
    private FederatedApiKeyContext buildFederatedApiKeyContext(String apiUuid, String apiKeyUuid, String apiKeyName,
                                                               String apiKeyValue, String apiKeyReferenceArtifact,
                                                               String authzUser, String applicationUuid,
                                                               String organization, GatewayEnvironmentContext env,
                                                               Long validityPeriod, String permittedIP,
                                                               String permittedReferer,
                                                               String localPolicyId) {
        return FederatedApiKeyContext.builder()
                .apiUuid(apiUuid)
                .apiName(null)
                .apiReferenceArtifact(env.getReferenceArtifact())
                .apiKeyUuid(apiKeyUuid)
                .apiKeyName(apiKeyName)
                .apiKeyValue(apiKeyValue)
                .apiKeyReferenceArtifact(apiKeyReferenceArtifact)
                .localPolicyId(localPolicyId)
                .authzUser(authzUser)
                .applicationUuid(applicationUuid)
                .organizationId(organization)
                .environmentId(env.getEnvironmentId())
                .validityPeriod(validityPeriod)
                .permittedIP(permittedIP)
                .permittedReferer(permittedReferer)
                .build();
    }

    private String resolveOrganization(Event event) throws APIManagementException {
        if (StringUtils.isBlank(event.getTenantDomain())) {
            throw new APIManagementException("Tenant domain is required for federated API key event processing");
        }
        return event.getTenantDomain();
    }

    private boolean isFederatedApi(String apiUuid) throws APIManagementException {
        return StringUtils.isNotBlank(apiUuid) && APIUtil.isFederatedGatewayApi(apiUuid);
    }

    /**
     * Resolves the application UUID for association events.
     */
    private String resolveApplicationUuid(APIKeyAssociationEvent event) throws APIManagementException {
        String applicationUuid = event.getApplicationUUId();
        if (StringUtils.isBlank(applicationUuid)) {
            throw new APIManagementException("Application UUID is required for federated API key association");
        }
        return applicationUuid;
    }

    /**
     * Resolves the active local subscription policy UUID for the application and API pair.
     */
    private String resolveSubscriptionPolicyId(String applicationUuid, String apiUuid) throws APIManagementException {
        if (StringUtils.isBlank(applicationUuid)) {
            throw new APIManagementException("Application UUID is required for federated API key association");
        }
        Application application = getApiMgtDAO().getApplicationByUUID(applicationUuid);
        if (application == null) {
            throw new APIManagementException("Application not found for UUID: " + applicationUuid);
        }
        Set<SubscribedAPI> subscribedAPIs = getApiMgtDAO().getSubscribedAPIsByApplication(application);
        for (SubscribedAPI subscribedAPI : subscribedAPIs) {
            if (!StringUtils.equals(apiUuid, subscribedAPI.getAPIUUId())) {
                continue;
            }
            if (!APIConstants.SubscriptionStatus.UNBLOCKED.equals(subscribedAPI.getSubStatus())) {
                throw new APIManagementException("API key association requires an active subscription for API: "
                        + apiUuid);
            }
            if (subscribedAPI.getTier() == null || StringUtils.isBlank(subscribedAPI.getTier().getName())) {
                throw new APIManagementException("Subscription tier is required for federated external plan mapping");
            }
            return resolveSubscriptionPolicyId(application, subscribedAPI.getTier().getName());
        }
        throw new APIManagementException("No active subscription found for application " + applicationUuid
                + " and API " + apiUuid);
    }

    private String resolveSubscriptionPolicyId(Application application, String policyName)
            throws APIManagementException {

        int tenantId = application.getSubscriber() != null && application.getSubscriber().getTenantId() > 0
                ? application.getSubscriber().getTenantId()
                : APIUtil.getTenantId(application.getSubscriber() != null ? application.getSubscriber().getName() : null);
        SubscriptionPolicy[] subscriptionPolicies =
                getApiMgtDAO().getSubscriptionPolicies(new String[] { policyName }, tenantId);
        if (subscriptionPolicies == null || subscriptionPolicies.length == 0
                || StringUtils.isBlank(subscriptionPolicies[0].getUUID())) {
            throw new APIManagementException("Subscription policy UUID not found for tier: " + policyName);
        }
        return subscriptionPolicies[0].getUUID();
    }

    /**
     * Returns the API management DAO singleton.
     */
    private ApiMgtDAO getApiMgtDAO() {
        return ApiMgtDAO.getInstance();
    }

    /**
     * Holds the gateway environment plus the connector-owned API reference artifact for that environment.
     */
    private static class GatewayEnvironmentContext {
        private final String environmentId;
        private final String referenceArtifact;

        private GatewayEnvironmentContext(String environmentId, String referenceArtifact) {
            this.environmentId = environmentId;
            this.referenceArtifact = referenceArtifact;
        }

        private String getEnvironmentId() {
            return environmentId;
        }

        private String getReferenceArtifact() {
            return referenceArtifact;
        }
    }
}
