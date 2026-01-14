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

import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.cxf.jaxrs.ext.MessageContext;
import org.wso2.carbon.apimgt.api.APIConsumer;
import org.wso2.carbon.apimgt.api.APIManagementException;
import org.wso2.carbon.apimgt.api.model.Application;
import org.wso2.carbon.apimgt.api.model.DiscoveredApplication;
import org.wso2.carbon.apimgt.api.model.Environment;
import org.wso2.carbon.apimgt.api.model.Subscriber;
import org.wso2.carbon.apimgt.impl.APIManagerFactory;
import org.wso2.carbon.apimgt.impl.dao.ApiMgtDAO;
import org.wso2.carbon.apimgt.impl.utils.APIUtil;
import org.wso2.carbon.apimgt.rest.api.common.RestApiCommonUtil;
import org.wso2.carbon.apimgt.rest.api.store.v1.DiscoveredApplicationsApiService;
import org.wso2.carbon.apimgt.rest.api.store.v1.dto.ApplicationDTO;
import org.wso2.carbon.apimgt.rest.api.store.v1.dto.ApplicationImportRequestDTO;
import org.wso2.carbon.apimgt.rest.api.store.v1.mappings.ApplicationMappingUtil;
import org.wso2.carbon.apimgt.rest.api.util.utils.RestApiUtil;

import java.util.Map;
import java.util.UUID;
import javax.ws.rs.core.Response;

/**
 * Implementation of the DiscoveredApplicationsApiService for importing discovered applications from external gateways.
 */
public class DiscoveredApplicationsApiServiceImpl implements DiscoveredApplicationsApiService {

    private static final Log log = LogFactory.getLog(DiscoveredApplicationsApiServiceImpl.class);
    private static final String RESOURCE_ENVIRONMENT = "Gateway Environment";

    @Override
    public Response importDiscoveredApplication(ApplicationImportRequestDTO body, MessageContext messageContext) 
            throws APIManagementException {
        String username = RestApiCommonUtil.getLoggedInUsername();
        String organization = RestApiCommonUtil.getLoggedInUserTenantDomain();
        
        try {
            // Validate request body
            if (body == null || StringUtils.isEmpty(body.getEnvironmentId()) 
                    || StringUtils.isEmpty(body.getReferenceArtifact())) {
                RestApiUtil.handleBadRequest("Missing required parameters: environmentId or referenceArtifact", log);
                return null;
            }

            // Retrieve and validate environment
            Map<String, Environment> environments = APIUtil.getEnvironments(organization);
            Environment environment = environments.get(body.getEnvironmentId());
            
            if (environment == null) {
                // Try finding by UUID if not found by name
                for (Environment env : environments.values()) {
                    if (body.getEnvironmentId().equals(env.getUuid())) {
                        environment = env;
                        break;
                    }
                }
            }

            if (environment == null) {
                RestApiUtil.handleResourceNotFoundError(RESOURCE_ENVIRONMENT, body.getEnvironmentId(), log);
                return null;
            }

            // Parse the reference artifact
            Gson gson = new Gson();
            DiscoveredApplication discoveredApp = gson.fromJson(body.getReferenceArtifact(), DiscoveredApplication.class);
            
            if (discoveredApp == null || StringUtils.isEmpty(discoveredApp.getName())) {
                RestApiUtil.handleBadRequest("Invalid reference artifact: application name is required", log);
                return null;
            }

            APIConsumer apiConsumer = APIManagerFactory.getInstance().getAPIConsumer(username);
            
            // Create the WSO2 Application
            Subscriber subscriber = new Subscriber(username);
            Application application = new Application(discoveredApp.getName(), subscriber);
            application.setDescription(discoveredApp.getDescription());
            application.setUUID(UUID.randomUUID().toString());
            
            // Set throttling tier if available
            if (StringUtils.isNotEmpty(discoveredApp.getThrottlingTier())) {
                application.setTier(discoveredApp.getThrottlingTier());
            }
            
            // Add attributes if available
            if (discoveredApp.getAttributes() != null && !discoveredApp.getAttributes().isEmpty()) {
                application.setApplicationAttributes(discoveredApp.getAttributes());
            }
            
            // Create the application
            int applicationId = apiConsumer.addApplication(application, username, organization);
            Application createdApp = apiConsumer.getApplicationById(applicationId);
            
            // Create the external mapping record
            ApiMgtDAO.getInstance().addApplicationExternalMapping(
                createdApp.getUUID(),
                body.getEnvironmentId(),
                discoveredApp.getExternalId(),
                body.getReferenceArtifact()
            );

            ApplicationDTO createdAppDTO = ApplicationMappingUtil.fromApplicationtoDTO(createdApp);
            
            return Response.status(Response.Status.CREATED).entity(createdAppDTO).build();

        } catch (APIManagementException e) {
            String errorMessage = "Error while importing discovered application";
            RestApiUtil.handleInternalServerError(errorMessage, e, log);
            return null;
        }
    }
}
