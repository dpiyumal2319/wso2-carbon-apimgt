package org.wso2.carbon.apimgt.rest.api.store.v1.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.validation.constraints.*;

/**
 * Instructions for invoking the federated API
 **/

import io.swagger.annotations.*;
import java.util.Objects;

import javax.xml.bind.annotation.*;
import org.wso2.carbon.apimgt.rest.api.common.annotations.Scope;
import com.fasterxml.jackson.annotation.JsonCreator;

import javax.validation.Valid;

@ApiModel(description = "Instructions for invoking the federated API")

public class InvocationInstructionDTO   {
  
    private String gatewayType = null;
    private String credentialType = null;
    private String credentialHeaderName = null;
    private String credentialQueryParam = null;
    private String baseUrl = null;
    private String basePath = null;
    private String fullUrl = null;
    private String region = null;
    private String environment = null;
    private Map<String, String> additionalHeaders = new HashMap<String, String>();
    private String curlExample = null;
    private String notes = null;

  /**
   * Type of the gateway
   **/
  public InvocationInstructionDTO gatewayType(String gatewayType) {
    this.gatewayType = gatewayType;
    return this;
  }

  
  @ApiModelProperty(example = "aws", value = "Type of the gateway")
  @JsonProperty("gatewayType")
  public String getGatewayType() {
    return gatewayType;
  }
  public void setGatewayType(String gatewayType) {
    this.gatewayType = gatewayType;
  }

  /**
   * Type of credential required
   **/
  public InvocationInstructionDTO credentialType(String credentialType) {
    this.credentialType = credentialType;
    return this;
  }

  
  @ApiModelProperty(example = "api-key", value = "Type of credential required")
  @JsonProperty("credentialType")
  public String getCredentialType() {
    return credentialType;
  }
  public void setCredentialType(String credentialType) {
    this.credentialType = credentialType;
  }

  /**
   * Header name for the credential
   **/
  public InvocationInstructionDTO credentialHeaderName(String credentialHeaderName) {
    this.credentialHeaderName = credentialHeaderName;
    return this;
  }

  
  @ApiModelProperty(example = "x-api-key", value = "Header name for the credential")
  @JsonProperty("credentialHeaderName")
  public String getCredentialHeaderName() {
    return credentialHeaderName;
  }
  public void setCredentialHeaderName(String credentialHeaderName) {
    this.credentialHeaderName = credentialHeaderName;
  }

  /**
   * Query parameter name for the credential (if applicable)
   **/
  public InvocationInstructionDTO credentialQueryParam(String credentialQueryParam) {
    this.credentialQueryParam = credentialQueryParam;
    return this;
  }

  
  @ApiModelProperty(example = "apikey", value = "Query parameter name for the credential (if applicable)")
  @JsonProperty("credentialQueryParam")
  public String getCredentialQueryParam() {
    return credentialQueryParam;
  }
  public void setCredentialQueryParam(String credentialQueryParam) {
    this.credentialQueryParam = credentialQueryParam;
  }

  /**
   * Base URL of the API
   **/
  public InvocationInstructionDTO baseUrl(String baseUrl) {
    this.baseUrl = baseUrl;
    return this;
  }

  
  @ApiModelProperty(example = "https://abc123.execute-api.us-east-1.amazonaws.com", value = "Base URL of the API")
  @JsonProperty("baseUrl")
  public String getBaseUrl() {
    return baseUrl;
  }
  public void setBaseUrl(String baseUrl) {
    this.baseUrl = baseUrl;
  }

  /**
   * Base path of the API
   **/
  public InvocationInstructionDTO basePath(String basePath) {
    this.basePath = basePath;
    return this;
  }

  
  @ApiModelProperty(example = "/prod", value = "Base path of the API")
  @JsonProperty("basePath")
  public String getBasePath() {
    return basePath;
  }
  public void setBasePath(String basePath) {
    this.basePath = basePath;
  }

  /**
   * Full URL to the API (baseUrl + basePath)
   **/
  public InvocationInstructionDTO fullUrl(String fullUrl) {
    this.fullUrl = fullUrl;
    return this;
  }

  
  @ApiModelProperty(example = "https://abc123.execute-api.us-east-1.amazonaws.com/prod", value = "Full URL to the API (baseUrl + basePath)")
  @JsonProperty("fullUrl")
  public String getFullUrl() {
    return fullUrl;
  }
  public void setFullUrl(String fullUrl) {
    this.fullUrl = fullUrl;
  }

