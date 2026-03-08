package org.wso2.carbon.apimgt.rest.api.store.v1.impl;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.cxf.jaxrs.ext.MessageContext;
import org.wso2.carbon.apimgt.api.APIManagementException;
import org.wso2.carbon.apimgt.api.model.Environment;
import org.wso2.carbon.apimgt.api.model.GatewayFeatureCatalog;
import org.wso2.carbon.apimgt.impl.APIConstants;
import org.wso2.carbon.apimgt.impl.utils.APIUtil;
import org.wso2.carbon.apimgt.rest.api.common.RestApiCommonUtil;
import org.wso2.carbon.apimgt.rest.api.store.v1.ExternalEnvironmentsApiService;
import org.wso2.carbon.apimgt.rest.api.store.v1.dto.ExternalGatewayEnvironmentListDTO;
import org.wso2.carbon.apimgt.rest.api.store.v1.dto.FederatedSubscriptionSupportDTO;
import org.wso2.carbon.apimgt.rest.api.store.v1.dto.FederatedSubscriptionSupportListDTO;
import org.wso2.carbon.apimgt.rest.api.store.v1.mappings.ExternalGatewayEnvironmentMappingUtil;
import org.wso2.carbon.apimgt.rest.api.util.utils.RestApiUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.ws.rs.core.Response;


/**
 * Implementation of the ExternalEnvironmentsApiService for handling external gateway environments.
 */
public class ExternalEnvironmentsApiServiceImpl implements ExternalEnvironmentsApiService {

    private static final Log log = LogFactory.getLog(ExternalEnvironmentsApiServiceImpl.class);

    @Override
    public Response externalEnvironmentsGet(MessageContext messageContext) {
        String organization = RestApiCommonUtil.getLoggedInUserTenantDomain();
        
        try {
            Map<String, Environment> environments = APIUtil.getEnvironments(organization);
            List<Environment> externalEnvList = filterExternalEnvironments(environments);
            
            ExternalGatewayEnvironmentListDTO dto = 
                ExternalGatewayEnvironmentMappingUtil.fromEnvListToEnvListDTO(externalEnvList);
            
            return Response.ok().entity(dto).build();
        } catch (APIManagementException e) {
            RestApiUtil.handleInternalServerError("Error while retrieving external gateway environments", e, log);
            return null;
        }
    }

    @Override
    public Response getFederatedSubscriptionSupport(MessageContext messageContext) {
        String organization = RestApiCommonUtil.getLoggedInUserTenantDomain();
        
        try {
            Map<String, Environment> environments = APIUtil.getEnvironments(organization);
            GatewayFeatureCatalog catalog = APIUtil.getGatewayFeatureCatalog();
            
            List<FederatedSubscriptionSupportDTO> supportList = new ArrayList<>();
            
            for (Environment env : filterExternalEnvironments(environments)) {
                FederatedSubscriptionSupportDTO support = buildFederatedSubscriptionSupport(env, catalog);
                if (support != null) {
                    supportList.add(support);
                }
            }
            
            FederatedSubscriptionSupportListDTO listDTO = new FederatedSubscriptionSupportListDTO();
            listDTO.setCount(supportList.size());
            listDTO.setList(supportList);
            
            return Response.ok().entity(listDTO).build();
        } catch (APIManagementException e) {
            RestApiUtil.handleInternalServerError(
                    "Error while retrieving federated subscription support information", e, log);
            return null;
        }
    }

    private List<Environment> filterExternalEnvironments(Map<String, Environment> environments) {
        List<Environment> externalEnvList = new ArrayList<>();
        for (Environment env : environments.values()) {
            if (APIConstants.EXTERNAL_GATEWAY_VENDOR.equalsIgnoreCase(env.getProvider())) {
                externalEnvList.add(env);
            }
        }
        return externalEnvList;
    }

    private FederatedSubscriptionSupportDTO buildFederatedSubscriptionSupport(
            Environment env, GatewayFeatureCatalog catalog) {
        
        FederatedSubscriptionSupportDTO dto = new FederatedSubscriptionSupportDTO();
        dto.setEnvironmentId(env.getUuid());
        dto.setEnvironmentName(env.getName());
        dto.setGatewayType(env.getGatewayType());
        dto.setSupported(false);
        
        Map<String, Object> gatewayFeatures = catalog.getGatewayFeatures();
        if (gatewayFeatures == null || env.getGatewayType() == null) {
            return dto;
        }
        Map<String, Object> featureMap = getNestedMap(gatewayFeatures, env.getGatewayType());
        if (featureMap != null) {
            Map<String, Object> fedSubFeatures = getNestedMap(featureMap, "federatedSubscription");
            if (fedSubFeatures != null) {
                extractFederatedSubscriptionInfo(dto, fedSubFeatures);
            }
        }
        
        return dto;
    }

    private void extractFederatedSubscriptionInfo(FederatedSubscriptionSupportDTO dto,
            Map<String, Object> capability) {
        List<String> credentialSchemas = new ArrayList<>();
        List<String> invocationSchemas = new ArrayList<>();
        
        try {
            boolean supportsFederatedSubscriptions = Boolean.TRUE.equals(capability.get("subcriptionSupport"));
            if (supportsFederatedSubscriptions) {
                Object credSchema = capability.get("credentialSchema");
                if (credSchema instanceof String) {
                    credentialSchemas.add((String) credSchema);
                }
                Object invSchema = capability.get("invocationSchema");
                if (invSchema instanceof String) {
                    invocationSchemas.add((String) invSchema);
                }
            }
            
            if (!credentialSchemas.isEmpty() || !invocationSchemas.isEmpty()) {
                dto.setSupported(true);
                dto.setCredentialSchemas(credentialSchemas);
                dto.setInvocationSchemas(invocationSchemas);
            }
        } catch (Exception e) {
            log.warn("Error parsing federated subscription features for gateway: " + dto.getGatewayType(), e);
        }
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> getNestedMap(Map<String, Object> parent, String key) {
        Object value = parent.get(key);
        return (value instanceof Map) ? (Map<String, Object>) value : null;
    }
}
