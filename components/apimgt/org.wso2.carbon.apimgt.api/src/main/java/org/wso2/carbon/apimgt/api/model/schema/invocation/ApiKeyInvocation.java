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

package org.wso2.carbon.apimgt.api.model.schema.invocation;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.wso2.carbon.apimgt.api.model.schema.InvocationBody;

/**
 * Unified invocation body for API key-based invocation across all gateways.
 * <p>
 * Boolean flags indicate which delivery methods are enabled for the API key:
 * <ul>
 *   <li>{@code headerEnabled} - key can be sent via HTTP header</li>
 *   <li>{@code queryParamEnabled} - key can be sent as a query parameter</li>
 *   <li>{@code bodyEnabled} - key can be sent in the request body</li>
 * </ul>
 * If all flags are false, the frontend should show an error (no valid invocation method).
 * </p>
 * Schema: "api-key-invocation"
 */
public class ApiKeyInvocation implements InvocationBody {

    public static final String SCHEMA_NAME = "api-key-invocation";

    private boolean headerEnabled;
    private boolean queryParamEnabled;
    private boolean bodyEnabled;
    private String headerName;
    private String queryParamName;
    private String bodyParamName;
    private String baseUrl;
    private String basePath;
    private String curlExampleHeader;
    private String curlExampleQuery;
    private String notes;

    private static final Gson gson = new Gson();

    public ApiKeyInvocation() {
    }

    @Override
    public String getSchemaName() {
        return SCHEMA_NAME;
    }

    @Override
    public String toJson() {
        return gson.toJson(this);
    }

    public static ApiKeyInvocation fromJson(String json) throws JsonSyntaxException {
        return gson.fromJson(json, ApiKeyInvocation.class);
    }

    public boolean isHeaderEnabled() {
        return headerEnabled;
    }

    public void setHeaderEnabled(boolean headerEnabled) {
        this.headerEnabled = headerEnabled;
    }

    public boolean isQueryParamEnabled() {
        return queryParamEnabled;
    }

    public void setQueryParamEnabled(boolean queryParamEnabled) {
        this.queryParamEnabled = queryParamEnabled;
    }

    public boolean isBodyEnabled() {
        return bodyEnabled;
    }

    public void setBodyEnabled(boolean bodyEnabled) {
        this.bodyEnabled = bodyEnabled;
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

    public String getBodyParamName() {
        return bodyParamName;
    }

    public void setBodyParamName(String bodyParamName) {
        this.bodyParamName = bodyParamName;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getBasePath() {
        return basePath;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

    public String getCurlExampleHeader() {
        return curlExampleHeader;
    }

    public void setCurlExampleHeader(String curlExampleHeader) {
        this.curlExampleHeader = curlExampleHeader;
    }

    public String getCurlExampleQuery() {
        return curlExampleQuery;
    }

    public void setCurlExampleQuery(String curlExampleQuery) {
        this.curlExampleQuery = curlExampleQuery;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
