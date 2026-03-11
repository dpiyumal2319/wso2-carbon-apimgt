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

import java.sql.Timestamp;

/**
 * Federation configuration for an API on an external gateway (AM_API_FEDERATION_CONFIG).
 * Persisted: subscriptionEnabled, publisherCuratedConfig, gatewaySnapshotHash (last-acknowledged).
 * Transient: liveGatewaySnapshot, liveSnapshotHash, stale — populated on GET via live gateway fetch.
 */
public class ApiFederationConfig {

    private String apiUuid;
    private String gatewayEnvId;
    private SubscriptionSupportInfo publisherCuratedConfig;
    private String gatewaySnapshotHash;
    private Timestamp createdTime;
    private Timestamp lastUpdatedTime;
    private Timestamp publisherReviewedTime;

    // Transient — not stored in DB
    private SubscriptionSupportInfo liveGatewaySnapshot;
    private String liveSnapshotHash;
    private boolean stale;
    private boolean subscriptionSupport;

    public ApiFederationConfig() {
    }

    public ApiFederationConfig(String apiUuid, String gatewayEnvId) {
        this.apiUuid = apiUuid;
        this.gatewayEnvId = gatewayEnvId;
    }

    public String getApiUuid() {
        return apiUuid;
    }

    public void setApiUuid(String apiUuid) {
        this.apiUuid = apiUuid;
    }

    public String getGatewayEnvId() {
        return gatewayEnvId;
    }

    public void setGatewayEnvId(String gatewayEnvId) {
        this.gatewayEnvId = gatewayEnvId;
    }

    public SubscriptionSupportInfo getLiveGatewaySnapshot() {
        return liveGatewaySnapshot;
    }

    public void setLiveGatewaySnapshot(SubscriptionSupportInfo liveGatewaySnapshot) {
        this.liveGatewaySnapshot = liveGatewaySnapshot;
    }

    public String getLiveSnapshotHash() {
        return liveSnapshotHash;
    }

    public void setLiveSnapshotHash(String liveSnapshotHash) {
        this.liveSnapshotHash = liveSnapshotHash;
    }

    public SubscriptionSupportInfo getPublisherCuratedConfig() {
        return publisherCuratedConfig;
    }

    public void setPublisherCuratedConfig(SubscriptionSupportInfo publisherCuratedConfig) {
        this.publisherCuratedConfig = publisherCuratedConfig;
    }

    public String getGatewaySnapshotHash() {
        return gatewaySnapshotHash;
    }

    public void setGatewaySnapshotHash(String gatewaySnapshotHash) {
        this.gatewaySnapshotHash = gatewaySnapshotHash;
    }

    public boolean isStale() {
        return stale;
    }

    public void setStale(boolean stale) {
        this.stale = stale;
    }

    public boolean isSubscriptionSupport() {
        return subscriptionSupport;
    }

    public void setSubscriptionSupport(boolean subscriptionSupport) {
        this.subscriptionSupport = subscriptionSupport;
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

    public Timestamp getPublisherReviewedTime() {
        return publisherReviewedTime;
    }

    public void setPublisherReviewedTime(Timestamp publisherReviewedTime) {
        this.publisherReviewedTime = publisherReviewedTime;
    }
}
