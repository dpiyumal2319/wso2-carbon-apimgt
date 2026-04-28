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

package org.wso2.carbon.apimgt.api;

/**
 * Shared property keys for optional federated API key connector metadata.
 */
public final class FederatedApiKeyConnectorPropertyKeys {

    public static final String API_NAME = "api.name";
    public static final String API_UUID = "api.uuid";
    public static final String AUTHZ_USER = "authz.user";
    public static final String LOCAL_POLICY_ID = "local.policy.id";
    public static final String ORGANIZATION_ID = "organization.id";
    public static final String VALIDITY_PERIOD = "key.validity.period";
    public static final String PERMITTED_IP = "key.permitted.ip";
    public static final String PERMITTED_REFERER = "key.permitted.referer";

    private FederatedApiKeyConnectorPropertyKeys() {
    }
}
