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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a normalized application discovered from an external gateway.
 * This class contains lightweight data optimized for display in the frontend
 * and sufficient information for onboarding without causing N+1 query problems.
 * <p>
 * Mapped from Azure Subscription, AWS API Key, or Kong Consumer.
 * </p>
 * <p>
 * The referenceArtifact field contains a JSON string with gateway-specific metadata
 * required for stateless import operations. Actual credentials are never exposed
 * in this object - only masked display names are provided.
 * </p>
 */
public class DiscoveredApplication {

    /**
     * Unique identifier from the external gateway (e.g., Azure Subscription ID).
     */
    private String externalId;

    /**
     * Display name of the application from the external gateway.
     */
    private String name;

    /**
     * Description of the application from the external gateway.
     */
    private String description;

    /**
     * Throttling tier/policy name mapped from the external gateway's rate limiting configuration.
     */
    private String throttlingTier;

    /**
     * Owner/creator identifier from the external gateway.
     */
    private String owner;

    /**
     * Creation timestamp from the external gateway (ISO 8601 format).
     */
    private String createdTime;

    /**
     * Application attributes from the external gateway.
     */
    private Map<String, String> attributes = new HashMap<>();

    /**
     * List of discovered credential metadata (keys are masked, secrets never exposed).
     */
    private List<DiscoveredApplicationKeyInfo> keyInfoList = new ArrayList<>();

    /**
     * JSON string containing gateway-specific metadata required for import.
     * This artifact enables stateless import without re-fetching data from the gateway.
     * Contains: external ID, name, tier, and any gateway-specific identifiers.
     */
    private String referenceArtifact;

    /**
     * Indicates whether an application with this external ID already exists in WSO2 APIM.
     */
    private boolean alreadyImported;

    /**
     * If already imported, the WSO2 Application UUID.
     */
    private String importedApplicationId;

    /**
     * Default constructor.
     */
    public DiscoveredApplication() {
    }

    /**
     * Constructor with essential fields.
     *
     * @param externalId       The unique identifier from the external gateway
     * @param name             The display name of the application
     * @param referenceArtifact The JSON reference artifact for stateless import
     */
    public DiscoveredApplication(String externalId, String name, String referenceArtifact) {
        this.externalId = externalId;
        this.name = name;
        this.referenceArtifact = referenceArtifact;
    }

    /**
     * Gets the external gateway identifier.
     *
     * @return The external ID
     */
    public String getExternalId() {
        return externalId;
    }

    /**
     * Sets the external gateway identifier.
     *
     * @param externalId The external ID to set
     */
    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    /**
     * Gets the application name.
     *
     * @return The application name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the application name.
     *
     * @param name The name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the application description.
     *
     * @return The description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the application description.
     *
     * @param description The description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the throttling tier/policy name.
     *
     * @return The throttling tier
     */
    public String getThrottlingTier() {
        return throttlingTier;
    }

    /**
     * Sets the throttling tier/policy name.
     *
     * @param throttlingTier The throttling tier to set
     */
    public void setThrottlingTier(String throttlingTier) {
        this.throttlingTier = throttlingTier;
    }

    /**
     * Gets the application owner.
     *
     * @return The owner
     */
    public String getOwner() {
        return owner;
    }

    /**
     * Sets the application owner.
     *
     * @param owner The owner to set
     */
    public void setOwner(String owner) {
        this.owner = owner;
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
     * @param createdTime The created time to set (ISO 8601 format)
     */
    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    /**
     * Gets the application attributes.
     *
     * @return Map of attribute key-value pairs
     */
    public Map<String, String> getAttributes() {
        return attributes;
    }

    /**
     * Sets the application attributes.
     *
     * @param attributes The attributes map to set
     */
    public void setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
    }

    /**
     * Gets the list of discovered credential metadata.
     *
     * @return List of key info objects (credentials are masked)
     */
    public List<DiscoveredApplicationKeyInfo> getKeyInfoList() {
        return keyInfoList;
    }

    /**
     * Sets the list of discovered credential metadata.
     *
     * @param keyInfoList The key info list to set
     */
    public void setKeyInfoList(List<DiscoveredApplicationKeyInfo> keyInfoList) {
        this.keyInfoList = keyInfoList;
    }

    /**
     * Adds a discovered key info to the list.
     *
     * @param keyInfo The key info to add
     */
    public void addKeyInfo(DiscoveredApplicationKeyInfo keyInfo) {
        this.keyInfoList.add(keyInfo);
    }

    /**
     * Gets the reference artifact JSON string.
     *
     * @return The reference artifact for stateless import
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
     * Checks if this application has already been imported to WSO2 APIM.
     *
     * @return true if already imported, false otherwise
     */
    public boolean isAlreadyImported() {
        return alreadyImported;
    }

    /**
     * Sets whether this application has already been imported.
     *
     * @param alreadyImported The import status to set
     */
    public void setAlreadyImported(boolean alreadyImported) {
        this.alreadyImported = alreadyImported;
    }

    /**
     * Gets the imported WSO2 Application UUID if already imported.
     *
     * @return The imported application UUID, or null if not imported
     */
    public String getImportedApplicationId() {
        return importedApplicationId;
    }

    /**
     * Sets the imported WSO2 Application UUID.
     *
     * @param importedApplicationId The application UUID to set
     */
    public void setImportedApplicationId(String importedApplicationId) {
        this.importedApplicationId = importedApplicationId;
    }
}