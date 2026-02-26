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
 * Represents the mapping between a WSO2 subscription and an external gateway subscription.
 * This model corresponds to the AM_SUBSCRIPTION_EXTERNAL_MAPPING database table.
 * <p>
 * The REFERENCE_ARTIFACT (JSON blob) stores all gateway-specific metadata including:
 * - Credential info (masked value, type, isValueRetrievable)
 * - Invocation instruction (kept for now, pending API team discussion)
 * </p>
 */
public class SubscriptionExternalMapping {

    private String subscriptionUuid;
    private String gatewayEnvironmentId;
    private String externalSubscriptionId;
    private String name;
    private String referenceArtifact;
    private String selectedOption;
    private Timestamp createdTime;
    private Timestamp lastUpdatedTime;

    public SubscriptionExternalMapping() {
    }

    public SubscriptionExternalMapping(String subscriptionUuid, String gatewayEnvironmentId) {
        this.subscriptionUuid = subscriptionUuid;
        this.gatewayEnvironmentId = gatewayEnvironmentId;
    }

    public String getSubscriptionUuid() {
        return subscriptionUuid;
    }

    public void setSubscriptionUuid(String subscriptionUuid) {
        this.subscriptionUuid = subscriptionUuid;
    }

    public String getGatewayEnvironmentId() {
        return gatewayEnvironmentId;
    }

    public void setGatewayEnvironmentId(String gatewayEnvironmentId) {
        this.gatewayEnvironmentId = gatewayEnvironmentId;
    }

    public String getExternalSubscriptionId() {
        return externalSubscriptionId;
    }

    public void setExternalSubscriptionId(String externalSubscriptionId) {
        this.externalSubscriptionId = externalSubscriptionId;
    }

    public String getReferenceArtifact() {
        return referenceArtifact;
    }

    public void setReferenceArtifact(String referenceArtifact) {
        this.referenceArtifact = referenceArtifact;
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

    @Override
    public String toString() {
        return "SubscriptionExternalMapping{" +
                "subscriptionUuid='" + subscriptionUuid + '\'' +
                ", gatewayEnvironmentId='" + gatewayEnvironmentId + '\'' +
                ", externalSubscriptionId='" + externalSubscriptionId + '\'' +
                ", createdTime=" + createdTime +
                ", lastUpdatedTime=" + lastUpdatedTime +
                '}';
    }
}
