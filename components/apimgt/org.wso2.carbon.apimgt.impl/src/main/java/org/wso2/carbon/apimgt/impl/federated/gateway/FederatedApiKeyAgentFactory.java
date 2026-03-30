/*
 * Copyright (c) 2026 WSO2 LLC. (http://www.wso2.org) All Rights Reserved.
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

package org.wso2.carbon.apimgt.impl.federated.gateway;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.apimgt.api.APIManagementException;
import org.wso2.carbon.apimgt.api.FederatedApiKeyAgent;
import org.wso2.carbon.apimgt.api.model.Environment;
import org.wso2.carbon.apimgt.api.model.GatewayAgentConfiguration;
import org.wso2.carbon.apimgt.impl.APIAdminImpl;
import org.wso2.carbon.apimgt.impl.internal.ServiceReferenceHolder;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Factory class to instantiate FederatedApiKeyAgent instances based on environment configuration.
 */
public class FederatedApiKeyAgentFactory {

    private static final Log log = LogFactory.getLog(FederatedApiKeyAgentFactory.class);
    private static final Map<String, FederatedApiKeyAgent> apiKeyAgentCache = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<String, Object> lockMap = new ConcurrentHashMap<>();

    private FederatedApiKeyAgentFactory() {

    }

    /**
     * Loads and initializes the appropriate FederatedApiKeyAgent for the given environment.
     *
     * @param environment  The environment for which api key agent is requested.
     * @param organization The organization of the request.
     * @return An initialized FederatedApiKeyAgent.
     * @throws APIManagementException If error occurs during instantiation or initialization.
     */
    public static FederatedApiKeyAgent getApiKeyAgent(Environment environment, String organization)
            throws APIManagementException {

        String cacheKey = organization + ":" + environment.getUuid();
        FederatedApiKeyAgent cachedAgent = apiKeyAgentCache.get(cacheKey);
        if (cachedAgent != null) {
            return cachedAgent;
        }

        Object lock = lockMap.computeIfAbsent(cacheKey, k -> new Object());
        synchronized (lock) {
            cachedAgent = apiKeyAgentCache.get(cacheKey);
            if (cachedAgent != null) {
                return cachedAgent;
            }

            GatewayAgentConfiguration agentConfiguration = ServiceReferenceHolder.getInstance()
                    .getExternalGatewayConnectorConfiguration(environment.getGatewayType());
            if (agentConfiguration == null) {
                throw new APIManagementException("Gateway Agent Configuration not found for type: "
                        + environment.getGatewayType());
            }

            String implementationClassName = agentConfiguration.getApiKeyAgentImplementation();
            if (implementationClassName == null || implementationClassName.isEmpty()) {
                throw new APIManagementException("API Key Agent Implementation class not found for gateway type: "
                        + environment.getGatewayType());
            }

            try {
                APIAdminImpl apiAdmin = new APIAdminImpl();
                Environment resolvedEnvironment = apiAdmin.getEnvironmentWithoutPropertyMasking(
                        organization, environment.getUuid());
                resolvedEnvironment = apiAdmin.decryptGatewayConfigurationValues(resolvedEnvironment);
                FederatedApiKeyAgent agent = instantiateApiKeyAgent(implementationClassName, resolvedEnvironment,
                        organization);
                apiKeyAgentCache.put(cacheKey, agent);
                return agent;
            } catch (ReflectiveOperationException e) {
                String msg = "Error while initializing Federated API Key Agent for type: "
                        + environment.getGatewayType();
                log.error(msg, e);
                throw new APIManagementException(msg, e);
            }
        }
    }

    /**
     * Creates a non-cached API key agent initialized with the provided environment object.
     * This is used for pre-save onboarding flows where environment configuration is not persisted yet.
     *
     * @param environment  environment configuration payload
     * @param organization organization name
     * @return initialized non-cached agent
     * @throws APIManagementException if initialization fails
     */
    public static FederatedApiKeyAgent getTransientApiKeyAgent(Environment environment, String organization)
            throws APIManagementException {

        GatewayAgentConfiguration agentConfiguration = ServiceReferenceHolder.getInstance()
                .getExternalGatewayConnectorConfiguration(environment.getGatewayType());
        if (agentConfiguration == null) {
            throw new APIManagementException("Gateway Agent Configuration not found for type: "
                    + environment.getGatewayType());
        }
        String implementationClassName = agentConfiguration.getApiKeyAgentImplementation();
        if (implementationClassName == null || implementationClassName.isEmpty()) {
            throw new APIManagementException("API Key Agent Implementation class not found for gateway type: "
                    + environment.getGatewayType());
        }
        try {
            return instantiateApiKeyAgent(implementationClassName, environment, organization);
        } catch (ReflectiveOperationException e) {
            String msg = "Error while initializing transient Federated API Key Agent for type: "
                    + environment.getGatewayType();
            log.error(msg, e);
            throw new APIManagementException(msg, e);
        }
    }

    private static FederatedApiKeyAgent instantiateApiKeyAgent(String implementationClassName,
                                                                Environment environment, String organization)
            throws ReflectiveOperationException, APIManagementException {

        Class<?> clazz = Class.forName(implementationClassName);
        FederatedApiKeyAgent agent = (FederatedApiKeyAgent) clazz.getDeclaredConstructor().newInstance();
        agent.init(environment, organization);
        return agent;
    }
}