  /**
   * Cloud region (for AWS, Azure)
   **/
  public InvocationInstructionDTO region(String region) {
    this.region = region;
    return this;
  }

  
  @ApiModelProperty(example = "us-east-1", value = "Cloud region (for AWS, Azure)")
  @JsonProperty("region")
  public String getRegion() {
    return region;
  }
  public void setRegion(String region) {
    this.region = region;
  }

  /**
   * Gateway environment name
   **/
  public InvocationInstructionDTO environment(String environment) {
    this.environment = environment;
    return this;
  }

  
  @ApiModelProperty(example = "production", value = "Gateway environment name")
  @JsonProperty("environment")
  public String getEnvironment() {
    return environment;
  }
  public void setEnvironment(String environment) {
    this.environment = environment;
  }

  /**
   * Additional headers required for invocation
   **/
  public InvocationInstructionDTO additionalHeaders(Map<String, String> additionalHeaders) {
    this.additionalHeaders = additionalHeaders;
    return this;
  }

  
  @ApiModelProperty(value = "Additional headers required for invocation")
  @JsonProperty("additionalHeaders")
  public Map<String, String> getAdditionalHeaders() {
    return additionalHeaders;
  }
  public void setAdditionalHeaders(Map<String, String> additionalHeaders) {
    this.additionalHeaders = additionalHeaders;
  }

  /**
   * Example curl command for invoking the API
   **/
  public InvocationInstructionDTO curlExample(String curlExample) {
    this.curlExample = curlExample;
    return this;
  }

  
  @ApiModelProperty(example = "curl -X GET -H \"x-api-key: YOUR_KEY_HERE\" https://abc123.execute-api.us-east-1.amazonaws.com/prod/resource", value = "Example curl command for invoking the API")
  @JsonProperty("curlExample")
  public String getCurlExample() {
    return curlExample;
  }
  public void setCurlExample(String curlExample) {
    this.curlExample = curlExample;
  }

  /**
   * Additional notes or instructions
   **/
  public InvocationInstructionDTO notes(String notes) {
    this.notes = notes;
    return this;
  }

  
  @ApiModelProperty(example = "This API key must be included in the x-api-key header for all requests", value = "Additional notes or instructions")
  @JsonProperty("notes")
  public String getNotes() {
    return notes;
  }
  public void setNotes(String notes) {
    this.notes = notes;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    InvocationInstructionDTO invocationInstruction = (InvocationInstructionDTO) o;
    return Objects.equals(gatewayType, invocationInstruction.gatewayType) &&
        Objects.equals(credentialType, invocationInstruction.credentialType) &&
        Objects.equals(credentialHeaderName, invocationInstruction.credentialHeaderName) &&
        Objects.equals(credentialQueryParam, invocationInstruction.credentialQueryParam) &&
        Objects.equals(baseUrl, invocationInstruction.baseUrl) &&
        Objects.equals(basePath, invocationInstruction.basePath) &&
        Objects.equals(fullUrl, invocationInstruction.fullUrl) &&
        Objects.equals(region, invocationInstruction.region) &&
        Objects.equals(environment, invocationInstruction.environment) &&
        Objects.equals(additionalHeaders, invocationInstruction.additionalHeaders) &&
        Objects.equals(curlExample, invocationInstruction.curlExample) &&
        Objects.equals(notes, invocationInstruction.notes);
  }

  @Override
  public int hashCode() {
    return Objects.hash(gatewayType, credentialType, credentialHeaderName, credentialQueryParam, baseUrl, basePath, fullUrl, region, environment, additionalHeaders, curlExample, notes);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class InvocationInstructionDTO {\n");
    
    sb.append("    gatewayType: ").append(toIndentedString(gatewayType)).append("\n");
    sb.append("    credentialType: ").append(toIndentedString(credentialType)).append("\n");
    sb.append("    credentialHeaderName: ").append(toIndentedString(credentialHeaderName)).append("\n");
    sb.append("    credentialQueryParam: ").append(toIndentedString(credentialQueryParam)).append("\n");
    sb.append("    baseUrl: ").append(toIndentedString(baseUrl)).append("\n");
    sb.append("    basePath: ").append(toIndentedString(basePath)).append("\n");
    sb.append("    fullUrl: ").append(toIndentedString(fullUrl)).append("\n");
    sb.append("    region: ").append(toIndentedString(region)).append("\n");
    sb.append("    environment: ").append(toIndentedString(environment)).append("\n");
    sb.append("    additionalHeaders: ").append(toIndentedString(additionalHeaders)).append("\n");
    sb.append("    curlExample: ").append(toIndentedString(curlExample)).append("\n");
    sb.append("    notes: ").append(toIndentedString(notes)).append("\n");
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

