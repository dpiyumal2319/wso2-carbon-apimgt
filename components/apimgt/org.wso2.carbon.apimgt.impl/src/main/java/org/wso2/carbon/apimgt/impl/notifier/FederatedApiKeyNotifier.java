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
import org.wso2.carbon.apimgt.api.model.APIKeyInfo;
import org.wso2.carbon.apimgt.api.model.Application;
import org.wso2.carbon.apimgt.api.model.Environment;
import org.wso2.carbon.apimgt.api.model.FederatedApiKeyContext;
import org.wso2.carbon.apimgt.api.model.FederatedApiKeyCreationResult;
import org.wso2.carbon.apimgt.api.model.GatewayTierMapping;
import org.wso2.carbon.apimgt.api.model.SubscribedAPI;
import org.wso2.carbon.apimgt.impl.APIConstants;
import org.wso2.carbon.apimgt.impl.dao.ApiKeyMgtDAO;
import org.wso2.carbon.apimgt.impl.dao.ApiMgtDAO;
import org.wso2.carbon.apimgt.impl.federated.gateway.FederatedApiKeyConnectorFactory;
import org.wso2.carbon.apimgt.impl.notifier.events.APIKeyAssociationEvent;
import org.wso2.carbon.apimgt.impl.notifier.events.APIKeyEvent;
import org.wso2.carbon.apimgt.impl.notifier.events.Event;
import org.wso2.carbon.apimgt.impl.notifier.exceptions.NotifierException;

