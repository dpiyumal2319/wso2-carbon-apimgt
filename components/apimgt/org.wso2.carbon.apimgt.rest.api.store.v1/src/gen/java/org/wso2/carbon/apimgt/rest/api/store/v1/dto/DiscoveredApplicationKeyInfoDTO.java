package org.wso2.carbon.apimgt.rest.api.store.v1.dto;

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

public class DiscoveredApplicationKeyInfoDTO   {
  
    private String keyType = null;
    private String keyName = null;
    private String maskedKeyValue = null;
    private String externalKeyReference = null;
    private String createdTime = null;
    private String expiryTime = null;
    private String state = null;

    /**
     **/
    public DiscoveredApplicationKeyInfoDTO keyType(String keyType) {
        this.keyType = keyType;
        return this;
    }

    @ApiModelProperty(value = "")
    @JsonProperty("keyType")
    public String getKeyType() {
        return keyType;
    }
    public void setKeyType(String keyType) {
        this.keyType = keyType;
    }

    /**
     **/
    public DiscoveredApplicationKeyInfoDTO keyName(String keyName) {
        this.keyName = keyName;
        return this;
    }

    @ApiModelProperty(value = "")
    @JsonProperty("keyName")
    public String getKeyName() {
        return keyName;
    }
    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }

    /**
     **/
    public DiscoveredApplicationKeyInfoDTO maskedKeyValue(String maskedKeyValue) {
        this.maskedKeyValue = maskedKeyValue;
        return this;
    }

    @ApiModelProperty(value = "")
    @JsonProperty("maskedKeyValue")
    public String getMaskedKeyValue() {
        return maskedKeyValue;
    }
    public void setMaskedKeyValue(String maskedKeyValue) {
        this.maskedKeyValue = maskedKeyValue;
    }

    /**
     **/
    public DiscoveredApplicationKeyInfoDTO externalKeyReference(String externalKeyReference) {
        this.externalKeyReference = externalKeyReference;
        return this;
    }

    @ApiModelProperty(value = "")
    @JsonProperty("externalKeyReference")
    public String getExternalKeyReference() {
        return externalKeyReference;
    }
    public void setExternalKeyReference(String externalKeyReference) {
        this.externalKeyReference = externalKeyReference;
    }

    /**
     **/
    public DiscoveredApplicationKeyInfoDTO createdTime(String createdTime) {
        this.createdTime = createdTime;
        return this;
    }

    @ApiModelProperty(value = "")
    @JsonProperty("createdTime")
    public String getCreatedTime() {
        return createdTime;
    }
    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    /**
     **/
    public DiscoveredApplicationKeyInfoDTO expiryTime(String expiryTime) {
        this.expiryTime = expiryTime;
        return this;
    }

    @ApiModelProperty(value = "")
    @JsonProperty("expiryTime")
    public String getExpiryTime() {
        return expiryTime;
    }
    public void setExpiryTime(String expiryTime) {
        this.expiryTime = expiryTime;
    }

    /**
     **/
    public DiscoveredApplicationKeyInfoDTO state(String state) {
        this.state = state;
        return this;
    }

    @ApiModelProperty(value = "")
    @JsonProperty("state")
    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }


    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DiscoveredApplicationKeyInfoDTO discoveredApplicationKeyInfo = (DiscoveredApplicationKeyInfoDTO) o;
        return Objects.equals(this.keyType, discoveredApplicationKeyInfo.keyType) &&
            Objects.equals(this.keyName, discoveredApplicationKeyInfo.keyName) &&
            Objects.equals(this.maskedKeyValue, discoveredApplicationKeyInfo.maskedKeyValue) &&
            Objects.equals(this.externalKeyReference, discoveredApplicationKeyInfo.externalKeyReference) &&
            Objects.equals(this.createdTime, discoveredApplicationKeyInfo.createdTime) &&
            Objects.equals(this.expiryTime, discoveredApplicationKeyInfo.expiryTime) &&
            Objects.equals(this.state, discoveredApplicationKeyInfo.state);
    }

    @Override
    public int hashCode() {
        return Objects.hash(keyType, keyName, maskedKeyValue, externalKeyReference, createdTime, expiryTime, state);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class DiscoveredApplicationKeyInfoDTO {\n");
        
        sb.append("    keyType: ").append(toIndentedString(keyType)).append("\n");
        sb.append("    keyName: ").append(toIndentedString(keyName)).append("\n");
        sb.append("    maskedKeyValue: ").append(toIndentedString(maskedKeyValue)).append("\n");
        sb.append("    externalKeyReference: ").append(toIndentedString(externalKeyReference)).append("\n");
        sb.append("    createdTime: ").append(toIndentedString(createdTime)).append("\n");
        sb.append("    expiryTime: ").append(toIndentedString(expiryTime)).append("\n");
        sb.append("    state: ").append(toIndentedString(state)).append("\n");
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
