/*
 * Copyright (c) 2025 WSO2 LLC. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 LLC. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.wso2.carbon.apimgt.api.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a paginated result set of discovered applications.
 * This class provides pagination metadata along with the discovered application list,
 * enabling efficient UI rendering and navigation.
 */
public class DiscoveredApplicationResult {

    /**
     * The list of discovered applications for the current page.
     */
    private List<DiscoveredApplication> discoveredApplications = new ArrayList<>();

    /**
     * The total count of applications available in the external gateway.
     * May be -1 if the count is unavailable.
     */
    private int totalCount = -1;

    /**
     * The offset used for this result set.
     */
    private int offset;

    /**
     * The limit used for this result set.
     */
    private int limit;

    /**
     * Indicates if there are more results available beyond this page.
     */
    private boolean hasMoreResults;

    /**
     * Default constructor.
     */
    public DiscoveredApplicationResult() {
    }

    /**
     * Constructor with essential fields.
     *
     * @param discoveredApplications The list of discovered applications
     * @param totalCount             The total count of applications
     * @param offset                 The offset used
     * @param limit                  The limit used
     */
    public DiscoveredApplicationResult(List<DiscoveredApplication> discoveredApplications, int totalCount,
                                       int offset, int limit) {
        this.discoveredApplications = discoveredApplications != null ? discoveredApplications : new ArrayList<>();
        this.totalCount = totalCount;
        this.offset = offset;
        this.limit = limit;
        this.hasMoreResults = calculateHasMoreResults();
    }

    /**
     * Gets the list of discovered applications.
     *
     * @return The discovered applications list
     */
    public List<DiscoveredApplication> getDiscoveredApplications() {
        return discoveredApplications;
    }

    /**
     * Sets the list of discovered applications.
     *
     * @param discoveredApplications The list to set
     */
    public void setDiscoveredApplications(List<DiscoveredApplication> discoveredApplications) {
        this.discoveredApplications = discoveredApplications != null ? discoveredApplications : new ArrayList<>();
        this.hasMoreResults = calculateHasMoreResults();
    }

    /**
     * Gets the total count of applications.
     *
     * @return The total count, or -1 if unavailable
     */
    public int getTotalCount() {
        return totalCount;
    }

    /**
     * Sets the total count of applications.
     *
     * @param totalCount The total count to set
     */
    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
        this.hasMoreResults = calculateHasMoreResults();
    }

    /**
     * Gets the offset used for this result set.
     *
     * @return The offset
     */
    public int getOffset() {
        return offset;
    }

    /**
     * Sets the offset used for this result set.
     *
     * @param offset The offset to set
     */
    public void setOffset(int offset) {
        this.offset = offset;
        this.hasMoreResults = calculateHasMoreResults();
    }

    /**
     * Gets the limit used for this result set.
     *
     * @return The limit
     */
    public int getLimit() {
        return limit;
    }

    /**
     * Sets the limit used for this result set.
     *
     * @param limit The limit to set
     */
    public void setLimit(int limit) {
        this.limit = limit;
        this.hasMoreResults = calculateHasMoreResults();
    }

    /**
     * Checks if there are more results available.
     *
     * @return true if more results exist, false otherwise
     */
    public boolean isHasMoreResults() {
        return hasMoreResults;
    }

    /**
     * Sets whether there are more results available.
     *
     * @param hasMoreResults The flag to set
     */
    public void setHasMoreResults(boolean hasMoreResults) {
        this.hasMoreResults = hasMoreResults;
    }

    /**
     * Gets whether there are more results available.
     *
     * @return true if more results exist
     */
    public boolean getHasMoreResults() {
        return hasMoreResults;
    }

    /**
     * Gets the count of applications in the current page.
     *
     * @return The count of applications in this result set
     */
    public int getReturnedCount() {
        return discoveredApplications != null ? discoveredApplications.size() : 0;
    }

    /**
     * Calculates the next offset for pagination.
     *
     * @return The next offset value
     */
    public int getNextOffset() {
        return offset + limit;
    }

    /**
     * Calculates the previous offset for pagination.
     *
     * @return The previous offset value (minimum 0)
     */
    public int getPreviousOffset() {
        return Math.max(0, offset - limit);
    }

    /**
     * Calculates whether more results are available based on current state.
     *
     * @return true if more results exist
     */
    private boolean calculateHasMoreResults() {
        if (totalCount >= 0) {
            return (offset + getReturnedCount()) < totalCount;
        }
        // If total count is unknown, check if we got a full page
        return getReturnedCount() >= limit;
    }

    /**
     * Adds a discovered application to the list.
     *
     * @param application The application to add
     */
    public void addDiscoveredApplication(DiscoveredApplication application) {
        if (application != null) {
            this.discoveredApplications.add(application);
        }
    }
}
