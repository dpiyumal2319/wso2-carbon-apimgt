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
 * Represents a lightweight API subscription information discovered from an external gateway.
 * This class contains minimal API details to show which APIs a discovered application is subscribed to.
 */
public class
DiscoveredAPISubscription {

    /**
     * External API identifier from the gateway.
     */
    private String apiId;

    /**
     * API name.
     */
    private String apiName;

    /**
     * API version.
     */
    private String apiVersion;

    /**
     * API context path.
     */
    private String apiContext;

    /**
     * Subscription tier/throttling policy.
     */
    private String subscriptionTier;

    /**
     * Subscription status (e.g., ACTIVE, BLOCKED).
     */
    private String subscriptionStatus;

    /**
     * Default constructor.
     */
    public DiscoveredAPISubscription() {
    }

    /**
     * Constructor with essential fields.
     *
     * @param apiId   The external API identifier
     * @param apiName The API name
     * @param apiVersion The API version
     */
    public DiscoveredAPISubscription(String apiId, String apiName, String apiVersion) {
        this.apiId = apiId;
        this.apiName = apiName;
        this.apiVersion = apiVersion;
    }

    public String getApiId() {
        return apiId;
    }

    public void setApiId(String apiId) {
        this.apiId = apiId;
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
}
