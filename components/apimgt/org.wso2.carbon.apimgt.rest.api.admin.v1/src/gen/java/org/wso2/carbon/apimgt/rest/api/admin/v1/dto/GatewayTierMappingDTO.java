package org.wso2.carbon.apimgt.rest.api.admin.v1.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.*;

/**
 * Maps a local WSO2 subscription tier to a plan on the remote external gateway.
 **/

import io.swagger.annotations.*;
import java.util.Objects;

import javax.xml.bind.annotation.*;
import org.wso2.carbon.apimgt.rest.api.common.annotations.Scope;
import com.fasterxml.jackson.annotation.JsonCreator;

import javax.validation.Valid;
@ApiModel(description = "Maps a local WSO2 subscription tier to a plan on the remote external gateway.")


public class GatewayTierMappingDTO   {
  
    private String localTierName = null;
    private String remotePlanReference = null;

  /**
   * Name of the local WSO2 subscription tier (e.g., Unlimited, Gold).
   **/
  public GatewayTierMappingDTO localTierName(String localTierName) {
    this.localTierName = localTierName;
    return this;
  }

  
  @ApiModelProperty(example = "Unlimited", required = true, value = "Name of the local WSO2 subscription tier (e.g., Unlimited, Gold).")
  @JsonProperty("localTierName")
  @NotNull
  public String getLocalTierName() {
    return localTierName;
  }
  public void setLocalTierName(String localTierName) {
    this.localTierName = localTierName;
  }

  /**
   * Opaque connector-owned reference for the remote gateway plan. The structure is gateway-specific and must not be interpreted by API Manager.
   **/
  public GatewayTierMappingDTO remotePlanReference(String remotePlanReference) {
    this.remotePlanReference = remotePlanReference;
    return this;
  }

  
  @ApiModelProperty(example = "{\"id\":\"abc123\"}", required = true, value = "Opaque connector-owned reference for the remote gateway plan. The structure is gateway-specific and must not be interpreted by API Manager. ")
  @JsonProperty("remotePlanReference")
  @NotNull
  public String getRemotePlanReference() {
    return remotePlanReference;
  }
  public void setRemotePlanReference(String remotePlanReference) {
    this.remotePlanReference = remotePlanReference;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    GatewayTierMappingDTO gatewayTierMapping = (GatewayTierMappingDTO) o;
    return Objects.equals(localTierName, gatewayTierMapping.localTierName) &&
        Objects.equals(remotePlanReference, gatewayTierMapping.remotePlanReference);
  }

  @Override
  public int hashCode() {
    return Objects.hash(localTierName, remotePlanReference);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class GatewayTierMappingDTO {\n");
    
    sb.append("    localTierName: ").append(toIndentedString(localTierName)).append("\n");
    sb.append("    remotePlanReference: ").append(toIndentedString(remotePlanReference)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

