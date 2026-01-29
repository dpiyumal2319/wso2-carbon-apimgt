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
 * Stores references to external gateway entities and masked credential information
 * for retrieval and display purposes.
 * </p>
 */
public class SubscriptionExternalMapping {

    /**
     * WSO2 Subscription UUID (primary key part 1).
     */
    private String subscriptionUuid;

    /**
     * Gateway Environment UUID (primary key part 2).
     */
    private String gatewayEnvironmentId;

    /**
     * External subscription identifier in the gateway.
     */
    private String externalSubscriptionId;

    /**
     * External container identifier (e.g., Usage Plan ID, Product ID, Consumer ID).
     */
    private String externalContainerId;

    /**
     * Masked credential reference for display.
     * Format: "••••••••ab12" (showing last 4 chars)
     */
    private String credentialReference;

    /**
     * Reference artifact as JSON blob.
     * Contains gateway-specific metadata (invocation instructions, headers, URLs, etc.).
     */
    private String referenceArtifact;

    /**
     * Timestamp when the mapping was created.
     */
    private Timestamp createdTime;

    /**
     * Timestamp when the mapping was last updated.
     */
    private Timestamp lastUpdatedTime;

    /**
     * Default constructor.
     */
    public SubscriptionExternalMapping() {
    }

    /**
     * Constructor with primary key fields.
     *
     * @param subscriptionUuid      WSO2 Subscription UUID
     * @param gatewayEnvironmentId  Gateway Environment UUID
     */
    public SubscriptionExternalMapping(String subscriptionUuid, String gatewayEnvironmentId) {
        this.subscriptionUuid = subscriptionUuid;
        this.gatewayEnvironmentId = gatewayEnvironmentId;
    }

    /**
     * Gets the WSO2 Subscription UUID.
     *
     * @return The subscription UUID
     */
    public String getSubscriptionUuid() {
        return subscriptionUuid;
    }

    /**
     * Sets the WSO2 Subscription UUID.
     *
     * @param subscriptionUuid The subscription UUID to set
     */
    public void setSubscriptionUuid(String subscriptionUuid) {
        this.subscriptionUuid = subscriptionUuid;
    }

    /**
     * Gets the Gateway Environment UUID.
     *
     * @return The gateway environment ID
     */
    public String getGatewayEnvironmentId() {
        return gatewayEnvironmentId;
    }

    /**
     * Sets the Gateway Environment UUID.
     *
     * @param gatewayEnvironmentId The gateway environment ID to set
     */
    public void setGatewayEnvironmentId(String gatewayEnvironmentId) {
        this.gatewayEnvironmentId = gatewayEnvironmentId;
    }

    /**
     * Gets the external subscription identifier.
     *
     * @return The external subscription ID
     */
    public String getExternalSubscriptionId() {
        return externalSubscriptionId;
    }

    /**
     * Sets the external subscription identifier.
     *
     * @param externalSubscriptionId The external subscription ID to set
     */
    public void setExternalSubscriptionId(String externalSubscriptionId) {
        this.externalSubscriptionId = externalSubscriptionId;
    }

    /**
     * Gets the external container identifier.
     *
     * @return The external container ID
     */
    public String getExternalContainerId() {
        return externalContainerId;
    }

    /**
     * Sets the external container identifier.
     *
     * @param externalContainerId The external container ID to set
     */
    public void setExternalContainerId(String externalContainerId) {
        this.externalContainerId = externalContainerId;
    }

    /**
     * Gets the masked credential reference.
     *
     * @return The credential reference
     */
    public String getCredentialReference() {
        return credentialReference;
    }

    /**
     * Sets the masked credential reference.
     *
     * @param credentialReference The credential reference to set
     */
    public void setCredentialReference(String credentialReference) {
        this.credentialReference = credentialReference;
    }

    /**
     * Gets the reference artifact as JSON.
     *
     * @return The reference artifact
     */
    public String getReferenceArtifact() {
        return referenceArtifact;
    }

    /**
     * Sets the reference artifact as JSON.
     *
     * @param referenceArtifact The reference artifact to set
     */
    public void setReferenceArtifact(String referenceArtifact) {
        this.referenceArtifact = referenceArtifact;
    }

    /**
     * Gets the creation timestamp.
     *
     * @return The created time
     */
    public Timestamp getCreatedTime() {
        return createdTime;
    }

    /**
     * Sets the creation timestamp.
     *
     * @param createdTime The created time to set
     */
    public void setCreatedTime(Timestamp createdTime) {
        this.createdTime = createdTime;
    }

    /**
     * Gets the last updated timestamp.
     *
     * @return The last updated time
     */
    public Timestamp getLastUpdatedTime() {
        return lastUpdatedTime;
    }

    /**
     * Sets the last updated timestamp.
     *
     * @param lastUpdatedTime The last updated time to set
     */
    public void setLastUpdatedTime(Timestamp lastUpdatedTime) {
        this.lastUpdatedTime = lastUpdatedTime;
    }

    @Override
    public String toString() {
        return "SubscriptionExternalMapping{" +
                "subscriptionUuid='" + subscriptionUuid + '\'' +
                ", gatewayEnvironmentId='" + gatewayEnvironmentId + '\'' +
                ", externalSubscriptionId='" + externalSubscriptionId + '\'' +
                ", externalContainerId='" + externalContainerId + '\'' +
                ", credentialReference='[MASKED]'" +
                ", createdTime=" + createdTime +
                ", lastUpdatedTime=" + lastUpdatedTime +
                '}';
    }
}
