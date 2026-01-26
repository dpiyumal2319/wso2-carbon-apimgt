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
 * Represents API subscription information discovered from an external gateway.
 * <p>
 * This class is populated in stages during the builder pattern construction:
 * <ul>
 *   <li><b>Stage 1 (External References):</b> {@code externalApiId}, {@code apiName}, {@code apiVersion},
 *       {@code subscriptionTier}, {@code subscriptionStatus} are populated from the connector.</li>
 *   <li><b>Stage 2 (DB Enrichment):</b> {@code wso2ApiId}, {@code apiContext}, {@code isImported},
 *       {@code apiStatus} are populated after matching with the database.</li>
 * </ul>
 * </p>
 */
public class DiscoveredAPISubscription {

    /**
     * External API identifier from the gateway (e.g., Azure API ID).
     * Populated in Stage 1 by the connector.
     */
    private String externalApiId;

    /**
     * WSO2 API UUID after database lookup.
     * Populated in Stage 2 during DB enrichment. Null if not imported.
     */
    private String wso2ApiId;

    /**
     * API name from the external gateway.
     * Populated in Stage 1 by the connector.
     */
    private String apiName;

    /**
     * API version from the external gateway.
     * Populated in Stage 1 by the connector.
     */
    private String apiVersion;

    /**
     * API context path.
     * Populated in Stage 2 during DB enrichment with WSO2 context.
     */
    private String apiContext;

    /**
     * Subscription tier/throttling policy from the external gateway.
     * Populated in Stage 1 by the connector.
     */
    private String subscriptionTier;

    /**
     * Subscription status from the external gateway (e.g., ACTIVE, BLOCKED).
     * Populated in Stage 1 by the connector.
     */
    private String subscriptionStatus;

    /**
     * Whether this API has been imported into WSO2 APIM.
     * Populated in Stage 2 during DB enrichment. Defaults to false.
     */
    private boolean isImported = false;

    /**
     * WSO2 API lifecycle status (e.g., PUBLISHED, CREATED).
     * Populated in Stage 2 during DB enrichment. Null if not imported.
     */
    private String apiStatus;

    /**
     * Default constructor.
     */
    public DiscoveredAPISubscription() {
    }

    /**
     * Constructor with external gateway fields (Stage 1).
     *
     * @param externalApiId The external API identifier from the gateway
     * @param apiName       The API name
     * @param apiVersion    The API version
     */
    public DiscoveredAPISubscription(String externalApiId, String apiName, String apiVersion) {
        this.externalApiId = externalApiId;
        this.apiName = apiName;
        this.apiVersion = apiVersion;
    }

    public String getExternalApiId() {
        return externalApiId;
    }

    public void setExternalApiId(String externalApiId) {
        this.externalApiId = externalApiId;
    }

    public String getWso2ApiId() {
        return wso2ApiId;
    }

    public void setWso2ApiId(String wso2ApiId) {
        this.wso2ApiId = wso2ApiId;
    }

    public String getApiName() {
        return apiName;
    }

    public void setApiName(String apiName) {
        this.apiName = apiName;
    }

    public String getApiVersion() {
        return apiVersion;
    }

    public void setApiVersion(String apiVersion) {
        this.apiVersion = apiVersion;
    }

    public String getApiContext() {
        return apiContext;
    }

    public void setApiContext(String apiContext) {
        this.apiContext = apiContext;
    }

    public String getSubscriptionTier() {
        return subscriptionTier;
    }

    public void setSubscriptionTier(String subscriptionTier) {
        this.subscriptionTier = subscriptionTier;
    }

    public String getSubscriptionStatus() {
        return subscriptionStatus;
    }

    public void setSubscriptionStatus(String subscriptionStatus) {
        this.subscriptionStatus = subscriptionStatus;
    }

    public boolean isImported() {
        return isImported;
    }

    public void setImported(boolean imported) {
        isImported = imported;
    }

    public String getApiStatus() {
        return apiStatus;
    }

    public void setApiStatus(String apiStatus) {
        this.apiStatus = apiStatus;
    }
}
