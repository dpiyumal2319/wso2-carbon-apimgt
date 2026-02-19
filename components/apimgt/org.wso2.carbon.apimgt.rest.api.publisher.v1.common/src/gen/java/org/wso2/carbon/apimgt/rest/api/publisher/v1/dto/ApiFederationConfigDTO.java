package org.wso2.carbon.apimgt.rest.api.publisher.v1.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.wso2.carbon.apimgt.rest.api.publisher.v1.dto.FederatedSubscriptionOptionsDTO;
import org.wso2.carbon.apimgt.rest.api.publisher.v1.dto.GatewaySupportSnapshotDTO;
import javax.validation.constraints.*;

/**
 * Federation configuration for an externally-discovered API. Contains the live gateway snapshot, publisher curation, and staleness status. 
 **/

import io.swagger.annotations.*;
import java.util.Objects;

import javax.xml.bind.annotation.*;
import org.wso2.carbon.apimgt.rest.api.common.annotations.Scope;
import com.fasterxml.jackson.annotation.JsonCreator;

import javax.validation.Valid;

@ApiModel(description = "Federation configuration for an externally-discovered API. Contains the live gateway snapshot, publisher curation, and staleness status. ")

public class ApiFederationConfigDTO   {
  
    private Boolean federationEnabled = null;
    private GatewaySupportSnapshotDTO gatewaySupportSnapshot = null;
    private FederatedSubscriptionOptionsDTO publisherCuratedOptions = null;
    private Boolean isStale = null;
    private String gatewaySnapshotHash = null;
    private String liveSnapshotHash = null;
    private String publisherReviewedTime = null;

  /**
   * Whether federation is enabled for this API
   **/
  public ApiFederationConfigDTO federationEnabled(Boolean federationEnabled) {
    this.federationEnabled = federationEnabled;
    return this;
  }

  
  @ApiModelProperty(value = "Whether federation is enabled for this API")
  @JsonProperty("federationEnabled")
  public Boolean isFederationEnabled() {
    return federationEnabled;
  }
  public void setFederationEnabled(Boolean federationEnabled) {
    this.federationEnabled = federationEnabled;
  }

  /**
   **/
  public ApiFederationConfigDTO gatewaySupportSnapshot(GatewaySupportSnapshotDTO gatewaySupportSnapshot) {
    this.gatewaySupportSnapshot = gatewaySupportSnapshot;
    return this;
  }

  
  @ApiModelProperty(value = "")
      @Valid
  @JsonProperty("gatewaySupportSnapshot")
  public GatewaySupportSnapshotDTO getGatewaySupportSnapshot() {
    return gatewaySupportSnapshot;
  }
  public void setGatewaySupportSnapshot(GatewaySupportSnapshotDTO gatewaySupportSnapshot) {
    this.gatewaySupportSnapshot = gatewaySupportSnapshot;
  }

  /**
   **/
  public ApiFederationConfigDTO publisherCuratedOptions(FederatedSubscriptionOptionsDTO publisherCuratedOptions) {
    this.publisherCuratedOptions = publisherCuratedOptions;
    return this;
  }

  
  @ApiModelProperty(value = "")
      @Valid
  @JsonProperty("publisherCuratedOptions")
  public FederatedSubscriptionOptionsDTO getPublisherCuratedOptions() {
    return publisherCuratedOptions;
  }
  public void setPublisherCuratedOptions(FederatedSubscriptionOptionsDTO publisherCuratedOptions) {
    this.publisherCuratedOptions = publisherCuratedOptions;
  }

  /**
   * Whether the live gateway snapshot differs from the last acknowledged snapshot
   **/
  public ApiFederationConfigDTO isStale(Boolean isStale) {
    this.isStale = isStale;
    return this;
  }

  
  @ApiModelProperty(value = "Whether the live gateway snapshot differs from the last acknowledged snapshot")
  @JsonProperty("isStale")
  public Boolean isIsStale() {
    return isStale;
  }
  public void setIsStale(Boolean isStale) {
    this.isStale = isStale;
  }

