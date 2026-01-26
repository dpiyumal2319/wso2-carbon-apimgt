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



public class DiscoveredAPISubscriptionDTO   {
  
    private String apiId = null;
    private String apiName = null;
    private String apiVersion = null;
    private String apiContext = null;
    private String subscriptionTier = null;
    private String subscriptionStatus = null;

  /**
   * External API identifier from the gateway
   **/
  public DiscoveredAPISubscriptionDTO apiId(String apiId) {
    this.apiId = apiId;
    return this;
  }

  
  @ApiModelProperty(value = "External API identifier from the gateway")
  @JsonProperty("apiId")
  public String getApiId() {
    return apiId;
  }
  public void setApiId(String apiId) {
    this.apiId = apiId;
  }

  /**
   * API name
   **/
  public DiscoveredAPISubscriptionDTO apiName(String apiName) {
    this.apiName = apiName;
    return this;
  }

  
  @ApiModelProperty(value = "API name")
  @JsonProperty("apiName")
  public String getApiName() {
    return apiName;
  }
  public void setApiName(String apiName) {
    this.apiName = apiName;
  }

  /**
   * API version
   **/
  public DiscoveredAPISubscriptionDTO apiVersion(String apiVersion) {
    this.apiVersion = apiVersion;
    return this;
  }

  
  @ApiModelProperty(value = "API version")
  @JsonProperty("apiVersion")
  public String getApiVersion() {
    return apiVersion;
  }
  public void setApiVersion(String apiVersion) {
    this.apiVersion = apiVersion;
  }

  /**
   * API context path
   **/
  public DiscoveredAPISubscriptionDTO apiContext(String apiContext) {
    this.apiContext = apiContext;
    return this;
  }

  
  @ApiModelProperty(value = "API context path")
  @JsonProperty("apiContext")
  public String getApiContext() {
    return apiContext;
  }
  public void setApiContext(String apiContext) {
    this.apiContext = apiContext;
  }

  /**
   * Subscription tier/throttling policy
   **/
  public DiscoveredAPISubscriptionDTO subscriptionTier(String subscriptionTier) {
    this.subscriptionTier = subscriptionTier;
    return this;
  }

  
  @ApiModelProperty(value = "Subscription tier/throttling policy")
  @JsonProperty("subscriptionTier")
  public String getSubscriptionTier() {
    return subscriptionTier;
  }
  public void setSubscriptionTier(String subscriptionTier) {
    this.subscriptionTier = subscriptionTier;
  }

  /**
   * Subscription status (e.g., ACTIVE, BLOCKED)
   **/
  public DiscoveredAPISubscriptionDTO subscriptionStatus(String subscriptionStatus) {
    this.subscriptionStatus = subscriptionStatus;
    return this;
  }

  
  @ApiModelProperty(value = "Subscription status (e.g., ACTIVE, BLOCKED)")
  @JsonProperty("subscriptionStatus")
  public String getSubscriptionStatus() {
    return subscriptionStatus;
  }
  public void setSubscriptionStatus(String subscriptionStatus) {
    this.subscriptionStatus = subscriptionStatus;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DiscoveredAPISubscriptionDTO discoveredAPISubscription = (DiscoveredAPISubscriptionDTO) o;
    return Objects.equals(apiId, discoveredAPISubscription.apiId) &&
        Objects.equals(apiName, discoveredAPISubscription.apiName) &&
        Objects.equals(apiVersion, discoveredAPISubscription.apiVersion) &&
        Objects.equals(apiContext, discoveredAPISubscription.apiContext) &&
        Objects.equals(subscriptionTier, discoveredAPISubscription.subscriptionTier) &&
        Objects.equals(subscriptionStatus, discoveredAPISubscription.subscriptionStatus);
  }

  @Override
  public int hashCode() {
    return Objects.hash(apiId, apiName, apiVersion, apiContext, subscriptionTier, subscriptionStatus);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DiscoveredAPISubscriptionDTO {\n");
    
    sb.append("    apiId: ").append(toIndentedString(apiId)).append("\n");
    sb.append("    apiName: ").append(toIndentedString(apiName)).append("\n");
    sb.append("    apiVersion: ").append(toIndentedString(apiVersion)).append("\n");
    sb.append("    apiContext: ").append(toIndentedString(apiContext)).append("\n");
    sb.append("    subscriptionTier: ").append(toIndentedString(subscriptionTier)).append("\n");
    sb.append("    subscriptionStatus: ").append(toIndentedString(subscriptionStatus)).append("\n");
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

