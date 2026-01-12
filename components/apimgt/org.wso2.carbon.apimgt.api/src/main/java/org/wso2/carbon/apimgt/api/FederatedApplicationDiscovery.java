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

package org.wso2.carbon.apimgt.api;

import org.wso2.carbon.apimgt.api.model.DiscoveredApplication;
import org.wso2.carbon.apimgt.api.model.DiscoveredApplicationResult;
import org.wso2.carbon.apimgt.api.model.Environment;

import java.util.List;

/**
 * Interface for discovering applications (consumers) from external gateways.
 * All federation agents must implement this interface to enable application discovery
 * from their respective gateway platforms (Azure, AWS, Kong, etc.).
 */
public interface FederatedApplicationDiscovery {

    /**
     * Initializes the discovery agent with environment configurations.
     * This method must be called before any discovery operations.
     *
     * @param environment  The gateway environment configuration containing
     *                     connection details and credentials
     * @param organization The organization identifier for multi-tenant support
     * @throws APIManagementException If initialization fails (e.g., invalid credentials,
     *                                unreachable gateway)
     */
    void init(Environment environment, String organization) throws APIManagementException;

    /**
     * Discovers all applications from the external gateway with default pagination.
     * This is a convenience method that calls discoverApplications(0, 100).
     *
     * @return A list of discovered applications
     * @throws APIManagementException If the discovery process fails
     */
    default List<DiscoveredApplication> discoverApplications() throws APIManagementException {
        return discoverApplications(0, 100);
    }

    /**
     * Discovers a specific page of applications from the external gateway.
     * This method uses server-side pagination to efficiently handle large datasets
     * without loading all applications into memory.
     * <p>
     * Implementation notes:
     * - Should translate gateway-native pagination to offset/limit
     * - Should batch policy/tier lookups to avoid N+1 query problems
     * - Should only fetch display-essential data
     * - Should mask credential values in the returned objects
     * </p>
     *
     * @param offset The number of records to skip (0-based starting index)
     * @param limit  The maximum number of records to return
     * @return A list of discovered applications (may be empty if no more results)
     * @throws APIManagementException If the discovery process fails
     */
    List<DiscoveredApplication> discoverApplications(int offset, int limit) throws APIManagementException;

    /**
     * Discovers applications with optional search query.
     * Combines discovery with server-side filtering.
     *
     * @param offset The number of records to skip (0-based starting index)
     * @param limit  The maximum number of records to return
     * @param query  The search query (can be null for no filtering)
     * @return A list of discovered applications matching the query
     * @throws APIManagementException If the discovery process fails
     */
    default List<DiscoveredApplication> discoverApplications(int offset, int limit, String query)
            throws APIManagementException {
        return discoverApplications(offset, limit);
    }

    /**
     * Discovers applications with pagination metadata.
     * Returns a result object containing the applications and pagination information.
     *
     * @param offset The number of records to skip (0-based starting index)
     * @param limit  The maximum number of records to return
     * @param query  The search query (can be null for no filtering)
     * @return A DiscoveredApplicationResult with applications and pagination metadata
     * @throws APIManagementException If the discovery process fails
     */
    default DiscoveredApplicationResult discoverApplicationsWithPagination(int offset, int limit, String query)
            throws APIManagementException {
        List<DiscoveredApplication> applications = discoverApplications(offset, limit, query);
        int totalCount = getTotalApplicationCount(query);

        DiscoveredApplicationResult result = new DiscoveredApplicationResult();
        result.setDiscoveredApplications(applications);
        result.setTotalCount(totalCount);
        result.setOffset(offset);
        result.setLimit(limit);
        result.setHasMoreResults(offset + applications.size() < totalCount);

        return result;
    }

    /**
     * Gets the total count of discoverable applications in the external gateway.
     * This is used for pagination metadata in the UI.
     * <p>
     * If the external gateway does not support count queries efficiently,
     * implementations may return -1 to indicate unknown total, and the UI
     * should use a "Load More" pattern instead of page numbers.
     * </p>
     *
     * @return The total number of applications, or -1 if count is unavailable
     * @throws APIManagementException If the count operation fails
     */
    default int getTotalApplicationCount() throws APIManagementException {
        return -1; // Default: count unavailable
    }

    /**
     * Gets the total count of discoverable applications matching the query.
     *
     * @param query The search query (can be null for total count)
     * @return The total number of matching applications, or -1 if count is unavailable
     * @throws APIManagementException If the count operation fails
     */
    default int getTotalApplicationCount(String query) throws APIManagementException {
        return getTotalApplicationCount();
    }

    /**
     * Searches for applications matching the given query string.
     * The search is performed on the external gateway's side if supported.
     *
     * @param query  The search query (matches against name, description, owner)
     * @param offset The number of records to skip
     * @param limit  The maximum number of records to return
     * @return A list of matching discovered applications
     * @throws APIManagementException If the search operation fails
     * @deprecated Use discoverApplications(offset, limit, query) instead
     */
    @Deprecated
    default List<DiscoveredApplication> searchApplications(String query, int offset, int limit)
            throws APIManagementException {
        // Default implementation: delegate to discoverApplications with query
        return discoverApplications(offset, limit, query);
    }

    /**
     * Checks if the referenced application still exists in the external gateway.
     * Used to validate before import operations.
     *
     * @param externalApplicationId The external gateway's application identifier
     * @return true if the application exists, false otherwise
     * @throws APIManagementException If the validation fails
     */
    default boolean applicationExists(String externalApplicationId) throws APIManagementException {
        return true; // Default: assume exists
    }

    /**
     * Checks if an application has been updated in the external gateway
     * by comparing reference artifacts.
     *
     * @param existingReferenceArtifact The stored reference artifact
     * @param newReferenceArtifact      The current reference artifact from gateway
     * @return true if the application has been updated, false otherwise
     * @throws APIManagementException If the comparison fails
     */
    default boolean isApplicationUpdated(String existingReferenceArtifact, String newReferenceArtifact)
            throws APIManagementException {
        // Default: compare strings directly
        if (existingReferenceArtifact == null || newReferenceArtifact == null) {
            return false;
        }
        return !existingReferenceArtifact.equals(newReferenceArtifact);
    }

    /**
     * Gets a specific application by its external ID.
     *
     * @param externalApplicationId The external gateway's application identifier
     * @return The discovered application
     * @throws APIManagementException If the application is not found or fetch fails
     */
    DiscoveredApplication getApplication(String externalApplicationId) throws APIManagementException;

    /**
     * Gets the gateway type identifier for this discovery agent.
     * Used for agent selection and logging.
     *
     * @return The gateway type (e.g., "Azure", "AWS", "Kong")
     */
    String getGatewayType();
}