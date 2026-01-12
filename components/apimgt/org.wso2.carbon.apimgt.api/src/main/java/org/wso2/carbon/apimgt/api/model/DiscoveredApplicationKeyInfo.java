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
 * Represents metadata about a discovered application's credential/key.
 * This class only contains display-safe information - actual secret values
 * are never exposed until the application is imported.
 * <p>
 * Examples of source credentials:
 * - Azure: Subscription Primary/Secondary Keys
 * - AWS: API Key
 * - Kong: Consumer API Key or JWT Credential
 * </p>
 */
public class DiscoveredApplicationKeyInfo {

    /**
     * The type of key (e.g., "PRODUCTION", "SANDBOX", "PRIMARY", "SECONDARY").
     */
    private String keyType;

    /**
     * Display name for the key (e.g., "Primary Key", "API Key").
     */
    private String keyName;

    /**
     * Masked representation of the key value for display purposes.
     * Example: "•••••abc123" - shows only last few characters.
     */
    private String maskedKeyValue;

    /**
     * Reference identifier for the key in the external gateway.
     * Used during import to retrieve the actual key value.
     */
    private String externalKeyReference;

    /**
     * Creation timestamp of the key (ISO 8601 format).
     */
    private String createdTime;

    /**
     * Expiry timestamp of the key if applicable (ISO 8601 format).
     */
    private String expiryTime;

    /**
     * Current state of the key (e.g., "ACTIVE", "REVOKED", "EXPIRED").
     */
    private String state;

    /**
     * Default constructor.
     */
    public DiscoveredApplicationKeyInfo() {
    }

    /**
     * Constructor with essential fields.
     *
     * @param keyType            The type of key
     * @param keyName            The display name of the key
     * @param maskedKeyValue     The masked key value for display
     * @param externalKeyReference The external gateway reference for the key
     */
    public DiscoveredApplicationKeyInfo(String keyType, String keyName, String maskedKeyValue,
                                        String externalKeyReference) {
        this.keyType = keyType;
        this.keyName = keyName;
        this.maskedKeyValue = maskedKeyValue;
        this.externalKeyReference = externalKeyReference;
    }

    /**
     * Gets the key type.
     *
     * @return The key type
     */
    public String getKeyType() {
        return keyType;
    }

    /**
     * Sets the key type.
     *
     * @param keyType The key type to set
     */
    public void setKeyType(String keyType) {
        this.keyType = keyType;
    }

    /**
     * Gets the key display name.
     *
     * @return The key name
     */
    public String getKeyName() {
        return keyName;
    }

    /**
     * Sets the key display name.
     *
     * @param keyName The key name to set
     */
    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }

    /**
     * Gets the masked key value for display.
     *
     * @return The masked key value
     */
    public String getMaskedKeyValue() {
        return maskedKeyValue;
    }

    /**
     * Sets the masked key value for display.
     *
     * @param maskedKeyValue The masked key value to set
     */
    public void setMaskedKeyValue(String maskedKeyValue) {
        this.maskedKeyValue = maskedKeyValue;
    }

    /**
     * Gets the external gateway key reference.
     *
     * @return The external key reference
     */
    public String getExternalKeyReference() {
        return externalKeyReference;
    }

    /**
     * Sets the external gateway key reference.
     *
     * @param externalKeyReference The external key reference to set
     */
    public void setExternalKeyReference(String externalKeyReference) {
        this.externalKeyReference = externalKeyReference;
    }

    /**
     * Gets the key creation timestamp.
     *
     * @return The created time in ISO 8601 format
     */
    public String getCreatedTime() {
        return createdTime;
    }

    /**
     * Sets the key creation timestamp.
     *
     * @param createdTime The created time to set
     */
    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    /**
     * Gets the key expiry timestamp.
     *
     * @return The expiry time in ISO 8601 format, or null if no expiry
     */
    public String getExpiryTime() {
        return expiryTime;
    }

    /**
     * Sets the key expiry timestamp.
     *
     * @param expiryTime The expiry time to set
     */
    public void setExpiryTime(String expiryTime) {
        this.expiryTime = expiryTime;
    }

    /**
     * Gets the current state of the key.
     *
     * @return The key state
     */
    public String getState() {
        return state;
    }

    /**
     * Sets the current state of the key.
     *
     * @param state The state to set
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * Utility method to mask a key value for display.
     * Shows only the last few characters of the key.
     *
     * @param keyValue The actual key value to mask
     * @param visibleChars Number of characters to show at the end
     * @return The masked key value
     */
    public static String maskKeyValue(String keyValue, int visibleChars) {
        if (keyValue == null || keyValue.isEmpty()) {
            return "•••••";
        }
        if (keyValue.length() <= visibleChars) {
            return "•••••" + keyValue;
        }
        String visiblePart = keyValue.substring(keyValue.length() - visibleChars);
        return "•••••" + visiblePart;
    }
}
