package org.wso2.carbon.apimgt.rest.api.store.v1.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.*;


import io.swagger.annotations.*;
import java.util.Objects;

import javax.xml.bind.annotation.*;
import org.wso2.carbon.apimgt.rest.api.common.annotations.Scope;
import com.fasterxml.jackson.annotation.JsonCreator;

import javax.validation.Valid;



public class ExternalGatewayEnvironmentDTO   {
  
    private String id = null;
    private String name = null;
    private String displayName = null;
    private String type = null;
    private String gatewayType = null;
    private String description = null;

  /**
   * UUID of the gateway environment
   **/
  public ExternalGatewayEnvironmentDTO id(String id) {
    this.id = id;
    return this;
  }

  
  @ApiModelProperty(example = "ece92bdc-e1e6-325c-b6f4-656208a041e9", value = "UUID of the gateway environment")
  @JsonProperty("id")
  public String getId() {
    return id;
  }
  public void setId(String id) {
    this.id = id;
  }

  /**
   * Name of the gateway environment
   **/
  public ExternalGatewayEnvironmentDTO name(String name) {
    this.name = name;
    return this;
  }

  
  @ApiModelProperty(example = "us-region", value = "Name of the gateway environment")
  @JsonProperty("name")
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Display name of the gateway environment
   **/
  public ExternalGatewayEnvironmentDTO displayName(String displayName) {
    this.displayName = displayName;
    return this;
  }

  
  @ApiModelProperty(example = "US Region", value = "Display name of the gateway environment")
  @JsonProperty("displayName")
  public String getDisplayName() {
    return displayName;
  }
  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }

  /**
   * Environment type (e.g., hybrid, production, sandbox)
   **/
  public ExternalGatewayEnvironmentDTO type(String type) {
    this.type = type;
    return this;
  }

  
  @ApiModelProperty(example = "hybrid", value = "Environment type (e.g., hybrid, production, sandbox)")
  @JsonProperty("type")
  public String getType() {
    return type;
  }
  public void setType(String type) {
    this.type = type;
  }

  /**
   * Type of gateway (e.g., Regular, APK)
   **/
  public ExternalGatewayEnvironmentDTO gatewayType(String gatewayType) {
    this.gatewayType = gatewayType;
    return this;
  }

  
  @ApiModelProperty(example = "APK", value = "Type of gateway (e.g., Regular, APK)")
  @JsonProperty("gatewayType")
  public String getGatewayType() {
    return gatewayType;
  }
  public void setGatewayType(String gatewayType) {
    this.gatewayType = gatewayType;
  }

  /**
   * Description of the environment
   **/
  public ExternalGatewayEnvironmentDTO description(String description) {
    this.description = description;
    return this;
  }

  
  @ApiModelProperty(example = "Gateway environment in US Region", value = "Description of the environment")
  @JsonProperty("description")
  public String getDescription() {
    return description;
  }
  public void setDescription(String description) {
    this.description = description;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ExternalGatewayEnvironmentDTO externalGatewayEnvironment = (ExternalGatewayEnvironmentDTO) o;
    return Objects.equals(id, externalGatewayEnvironment.id) &&
        Objects.equals(name, externalGatewayEnvironment.name) &&
        Objects.equals(displayName, externalGatewayEnvironment.displayName) &&
        Objects.equals(type, externalGatewayEnvironment.type) &&
        Objects.equals(gatewayType, externalGatewayEnvironment.gatewayType) &&
        Objects.equals(description, externalGatewayEnvironment.description);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, displayName, type, gatewayType, description);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ExternalGatewayEnvironmentDTO {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    displayName: ").append(toIndentedString(displayName)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    gatewayType: ").append(toIndentedString(gatewayType)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
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

