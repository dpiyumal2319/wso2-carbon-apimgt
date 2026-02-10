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
 * Interface for credential body types used in federated subscriptions.
 * <p>
 * Each implementation represents a specific credential schema (e.g., opaque API key,
 * primary-secondary key pair). The schema name is derived from the type itself,
 * preventing mismatches between body content and schema identifier.
 * </p>
 * <p>
 * The service layer interacts with credentials only through this interface,
 * enforcing the "backend never parses body" principle. Only connectors and
 * frontend understand the internal structure.
 * </p>
 */
public interface CredentialBody {

    /**
     * Returns the schema name for this credential type.
     * Used by the frontend to select the appropriate renderer.
     *
     * @return schema name (e.g., "opaque-api-key", "primary-secondary-key-pair")
     */
    String getSchemaName();

    /**
     * Serializes this credential body to a JSON string.
     *
     * @return JSON representation of the credential body
     */
    String toJson();

    /**
     * Returns a copy of this credential with sensitive values masked.
     * Used when storing credentials in reference artifacts.
     *
     * @return a new CredentialBody instance with masked values
     */
    CredentialBody masked();
}
