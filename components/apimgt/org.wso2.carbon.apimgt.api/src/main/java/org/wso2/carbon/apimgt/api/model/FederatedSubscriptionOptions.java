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

import org.wso2.carbon.apimgt.api.model.schema.SubscriptionOptionsBody;
import org.wso2.carbon.apimgt.api.model.schema.SubscriptionOptionsBodyRegistry;

/**
 * Represents subscription options available from an external gateway.
 * <p>
 * The {@code body} field is a typed {@link SubscriptionOptionsBody} whose schema name
 * is derived from the implementation type, preventing mismatches.
 * </p>
 */
public class FederatedSubscriptionOptions {

    /**
     * Typed subscription options body. Schema name is derived from the body type.
     */
    private SubscriptionOptionsBody body;

    // Used when deserialized without a typed body (e.g. from snapshot JSON)
    private String rawSchemaName;
    private String rawBodyJson;

    public String getSchemaName() {
        if (body != null) {
            return body.getSchemaName();
        }
        return rawSchemaName;
    }

    public String getBodyAsJson() {
        if (body != null) {
            return body.toJson();
        }
        return rawBodyJson;
    }

    public SubscriptionOptionsBody getBody() {
        return body;
    }

    public void setBody(SubscriptionOptionsBody body) {
        this.body = body;
    }

    /** Constructs a raw-string-backed instance for use during snapshot deserialization. */
    public static FederatedSubscriptionOptions fromRawJson(String schemaName, String bodyJson) {
        FederatedSubscriptionOptions opts = new FederatedSubscriptionOptions();
        opts.rawSchemaName = schemaName;
        opts.rawBodyJson = bodyJson;
        return opts;
    }

    /**
     * Resolves a raw-string-backed instance into a typed body using {@link SubscriptionOptionsBodyRegistry}.
     * No-op if the body is already typed or if no parser is registered for the schema name.
     */
    public void materializeBody() {
        if (body == null && rawSchemaName != null && rawBodyJson != null) {
            body = SubscriptionOptionsBodyRegistry.parse(rawSchemaName, rawBodyJson);
            if (body != null) {
                rawSchemaName = null;
                rawBodyJson = null;
            }
        }
    }
}
