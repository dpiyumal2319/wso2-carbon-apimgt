package org.wso2.carbon.apimgt.rest.api.publisher.v1.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import org.wso2.carbon.apimgt.rest.api.publisher.v1.dto.FederatedSubscriptionOptionsDTO;
import org.wso2.carbon.apimgt.rest.api.publisher.v1.dto.InvocationTemplateDTO;
import javax.validation.constraints.*;

/**
 * Deserialized subscription support information captured from the gateway during discovery. 
 **/

import io.swagger.annotations.*;
import java.util.Objects;

import javax.xml.bind.annotation.*;
import org.wso2.carbon.apimgt.rest.api.common.annotations.Scope;
import com.fasterxml.jackson.annotation.JsonCreator;

import javax.validation.Valid;

@ApiModel(description = "Deserialized subscription support information captured from the gateway during discovery. ")

public class GatewaySupportSnapshotDTO   {
  

    @XmlType(name="SubscriptionStatusEnum")
    @XmlEnum(String.class)
    public enum SubscriptionStatusEnum {
        REQUIRED("REQUIRED"),
        OPTIONAL("OPTIONAL"),
        OPEN("OPEN");
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
   * Whether the gateway requires subscriptions for this API
   **/
  public GatewaySupportSnapshotDTO subscriptionStatus(SubscriptionStatusEnum subscriptionStatus) {
    this.subscriptionStatus = subscriptionStatus;
    return this;
  }

  
  @ApiModelProperty(value = "Whether the gateway requires subscriptions for this API")
  @JsonProperty("subscriptionStatus")
  public SubscriptionStatusEnum getSubscriptionStatus() {
    return subscriptionStatus;
  }
  public void setSubscriptionStatus(SubscriptionStatusEnum subscriptionStatus) {
    this.subscriptionStatus = subscriptionStatus;
  }

  /**
   * Auth types supported by this API on the gateway
   **/
  public GatewaySupportSnapshotDTO supportedAuthTypes(List<String> supportedAuthTypes) {
    this.supportedAuthTypes = supportedAuthTypes;
    return this;
  }

  
  @ApiModelProperty(value = "Auth types supported by this API on the gateway")
  @JsonProperty("supportedAuthTypes")
  public List<String> getSupportedAuthTypes() {
    return supportedAuthTypes;
  }
  public void setSupportedAuthTypes(List<String> supportedAuthTypes) {
    this.supportedAuthTypes = supportedAuthTypes;
  }

  /**
   **/
  public GatewaySupportSnapshotDTO subscriptionOptions(FederatedSubscriptionOptionsDTO subscriptionOptions) {
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
  public GatewaySupportSnapshotDTO invocationTemplate(InvocationTemplateDTO invocationTemplate) {
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
    GatewaySupportSnapshotDTO gatewaySupportSnapshot = (GatewaySupportSnapshotDTO) o;
    return Objects.equals(subscriptionStatus, gatewaySupportSnapshot.subscriptionStatus) &&
        Objects.equals(supportedAuthTypes, gatewaySupportSnapshot.supportedAuthTypes) &&
        Objects.equals(subscriptionOptions, gatewaySupportSnapshot.subscriptionOptions) &&
        Objects.equals(invocationTemplate, gatewaySupportSnapshot.invocationTemplate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(subscriptionStatus, supportedAuthTypes, subscriptionOptions, invocationTemplate);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class GatewaySupportSnapshotDTO {\n");
    
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

