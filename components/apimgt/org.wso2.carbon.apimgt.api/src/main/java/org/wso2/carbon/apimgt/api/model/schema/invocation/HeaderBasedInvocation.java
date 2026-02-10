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
 * Invocation body for header-based API invocation (e.g., AWS API Gateway with x-api-key).
 * Schema: "header-based"
 */
public class HeaderBasedInvocation implements InvocationBody {

    public static final String SCHEMA_NAME = "header-based";

    private String headerName;
    private String baseUrl;
    private String basePath;
    private String curlExampleHeader;

    private static final Gson gson = new Gson();

    public HeaderBasedInvocation() {
    }

    public HeaderBasedInvocation(String headerName, String baseUrl, String basePath, String curlExampleHeader) {
        this.headerName = headerName;
        this.baseUrl = baseUrl;
        this.basePath = basePath;
        this.curlExampleHeader = curlExampleHeader;
    }

    @Override
    public String getSchemaName() {
        return SCHEMA_NAME;
    }

    @Override
    public String toJson() {
        return gson.toJson(this);
    }

    public static HeaderBasedInvocation fromJson(String json) throws JsonSyntaxException {
        return gson.fromJson(json, HeaderBasedInvocation.class);
    }

    public String getHeaderName() {
        return headerName;
    }

    public void setHeaderName(String headerName) {
        this.headerName = headerName;
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
}
