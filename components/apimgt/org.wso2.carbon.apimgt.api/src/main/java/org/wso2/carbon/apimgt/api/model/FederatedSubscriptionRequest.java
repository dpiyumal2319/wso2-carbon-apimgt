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
 * Request model for creating a federated subscription in an external gateway.
 * <p>
 * Contains both WSO2 identifiers (for internal tracking) and external gateway
 * identifiers (for the actual subscription creation).
 * </p>
 */
public class FederatedSubscriptionRequest {

    /**
     * WSO2 Application UUID.
     */
    private String applicationUuid;

    /**
     * WSO2 API UUID.
     */
    private String apiUuid;

    /**
     * WSO2 Subscription UUID.
     */
    private String subscriptionUuid;

    /**
     * Organization identifier for multi-tenant support.
     */
    private String organizationId;

    /**
     * Gateway environment UUID.
     */
    private String environmentId;

    /**
     * Subscriber user identifier.
     */
    private String subscriberId;

    /**
     * Throttling policy/tier name.
     */
    private String throttlingPolicy;

    /**
     * External gateway's API identifier.
     */
    private String externalApiId;

    /**
     * External gateway's application/consumer identifier.
     */
    private String externalApplicationId;

    /**
     * Raw reference artifact JSON from AM_API_EXTERNAL_API_MAPPING.
     * Each connector knows how to parse its own format.
     */
    private String referenceArtifact;

    /**
     * Gateway-specific additional parameters.
     * For example, Azure may need product ID, Kong may need ACL group name.
     */
    private Map<String, String> additionalParameters = new HashMap<>();

    /**
     * Default constructor.
     */
    public FederatedSubscriptionRequest() {
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
     * Gets the WSO2 API UUID.
     *
     * @return The API UUID
     */
    public String getApiUuid() {
        return apiUuid;
    }

    /**
     * Sets the WSO2 API UUID.
     *
     * @param apiUuid The API UUID to set
     */
    public void setApiUuid(String apiUuid) {
        this.apiUuid = apiUuid;
    }

    /**
     * Gets the WSO2 Subscription UUID.
     *
     * @return The subscription UUID
     */
    public String getSubscriptionUuid() {
        return subscriptionUuid;
    }

    /**
     * Sets the WSO2 Subscription UUID.
     *
     * @param subscriptionUuid The subscription UUID to set
     */
    public void setSubscriptionUuid(String subscriptionUuid) {
        this.subscriptionUuid = subscriptionUuid;
    }

    /**
     * Gets the organization identifier.
     *
     * @return The organization ID
     */
    public String getOrganizationId() {
        return organizationId;
    }

    /**
     * Sets the organization identifier.
     *
     * @param organizationId The organization ID to set
     */
    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    /**
     * Gets the gateway environment UUID.
     *
     * @return The environment ID
     */
    public String getEnvironmentId() {
        return environmentId;
    }

    /**
     * Sets the gateway environment UUID.
     *
     * @param environmentId The environment ID to set
     */
    public void setEnvironmentId(String environmentId) {
        this.environmentId = environmentId;
    }

    /**
     * Gets the subscriber user identifier.
     *
     * @return The subscriber ID
     */
    public String getSubscriberId() {
        return subscriberId;
    }

    /**
     * Sets the subscriber user identifier.
     *
     * @param subscriberId The subscriber ID to set
     */
    public void setSubscriberId(String subscriberId) {
        this.subscriberId = subscriberId;
    }

    /**
     * Gets the throttling policy name.
     *
     * @return The throttling policy
     */
    public String getThrottlingPolicy() {
        return throttlingPolicy;
    }

    /**
     * Sets the throttling policy name.
     *
     * @param throttlingPolicy The throttling policy to set
     */
    public void setThrottlingPolicy(String throttlingPolicy) {
        this.throttlingPolicy = throttlingPolicy;
    }

    /**
     * Gets the raw reference artifact JSON.
     *
     * @return The reference artifact
     */
    public String getReferenceArtifact() {
        return referenceArtifact;
    }

    /**
     * Sets the raw reference artifact JSON.
     *
     * @param referenceArtifact The reference artifact to set
     */
    public void setReferenceArtifact(String referenceArtifact) {
        this.referenceArtifact = referenceArtifact;
    }

    /**
     * Gets the external gateway's API identifier.
     *
     * @return The external API ID
     */
    public String getExternalApiId() {
        return externalApiId;
    }

    /**
     * Sets the external gateway's API identifier.
     *
     * @param externalApiId The external API ID to set
     */
    public void setExternalApiId(String externalApiId) {
        this.externalApiId = externalApiId;
    }

    /**
     * Gets the external gateway's application identifier.
     *
     * @return The external application ID
     */
    public String getExternalApplicationId() {
        return externalApplicationId;
    }

    /**
     * Sets the external gateway's application identifier.
     *
     * @param externalApplicationId The external application ID to set
     */
    public void setExternalApplicationId(String externalApplicationId) {
        this.externalApplicationId = externalApplicationId;
    }

    /**
     * Gets the gateway-specific additional parameters.
     *
     * @return Map of additional parameters
     */
    public Map<String, String> getAdditionalParameters() {
        return additionalParameters;
    }

    /**
     * Sets the gateway-specific additional parameters.
     *
     * @param additionalParameters The additional parameters to set
     */
    public void setAdditionalParameters(Map<String, String> additionalParameters) {
        this.additionalParameters = additionalParameters != null ? additionalParameters : new HashMap<>();
    }

    /**
     * Adds a single additional parameter.
     *
     * @param key   The parameter key
     * @param value The parameter value
     */
    public void addAdditionalParameter(String key, String value) {
        this.additionalParameters.put(key, value);
    }

    /**
     * Gets an additional parameter by key.
     *
     * @param key The parameter key
     * @return The parameter value, or null if not found
     */
    public String getAdditionalParameter(String key) {
        return this.additionalParameters.get(key);
    }

    /**
     * Generates a unique subscription identifier following the WSO2 pattern.
     * Format: wso2_{org}_{appUuid}_{apiUuid}_{envId}
     *
     * @return The generated unique identifier
     */
    public String generateExternalSubscriptionId() {
        return String.format("wso2_%s_%s_%s_%s",
                sanitize(organizationId),
                sanitize(applicationUuid),
                sanitize(apiUuid),
                sanitize(environmentId));
    }

    /**
     * Sanitizes a string for use in identifiers by replacing non-alphanumeric characters.
     *
     * @param value The string to sanitize
     * @return The sanitized string
     */
    private String sanitize(String value) {
        if (value == null) {
            return "null";
        }
        return value.replaceAll("[^a-zA-Z0-9]", "_");
    }
}
