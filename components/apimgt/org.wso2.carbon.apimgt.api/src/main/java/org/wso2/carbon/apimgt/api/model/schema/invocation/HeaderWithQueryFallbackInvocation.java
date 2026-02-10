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
 * Invocation body for header-based invocation with query parameter fallback
 * (e.g., Azure APIM with Ocp-Apim-Subscription-Key header or subscription-key query param).
 * Schema: "header-with-query-fallback"
 */
public class HeaderWithQueryFallbackInvocation implements InvocationBody {

    public static final String SCHEMA_NAME = "header-with-query-fallback";

    private String headerName;
    private String queryParamName;
    private String baseUrl;
    private String basePath;
    private String curlExampleHeader;
    private String curlExampleQuery;
    private String notes;

    private static final Gson gson = new Gson();

    public HeaderWithQueryFallbackInvocation() {
    }

    public HeaderWithQueryFallbackInvocation(String headerName, String queryParamName, String baseUrl,
                                              String basePath, String curlExampleHeader,
                                              String curlExampleQuery, String notes) {
        this.headerName = headerName;
        this.queryParamName = queryParamName;
        this.baseUrl = baseUrl;
        this.basePath = basePath;
        this.curlExampleHeader = curlExampleHeader;
        this.curlExampleQuery = curlExampleQuery;
        this.notes = notes;
    }

    @Override
    public String getSchemaName() {
        return SCHEMA_NAME;
    }

    @Override
    public String toJson() {
        return gson.toJson(this);
    }

    public static HeaderWithQueryFallbackInvocation fromJson(String json) throws JsonSyntaxException {
        return gson.fromJson(json, HeaderWithQueryFallbackInvocation.class);
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
