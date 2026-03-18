/*
 * Copyright (c) 2026, WSO2 LLC. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 LLC. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.wso2.carbon.apimgt.api;

import org.wso2.carbon.apimgt.api.model.Environment;
import org.wso2.carbon.apimgt.api.model.FederatedApiKeyContext;
import org.wso2.carbon.apimgt.api.model.RemotePlan;

import java.util.Collections;
import java.util.List;

/**
 * Interface for managing API-bound API keys in external gateways.
 */
public interface FederatedApiKeyAgent {

    /**
     * Initializes the API key agent.
     *
     * @param environment gateway environment configuration
     * @param organization organization identifier
     * @throws APIManagementException if initialization fails
     */
    void init(Environment environment, String organization) throws APIManagementException;

    /**
     * Creates/pushes an API key in the external gateway.
     *
     * @param context API key operation context
     * @return remote API key identifier
     * @throws APIManagementException if operation fails
     */
    String createApiKey(FederatedApiKeyContext context) throws APIManagementException;

    /**
     * Revokes/deletes an API key in the external gateway.
     *
     * @param context API key operation context
     * @throws APIManagementException if operation fails
     */
    void revokeApiKey(FederatedApiKeyContext context) throws APIManagementException;

    /**
     * Associates an API key with a remote usage plan.
     *
     * @param context API key operation context
     * @param remoteUsagePlanId remote usage plan identifier
     * @throws APIManagementException if operation fails
     */
    void associateApiKeyWithUsagePlan(FederatedApiKeyContext context, String remoteUsagePlanId)
            throws APIManagementException;

    /**
     * Removes API key associations from remote usage plans.
     *
     * @param context API key operation context
     * @throws APIManagementException if operation fails
     */
    void removeApiKeyAssociations(FederatedApiKeyContext context) throws APIManagementException;

    /**
     * Gets gateway type identifier.
     *
     * @return gateway type
     */
    String getGatewayType();

    /**
     * Returns whether this gateway supports federated API key management.
     *
     * @return true if supported
     */
    default boolean isApiKeySupport() {
        return false;
    }

    /**
     * Lists available remote plans from gateway for environment onboarding and local tier mapping.
     *
     * @param environment gateway environment configuration
     * @return list of remote plans
     * @throws APIManagementException if remote plan retrieval fails
     */
    default List<RemotePlan> listRemotePlans(Environment environment) throws APIManagementException {
        return Collections.emptyList();
    }
}
