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

package org.wso2.carbon.apimgt.api.model.schema.credential;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.wso2.carbon.apimgt.api.model.schema.CredentialBody;

/**
 * Credential body for opaque API key credentials (e.g., AWS API Gateway).
 * Schema: "opaque-api-key"
 */
public class OpaqueApiKeyCredential implements CredentialBody {

    public static final String SCHEMA_NAME = "opaque-api-key";

    private String headerName;
    private String value;

    private static final Gson gson = new Gson();

    public OpaqueApiKeyCredential() {
    }

    public OpaqueApiKeyCredential(String headerName, String value) {
        this.headerName = headerName;
        this.value = value;
    }

    @Override
    public String getSchemaName() {
        return SCHEMA_NAME;
    }

    @Override
    public String toJson() {
        return gson.toJson(this);
    }

    @Override
    public CredentialBody masked() {
        OpaqueApiKeyCredential masked = new OpaqueApiKeyCredential();
        masked.headerName = this.headerName;
        masked.value = maskValue(this.value);
        return masked;
    }

    public static OpaqueApiKeyCredential fromJson(String json) throws JsonSyntaxException {
        return gson.fromJson(json, OpaqueApiKeyCredential.class);
    }

    private String maskValue(String val) {
        if (val == null || val.length() <= 4) {
            return val;
        }
        int visibleChars = 4;
        return "*".repeat(val.length() - visibleChars) + val.substring(val.length() - visibleChars);
    }

    public String getHeaderName() {
        return headerName;
    }

    public void setHeaderName(String headerName) {
        this.headerName = headerName;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
