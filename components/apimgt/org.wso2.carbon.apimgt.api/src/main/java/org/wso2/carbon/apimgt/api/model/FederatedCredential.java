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
 * On CREATE or REGENERATE operations, the full credential value is returned
 * for one-time display. On GET operations, the credential is masked.
 * </p>
 */
public class FederatedCredential {

    /**
     * Default number of characters to show at the end when masking.
     */
    private static final int DEFAULT_VISIBLE_CHARS = 4;

    /**
     * The mask character used for hidden parts.
     */
    private static final char MASK_CHAR = '\u2022'; // bullet character

    /**
     * Type of credential (e.g., "api-key", "subscription-key", "bearer-token").
     */
    private String credentialType;

    /**
     * The credential value. Full value on create/regenerate, masked on get.
     */
    private String credentialValue;

    /**
     * The header name to use when invoking the API (e.g., "x-api-key", "Ocp-Apim-Subscription-Key").
     */
    private String headerName;

    /**
     * The external subscription identifier in the gateway.
     */
    private String externalSubscriptionId;

    /**
     * The external container identifier (e.g., usage plan ID, product ID).
     */
    private String externalContainerId;

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

    /**
     * Default constructor.
     */
    public FederatedCredential() {
    }

    /**
     * Constructor with essential fields.
     *
     * @param credentialType         Type of the credential
     * @param credentialValue        The credential value
     * @param headerName             The header name for API invocation
     * @param externalSubscriptionId The external subscription ID
     */
    public FederatedCredential(String credentialType, String credentialValue, String headerName,
                               String externalSubscriptionId) {
        this.credentialType = credentialType;
        this.credentialValue = credentialValue;
        this.headerName = headerName;
        this.externalSubscriptionId = externalSubscriptionId;
        this.masked = false;
    }

    /**
     * Gets the credential type.
     *
     * @return The credential type
     */
    public String getCredentialType() {
        return credentialType;
    }

    /**
     * Sets the credential type.
     *
     * @param credentialType The credential type to set
     */
    public void setCredentialType(String credentialType) {
        this.credentialType = credentialType;
    }

    /**
     * Gets the credential value.
     *
     * @return The credential value (full or masked depending on context)
     */
    public String getCredentialValue() {
        return credentialValue;
    }

    /**
     * Sets the credential value.
     *
     * @param credentialValue The credential value to set
     */
    public void setCredentialValue(String credentialValue) {
        this.credentialValue = credentialValue;
    }

    /**
     * Gets the header name for API invocation.
     *
     * @return The header name
     */
    public String getHeaderName() {
        return headerName;
    }

    /**
     * Sets the header name for API invocation.
     *
     * @param headerName The header name to set
     */
    public void setHeaderName(String headerName) {
        this.headerName = headerName;
    }

    /**
     * Gets the external subscription identifier.
     *
     * @return The external subscription ID
     */
    public String getExternalSubscriptionId() {
        return externalSubscriptionId;
    }

    /**
     * Sets the external subscription identifier.
     *
     * @param externalSubscriptionId The external subscription ID to set
     */
    public void setExternalSubscriptionId(String externalSubscriptionId) {
        this.externalSubscriptionId = externalSubscriptionId;
    }

    /**
     * Gets the external container identifier.
     *
     * @return The external container ID
     */
    public String getExternalContainerId() {
        return externalContainerId;
    }

    /**
     * Sets the external container identifier.
     *
     * @param externalContainerId The external container ID to set
     */
    public void setExternalContainerId(String externalContainerId) {
        this.externalContainerId = externalContainerId;
    }

    /**
     * Gets the creation timestamp.
     *
     * @return The created time in ISO 8601 format
     */
    public String getCreatedTime() {
        return createdTime;
    }

    /**
     * Sets the creation timestamp.
     *
     * @param createdTime The created time to set
     */
    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    /**
     * Gets the expiration timestamp.
     *
     * @return The expiration time in ISO 8601 format, or null if no expiry
     */
    public String getExpiresAt() {
        return expiresAt;
    }

    /**
     * Sets the expiration timestamp.
     *
     * @param expiresAt The expiration time to set
     */
    public void setExpiresAt(String expiresAt) {
        this.expiresAt = expiresAt;
    }

    /**
     * Checks if this credential is masked.
     *
     * @return true if the credential value is masked, false otherwise
     */
    public boolean isMasked() {
        return masked;
    }

    /**
     * Sets whether this credential is masked.
     *
     * @param masked The masked flag to set
     */
    public void setMasked(boolean masked) {
        this.masked = masked;
    }

    /**
     * Creates a masked copy of this credential with default visible characters.
     * Format: "••••••••ab12" (showing last 4 characters)
     *
     * @return A new FederatedCredential with masked credential value
     */
    public FederatedCredential toMasked() {
        return toMasked(DEFAULT_VISIBLE_CHARS);
    }

    /**
     * Creates a masked copy of this credential.
     * Format: "••••••••xxxx" where xxxx is the last N visible characters.
     *
     * @param visibleChars Number of characters to show at the end
     * @return A new FederatedCredential with masked credential value
     */
    public FederatedCredential toMasked(int visibleChars) {
        FederatedCredential masked = new FederatedCredential();
        masked.setCredentialType(this.credentialType);
        masked.setHeaderName(this.headerName);
        masked.setExternalSubscriptionId(this.externalSubscriptionId);
        masked.setExternalContainerId(this.externalContainerId);
        masked.setCreatedTime(this.createdTime);
        masked.setExpiresAt(this.expiresAt);
        masked.setMasked(true);
        masked.setCredentialValue(maskValue(this.credentialValue, visibleChars));
        return masked;
    }

    /**
     * Masks a credential value, showing only the last N characters.
     *
     * @param value        The value to mask
     * @param visibleChars Number of characters to show at the end
     * @return The masked value
     */
    private String maskValue(String value, int visibleChars) {
        if (value == null || value.isEmpty()) {
            return value;
        }

        int length = value.length();
        if (length <= visibleChars) {
            // If the value is shorter than visible chars, mask entirely
            return repeatChar(MASK_CHAR, length);
        }

        int maskLength = Math.min(8, length - visibleChars);
        String maskedPart = repeatChar(MASK_CHAR, maskLength);
        String visiblePart = value.substring(length - visibleChars);
        return maskedPart + visiblePart;
    }

    /**
     * Creates a string by repeating a character.
     *
     * @param c     The character to repeat
     * @param count The number of times to repeat
     * @return The repeated string
     */
    private String repeatChar(char c, int count) {
        StringBuilder sb = new StringBuilder(count);
        for (int i = 0; i < count; i++) {
            sb.append(c);
        }
        return sb.toString();
    }

    /**
     * Creates a masked credential reference for storage.
     * This is what gets stored in the database for display purposes.
     *
     * @return The masked credential value for storage
     */
    public String getMaskedCredentialReference() {
        return maskValue(this.credentialValue, DEFAULT_VISIBLE_CHARS);
    }
}
