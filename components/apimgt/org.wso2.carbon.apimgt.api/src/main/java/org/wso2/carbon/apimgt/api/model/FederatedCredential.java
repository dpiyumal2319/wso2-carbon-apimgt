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

import org.wso2.carbon.apimgt.api.model.schema.CredentialBody;

/**
 * Represents a credential returned from an external gateway subscription.
 * <p>
 * The {@code body} field is a typed {@link CredentialBody} whose schema name is derived
 * from the implementation type, preventing mismatches between body content and schema.
 * The service layer interacts with the body only through the interface (getSchemaName,
 * toJson, masked) — it never accesses body-specific fields.
 * </p>
 * <p>
 * On CREATE or REGENERATE, the body contains the full credential value (masked = false).
 * On GET (from reference artifact), the body contains a masked credential (masked = true).
 * </p>
 */
public class FederatedCredential {

    /**
     * Typed credential body. Schema name is derived from the body type.
     */
    private CredentialBody body;

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

    /**
     * Returns the schema name derived from the body type.
     *
     * @return schema name, or null if body is null
     */
    public String getSchemaName() {
        return body != null ? body.getSchemaName() : null;
    }

    /**
     * Returns the body serialized as a JSON string.
     * Used by mapping utilities and reference artifact builders.
     *
     * @return JSON string, or null if body is null
     */
    public String getBodyAsJson() {
        return body != null ? body.toJson() : null;
    }

    public CredentialBody getBody() {
        return body;
    }

    public void setBody(CredentialBody body) {
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
