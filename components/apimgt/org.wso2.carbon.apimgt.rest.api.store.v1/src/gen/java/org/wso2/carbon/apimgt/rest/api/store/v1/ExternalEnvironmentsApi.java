package org.wso2.carbon.apimgt.rest.api.store.v1;

import org.wso2.carbon.apimgt.rest.api.store.v1.dto.ErrorDTO;
import org.wso2.carbon.apimgt.rest.api.store.v1.dto.ExternalGatewayEnvironmentListDTO;
import org.wso2.carbon.apimgt.rest.api.store.v1.ExternalEnvironmentsApiService;
import org.wso2.carbon.apimgt.rest.api.store.v1.impl.ExternalEnvironmentsApiServiceImpl;
import org.wso2.carbon.apimgt.api.APIManagementException;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.inject.Inject;

import io.swagger.annotations.*;
import java.io.InputStream;

import org.apache.cxf.jaxrs.ext.MessageContext;
import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.apache.cxf.jaxrs.ext.multipart.Multipart;

import java.util.Map;
import java.util.List;
import javax.validation.constraints.*;
@Path("/external-environments")

@Api(description = "the external-environments API")




public class ExternalEnvironmentsApi  {

  @Context MessageContext securityContext;

ExternalEnvironmentsApiService delegate = new ExternalEnvironmentsApiServiceImpl();


    @GET
    
    
    @Produces({ "application/json" })
    @ApiOperation(value = "Get all external gateway environments", notes = "This operation can be used to retrieve the list of external gateway environments available for application federation. ", response = ExternalGatewayEnvironmentListDTO.class, authorizations = {
        @Authorization(value = "OAuth2Security", scopes = {
            @AuthorizationScope(scope = "apim:admin", description = "Manage all admin operations")
        })
    }, tags={ "Environments" })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK. List of external gateway environments returned.", response = ExternalGatewayEnvironmentListDTO.class),
        @ApiResponse(code = 500, message = "Internal Server Error.", response = ErrorDTO.class) })
    public Response externalEnvironmentsGet() throws APIManagementException{
        return delegate.externalEnvironmentsGet(securityContext);
    }
}
