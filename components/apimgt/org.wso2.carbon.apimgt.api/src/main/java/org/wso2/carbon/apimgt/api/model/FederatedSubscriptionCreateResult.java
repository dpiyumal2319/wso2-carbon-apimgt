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
 * Result returned by createFederatedSubscription.
 * Contains the new subscription UUID and its status (ACTIVE or PENDING_APPROVAL).
 */
public class FederatedSubscriptionCreateResult {

    public static final String STATUS_ACTIVE = "ACTIVE";
    public static final String STATUS_PENDING_APPROVAL = "PENDING_APPROVAL";

    private String subscriptionUuid;
    private String status;

    public FederatedSubscriptionCreateResult(String subscriptionUuid, String status) {
        this.subscriptionUuid = subscriptionUuid;
        this.status = status;
    }

    public String getSubscriptionUuid() {
        return subscriptionUuid;
    }

    public void setSubscriptionUuid(String subscriptionUuid) {
        this.subscriptionUuid = subscriptionUuid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
