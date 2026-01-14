package org.wso2.carbon.apimgt.rest.api.store.v1;

import org.wso2.carbon.apimgt.rest.api.store.v1.dto.DiscoveredApplicationListDTO;
import org.wso2.carbon.apimgt.rest.api.store.v1.dto.ErrorDTO;
import org.wso2.carbon.apimgt.rest.api.store.v1.EnvironmentsApiService;
import org.wso2.carbon.apimgt.rest.api.store.v1.impl.EnvironmentsApiServiceImpl;
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
@Path("/environments")

@Api(description = "the environments API")




public class EnvironmentsApi  {

  @Context MessageContext securityContext;

EnvironmentsApiService delegate = new EnvironmentsApiServiceImpl();


    @GET
    @Path("/{environmentId}/discovered-applications")
    
    @Produces({ "application/json" })
    @ApiOperation(value = "Get discovered applications from external gateway", notes = "This operation can be used to retrieve applications discovered from an external gateway environment. ", response = DiscoveredApplicationListDTO.class, authorizations = {
        @Authorization(value = "OAuth2Security", scopes = {
            @AuthorizationScope(scope = "apim:subscribe", description = "Subscribe API")
        })
    }, tags={ "Applications" })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK. List of discovered applications.", response = DiscoveredApplicationListDTO.class),
        @ApiResponse(code = 400, message = "Bad Request. Invalid request or validation error.", response = ErrorDTO.class),
        @ApiResponse(code = 404, message = "Not Found. The specified resource does not exist.", response = ErrorDTO.class),
        @ApiResponse(code = 500, message = "Internal Server Error.", response = ErrorDTO.class) })
    public Response getDiscoveredApplications(@ApiParam(value = "**Environment ID** consisting of the **UUID** of the Gateway Environment. ",required=true) @PathParam("environmentId") String environmentId,  @ApiParam(value = "Maximum size of resource array to return. ", defaultValue="25") @DefaultValue("25") @QueryParam("limit") Integer limit,  @ApiParam(value = "Starting point within the complete list of items qualified. ", defaultValue="0") @DefaultValue("0") @QueryParam("offset") Integer offset,  @ApiParam(value = "**Search condition**. ")  @QueryParam("query") String query) throws APIManagementException{
        return delegate.getDiscoveredApplications(environmentId, limit, offset, query, securityContext);
    }
}