import java.util.ArrayList;
import java.util.Collections;
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
    private static final String FEDERATED_API_KEY_REMOTE_ID = "federated.remoteApiKeyId";
    private static final String FEDERATED_API_KEY_REMOTE_ID_PREFIX = FEDERATED_API_KEY_REMOTE_ID + ".";
    private static final String FEDERATED_API_KEY_VALUE = "federated.apiKeyValue";

    @Override
    public boolean publishEvent(Event event) throws NotifierException {
        if (log.isDebugEnabled()) {
            log.debug("Processing federated API key event: " + event);
        }

        try {
            if (event instanceof APIKeyEvent) {
                APIKeyEvent apiKeyEvent = (APIKeyEvent) event;
                switch (apiKeyEvent.getType()) {
                    case "API_KEY_CREATE":
                        handleCreate(apiKeyEvent);
                        break;
                    case "API_KEY_DELETE":
                        handleRevoke(apiKeyEvent);
                        break;
                    default:
                        log.warn("Unknown federated API key event type: " + apiKeyEvent.getType());
                }
            } else if (event instanceof APIKeyAssociationEvent) {
                APIKeyAssociationEvent associationEvent = (APIKeyAssociationEvent) event;
                switch (associationEvent.getType()) {
                    case "API_KEY_ASSOCIATION_CREATE":
                        handleApplyRateLimitPolicy(associationEvent);
                        break;
                    case "API_KEY_ASSOCIATION_DELETE":
                        handleRemoveRateLimitPolicy(associationEvent);
                        break;
                    default:
                        log.warn("Unknown federated API key association event type: " + associationEvent.getType());
                }
            }
            return true;
        } catch (APIManagementException e) {
            log.error("Failed to process federated API key event: " + event, e);
            throw new NotifierException("Failed to process federated API key event", e);
        }
    }

    @Override
    public String getType() {
        return APIConstants.NotifierType.FEDERATED_API_KEY.name();
    }

    private void handleCreate(APIKeyEvent event) throws APIManagementException {
        APIKeyInfo keyInfo = getApiKeyMgtDAO().getAPIKey(event.getUuid(), event.getUser());
        String apiUuid = resolveApiUuid(event, keyInfo);
        String organization = resolveOrganization(apiUuid);
        String apiKeyValue = getEventProperties(event).get(FEDERATED_API_KEY_VALUE);
        if (StringUtils.isBlank(apiKeyValue)) {
            throw new APIManagementException("Federated API key create event is missing the generated key value");
        }

        List<GatewayEnvironmentContext> gatewayEnvironments = resolveMappedGatewayEnvironments(apiUuid, organization);
        Map<String, String> remoteApiKeyIds = new LinkedHashMap<>();
        try {
            for (GatewayEnvironmentContext gatewayEnvironment : gatewayEnvironments) {
                FederatedApiKeyConnector connector = resolveConnector(organization, gatewayEnvironment.getEnvironment());
                FederatedApiKeyContext context = buildFederatedApiKeyContext(apiUuid, event.getUuid(), event.getName(),
                        apiKeyValue, null, resolveAuthUser(event, keyInfo), resolveApplicationUuid(event, keyInfo),
                        organization, gatewayEnvironment, event.getValidityPeriod(), event.getPermittedIP(),
                        event.getPermittedReferer());
                FederatedApiKeyCreationResult result = connector.createApiKey(context);
                if (result == null || StringUtils.isBlank(result.getRemoteCredentialId())) {
                    throw new APIManagementException("Federated API key creation did not return a remote credential ID "
                            + "for environment: " + gatewayEnvironment.getEnvironmentId());
                }
                remoteApiKeyIds.put(gatewayEnvironment.getEnvironmentId(), result.getRemoteCredentialId());
            }
        } catch (APIManagementException e) {
            rollbackCreatedApiKeys(apiUuid, organization, event, keyInfo, gatewayEnvironments, remoteApiKeyIds);
            throw e;
        }

        Map<String, String> updatedProperties = mergeApiKeyProperties(keyInfo, remoteApiKeyIds);
        if (StringUtils.isNotBlank(event.getPermittedIP())) {
            updatedProperties.put(APIConstants.JwtTokenConstants.PERMITTED_IP, event.getPermittedIP());
        }
        if (StringUtils.isNotBlank(event.getPermittedReferer())) {
            updatedProperties.put(APIConstants.JwtTokenConstants.PERMITTED_REFERER, event.getPermittedReferer());
        }

        getApiKeyMgtDAO().updateApiKeyGatewaySync(event.getUuid(), updatedProperties);
        log.info("Successfully created federated API key on " + remoteApiKeyIds.size()
                + " gateway environment(s). KeyUuid: " + event.getUuid());
    }

    private void handleRevoke(APIKeyEvent event) throws APIManagementException {
        APIKeyInfo keyInfo = getApiKeyMgtDAO().getAPIKeyForTenantAnyStatus(event.getUuid(), event.getTenantDomain());
        String apiUuid = resolveApiUuid(event, keyInfo);
        String organization = resolveOrganization(apiUuid);
        Map<String, String> remoteApiKeyIds = resolveRemoteApiKeyIds(keyInfo);
        if (remoteApiKeyIds.isEmpty()) {
            log.warn("No per-environment remote API key IDs found for federated API key UUID: " + event.getUuid()
                    + ". Skipping remote revocation.");
            return;
        }

        List<GatewayEnvironmentContext> gatewayEnvironments =
                resolveGatewayEnvironments(apiUuid, organization, remoteApiKeyIds.keySet(), null);
        for (GatewayEnvironmentContext gatewayEnvironment : gatewayEnvironments) {
            String remoteApiKeyId = remoteApiKeyIds.get(gatewayEnvironment.getEnvironmentId());
            if (StringUtils.isBlank(remoteApiKeyId)) {
                log.warn("Remote API key ID is missing for federated API key UUID: " + event.getUuid()
                        + " in environment: " + gatewayEnvironment.getEnvironmentId() + ". Skipping remote revocation.");
                continue;
            }

            FederatedApiKeyConnector connector = resolveConnector(organization, gatewayEnvironment.getEnvironment());
            FederatedApiKeyContext context = buildFederatedApiKeyContext(apiUuid, event.getUuid(), event.getName(),
                    null, remoteApiKeyId, resolveAuthUser(event, keyInfo), resolveApplicationUuid(event, keyInfo),
                    organization, gatewayEnvironment, null, null, null);
            connector.revokeApiKey(context);
        }

        log.info("Successfully revoked federated API key on " + remoteApiKeyIds.size()
                + " gateway environment(s). KeyUuid: " + event.getUuid());
    }

    private void handleApplyRateLimitPolicy(APIKeyAssociationEvent event) throws APIManagementException {
        APIKeyInfo keyInfo = resolveAssociationKeyInfo(event);
        String apiUuid = resolveApiUuid(event, keyInfo);
        String applicationUuid = resolveApplicationUuid(event, keyInfo);
        String organization = resolveOrganization(apiUuid);
        String localTierName = resolveSubscriptionTierName(applicationUuid, apiUuid);
        Map<String, String> remoteApiKeyIds = resolveRemoteApiKeyIds(keyInfo);
        if (remoteApiKeyIds.isEmpty()) {
            throw new APIManagementException("No per-environment remote API key IDs found for federated API key UUID: "
                    + event.getApiKeyUUId());
        }

        List<GatewayEnvironmentContext> gatewayEnvironments = resolveMappedGatewayEnvironments(apiUuid, organization);
        for (GatewayEnvironmentContext gatewayEnvironment : gatewayEnvironments) {
            String remoteApiKeyId = remoteApiKeyIds.get(gatewayEnvironment.getEnvironmentId());
            if (StringUtils.isBlank(remoteApiKeyId)) {
                throw new APIManagementException("Remote API key ID is required for applying rate limit policy in "
                        + "environment: " + gatewayEnvironment.getEnvironmentId());
            }

            FederatedApiKeyConnector connector = resolveConnector(organization, gatewayEnvironment.getEnvironment());
            String remotePolicyId = resolveRemotePolicyId(gatewayEnvironment.getEnvironment(), localTierName, connector);
            FederatedApiKeyContext context = buildFederatedApiKeyContext(apiUuid, event.getApiKeyUUId(),
                    keyInfo.getKeyName(), null, remoteApiKeyId, keyInfo.getAuthUser(), applicationUuid, organization,
                    gatewayEnvironment, null, null, null);
            connector.applyRateLimitPolicy(context, remotePolicyId);
        }

        log.info("Successfully applied rate limit policy to federated API key across "
                + gatewayEnvironments.size() + " gateway environment(s). KeyUuid: " + event.getApiKeyUUId());
    }

    private void handleRemoveRateLimitPolicy(APIKeyAssociationEvent event) throws APIManagementException {
        APIKeyInfo keyInfo = resolveAssociationKeyInfo(event);
        String apiUuid = resolveApiUuid(event, keyInfo);
        String applicationUuid = resolveApplicationUuid(event, keyInfo);
        String organization = resolveOrganization(apiUuid);
        Map<String, String> remoteApiKeyIds = resolveRemoteApiKeyIds(keyInfo);
        if (remoteApiKeyIds.isEmpty()) {
            log.warn("No per-environment remote API key IDs found for federated API key UUID: " + event.getApiKeyUUId()
                    + ". Skipping remote policy removal.");
            return;
        }

        Map<String, String> currentGatewayMappings = getApiMgtDAO().getApiExternalGatewayMappings(apiUuid);
        List<GatewayEnvironmentContext> gatewayEnvironments =
                resolveGatewayEnvironments(apiUuid, organization, remoteApiKeyIds.keySet(), currentGatewayMappings);
        for (GatewayEnvironmentContext gatewayEnvironment : gatewayEnvironments) {
            String remoteApiKeyId = remoteApiKeyIds.get(gatewayEnvironment.getEnvironmentId());
            if (StringUtils.isBlank(remoteApiKeyId)) {
                log.warn("Remote API key ID is missing for federated API key UUID: " + event.getApiKeyUUId()
                        + " in environment: " + gatewayEnvironment.getEnvironmentId()
                        + ". Skipping remote policy removal.");
                continue;
            }

            FederatedApiKeyConnector connector = resolveConnector(organization, gatewayEnvironment.getEnvironment());
            FederatedApiKeyContext context = buildFederatedApiKeyContext(apiUuid, event.getApiKeyUUId(),
                    keyInfo.getKeyName(), null, remoteApiKeyId, keyInfo.getAuthUser(), applicationUuid, organization,
                    gatewayEnvironment, null, null, null);
            connector.removeRateLimitPolicy(context);
        }

        log.info("Successfully removed rate limit policy from federated API key across "
                + gatewayEnvironments.size() + " gateway environment(s). KeyUuid: " + event.getApiKeyUUId());
    }

    private void rollbackCreatedApiKeys(String apiUuid, String organization, APIKeyEvent event, APIKeyInfo keyInfo,
                                        List<GatewayEnvironmentContext> gatewayEnvironments,
                                        Map<String, String> remoteApiKeyIds) {
        if (remoteApiKeyIds.isEmpty()) {
            return;
        }

        Map<String, GatewayEnvironmentContext> gatewayEnvironmentMap = new HashMap<>();
        for (GatewayEnvironmentContext gatewayEnvironment : gatewayEnvironments) {
            gatewayEnvironmentMap.put(gatewayEnvironment.getEnvironmentId(), gatewayEnvironment);
        }

        for (Map.Entry<String, String> remoteApiKeyIdEntry : remoteApiKeyIds.entrySet()) {
            GatewayEnvironmentContext gatewayEnvironment = gatewayEnvironmentMap.get(remoteApiKeyIdEntry.getKey());
            if (gatewayEnvironment == null) {
                continue;
            }

            try {
                FederatedApiKeyConnector connector = resolveConnector(organization, gatewayEnvironment.getEnvironment());
                FederatedApiKeyContext context = buildFederatedApiKeyContext(apiUuid, event.getUuid(), event.getName(),
                        null, remoteApiKeyIdEntry.getValue(), resolveAuthUser(event, keyInfo),
                        resolveApplicationUuid(event, keyInfo), organization, gatewayEnvironment, null, null, null);
                connector.revokeApiKey(context);
            } catch (APIManagementException e) {
                log.error("Failed to rollback federated API key creation in environment: "
                        + gatewayEnvironment.getEnvironmentId() + " for key UUID: " + event.getUuid(), e);
            }
        }
    }

    private List<GatewayEnvironmentContext> resolveMappedGatewayEnvironments(String apiUuid, String organization)
            throws APIManagementException {
        Map<String, String> gatewayMappings = getApiMgtDAO().getApiExternalGatewayMappings(apiUuid);
        if (gatewayMappings.isEmpty()) {
            throw new APIManagementException("No external gateway environment mappings found for federated API: "
                    + apiUuid);
        }
        return resolveGatewayEnvironments(apiUuid, organization, gatewayMappings.keySet(), gatewayMappings);
    }

    private List<GatewayEnvironmentContext> resolveGatewayEnvironments(String apiUuid, String organization,
                                                                       Set<String> environmentIds,
                                                                       Map<String, String> gatewayMappings)
            throws APIManagementException {
        List<GatewayEnvironmentContext> gatewayEnvironments = new ArrayList<>();
        for (String environmentId : new LinkedHashSet<>(environmentIds)) {
            if (StringUtils.isBlank(environmentId)) {
                continue;
            }
            Environment environment = getApiMgtDAO().getEnvironment(organization, environmentId);
            if (environment == null) {
                throw new APIManagementException("Gateway environment not found: " + environmentId);
            }
            String referenceArtifact = gatewayMappings != null ? gatewayMappings.get(environmentId) : null;
            if (referenceArtifact == null) {
                referenceArtifact = getApiMgtDAO().getApiExternalApiMappingReference(apiUuid, environmentId);
            }
            gatewayEnvironments.add(new GatewayEnvironmentContext(environmentId, environment, referenceArtifact));
        }
        return gatewayEnvironments;
    }

    private FederatedApiKeyConnector resolveConnector(String organization, Environment environment)
            throws APIManagementException {
        return FederatedApiKeyConnectorFactory.getApiKeyConnector(environment, organization);
    }

    private String resolveRemotePolicyId(Environment environment, String localTierName,
                                         FederatedApiKeyConnector connector) throws APIManagementException {
        if (StringUtils.isBlank(localTierName)) {
            throw new APIManagementException("Local application tier is required for external tier mapping");
        }
        List<GatewayTierMapping> tierMappings = environment.getTierMappings();
        if (tierMappings == null || tierMappings.isEmpty()) {
            throw new APIManagementException("No external tier mappings configured for environment: "
                    + environment.getUuid());
        }
        for (GatewayTierMapping tierMapping : tierMappings) {
            if (tierMapping != null && StringUtils.equalsIgnoreCase(localTierName, tierMapping.getLocalTierName())) {
                if (StringUtils.isBlank(tierMapping.getRemotePlanReference())) {
                    throw new APIManagementException("External tier is not configured for local tier: " + localTierName);
                }
                String remotePolicyId = connector.resolveRemotePolicyId(tierMapping.getRemotePlanReference());
                if (StringUtils.isBlank(remotePolicyId)) {
                    throw new APIManagementException("External tier is not configured for local tier: "
                            + localTierName);
                }
                return remotePolicyId;
            }
        }
        throw new APIManagementException("No external tier mapping found for local tier: " + localTierName);
    }

    private FederatedApiKeyContext buildFederatedApiKeyContext(String apiUuid, String apiKeyUuid, String apiKeyName,
                                                               String apiKeyValue, String remoteApiKeyId,
                                                               String authzUser, String applicationUuid,
                                                               String organization, GatewayEnvironmentContext env,
                                                               Long validityPeriod, String permittedIP,
                                                               String permittedReferer) {
        return FederatedApiKeyContext.builder()
                .apiUuid(apiUuid)
                .apiName(null)
                .apiReferenceArtifact(env.getReferenceArtifact())
                .apiKeyUuid(apiKeyUuid)
                .apiKeyName(apiKeyName)
                .apiKeyValue(apiKeyValue)
                .remoteApiKeyId(remoteApiKeyId)
                .authzUser(authzUser)
                .applicationUuid(applicationUuid)
                .organizationId(organization)
                .environmentId(env.getEnvironmentId())
                .validityPeriod(validityPeriod)
                .permittedIP(permittedIP)
                .permittedReferer(permittedReferer)
                .build();
    }

    private Map<String, String> mergeApiKeyProperties(APIKeyInfo keyInfo, Map<String, String> remoteApiKeyIds) {
        Map<String, String> properties = new HashMap<>();
        if (keyInfo != null && keyInfo.getProperties() != null) {
            properties.putAll(keyInfo.getProperties());
        }
        properties.remove(FEDERATED_API_KEY_REMOTE_ID);
        properties.keySet().removeIf(key -> key != null && key.startsWith(FEDERATED_API_KEY_REMOTE_ID_PREFIX));
        for (Map.Entry<String, String> remoteApiKeyIdEntry : remoteApiKeyIds.entrySet()) {
            properties.put(FEDERATED_API_KEY_REMOTE_ID_PREFIX + remoteApiKeyIdEntry.getKey(),
                    remoteApiKeyIdEntry.getValue());
        }
        return properties;
    }

    private Map<String, String> resolveRemoteApiKeyIds(APIKeyInfo keyInfo) {
        if (keyInfo == null || keyInfo.getProperties() == null || keyInfo.getProperties().isEmpty()) {
            return Collections.emptyMap();
        }

        Map<String, String> remoteApiKeyIds = new LinkedHashMap<>();
        for (Map.Entry<String, String> propertyEntry : keyInfo.getProperties().entrySet()) {
            String propertyKey = propertyEntry.getKey();
            if (!StringUtils.startsWith(propertyKey, FEDERATED_API_KEY_REMOTE_ID_PREFIX)) {
                continue;
            }
            String environmentId = StringUtils.substringAfter(propertyKey, FEDERATED_API_KEY_REMOTE_ID_PREFIX);
            if (StringUtils.isBlank(environmentId) || StringUtils.isBlank(propertyEntry.getValue())) {
                continue;
            }
            remoteApiKeyIds.put(environmentId, propertyEntry.getValue());
        }
        return remoteApiKeyIds;
    }

    private String resolveOrganization(String apiUuid) throws APIManagementException {
        if (StringUtils.isBlank(apiUuid)) {
            throw new APIManagementException("API UUID is required for federated API key event processing");
        }
        String organization = getApiMgtDAO().getOrganizationByAPIUUID(apiUuid);
        if (StringUtils.isBlank(organization)) {
            throw new APIManagementException("Unable to resolve organization for federated API UUID: " + apiUuid);
        }
        return organization;
    }

    private String resolveApiUuid(APIKeyEvent event, APIKeyInfo keyInfo) throws APIManagementException {
        String apiUuid = event.getApiUUId();
        if (StringUtils.isBlank(apiUuid) && keyInfo != null) {
            apiUuid = keyInfo.getApiUUId();
        }
        if (StringUtils.isBlank(apiUuid)) {
            throw new APIManagementException("API UUID is required for federated API key event processing");
        }
        return apiUuid;
    }

    private String resolveApiUuid(APIKeyAssociationEvent event, APIKeyInfo keyInfo) throws APIManagementException {
        String apiUuid = event.getApiUUId();
        if (StringUtils.isBlank(apiUuid) && keyInfo != null) {
            apiUuid = keyInfo.getApiUUId();
        }
        if (StringUtils.isBlank(apiUuid)) {
            throw new APIManagementException("API UUID is required for federated API key event processing");
        }
        return apiUuid;
    }

    private String resolveApplicationUuid(APIKeyEvent event, APIKeyInfo keyInfo) {
        if (StringUtils.isNotBlank(event.getApplicationUUId())) {
            return event.getApplicationUUId();
        }
        return keyInfo != null ? keyInfo.getApplicationId() : null;
    }

    private String resolveApplicationUuid(APIKeyAssociationEvent event, APIKeyInfo keyInfo) {
        if (StringUtils.isNotBlank(event.getApplicationUUId())) {
            return event.getApplicationUUId();
        }
        return keyInfo != null ? keyInfo.getApplicationId() : null;
    }

    private String resolveAuthUser(APIKeyEvent event, APIKeyInfo keyInfo) {
        if (StringUtils.isNotBlank(event.getUser())) {
            return event.getUser();
        }
        return keyInfo != null ? keyInfo.getAuthUser() : null;
    }

    private String resolveSubscriptionTierName(String applicationUuid, String apiUuid) throws APIManagementException {
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
                throw new APIManagementException("Subscription tier is required for federated external tier mapping");
            }
            return subscribedAPI.getTier().getName();
        }
        throw new APIManagementException("No active subscription found for application " + applicationUuid
                + " and API " + apiUuid);
    }

    private Map<String, String> getEventProperties(APIKeyEvent event) {
        return event.getProperties() == null ? Collections.emptyMap() : (Map<String, String>) event.getProperties();
    }

    private APIKeyInfo resolveAssociationKeyInfo(APIKeyAssociationEvent event) throws APIManagementException {
        if (StringUtils.isBlank(event.getApiKeyUUId())) {
            throw new APIManagementException("API key UUID is required for federated API key association");
        }
        return getApiKeyMgtDAO().getAPIKeyForTenant(event.getApiKeyUUId(), event.getTenantDomain());
    }

    private ApiKeyMgtDAO getApiKeyMgtDAO() {
        return ApiKeyMgtDAO.getInstance();
    }

    private ApiMgtDAO getApiMgtDAO() {
        return ApiMgtDAO.getInstance();
    }

    private static class GatewayEnvironmentContext {
        private final String environmentId;
        private final Environment environment;
        private final String referenceArtifact;

        private GatewayEnvironmentContext(String environmentId, Environment environment, String referenceArtifact) {
            this.environmentId = environmentId;
            this.environment = environment;
            this.referenceArtifact = referenceArtifact;
        }

        private String getEnvironmentId() {
            return environmentId;
        }

        private Environment getEnvironment() {
            return environment;
        }

        private String getReferenceArtifact() {
            return referenceArtifact;
        }
    }
}
