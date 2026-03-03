package org.wso2.carbon.apimgt.rest.api.store.v1.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.wso2.carbon.apimgt.rest.api.store.v1.dto.FederatedCredentialDTO;
import org.wso2.carbon.apimgt.rest.api.store.v1.dto.InvocationInstructionDTO;
import javax.validation.constraints.*;

/**
 * Contains credential and invocation information for APIs deployed on external gateways
 **/

import io.swagger.annotations.*;
import java.util.Objects;

import javax.xml.bind.annotation.*;
import org.wso2.carbon.apimgt.rest.api.common.annotations.Scope;
import com.fasterxml.jackson.annotation.JsonCreator;

import javax.validation.Valid;

@ApiModel(description = "Contains credential and invocation information for APIs deployed on external gateways")

public class FederatedCredentialInfoDTO   {
  
    private String credentialId = null;
    private FederatedCredentialDTO credential = null;
    private InvocationInstructionDTO invocationInstruction = null;

    @XmlType(name="GatewayTypeEnum")
    @XmlEnum(String.class)
    public enum GatewayTypeEnum {
        AWS("aws"),
        AZURE("azure"),
        KONG("kong"),
        ENVOY("envoy");
        private String value;

        GatewayTypeEnum (String v) {
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
        public static GatewayTypeEnum fromValue(String v) {
            for (GatewayTypeEnum b : GatewayTypeEnum.values()) {
                if (String.valueOf(b.value).equals(v)) {
                    return b;
                }
            }
return null;
        }
    }
    private GatewayTypeEnum gatewayType = null;
    private String gatewayEnvironmentId = null;

  /**
   * UUID of the local credential record (used to reference specific credentials for delete/retrieve)
   **/
  public FederatedCredentialInfoDTO credentialId(String credentialId) {
    this.credentialId = credentialId;
    return this;
  }

  
  @ApiModelProperty(example = "faae5fcc-cbae-40c4-bf43-89931571d250", value = "UUID of the local credential record (used to reference specific credentials for delete/retrieve)")
  @JsonProperty("credentialId")
  public String getCredentialId() {
    return credentialId;
  }
  public void setCredentialId(String credentialId) {
    this.credentialId = credentialId;
  }

  /**
   **/
  public FederatedCredentialInfoDTO credential(FederatedCredentialDTO credential) {
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
  public FederatedCredentialInfoDTO invocationInstruction(InvocationInstructionDTO invocationInstruction) {
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
  public FederatedCredentialInfoDTO gatewayType(GatewayTypeEnum gatewayType) {
    this.gatewayType = gatewayType;
    return this;
  }

  
  @ApiModelProperty(example = "aws", value = "Type of the external gateway")
  @JsonProperty("gatewayType")
  public GatewayTypeEnum getGatewayType() {
    return gatewayType;
  }
  public void setGatewayType(GatewayTypeEnum gatewayType) {
    this.gatewayType = gatewayType;
  }

  /**
   * UUID of the gateway environment
   **/
  public FederatedCredentialInfoDTO gatewayEnvironmentId(String gatewayEnvironmentId) {
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
    FederatedCredentialInfoDTO federatedCredentialInfo = (FederatedCredentialInfoDTO) o;
    return Objects.equals(credentialId, federatedCredentialInfo.credentialId) &&
        Objects.equals(credential, federatedCredentialInfo.credential) &&
        Objects.equals(invocationInstruction, federatedCredentialInfo.invocationInstruction) &&
        Objects.equals(gatewayType, federatedCredentialInfo.gatewayType) &&
        Objects.equals(gatewayEnvironmentId, federatedCredentialInfo.gatewayEnvironmentId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(credentialId, credential, invocationInstruction, gatewayType, gatewayEnvironmentId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class FederatedCredentialInfoDTO {\n");
    
    sb.append("    credentialId: ").append(toIndentedString(credentialId)).append("\n");
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

