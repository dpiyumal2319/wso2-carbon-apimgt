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

package org.wso2.carbon.apimgt.impl.federated;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Utility methods for JSON artifact manipulation in federated subscription flows.
 */
public class FederatedSubscriptionUtils {

    private static final Log log = LogFactory.getLog(FederatedSubscriptionUtils.class);

    private FederatedSubscriptionUtils() {
    }

    /**
     * Extracts the raw body from a {schemaName, body} wrapper.
     * If selectedOption is null or not in wrapper format, returns it as-is.
     */
    public static String extractSelectedOptionBody(String selectedOption) {
        if (selectedOption == null) {
            return null;
        }
        try {
            JsonObject wrapper = JsonParser.parseString(selectedOption).getAsJsonObject();
            if (wrapper.has("schemaName") && wrapper.has("body")) {
                return wrapper.get("body").getAsString();
            }
        } catch (Exception e) {
            // Not a wrapper format, return as-is
        }
        return selectedOption;
    }

    /**
     * Extracts the "selectedOption" field from a mapping's JSON reference artifact.
     * Returns null if the artifact is null or does not contain the field.
     */
    public static String extractSelectedOptionFromMappingArtifact(String mappingArtifact) {
        if (mappingArtifact == null || mappingArtifact.isEmpty()) {
            return null;
        }
        try {
            JsonObject json = JsonParser.parseString(mappingArtifact).getAsJsonObject();
            if (json.has("selectedOption")) {
                JsonElement el = json.get("selectedOption");
                return el.isJsonNull() ? null : el.getAsString();
            }
        } catch (Exception e) {
            log.warn("Failed to parse mapping reference artifact for selectedOption extraction", e);
        }
        return null;
    }

    /**
     * Builds the JSON blob stored in AM_SUBSCRIPTION_EXTERNAL_MAPPING.REFERENCE_ARTIFACT.
     * Embeds the selectedOption so it can be recovered at provisioning time.
     * Returns null if selectedOption is null.
     */
    public static String buildMappingArtifact(String selectedOption) {
        if (selectedOption == null) {
            return null;
        }
        JsonObject json = new JsonObject();
        json.addProperty("selectedOption", selectedOption);
        return json.toString();
    }

    /**
     * Patches the selectedOption into a credential's reference artifact JSON.
     * Returns the original artifact unchanged if patching fails.
     */
    public static String withSelectedOptionInCredentialArtifact(String credentialArtifact, String selectedOption) {
        if (credentialArtifact == null || credentialArtifact.isEmpty() || selectedOption == null) {
            return credentialArtifact;
        }
        try {
            JsonObject json = JsonParser.parseString(credentialArtifact).getAsJsonObject();
            json.addProperty("selectedOption", selectedOption);
            return json.toString();
        } catch (Exception e) {
            log.warn("Failed to patch selectedOption into credential reference artifact", e);
            return credentialArtifact;
        }
    }
}
