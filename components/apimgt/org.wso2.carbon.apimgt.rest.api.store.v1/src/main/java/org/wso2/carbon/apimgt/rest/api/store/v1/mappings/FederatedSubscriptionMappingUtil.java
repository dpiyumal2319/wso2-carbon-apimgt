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

import org.wso2.carbon.apimgt.api.model.FederatedCredential;
import org.wso2.carbon.apimgt.api.model.FederatedCredentialSummary;
import org.wso2.carbon.apimgt.api.model.FederatedCredentialsResult;
import org.wso2.carbon.apimgt.api.model.FederatedSubscriptionCreateResult;
import org.wso2.carbon.apimgt.api.model.FederatedSubscriptionSummary;
import org.wso2.carbon.apimgt.rest.api.store.v1.dto.FederatedCredentialDTO;
import org.wso2.carbon.apimgt.rest.api.store.v1.dto.FederatedCredentialListDTO;
import org.wso2.carbon.apimgt.rest.api.store.v1.dto.FederatedCredentialSummaryDTO;
import org.wso2.carbon.apimgt.rest.api.store.v1.dto.FederatedCredentialSummaryListDTO;
import org.wso2.carbon.apimgt.rest.api.store.v1.dto.FederatedSubscriptionCreateResponseDTO;
import org.wso2.carbon.apimgt.rest.api.store.v1.dto.FederatedCredentialInfoDTO;
import org.wso2.carbon.apimgt.rest.api.store.v1.dto.FederatedSubscriptionSummaryDTO;
import org.wso2.carbon.apimgt.rest.api.store.v1.dto.FederatedSubscriptionSummaryListDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Mapping utility for federated subscription related DTOs.
 * Maps opaque body field directly without parsing. All JSON structure
 * interpretation is the responsibility of gateway connectors and frontend.
 */
public class FederatedSubscriptionMappingUtil {

    /**
     * Converts FederatedCredential model to DTO.
     * Simply passes through the opaque body field without interpretation.
     */
    public static FederatedCredentialDTO fromFederatedCredentialToDTO(FederatedCredential credential) {
        if (credential == null) {
            return null;
        }

        FederatedCredentialDTO dto = new FederatedCredentialDTO();
        dto.setSchemaName(credential.getSchemaName());
        dto.setBody(credential.getBodyAsJson());
        dto.setExternalSubscriptionId(credential.getExternalSubscriptionId());
        dto.setIsValueRetrievable(credential.isValueRetrievable());
        dto.setMasked(credential.isMasked());

        return dto;
    }

    /**
     * Converts a FederatedCredentialsResult to DTO.
     * Single mapping call that assembles the complete response DTO.
     */
    public static FederatedCredentialInfoDTO toDTO(FederatedCredentialsResult result) {
        if (result == null) {
            return null;
        }

        FederatedCredentialInfoDTO dto = new FederatedCredentialInfoDTO();
        dto.setCredentialId(result.getCredentialUuid());
        dto.setCredential(fromFederatedCredentialToDTO(result.getCredential()));
        dto.setInvocationInstruction(
                InvocationInstructionMappingUtil.fromInvocationInstructionToDTO(result.getInvocationInstruction()));
        dto.setGatewayType(FederatedCredentialInfoDTO.GatewayTypeEnum.fromValue(
                result.getGatewayType() != null ? result.getGatewayType().toLowerCase() : null));
        dto.setGatewayEnvironmentId(result.getGatewayEnvironmentId());

        return dto;
    }

    /**
     * Converts a FederatedSubscriptionCreateResult to the response DTO.
     */
    public static FederatedSubscriptionCreateResponseDTO fromCreateResultToDTO(
            FederatedSubscriptionCreateResult result) {
        if (result == null) {
            return null;
        }

        FederatedSubscriptionCreateResponseDTO dto = new FederatedSubscriptionCreateResponseDTO();
        dto.setSubscriptionId(result.getSubscriptionUuid());
        dto.setStatus(FederatedSubscriptionCreateResponseDTO.StatusEnum.fromValue(result.getStatus()));

        return dto;
    }

