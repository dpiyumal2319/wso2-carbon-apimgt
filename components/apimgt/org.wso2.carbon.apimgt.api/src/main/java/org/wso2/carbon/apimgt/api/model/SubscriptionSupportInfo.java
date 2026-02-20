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

package org.wso2.carbon.apimgt.api.model;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Subscription support information for an API on an external gateway.
 * Supports JSON serialization for live snapshot hashing; nested bodies are opaque {schemaName, body} pairs.
 */
public class SubscriptionSupportInfo {

    /**
     * Enum representing the subscription status of an API.
     */
    public enum SubscriptionStatus {
        OPEN,
        SECURED
    }

    private SubscriptionStatus status;
    private String[] supportedAuthTypes;
    private FederatedSubscriptionOptions subscriptionOptions;
    private InvocationInstruction invocationTemplate;

    public SubscriptionSupportInfo() {
    }

    private SubscriptionSupportInfo(Builder builder) {
        this.status = builder.status;
        this.supportedAuthTypes = builder.supportedAuthTypes;
        this.subscriptionOptions = builder.subscriptionOptions;
        this.invocationTemplate = builder.invocationTemplate;
    }

    public SubscriptionStatus getStatus() {
        return status;
    }

    public void setStatus(SubscriptionStatus status) {
        this.status = status;
    }

    public String[] getSupportedAuthTypes() {
        return supportedAuthTypes;
    }

    public void setSupportedAuthTypes(String[] supportedAuthTypes) {
        this.supportedAuthTypes = supportedAuthTypes;
    }

    public FederatedSubscriptionOptions getSubscriptionOptions() {
        return subscriptionOptions;
    }

    public void setSubscriptionOptions(FederatedSubscriptionOptions subscriptionOptions) {
        this.subscriptionOptions = subscriptionOptions;
    }

    public InvocationInstruction getInvocationTemplate() {
        return invocationTemplate;
    }

    public void setInvocationTemplate(InvocationInstruction invocationTemplate) {
        this.invocationTemplate = invocationTemplate;
    }

    /**
     * Filters out disabled subscription plans by delegating to the polymorphic body.
     * No-op if there are no subscription options.
     */
    public void filterDisabledPlans() {
        if (subscriptionOptions == null) return;
        subscriptionOptions.materializeBody();
        if (subscriptionOptions.getBody() != null) {
            subscriptionOptions.getBody().filterDisabled();
        }
    }

    /**
     * Applies publisher curation selections by delegating to the polymorphic body.
     * No-op if there are no subscription options or no selections provided.
     *
     * @param selectionsJson JSON array of curation selections (schema-specific format)
     */
    public void applyCuration(String selectionsJson) {
        if (subscriptionOptions == null || selectionsJson == null) return;
        subscriptionOptions.materializeBody();
        if (subscriptionOptions.getBody() != null) {
            subscriptionOptions.getBody().applyCuration(selectionsJson);
        }
    }

    /** Serializes to JSON; nested bodies flattened to {schemaName, body} pairs. */
    public String toJson() {
        JsonObject json = new JsonObject();
        json.addProperty("status", status != null ? status.name() : null);

        if (supportedAuthTypes != null) {
            JsonArray authTypes = new JsonArray();
            for (String type : supportedAuthTypes) {
                authTypes.add(type);
            }
            json.add("supportedAuthTypes", authTypes);
        }

        if (subscriptionOptions != null) {
            JsonObject opts = new JsonObject();
            opts.addProperty("schemaName", subscriptionOptions.getSchemaName());
            opts.addProperty("body", subscriptionOptions.getBodyAsJson());
            json.add("subscriptionOptions", opts);
        }

        if (invocationTemplate != null) {
            JsonObject tmpl = new JsonObject();
            tmpl.addProperty("schemaName", invocationTemplate.getSchemaName());
            tmpl.addProperty("body", invocationTemplate.getBodyAsJson());
            json.add("invocationTemplate", tmpl);
        }

        return new Gson().toJson(json);
    }

    /** Deserializes from JSON; nested objects are restored as raw-string-backed instances. */
    public static SubscriptionSupportInfo fromJson(String json) {
        JsonObject root = JsonParser.parseString(json).getAsJsonObject();

        Builder builder = new Builder();

        if (root.has("status") && !root.get("status").isJsonNull()) {
            builder.status(SubscriptionStatus.valueOf(root.get("status").getAsString()));
        }

        if (root.has("supportedAuthTypes") && !root.get("supportedAuthTypes").isJsonNull()) {
            JsonArray arr = root.getAsJsonArray("supportedAuthTypes");
            String[] types = new String[arr.size()];
            for (int i = 0; i < arr.size(); i++) {
                types[i] = arr.get(i).getAsString();
            }
            builder.supportedAuthTypes(types);
        }

        if (root.has("subscriptionOptions") && !root.get("subscriptionOptions").isJsonNull()) {
            JsonObject opts = root.getAsJsonObject("subscriptionOptions");
            String schemaName = opts.has("schemaName") ? opts.get("schemaName").getAsString() : null;
            String body = opts.has("body") && !opts.get("body").isJsonNull()
                    ? opts.get("body").getAsString() : null;
            builder.subscriptionOptions(
                    FederatedSubscriptionOptions.fromRawJson(schemaName, body));
        }

        if (root.has("invocationTemplate") && !root.get("invocationTemplate").isJsonNull()) {
            JsonObject tmpl = root.getAsJsonObject("invocationTemplate");
            String schemaName = tmpl.has("schemaName") ? tmpl.get("schemaName").getAsString() : null;
            String body = tmpl.has("body") && !tmpl.get("body").isJsonNull()
                    ? tmpl.get("body").getAsString() : null;
            builder.invocationTemplate(InvocationInstruction.fromRawJson(schemaName, body));
        }

        return builder.build();
    }

    public static class Builder {
        private SubscriptionStatus status;
        private String[] supportedAuthTypes;
        private FederatedSubscriptionOptions subscriptionOptions;
        private InvocationInstruction invocationTemplate;

        public Builder status(SubscriptionStatus status) {
            this.status = status;
            return this;
        }

        public Builder supportedAuthTypes(String[] supportedAuthTypes) {
            this.supportedAuthTypes = supportedAuthTypes;
            return this;
        }

        public Builder subscriptionOptions(FederatedSubscriptionOptions subscriptionOptions) {
            this.subscriptionOptions = subscriptionOptions;
            return this;
        }

        public Builder invocationTemplate(InvocationInstruction invocationTemplate) {
            this.invocationTemplate = invocationTemplate;
            return this;
        }

        public SubscriptionSupportInfo build() {
            return new SubscriptionSupportInfo(this);
        }
    }
}
