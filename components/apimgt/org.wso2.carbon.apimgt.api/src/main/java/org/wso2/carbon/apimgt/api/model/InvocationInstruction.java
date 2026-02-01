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
 * Contains instructions for invoking an API through an external gateway.
 * <p>
 * The {@code body} field carries an opaque JSON string whose structure is defined
 * by each gateway connector. The backend never parses this body — only the connector
 * (producer) and the frontend (consumer) understand its schema.
 * </p>
 */
public class InvocationInstruction {

    /**
     * Opaque JSON body containing invocation details.
     * Structure is connector-specific (e.g., headerName, baseUrl, basePath, curlExample, notes).
     */
    private String body;

    public InvocationInstruction() {
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
