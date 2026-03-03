package org.wso2.carbon.apimgt.rest.api.store.v1.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.*;

/**
 * API credential for accessing a federated API. The body field carries an opaque JSON string whose structure is defined by each gateway connector. 
 **/

import io.swagger.annotations.*;
import java.util.Objects;

import javax.xml.bind.annotation.*;
import org.wso2.carbon.apimgt.rest.api.common.annotations.Scope;
import com.fasterxml.jackson.annotation.JsonCreator;

import javax.validation.Valid;

@ApiModel(description = "API credential for accessing a federated API. The body field carries an opaque JSON string whose structure is defined by each gateway connector. ")

public class FederatedCredentialDTO   {
  
    private String schemaName = null;
    private String body = null;
    private String externalSubscriptionId = null;
    private Boolean isValueRetrievable = null;
    private Boolean masked = null;

  /**
   * Schema identifier for renderer selection (e.g., \&quot;opaque-api-key\&quot;, \&quot;primary-secondary-key-pair\&quot;)
   **/
  public FederatedCredentialDTO schemaName(String schemaName) {
    this.schemaName = schemaName;
    return this;
  }

  
  @ApiModelProperty(example = "opaque-api-key", value = "Schema identifier for renderer selection (e.g., \"opaque-api-key\", \"primary-secondary-key-pair\")")
  @JsonProperty("schemaName")
  public String getSchemaName() {
    return schemaName;
  }
  public void setSchemaName(String schemaName) {
    this.schemaName = schemaName;
  }

  /**
   * Opaque JSON string containing credential details. Structure is connector-specific. On create contains full credential value. On get contains masked value. 
   **/
  public FederatedCredentialDTO body(String body) {
    this.body = body;
    return this;
  }

  
  @ApiModelProperty(example = "{\"value\":\"abc123...\",\"headerName\":\"x-api-key\",\"createdTime\":\"2025-01-30T10:00:00Z\"}", value = "Opaque JSON string containing credential details. Structure is connector-specific. On create contains full credential value. On get contains masked value. ")
  @JsonProperty("body")
  public String getBody() {
    return body;
  }
  public void setBody(String body) {
    this.body = body;
  }

  /**
   * External gateway subscription identifier
   **/
  public FederatedCredentialDTO externalSubscriptionId(String externalSubscriptionId) {
    this.externalSubscriptionId = externalSubscriptionId;
    return this;
  }

  
  @ApiModelProperty(example = "wso2_abc123def456", value = "External gateway subscription identifier")
  @JsonProperty("externalSubscriptionId")
  public String getExternalSubscriptionId() {
    return externalSubscriptionId;
  }
  public void setExternalSubscriptionId(String externalSubscriptionId) {
    this.externalSubscriptionId = externalSubscriptionId;
  }

  /**
   * Whether the full credential can be retrieved from the gateway after creation
   **/
  public FederatedCredentialDTO isValueRetrievable(Boolean isValueRetrievable) {
    this.isValueRetrievable = isValueRetrievable;
    return this;
  }

  
  @ApiModelProperty(example = "false", value = "Whether the full credential can be retrieved from the gateway after creation")
  @JsonProperty("isValueRetrievable")
  public Boolean isIsValueRetrievable() {
    return isValueRetrievable;
  }
  public void setIsValueRetrievable(Boolean isValueRetrievable) {
    this.isValueRetrievable = isValueRetrievable;
  }

  /**
   * Whether the credential value in body is masked
   **/
  public FederatedCredentialDTO masked(Boolean masked) {
    this.masked = masked;
    return this;
  }

  
  @ApiModelProperty(example = "true", value = "Whether the credential value in body is masked")
  @JsonProperty("masked")
  public Boolean isMasked() {
    return masked;
  }
  public void setMasked(Boolean masked) {
    this.masked = masked;
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
    return Objects.equals(schemaName, federatedCredential.schemaName) &&
        Objects.equals(body, federatedCredential.body) &&
        Objects.equals(externalSubscriptionId, federatedCredential.externalSubscriptionId) &&
        Objects.equals(isValueRetrievable, federatedCredential.isValueRetrievable) &&
        Objects.equals(masked, federatedCredential.masked);
  }

  @Override
  public int hashCode() {
    return Objects.hash(schemaName, body, externalSubscriptionId, isValueRetrievable, masked);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class FederatedCredentialDTO {\n");
    
    sb.append("    schemaName: ").append(toIndentedString(schemaName)).append("\n");
    sb.append("    body: ").append(toIndentedString(body)).append("\n");
    sb.append("    externalSubscriptionId: ").append(toIndentedString(externalSubscriptionId)).append("\n");
    sb.append("    isValueRetrievable: ").append(toIndentedString(isValueRetrievable)).append("\n");
    sb.append("    masked: ").append(toIndentedString(masked)).append("\n");
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

