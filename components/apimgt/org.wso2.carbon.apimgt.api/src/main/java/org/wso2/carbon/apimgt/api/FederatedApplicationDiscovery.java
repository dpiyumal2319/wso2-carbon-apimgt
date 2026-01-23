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
import org.wso2.carbon.apimgt.api.model.DiscoveredApplicationInfo;
import org.wso2.carbon.apimgt.api.model.DiscoveredApplicationResult;
import org.wso2.carbon.apimgt.api.model.Environment;

import java.util.stream.Stream;

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
     * Discovers applications with pagination metadata.
     * Returns lightweight application info without keys or subscriptions for efficient listing.
     *
     * @param offset The number of records to skip (0-based starting index)
     * @param limit  The maximum number of records to return
     * @param query  The search query (can be null for no filtering)
     * @return A DiscoveredApplicationResult with lightweight application info and pagination metadata
     * @throws APIManagementException If the discovery process fails
     */
    DiscoveredApplicationResult discoverApplications(int offset, int limit, String query)
        throws APIManagementException;

    /**
     * Returns a lazy-loaded Stream of ALL applications matching the query.
     * Returns lightweight application info without keys or subscriptions.
     * This is used for background tasks (e.g., "Import All").
     * 
     * Implementation Requirement:
     * This must NOT load all data into memory at once. It should wrap the underlying
     * gateway's pagination cursor to fetch pages on-demand as the stream is consumed.
     *
     * @param query Optional search string. Pass null to stream everything.
     * @return A Stream of DiscoveredApplicationInfo objects (lightweight).
     * @throws APIManagementException If the stream cannot be initiated.
     */
    Stream<DiscoveredApplicationInfo> streamApplications(String query)
            throws APIManagementException;

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
     * Gets a specific application by its external ID with complete details.
     * This method fetches full application details including:
     * - Masked credentials/keys
     * - List of API subscriptions from the external gateway
     * 
     * Used when user clicks on a specific application in the discovery list
     * to preview details before import.
     *
     * @param externalApplicationId The external gateway's application identifier
     * @return The discovered application with keys masked and subscriptions populated
     * @throws APIManagementException If the application is not found or fetch fails
     */
    DiscoveredApplication getApplicationWithKeysMasked(String externalApplicationId) throws APIManagementException;

    /**
     * Gets the gateway type identifier for this discovery agent.
     * Used for agent selection and logging.
     *
     * @return The gateway type (e.g., "Azure", "AWS", "Kong")
     */
    String getGatewayType();
}