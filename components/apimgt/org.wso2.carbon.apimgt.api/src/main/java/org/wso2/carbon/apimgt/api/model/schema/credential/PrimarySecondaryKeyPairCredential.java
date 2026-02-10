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
 * Credential body for primary-secondary key pair credentials (e.g., Azure APIM).
 * Schema: "primary-secondary-key-pair"
 */
public class PrimarySecondaryKeyPairCredential implements CredentialBody {

    public static final String SCHEMA_NAME = "primary-secondary-key-pair";

    private String headerName;
    private String queryParamName;
    private String primaryKey;
    private String secondaryKey;
    private String createdTime;

    private static final Gson gson = new Gson();

    public PrimarySecondaryKeyPairCredential() {
    }

    public PrimarySecondaryKeyPairCredential(String headerName, String queryParamName,
                                              String primaryKey, String secondaryKey,
                                              String createdTime) {
        this.headerName = headerName;
        this.queryParamName = queryParamName;
        this.primaryKey = primaryKey;
        this.secondaryKey = secondaryKey;
        this.createdTime = createdTime;
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
        PrimarySecondaryKeyPairCredential masked = new PrimarySecondaryKeyPairCredential();
        masked.headerName = this.headerName;
        masked.queryParamName = this.queryParamName;
        masked.primaryKey = maskKey(this.primaryKey);
        masked.secondaryKey = maskKey(this.secondaryKey);
        masked.createdTime = this.createdTime;
        return masked;
    }

    public static PrimarySecondaryKeyPairCredential fromJson(String json) throws JsonSyntaxException {
        return gson.fromJson(json, PrimarySecondaryKeyPairCredential.class);
    }

    private String maskKey(String key) {
        if (key == null || key.length() <= 4) {
            return key;
        }
        int visibleChars = 4;
        return "*".repeat(key.length() - visibleChars) + key.substring(key.length() - visibleChars);
    }

    public String getHeaderName() {
        return headerName;
    }

    public void setHeaderName(String headerName) {
        this.headerName = headerName;
    }

    public String getQueryParamName() {
        return queryParamName;
    }

    public void setQueryParamName(String queryParamName) {
        this.queryParamName = queryParamName;
    }

    public String getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(String primaryKey) {
        this.primaryKey = primaryKey;
    }

    public String getSecondaryKey() {
        return secondaryKey;
    }

    public void setSecondaryKey(String secondaryKey) {
        this.secondaryKey = secondaryKey;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }
}
