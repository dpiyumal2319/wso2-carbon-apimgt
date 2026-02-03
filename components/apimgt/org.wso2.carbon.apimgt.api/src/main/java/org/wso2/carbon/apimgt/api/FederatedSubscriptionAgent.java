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
import org.wso2.carbon.apimgt.api.model.FederatedSubscriptionRequest;
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
     * @param request The subscription request containing WSO2 and external identifiers
     * @return FederatedCredential containing the full credential value (one-time display) (Unmasked)
     * @throws APIManagementException If subscription creation fails
     */
    FederatedCredential createSubscription(FederatedSubscriptionRequest request) throws APIManagementException;

    /**
     * Deletes a subscription from the external gateway.
     * <p>
     * This operation removes all gateway resources associated with the subscription
     * (e.g., API key, ACL entry). The operation should be idempotent - if the
     * subscription doesn't exist, it should not throw an error.
     * </p>
     *
     * @param externalSubscriptionId The external gateway's subscription identifier
     * @throws APIManagementException If subscription deletion fails
     */
    void deleteSubscription(String externalSubscriptionId) throws APIManagementException;

    /**
     * Retrieves the full credential value from the gateway.
     * Only supported by gateways that allow credential retrieval (Azure).
     * <p>
     * Default implementation throws an exception. Override for gateways that support it.
     * </p>
     *
     * @param externalSubscriptionId The external gateway's subscription identifier
     * @return FederatedCredential containing the full credential value
     * @throws APIManagementException If retrieval fails or is not supported
     */
    default FederatedCredential retrieveCredential(String externalSubscriptionId) throws APIManagementException {
        throw new APIManagementException("This gateway does not support credential retrieval");
    }

    /**
     * Gets the API invocation instructions from the reference artifact.
     * Each connector implementation parses its own reference artifact format.
     *
     * @param referenceArtifact The raw reference artifact JSON from AM_API_EXTERNAL_API_MAPPING
     * @return InvocationInstruction containing API invocation details
     * @throws APIManagementException If fetching instructions fails
     */
    InvocationInstruction getInvocationInstruction(String referenceArtifact) throws APIManagementException;

    /**
     * Builds the subscription reference artifact JSON for storage in AM_SUBSCRIPTION_EXTERNAL_MAPPING.
     * Each connector owns its reference artifact format and encodes credential info,
     * invocation instructions, and any gateway-specific metadata.
     *
     * @param credential  The credential returned from createSubscription/regenerateCredential
     * @param instruction The invocation instruction (may be null)
     * @return JSON string to store as the subscription reference artifact
     */
    String buildSubscriptionReferenceArtifact(FederatedCredential credential,
                                               InvocationInstruction instruction);

    /**
     * Extracts a masked credential from the subscription reference artifact.
     * Each connector parses its own reference artifact format.
     *
     * @param subscriptionReferenceArtifact The reference artifact from AM_SUBSCRIPTION_EXTERNAL_MAPPING
     * @return FederatedCredential with masked values, or null if not available
     */
    FederatedCredential extractCredentialFromReferenceArtifact(String subscriptionReferenceArtifact);

    /**
     * Checks if a subscription exists in the external gateway.
     * <p>
     * Default implementation returns true. Override for gateways that support
     * existence checking.
     * </p>
     *
     * @param externalSubscriptionId The external gateway's subscription identifier
     * @return true if the subscription exists, false otherwise
     * @throws APIManagementException If the validation fails
     */
    default boolean subscriptionExists(String externalSubscriptionId) throws APIManagementException {
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
     * @param apiReferenceArtifact The API reference artifact from AM_API_EXTERNAL_API_MAPPING
     * @return Array of supported auth type strings (e.g., ["opaque-api-key"]), empty if no subscription security
     * @throws APIManagementException if check fails
     */
    default String[] getSupportedAuthTypes(String apiReferenceArtifact) throws APIManagementException {
        // Default: assume subscription required with opaque API key
        return new String[]{"opaque-api-key"};
    }
}
