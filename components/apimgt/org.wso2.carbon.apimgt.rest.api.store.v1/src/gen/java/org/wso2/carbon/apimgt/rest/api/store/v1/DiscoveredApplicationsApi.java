package org.wso2.carbon.apimgt.rest.api.store.v1;

import org.wso2.carbon.apimgt.rest.api.store.v1.dto.ApplicationDTO;
import org.wso2.carbon.apimgt.rest.api.store.v1.dto.ApplicationImportRequestDTO;
import org.wso2.carbon.apimgt.rest.api.store.v1.dto.ErrorDTO;
import org.wso2.carbon.apimgt.rest.api.store.v1.DiscoveredApplicationsApiService;
import org.wso2.carbon.apimgt.rest.api.store.v1.impl.DiscoveredApplicationsApiServiceImpl;
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
@Path("/discovered-applications")

@Api(description = "the discovered-applications API")




public class DiscoveredApplicationsApi  {

  @Context MessageContext securityContext;

DiscoveredApplicationsApiService delegate = new DiscoveredApplicationsApiServiceImpl();


    @POST
    @Path("/import")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @ApiOperation(value = "Import discovered application", notes = "This operation can be used to import a discovered application from an external gateway. ", response = ApplicationDTO.class, authorizations = {
        @Authorization(value = "OAuth2Security", scopes = {
            @AuthorizationScope(scope = "apim:admin", description = "Manage all admin operations")
        })
    }, tags={ "Applications" })
    @ApiResponses(value = { 
        @ApiResponse(code = 201, message = "Created", response = ApplicationDTO.class),
        @ApiResponse(code = 400, message = "Bad Request. Invalid request or validation error.", response = ErrorDTO.class),
        @ApiResponse(code = 409, message = "Conflict. Specified resource already exists.", response = ErrorDTO.class),
        @ApiResponse(code = 500, message = "Internal Server Error.", response = ErrorDTO.class) })
    public Response importDiscoveredApplication(@ApiParam(value = "Application import request" ,required=true) ApplicationImportRequestDTO applicationImportRequestDTO) throws APIManagementException{
        return delegate.importDiscoveredApplication(applicationImportRequestDTO, securityContext);
    }
}
