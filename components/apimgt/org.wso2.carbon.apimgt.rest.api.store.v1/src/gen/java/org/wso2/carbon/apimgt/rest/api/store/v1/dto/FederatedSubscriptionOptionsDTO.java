package org.wso2.carbon.apimgt.rest.api.store.v1.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.*;

/**
 * Subscription options available from external gateway (e.g., usage plans)
 **/

import io.swagger.annotations.*;
import java.util.Objects;

import javax.xml.bind.annotation.*;
import org.wso2.carbon.apimgt.rest.api.common.annotations.Scope;
import com.fasterxml.jackson.annotation.JsonCreator;

import javax.validation.Valid;

@ApiModel(description = "Subscription options available from external gateway (e.g., usage plans)")

public class FederatedSubscriptionOptionsDTO   {
  
    private String body = null;
    private String schemaName = null;

  /**
   * Opaque JSON containing gateway-specific subscription options
   **/
  public FederatedSubscriptionOptionsDTO body(String body) {
    this.body = body;
    return this;
  }

  
  @ApiModelProperty(example = "{\"options\":[{\"id\":\"plan1\",\"name\":\"Basic\",\"rateLimit\":100}]}", value = "Opaque JSON containing gateway-specific subscription options")
  @JsonProperty("body")
  public String getBody() {
    return body;
  }
  public void setBody(String body) {
    this.body = body;
  }

  /**
   * Schema identifier for frontend renderer selection (e.g., \&quot;tier-selector\&quot;)
   **/
  public FederatedSubscriptionOptionsDTO schemaName(String schemaName) {
    this.schemaName = schemaName;
    return this;
  }

  
  @ApiModelProperty(example = "tier-selector", value = "Schema identifier for frontend renderer selection (e.g., \"tier-selector\")")
  @JsonProperty("schemaName")
  public String getSchemaName() {
    return schemaName;
  }
  public void setSchemaName(String schemaName) {
    this.schemaName = schemaName;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    FederatedSubscriptionOptionsDTO federatedSubscriptionOptions = (FederatedSubscriptionOptionsDTO) o;
    return Objects.equals(body, federatedSubscriptionOptions.body) &&
        Objects.equals(schemaName, federatedSubscriptionOptions.schemaName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(body, schemaName);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class FederatedSubscriptionOptionsDTO {\n");
    
    sb.append("    body: ").append(toIndentedString(body)).append("\n");
    sb.append("    schemaName: ").append(toIndentedString(schemaName)).append("\n");
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

