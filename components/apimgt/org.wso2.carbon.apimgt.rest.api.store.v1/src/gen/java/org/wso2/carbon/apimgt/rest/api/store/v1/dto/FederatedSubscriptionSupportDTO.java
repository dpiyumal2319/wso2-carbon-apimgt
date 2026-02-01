package org.wso2.carbon.apimgt.rest.api.store.v1.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.*;

/**
 * Federated subscription capabilities for a specific gateway environment. Contains credential and invocation schemas supported by the gateway. 
 **/

import io.swagger.annotations.*;
import java.util.Objects;

import javax.xml.bind.annotation.*;
import org.wso2.carbon.apimgt.rest.api.common.annotations.Scope;
import com.fasterxml.jackson.annotation.JsonCreator;

import javax.validation.Valid;

@ApiModel(description = "Federated subscription capabilities for a specific gateway environment. Contains credential and invocation schemas supported by the gateway. ")

public class FederatedSubscriptionSupportDTO   {
  
    private String environmentId = null;
    private String environmentName = null;
    private String gatewayType = null;
    private Boolean supported = null;
    private List<String> credentialSchemas = new ArrayList<String>();
    private List<String> invocationSchemas = new ArrayList<String>();

  /**
   * UUID of the gateway environment
   **/
  public FederatedSubscriptionSupportDTO environmentId(String environmentId) {
    this.environmentId = environmentId;
    return this;
  }

  
  @ApiModelProperty(example = "ece92bdc-e1e6-325c-b6f4-656208a041e9", value = "UUID of the gateway environment")
  @JsonProperty("environmentId")
  public String getEnvironmentId() {
    return environmentId;
  }
  public void setEnvironmentId(String environmentId) {
    this.environmentId = environmentId;
  }

  /**
   * Name of the gateway environment
   **/
  public FederatedSubscriptionSupportDTO environmentName(String environmentName) {
    this.environmentName = environmentName;
    return this;
  }

  
  @ApiModelProperty(example = "us-region", value = "Name of the gateway environment")
  @JsonProperty("environmentName")
  public String getEnvironmentName() {
    return environmentName;
  }
  public void setEnvironmentName(String environmentName) {
    this.environmentName = environmentName;
  }

  /**
   * Type of gateway (e.g., AWS, Azure, Kong, Envoy)
   **/
  public FederatedSubscriptionSupportDTO gatewayType(String gatewayType) {
    this.gatewayType = gatewayType;
    return this;
  }

  
  @ApiModelProperty(example = "AWS", value = "Type of gateway (e.g., AWS, Azure, Kong, Envoy)")
  @JsonProperty("gatewayType")
  public String getGatewayType() {
    return gatewayType;
  }
  public void setGatewayType(String gatewayType) {
    this.gatewayType = gatewayType;
  }

  /**
   * Whether this gateway supports federated subscriptions
   **/
  public FederatedSubscriptionSupportDTO supported(Boolean supported) {
    this.supported = supported;
    return this;
  }

  
  @ApiModelProperty(example = "true", value = "Whether this gateway supports federated subscriptions")
  @JsonProperty("supported")
  public Boolean isSupported() {
    return supported;
  }
  public void setSupported(Boolean supported) {
    this.supported = supported;
  }

  /**
   * Credential types supported by this gateway
   **/
  public FederatedSubscriptionSupportDTO credentialSchemas(List<String> credentialSchemas) {
    this.credentialSchemas = credentialSchemas;
    return this;
  }

  
  @ApiModelProperty(example = "[\"opaque-api-key\"]", value = "Credential types supported by this gateway")
  @JsonProperty("credentialSchemas")
  public List<String> getCredentialSchemas() {
    return credentialSchemas;
  }
  public void setCredentialSchemas(List<String> credentialSchemas) {
    this.credentialSchemas = credentialSchemas;
  }

  /**
   * Invocation methods supported by this gateway
   **/
  public FederatedSubscriptionSupportDTO invocationSchemas(List<String> invocationSchemas) {
    this.invocationSchemas = invocationSchemas;
    return this;
  }

  
  @ApiModelProperty(example = "[\"header-based\"]", value = "Invocation methods supported by this gateway")
  @JsonProperty("invocationSchemas")
  public List<String> getInvocationSchemas() {
    return invocationSchemas;
  }
  public void setInvocationSchemas(List<String> invocationSchemas) {
    this.invocationSchemas = invocationSchemas;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    FederatedSubscriptionSupportDTO federatedSubscriptionSupport = (FederatedSubscriptionSupportDTO) o;
    return Objects.equals(environmentId, federatedSubscriptionSupport.environmentId) &&
        Objects.equals(environmentName, federatedSubscriptionSupport.environmentName) &&
        Objects.equals(gatewayType, federatedSubscriptionSupport.gatewayType) &&
        Objects.equals(supported, federatedSubscriptionSupport.supported) &&
        Objects.equals(credentialSchemas, federatedSubscriptionSupport.credentialSchemas) &&
        Objects.equals(invocationSchemas, federatedSubscriptionSupport.invocationSchemas);
  }

  @Override
  public int hashCode() {
    return Objects.hash(environmentId, environmentName, gatewayType, supported, credentialSchemas, invocationSchemas);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class FederatedSubscriptionSupportDTO {\n");
    
    sb.append("    environmentId: ").append(toIndentedString(environmentId)).append("\n");
    sb.append("    environmentName: ").append(toIndentedString(environmentName)).append("\n");
    sb.append("    gatewayType: ").append(toIndentedString(gatewayType)).append("\n");
    sb.append("    supported: ").append(toIndentedString(supported)).append("\n");
    sb.append("    credentialSchemas: ").append(toIndentedString(credentialSchemas)).append("\n");
    sb.append("    invocationSchemas: ").append(toIndentedString(invocationSchemas)).append("\n");
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

