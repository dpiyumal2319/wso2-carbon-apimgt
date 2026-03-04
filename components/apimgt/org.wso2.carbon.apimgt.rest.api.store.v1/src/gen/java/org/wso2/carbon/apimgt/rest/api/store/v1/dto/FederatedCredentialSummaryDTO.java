package org.wso2.carbon.apimgt.rest.api.store.v1.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.*;

/**
 * Lightweight summary of a federated credential for table display
 **/

import io.swagger.annotations.*;
import java.util.Objects;

import javax.xml.bind.annotation.*;
import org.wso2.carbon.apimgt.rest.api.common.annotations.Scope;
import com.fasterxml.jackson.annotation.JsonCreator;

import javax.validation.Valid;

@ApiModel(description = "Lightweight summary of a federated credential for table display")

public class FederatedCredentialSummaryDTO   {
  
    private String credentialId = null;
    private String subscriptionId = null;
    private String applicationId = null;
    private String applicationName = null;
    private String apiId = null;
    private String apiName = null;
    private String apiDisplayName = null;
    private String apiVersion = null;
    private String apiLifeCycleStatus = null;
    private String apiType = null;
    private String name = null;
    private String selectedOption = null;
    private java.util.Date lastUpdatedTime = null;

  /**
   * UUID of the local credential record
   **/
  public FederatedCredentialSummaryDTO credentialId(String credentialId) {
    this.credentialId = credentialId;
    return this;
  }

  
  @ApiModelProperty(example = "1b2c3d4e-5f67-890a-bcde-f1234567890a", value = "UUID of the local credential record")
  @JsonProperty("credentialId")
  public String getCredentialId() {
    return credentialId;
  }
  public void setCredentialId(String credentialId) {
    this.credentialId = credentialId;
  }

  /**
   * UUID of the WSO2 subscription
   **/
  public FederatedCredentialSummaryDTO subscriptionId(String subscriptionId) {
    this.subscriptionId = subscriptionId;
    return this;
  }

  
  @ApiModelProperty(example = "faae5fcc-cbae-40c4-bf43-89931571d250", value = "UUID of the WSO2 subscription")
  @JsonProperty("subscriptionId")
  public String getSubscriptionId() {
    return subscriptionId;
  }
  public void setSubscriptionId(String subscriptionId) {
    this.subscriptionId = subscriptionId;
  }

  /**
   * UUID of the application
   **/
  public FederatedCredentialSummaryDTO applicationId(String applicationId) {
    this.applicationId = applicationId;
    return this;
  }

  
  @ApiModelProperty(example = "3a4b5c6d-7e8f-9a0b-1c2d-3e4f5a6b7c8d", value = "UUID of the application")
  @JsonProperty("applicationId")
  public String getApplicationId() {
    return applicationId;
  }
  public void setApplicationId(String applicationId) {
    this.applicationId = applicationId;
  }

  /**
   * Name of the application
   **/
  public FederatedCredentialSummaryDTO applicationName(String applicationName) {
    this.applicationName = applicationName;
    return this;
  }

  
  @ApiModelProperty(example = "MyApp", value = "Name of the application")
  @JsonProperty("applicationName")
  public String getApplicationName() {
    return applicationName;
  }
  public void setApplicationName(String applicationName) {
    this.applicationName = applicationName;
  }

  /**
   * UUID of the API
   **/
  public FederatedCredentialSummaryDTO apiId(String apiId) {
    this.apiId = apiId;
    return this;
  }

  
  @ApiModelProperty(example = "6f5e4d3c-2b1a-0f9e-8d7c-6b5a4e3d2c1b", value = "UUID of the API")
  @JsonProperty("apiId")
  public String getApiId() {
    return apiId;
  }
  public void setApiId(String apiId) {
    this.apiId = apiId;
  }

  /**
   * Name of the API
   **/
  public FederatedCredentialSummaryDTO apiName(String apiName) {
    this.apiName = apiName;
    return this;
  }

  
  @ApiModelProperty(example = "PetStore", value = "Name of the API")
  @JsonProperty("apiName")
  public String getApiName() {
    return apiName;
  }
  public void setApiName(String apiName) {
    this.apiName = apiName;
  }

  /**
   * Display name of the API
   **/
  public FederatedCredentialSummaryDTO apiDisplayName(String apiDisplayName) {
    this.apiDisplayName = apiDisplayName;
    return this;
  }

  
  @ApiModelProperty(example = "Pet Store API", value = "Display name of the API")
  @JsonProperty("apiDisplayName")
  public String getApiDisplayName() {
    return apiDisplayName;
  }
  public void setApiDisplayName(String apiDisplayName) {
    this.apiDisplayName = apiDisplayName;
  }

  /**
   * Version of the API
   **/
  public FederatedCredentialSummaryDTO apiVersion(String apiVersion) {
    this.apiVersion = apiVersion;
    return this;
  }

  
  @ApiModelProperty(example = "v1", value = "Version of the API")
  @JsonProperty("apiVersion")
  public String getApiVersion() {
    return apiVersion;
  }
  public void setApiVersion(String apiVersion) {
    this.apiVersion = apiVersion;
  }

  /**
   * Lifecycle status of the API
   **/
  public FederatedCredentialSummaryDTO apiLifeCycleStatus(String apiLifeCycleStatus) {
    this.apiLifeCycleStatus = apiLifeCycleStatus;
    return this;
  }

  
  @ApiModelProperty(example = "PUBLISHED", value = "Lifecycle status of the API")
  @JsonProperty("apiLifeCycleStatus")
  public String getApiLifeCycleStatus() {
    return apiLifeCycleStatus;
  }
  public void setApiLifeCycleStatus(String apiLifeCycleStatus) {
    this.apiLifeCycleStatus = apiLifeCycleStatus;
  }

