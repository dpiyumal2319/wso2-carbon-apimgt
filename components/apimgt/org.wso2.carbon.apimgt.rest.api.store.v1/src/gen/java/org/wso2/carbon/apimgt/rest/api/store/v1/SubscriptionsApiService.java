package org.wso2.carbon.apimgt.rest.api.store.v1;

import org.wso2.carbon.apimgt.rest.api.store.v1.*;
import org.wso2.carbon.apimgt.rest.api.store.v1.dto.*;

import org.apache.cxf.jaxrs.ext.MessageContext;
import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.apache.cxf.jaxrs.ext.multipart.Multipart;

import org.wso2.carbon.apimgt.api.APIManagementException;

import org.wso2.carbon.apimgt.rest.api.store.v1.dto.APIMonetizationUsageDTO;
import org.wso2.carbon.apimgt.rest.api.store.v1.dto.AdditionalSubscriptionInfoListDTO;
import org.wso2.carbon.apimgt.rest.api.store.v1.dto.ErrorDTO;
import org.wso2.carbon.apimgt.rest.api.store.v1.dto.FederatedCredentialListDTO;
import org.wso2.carbon.apimgt.rest.api.store.v1.dto.FederatedCredentialRequestDTO;
import org.wso2.carbon.apimgt.rest.api.store.v1.dto.FederatedSubscriptionInfoDTO;
import java.util.List;
import org.wso2.carbon.apimgt.rest.api.store.v1.dto.SubscriptionDTO;
import org.wso2.carbon.apimgt.rest.api.store.v1.dto.SubscriptionListDTO;
import org.wso2.carbon.apimgt.rest.api.store.v1.dto.WorkflowResponseDTO;

import java.util.List;

import java.io.InputStream;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;


public interface SubscriptionsApiService {
      public Response createFederatedCredential(String subscriptionId, FederatedCredentialRequestDTO federatedCredentialRequestDTO, MessageContext messageContext) throws APIManagementException;
      public Response deleteFederatedCredential(String subscriptionId, String credentialId, MessageContext messageContext) throws APIManagementException;
      public Response deleteFederatedSubscription(String subscriptionId, MessageContext messageContext) throws APIManagementException;
      public Response getAdditionalInfoOfAPISubscriptions(String apiId, String groupId, String xWSO2Tenant, Integer offset, Integer limit, String ifNoneMatch, MessageContext messageContext) throws APIManagementException;
      public Response getFederatedCredential(String subscriptionId, String credentialId, MessageContext messageContext) throws APIManagementException;
      public Response listFederatedCredentials(String subscriptionId, MessageContext messageContext) throws APIManagementException;
      public Response regenerateFederatedCredential(String subscriptionId, String credentialId, MessageContext messageContext) throws APIManagementException;
      public Response retrieveFederatedCredential(String subscriptionId, String credentialId, MessageContext messageContext) throws APIManagementException;
      public Response subscriptionsGet(String apiId, String applicationId, String groupId, String xWSO2Tenant, Integer offset, Integer limit, String ifNoneMatch, MessageContext messageContext) throws APIManagementException;
      public Response subscriptionsMultiplePost(List<SubscriptionDTO> subscriptionDTO, String xWSO2Tenant, MessageContext messageContext) throws APIManagementException;
      public Response subscriptionsPost(SubscriptionDTO subscriptionDTO, String xWSO2Tenant, MessageContext messageContext) throws APIManagementException;
      public Response subscriptionsSubscriptionIdDelete(String subscriptionId, String ifMatch, MessageContext messageContext) throws APIManagementException;
      public Response subscriptionsSubscriptionIdGet(String subscriptionId, String ifNoneMatch, MessageContext messageContext) throws APIManagementException;
      public Response subscriptionsSubscriptionIdPut(String subscriptionId, SubscriptionDTO subscriptionDTO, String xWSO2Tenant, MessageContext messageContext) throws APIManagementException;
      public Response subscriptionsSubscriptionIdUsageGet(String subscriptionId, MessageContext messageContext) throws APIManagementException;
}
