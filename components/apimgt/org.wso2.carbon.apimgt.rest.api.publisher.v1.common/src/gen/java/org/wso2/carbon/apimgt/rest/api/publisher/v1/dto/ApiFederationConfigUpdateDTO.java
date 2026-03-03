package org.wso2.carbon.apimgt.rest.api.publisher.v1.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.*;

/**
 * Request body for updating publisher curation of federation configuration. 
 **/

import io.swagger.annotations.*;
import java.util.Objects;

import javax.xml.bind.annotation.*;
import org.wso2.carbon.apimgt.rest.api.common.annotations.Scope;
import com.fasterxml.jackson.annotation.JsonCreator;

import javax.validation.Valid;

@ApiModel(description = "Request body for updating publisher curation of federation configuration. ")

public class ApiFederationConfigUpdateDTO   {
  
    private Boolean subscriptionEnabled = null;
    private String curatedPlanSelections = null;

  /**
   * Whether federation should be enabled for this API
   **/
  public ApiFederationConfigUpdateDTO subscriptionEnabled(Boolean subscriptionEnabled) {
    this.subscriptionEnabled = subscriptionEnabled;
    return this;
  }

  
  @ApiModelProperty(value = "Whether federation should be enabled for this API")
  @JsonProperty("subscriptionEnabled")
  public Boolean isSubscriptionEnabled() {
    return subscriptionEnabled;
  }
  public void setSubscriptionEnabled(Boolean subscriptionEnabled) {
    this.subscriptionEnabled = subscriptionEnabled;
  }

  /**
   * Opaque JSON string containing schema-specific curation selections. The format depends on the subscription options schema (subscription-plans, option-groups, etc.) and is parsed by the schema&#39;s applyCuration() method. Passed through to the schema body without parsing at the controller layer. 
   **/
  public ApiFederationConfigUpdateDTO curatedPlanSelections(String curatedPlanSelections) {
    this.curatedPlanSelections = curatedPlanSelections;
    return this;
  }

  
  @ApiModelProperty(value = "Opaque JSON string containing schema-specific curation selections. The format depends on the subscription options schema (subscription-plans, option-groups, etc.) and is parsed by the schema's applyCuration() method. Passed through to the schema body without parsing at the controller layer. ")
  @JsonProperty("curatedPlanSelections")
  public String getCuratedPlanSelections() {
    return curatedPlanSelections;
  }
  public void setCuratedPlanSelections(String curatedPlanSelections) {
    this.curatedPlanSelections = curatedPlanSelections;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ApiFederationConfigUpdateDTO apiFederationConfigUpdate = (ApiFederationConfigUpdateDTO) o;
    return Objects.equals(subscriptionEnabled, apiFederationConfigUpdate.subscriptionEnabled) &&
        Objects.equals(curatedPlanSelections, apiFederationConfigUpdate.curatedPlanSelections);
  }

  @Override
  public int hashCode() {
    return Objects.hash(subscriptionEnabled, curatedPlanSelections);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ApiFederationConfigUpdateDTO {\n");
    
    sb.append("    subscriptionEnabled: ").append(toIndentedString(subscriptionEnabled)).append("\n");
    sb.append("    curatedPlanSelections: ").append(toIndentedString(curatedPlanSelections)).append("\n");
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

