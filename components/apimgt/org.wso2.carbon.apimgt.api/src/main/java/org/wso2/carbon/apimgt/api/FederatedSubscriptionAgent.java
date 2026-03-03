/*
 * Copyright (c) 2025 WSO2 LLC. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 LLC. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.wso2.carbon.apimgt.api;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.apimgt.api.model.AgentOperationResult;
import org.wso2.carbon.apimgt.api.model.Environment;
import org.wso2.carbon.apimgt.api.model.FederatedSubscriptionContext;
import org.wso2.carbon.apimgt.api.model.FederatedSubscriptionOptions;
import org.wso2.carbon.apimgt.api.model.SubscriptionSupportInfo;

/**
 * Interface for managing subscriptions in external gateways.
 * <p>
 * Mutating operations (create) return {@link AgentOperationResult}
 * containing credential, instruction, and reference artifact.
 * </p>
 * <p>
 * {@code selectedOption} is a create-time parameter passed to {@link #createSubscription}.
 * </p>
 */
public interface FederatedSubscriptionAgent {

    Log log = LogFactory.getLog(FederatedSubscriptionAgent.class);

    /**
     * Initializes the subscription agent.
     *
     * @param environment  The gateway environment configuration
     * @param organization The organization identifier
     * @throws APIManagementException If initialization fails
     */
    void init(Environment environment, String organization) throws APIManagementException;

    /**
     * Creates a subscription in the external gateway.
     *
     * @param context        The subscription context
     * @param selectedOption Developer's chosen subscription option
     * @return AgentOperationResult with credential, instruction, and reference artifact
     * @throws APIManagementException If subscription creation fails
     */
    AgentOperationResult createSubscription(FederatedSubscriptionContext context, String selectedOption)
            throws APIManagementException;

    /**
     * Deletes a subscription from the external gateway.
     *
     * @param context The subscription context
     * @throws APIManagementException If subscription deletion fails
     */
    void deleteSubscription(FederatedSubscriptionContext context) throws APIManagementException;

    /**
     * Retrieves the full credential value from the gateway.
     * Only supported by gateways that allow credential retrieval (Azure).
     *
     * @param context The subscription context
     * @param includeFullCredentials Whether to include the full credential value
     * @return AgentOperationResult with the full credential
     * @throws APIManagementException If retrieval fails or is not supported
     */
    default AgentOperationResult retrieveSubscription(FederatedSubscriptionContext context, boolean includeFullCredentials) throws APIManagementException {
        throw new APIManagementException("This gateway does not support credential retrieval");
    }

    /**
     * Checks if a subscription exists in the external gateway.
     *
     * @param context The subscription context containing external subscription identifier
     * @return true if the subscription exists, false otherwise
     * @throws APIManagementException If the validation fails
     */
    default boolean subscriptionExists(FederatedSubscriptionContext context) throws APIManagementException {
        return true;
    }

    /**
     * Gets the gateway type identifier for this subscription agent.
     *
     * @return The gateway type (e.g., "Azure", "AWS", "Kong", "Envoy")
     */
    String getGatewayType();

    /**
     * Returns whether this agent/gateway supports managed subscription workflows.
     * Defaults to false for backward compatibility.
     *
     * @return true when subscription operations are supported by this gateway
     */
    default boolean isSubscriptionSupport() {
        return false;
    }

    /**
     * Fetches the full federation config live from the gateway, including the invocation template.
     * Used by the Publisher to populate and refresh {@code AM_API_FEDERATION_CONFIG}.
     * <p>
     * Default implementation returns a backward-compatible SECURED result with opaque-api-key.
     * Override to perform the actual live gateway call and include the invocation template.
     * </p>
     *
     * @param context The subscription context containing the API reference artifact
     * @return live SubscriptionSupportInfo
     * @throws APIManagementException if the gateway call fails
     */
    default SubscriptionSupportInfo getFederationConfigProvider(FederatedSubscriptionContext context)
            throws APIManagementException {
        return new SubscriptionSupportInfo.Builder()
                .status(SubscriptionSupportInfo.SubscriptionStatus.SECURED)
                .supportedAuthTypes(new String[]{"opaque-api-key"})
                .build();
    }

    /**
     * Returns the federation config for the DevPortal consumer view.
     * Reads the publisher-curated config from {@code context.getFederationConfigSnapshot()} and
     * filters out plans that the publisher has disabled (plans with {@code enabled=false}).
     * Falls back to a live gateway call via {@link #getFederationConfigProvider} when no snapshot
     * is available (e.g. APIs not yet re-discovered after the feature was introduced).
     *
     * @param context The subscription context, enriched with the stored federation config snapshot
     * @return SubscriptionSupportInfo with disabled plans removed
     * @throws APIManagementException if the fallback gateway call fails
     */
    default SubscriptionSupportInfo getFederationConfigConsumer(FederatedSubscriptionContext context)
            throws APIManagementException {
        SubscriptionSupportInfo snapshot = context.getFederationConfigSnapshot();
        if (snapshot == null) {
            return getFederationConfigProvider(context);
        }
        snapshot.filterDisabledPlans();
        return snapshot;
    }
}
