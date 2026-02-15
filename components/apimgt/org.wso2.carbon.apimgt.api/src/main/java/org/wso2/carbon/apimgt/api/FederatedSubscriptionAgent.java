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

import org.wso2.carbon.apimgt.api.model.AgentOperationResult;
import org.wso2.carbon.apimgt.api.model.Environment;
import org.wso2.carbon.apimgt.api.model.FederatedSubscriptionContext;
import org.wso2.carbon.apimgt.api.model.FederatedSubscriptionOptions;
import org.wso2.carbon.apimgt.api.model.SubscriptionSupportInfo;

/**
 * Interface for managing subscriptions in external gateways.
 * <p>
 * Mutating operations (create, regenerate) return {@link AgentOperationResult}
 * containing credential, instruction, and reference artifact.
 * </p>
 * <p>
 * {@code selectedOption} is a create-time parameter passed to {@link #createSubscription}.
 * </p>
 */
public interface FederatedSubscriptionAgent {

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
     * Regenerates a credential for an existing subscription.
     * <p>
     * Default implementation deletes and recreates the subscription.
     * Override to preserve metadata from the old artifact.
     * </p>
     *
     * @param context The subscription context
     * @return AgentOperationResult with the new credential and reference artifact
     * @throws APIManagementException If regeneration fails
     */
    default AgentOperationResult regenerateCredential(FederatedSubscriptionContext context)
            throws APIManagementException {
        try {
            deleteSubscription(context);
        } catch (APIManagementException e) {
            // best-effort delete, continue with create
        }
        FederatedSubscriptionContext createCtx = context.toBuilder()
                .externalSubscriptionId(null)
                .subscriptionReferenceArtifact(null)
                .build();
        return createSubscription(createCtx, null);
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
     * Gets subscription support information for the API on the external gateway.
     * <p>
     * Returns a {@link SubscriptionSupportInfo} object containing:
     * <ul>
     *   <li>status: OPEN (no credentials needed) or SECURED (credentials required, subscription management available)</li>
     *   <li>supportedAuthTypes: Non-empty array for SECURED status only</li>
     *   <li>subscriptionOptions: Subscription options for SECURED status (null if no options)</li>
     * </ul>
     * </p>
     * <p>
     * Default implementation returns SECURED with opaque-api-key auth type (backward-compatible).
     * </p>
     *
     * @param context The subscription context containing API reference artifact
     * @return SubscriptionSupportInfo with status and related metadata
     * @throws APIManagementException if check fails
     */
    default SubscriptionSupportInfo getSubscriptionSupportInfo(FederatedSubscriptionContext context) 
            throws APIManagementException {
        // Default: backward-compatible SECURED status with opaque API key
        return new SubscriptionSupportInfo.Builder()
                .status(SubscriptionSupportInfo.SubscriptionStatus.SECURED)
                .supportedAuthTypes(new String[]{"opaque-api-key"})
                .build();
    }
}
