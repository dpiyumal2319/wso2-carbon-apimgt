/*
 * Copyright (c) 2025 WSO2 LLC. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 LLC. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.wso2.carbon.apimgt.api.model;

/**
 * Represents a credential returned from an external gateway subscription.
 * <p>
 * The credential type (e.g., "api-key") is set by the agent from gateway constants.
 * On CREATE or REGENERATE, the full credential value is available internally
 * but only the masked value is returned to the frontend by default.
 * A separate endpoint allows retrieval of the full value for gateways that support it.
 * <p>
 * Note: Credential masking is handled by the agent during reference artifact serialization.
 * Agents control their own masking strategies based on gateway-specific requirements.
 * </p>
 */
public class FederatedCredential {

    /**
     * Type of credential (e.g., "api-key"). Set by agent from gateway constants.
     */
    private String credentialType;

    /**
     * The credential value. Full value internally, masked when sent to frontend.
     */
    private String credentialValue;

    /**
     * The external subscription identifier in the gateway.
     */
    private String externalSubscriptionId;

    /**
     * Whether the gateway supports retrieving the credential value after creation.
     * Azure = true, AWS = false.
     */
    private boolean valueRetrievable;

    /**
     * Timestamp when the credential was created (ISO 8601 format).
     */
    private String createdTime;

    /**
     * Timestamp when the credential expires (ISO 8601 format), null if no expiry.
     */
    private String expiresAt;

    /**
     * Indicates whether this credential value is masked.
     */
    private boolean masked;

    public FederatedCredential() {
    }

    public String getCredentialType() {
        return credentialType;
    }

    public void setCredentialType(String credentialType) {
        this.credentialType = credentialType;
    }

    public String getCredentialValue() {
        return credentialValue;
    }

    public void setCredentialValue(String credentialValue) {
        this.credentialValue = credentialValue;
    }

    public String getExternalSubscriptionId() {
        return externalSubscriptionId;
    }

    public void setExternalSubscriptionId(String externalSubscriptionId) {
        this.externalSubscriptionId = externalSubscriptionId;
    }

    public boolean isValueRetrievable() {
        return valueRetrievable;
    }

    public void setValueRetrievable(boolean valueRetrievable) {
        this.valueRetrievable = valueRetrievable;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(String expiresAt) {
        this.expiresAt = expiresAt;
    }

    public boolean isMasked() {
        return masked;
    }

    public void setMasked(boolean masked) {
        this.masked = masked;
    }
}
