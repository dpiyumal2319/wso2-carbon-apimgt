package org.wso2.carbon.apimgt.rest.api.store.v1.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import org.wso2.carbon.apimgt.rest.api.store.v1.dto.FederatedSubscriptionOptionsDTO;
import org.wso2.carbon.apimgt.rest.api.store.v1.dto.InvocationTemplateDTO;
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
  

    @XmlType(name="SubscriptionStatusEnum")
    @XmlEnum(String.class)
    public enum SubscriptionStatusEnum {
        OPEN("OPEN"),
        SECURED("SECURED");
        private String value;

        SubscriptionStatusEnum (String v) {
            value = v;
        }

        public String value() {
            return value;
        }

        @Override
        public String toString() {
            return String.valueOf(value);
        }

        @JsonCreator
        public static SubscriptionStatusEnum fromValue(String v) {
            for (SubscriptionStatusEnum b : SubscriptionStatusEnum.values()) {
                if (String.valueOf(b.value).equals(v)) {
                    return b;
                }
            }
return null;
        }
    }
    private SubscriptionStatusEnum subscriptionStatus = null;
    private List<String> supportedAuthTypes = new ArrayList<String>();
    private FederatedSubscriptionOptionsDTO subscriptionOptions = null;
    private InvocationTemplateDTO invocationTemplate = null;

  /**
   * Subscription status of the API: * OPEN - No credentials needed, invoke directly * SECURED - Credentials required, subscription management available 
   **/
  public SubscriptionSupportInfoDTO subscriptionStatus(SubscriptionStatusEnum subscriptionStatus) {
    this.subscriptionStatus = subscriptionStatus;
    return this;
  }

  
  @ApiModelProperty(example = "SECURED", value = "Subscription status of the API: * OPEN - No credentials needed, invoke directly * SECURED - Credentials required, subscription management available ")
  @JsonProperty("subscriptionStatus")
  public SubscriptionStatusEnum getSubscriptionStatus() {
    return subscriptionStatus;
  }
  public void setSubscriptionStatus(SubscriptionStatusEnum subscriptionStatus) {
    this.subscriptionStatus = subscriptionStatus;
  }

  /**
   * Array of supported authentication types (non-empty only for SECURED status)
   **/
  public SubscriptionSupportInfoDTO supportedAuthTypes(List<String> supportedAuthTypes) {
    this.supportedAuthTypes = supportedAuthTypes;
    return this;
  }

  
  @ApiModelProperty(example = "[\"opaque-api-key\"]", value = "Array of supported authentication types (non-empty only for SECURED status)")
  @JsonProperty("supportedAuthTypes")
  public List<String> getSupportedAuthTypes() {
    return supportedAuthTypes;
  }
  public void setSupportedAuthTypes(List<String> supportedAuthTypes) {
    this.supportedAuthTypes = supportedAuthTypes;
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

  /**
   **/
  public SubscriptionSupportInfoDTO invocationTemplate(InvocationTemplateDTO invocationTemplate) {
    this.invocationTemplate = invocationTemplate;
    return this;
  }

  
  @ApiModelProperty(value = "")
      @Valid
  @JsonProperty("invocationTemplate")
  public InvocationTemplateDTO getInvocationTemplate() {
    return invocationTemplate;
  }
  public void setInvocationTemplate(InvocationTemplateDTO invocationTemplate) {
    this.invocationTemplate = invocationTemplate;
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
    return Objects.equals(subscriptionStatus, subscriptionSupportInfo.subscriptionStatus) &&
        Objects.equals(supportedAuthTypes, subscriptionSupportInfo.supportedAuthTypes) &&
        Objects.equals(subscriptionOptions, subscriptionSupportInfo.subscriptionOptions) &&
        Objects.equals(invocationTemplate, subscriptionSupportInfo.invocationTemplate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(subscriptionStatus, supportedAuthTypes, subscriptionOptions, invocationTemplate);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SubscriptionSupportInfoDTO {\n");
    
    sb.append("    subscriptionStatus: ").append(toIndentedString(subscriptionStatus)).append("\n");
    sb.append("    supportedAuthTypes: ").append(toIndentedString(supportedAuthTypes)).append("\n");
    sb.append("    subscriptionOptions: ").append(toIndentedString(subscriptionOptions)).append("\n");
    sb.append("    invocationTemplate: ").append(toIndentedString(invocationTemplate)).append("\n");
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

