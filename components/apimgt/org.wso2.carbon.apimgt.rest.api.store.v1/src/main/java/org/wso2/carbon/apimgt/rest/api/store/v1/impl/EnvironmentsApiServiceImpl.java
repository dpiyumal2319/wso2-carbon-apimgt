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

package org.wso2.carbon.apimgt.rest.api.store.v1.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.cxf.jaxrs.ext.MessageContext;
import org.wso2.carbon.apimgt.api.APIManagementException;
import org.wso2.carbon.apimgt.api.FederatedApplicationDiscovery;
import org.wso2.carbon.apimgt.api.model.DiscoveredApplication;
import org.wso2.carbon.apimgt.api.model.DiscoveredApplicationResult;
import org.wso2.carbon.apimgt.api.model.Environment;
import org.wso2.carbon.apimgt.impl.federated.gateway.FederatedApplicationDiscoveryFactory;
import org.wso2.carbon.apimgt.impl.utils.APIUtil;
import org.wso2.carbon.apimgt.impl.utils.DiscoveredApplicationEnricher;
import org.wso2.carbon.apimgt.rest.api.common.RestApiCommonUtil;
import org.wso2.carbon.apimgt.rest.api.store.v1.EnvironmentsApiService;
import org.wso2.carbon.apimgt.rest.api.store.v1.dto.DiscoveredApplicationDTO;
import org.wso2.carbon.apimgt.rest.api.store.v1.dto.DiscoveredApplicationListDTO;
import org.wso2.carbon.apimgt.rest.api.store.v1.mappings.DiscoveredApplicationMappingUtil;
import org.wso2.carbon.apimgt.rest.api.util.utils.RestApiUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.ws.rs.core.Response;

/**
 * Implementation of the EnvironmentsApiService for handling discovered applications from external gateways.
 */
public class EnvironmentsApiServiceImpl implements EnvironmentsApiService {

    private static final Log log = LogFactory.getLog(EnvironmentsApiServiceImpl.class);
    private static final String RESOURCE_ENVIRONMENT = "Gateway Environment";
    private static final String RESOURCE_APPLICATION = "Discovered Application";

    @Override
    public Response getDiscoveredApplication(String environmentId, String applicationId,
                                              MessageContext messageContext) throws APIManagementException {
        String organization = RestApiCommonUtil.getLoggedInUserTenantDomain();
        
        try {
            Environment environment = findEnvironment(environmentId, organization);
            
            if (environment == null) {
                RestApiUtil.handleResourceNotFoundError(RESOURCE_ENVIRONMENT, environmentId, log);
                return null;
            }

            FederatedApplicationDiscovery agent = FederatedApplicationDiscoveryFactory.getDiscoveryAgent(environment, organization);
            
            if (agent == null) {
                RestApiUtil.handleBadRequest("Application discovery is not supported for this gateway type: " 
                        + environment.getGatewayType(), log);
                return null;
            }
            
            // Get application with keys masked (Stages 1-3 from builder)
            DiscoveredApplication application = agent.getApplicationWithKeysMasked(applicationId);
            
            if (application == null) {
                RestApiUtil.handleResourceNotFoundError(RESOURCE_APPLICATION, applicationId, log);
                return null;
            }

            // This matches external API IDs with imported APIs and populates WSO2-specific fields
            if (application.getSubscribedApis() != null && !application.getSubscribedApis().isEmpty()) {
                DiscoveredApplicationEnricher.enrichSubscribedApis(
                        application.getSubscribedApis(), environment.getUuid());

                // Filter to only show imported APIs (those that exist in WSO2)
                application.setSubscribedApis(
                        DiscoveredApplicationEnricher.filterImportedApis(application.getSubscribedApis()));
            }
            
            DiscoveredApplicationDTO dto = DiscoveredApplicationMappingUtil.fromDiscoveredApplicationToDTO(application);
            
            return Response.ok().entity(dto).build();
        } catch (APIManagementException e) {
            if (e.getMessage().contains("not found")) {
                RestApiUtil.handleResourceNotFoundError(RESOURCE_APPLICATION, applicationId, log);
            } else {
                RestApiUtil.handleInternalServerError("Error while retrieving discovered application: " 
                        + applicationId + " from environment: " + environmentId, e, log);
            }
            return null;
        }
    }

    @Override
    public Response getDiscoveredApplications(String environmentId, Integer limit, Integer offset, String query,
                                              MessageContext messageContext) throws APIManagementException {
        String organization = RestApiCommonUtil.getLoggedInUserTenantDomain();
        
        try {
            Environment environment = findEnvironment(environmentId, organization);
            
            if (environment == null) {
                RestApiUtil.handleResourceNotFoundError(RESOURCE_ENVIRONMENT, environmentId, log);
                return null;
            }

            FederatedApplicationDiscovery agent = FederatedApplicationDiscoveryFactory.getDiscoveryAgent(environment, organization);
            
            if (agent == null) {
                RestApiUtil.handleBadRequest("Application discovery is not supported for this gateway type: " 
                        + environment.getGatewayType(), log);
                return null;
            }
            
            int validLimit = (limit == null || limit <= 0) ? 10 : limit;
            int validOffset = (offset == null || offset < 0) ? 0 : offset;
            
            DiscoveredApplicationResult result = agent.discoverApplications(validOffset, validLimit, query);
            DiscoveredApplicationListDTO dto = DiscoveredApplicationMappingUtil.fromDiscoveredApplicationListToDTO(result);
            
            return Response.ok().entity(dto).build();
        } catch (APIManagementException e) {
            RestApiUtil.handleInternalServerError("Error while discovering applications from environment: " 
                    + environmentId, e, log);
            return null;
        }
    }

    /**
     * Helper method to find environment by ID or UUID
     */
    private Environment findEnvironment(String environmentId, String organization) throws APIManagementException {
        Map<String, Environment> environments = APIUtil.getEnvironments(organization);
        Environment environment = environments.get(environmentId);
        
        if (environment == null) {
            // Try finding by UUID if not found by name
            for (Environment env : environments.values()) {
                if (environmentId.equals(env.getUuid())) {
                    environment = env;
                    break;
                }
            }
        }
        
        return environment;
    }
}
