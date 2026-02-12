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
 * Interface for subscription options body types used in federated subscriptions.
 * <p>
 * Each implementation represents a specific options schema (e.g., subscription-plans).
 * The schema name is derived from the type itself.
 * </p>
 */
public interface SubscriptionOptionsBody {

    /**
     * Returns the schema name for this options type.
     * Used by the frontend to select the appropriate renderer.
     *
     * @return schema name (e.g., "subscription-plans")
     */
    String getSchemaName();

    /**
     * Serializes this options body to a JSON string.
     *
     * @return JSON representation of the options body
     */
    String toJson();
}
