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
  
    private String subscriptionId = null;
    private String applicationId = null;
    private String applicationName = null;
    private String name = null;
    private String selectedOption = null;
    private Boolean isProvisioned = null;

    @XmlType(name="SubscriptionStatusEnum")
    @XmlEnum(String.class)
    public enum SubscriptionStatusEnum {
        UNBLOCKED("UNBLOCKED"),
        ON_HOLD("ON_HOLD"),
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
    private java.util.Date lastUpdatedTime = null;

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
   * Whether the credential has been provisioned on the gateway
   **/
  public FederatedCredentialSummaryDTO isProvisioned(Boolean isProvisioned) {
    this.isProvisioned = isProvisioned;
    return this;
  }

  
  @ApiModelProperty(example = "true", value = "Whether the credential has been provisioned on the gateway")
  @JsonProperty("isProvisioned")
  public Boolean isIsProvisioned() {
    return isProvisioned;
  }
  public void setIsProvisioned(Boolean isProvisioned) {
    this.isProvisioned = isProvisioned;
  }

  /**
   * Status of the WSO2 subscription
   **/
  public FederatedCredentialSummaryDTO subscriptionStatus(SubscriptionStatusEnum subscriptionStatus) {
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
    return Objects.equals(subscriptionId, federatedCredentialSummary.subscriptionId) &&
        Objects.equals(applicationId, federatedCredentialSummary.applicationId) &&
        Objects.equals(applicationName, federatedCredentialSummary.applicationName) &&
        Objects.equals(name, federatedCredentialSummary.name) &&
        Objects.equals(selectedOption, federatedCredentialSummary.selectedOption) &&
        Objects.equals(isProvisioned, federatedCredentialSummary.isProvisioned) &&
        Objects.equals(subscriptionStatus, federatedCredentialSummary.subscriptionStatus) &&
        Objects.equals(lastUpdatedTime, federatedCredentialSummary.lastUpdatedTime);
  }

  @Override
  public int hashCode() {
    return Objects.hash(subscriptionId, applicationId, applicationName, name, selectedOption, isProvisioned, subscriptionStatus, lastUpdatedTime);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class FederatedCredentialSummaryDTO {\n");
    
    sb.append("    subscriptionId: ").append(toIndentedString(subscriptionId)).append("\n");
    sb.append("    applicationId: ").append(toIndentedString(applicationId)).append("\n");
    sb.append("    applicationName: ").append(toIndentedString(applicationName)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    selectedOption: ").append(toIndentedString(selectedOption)).append("\n");
    sb.append("    isProvisioned: ").append(toIndentedString(isProvisioned)).append("\n");
    sb.append("    subscriptionStatus: ").append(toIndentedString(subscriptionStatus)).append("\n");
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