    /**
     * Converts a FederatedSubscriptionSummary model to DTO.
     */
    public static FederatedSubscriptionSummaryDTO fromSubscriptionSummaryToDTO(FederatedSubscriptionSummary summary) {
        if (summary == null) {
            return null;
        }

        FederatedSubscriptionSummaryDTO dto = new FederatedSubscriptionSummaryDTO();
        dto.setSubscriptionId(summary.getSubscriptionUuid());
        dto.setMappingId(summary.getMappingUuid());
        dto.setApplicationId(summary.getApplicationId());
        dto.setApplicationName(summary.getApplicationName());
        dto.setSelectedOption(summary.getSelectedOption());
        dto.setSubscriptionStatus(FederatedSubscriptionSummaryDTO.SubscriptionStatusEnum.fromValue(
                summary.getSubscriptionStatus()));
        dto.setCredentialCount(summary.getCredentialCount());
        if (summary.getCreatedTime() != null) {
            dto.setCreatedTime(summary.getCreatedTime());
        }
        if (summary.getLastUpdatedTime() != null) {
            dto.setLastUpdatedTime(summary.getLastUpdatedTime());
        }

        return dto;
    }

    /**
     * Converts a list of FederatedSubscriptionSummary models to a list DTO.
     */
    public static FederatedSubscriptionSummaryListDTO fromSubscriptionSummaryListToDTO(
            List<FederatedSubscriptionSummary> summaries) {
        FederatedSubscriptionSummaryListDTO listDTO = new FederatedSubscriptionSummaryListDTO();
        List<FederatedSubscriptionSummaryDTO> dtoList = new ArrayList<>();

        if (summaries != null) {
            for (FederatedSubscriptionSummary summary : summaries) {
                dtoList.add(fromSubscriptionSummaryToDTO(summary));
            }
        }

        listDTO.setList(dtoList);
        listDTO.setCount(dtoList.size());
        return listDTO;
    }

    /**
     * Converts a list of FederatedCredentialsResult models to a credential list DTO.
     * Each entry contains a masked credential and invocation instructions.
     */
    public static FederatedCredentialListDTO fromCredentialResultsListToDTO(
            List<FederatedCredentialsResult> results) {
        FederatedCredentialListDTO listDTO = new FederatedCredentialListDTO();
        List<FederatedCredentialInfoDTO> dtoList = new ArrayList<>();

        if (results != null) {
            for (FederatedCredentialsResult result : results) {
                dtoList.add(toDTO(result));
            }
        }

        listDTO.setList(dtoList);
        listDTO.setCount(dtoList.size());
        return listDTO;
    }

    /**
     * Converts a FederatedCredentialSummary model to DTO.
     */
    public static FederatedCredentialSummaryDTO fromSummaryToDTO(FederatedCredentialSummary summary) {
        if (summary == null) {
            return null;
        }

        FederatedCredentialSummaryDTO dto = new FederatedCredentialSummaryDTO();
        dto.setCredentialId(summary.getCredentialUuid());
        dto.setSubscriptionId(summary.getSubscriptionUuid());
        dto.setApplicationId(summary.getApplicationId());
        dto.setApplicationName(summary.getApplicationName());
        dto.setApiId(summary.getApiId());
        dto.setApiName(summary.getApiName());
        dto.setApiDisplayName(summary.getApiDisplayName());
        dto.setApiVersion(summary.getApiVersion());
        dto.setApiLifeCycleStatus(summary.getApiLifeCycleStatus());
        dto.setApiType(summary.getApiType());
        dto.setName(summary.getName());
        dto.setSelectedOption(summary.getSelectedOption());
        if (summary.getLastUpdatedTime() != null) {
            dto.setLastUpdatedTime(summary.getLastUpdatedTime());
        }

        return dto;
    }

    /**
     * Converts a list of FederatedCredentialSummary models to a list DTO.
     */
    public static FederatedCredentialSummaryListDTO fromSummaryListToDTO(
            List<FederatedCredentialSummary> summaries) {
        FederatedCredentialSummaryListDTO listDTO = new FederatedCredentialSummaryListDTO();
        List<FederatedCredentialSummaryDTO> dtoList = new ArrayList<>();

        if (summaries != null) {
            for (FederatedCredentialSummary summary : summaries) {
                dtoList.add(fromSummaryToDTO(summary));
            }
        }

        listDTO.setList(dtoList);
        listDTO.setCount(dtoList.size());
        return listDTO;
    }
}
