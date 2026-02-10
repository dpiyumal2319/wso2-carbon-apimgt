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

package org.wso2.carbon.apimgt.api.model.schema.options;

import org.wso2.carbon.apimgt.api.model.schema.SubscriptionOptionsBody;

/**
 * Subscription options body for tier/plan selection (e.g., AWS usage plans).
 * Schema: "tier-selector"
 * <p>
 * The body is stored as an opaque JSON string because the options structure
 * is gateway-specific (usage plans with throttle/quota details). The backend
 * passes this through without parsing — only the frontend renders it.
 * </p>
 */
public class TierSelectorOptions implements SubscriptionOptionsBody {

    public static final String SCHEMA_NAME = "tier-selector";

    private final String body;

    public TierSelectorOptions(String body) {
        this.body = body;
    }

    @Override
    public String getSchemaName() {
        return SCHEMA_NAME;
    }

    @Override
    public String toJson() {
        return body;
    }
}
