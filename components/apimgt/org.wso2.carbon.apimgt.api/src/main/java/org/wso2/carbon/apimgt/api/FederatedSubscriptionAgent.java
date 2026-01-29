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
 * - Regenerating credentials
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
     * @return FederatedCredential containing the full credential value (one-time display)
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
     * Regenerates the credential for an existing subscription.
     * <p>
     * This invalidates the old credential and creates a new one.
     * The new credential is returned in full for one-time display.
     * </p>
     *
     * @param externalSubscriptionId The external gateway's subscription identifier
     * @return FederatedCredential containing the new full credential value
     * @throws APIManagementException If credential regeneration fails
     */
    FederatedCredential regenerateCredential(String externalSubscriptionId) throws APIManagementException;

    /**
     * Gets the API invocation instructions from the reference artifact.
     * <p>
     * Returns information about how to invoke the API including:
     * - The header name for the credential
     * - Base URL and path
     * - Example curl command
     * </p>
     * <p>
     * Each connector implementation parses its own reference artifact format
     * to extract the necessary API identifiers.
     * </p>
     *
     * @param referenceArtifact The raw reference artifact JSON from AM_API_EXTERNAL_API_MAPPING
     * @return InvocationInstruction containing API invocation details
     * @throws APIManagementException If fetching instructions fails
     */
    InvocationInstruction getInvocationInstruction(String referenceArtifact) throws APIManagementException;

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
}
