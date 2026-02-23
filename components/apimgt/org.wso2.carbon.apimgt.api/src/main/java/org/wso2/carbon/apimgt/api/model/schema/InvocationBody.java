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

package org.wso2.carbon.apimgt.api.model.schema;

/**
 * Interface for invocation instruction body types used in federated subscriptions.
 * <p>
 * Each implementation represents a specific invocation schema (e.g., api-key-invocation).
 * The schema name is derived from the type itself.
 * </p>
 */
public interface InvocationBody {

    /**
     * Returns the schema name for this invocation type.
     * Used by the frontend to select the appropriate renderer.
     *
     * @return schema name (e.g., "api-key-invocation")
     */
    String getSchemaName();

    /**
     * Serializes this invocation body to a JSON string.
     *
     * @return JSON representation of the invocation body
     */
    String toJson();

    /**
     * Serializes only gateway-native fields (excluding any publisher curation fields).
     * Used for staleness hash computation. Default delegates to {@link #toJson()}.
     *
     * @return JSON representation of gateway-native fields only
     */
    default String toGatewayNativeJson() { return toJson(); }
}
