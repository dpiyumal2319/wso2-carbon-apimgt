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
 * Represents a credential returned from an external gateway subscription.
 * <p>
 * The {@code body} field carries an opaque JSON string whose structure is defined
 * by each gateway connector. The backend never parses this body — only the connector
 * (producer) and the frontend (consumer) understand its schema.
 * <p>
 * On CREATE or REGENERATE, the body contains the full credential value (masked = false).
 * On GET (from reference artifact), the body contains a masked credential (masked = true).
 * </p>
 */
public class FederatedCredential {

    /**
     * Opaque JSON body containing credential details.
     * Structure is connector-specific (e.g., credentialType, value, headerName, timestamps).
     */
    private String body;

    /**
     * The external subscription identifier in the gateway.
     */
    private String externalSubscriptionId;

    /**
     * Whether the gateway supports retrieving the credential value after creation.
     */
    private boolean valueRetrievable;

    /**
     * Indicates whether the credential value in body is masked.
     */
    private boolean masked;

    public FederatedCredential() {
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getExternalSubscriptionId() {
        return externalSubscriptionId;
    }

    public void setExternalSubscriptionId(String externalSubscriptionId) {
        this.externalSubscriptionId = externalSubscriptionId;
    }

    public boolean isValueRetrievable() {
        return valueRetrievable;
    }

    public void setValueRetrievable(boolean valueRetrievable) {
        this.valueRetrievable = valueRetrievable;
    }

    public boolean isMasked() {
        return masked;
    }

    public void setMasked(boolean masked) {
        this.masked = masked;
    }
}
