package org.wso2.carbon.apimgt.rest.api.publisher.v1.dto;

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



public class ApiFederationConfigUpdateCuratedPlanSelectionsDTO   {
  
    private String planId = null;
    private Boolean enabled = null;

  /**
   * Gateway-specific plan identifier
   **/
  public ApiFederationConfigUpdateCuratedPlanSelectionsDTO planId(String planId) {
    this.planId = planId;
    return this;
  }

  
  @ApiModelProperty(value = "Gateway-specific plan identifier")
  @JsonProperty("planId")
  public String getPlanId() {
    return planId;
  }
  public void setPlanId(String planId) {
    this.planId = planId;
  }

  /**
   * Whether the plan is enabled for developers
   **/
  public ApiFederationConfigUpdateCuratedPlanSelectionsDTO enabled(Boolean enabled) {
    this.enabled = enabled;
    return this;
  }

  
  @ApiModelProperty(value = "Whether the plan is enabled for developers")
  @JsonProperty("enabled")
  public Boolean isEnabled() {
    return enabled;
  }
  public void setEnabled(Boolean enabled) {
    this.enabled = enabled;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ApiFederationConfigUpdateCuratedPlanSelectionsDTO apiFederationConfigUpdateCuratedPlanSelections = (ApiFederationConfigUpdateCuratedPlanSelectionsDTO) o;
    return Objects.equals(planId, apiFederationConfigUpdateCuratedPlanSelections.planId) &&
        Objects.equals(enabled, apiFederationConfigUpdateCuratedPlanSelections.enabled);
  }

  @Override
  public int hashCode() {
    return Objects.hash(planId, enabled);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ApiFederationConfigUpdateCuratedPlanSelectionsDTO {\n");
    
    sb.append("    planId: ").append(toIndentedString(planId)).append("\n");
    sb.append("    enabled: ").append(toIndentedString(enabled)).append("\n");
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

