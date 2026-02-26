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
 * Result of the combined subscribe-and-create-federated-credential operation.
 * Contains the subscription ID, provisioning status, and credential info if provisioned.
 */
public class FederatedCredentialCreateResult {

    public enum Status {
        ACTIVE,
        PENDING_APPROVAL
    }

    private final String subscriptionId;
    private final Status status;
    private final FederatedSubscriptionResult federatedSubscriptionResult;

    private FederatedCredentialCreateResult(Builder builder) {
        this.subscriptionId = builder.subscriptionId;
        this.status = builder.status;
        this.federatedSubscriptionResult = builder.federatedSubscriptionResult;
    }

    public String getSubscriptionId() {
        return subscriptionId;
    }

    public Status getStatus() {
        return status;
    }

    /**
     * Returns the full credential result. Only non-null when status is ACTIVE.
     */
    public FederatedSubscriptionResult getFederatedSubscriptionResult() {
        return federatedSubscriptionResult;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String subscriptionId;
        private Status status;
        private FederatedSubscriptionResult federatedSubscriptionResult;

        public Builder subscriptionId(String subscriptionId) {
            this.subscriptionId = subscriptionId;
            return this;
        }

        public Builder status(Status status) {
            this.status = status;
            return this;
        }

        public Builder federatedSubscriptionResult(FederatedSubscriptionResult result) {
            this.federatedSubscriptionResult = result;
            return this;
        }

        public FederatedCredentialCreateResult build() {
            return new FederatedCredentialCreateResult(this);
        }
    }
}
