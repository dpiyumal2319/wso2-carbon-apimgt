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
 * Represents a single selectable item within an {@link OptionGroup}.
 * <p>
 * Gateway-agnostic — can model ACL groups, consumer groups, rate limit tiers,
 * or any other selectable entity a gateway exposes.
 * </p>
 */
public class OptionItem {

    private String id;
    private String name;
    private String description;
    private Map<String, String> properties;
    private boolean enabled = true; // publisher curation flag — not sent to gateway

    public OptionItem() {
        this.properties = new HashMap<>();
    }

    public OptionItem(String id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.properties = new HashMap<>();
    }

    public OptionItem(String id, String name, String description, Map<String, String> properties) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.properties = properties != null ? properties : new HashMap<>();
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

    public Map<String, String> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }

    public void addProperty(String key, String value) {
        this.properties.put(key, value);
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
