/*
 * Copyright (c) 2022, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
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
import org.wso2.carbon.apimgt.api.model.APIProduct;
import org.wso2.carbon.apimgt.api.model.Environment;
import org.wso2.carbon.apimgt.api.model.FederatedSubscriptionContext;
import org.wso2.carbon.apimgt.api.model.SubscribedAPI;
import org.wso2.carbon.apimgt.api.model.SubscriptionExternalMapping;
import org.wso2.carbon.apimgt.impl.APIConstants;
import org.wso2.carbon.apimgt.impl.dao.ApiMgtDAO;
import org.wso2.carbon.apimgt.impl.dto.SubscriptionWorkflowDTO;
import org.wso2.carbon.apimgt.impl.dto.WorkflowDTO;
import org.wso2.carbon.apimgt.impl.federated.gateway.FederatedSubscriptionAgentFactory;

import java.util.*;

public class SubscriptionDeletionApprovalWorkflowExecutor extends WorkflowExecutor {

    private static final Log log = LogFactory.getLog(SubscriptionDeletionApprovalWorkflowExecutor.class);
    private static final String API_NAME_PROPERTY = "apiName";
    private static final String API_VERSION_PROPERTY = "apiVersion";
    private static final String API_CONTEXT_PROPERTY = "apiContext";
    private static final String SUBSCRIBER_PROPERTY = "subscriber";
    private static final String APPLICATION_NAME_PROPERTY = "applicationName";
    private static final String SUBSCRIPTION_TIER_PROPERTY = "subscriptionTier";

    @Override
    public String getWorkflowType() {

        return WorkflowConstants.WF_TYPE_AM_SUBSCRIPTION_DELETION;
    }

    @Override
    public WorkflowResponse execute(WorkflowDTO workflowDTO) throws WorkflowException {

        if (log.isDebugEnabled()) {
            log.debug("Executing subscription deletion approval workflow. Workflow reference: " + workflowDTO.getWorkflowReference());
        }
        SubscriptionWorkflowDTO subsWorkflowDTO = (SubscriptionWorkflowDTO) workflowDTO;
        String message = String.format(
                "Approve API %s - %s subscription delete request from subscriber - %s for the application - %s",
                subsWorkflowDTO.getApiName(),
                subsWorkflowDTO.getApiVersion(),
                subsWorkflowDTO.getSubscriber(),
                subsWorkflowDTO.getApplicationName()
        );
        workflowDTO.setWorkflowDescription(message);
        workflowDTO.setProperties(API_NAME_PROPERTY, subsWorkflowDTO.getApiName());
        workflowDTO.setProperties(API_VERSION_PROPERTY, subsWorkflowDTO.getApiVersion());
        workflowDTO.setProperties(API_CONTEXT_PROPERTY, subsWorkflowDTO.getApiContext());
        workflowDTO.setProperties(APPLICATION_NAME_PROPERTY, subsWorkflowDTO.getApplicationName());
        workflowDTO.setProperties(SUBSCRIPTION_TIER_PROPERTY, subsWorkflowDTO.getTierName());
        workflowDTO.setProperties(SUBSCRIBER_PROPERTY, subsWorkflowDTO.getSubscriber());
        super.execute(workflowDTO);
        if (log.isDebugEnabled()) {
            log.debug("Subscription deletion approval workflow executed successfully. Workflow reference: "
                    + workflowDTO.getWorkflowReference());
        }

        return new GeneralWorkflowResponse();
    }

    @Override
    public WorkflowResponse complete(WorkflowDTO workflowDTO) throws WorkflowException {

        ApiMgtDAO apiMgtDAO = ApiMgtDAO.getInstance();
        SubscriptionWorkflowDTO subWorkflowDTO = (SubscriptionWorkflowDTO) workflowDTO;
        String errorMsg = null;

        super.complete(subWorkflowDTO);

        if (WorkflowStatus.APPROVED.equals(subWorkflowDTO.getStatus())) {
            int subscriptionId = Integer.parseInt(subWorkflowDTO.getWorkflowReference());
            try {
                // Clean up federated subscription resources before deleting the local WSO2 subscription row
                SubscribedAPI subscribedAPI = apiMgtDAO.getSubscriptionById(subscriptionId);
                if (subscribedAPI != null && subscribedAPI.getUUID() != null) {
                    cleanupFederatedSubscription(apiMgtDAO, subscribedAPI.getUUID(),
                            subWorkflowDTO.getTenantDomain());
                }
                apiMgtDAO.removeSubscriptionById(subscriptionId);
            } catch (APIManagementException e) {
                errorMsg = "Could not complete subscription deletion workflow for api: " + subWorkflowDTO.getApiName();
                throw new WorkflowException(errorMsg, e);
            }
        } else if (WorkflowStatus.REJECTED.equals(workflowDTO.getStatus())) {
            try {
                apiMgtDAO.updateSubscriptionStatus(Integer.parseInt(subWorkflowDTO.getWorkflowReference()),
                        APIConstants.SubscriptionStatus.UNBLOCKED);
            } catch (APIManagementException e) {
                if (e.getMessage() == null) {
                    errorMsg = "Couldn't complete simple application deletion workflow for application: ";
                } else {
                    errorMsg = e.getMessage();
                }
                throw new WorkflowException(errorMsg, e);
            }
        }
        return new GeneralWorkflowResponse();
    }

    @Override
    public List<WorkflowDTO> getWorkflowDetails(String workflowStatus) throws WorkflowException {

        return Collections.emptyList();
    }

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
    public void cleanUpPendingTask(String workflowExtRef) throws WorkflowException {

        String errorMsg;
        super.cleanUpPendingTask(workflowExtRef);
        if (log.isDebugEnabled()) {
            log.debug("Starting cleanup task for SubscriptionDeletionApprovalWorkflowExecutor for :" + workflowExtRef);
        }
        try {
            ApiMgtDAO apiMgtDAO = ApiMgtDAO.getInstance();
            apiMgtDAO.deleteWorkflowRequest(workflowExtRef);
        } catch (APIManagementException ex) {
            errorMsg = "Error sending out cancel pending subscription deletion approval process message. cause: " + ex
                    .getMessage();
            throw new WorkflowException(errorMsg, ex);
        }
    }

    /**
     * Best-effort cleanup of federated subscription resources (gateway credential + local mapping).
     * If the subscription has no external mappings this method returns immediately without any gateway calls.
     * Agent call failures are logged but do not propagate — local mapping deletion always proceeds.
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
                    Environment environment = dao.getEnvironment(organization, envId);
                    if (environment == null) {
                        log.warn("Gateway environment not found: " + envId
                                + ". Skipping external deletion for subscription: " + subscriptionUuid);
                        continue;
                    }
                    FederatedSubscriptionAgent agent =
                            FederatedSubscriptionAgentFactory.getSubscriptionAgent(environment, organization);
                    FederatedSubscriptionContext context = FederatedSubscriptionContext.builder()
                            .subscriptionUuid(subscriptionUuid)
                            .externalSubscriptionId(mapping.getExternalSubscriptionId())
                            .subscriptionReferenceArtifact(mapping.getReferenceArtifact())
                            .environmentId(envId)
                            .organizationId(organization)
                            .build();
                    agent.deleteSubscription(context);
                } catch (Exception e) {
                    log.error("Failed to delete federated subscription from external gateway for subscription: "
                            + subscriptionUuid + ", env: " + envId + ". Proceeding to delete local mapping.", e);
                }
            }
            dao.deleteAllSubscriptionExternalMappings(subscriptionUuid);
        } catch (Exception e) {
            log.error("Failed to clean up federated subscription mappings for subscription: "
                    + subscriptionUuid, e);
        }
    }
}
