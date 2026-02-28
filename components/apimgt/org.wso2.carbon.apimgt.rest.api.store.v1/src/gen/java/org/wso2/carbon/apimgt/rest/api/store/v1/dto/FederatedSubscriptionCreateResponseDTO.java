package org.wso2.carbon.apimgt.rest.api.store.v1.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.*;

/**
 * Result of creating a federated subscription
 **/

import io.swagger.annotations.*;
import java.util.Objects;

import javax.xml.bind.annotation.*;
import org.wso2.carbon.apimgt.rest.api.common.annotations.Scope;
import com.fasterxml.jackson.annotation.JsonCreator;

import javax.validation.Valid;

@ApiModel(description = "Result of creating a federated subscription")

public class FederatedSubscriptionCreateResponseDTO   {
  
    private String subscriptionId = null;

    @XmlType(name="StatusEnum")
    @XmlEnum(String.class)
    public enum StatusEnum {
        ACTIVE("ACTIVE"),
        PENDING_APPROVAL("PENDING_APPROVAL");
        private String value;

        StatusEnum (String v) {
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
        public static StatusEnum fromValue(String v) {
            for (StatusEnum b : StatusEnum.values()) {
                if (String.valueOf(b.value).equals(v)) {
                    return b;
                }
            }
return null;
        }
    }
    private StatusEnum status = null;

  /**
   * UUID of the created WSO2 subscription
   **/
  public FederatedSubscriptionCreateResponseDTO subscriptionId(String subscriptionId) {
    this.subscriptionId = subscriptionId;
    return this;
  }

  
  @ApiModelProperty(example = "faae5fcc-cbae-40c4-bf43-89931571d250", value = "UUID of the created WSO2 subscription")
  @JsonProperty("subscriptionId")
  public String getSubscriptionId() {
    return subscriptionId;
  }
  public void setSubscriptionId(String subscriptionId) {
    this.subscriptionId = subscriptionId;
  }

  /**
   * Provisioning status: * ACTIVE - Subscription is active; credentials can now be generated * PENDING_APPROVAL - Subscription requires admin approval before credentials can be provisioned 
   **/
  public FederatedSubscriptionCreateResponseDTO status(StatusEnum status) {
    this.status = status;
    return this;
  }

  
  @ApiModelProperty(example = "ACTIVE", value = "Provisioning status: * ACTIVE - Subscription is active; credentials can now be generated * PENDING_APPROVAL - Subscription requires admin approval before credentials can be provisioned ")
  @JsonProperty("status")
  public StatusEnum getStatus() {
    return status;
  }
  public void setStatus(StatusEnum status) {
    this.status = status;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    FederatedSubscriptionCreateResponseDTO federatedSubscriptionCreateResponse = (FederatedSubscriptionCreateResponseDTO) o;
    return Objects.equals(subscriptionId, federatedSubscriptionCreateResponse.subscriptionId) &&
        Objects.equals(status, federatedSubscriptionCreateResponse.status);
  }

  @Override
  public int hashCode() {
    return Objects.hash(subscriptionId, status);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class FederatedSubscriptionCreateResponseDTO {\n");
    
    sb.append("    subscriptionId: ").append(toIndentedString(subscriptionId)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
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

