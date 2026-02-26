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

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a named category of selectable {@link OptionItem}s within an {@link OptionGroup} body.
 * <p>
 * The {@code required} flag is a publisher curation concern — when {@code true}, developers
 * must select an item from this group before creating a subscription. Publishers can toggle
 * this per group. Gateway agents set the default value.
 * </p>
 */
public class OptionGroup {

    private String groupId;
    private String groupName;
    private boolean required; // publisher-curable: whether developer must select from this group
    private List<OptionItem> items;

    public OptionGroup() {
        this.items = new ArrayList<>();
    }

    public OptionGroup(String groupId, String groupName, boolean required, List<OptionItem> items) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.required = required;
        this.items = items != null ? items : new ArrayList<>();
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public List<OptionItem> getItems() {
        return items;
    }

    public void setItems(List<OptionItem> items) {
        this.items = items;
    }

    public void addItem(OptionItem item) {
        this.items.add(item);
    }
}
