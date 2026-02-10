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

import org.wso2.carbon.apimgt.api.model.schema.InvocationBody;

/**
 * Contains instructions for invoking an API through an external gateway.
 * <p>
 * The {@code body} field is a typed {@link InvocationBody} whose schema name is derived
 * from the implementation type, preventing mismatches between body content and schema.
 * </p>
 */
public class InvocationInstruction {

    /**
     * Typed invocation body. Schema name is derived from the body type.
     */
    private InvocationBody body;

    public InvocationInstruction() {
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

    public InvocationBody getBody() {
        return body;
    }

    public void setBody(InvocationBody body) {
        this.body = body;
    }
}
