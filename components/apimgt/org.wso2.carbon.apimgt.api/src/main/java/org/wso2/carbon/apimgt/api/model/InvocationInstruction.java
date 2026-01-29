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

import java.util.HashMap;
import java.util.Map;

/**
 * Contains instructions for invoking an API through an external gateway.
 * <p>
 * Provides developers with all the information needed to make API calls,
 * including the credential header, endpoint URLs, and example commands.
 * </p>
 */
public class InvocationInstruction {

    /**
     * The gateway type (e.g., "AWS", "Azure", "Kong", "Envoy").
     */
    private String gatewayType;

    /**
     * The header name for the credential (e.g., "x-api-key", "Ocp-Apim-Subscription-Key").
     */
    private String headerName;

    /**
     * The base URL of the gateway (e.g., "https://api.example.com").
     */
    private String baseUrl;

    /**
     * The base path/context of the API (e.g., "/v1/users").
     */
    private String basePath;

    /**
     * Additional headers required for invocation.
     */
    private Map<String, String> additionalHeaders = new HashMap<>();

    /**
     * Example curl command for invoking the API.
     */
    private String curlExample;

    /**
     * Additional notes or instructions for the developer.
     */
    private String notes;

    /**
     * Default constructor.
     */
    public InvocationInstruction() {
    }

    /**
     * Constructor with essential fields.
     *
     * @param gatewayType The gateway type
     * @param headerName  The credential header name
     * @param baseUrl     The base URL
     * @param basePath    The base path
     */
    public InvocationInstruction(String gatewayType, String headerName, String baseUrl, String basePath) {
        this.gatewayType = gatewayType;
        this.headerName = headerName;
        this.baseUrl = baseUrl;
        this.basePath = basePath;
    }

    /**
     * Gets the gateway type.
     *
     * @return The gateway type
     */
    public String getGatewayType() {
        return gatewayType;
    }

    /**
     * Sets the gateway type.
     *
     * @param gatewayType The gateway type to set
     */
    public void setGatewayType(String gatewayType) {
        this.gatewayType = gatewayType;
    }

    /**
     * Gets the header name for the credential.
     *
     * @return The header name
     */
    public String getHeaderName() {
        return headerName;
    }

    /**
     * Sets the header name for the credential.
     *
     * @param headerName The header name to set
     */
    public void setHeaderName(String headerName) {
        this.headerName = headerName;
    }

    /**
     * Gets the base URL.
     *
     * @return The base URL
     */
    public String getBaseUrl() {
        return baseUrl;
    }

    /**
     * Sets the base URL.
     *
     * @param baseUrl The base URL to set
     */
    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    /**
     * Gets the base path.
     *
     * @return The base path
     */
    public String getBasePath() {
        return basePath;
    }

    /**
     * Sets the base path.
     *
     * @param basePath The base path to set
     */
    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

    /**
     * Gets the full endpoint URL (baseUrl + basePath).
     *
     * @return The full endpoint URL
     */
    public String getFullEndpointUrl() {
        if (baseUrl == null) {
            return basePath;
        }
        if (basePath == null) {
            return baseUrl;
        }

        String normalizedBase = baseUrl.endsWith("/") ? baseUrl.substring(0, baseUrl.length() - 1) : baseUrl;
        String normalizedPath = basePath.startsWith("/") ? basePath : "/" + basePath;
        return normalizedBase + normalizedPath;
    }

    /**
     * Gets the additional headers.
     *
     * @return Map of additional headers
     */
    public Map<String, String> getAdditionalHeaders() {
        return additionalHeaders;
    }

    /**
     * Sets the additional headers.
     *
     * @param additionalHeaders The additional headers to set
     */
    public void setAdditionalHeaders(Map<String, String> additionalHeaders) {
        this.additionalHeaders = additionalHeaders != null ? additionalHeaders : new HashMap<>();
    }

    /**
     * Adds an additional header.
     *
     * @param name  The header name
     * @param value The header value
     */
    public void addAdditionalHeader(String name, String value) {
        this.additionalHeaders.put(name, value);
    }

    /**
     * Gets the curl example.
     *
     * @return The curl example command
     */
    public String getCurlExample() {
        return curlExample;
    }

    /**
     * Sets the curl example.
     *
     * @param curlExample The curl example to set
     */
    public void setCurlExample(String curlExample) {
        this.curlExample = curlExample;
    }

    /**
     * Gets the notes.
     *
     * @return The notes
     */
    public String getNotes() {
        return notes;
    }

    /**
     * Sets the notes.
     *
     * @param notes The notes to set
     */
    public void setNotes(String notes) {
        this.notes = notes;
    }

    /**
     * Generates a curl example command for this invocation instruction.
     *
     * @param credentialValue The credential value to use in the example
     * @param httpMethod      The HTTP method (GET, POST, etc.)
     * @return The generated curl command
     */
    public String generateCurlExample(String credentialValue, String httpMethod) {
        StringBuilder curl = new StringBuilder();
        curl.append("curl");

        if (httpMethod != null && !httpMethod.equalsIgnoreCase("GET")) {
            curl.append(" -X ").append(httpMethod.toUpperCase());
        }

        curl.append(" \"").append(getFullEndpointUrl()).append("\"");

        if (headerName != null && credentialValue != null) {
            curl.append(" \\\n  -H \"").append(headerName).append(": ").append(credentialValue).append("\"");
        }

        for (Map.Entry<String, String> header : additionalHeaders.entrySet()) {
            curl.append(" \\\n  -H \"").append(header.getKey()).append(": ").append(header.getValue()).append("\"");
        }

        return curl.toString();
    }

    /**
     * Generates a curl example with a placeholder for the credential.
     *
     * @param httpMethod The HTTP method (GET, POST, etc.)
     * @return The generated curl command with placeholder
     */
    public String generateCurlExampleWithPlaceholder(String httpMethod) {
        return generateCurlExample("<YOUR_API_KEY>", httpMethod);
    }
}
