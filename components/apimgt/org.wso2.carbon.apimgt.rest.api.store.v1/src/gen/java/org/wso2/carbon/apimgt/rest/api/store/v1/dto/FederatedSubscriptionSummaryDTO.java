package org.wso2.carbon.apimgt.rest.api.store.v1.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.*;

/**
 * Lightweight summary of a federated subscription for table display
 **/

import io.swagger.annotations.*;
import java.util.Objects;

import javax.xml.bind.annotation.*;
import org.wso2.carbon.apimgt.rest.api.common.annotations.Scope;
import com.fasterxml.jackson.annotation.JsonCreator;

import javax.validation.Valid;

@ApiModel(description = "Lightweight summary of a federated subscription for table display")

public class FederatedSubscriptionSummaryDTO   {
  
    private String subscriptionId = null;
    private String mappingId = null;
    private String applicationId = null;
    private String applicationName = null;
    private String selectedOption = null;

    @XmlType(name="SubscriptionStatusEnum")
    @XmlEnum(String.class)
    public enum SubscriptionStatusEnum {
        BLOCKED("BLOCKED"),
        PROD_ONLY_BLOCKED("PROD_ONLY_BLOCKED"),
        UNBLOCKED("UNBLOCKED"),
        ON_HOLD("ON_HOLD"),
        REJECTED("REJECTED"),
        TIER_UPDATE_PENDING("TIER_UPDATE_PENDING"),
        DELETE_PENDING("DELETE_PENDING");
        private String value;

        SubscriptionStatusEnum (String v) {
            value = v;
        }

        public String value() {
            return value;
        }

        @Override
        public String toString() {
            return String.valueOf(value);
        }

        @JsonCreator
        public static SubscriptionStatusEnum fromValue(String v) {
            for (SubscriptionStatusEnum b : SubscriptionStatusEnum.values()) {
                if (String.valueOf(b.value).equals(v)) {
                    return b;
                }
            }
return null;
        }
    }
    private SubscriptionStatusEnum subscriptionStatus = null;
    private Integer credentialCount = null;
    private java.util.Date createdTime = null;
    private java.util.Date lastUpdatedTime = null;

  /**
   * UUID of the WSO2 subscription
   **/
  public FederatedSubscriptionSummaryDTO subscriptionId(String subscriptionId) {
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
   * UUID of the external mapping record
   **/
  public FederatedSubscriptionSummaryDTO mappingId(String mappingId) {
    this.mappingId = mappingId;
    return this;
  }

  
  @ApiModelProperty(example = "3a4b5c6d-7e8f-9a0b-1c2d-3e4f5a6b7c8d", value = "UUID of the external mapping record")
  @JsonProperty("mappingId")
  public String getMappingId() {
    return mappingId;
  }
  public void setMappingId(String mappingId) {
    this.mappingId = mappingId;
  }

  /**
   * UUID of the application
   **/
  public FederatedSubscriptionSummaryDTO applicationId(String applicationId) {
    this.applicationId = applicationId;
    return this;
  }

  
  @ApiModelProperty(example = "7c8d9e0f-1a2b-3c4d-5e6f-7a8b9c0d1e2f", value = "UUID of the application")
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
  public FederatedSubscriptionSummaryDTO applicationName(String applicationName) {
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
   * JSON wrapper containing schemaName and body of the selected subscription option. Format: {\&quot;schemaName\&quot;:\&quot;tier-selector\&quot;,\&quot;body\&quot;:\&quot;{\\\&quot;id\\\&quot;:\\\&quot;plan1\\\&quot;,\\\&quot;name\\\&quot;:\\\&quot;Basic\\\&quot;}\&quot;} 
   **/
  public FederatedSubscriptionSummaryDTO selectedOption(String selectedOption) {
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
   * Status of the WSO2 subscription
   **/
  public FederatedSubscriptionSummaryDTO subscriptionStatus(SubscriptionStatusEnum subscriptionStatus) {
    this.subscriptionStatus = subscriptionStatus;
    return this;
  }

  
  @ApiModelProperty(example = "UNBLOCKED", value = "Status of the WSO2 subscription")
  @JsonProperty("subscriptionStatus")
  public SubscriptionStatusEnum getSubscriptionStatus() {
    return subscriptionStatus;
  }
  public void setSubscriptionStatus(SubscriptionStatusEnum subscriptionStatus) {
    this.subscriptionStatus = subscriptionStatus;
  }

  /**
   * Number of credentials provisioned for this subscription
   **/
  public FederatedSubscriptionSummaryDTO credentialCount(Integer credentialCount) {
    this.credentialCount = credentialCount;
    return this;
  }

  
  @ApiModelProperty(example = "2", value = "Number of credentials provisioned for this subscription")
  @JsonProperty("credentialCount")
  public Integer getCredentialCount() {
    return credentialCount;
  }
  public void setCredentialCount(Integer credentialCount) {
    this.credentialCount = credentialCount;
  }

  /**
   * When the subscription was created
   **/
  public FederatedSubscriptionSummaryDTO createdTime(java.util.Date createdTime) {
    this.createdTime = createdTime;
    return this;
  }

  
  @ApiModelProperty(example = "2025-01-30T10:00Z", value = "When the subscription was created")
  @JsonProperty("createdTime")
  public java.util.Date getCreatedTime() {
    return createdTime;
  }
  public void setCreatedTime(java.util.Date createdTime) {
    this.createdTime = createdTime;
  }

  /**
   * Last time the mapping was updated
   **/
  public FederatedSubscriptionSummaryDTO lastUpdatedTime(java.util.Date lastUpdatedTime) {
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
    FederatedSubscriptionSummaryDTO federatedSubscriptionSummary = (FederatedSubscriptionSummaryDTO) o;
    return Objects.equals(subscriptionId, federatedSubscriptionSummary.subscriptionId) &&
        Objects.equals(mappingId, federatedSubscriptionSummary.mappingId) &&
        Objects.equals(applicationId, federatedSubscriptionSummary.applicationId) &&
        Objects.equals(applicationName, federatedSubscriptionSummary.applicationName) &&
        Objects.equals(selectedOption, federatedSubscriptionSummary.selectedOption) &&
        Objects.equals(subscriptionStatus, federatedSubscriptionSummary.subscriptionStatus) &&
        Objects.equals(credentialCount, federatedSubscriptionSummary.credentialCount) &&
        Objects.equals(createdTime, federatedSubscriptionSummary.createdTime) &&
        Objects.equals(lastUpdatedTime, federatedSubscriptionSummary.lastUpdatedTime);
  }

  @Override
  public int hashCode() {
    return Objects.hash(subscriptionId, mappingId, applicationId, applicationName, selectedOption, subscriptionStatus, credentialCount, createdTime, lastUpdatedTime);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class FederatedSubscriptionSummaryDTO {\n");
    
    sb.append("    subscriptionId: ").append(toIndentedString(subscriptionId)).append("\n");
    sb.append("    mappingId: ").append(toIndentedString(mappingId)).append("\n");
    sb.append("    applicationId: ").append(toIndentedString(applicationId)).append("\n");
    sb.append("    applicationName: ").append(toIndentedString(applicationName)).append("\n");
    sb.append("    selectedOption: ").append(toIndentedString(selectedOption)).append("\n");
    sb.append("    subscriptionStatus: ").append(toIndentedString(subscriptionStatus)).append("\n");
    sb.append("    credentialCount: ").append(toIndentedString(credentialCount)).append("\n");
    sb.append("    createdTime: ").append(toIndentedString(createdTime)).append("\n");
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

