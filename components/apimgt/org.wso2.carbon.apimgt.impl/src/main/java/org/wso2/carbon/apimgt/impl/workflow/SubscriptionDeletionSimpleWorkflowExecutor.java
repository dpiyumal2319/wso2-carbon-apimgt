/*
 * Copyright (c) 2015, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.wso2.carbon.apimgt.impl.workflow;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.apimgt.api.APIManagementException;
import org.wso2.carbon.apimgt.api.FederatedSubscriptionAgent;
import org.wso2.carbon.apimgt.api.WorkflowResponse;
import org.wso2.carbon.apimgt.api.model.API;
import org.wso2.carbon.apimgt.api.model.APIIdentifier;
import org.wso2.carbon.apimgt.api.model.APIProduct;
import org.wso2.carbon.apimgt.api.model.Environment;
import org.wso2.carbon.apimgt.api.model.ExternalCredential;
import org.wso2.carbon.apimgt.api.model.FederatedSubscriptionContext;
import org.wso2.carbon.apimgt.api.model.SubscriptionExternalMapping;
import org.wso2.carbon.apimgt.impl.dao.ApiMgtDAO;
import org.wso2.carbon.apimgt.impl.dto.SubscriptionWorkflowDTO;
import org.wso2.carbon.apimgt.impl.dto.WorkflowDTO;
import org.wso2.carbon.apimgt.impl.federated.gateway.FederatedSubscriptionAgentFactory;

import java.util.List;
import java.util.Map;

/**
 * Simple workflow executor for subscription delete action
 */
public class SubscriptionDeletionSimpleWorkflowExecutor extends WorkflowExecutor {
    private static final Log log = LogFactory.getLog(SubscriptionDeletionSimpleWorkflowExecutor.class);

    @Override
    public String getWorkflowType() {
        return WorkflowConstants.WF_TYPE_AM_SUBSCRIPTION_DELETION;
    }

    @Override
    public List<WorkflowDTO> getWorkflowDetails(String workflowStatus) throws WorkflowException {

        // implemetation is not provided in this version
        return null;
    }

    @Override
    public WorkflowResponse execute(WorkflowDTO workflowDTO) throws WorkflowException {
        workflowDTO.setStatus(WorkflowStatus.APPROVED);
        complete(workflowDTO);
        return new GeneralWorkflowResponse();
    }

    /**
     * This method is responsible for deleting the monetized subscription and returns the execute method.
     *
     * @param workflowDTO The WorkflowDTO which contains workflow contextual information related to the workflow
     * @return workflow response to the caller by returning the execute() method
     * @throws WorkflowException
     */
    @Override
    public WorkflowResponse deleteMonetizedSubscription(WorkflowDTO workflowDTO, API api) throws WorkflowException {
        // implementation is not provided in this version
        return execute(workflowDTO);
    }

    @Override
    public WorkflowResponse deleteMonetizedSubscription(WorkflowDTO workflowDTO, APIProduct apiProduct) throws WorkflowException {
        // implementation is not provided in this version
        return execute(workflowDTO);
    }

    @Override
    public WorkflowResponse complete(WorkflowDTO workflowDTO) throws WorkflowException {
        ApiMgtDAO apiMgtDAO = ApiMgtDAO.getInstance();
        SubscriptionWorkflowDTO subWorkflowDTO = (SubscriptionWorkflowDTO) workflowDTO;
        String errorMsg = null;

        try {
            APIIdentifier identifier = new APIIdentifier(subWorkflowDTO.getApiProvider(),
                    subWorkflowDTO.getApiName(), subWorkflowDTO.getApiVersion());
            identifier.setId(Integer.parseInt(subWorkflowDTO.getMetadata(WorkflowConstants.PayloadConstants.API_ID)));
            int applicationId = subWorkflowDTO.getApplicationId();

            // Clean up federated subscription resources before deleting the local WSO2 subscription row
            String subscriptionUuid = apiMgtDAO.getSubscriptionUuid(identifier.getId(), applicationId);
            if (subscriptionUuid != null) {
                cleanupFederatedSubscription(apiMgtDAO, subscriptionUuid, subWorkflowDTO.getTenantDomain());
            }

            apiMgtDAO.removeSubscription(identifier, applicationId);
        } catch (APIManagementException e) {
            errorMsg = "Could not complete subscription deletion workflow for api: " + subWorkflowDTO.getApiName();
            throw new WorkflowException(errorMsg, e);
        }
        return new GeneralWorkflowResponse();
    }

    /**
     * Best-effort cleanup of federated subscription resources (gateway credentials + local mappings).
     * For each mapping, iterates all provisioned credentials and calls the gateway agent to delete each one.
     * Agent call failures are logged but do not propagate — local mapping deletion (with cascade) always proceeds.
     */
    private void cleanupFederatedSubscription(ApiMgtDAO dao, String subscriptionUuid, String organization) {
        try {
            Map<String, SubscriptionExternalMapping> mappings = dao.getSubscriptionExternalMappings(subscriptionUuid);
            if (mappings.isEmpty()) {
                return;
            }
            for (Map.Entry<String, SubscriptionExternalMapping> entry : mappings.entrySet()) {
                String envId = entry.getKey();
                SubscriptionExternalMapping mapping = entry.getValue();
                try {
                    List<ExternalCredential> credentials = dao.getExternalCredentials(mapping.getUuid());
                    if (credentials.isEmpty()) {
                        continue;
                    }
                    Environment environment = dao.getEnvironment(organization, envId);
                    if (environment == null) {
                        log.warn("Gateway environment not found: " + envId
                                + ". Skipping external deletion for subscription: " + subscriptionUuid);
                        continue;
                    }
                    FederatedSubscriptionAgent agent =
                            FederatedSubscriptionAgentFactory.getSubscriptionAgent(environment, organization);
                    for (ExternalCredential credential : credentials) {
                        try {
                            FederatedSubscriptionContext context = FederatedSubscriptionContext.builder()
                                    .subscriptionUuid(subscriptionUuid)
                                    .externalSubscriptionId(credential.getExternalSubscriptionId())
                                    .subscriptionReferenceArtifact(credential.getReferenceArtifact())
                                    .environmentId(envId)
                                    .organizationId(organization)
                                    .build();
                            agent.deleteSubscription(context);
                        } catch (Exception e) {
                            log.error("Failed to delete credential " + credential.getUuid()
                                    + " from external gateway for subscription: " + subscriptionUuid
                                    + ", env: " + envId + ". Proceeding.", e);
                        }
                    }
                } catch (Exception e) {
                    log.error("Failed to cleanup credentials for mapping " + mapping.getUuid()
                            + ", env: " + envId + " for subscription: " + subscriptionUuid, e);
                }
            }
            // Cascade deletes all AM_EXTERNAL_CREDENTIAL rows for this subscription
            dao.deleteAllSubscriptionExternalMappings(subscriptionUuid);
        } catch (Exception e) {
            log.error("Failed to clean up federated subscription mappings for subscription: "
                    + subscriptionUuid, e);
        }
    }
}
