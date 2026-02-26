package org.wso2.carbon.apimgt.rest.api.store.v1.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.wso2.carbon.apimgt.rest.api.store.v1.dto.FederatedCredentialDTO;
import org.wso2.carbon.apimgt.rest.api.store.v1.dto.InvocationInstructionDTO;
import javax.validation.constraints.*;

/**
 * Result of the combined subscribe-and-create-credential operation
 **/

import io.swagger.annotations.*;
import java.util.Objects;

import javax.xml.bind.annotation.*;
import org.wso2.carbon.apimgt.rest.api.common.annotations.Scope;
import com.fasterxml.jackson.annotation.JsonCreator;

import javax.validation.Valid;

@ApiModel(description = "Result of the combined subscribe-and-create-credential operation")

public class FederatedCredentialCreateResponseDTO   {
  
    private String subscriptionId = null;

    @XmlType(name="StatusEnum")
    @XmlEnum(String.class)
    public enum StatusEnum {
        ACTIVE("ACTIVE"),
        PENDING_APPROVAL("PENDING_APPROVAL");
        private String value;

        StatusEnum (String v) {
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
        public static StatusEnum fromValue(String v) {
            for (StatusEnum b : StatusEnum.values()) {
                if (String.valueOf(b.value).equals(v)) {
                    return b;
                }
            }
return null;
        }
    }
    private StatusEnum status = null;
    private FederatedCredentialDTO credential = null;
    private InvocationInstructionDTO invocationInstruction = null;
    private String gatewayType = null;
    private String gatewayEnvironmentId = null;

  /**
   * UUID of the created WSO2 subscription
   **/
  public FederatedCredentialCreateResponseDTO subscriptionId(String subscriptionId) {
    this.subscriptionId = subscriptionId;
    return this;
  }

  
  @ApiModelProperty(example = "faae5fcc-cbae-40c4-bf43-89931571d250", value = "UUID of the created WSO2 subscription")
  @JsonProperty("subscriptionId")
  public String getSubscriptionId() {
    return subscriptionId;
  }
  public void setSubscriptionId(String subscriptionId) {
    this.subscriptionId = subscriptionId;
  }

  /**
   * Provisioning status: * ACTIVE - Credential was created immediately, included in response * PENDING_APPROVAL - Subscription requires approval before credential can be provisioned 
   **/
  public FederatedCredentialCreateResponseDTO status(StatusEnum status) {
    this.status = status;
    return this;
  }

  
  @ApiModelProperty(example = "ACTIVE", value = "Provisioning status: * ACTIVE - Credential was created immediately, included in response * PENDING_APPROVAL - Subscription requires approval before credential can be provisioned ")
  @JsonProperty("status")
  public StatusEnum getStatus() {
    return status;
  }
  public void setStatus(StatusEnum status) {
    this.status = status;
  }

  /**
   **/
  public FederatedCredentialCreateResponseDTO credential(FederatedCredentialDTO credential) {
    this.credential = credential;
    return this;
  }

  
  @ApiModelProperty(value = "")
      @Valid
  @JsonProperty("credential")
  public FederatedCredentialDTO getCredential() {
    return credential;
  }
  public void setCredential(FederatedCredentialDTO credential) {
    this.credential = credential;
  }

  /**
   **/
  public FederatedCredentialCreateResponseDTO invocationInstruction(InvocationInstructionDTO invocationInstruction) {
    this.invocationInstruction = invocationInstruction;
    return this;
  }

  
  @ApiModelProperty(value = "")
      @Valid
  @JsonProperty("invocationInstruction")
  public InvocationInstructionDTO getInvocationInstruction() {
    return invocationInstruction;
  }
  public void setInvocationInstruction(InvocationInstructionDTO invocationInstruction) {
    this.invocationInstruction = invocationInstruction;
  }

  /**
   * Type of the external gateway
   **/
  public FederatedCredentialCreateResponseDTO gatewayType(String gatewayType) {
    this.gatewayType = gatewayType;
    return this;
  }

  
  @ApiModelProperty(example = "aws", value = "Type of the external gateway")
  @JsonProperty("gatewayType")
  public String getGatewayType() {
    return gatewayType;
  }
  public void setGatewayType(String gatewayType) {
    this.gatewayType = gatewayType;
  }

  /**
   * UUID of the gateway environment
   **/
  public FederatedCredentialCreateResponseDTO gatewayEnvironmentId(String gatewayEnvironmentId) {
    this.gatewayEnvironmentId = gatewayEnvironmentId;
    return this;
  }

  
  @ApiModelProperty(example = "3a4b5c6d-7e8f-9a0b-1c2d-3e4f5a6b7c8d", value = "UUID of the gateway environment")
  @JsonProperty("gatewayEnvironmentId")
  public String getGatewayEnvironmentId() {
    return gatewayEnvironmentId;
  }
  public void setGatewayEnvironmentId(String gatewayEnvironmentId) {
    this.gatewayEnvironmentId = gatewayEnvironmentId;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    FederatedCredentialCreateResponseDTO federatedCredentialCreateResponse = (FederatedCredentialCreateResponseDTO) o;
    return Objects.equals(subscriptionId, federatedCredentialCreateResponse.subscriptionId) &&
        Objects.equals(status, federatedCredentialCreateResponse.status) &&
        Objects.equals(credential, federatedCredentialCreateResponse.credential) &&
        Objects.equals(invocationInstruction, federatedCredentialCreateResponse.invocationInstruction) &&
        Objects.equals(gatewayType, federatedCredentialCreateResponse.gatewayType) &&
        Objects.equals(gatewayEnvironmentId, federatedCredentialCreateResponse.gatewayEnvironmentId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(subscriptionId, status, credential, invocationInstruction, gatewayType, gatewayEnvironmentId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class FederatedCredentialCreateResponseDTO {\n");
    
    sb.append("    subscriptionId: ").append(toIndentedString(subscriptionId)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    credential: ").append(toIndentedString(credential)).append("\n");
    sb.append("    invocationInstruction: ").append(toIndentedString(invocationInstruction)).append("\n");
    sb.append("    gatewayType: ").append(toIndentedString(gatewayType)).append("\n");
    sb.append("    gatewayEnvironmentId: ").append(toIndentedString(gatewayEnvironmentId)).append("\n");
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

