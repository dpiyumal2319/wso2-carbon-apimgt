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
import java.util.function.BiFunction;

/**
 * Builder for constructing {@link DiscoveredApplication} objects in a step-by-step manner.
 * <p>
 * This builder enforces a specific construction order to ensure data integrity and
 * provides clear semantics about what data is available at each stage:
 * </p>
 * <ol>
 *   <li><b>Stage 1 - Base Info:</b> Create with basic application metadata (via constructor)</li>
 *   <li><b>Stage 2 - Keys:</b> Add masked credential information via {@link #withKeys(List)}</li>
 *   <li><b>Stage 3 - External API References:</b> Add subscribed APIs with external IDs via
 *       {@link #withExternalApiReferences(List)}</li>
 *   <li><b>Stage 4 - DB Enrichment:</b> Enrich subscribed APIs with WSO2 data via
 *       {@link #enrichSubscribedApisFromDatabase(BiFunction)}</li>
 * </ol>
 * <p>
 * State validation is enforced: calling {@link #enrichSubscribedApisFromDatabase(BiFunction)}
 * before {@link #withExternalApiReferences(List)} will throw an {@link IllegalStateException}.
 * </p>
 *
 * <h3>Usage Example:</h3>
 * <pre>{@code
 * DiscoveredApplication app = DiscoveredApplicationBuilder.from(appInfo)
 *     .withKeys(keyInfoList)
 *     .withExternalApiReferences(apiSubscriptions)
 *     .enrichSubscribedApisFromDatabase((externalId, envId) -> {
 *         // lookup logic
 *         return apiDetails;
 *     })
 *     .build();
 * }</pre>
 */
public class DiscoveredApplicationBuilder {

    private final DiscoveredApplication application;
    private boolean keysPopulated = false;
    private boolean externalRefsAdded = false;
    private boolean dbEnriched = false;
    private String environmentId;

    /**
     * Private constructor. Use {@link #from(DiscoveredApplicationInfo)} to create a builder.
     *
     * @param info The base application info
     */
    private DiscoveredApplicationBuilder(DiscoveredApplicationInfo info) {
        this.application = new DiscoveredApplication(info);
    }

    /**
     * Creates a new builder from a {@link DiscoveredApplicationInfo} object.
     * This is Stage 1 of the construction process.
     *
     * @param info The lightweight application info from the connector
     * @return A new builder instance
     * @throws IllegalArgumentException if info is null
     */
    public static DiscoveredApplicationBuilder from(DiscoveredApplicationInfo info) {
        if (info == null) {
            throw new IllegalArgumentException("DiscoveredApplicationInfo cannot be null");
        }
        return new DiscoveredApplicationBuilder(info);
    }

    /**
     * Sets the environment ID for this application.
     * Required for database enrichment operations.
     *
     * @param environmentId The gateway environment UUID
     * @return This builder for chaining
     */
    public DiscoveredApplicationBuilder forEnvironment(String environmentId) {
        this.environmentId = environmentId;
        return this;
    }

    /**
     * Stage 2: Adds masked credential information to the application.
     * <p>
     * This step populates the key information list with masked credentials
     * from the external gateway.
     * </p>
     *
     * @param keyInfoList List of discovered application key info objects
     * @return This builder for chaining
     */
    public DiscoveredApplicationBuilder withKeys(List<DiscoveredApplicationKeyInfo> keyInfoList) {
        if (keyInfoList != null) {
            application.setKeyInfoList(new ArrayList<>(keyInfoList));
        }
        this.keysPopulated = true;
        return this;
    }

    /**
     * Stage 3: Adds subscribed APIs with external reference IDs.
     * <p>
     * This step populates the subscribed APIs list with information from the
     * external gateway. At this stage, only external API IDs are available;
     * WSO2-specific fields (wso2ApiId, isImported, apiStatus) are not populated.
     * </p>
     *
     * @param subscribedApis List of API subscriptions with external IDs
     * @return This builder for chaining
     */
    public DiscoveredApplicationBuilder withExternalApiReferences(List<DiscoveredAPISubscription> subscribedApis) {
        if (subscribedApis != null) {
            application.setSubscribedApis(new ArrayList<>(subscribedApis));
        }
        this.externalRefsAdded = true;
        return this;
    }