  /**
   * Type of the API
   **/
  public FederatedCredentialSummaryDTO apiType(String apiType) {
    this.apiType = apiType;
    return this;
  }

  
  @ApiModelProperty(example = "HTTP", value = "Type of the API")
  @JsonProperty("apiType")
  public String getApiType() {
    return apiType;
  }
  public void setApiType(String apiType) {
    this.apiType = apiType;
  }

  /**
   * Name for the credential
   **/
  public FederatedCredentialSummaryDTO name(String name) {
    this.name = name;
    return this;
  }

  
  @ApiModelProperty(example = "My Production Key", value = "Name for the credential")
  @JsonProperty("name")
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }

  /**
   * JSON wrapper containing schemaName and body of the selected subscription option. Format: {\&quot;schemaName\&quot;:\&quot;tier-selector\&quot;,\&quot;body\&quot;:\&quot;{\\\&quot;id\\\&quot;:\\\&quot;plan1\\\&quot;,\\\&quot;name\\\&quot;:\\\&quot;Basic\\\&quot;}\&quot;} 
   **/
  public FederatedCredentialSummaryDTO selectedOption(String selectedOption) {
    this.selectedOption = selectedOption;
    return this;
  }

  
  @ApiModelProperty(example = "{\"schemaName\":\"tier-selector\",\"body\":\"{\\\"id\\\":\\\"plan1\\\",\\\"name\\\":\\\"Basic\\\"}\"}", value = "JSON wrapper containing schemaName and body of the selected subscription option. Format: {\"schemaName\":\"tier-selector\",\"body\":\"{\\\"id\\\":\\\"plan1\\\",\\\"name\\\":\\\"Basic\\\"}\"} ")
  @JsonProperty("selectedOption")
  public String getSelectedOption() {
    return selectedOption;
  }
  public void setSelectedOption(String selectedOption) {
    this.selectedOption = selectedOption;
  }

  /**
   * Last time the mapping was updated
   **/
  public FederatedCredentialSummaryDTO lastUpdatedTime(java.util.Date lastUpdatedTime) {
    this.lastUpdatedTime = lastUpdatedTime;
    return this;
  }

  
  @ApiModelProperty(example = "2025-01-30T10:00Z", value = "Last time the mapping was updated")
  @JsonProperty("lastUpdatedTime")
  public java.util.Date getLastUpdatedTime() {
    return lastUpdatedTime;
  }
  public void setLastUpdatedTime(java.util.Date lastUpdatedTime) {
    this.lastUpdatedTime = lastUpdatedTime;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    FederatedCredentialSummaryDTO federatedCredentialSummary = (FederatedCredentialSummaryDTO) o;
    return Objects.equals(credentialId, federatedCredentialSummary.credentialId) &&
        Objects.equals(subscriptionId, federatedCredentialSummary.subscriptionId) &&
        Objects.equals(applicationId, federatedCredentialSummary.applicationId) &&
        Objects.equals(applicationName, federatedCredentialSummary.applicationName) &&
        Objects.equals(apiId, federatedCredentialSummary.apiId) &&
        Objects.equals(apiName, federatedCredentialSummary.apiName) &&
        Objects.equals(apiDisplayName, federatedCredentialSummary.apiDisplayName) &&
        Objects.equals(apiVersion, federatedCredentialSummary.apiVersion) &&
        Objects.equals(apiLifeCycleStatus, federatedCredentialSummary.apiLifeCycleStatus) &&
        Objects.equals(apiType, federatedCredentialSummary.apiType) &&
        Objects.equals(name, federatedCredentialSummary.name) &&
        Objects.equals(selectedOption, federatedCredentialSummary.selectedOption) &&
        Objects.equals(lastUpdatedTime, federatedCredentialSummary.lastUpdatedTime);
  }

  @Override
  public int hashCode() {
    return Objects.hash(credentialId, subscriptionId, applicationId, applicationName, apiId, apiName, apiDisplayName, apiVersion, apiLifeCycleStatus, apiType, name, selectedOption, lastUpdatedTime);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class FederatedCredentialSummaryDTO {\n");
    
    sb.append("    credentialId: ").append(toIndentedString(credentialId)).append("\n");
    sb.append("    subscriptionId: ").append(toIndentedString(subscriptionId)).append("\n");
    sb.append("    applicationId: ").append(toIndentedString(applicationId)).append("\n");
    sb.append("    applicationName: ").append(toIndentedString(applicationName)).append("\n");
    sb.append("    apiId: ").append(toIndentedString(apiId)).append("\n");
    sb.append("    apiName: ").append(toIndentedString(apiName)).append("\n");
    sb.append("    apiDisplayName: ").append(toIndentedString(apiDisplayName)).append("\n");
    sb.append("    apiVersion: ").append(toIndentedString(apiVersion)).append("\n");
    sb.append("    apiLifeCycleStatus: ").append(toIndentedString(apiLifeCycleStatus)).append("\n");
    sb.append("    apiType: ").append(toIndentedString(apiType)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    selectedOption: ").append(toIndentedString(selectedOption)).append("\n");
    sb.append("    lastUpdatedTime: ").append(toIndentedString(lastUpdatedTime)).append("\n");
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

