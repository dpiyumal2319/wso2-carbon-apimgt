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
 * Complete result object returned from the service layer to the controller for federated subscription operations.
 * Contains everything needed to build the DTO in a single mapping call.
 */
public class FederatedSubscriptionResult {

    private FederatedCredential credential;
    private InvocationInstruction invocationInstruction;
    private String gatewayType;          // "aws", "azure", etc.
    private String gatewayEnvironmentId;

    public FederatedSubscriptionResult() {
    }

    public FederatedSubscriptionResult(FederatedCredential credential, InvocationInstruction invocationInstruction,
                                       String gatewayType, String gatewayEnvironmentId) {
        this.credential = credential;
        this.invocationInstruction = invocationInstruction;
        this.gatewayType = gatewayType;
        this.gatewayEnvironmentId = gatewayEnvironmentId;
    }

    public FederatedCredential getCredential() {
        return credential;
    }

    public void setCredential(FederatedCredential credential) {
        this.credential = credential;
    }

    public InvocationInstruction getInvocationInstruction() {
        return invocationInstruction;
    }

    public void setInvocationInstruction(InvocationInstruction invocationInstruction) {
        this.invocationInstruction = invocationInstruction;
    }

    public String getGatewayType() {
        return gatewayType;
    }

    public void setGatewayType(String gatewayType) {
        this.gatewayType = gatewayType;
    }

    public String getGatewayEnvironmentId() {
        return gatewayEnvironmentId;
    }

    public void setGatewayEnvironmentId(String gatewayEnvironmentId) {
        this.gatewayEnvironmentId = gatewayEnvironmentId;
    }
}
