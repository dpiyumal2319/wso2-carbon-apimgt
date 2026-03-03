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
 * Lightweight summary of a federated credential mapping for bulk-fetch display.
 * Used by the API Credentials page to render the credentials table without
 * fetching full reference artifacts or calling gateway agents.
 */
public class FederatedCredentialSummary {

    private String subscriptionUuid;
    private String mappingUuid;
    private String credentialUuid;
    private String applicationId;
    private String applicationName;
    private String name;
    private String selectedOption;
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

    public String getCredentialUuid() {
        return credentialUuid;
    }

    public void setCredentialUuid(String credentialUuid) {
        this.credentialUuid = credentialUuid;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSelectedOption() {
        return selectedOption;
    }

    public void setSelectedOption(String selectedOption) {
        this.selectedOption = selectedOption;
    }

    public Timestamp getLastUpdatedTime() {
        return lastUpdatedTime;
    }

    public void setLastUpdatedTime(Timestamp lastUpdatedTime) {
        this.lastUpdatedTime = lastUpdatedTime;
    }
}
