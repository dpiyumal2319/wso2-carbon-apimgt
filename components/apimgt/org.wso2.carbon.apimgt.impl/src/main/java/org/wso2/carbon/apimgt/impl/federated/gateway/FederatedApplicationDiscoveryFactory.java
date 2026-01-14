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
import org.wso2.carbon.apimgt.api.FederatedApplicationDiscovery;
import org.wso2.carbon.apimgt.api.model.Environment;
import org.wso2.carbon.apimgt.api.model.GatewayAgentConfiguration;
import org.wso2.carbon.apimgt.impl.internal.ServiceReferenceHolder;

/**
 * Factory class to instantiate FederatedApplicationDiscovery agents based on the environment configuration.
 */
public class FederatedApplicationDiscoveryFactory {

    private static final Log log = LogFactory.getLog(FederatedApplicationDiscoveryFactory.class);

    /**
     * Loads and initializes the appropriate FederatedApplicationDiscovery agent for the given environment.
     *
     * @param environment  The environment for which discovery is requested.
     * @param organization The organization of the request.
     * @return An initialized FederatedApplicationDiscovery agent.
     * @throws APIManagementException If error occurs during instantiation or initialization.
     */
    public static FederatedApplicationDiscovery getDiscoveryAgent(Environment environment, String organization)
            throws APIManagementException {

        GatewayAgentConfiguration agentConfiguration = ServiceReferenceHolder.getInstance()
                .getExternalGatewayConnectorConfiguration(environment.getGatewayType());

        if (agentConfiguration == null) {
            String msg = "Gateway Agent Configuration not found for type: " + environment.getGatewayType();
            log.error(msg);
            throw new APIManagementException(msg);
        }

        String implementationClassName = agentConfiguration.getApplicationDiscoveryImplementation();
        if (implementationClassName == null || implementationClassName.isEmpty()) {
            String msg = "Application Discovery Implementation class not found for gateway type: " + environment.getGatewayType();
            log.error(msg);
            throw new APIManagementException(msg);
        }

        try {
            Class<?> clazz = Class.forName(implementationClassName);
            FederatedApplicationDiscovery agent = (FederatedApplicationDiscovery) clazz.newInstance();
            agent.init(environment, organization);
            return agent;
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            String msg = "Error while initializing Federated Application Discovery Agent for type: " + environment.getGatewayType();
            log.error(msg, e);
            throw new APIManagementException(msg, e);
        }
    }
}
