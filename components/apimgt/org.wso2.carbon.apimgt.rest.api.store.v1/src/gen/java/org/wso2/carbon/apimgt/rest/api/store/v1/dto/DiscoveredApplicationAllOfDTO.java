package org.wso2.carbon.apimgt.rest.api.store.v1.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import org.wso2.carbon.apimgt.rest.api.store.v1.dto.DiscoveredAPISubscriptionDTO;
import org.wso2.carbon.apimgt.rest.api.store.v1.dto.DiscoveredApplicationKeyInfoDTO;
import javax.validation.constraints.*;


import io.swagger.annotations.*;
import java.util.Objects;

import javax.xml.bind.annotation.*;
import org.wso2.carbon.apimgt.rest.api.common.annotations.Scope;
import com.fasterxml.jackson.annotation.JsonCreator;

import javax.validation.Valid;



public class DiscoveredApplicationAllOfDTO   {
  
    private List<DiscoveredApplicationKeyInfoDTO> keyInfoList = new ArrayList<DiscoveredApplicationKeyInfoDTO>();
    private List<DiscoveredAPISubscriptionDTO> subscribedApis = new ArrayList<DiscoveredAPISubscriptionDTO>();

  /**
   * List of application keys (masked)
   **/
  public DiscoveredApplicationAllOfDTO keyInfoList(List<DiscoveredApplicationKeyInfoDTO> keyInfoList) {
    this.keyInfoList = keyInfoList;
    return this;
  }

  
  @ApiModelProperty(value = "List of application keys (masked)")
      @Valid
  @JsonProperty("keyInfoList")
  public List<DiscoveredApplicationKeyInfoDTO> getKeyInfoList() {
    return keyInfoList;
  }
  public void setKeyInfoList(List<DiscoveredApplicationKeyInfoDTO> keyInfoList) {
    this.keyInfoList = keyInfoList;
  }

  /**
   * List of API subscriptions in the external gateway
   **/
  public DiscoveredApplicationAllOfDTO subscribedApis(List<DiscoveredAPISubscriptionDTO> subscribedApis) {
    this.subscribedApis = subscribedApis;
    return this;
  }

  
  @ApiModelProperty(value = "List of API subscriptions in the external gateway")
      @Valid
  @JsonProperty("subscribedApis")
  public List<DiscoveredAPISubscriptionDTO> getSubscribedApis() {
    return subscribedApis;
  }
  public void setSubscribedApis(List<DiscoveredAPISubscriptionDTO> subscribedApis) {
    this.subscribedApis = subscribedApis;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DiscoveredApplicationAllOfDTO discoveredApplicationAllOf = (DiscoveredApplicationAllOfDTO) o;
    return Objects.equals(keyInfoList, discoveredApplicationAllOf.keyInfoList) &&
        Objects.equals(subscribedApis, discoveredApplicationAllOf.subscribedApis);
  }

  @Override
  public int hashCode() {
    return Objects.hash(keyInfoList, subscribedApis);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DiscoveredApplicationAllOfDTO {\n");
    
    sb.append("    keyInfoList: ").append(toIndentedString(keyInfoList)).append("\n");
    sb.append("    subscribedApis: ").append(toIndentedString(subscribedApis)).append("\n");
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

