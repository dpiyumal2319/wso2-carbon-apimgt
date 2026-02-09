package org.wso2.carbon.apimgt.rest.api.store.v1.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.*;

/**
 * Optional parameters for creating a federated subscription
 **/

import io.swagger.annotations.*;
import java.util.Objects;

import javax.xml.bind.annotation.*;
import org.wso2.carbon.apimgt.rest.api.common.annotations.Scope;
import com.fasterxml.jackson.annotation.JsonCreator;

import javax.validation.Valid;

@ApiModel(description = "Optional parameters for creating a federated subscription")

public class FederatedSubscriptionCreateRequestDTO   {
  
    private String selectedOption = null;

  /**
   * Opaque JSON containing the developer&#39;s selected subscription option
   **/
  public FederatedSubscriptionCreateRequestDTO selectedOption(String selectedOption) {
    this.selectedOption = selectedOption;
    return this;
  }

  
  @ApiModelProperty(example = "{\"id\":\"plan1\",\"name\":\"Basic\"}", value = "Opaque JSON containing the developer's selected subscription option")
  @JsonProperty("selectedOption")
  public String getSelectedOption() {
    return selectedOption;
  }
  public void setSelectedOption(String selectedOption) {
    this.selectedOption = selectedOption;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    FederatedSubscriptionCreateRequestDTO federatedSubscriptionCreateRequest = (FederatedSubscriptionCreateRequestDTO) o;
    return Objects.equals(selectedOption, federatedSubscriptionCreateRequest.selectedOption);
  }

  @Override
  public int hashCode() {
    return Objects.hash(selectedOption);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class FederatedSubscriptionCreateRequestDTO {\n");
    
    sb.append("    selectedOption: ").append(toIndentedString(selectedOption)).append("\n");
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

