package org.wso2.carbon.apimgt.rest.api.store.v1.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.*;

/**
 * Instructions for invoking a federated API. The body field carries an opaque JSON string whose structure is defined by each gateway connector. 
 **/

import io.swagger.annotations.*;
import java.util.Objects;

import javax.xml.bind.annotation.*;
import org.wso2.carbon.apimgt.rest.api.common.annotations.Scope;
import com.fasterxml.jackson.annotation.JsonCreator;

import javax.validation.Valid;

@ApiModel(description = "Instructions for invoking a federated API. The body field carries an opaque JSON string whose structure is defined by each gateway connector. ")

public class InvocationInstructionDTO   {
  
    private String schemaName = null;
    private String body = null;

  /**
   * Schema identifier for renderer selection (e.g., \&quot;api-key-invocation\&quot;)
   **/
  public InvocationInstructionDTO schemaName(String schemaName) {
    this.schemaName = schemaName;
    return this;
  }

  
  @ApiModelProperty(example = "api-key-invocation", value = "Schema identifier for renderer selection (e.g., \"api-key-invocation\")")
  @JsonProperty("schemaName")
  public String getSchemaName() {
    return schemaName;
  }
  public void setSchemaName(String schemaName) {
    this.schemaName = schemaName;
  }

  /**
   * Opaque JSON string containing invocation details. Structure is connector-specific (e.g., headerName, baseUrl, basePath, curlExample, notes). 
   **/
  public InvocationInstructionDTO body(String body) {
    this.body = body;
    return this;
  }

  
  @ApiModelProperty(example = "{\"headerName\":\"x-api-key\",\"baseUrl\":\"https://api.example.com\",\"basePath\":\"/v1\",\"curlExample\":\"curl ...\"}", value = "Opaque JSON string containing invocation details. Structure is connector-specific (e.g., headerName, baseUrl, basePath, curlExample, notes). ")
  @JsonProperty("body")
  public String getBody() {
    return body;
  }
  public void setBody(String body) {
    this.body = body;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    InvocationInstructionDTO invocationInstruction = (InvocationInstructionDTO) o;
    return Objects.equals(schemaName, invocationInstruction.schemaName) &&
        Objects.equals(body, invocationInstruction.body);
  }

  @Override
  public int hashCode() {
    return Objects.hash(schemaName, body);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class InvocationInstructionDTO {\n");
    
    sb.append("    schemaName: ").append(toIndentedString(schemaName)).append("\n");
    sb.append("    body: ").append(toIndentedString(body)).append("\n");
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