  /**
   * SHA-256 hash of the last acknowledged gateway snapshot
   **/
  public ApiFederationConfigDTO gatewaySnapshotHash(String gatewaySnapshotHash) {
    this.gatewaySnapshotHash = gatewaySnapshotHash;
    return this;
  }

  
  @ApiModelProperty(value = "SHA-256 hash of the last acknowledged gateway snapshot")
  @JsonProperty("gatewaySnapshotHash")
  public String getGatewaySnapshotHash() {
    return gatewaySnapshotHash;
  }
  public void setGatewaySnapshotHash(String gatewaySnapshotHash) {
    this.gatewaySnapshotHash = gatewaySnapshotHash;
  }

  /**
   * SHA-256 hash of the current live gateway snapshot
   **/
  public ApiFederationConfigDTO liveSnapshotHash(String liveSnapshotHash) {
    this.liveSnapshotHash = liveSnapshotHash;
    return this;
  }

  
  @ApiModelProperty(value = "SHA-256 hash of the current live gateway snapshot")
  @JsonProperty("liveSnapshotHash")
  public String getLiveSnapshotHash() {
    return liveSnapshotHash;
  }
  public void setLiveSnapshotHash(String liveSnapshotHash) {
    this.liveSnapshotHash = liveSnapshotHash;
  }

  /**
   * Timestamp when the publisher last reviewed the configuration
   **/
  public ApiFederationConfigDTO publisherReviewedTime(String publisherReviewedTime) {
    this.publisherReviewedTime = publisherReviewedTime;
    return this;
  }

  
  @ApiModelProperty(value = "Timestamp when the publisher last reviewed the configuration")
  @JsonProperty("publisherReviewedTime")
  public String getPublisherReviewedTime() {
    return publisherReviewedTime;
  }
  public void setPublisherReviewedTime(String publisherReviewedTime) {
    this.publisherReviewedTime = publisherReviewedTime;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ApiFederationConfigDTO apiFederationConfig = (ApiFederationConfigDTO) o;
    return Objects.equals(federationEnabled, apiFederationConfig.federationEnabled) &&
        Objects.equals(gatewaySupportSnapshot, apiFederationConfig.gatewaySupportSnapshot) &&
        Objects.equals(publisherCuratedOptions, apiFederationConfig.publisherCuratedOptions) &&
        Objects.equals(isStale, apiFederationConfig.isStale) &&
        Objects.equals(gatewaySnapshotHash, apiFederationConfig.gatewaySnapshotHash) &&
        Objects.equals(liveSnapshotHash, apiFederationConfig.liveSnapshotHash) &&
        Objects.equals(publisherReviewedTime, apiFederationConfig.publisherReviewedTime);
  }

  @Override
  public int hashCode() {
    return Objects.hash(federationEnabled, gatewaySupportSnapshot, publisherCuratedOptions, isStale, gatewaySnapshotHash, liveSnapshotHash, publisherReviewedTime);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ApiFederationConfigDTO {\n");
    
    sb.append("    federationEnabled: ").append(toIndentedString(federationEnabled)).append("\n");
    sb.append("    gatewaySupportSnapshot: ").append(toIndentedString(gatewaySupportSnapshot)).append("\n");
    sb.append("    publisherCuratedOptions: ").append(toIndentedString(publisherCuratedOptions)).append("\n");
    sb.append("    isStale: ").append(toIndentedString(isStale)).append("\n");
    sb.append("    gatewaySnapshotHash: ").append(toIndentedString(gatewaySnapshotHash)).append("\n");
    sb.append("    liveSnapshotHash: ").append(toIndentedString(liveSnapshotHash)).append("\n");
    sb.append("    publisherReviewedTime: ").append(toIndentedString(publisherReviewedTime)).append("\n");
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