    /**
     * Stage 4: Enriches subscribed APIs with data from the WSO2 database.
     * <p>
     * This step matches external API IDs with entries in AM_API_EXTERNAL_API_MAPPING
     * and populates WSO2-specific fields (wso2ApiId, apiContext, isImported, apiStatus).
     * </p>
     * <p>
     * The enrichment function receives each external API ID and environment ID,
     * and should return an {@link ApiEnrichmentData} object with the WSO2 API details,
     * or null if the API is not imported.
     * </p>
     *
     * @param enrichmentFunction Function that looks up API details by external ID and environment ID
     * @return This builder for chaining
     * @throws IllegalStateException if called before {@link #withExternalApiReferences(List)}
     */
    public DiscoveredApplicationBuilder enrichSubscribedApisFromDatabase(
            BiFunction<String, String, ApiEnrichmentData> enrichmentFunction) {
        
        if (!externalRefsAdded) {
            throw new IllegalStateException(
                    "Cannot enrich from database before adding external API references. " +
                    "Call withExternalApiReferences() first.");
        }

        if (enrichmentFunction == null) {
            throw new IllegalArgumentException("Enrichment function cannot be null");
        }

        List<DiscoveredAPISubscription> subscribedApis = application.getSubscribedApis();
        if (subscribedApis != null && !subscribedApis.isEmpty()) {
            for (DiscoveredAPISubscription subscription : subscribedApis) {
                String externalApiId = subscription.getExternalApiId();
                if (externalApiId != null && !externalApiId.isEmpty()) {
                    ApiEnrichmentData enrichmentData = enrichmentFunction.apply(externalApiId, environmentId);
                    if (enrichmentData != null) {
                        subscription.setWso2ApiId(enrichmentData.getApiId());
                        subscription.setApiContext(enrichmentData.getApiContext());
                        subscription.setApiStatus(enrichmentData.getApiStatus());
                        subscription.setImported(true);
                        // Optionally update name/version from WSO2 if different
                        if (enrichmentData.getApiName() != null) {
                            subscription.setApiName(enrichmentData.getApiName());
                        }
                        if (enrichmentData.getApiVersion() != null) {
                            subscription.setApiVersion(enrichmentData.getApiVersion());
                        }
                    }
                    // If enrichmentData is null, the API is not imported - leave isImported as false
                }
            }
        }

        this.dbEnriched = true;
        return this;
    }

    /**
     * Builds and returns the final {@link DiscoveredApplication} object.
     * <p>
     * The application can be built at any valid stage. The caller should be aware
     * of which stages have been completed based on their usage pattern.
     * </p>
     *
     * @return The constructed DiscoveredApplication
     */
    public DiscoveredApplication build() {
        return application;
    }

    /**
     * Returns whether keys have been populated (Stage 2 completed).
     *
     * @return true if keys are populated
     */
    public boolean isKeysPopulated() {
        return keysPopulated;
    }

    /**
     * Returns whether external API references have been added (Stage 3 completed).
     *
     * @return true if external refs are added
     */
    public boolean isExternalRefsAdded() {
        return externalRefsAdded;
    }

    /**
     * Returns whether database enrichment has been performed (Stage 4 completed).
     *
     * @return true if DB enrichment is done
     */
    public boolean isDbEnriched() {
        return dbEnriched;
    }

    /**
     * Data class for API enrichment information from the database.
     */
    public static class ApiEnrichmentData {
        private final String apiId;
        private final String apiName;
        private final String apiVersion;
        private final String apiContext;
        private final String apiStatus;

        public ApiEnrichmentData(String apiId, String apiName, String apiVersion,
                                  String apiContext, String apiStatus) {
            this.apiId = apiId;
            this.apiName = apiName;
            this.apiVersion = apiVersion;
            this.apiContext = apiContext;
            this.apiStatus = apiStatus;
        }

        public String getApiId() {
            return apiId;
        }

        public String getApiName() {
            return apiName;
        }

        public String getApiVersion() {
            return apiVersion;
        }

        public String getApiContext() {
            return apiContext;
        }

        public String getApiStatus() {
            return apiStatus;
        }
    }
}
