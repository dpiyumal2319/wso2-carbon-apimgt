package org.wso2.carbon.apimgt.rest.api.store.v1.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.wso2.carbon.apimgt.rest.api.store.v1.dto.DiscoveredApplicationKeyInfoDTO;
import javax.validation.constraints.*;


import io.swagger.annotations.*;
import java.util.Objects;

import javax.xml.bind.annotation.*;
import org.wso2.carbon.apimgt.rest.api.common.annotations.Scope;
import com.fasterxml.jackson.annotation.JsonCreator;

import javax.validation.Valid;



public class DiscoveredApplicationDTO   {
  
    private String externalId = null;
    private String name = null;
    private String description = null;
    private String tier = null;
    private String owner = null;
    private String createdTime = null;
    private Map<String, String> attributes = new HashMap<String, String>();
    private List<DiscoveredApplicationKeyInfoDTO> keyInfoList = new ArrayList<DiscoveredApplicationKeyInfoDTO>();
    private Boolean alreadyImported = null;
    private String importedApplicationId = null;

  /**
   * Unique identifier from the external gateway
   **/
  public DiscoveredApplicationDTO externalId(String externalId) {
    this.externalId = externalId;
    return this;
  }

  
  @ApiModelProperty(value = "Unique identifier from the external gateway")
  @JsonProperty("externalId")
  public String getExternalId() {
    return externalId;
  }
  public void setExternalId(String externalId) {
    this.externalId = externalId;
  }

  /**
   * Name of the application
   **/
  public DiscoveredApplicationDTO name(String name) {
    this.name = name;
    return this;
  }

  
  @ApiModelProperty(value = "Name of the application")
  @JsonProperty("name")
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Description of the application
   **/
  public DiscoveredApplicationDTO description(String description) {
    this.description = description;
    return this;
  }

  
  @ApiModelProperty(value = "Description of the application")
  @JsonProperty("description")
  public String getDescription() {
    return description;
  }
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * Throttling policy/tier
   **/
  public DiscoveredApplicationDTO tier(String tier) {
    this.tier = tier;
    return this;
  }

  
  @ApiModelProperty(value = "Throttling policy/tier")
  @JsonProperty("tier")
  public String getTier() {
    return tier;
  }
  public void setTier(String tier) {
    this.tier = tier;
  }

  /**
   * Owner of the application
   **/
  public DiscoveredApplicationDTO owner(String owner) {
    this.owner = owner;
    return this;
  }

  
  @ApiModelProperty(value = "Owner of the application")
  @JsonProperty("owner")
  public String getOwner() {
    return owner;
  }
  public void setOwner(String owner) {
    this.owner = owner;
  }

  /**
   * Timestamp when the application was created
   **/
  public DiscoveredApplicationDTO createdTime(String createdTime) {
    this.createdTime = createdTime;
    return this;
  }

  
  @ApiModelProperty(value = "Timestamp when the application was created")
  @JsonProperty("createdTime")
  public String getCreatedTime() {
    return createdTime;
  }
  public void setCreatedTime(String createdTime) {
    this.createdTime = createdTime;
  }

  /**
   * Additional attributes of the application
   **/
  public DiscoveredApplicationDTO attributes(Map<String, String> attributes) {
    this.attributes = attributes;
    return this;
  }

  
  @ApiModelProperty(value = "Additional attributes of the application")
  @JsonProperty("attributes")
  public Map<String, String> getAttributes() {
    return attributes;
  }
  public void setAttributes(Map<String, String> attributes) {
    this.attributes = attributes;
  }

  /**
   **/
  public DiscoveredApplicationDTO keyInfoList(List<DiscoveredApplicationKeyInfoDTO> keyInfoList) {
    this.keyInfoList = keyInfoList;
    return this;
  }

  
  @ApiModelProperty(value = "")
      @Valid
  @JsonProperty("keyInfoList")
  public List<DiscoveredApplicationKeyInfoDTO> getKeyInfoList() {
    return keyInfoList;
  }
  public void setKeyInfoList(List<DiscoveredApplicationKeyInfoDTO> keyInfoList) {
    this.keyInfoList = keyInfoList;
  }

  /**
   * Indicates if the application is already imported to WSO2
   **/
  public DiscoveredApplicationDTO alreadyImported(Boolean alreadyImported) {
    this.alreadyImported = alreadyImported;
    return this;
  }

  
  @ApiModelProperty(value = "Indicates if the application is already imported to WSO2")
  @JsonProperty("alreadyImported")
  public Boolean isAlreadyImported() {
    return alreadyImported;
  }
  public void setAlreadyImported(Boolean alreadyImported) {
    this.alreadyImported = alreadyImported;
  }

  /**
   * UUID of the imported application if already imported
   **/
  public DiscoveredApplicationDTO importedApplicationId(String importedApplicationId) {
    this.importedApplicationId = importedApplicationId;
    return this;
  }

  
  @ApiModelProperty(value = "UUID of the imported application if already imported")
  @JsonProperty("importedApplicationId")
  public String getImportedApplicationId() {
    return importedApplicationId;
  }
  public void setImportedApplicationId(String importedApplicationId) {
    this.importedApplicationId = importedApplicationId;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DiscoveredApplicationDTO discoveredApplication = (DiscoveredApplicationDTO) o;
    return Objects.equals(externalId, discoveredApplication.externalId) &&
        Objects.equals(name, discoveredApplication.name) &&
        Objects.equals(description, discoveredApplication.description) &&
        Objects.equals(tier, discoveredApplication.tier) &&
        Objects.equals(owner, discoveredApplication.owner) &&
        Objects.equals(createdTime, discoveredApplication.createdTime) &&
        Objects.equals(attributes, discoveredApplication.attributes) &&
        Objects.equals(keyInfoList, discoveredApplication.keyInfoList) &&
        Objects.equals(alreadyImported, discoveredApplication.alreadyImported) &&
        Objects.equals(importedApplicationId, discoveredApplication.importedApplicationId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(externalId, name, description, tier, owner, createdTime, attributes, keyInfoList, alreadyImported, importedApplicationId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DiscoveredApplicationDTO {\n");
    
    sb.append("    externalId: ").append(toIndentedString(externalId)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    tier: ").append(toIndentedString(tier)).append("\n");
    sb.append("    owner: ").append(toIndentedString(owner)).append("\n");
    sb.append("    createdTime: ").append(toIndentedString(createdTime)).append("\n");
    sb.append("    attributes: ").append(toIndentedString(attributes)).append("\n");
    sb.append("    keyInfoList: ").append(toIndentedString(keyInfoList)).append("\n");
    sb.append("    alreadyImported: ").append(toIndentedString(alreadyImported)).append("\n");
    sb.append("    importedApplicationId: ").append(toIndentedString(importedApplicationId)).append("\n");
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

