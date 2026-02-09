package org.wso2.carbon.apimgt.rest.api.store.v1.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import org.wso2.carbon.apimgt.rest.api.store.v1.dto.FederatedSubscriptionOptionsDTO;
import javax.validation.constraints.*;

/**
 * Information about subscription-level security support for an API
 **/

import io.swagger.annotations.*;
import java.util.Objects;

import javax.xml.bind.annotation.*;
import org.wso2.carbon.apimgt.rest.api.common.annotations.Scope;
import com.fasterxml.jackson.annotation.JsonCreator;

import javax.validation.Valid;

@ApiModel(description = "Information about subscription-level security support for an API")

public class SubscriptionSupportInfoDTO   {
  
    private List<String> supportedAuthTypes = new ArrayList<String>();
    private Boolean requiresSubscription = null;
    private FederatedSubscriptionOptionsDTO subscriptionOptions = null;

  /**
   * Array of supported authentication types, empty if no subscription security
   **/
  public SubscriptionSupportInfoDTO supportedAuthTypes(List<String> supportedAuthTypes) {
    this.supportedAuthTypes = supportedAuthTypes;
    return this;
  }

  
  @ApiModelProperty(example = "[\"opaque-api-key\"]", value = "Array of supported authentication types, empty if no subscription security")
  @JsonProperty("supportedAuthTypes")
  public List<String> getSupportedAuthTypes() {
    return supportedAuthTypes;
  }
  public void setSupportedAuthTypes(List<String> supportedAuthTypes) {
    this.supportedAuthTypes = supportedAuthTypes;
  }

  /**
   * Whether the API requires subscription-level authentication
   **/
  public SubscriptionSupportInfoDTO requiresSubscription(Boolean requiresSubscription) {
    this.requiresSubscription = requiresSubscription;
    return this;
  }

  
  @ApiModelProperty(example = "true", value = "Whether the API requires subscription-level authentication")
  @JsonProperty("requiresSubscription")
  public Boolean isRequiresSubscription() {
    return requiresSubscription;
  }
  public void setRequiresSubscription(Boolean requiresSubscription) {
    this.requiresSubscription = requiresSubscription;
  }

  /**
   **/
  public SubscriptionSupportInfoDTO subscriptionOptions(FederatedSubscriptionOptionsDTO subscriptionOptions) {
    this.subscriptionOptions = subscriptionOptions;
    return this;
  }

  
  @ApiModelProperty(value = "")
      @Valid
  @JsonProperty("subscriptionOptions")
  public FederatedSubscriptionOptionsDTO getSubscriptionOptions() {
    return subscriptionOptions;
  }
  public void setSubscriptionOptions(FederatedSubscriptionOptionsDTO subscriptionOptions) {
    this.subscriptionOptions = subscriptionOptions;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SubscriptionSupportInfoDTO subscriptionSupportInfo = (SubscriptionSupportInfoDTO) o;
    return Objects.equals(supportedAuthTypes, subscriptionSupportInfo.supportedAuthTypes) &&
        Objects.equals(requiresSubscription, subscriptionSupportInfo.requiresSubscription) &&
        Objects.equals(subscriptionOptions, subscriptionSupportInfo.subscriptionOptions);
  }

  @Override
  public int hashCode() {
    return Objects.hash(supportedAuthTypes, requiresSubscription, subscriptionOptions);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SubscriptionSupportInfoDTO {\n");
    
    sb.append("    supportedAuthTypes: ").append(toIndentedString(supportedAuthTypes)).append("\n");
    sb.append("    requiresSubscription: ").append(toIndentedString(requiresSubscription)).append("\n");
    sb.append("    subscriptionOptions: ").append(toIndentedString(subscriptionOptions)).append("\n");
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

