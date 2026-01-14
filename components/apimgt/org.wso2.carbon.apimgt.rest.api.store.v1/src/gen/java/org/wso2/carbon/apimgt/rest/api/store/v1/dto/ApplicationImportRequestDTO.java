package org.wso2.carbon.apimgt.rest.api.store.v1.dto;

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



public class ApplicationImportRequestDTO   {
  
    private String environmentId = null;
    private String referenceArtifact = null;

  /**
   * Gateway environment ID
   **/
  public ApplicationImportRequestDTO environmentId(String environmentId) {
    this.environmentId = environmentId;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Gateway environment ID")
  @JsonProperty("environmentId")
  @NotNull
  public String getEnvironmentId() {
    return environmentId;
  }
  public void setEnvironmentId(String environmentId) {
    this.environmentId = environmentId;
  }

  /**
   * JSON string containing gateway metadata for import
   **/
  public ApplicationImportRequestDTO referenceArtifact(String referenceArtifact) {
    this.referenceArtifact = referenceArtifact;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "JSON string containing gateway metadata for import")
  @JsonProperty("referenceArtifact")
  @NotNull
  public String getReferenceArtifact() {
    return referenceArtifact;
  }
  public void setReferenceArtifact(String referenceArtifact) {
    this.referenceArtifact = referenceArtifact;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ApplicationImportRequestDTO applicationImportRequest = (ApplicationImportRequestDTO) o;
    return Objects.equals(environmentId, applicationImportRequest.environmentId) &&
        Objects.equals(referenceArtifact, applicationImportRequest.referenceArtifact);
  }

  @Override
  public int hashCode() {
    return Objects.hash(environmentId, referenceArtifact);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ApplicationImportRequestDTO {\n");
    
    sb.append("    environmentId: ").append(toIndentedString(environmentId)).append("\n");
    sb.append("    referenceArtifact: ").append(toIndentedString(referenceArtifact)).append("\n");
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

