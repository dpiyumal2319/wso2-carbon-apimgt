package org.wso2.carbon.apimgt.rest.api.store.v1.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.*;

/**
 * API credential for accessing federated API
 **/

import io.swagger.annotations.*;
import java.util.Objects;

import javax.xml.bind.annotation.*;
import org.wso2.carbon.apimgt.rest.api.common.annotations.Scope;
import com.fasterxml.jackson.annotation.JsonCreator;

import javax.validation.Valid;

@ApiModel(description = "API credential for accessing federated API")

public class FederatedCredentialDTO   {
  

    @XmlType(name="CredentialTypeEnum")
    @XmlEnum(String.class)
    public enum CredentialTypeEnum {
        API_KEY("api-key"),
        SUBSCRIPTION_KEY("subscription-key");
        private String value;

        CredentialTypeEnum (String v) {
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
        public static CredentialTypeEnum fromValue(String v) {
            for (CredentialTypeEnum b : CredentialTypeEnum.values()) {
                if (String.valueOf(b.value).equals(v)) {
                    return b;
                }
            }
return null;
        }
    }
    private CredentialTypeEnum credentialType = null;
    private String credentialValue = null;
    private Boolean isValueRetrievable = null;
    private String externalSubscriptionId = null;
    private java.util.Date createdTime = null;
    private java.util.Date expiresAt = null;

  /**
   * Type of credential
   **/
  public FederatedCredentialDTO credentialType(CredentialTypeEnum credentialType) {
    this.credentialType = credentialType;
    return this;
  }

  
  @ApiModelProperty(example = "api-key", value = "Type of credential")
  @JsonProperty("credentialType")
  public CredentialTypeEnum getCredentialType() {
    return credentialType;
  }
  public void setCredentialType(CredentialTypeEnum credentialType) {
    this.credentialType = credentialType;
  }

  /**
   * The credential value (full key on create/regenerate, masked on get)
   **/
  public FederatedCredentialDTO credentialValue(String credentialValue) {
    this.credentialValue = credentialValue;
    return this;
  }

  
  @ApiModelProperty(example = "••••••••ab12", value = "The credential value (full key on create/regenerate, masked on get)")
  @JsonProperty("credentialValue")
  public String getCredentialValue() {
    return credentialValue;
  }
  public void setCredentialValue(String credentialValue) {
    this.credentialValue = credentialValue;
  }

  /**
   * Whether the full credential can be retrieved later
   **/
  public FederatedCredentialDTO isValueRetrievable(Boolean isValueRetrievable) {
    this.isValueRetrievable = isValueRetrievable;
    return this;
  }

  
  @ApiModelProperty(example = "false", value = "Whether the full credential can be retrieved later")
  @JsonProperty("isValueRetrievable")
  public Boolean isIsValueRetrievable() {
    return isValueRetrievable;
  }
  public void setIsValueRetrievable(Boolean isValueRetrievable) {
    this.isValueRetrievable = isValueRetrievable;
  }

  /**
   * External gateway subscription identifier
   **/
  public FederatedCredentialDTO externalSubscriptionId(String externalSubscriptionId) {
    this.externalSubscriptionId = externalSubscriptionId;
    return this;
  }

  
  @ApiModelProperty(example = "abc123def456", value = "External gateway subscription identifier")
  @JsonProperty("externalSubscriptionId")
  public String getExternalSubscriptionId() {
    return externalSubscriptionId;
  }
  public void setExternalSubscriptionId(String externalSubscriptionId) {
    this.externalSubscriptionId = externalSubscriptionId;
  }

  /**
   * Credential creation timestamp
   **/
  public FederatedCredentialDTO createdTime(java.util.Date createdTime) {
    this.createdTime = createdTime;
    return this;
  }

  
  @ApiModelProperty(value = "Credential creation timestamp")
  @JsonProperty("createdTime")
  public java.util.Date getCreatedTime() {
    return createdTime;
  }
  public void setCreatedTime(java.util.Date createdTime) {
    this.createdTime = createdTime;
  }

  /**
   * Credential expiration timestamp (if applicable)
   **/
  public FederatedCredentialDTO expiresAt(java.util.Date expiresAt) {
    this.expiresAt = expiresAt;
    return this;
  }

  
  @ApiModelProperty(value = "Credential expiration timestamp (if applicable)")
  @JsonProperty("expiresAt")
  public java.util.Date getExpiresAt() {
    return expiresAt;
  }
  public void setExpiresAt(java.util.Date expiresAt) {
    this.expiresAt = expiresAt;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    FederatedCredentialDTO federatedCredential = (FederatedCredentialDTO) o;
    return Objects.equals(credentialType, federatedCredential.credentialType) &&
        Objects.equals(credentialValue, federatedCredential.credentialValue) &&
        Objects.equals(isValueRetrievable, federatedCredential.isValueRetrievable) &&
        Objects.equals(externalSubscriptionId, federatedCredential.externalSubscriptionId) &&
        Objects.equals(createdTime, federatedCredential.createdTime) &&
        Objects.equals(expiresAt, federatedCredential.expiresAt);
  }

  @Override
  public int hashCode() {
    return Objects.hash(credentialType, credentialValue, isValueRetrievable, externalSubscriptionId, createdTime, expiresAt);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class FederatedCredentialDTO {\n");
    
    sb.append("    credentialType: ").append(toIndentedString(credentialType)).append("\n");
    sb.append("    credentialValue: ").append(toIndentedString(credentialValue)).append("\n");
    sb.append("    isValueRetrievable: ").append(toIndentedString(isValueRetrievable)).append("\n");
    sb.append("    externalSubscriptionId: ").append(toIndentedString(externalSubscriptionId)).append("\n");
    sb.append("    createdTime: ").append(toIndentedString(createdTime)).append("\n");
    sb.append("    expiresAt: ").append(toIndentedString(expiresAt)).append("\n");
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

