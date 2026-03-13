/*
 * Copyright (c) 2025, WSO2 LLC. (http://www.wso2.org) All Rights Reserved.
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
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.wso2.carbon.apimgt.api.model;

/**
 * Represents a mapping between a local WSO2 subscription tier and a remote gateway plan.
 * Stored per gateway environment to allow admins to associate WSO2 business plans
 * (e.g., Unlimited, Gold) with the corresponding plans on the external gateway (e.g., AWS Usage Plans).
 * The remote plan reference is stored as an opaque JSON blob for gateway-specific flexibility.
 */
public class GatewayTierMapping {

    private String localTierName;
    private String remotePlanReference;

    public GatewayTierMapping() {
    }

    public GatewayTierMapping(String localTierName, String remotePlanReference) {
        this.localTierName = localTierName;
        this.remotePlanReference = remotePlanReference;
    }

    public String getLocalTierName() {
        return localTierName;
    }

    public void setLocalTierName(String localTierName) {
        this.localTierName = localTierName;
    }

    public String getRemotePlanReference() {
        return remotePlanReference;
    }

    public void setRemotePlanReference(String remotePlanReference) {
        this.remotePlanReference = remotePlanReference;
    }
}
