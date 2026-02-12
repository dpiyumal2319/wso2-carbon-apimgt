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

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a single subscription plan/tier option from a gateway.
 * Gateway-agnostic representation - can be used by AWS (usage plans),
 * Azure (subscription tiers), Kong (rate limit plans), etc.
 */
public class SubscriptionPlan {

    private String id;
    private String name;
    private String description;
    private Map<String, String> limits;

    public SubscriptionPlan() {
        this.limits = new HashMap<>();
    }

    public SubscriptionPlan(String id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.limits = new HashMap<>();
    }

    public SubscriptionPlan(String id, String name, String description, Map<String, String> limits) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.limits = limits != null ? limits : new HashMap<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Map<String, String> getLimits() {
        return limits;
    }

    public void setLimits(Map<String, String> limits) {
        this.limits = limits;
    }

    public void addLimit(String key, String value) {
        this.limits.put(key, value);
    }
}
