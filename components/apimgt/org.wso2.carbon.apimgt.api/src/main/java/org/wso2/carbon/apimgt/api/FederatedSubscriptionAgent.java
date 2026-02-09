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

import org.wso2.carbon.apimgt.api.model.Environment;
import org.wso2.carbon.apimgt.api.model.FederatedCredential;
import org.wso2.carbon.apimgt.api.model.FederatedSubscriptionContext;
import org.wso2.carbon.apimgt.api.model.FederatedSubscriptionOptions;
import org.wso2.carbon.apimgt.api.model.InvocationInstruction;

/**
 * Interface for managing subscriptions in external gateways.
 * All federation agents must implement this interface to enable subscription management
 * for their respective gateway platforms (AWS, Azure, Kong, Envoy).
 * <p>
 * This interface handles the full subscription lifecycle:
 * - Creating subscriptions with credential generation
 * - Deleting subscriptions
 * - Providing API invocation instructions
 * </p>
 */
public interface FederatedSubscriptionAgent {

    /**
     * Initializes the subscription agent with environment configurations.
     * This method must be called before any subscription operations.
     *
     * @param environment  The gateway environment configuration containing
     *                     connection details and credentials
     * @param organization The organization identifier for multi-tenant support
     * @throws APIManagementException If initialization fails (e.g., invalid credentials,
     *                                unreachable gateway)
     */
    void init(Environment environment, String organization) throws APIManagementException;

    /**
     * Creates a subscription in the external gateway and returns the credential.
     * <p>
     * This operation creates all necessary gateway resources (e.g., API key, ACL entry)
     * and returns the full credential for one-time display to the user.
     * </p>
     *
     * @param context The subscription context containing complete WSO2 and gateway information
     * @return FederatedCredential containing the full credential value (one-time display) (Unmasked)
     * @throws APIManagementException If subscription creation fails
     */
    FederatedCredential createSubscription(FederatedSubscriptionContext context) throws APIManagementException;

    /**
     * Deletes a subscription from the external gateway.
     * <p>
     * This operation removes all gateway resources associated with the subscription
     * (e.g., API key, ACL entry). The operation should be idempotent - if the
     * subscription doesn't exist, it should not throw an error.
     * </p>
     *
     * @param context The subscription context containing all information needed for deletion
     * @throws APIManagementException If subscription deletion fails
     */
    void deleteSubscription(FederatedSubscriptionContext context) throws APIManagementException;

    /**
     * Retrieves the full credential value from the gateway.
     * Only supported by gateways that allow credential retrieval (Azure).
     * <p>
     * Default implementation throws an exception. Override for gateways that support it.
     * </p>
     *
     * @param context The subscription context containing all information needed for retrieval
     * @return FederatedCredential containing the full credential value
     * @throws APIManagementException If retrieval fails or is not supported
     */
    default FederatedCredential retrieveCredential(FederatedSubscriptionContext context) throws APIManagementException {
        throw new APIManagementException("This gateway does not support credential retrieval");
    }

    /**
     * Gets the API invocation instructions from the context.
     * Each connector implementation parses its own reference artifact format.
     *
     * @param context The subscription context containing API reference artifact
     * @return InvocationInstruction containing API invocation details
     * @throws APIManagementException If fetching instructions fails
     */
    InvocationInstruction getInvocationInstruction(FederatedSubscriptionContext context) throws APIManagementException;

    /**
     * Builds the subscription reference artifact JSON for storage in AM_SUBSCRIPTION_EXTERNAL_MAPPING.
     * Each connector owns its reference artifact format and encodes credential info,
     * invocation instructions, and any gateway-specific metadata.
     *
     * @param credential  The credential returned from createSubscription/regenerateCredential
     * @param instruction The invocation instruction (may be null)
     * @param context     The subscription context containing selectedOption for regeneration
     * @return JSON string to store as the subscription reference artifact
     */
    String buildSubscriptionReferenceArtifact(FederatedCredential credential,
                                               InvocationInstruction instruction,
                                               FederatedSubscriptionContext context);

    /**
     * Extracts a masked credential from the subscription reference artifact.
     * Each connector parses its own reference artifact format.
     *
     * @param context The subscription context containing subscription reference artifact
     * @return FederatedCredential with masked values, or null if not available
     */
    FederatedCredential extractCredentialFromReferenceArtifact(FederatedSubscriptionContext context);

    /**
     * Checks if a subscription exists in the external gateway.
     * <p>
     * Default implementation returns true. Override for gateways that support
     * existence checking.
     * </p>
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
     * Used for agent selection and logging.
     *
     * @return The gateway type (e.g., "Azure", "AWS", "Kong", "Envoy")
     */
    String getGatewayType();

    /**
     * Checks supported authentication types for the API on the external gateway.
     * <p>
     * This method queries the external gateway to determine if the API requires
     * subscription-level authentication and what credential types are supported.
     * </p>
     * <p>
     * Default implementation assumes subscription is required with opaque API key authentication.
     * Override this method to implement gateway-specific logic.
     * </p>
     *
     * @param context The subscription context containing API reference artifact
     * @return Array of supported auth type strings (e.g., ["opaque-api-key"]), empty if no subscription security
     * @throws APIManagementException if check fails
     */
    default String[] getSupportedAuthTypes(FederatedSubscriptionContext context) throws APIManagementException {
        // Default: assume subscription required with opaque API key
        return new String[]{"opaque-api-key"};
    }

    /**
     * Discovers subscription options available from the external gateway for an API.
     * <p>
     * Returns gateway-specific subscription tiers/plans (e.g., AWS usage plans with throttle/quota).
     * Uses opaque body pattern - backend never parses options, only frontend.
     * </p>
     * <p>
     * Default implementation returns null, indicating no subscription options are available.
     * Gateways with subscription tiers should override this method.
     * </p>
     *
     * @param context The subscription context containing API reference artifact
     * @return FederatedSubscriptionOptions with opaque body and schema, or null if no options
     * @throws APIManagementException if discovery fails
     */
    default FederatedSubscriptionOptions getSubscriptionOptions(FederatedSubscriptionContext context) 
            throws APIManagementException {
        return null;  // Default: no subscription options
    }
}
