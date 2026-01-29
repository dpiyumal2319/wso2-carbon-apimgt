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

package org.wso2.carbon.apimgt.impl.federated.gateway;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.apimgt.api.APIManagementException;
import org.wso2.carbon.apimgt.api.FederatedSubscriptionAgent;
import org.wso2.carbon.apimgt.api.model.Environment;
import org.wso2.carbon.apimgt.api.model.GatewayAgentConfiguration;
import org.wso2.carbon.apimgt.impl.APIAdminImpl;
import org.wso2.carbon.apimgt.impl.internal.ServiceReferenceHolder;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Factory class to instantiate FederatedSubscriptionAgent instances based on environment configuration.
 * Implements caching to reuse subscription agent instances for better performance.
 * <p>
 * This factory follows the same pattern as FederatedApplicationDiscoveryFactory:
 * - Cached agents per organization:environment combination
 * - Double-checked locking for thread safety
 * - Decryption of sensitive environment credentials
 * </p>
 */
public class FederatedSubscriptionAgentFactory {

    private static final Log log = LogFactory.getLog(FederatedSubscriptionAgentFactory.class);
    
    // Cache for subscription agents per organization and environment
    private static final Map<String, FederatedSubscriptionAgent> subscriptionAgentCache = new ConcurrentHashMap<>();

    /**
     * Loads and initializes the appropriate FederatedSubscriptionAgent for the given environment.
     * Reuses cached agents for better performance.
     *
     * @param environment  The environment for which subscription agent is requested.
     * @param organization The organization of the request.
     * @return An initialized FederatedSubscriptionAgent.
     * @throws APIManagementException If error occurs during instantiation or initialization.
     */
    public static FederatedSubscriptionAgent getSubscriptionAgent(Environment environment, String organization)
            throws APIManagementException {

        String cacheKey = organization + ":" + environment.getUuid();
        
        // Return cached agent if available
        FederatedSubscriptionAgent cachedAgent = subscriptionAgentCache.get(cacheKey);
        if (cachedAgent != null) {
            if (log.isDebugEnabled()) {
                log.debug("Returning cached subscription agent for environment: " + environment.getName() 
                        + " in organization: " + organization);
            }
            return cachedAgent;
        }

        // Double-checked locking for thread safety
        synchronized (cacheKey.intern()) {
            // Check again after acquiring lock
            cachedAgent = subscriptionAgentCache.get(cacheKey);
            if (cachedAgent != null) {
                return cachedAgent;
            }

            GatewayAgentConfiguration agentConfiguration = ServiceReferenceHolder.getInstance()
                    .getExternalGatewayConnectorConfiguration(environment.getGatewayType());

            if (agentConfiguration == null) {
                String msg = "Gateway Agent Configuration not found for type: " + environment.getGatewayType();
                log.error(msg);
                throw new APIManagementException(msg);
            }

            String implementationClassName = agentConfiguration.getSubscriptionAgentImplementation();
            if (implementationClassName == null || implementationClassName.isEmpty()) {
                String msg = "Subscription Agent Implementation class not found for gateway type: " 
                        + environment.getGatewayType();
                log.error(msg);
                throw new APIManagementException(msg);
            }

            try {
                // Environment fetched from DB might have encrypted properties, hence need to decrypt before
                // initializing the subscription agent
                APIAdminImpl apiAdmin = new APIAdminImpl();
                Environment resolvedEnvironment = apiAdmin.getEnvironmentWithoutPropertyMasking(organization,
                        environment.getUuid());
                resolvedEnvironment = apiAdmin.decryptGatewayConfigurationValues(resolvedEnvironment);

                Class<?> clazz = Class.forName(implementationClassName);
                FederatedSubscriptionAgent agent = (FederatedSubscriptionAgent) clazz.newInstance();
                agent.init(resolvedEnvironment, organization);
                
                // Cache the initialized agent
                subscriptionAgentCache.put(cacheKey, agent);
                
                if (log.isDebugEnabled()) {
                    log.debug("Created and cached new subscription agent for environment: " + environment.getName() 
                            + " in organization: " + organization);
                }
                
                return agent;
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                String msg = "Error while initializing Federated Subscription Agent for type: " 
                        + environment.getGatewayType();
                log.error(msg, e);
                throw new APIManagementException(msg, e);
            }
        }
    }

    /**
     * Clears the cached subscription agent for a specific environment.
     * Should be called when environment configuration changes.
     *
     * @param organization The organization.
     * @param environmentId The environment UUID.
     */
    public static void clearSubscriptionAgentCache(String organization, String environmentId) {
        String cacheKey = organization + ":" + environmentId;
        subscriptionAgentCache.remove(cacheKey);
        if (log.isDebugEnabled()) {
            log.debug("Cleared subscription agent cache for environment: " + environmentId 
                    + " in organization: " + organization);
        }
    }

    /**
     * Clears all cached subscription agents.
     * Useful for testing or when environments are updated in bulk.
     */
    public static void clearAllSubscriptionAgentCache() {
        subscriptionAgentCache.clear();
        if (log.isDebugEnabled()) {
            log.debug("Cleared all subscription agent cache");
        }
    }
}
