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

import org.wso2.carbon.apimgt.api.model.schema.options.SubscriptionPlans;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

/**
 * Registry mapping schema names to {@link SubscriptionOptionsBody} parsers.
 * <p>
 * Used by {@link org.wso2.carbon.apimgt.api.model.FederatedSubscriptionOptions#materializeBody()}
 * to convert raw-string-backed instances (deserialized from DB) into typed objects.
 * New schemas call {@link #register} — no changes to filtering/curation logic required.
 * </p>
 */
public final class SubscriptionOptionsBodyRegistry {

    private static final Map<String, Function<String, SubscriptionOptionsBody>> parsers =
            new ConcurrentHashMap<>();

    static {
        parsers.put(SubscriptionPlans.SCHEMA_NAME, SubscriptionPlans::fromJson);
    }

    private SubscriptionOptionsBodyRegistry() {
    }

    /**
     * Registers a parser for the given schema name.
     *
     * @param schemaName schema name (e.g., "subscription-plans")
     * @param parser     function that parses a JSON string into a typed body
     */
    public static void register(String schemaName, Function<String, SubscriptionOptionsBody> parser) {
        parsers.put(schemaName, parser);
    }

    /**
     * Parses a JSON body into a typed {@link SubscriptionOptionsBody} using the registered parser.
     *
     * @param schemaName schema name identifying the body type
     * @param bodyJson   JSON body to parse
     * @return typed body, or {@code null} if no parser is registered for the schema name
     */
    public static SubscriptionOptionsBody parse(String schemaName, String bodyJson) {
        Function<String, SubscriptionOptionsBody> parser = parsers.get(schemaName);
        return parser != null ? parser.apply(bodyJson) : null;
    }
}
