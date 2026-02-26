package org.wso2.carbon.apimgt.rest.api.store.v1.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.*;

/**
 * Request parameters for the combined subscribe-and-create-credential operation
 **/

import io.swagger.annotations.*;
import java.util.Objects;

import javax.xml.bind.annotation.*;
import org.wso2.carbon.apimgt.rest.api.common.annotations.Scope;
import com.fasterxml.jackson.annotation.JsonCreator;

import javax.validation.Valid;

@ApiModel(description = "Request parameters for the combined subscribe-and-create-credential operation")

public class FederatedCredentialCreateRequestDTO   {
  
    private String applicationId = null;
    private String name = null;
    private String selectedOption = null;

  /**
   * UUID of the application to subscribe with
   **/
  public FederatedCredentialCreateRequestDTO applicationId(String applicationId) {
    this.applicationId = applicationId;
    return this;
  }

  
  @ApiModelProperty(example = "3a4b5c6d-7e8f-9a0b-1c2d-3e4f5a6b7c8d", required = true, value = "UUID of the application to subscribe with")
  @JsonProperty("applicationId")
  @NotNull
  public String getApplicationId() {
    return applicationId;
  }
  public void setApplicationId(String applicationId) {
    this.applicationId = applicationId;
  }

  /**
   * Name for the credential
   **/
  public FederatedCredentialCreateRequestDTO name(String name) {
    this.name = name;
    return this;
  }

  
  @ApiModelProperty(example = "My Production Key", required = true, value = "Name for the credential")
  @JsonProperty("name")
  @NotNull
 @Size(min=1,max=255)  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }

  /**
   * JSON wrapper containing schemaName and body for the developer&#39;s selected subscription option. Format: {\&quot;schemaName\&quot;:\&quot;tier-selector\&quot;,\&quot;body\&quot;:\&quot;{\\\&quot;id\\\&quot;:\\\&quot;plan1\\\&quot;,\\\&quot;name\\\&quot;:\\\&quot;Basic\\\&quot;}\&quot;} 
   **/
  public FederatedCredentialCreateRequestDTO selectedOption(String selectedOption) {
    this.selectedOption = selectedOption;
    return this;
  }

  
  @ApiModelProperty(example = "{\"schemaName\":\"tier-selector\",\"body\":\"{\\\"id\\\":\\\"plan1\\\",\\\"name\\\":\\\"Basic\\\"}\"}", value = "JSON wrapper containing schemaName and body for the developer's selected subscription option. Format: {\"schemaName\":\"tier-selector\",\"body\":\"{\\\"id\\\":\\\"plan1\\\",\\\"name\\\":\\\"Basic\\\"}\"} ")
  @JsonProperty("selectedOption")
  public String getSelectedOption() {
    return selectedOption;
  }
  public void setSelectedOption(String selectedOption) {
    this.selectedOption = selectedOption;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    FederatedCredentialCreateRequestDTO federatedCredentialCreateRequest = (FederatedCredentialCreateRequestDTO) o;
    return Objects.equals(applicationId, federatedCredentialCreateRequest.applicationId) &&
        Objects.equals(name, federatedCredentialCreateRequest.name) &&
        Objects.equals(selectedOption, federatedCredentialCreateRequest.selectedOption);
  }

  @Override
  public int hashCode() {
    return Objects.hash(applicationId, name, selectedOption);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class FederatedCredentialCreateRequestDTO {\n");
    
    sb.append("    applicationId: ").append(toIndentedString(applicationId)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    selectedOption: ").append(toIndentedString(selectedOption)).append("\n");
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

