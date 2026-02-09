/*
 * Copyright (c) 2026, WSO2 LLC. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 LLC. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.wso2.carbon.apimgt.api.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Context object carrying all information needed for federated subscription operations.
 * Reduces coupling by providing complete context to agents instead of requiring
 * them to fetch missing information.
 */
public class FederatedSubscriptionContext {

    // Core Identifiers
    private String subscriptionUuid;
    private String externalSubscriptionId;  // null for CREATE, set for DELETE/RETRIEVE

    // Both Reference Artifacts
    private String apiReferenceArtifact;        // From AM_API_EXTERNAL_API_MAPPING
    private String subscriptionReferenceArtifact; // From AM_SUBSCRIPTION_EXTERNAL_MAPPING
    
    // Subscription Options
    private String selectedOption;  // Opaque JSON - developer's chosen subscription option

    // WSO2 Metadata (for display names, logging)
    private String apiName;
    private String apiVersion;
    private String apiContext;
    private String apiUuid;
    private String applicationName;
    private String applicationUuid;
    private String subscriberName;
    private String organizationId;
    private String environmentId;
    private String throttlingPolicy;

    // Extensibility
    private Map<String, Object> properties;

    private FederatedSubscriptionContext(Builder builder) {
        this.subscriptionUuid = builder.subscriptionUuid;
        this.externalSubscriptionId = builder.externalSubscriptionId;
        this.apiReferenceArtifact = builder.apiReferenceArtifact;
        this.subscriptionReferenceArtifact = builder.subscriptionReferenceArtifact;
        this.selectedOption = builder.selectedOption;
        this.apiName = builder.apiName;
        this.apiVersion = builder.apiVersion;
        this.apiContext = builder.apiContext;
        this.apiUuid = builder.apiUuid;
        this.applicationName = builder.applicationName;
        this.applicationUuid = builder.applicationUuid;
        this.subscriberName = builder.subscriberName;
        this.organizationId = builder.organizationId;
        this.environmentId = builder.environmentId;
        this.throttlingPolicy = builder.throttlingPolicy;
        this.properties = builder.properties != null ? new HashMap<>(builder.properties) : new HashMap<>();
    }

    public static Builder builder() {
        return new Builder();
    }

    // Getters
    public String getSubscriptionUuid() {
        return subscriptionUuid;
    }

    public String getExternalSubscriptionId() {
        return externalSubscriptionId;
    }

    public String getApiReferenceArtifact() {
        return apiReferenceArtifact;
    }

    public String getSubscriptionReferenceArtifact() {
        return subscriptionReferenceArtifact;
    }

    public String getSelectedOption() {
        return selectedOption;
    }

    public String getApiName() {
        return apiName;
    }

    public String getApiVersion() {
        return apiVersion;
    }

    public String getApiContext() {
        return apiContext;
    }

    public String getApiUuid() {
        return apiUuid;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public String getApplicationUuid() {
        return applicationUuid;
    }

    public String getSubscriberName() {
        return subscriberName;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public String getEnvironmentId() {
        return environmentId;
    }

    public String getThrottlingPolicy() {
        return throttlingPolicy;
    }

    public Map<String, Object> getProperties() {
        return properties;
    }

    public Object getProperty(String key) {
        return properties.get(key);
    }

    public static class Builder {
        private String subscriptionUuid;
        private String externalSubscriptionId;
        private String apiReferenceArtifact;
        private String subscriptionReferenceArtifact;
        private String selectedOption;
        private String apiName;
        private String apiVersion;
        private String apiContext;
        private String apiUuid;
        private String applicationName;
        private String applicationUuid;
        private String subscriberName;
        private String organizationId;
        private String environmentId;
        private String throttlingPolicy;
        private Map<String, Object> properties;

        public Builder subscriptionUuid(String subscriptionUuid) {
            this.subscriptionUuid = subscriptionUuid;
            return this;
        }

        public Builder externalSubscriptionId(String externalSubscriptionId) {
            this.externalSubscriptionId = externalSubscriptionId;
            return this;
        }

        public Builder apiReferenceArtifact(String apiReferenceArtifact) {
            this.apiReferenceArtifact = apiReferenceArtifact;
            return this;
        }

        public Builder subscriptionReferenceArtifact(String subscriptionReferenceArtifact) {
            this.subscriptionReferenceArtifact = subscriptionReferenceArtifact;
            return this;
        }

        public Builder selectedOption(String selectedOption) {
            this.selectedOption = selectedOption;
            return this;
        }

        public Builder apiName(String apiName) {
            this.apiName = apiName;
            return this;
        }

        public Builder apiVersion(String apiVersion) {
            this.apiVersion = apiVersion;
            return this;
        }

        public Builder apiContext(String apiContext) {
            this.apiContext = apiContext;
            return this;
        }

        public Builder apiUuid(String apiUuid) {
            this.apiUuid = apiUuid;
            return this;
        }

        public Builder applicationName(String applicationName) {
            this.applicationName = applicationName;
            return this;
        }

        public Builder applicationUuid(String applicationUuid) {
            this.applicationUuid = applicationUuid;
            return this;
        }

        public Builder subscriberName(String subscriberName) {
            this.subscriberName = subscriberName;
            return this;
        }

        public Builder organizationId(String organizationId) {
            this.organizationId = organizationId;
            return this;
        }

        public Builder environmentId(String environmentId) {
            this.environmentId = environmentId;
            return this;
        }

        public Builder throttlingPolicy(String throttlingPolicy) {
            this.throttlingPolicy = throttlingPolicy;
            return this;
        }

        public Builder properties(Map<String, Object> properties) {
            this.properties = properties;
            return this;
        }

        public Builder property(String key, Object value) {
            if (this.properties == null) {
                this.properties = new HashMap<>();
            }
            this.properties.put(key, value);
            return this;
        }

        public FederatedSubscriptionContext build() {
            return new FederatedSubscriptionContext(this);
        }
    }
}
