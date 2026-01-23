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

import java.util.HashMap;
import java.util.Map;

/**
 * Represents lightweight information about a discovered application from an external gateway.
 * This class is used for listing applications without including keys or subscription details.
 * <p>
 * For complete details including keys and API subscriptions, use {@link DiscoveredApplication}.
 * </p>
 */
public class DiscoveredApplicationInfo {

    /**
     * Unique identifier from the external gateway (e.g., Azure Subscription ID).
     */
    protected String externalId;

    /**
     * Display name of the application from the external gateway.
     */
    protected String name;

    /**
     * Description of the application from the external gateway.
     */
    protected String description;

    /**
     * Throttling tier/policy name mapped from the external gateway's rate limiting configuration.
     */
    protected String throttlingTier;

    /**
     * Owner/creator identifier from the external gateway.
     */
    protected String owner;

    /**
     * Creation timestamp from the external gateway (ISO 8601 format).
     */
    protected String createdTime;

    /**
     * Application attributes from the external gateway.
     */
    protected Map<String, String> attributes = new HashMap<>();

    /**
     * JSON string containing gateway-specific metadata required for import.
     * This artifact enables stateless import without re-fetching data from the gateway.
     */
    protected String referenceArtifact;

    /**
     * Indicates whether an application with this external ID already exists in WSO2 APIM.
     */
    protected boolean alreadyImported;

    /**
     * If already imported, the WSO2 Application UUID.
     */
    protected String importedApplicationId;

    /**
     * Default constructor.
     */
    public DiscoveredApplicationInfo() {
    }

    /**
     * Constructor with essential fields.
     *
     * @param externalId       The unique identifier from the external gateway
     * @param name             The display name of the application
     * @param referenceArtifact The JSON reference artifact for stateless import
     */
    public DiscoveredApplicationInfo(String externalId, String name, String referenceArtifact) {
        this.externalId = externalId;
        this.name = name;
        this.referenceArtifact = referenceArtifact;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getThrottlingTier() {
        return throttlingTier;
    }

    public void setThrottlingTier(String throttlingTier) {
        this.throttlingTier = throttlingTier;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, String> attributes) {
        this.attributes = attributes != null ? attributes : new HashMap<>();
    }

    public String getReferenceArtifact() {
        return referenceArtifact;
    }

    public void setReferenceArtifact(String referenceArtifact) {
        this.referenceArtifact = referenceArtifact;
    }

    public boolean isAlreadyImported() {
        return alreadyImported;
    }

    public void setAlreadyImported(boolean alreadyImported) {
        this.alreadyImported = alreadyImported;
    }

    public String getImportedApplicationId() {
        return importedApplicationId;
    }

    public void setImportedApplicationId(String importedApplicationId) {
        this.importedApplicationId = importedApplicationId;
    }
}
