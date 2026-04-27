package org.wso2.carbon.apimgt.rest.api.admin.v1.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.*;


import io.swagger.annotations.*;
import java.util.Objects;

import javax.xml.bind.annotation.*;
import org.wso2.carbon.apimgt.rest.api.common.annotations.Scope;
import com.fasterxml.jackson.annotation.JsonCreator;

import javax.validation.Valid;



public class PlanMappingDTO   {
  
    private String localPolicyId = null;
    private String remotePlanReference = null;

  /**
   **/
  public PlanMappingDTO localPolicyId(String localPolicyId) {
    this.localPolicyId = localPolicyId;
    return this;
  }

  
  @ApiModelProperty(example = "78c85595-bbe0-4802-9c9d-46c8dd72d731", value = "")
  @JsonProperty("localPolicyId")
  public String getLocalPolicyId() {
    return localPolicyId;
  }
  public void setLocalPolicyId(String localPolicyId) {
    this.localPolicyId = localPolicyId;
  }

  /**
   **/
  public PlanMappingDTO remotePlanReference(String remotePlanReference) {
    this.remotePlanReference = remotePlanReference;
    return this;
  }

  
  @ApiModelProperty(example = "4msf4m", value = "")
  @JsonProperty("remotePlanReference")
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
    PlanMappingDTO planMapping = (PlanMappingDTO) o;
    return Objects.equals(localPolicyId, planMapping.localPolicyId) &&
        Objects.equals(remotePlanReference, planMapping.remotePlanReference);
  }

  @Override
  public int hashCode() {
    return Objects.hash(localPolicyId, remotePlanReference);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PlanMappingDTO {\n");
    
    sb.append("    localPolicyId: ").append(toIndentedString(localPolicyId)).append("\n");
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

