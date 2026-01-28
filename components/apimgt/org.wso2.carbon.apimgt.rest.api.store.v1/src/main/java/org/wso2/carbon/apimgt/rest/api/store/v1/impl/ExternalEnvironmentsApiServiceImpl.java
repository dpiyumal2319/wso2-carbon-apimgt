package org.wso2.carbon.apimgt.rest.api.store.v1.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.cxf.jaxrs.ext.MessageContext;
import org.wso2.carbon.apimgt.api.APIManagementException;
import org.wso2.carbon.apimgt.api.model.Environment;
import org.wso2.carbon.apimgt.impl.APIConstants;
import org.wso2.carbon.apimgt.impl.utils.APIUtil;
import org.wso2.carbon.apimgt.rest.api.common.RestApiCommonUtil;
import org.wso2.carbon.apimgt.rest.api.store.v1.ExternalEnvironmentsApiService;
import org.wso2.carbon.apimgt.rest.api.store.v1.dto.ExternalGatewayEnvironmentListDTO;
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
            List<Environment> externalEnvList = new ArrayList<>();
            
            // Filter to only include environments with provider="external"
            for (Environment env : environments.values()) {
                if (APIConstants.EXTERNAL_GATEWAY_VENDOR.equalsIgnoreCase(env.getProvider())) {
                    externalEnvList.add(env);
                }
            }
            
            ExternalGatewayEnvironmentListDTO dto = 
                ExternalGatewayEnvironmentMappingUtil.fromEnvListToEnvListDTO(externalEnvList);
            
            return Response.ok().entity(dto).build();
        } catch (APIManagementException e) {
            RestApiUtil.handleInternalServerError("Error while retrieving external gateway environments", e, log);
            return null;
        }
    }
}
