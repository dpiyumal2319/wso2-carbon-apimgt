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
 * Represents the mapping between a WSO2 APIM Application and its corresponding
 * entity in an external gateway (federated environment).
 * <p>
 * This model is used to:
 * - Track which external gateway applications have been imported
 * - Store the reference artifact for detecting updates
 * - Enable bi-directional linkage between WSO2 and external gateway
 * </p>
 * <p>
 * The referenceArtifact contains gateway-specific metadata as a JSON string,
 * which can be used to detect if the external application has been modified
 * since the last sync.
 * </p>
 */
public class ApplicationExternalMapping {

    /**
     * The internal WSO2 Application ID (from AM_APPLICATION table).
     */
    private int applicationId;

    /**
     * The WSO2 Application UUID.
     */
    private String applicationUuid;

    /**
     * The UUID of the gateway environment where the external application exists.
     */
    private String gatewayEnvironmentId;

    /**
     * The unique identifier of the application in the external gateway.
     */
    private String externalApplicationId;

    /**
     * JSON string containing gateway-specific metadata.
     * Used for detecting changes and supporting stateless operations.
     */
    private String referenceArtifact;

    /**
     * Timestamp when this mapping was created (ISO 8601 format).
     */
    private String createdTime;

    /**
     * Timestamp when this mapping was last updated (ISO 8601 format).
     */
    private String lastUpdatedTime;

    /**
     * Default constructor.
     */
    public ApplicationExternalMapping() {
    }

    /**
     * Constructor with essential fields.
     *
     * @param applicationId        The WSO2 Application ID
     * @param gatewayEnvironmentId The gateway environment UUID
     * @param referenceArtifact    The reference artifact JSON
     */
    public ApplicationExternalMapping(int applicationId, String gatewayEnvironmentId, String referenceArtifact) {
        this.applicationId = applicationId;
        this.gatewayEnvironmentId = gatewayEnvironmentId;
        this.referenceArtifact = referenceArtifact;
    }

    /**
     * Gets the WSO2 Application ID.
     *
     * @return The application ID
     */
    public int getApplicationId() {
        return applicationId;
    }

    /**
     * Sets the WSO2 Application ID.
     *
     * @param applicationId The application ID to set
     */
    public void setApplicationId(int applicationId) {
        this.applicationId = applicationId;
    }

    /**
     * Gets the WSO2 Application UUID.
     *
     * @return The application UUID
     */
    public String getApplicationUuid() {
        return applicationUuid;
    }

    /**
     * Sets the WSO2 Application UUID.
     *
     * @param applicationUuid The application UUID to set
     */
    public void setApplicationUuid(String applicationUuid) {
        this.applicationUuid = applicationUuid;
    }

    /**
     * Gets the gateway environment UUID.
     *
     * @return The gateway environment ID
     */
    public String getGatewayEnvironmentId() {
        return gatewayEnvironmentId;
    }

    /**
     * Sets the gateway environment UUID.
     *
     * @param gatewayEnvironmentId The gateway environment ID to set
     */
    public void setGatewayEnvironmentId(String gatewayEnvironmentId) {
        this.gatewayEnvironmentId = gatewayEnvironmentId;
    }

    /**
     * Gets the external application ID.
     *
     * @return The external application ID
     */
    public String getExternalApplicationId() {
        return externalApplicationId;
    }

    /**
     * Sets the external application ID.
     *
     * @param externalApplicationId The external application ID to set
     */
    public void setExternalApplicationId(String externalApplicationId) {
        this.externalApplicationId = externalApplicationId;
    }

    /**
     * Gets the reference artifact JSON string.
     *
     * @return The reference artifact
     */
    public String getReferenceArtifact() {
        return referenceArtifact;
    }

    /**
     * Sets the reference artifact JSON string.
     *
     * @param referenceArtifact The reference artifact to set
     */
    public void setReferenceArtifact(String referenceArtifact) {
        this.referenceArtifact = referenceArtifact;
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
     * Gets the last updated timestamp.
     *
     * @return The last updated time in ISO 8601 format
     */
    public String getLastUpdatedTime() {
        return lastUpdatedTime;
    }

    /**
     * Sets the last updated timestamp.
     *
     * @param lastUpdatedTime The last updated time to set
     */
    public void setLastUpdatedTime(String lastUpdatedTime) {
        this.lastUpdatedTime = lastUpdatedTime;
    }

    /**
     * Checks if the external application has been updated by comparing reference artifacts.
     *
     * @param newReferenceArtifact The new reference artifact to compare
     * @return true if the artifacts are different (application updated), false otherwise
     */
    public boolean isExternalApplicationUpdated(String newReferenceArtifact) {
        if (this.referenceArtifact == null && newReferenceArtifact == null) {
            return false;
        }
        if (this.referenceArtifact == null || newReferenceArtifact == null) {
            return true;
        }
        return !this.referenceArtifact.equals(newReferenceArtifact);
    }
}
