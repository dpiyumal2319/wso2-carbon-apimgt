package org.wso2.carbon.apimgt.rest.api.publisher.v1.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import org.wso2.carbon.apimgt.rest.api.publisher.v1.dto.ApiFederationConfigUpdateCuratedPlanSelectionsDTO;
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
  
    private Boolean federationEnabled = null;
    private List<ApiFederationConfigUpdateCuratedPlanSelectionsDTO> curatedPlanSelections = new ArrayList<ApiFederationConfigUpdateCuratedPlanSelectionsDTO>();

  /**
   * Whether federation should be enabled for this API
   **/
  public ApiFederationConfigUpdateDTO federationEnabled(Boolean federationEnabled) {
    this.federationEnabled = federationEnabled;
    return this;
  }

  
  @ApiModelProperty(value = "Whether federation should be enabled for this API")
  @JsonProperty("federationEnabled")
  public Boolean isFederationEnabled() {
    return federationEnabled;
  }
  public void setFederationEnabled(Boolean federationEnabled) {
    this.federationEnabled = federationEnabled;
  }

  /**
   * List of plan enable/disable selections
   **/
  public ApiFederationConfigUpdateDTO curatedPlanSelections(List<ApiFederationConfigUpdateCuratedPlanSelectionsDTO> curatedPlanSelections) {
    this.curatedPlanSelections = curatedPlanSelections;
    return this;
  }

  
  @ApiModelProperty(value = "List of plan enable/disable selections")
      @Valid
  @JsonProperty("curatedPlanSelections")
  public List<ApiFederationConfigUpdateCuratedPlanSelectionsDTO> getCuratedPlanSelections() {
    return curatedPlanSelections;
  }
  public void setCuratedPlanSelections(List<ApiFederationConfigUpdateCuratedPlanSelectionsDTO> curatedPlanSelections) {
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
    return Objects.equals(federationEnabled, apiFederationConfigUpdate.federationEnabled) &&
        Objects.equals(curatedPlanSelections, apiFederationConfigUpdate.curatedPlanSelections);
  }

  @Override
  public int hashCode() {
    return Objects.hash(federationEnabled, curatedPlanSelections);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ApiFederationConfigUpdateDTO {\n");
    
    sb.append("    federationEnabled: ").append(toIndentedString(federationEnabled)).append("\n");
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

