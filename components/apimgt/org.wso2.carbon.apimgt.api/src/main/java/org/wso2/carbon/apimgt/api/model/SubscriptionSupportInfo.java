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

/**
 * Represents subscription support information for an API.
 * Contains the subscription status and related metadata.
 */
public class SubscriptionSupportInfo {

    /**
     * Enum representing the subscription status of an API.
     */
    public enum SubscriptionStatus {
        /**
         * No credentials needed, invoke directly.
         */
        OPEN,
        
        /**
         * Credentials required, subscription management available.
         */
        SECURED
    }

    private SubscriptionStatus status;
    private String[] supportedAuthTypes;
    private FederatedSubscriptionOptions subscriptionOptions;

    public SubscriptionSupportInfo() {
    }

    private SubscriptionSupportInfo(Builder builder) {
        this.status = builder.status;
        this.supportedAuthTypes = builder.supportedAuthTypes;
        this.subscriptionOptions = builder.subscriptionOptions;
    }

    public SubscriptionStatus getStatus() {
        return status;
    }

    public void setStatus(SubscriptionStatus status) {
        this.status = status;
    }

    public String[] getSupportedAuthTypes() {
        return supportedAuthTypes;
    }

    public void setSupportedAuthTypes(String[] supportedAuthTypes) {
        this.supportedAuthTypes = supportedAuthTypes;
    }

    public FederatedSubscriptionOptions getSubscriptionOptions() {
        return subscriptionOptions;
    }

    public void setSubscriptionOptions(FederatedSubscriptionOptions subscriptionOptions) {
        this.subscriptionOptions = subscriptionOptions;
    }

    /**
     * Builder for SubscriptionSupportInfo.
     */
    public static class Builder {
        private SubscriptionStatus status;
        private String[] supportedAuthTypes;
        private FederatedSubscriptionOptions subscriptionOptions;

        public Builder status(SubscriptionStatus status) {
            this.status = status;
            return this;
        }

        public Builder supportedAuthTypes(String[] supportedAuthTypes) {
            this.supportedAuthTypes = supportedAuthTypes;
            return this;
        }

        public Builder subscriptionOptions(FederatedSubscriptionOptions subscriptionOptions) {
            this.subscriptionOptions = subscriptionOptions;
            return this;
        }

        public SubscriptionSupportInfo build() {
            return new SubscriptionSupportInfo(this);
        }
    }
}
