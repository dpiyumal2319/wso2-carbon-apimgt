/*
 * Copyright (c) 2026 WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
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

package org.wso2.carbon.apimgt.rest.api.store.v1.mappings;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.apimgt.api.model.FederatedCredential;
import org.wso2.carbon.apimgt.rest.api.store.v1.dto.FederatedCredentialDTO;
import org.wso2.carbon.apimgt.rest.api.store.v1.dto.FederatedSubscriptionInfoDTO;

import java.time.OffsetDateTime;
import java.time.format.DateTimeParseException;
import java.util.Date;

/**
 * Mapping utility for federated subscription related DTOs.
 * This utility only handles model-to-DTO conversion. Reference artifact parsing
 * is the responsibility of each gateway connector.
 */
public class FederatedSubscriptionMappingUtil {

    private static final Log log = LogFactory.getLog(FederatedSubscriptionMappingUtil.class);

    /**
     * Converts FederatedCredential model to DTO.
     */
    public static FederatedCredentialDTO fromFederatedCredentialToDTO(FederatedCredential credential) {
        if (credential == null) {
            return null;
        }

        FederatedCredentialDTO dto = new FederatedCredentialDTO();
        dto.setCredentialType(FederatedCredentialDTO.CredentialTypeEnum.fromValue(credential.getCredentialType()));
        dto.setCredentialValue(credential.getCredentialValue());
        dto.setIsValueRetrievable(credential.isValueRetrievable());
        dto.setExternalSubscriptionId(credential.getExternalSubscriptionId());

        // Parse ISO timestamps to Date
        if (credential.getCreatedTime() != null) {
            try {
                dto.setCreatedTime(Date.from(OffsetDateTime.parse(credential.getCreatedTime()).toInstant()));
            } catch (DateTimeParseException e) {
                log.warn("Failed to parse createdTime: " + credential.getCreatedTime(), e);
            }
        }
        if (credential.getExpiresAt() != null) {
            try {
                dto.setExpiresAt(Date.from(OffsetDateTime.parse(credential.getExpiresAt()).toInstant()));
            } catch (DateTimeParseException e) {
                log.warn("Failed to parse expiresAt: " + credential.getExpiresAt(), e);
            }
        }

        return dto;
    }

    /**
     * Converts complete federated subscription info to DTO.
     */
    public static FederatedSubscriptionInfoDTO fromFederatedSubscriptionInfoToDTO(
            FederatedCredential credential, String gatewayType, String gatewayEnvironmentId) {

        if (credential == null) {
            return null;
        }

        FederatedSubscriptionInfoDTO dto = new FederatedSubscriptionInfoDTO();
        dto.setCredential(fromFederatedCredentialToDTO(credential));
        dto.setGatewayType(FederatedSubscriptionInfoDTO.GatewayTypeEnum.fromValue(
                gatewayType != null ? gatewayType.toLowerCase() : null));
        dto.setGatewayEnvironmentId(gatewayEnvironmentId);

        return dto;
    }
}
