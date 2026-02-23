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

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import org.wso2.carbon.apimgt.api.model.schema.SubscriptionOptionsBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Subscription options body for tier/plan selection.
 * Schema: "subscription-plans"
 * <p>
 * Contains a list of typed {@link SubscriptionPlan} objects representing
 * gateway subscription options (AWS usage plans, Azure subscription tiers, etc.)
 * and an optionName for display (e.g., "Usage Plan", "Subscription Tier").
 * </p>
 */
public class SubscriptionPlans implements SubscriptionOptionsBody {

    public static final String SCHEMA_NAME = "subscription-plans";

    private String optionName;
    private List<SubscriptionPlan> plans;

    private static final Gson gson = new Gson();

    public SubscriptionPlans() {
        this.plans = new ArrayList<>();
    }

    public SubscriptionPlans(String optionName, List<SubscriptionPlan> plans) {
        this.optionName = optionName;
        this.plans = plans != null ? plans : new ArrayList<>();
    }

    @Override
    public String getSchemaName() {
        return SCHEMA_NAME;
    }

    @Override
    public String toJson() {
        return gson.toJson(this);
    }

    public static SubscriptionPlans fromJson(String json) throws JsonSyntaxException {
        return gson.fromJson(json, SubscriptionPlans.class);
    }

    public String getOptionName() {
        return optionName;
    }

    public void setOptionName(String optionName) {
        this.optionName = optionName;
    }

    public List<SubscriptionPlan> getPlans() {
        return plans;
    }

    public void setPlans(List<SubscriptionPlan> plans) {
        this.plans = plans;
    }

    public void addPlan(SubscriptionPlan plan) {
        this.plans.add(plan);
    }

    @Override
    public void filterDisabled() {
        plans.removeIf(p -> !p.isEnabled());
    }

    @Override
    public String toGatewayNativeJson() {
        JsonObject json = new JsonObject();
        json.addProperty("optionName", optionName);
        JsonArray plansArray = new JsonArray();
        for (SubscriptionPlan plan : plans) {
            JsonObject p = new JsonObject();
            p.addProperty("id", plan.getId());
            p.addProperty("name", plan.getName());
            p.addProperty("description", plan.getDescription());
            if (plan.getLimits() != null) {
                p.add("limits", gson.toJsonTree(plan.getLimits()));
            }
            // 'enabled' intentionally excluded — it's publisher curation, not gateway data
            plansArray.add(p);
        }
        json.add("plans", plansArray);
        return gson.toJson(json);
    }

    @Override
    public void applyCuration(String selectionsJson) {
        if (selectionsJson == null) return;
        JsonArray selections = JsonParser.parseString(selectionsJson).getAsJsonArray();
        Map<String, Boolean> enabledMap = new HashMap<>();
        for (JsonElement el : selections) {
            JsonObject obj = el.getAsJsonObject();
            enabledMap.put(obj.get("planId").getAsString(), obj.get("enabled").getAsBoolean());
        }
        for (SubscriptionPlan plan : plans) {
            if (enabledMap.containsKey(plan.getId())) {
                plan.setEnabled(enabledMap.get(plan.getId()));
            }
        }
    }
}
