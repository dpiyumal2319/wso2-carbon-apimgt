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
 * Represents a concrete credential provisioned on an external gateway for a given subscription mapping.
 * This model corresponds to the AM_EXTERNAL_CREDENTIAL database table.
 * <p>
 * Multiple credentials can exist per {@link SubscriptionExternalMapping}, enabling
 * named credentials, credential rotation, and multi-key scenarios.
 * The REFERENCE_ARTIFACT is an agent-owned opaque blob holding gateway-specific
 * credential metadata (masked value, type, retrievability flag, etc.).
 * </p>
 */
public class ExternalCredential {

    private String uuid;
    private String mappingUuid;
    private String applicationId;
    private String apiId;
    private String authzUser;
    private String gatewayEnvironmentId;
    private String name;
    private String externalSubscriptionId;
    private String referenceArtifact;
    private Timestamp createdTime;
    private Timestamp lastUpdatedTime;

    public ExternalCredential() {
    }

    public ExternalCredential(String mappingUuid) {
        this.mappingUuid = mappingUuid;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
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

    public String getApiId() {
        return apiId;
    }

    public void setApiId(String apiId) {
        this.apiId = apiId;
    }

    public String getAuthzUser() {
        return authzUser;
    }

    public void setAuthzUser(String authzUser) {
        this.authzUser = authzUser;
    }

    public String getGatewayEnvironmentId() {
        return gatewayEnvironmentId;
    }

    public void setGatewayEnvironmentId(String gatewayEnvironmentId) {
        this.gatewayEnvironmentId = gatewayEnvironmentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    @Override
    public String toString() {
        return "ExternalCredential{" +
                "uuid='" + uuid + '\'' +
                ", mappingUuid='" + mappingUuid + '\'' +
                ", applicationId='" + applicationId + '\'' +
                ", apiId='" + apiId + '\'' +
                ", authzUser='" + authzUser + '\'' +
                ", gatewayEnvironmentId='" + gatewayEnvironmentId + '\'' +
                ", name='" + name + '\'' +
                ", externalSubscriptionId='" + externalSubscriptionId + '\'' +
                ", createdTime=" + createdTime +
                ", lastUpdatedTime=" + lastUpdatedTime +
                '}';
    }
}
