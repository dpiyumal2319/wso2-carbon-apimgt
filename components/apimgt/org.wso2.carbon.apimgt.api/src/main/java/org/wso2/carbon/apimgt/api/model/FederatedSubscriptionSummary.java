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

import java.sql.Timestamp;

/**
 * Lightweight summary of a federated subscription mapping for bulk-fetch display.
 * Used by the Subscriptions page to render the subscriptions table — shows which
 * applications have subscribed, with what selected option, and how many credentials
 * have been provisioned — without fetching full reference artifacts.
 */
public class FederatedSubscriptionSummary {

    private String subscriptionUuid;
    private String mappingUuid;
    private String applicationId;
    private String applicationName;
    private String selectedOption;
    private String subscriptionStatus;
    private int credentialCount;
    private Timestamp createdTime;
    private Timestamp lastUpdatedTime;

    public String getSubscriptionUuid() {
        return subscriptionUuid;
    }

    public void setSubscriptionUuid(String subscriptionUuid) {
        this.subscriptionUuid = subscriptionUuid;
    }

    public String getMappingUuid() {
        return mappingUuid;
    }

    public void setMappingUuid(String mappingUuid) {
        this.mappingUuid = mappingUuid;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getSelectedOption() {
        return selectedOption;
    }

    public void setSelectedOption(String selectedOption) {
        this.selectedOption = selectedOption;
    }

    public String getSubscriptionStatus() {
        return subscriptionStatus;
    }

    public void setSubscriptionStatus(String subscriptionStatus) {
        this.subscriptionStatus = subscriptionStatus;
    }

    public int getCredentialCount() {
        return credentialCount;
    }

    public void setCredentialCount(int credentialCount) {
        this.credentialCount = credentialCount;
    }

    public Timestamp getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Timestamp createdTime) {
        this.createdTime = createdTime;
    }

    public Timestamp getLastUpdatedTime() {
        return lastUpdatedTime;
    }

    public void setLastUpdatedTime(Timestamp lastUpdatedTime) {
        this.lastUpdatedTime = lastUpdatedTime;
    }
}
