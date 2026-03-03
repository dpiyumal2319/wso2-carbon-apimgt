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
 * Subscription options body supporting multiple independent option categories.
 * Schema: "option-groups"
 * <p>
 * Each {@link OptionGroup} is an independent selection category (e.g., "Access Group",
 * "Rate Limit Tier"). Publisher curation controls:
 * <ul>
 *   <li>{@link OptionGroup#required} — whether developers must select from a group</li>
 *   <li>{@link OptionItem#enabled} — whether an item is visible to developers</li>
 * </ul>
 * </p>
 * <p>
 * Generic design: not tied to Kong or any specific gateway. Suitable for any gateway
 * that exposes multiple independent selection dimensions at subscription time.
 * </p>
 */
public class OptionGroups implements SubscriptionOptionsBody {

    public static final String SCHEMA_NAME = SubscriptionOptionsBody.SCHEMA_OPTION_GROUPS;

    private List<OptionGroup> groups;

    private static final Gson gson = new Gson();

    public OptionGroups() {
        this.groups = new ArrayList<>();
    }

    public OptionGroups(List<OptionGroup> groups) {
        this.groups = groups != null ? groups : new ArrayList<>();
    }

    @Override
    public String getSchemaName() {
        return SCHEMA_NAME;
    }

    @Override
    public String toJson() {
        return gson.toJson(this);
    }

    public static OptionGroups fromJson(String json) throws JsonSyntaxException {
        return gson.fromJson(json, OptionGroups.class);
    }

    public List<OptionGroup> getGroups() {
        return groups;
    }

    public void setGroups(List<OptionGroup> groups) {
        this.groups = groups;
    }

    public void addGroup(OptionGroup group) {
        this.groups.add(group);
    }

    /**
     * Removes disabled items from each group; removes groups that become empty after filtering.
     */
    @Override
    public void filterDisabled() {
        List<OptionGroup> filteredGroups = new ArrayList<>();
        for (OptionGroup group : groups) {
            List<OptionItem> enabledItems = new ArrayList<>();
            for (OptionItem item : group.getItems()) {
                if (item.isEnabled()) {
                    enabledItems.add(item);
                }
            }
            if (!enabledItems.isEmpty()) {
                group.setItems(enabledItems);
                filteredGroups.add(group);
            }
        }
        this.groups = filteredGroups;
    }

    /**
     * Applies publisher curation selections.
     * <p>
     * Expected format:
     * <pre>
     * [
     *   {
     *     "groupId": "acl-groups",
     *     "required": true,
     *     "items": [
     *       {"itemId": "premium", "enabled": true},
     *       {"itemId": "basic", "enabled": false}
     *     ]
     *   }
     * ]
     * </pre>
     * </p>
     *
     * @param selectionsJson JSON array of group curation entries
     */
    @Override
    public void applyCuration(String selectionsJson) {
        if (selectionsJson == null) return;
        JsonArray selections = JsonParser.parseString(selectionsJson).getAsJsonArray();

        // Build lookup maps from curation payload
        Map<String, Boolean> groupRequiredMap = new HashMap<>();
        Map<String, Map<String, Boolean>> groupItemEnabledMap = new HashMap<>();

        for (JsonElement el : selections) {
            JsonObject groupEntry = el.getAsJsonObject();
            String groupId = groupEntry.get("groupId").getAsString();

            if (groupEntry.has("required")) {
                groupRequiredMap.put(groupId, groupEntry.get("required").getAsBoolean());
            }

            if (groupEntry.has("items") && groupEntry.get("items").isJsonArray()) {
                Map<String, Boolean> itemEnabledMap = new HashMap<>();
                for (JsonElement itemEl : groupEntry.get("items").getAsJsonArray()) {
                    JsonObject itemEntry = itemEl.getAsJsonObject();
                    itemEnabledMap.put(
                            itemEntry.get("itemId").getAsString(),
                            itemEntry.get("enabled").getAsBoolean()
                    );
                }
                groupItemEnabledMap.put(groupId, itemEnabledMap);
            }
        }

        // Apply to groups
        for (OptionGroup group : groups) {
            if (groupRequiredMap.containsKey(group.getGroupId())) {
                group.setRequired(groupRequiredMap.get(group.getGroupId()));
            }
            Map<String, Boolean> itemEnabledMap = groupItemEnabledMap.get(group.getGroupId());
            if (itemEnabledMap != null) {
                for (OptionItem item : group.getItems()) {
                    if (itemEnabledMap.containsKey(item.getId())) {
                        item.setEnabled(itemEnabledMap.get(item.getId()));
                    }
                }
            }
        }
    }

    /**
     * Serializes only gateway-native fields — excludes publisher curation fields
     * ({@code enabled} on items, {@code required} on groups).
     * Used for staleness hash computation.
     */
    @Override
    public String toGatewayNativeJson() {
        JsonArray groupsArray = new JsonArray();
        for (OptionGroup group : groups) {
            JsonObject g = new JsonObject();
            g.addProperty("groupId", group.getGroupId());
            g.addProperty("groupName", group.getGroupName());
            // 'required' intentionally excluded — publisher curation only

            JsonArray itemsArray = new JsonArray();
            for (OptionItem item : group.getItems()) {
                JsonObject i = new JsonObject();
                i.addProperty("id", item.getId());
                i.addProperty("name", item.getName());
                if (item.getDescription() != null) {
                    i.addProperty("description", item.getDescription());
                }
                if (item.getProperties() != null && !item.getProperties().isEmpty()) {
                    i.add("properties", gson.toJsonTree(item.getProperties()));
                }
                // 'enabled' intentionally excluded — publisher curation only
                itemsArray.add(i);
            }
            g.add("items", itemsArray);
            groupsArray.add(g);
        }

        JsonObject root = new JsonObject();
        root.add("groups", groupsArray);
        return gson.toJson(root);
    }
}
