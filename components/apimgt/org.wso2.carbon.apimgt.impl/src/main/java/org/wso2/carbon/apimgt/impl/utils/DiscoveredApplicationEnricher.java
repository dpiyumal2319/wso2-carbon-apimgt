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

package org.wso2.carbon.apimgt.impl.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.apimgt.api.APIManagementException;
import org.wso2.carbon.apimgt.api.model.DiscoveredAPISubscription;
import org.wso2.carbon.apimgt.api.model.DiscoveredApplication;
import org.wso2.carbon.apimgt.api.model.DiscoveredApplicationBuilder;
import org.wso2.carbon.apimgt.impl.dao.ApiMgtDAO;

import java.util.List;
import java.util.Map;

/**
 * Utility class for enriching {@link DiscoveredApplication} objects with data from the WSO2 database.
 * <p>
 * This class provides methods to match external API IDs from discovered applications with
 * entries in the AM_API_EXTERNAL_API_MAPPING table and populate WSO2-specific fields.
 * </p>
 */
public class DiscoveredApplicationEnricher {

    private static final Log log = LogFactory.getLog(DiscoveredApplicationEnricher.class);

    private DiscoveredApplicationEnricher() {
        // Utility class - prevent instantiation
    }

    /**
     * Enriches subscribed APIs in a DiscoveredApplication with WSO2 database information.
     * <p>
     * This method:
     * <ul>
     *   <li>Fetches all API mappings for the given environment from the database</li>
     *   <li>Matches external API IDs from the subscribed APIs list</li>
     *   <li>Populates wso2ApiId, apiContext, isImported, and apiStatus for matched APIs</li>
     *   <li>Only includes APIs with PUBLISHED status</li>
     * </ul>
     * </p>
     *
     * @param subscribedApis List of API subscriptions to enrich
     * @param environmentId  Gateway environment UUID
     * @throws APIManagementException if database access fails
     */
    public static void enrichSubscribedApis(List<DiscoveredAPISubscription> subscribedApis, String environmentId)
            throws APIManagementException {

        if (subscribedApis == null || subscribedApis.isEmpty()) {
            log.debug("No subscribed APIs to enrich");
            return;
        }

        if (environmentId == null || environmentId.isEmpty()) {
            throw new APIManagementException("Environment ID is required for API enrichment");
        }

        // Fetch all published API mappings for this environment (bulk query)
        Map<String, Map<String, String>> apiMappings = ApiMgtDAO.getInstance()
                .getApiMappingsByEnvironment(environmentId, true);

        if (log.isDebugEnabled()) {
            log.debug("Fetched " + apiMappings.size() + " published API mappings for environment: " + environmentId);
        }

        // Enrich each subscribed API
        for (DiscoveredAPISubscription subscription : subscribedApis) {
            String externalApiId = subscription.getExternalApiId();
            if (externalApiId != null && !externalApiId.isEmpty()) {
                Map<String, String> apiDetails = apiMappings.get(externalApiId);
                if (apiDetails != null) {
                    subscription.setWso2ApiId(apiDetails.get("apiId"));
                    subscription.setApiContext(apiDetails.get("context"));
                    subscription.setApiStatus(apiDetails.get("status"));
                    subscription.setImported(true);

                    // Update name and version from WSO2 if available
                    if (apiDetails.get("apiName") != null) {
                        subscription.setApiName(apiDetails.get("apiName"));
                    }
                    if (apiDetails.get("apiVersion") != null) {
                        subscription.setApiVersion(apiDetails.get("apiVersion"));
                    }

                    if (log.isDebugEnabled()) {
                        log.debug("Enriched API subscription: externalId=" + externalApiId +
                                ", wso2ApiId=" + apiDetails.get("apiId"));
                    }
                } else {
                    if (log.isDebugEnabled()) {
                        log.debug("No matching imported API found for external ID: " + externalApiId);
                    }
                }
            }
        }
    }

    /**
     * Creates an enrichment function for use with {@link DiscoveredApplicationBuilder}.
     * <p>
     * This method pre-fetches all API mappings for the environment and returns a function
     * that can be used with the builder's enrichSubscribedApisFromDatabase method.
     * </p>
     *
     * @param environmentId Gateway environment UUID
     * @return A function that looks up API details by external API ID
     * @throws APIManagementException if database access fails
     */
    public static java.util.function.BiFunction<String, String, DiscoveredApplicationBuilder.ApiEnrichmentData>
            createEnrichmentFunction(String environmentId) throws APIManagementException {

        // Pre-fetch all API mappings for the environment
        Map<String, Map<String, String>> apiMappings = ApiMgtDAO.getInstance()
                .getApiMappingsByEnvironment(environmentId, true);

        if (log.isDebugEnabled()) {
            log.debug("Created enrichment function with " + apiMappings.size() +
                    " API mappings for environment: " + environmentId);
        }

        // Return a function that looks up from the pre-fetched map
        return (externalApiId, envId) -> {
            Map<String, String> apiDetails = apiMappings.get(externalApiId);
            if (apiDetails != null) {
                return new DiscoveredApplicationBuilder.ApiEnrichmentData(
                        apiDetails.get("apiId"),
                        apiDetails.get("apiName"),
                        apiDetails.get("apiVersion"),
                        apiDetails.get("context"),
                        apiDetails.get("status")
                );
            }
            return null;
        };
    }

    /**
     * Filters subscribed APIs to only include those that are imported in WSO2.
     * <p>
     * This is useful when the frontend should only see APIs that exist in WSO2.
     * </p>
     *
     * @param subscribedApis List of API subscriptions (should be enriched first)
     * @return A new list containing only imported APIs
     */
    public static List<DiscoveredAPISubscription> filterImportedApis(List<DiscoveredAPISubscription> subscribedApis) {
        if (subscribedApis == null) {
            return java.util.Collections.emptyList();
        }
        return subscribedApis.stream()
                .filter(DiscoveredAPISubscription::isImported)
                .collect(java.util.stream.Collectors.toList());
    }
}
