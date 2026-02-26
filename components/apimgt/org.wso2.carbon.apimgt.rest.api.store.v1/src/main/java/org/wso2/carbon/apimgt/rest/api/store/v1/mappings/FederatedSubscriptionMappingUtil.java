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
import org.wso2.carbon.apimgt.api.model.FederatedCredentialCreateResult;
import org.wso2.carbon.apimgt.api.model.FederatedCredentialSummary;
import org.wso2.carbon.apimgt.api.model.FederatedSubscriptionResult;
import org.wso2.carbon.apimgt.rest.api.store.v1.dto.FederatedCredentialCreateResponseDTO;
import org.wso2.carbon.apimgt.rest.api.store.v1.dto.FederatedCredentialDTO;
import org.wso2.carbon.apimgt.rest.api.store.v1.dto.FederatedCredentialSummaryDTO;
import org.wso2.carbon.apimgt.rest.api.store.v1.dto.FederatedCredentialSummaryListDTO;
import org.wso2.carbon.apimgt.rest.api.store.v1.dto.FederatedSubscriptionInfoDTO;

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
     * Converts a FederatedSubscriptionResult to DTO.
     * Single mapping call that assembles the complete response DTO.
     */
    public static FederatedSubscriptionInfoDTO toDTO(FederatedSubscriptionResult result) {
        if (result == null) {
            return null;
        }

        FederatedSubscriptionInfoDTO dto = new FederatedSubscriptionInfoDTO();
        dto.setCredential(fromFederatedCredentialToDTO(result.getCredential()));
        dto.setInvocationInstruction(
                InvocationInstructionMappingUtil.fromInvocationInstructionToDTO(result.getInvocationInstruction()));
        dto.setGatewayType(FederatedSubscriptionInfoDTO.GatewayTypeEnum.fromValue(
                result.getGatewayType() != null ? result.getGatewayType().toLowerCase() : null));
        dto.setGatewayEnvironmentId(result.getGatewayEnvironmentId());

        return dto;
    }

    /**
     * Converts a FederatedCredentialCreateResult to the response DTO.
     */
    public static FederatedCredentialCreateResponseDTO fromCreateResultToDTO(
            FederatedCredentialCreateResult result) {
        if (result == null) {
            return null;
        }

        FederatedCredentialCreateResponseDTO dto = new FederatedCredentialCreateResponseDTO();
        dto.setSubscriptionId(result.getSubscriptionId());
        dto.setStatus(FederatedCredentialCreateResponseDTO.StatusEnum.fromValue(
                result.getStatus().name()));

        FederatedSubscriptionResult subResult = result.getFederatedSubscriptionResult();
        if (subResult != null) {
            dto.setCredential(fromFederatedCredentialToDTO(subResult.getCredential()));
            dto.setInvocationInstruction(
                    InvocationInstructionMappingUtil.fromInvocationInstructionToDTO(
                            subResult.getInvocationInstruction()));
            dto.setGatewayType(subResult.getGatewayType());
            dto.setGatewayEnvironmentId(subResult.getGatewayEnvironmentId());
        }

        return dto;
    }

    /**
     * Converts a FederatedCredentialSummary model to DTO.
     */
    public static FederatedCredentialSummaryDTO fromSummaryToDTO(FederatedCredentialSummary summary) {
        if (summary == null) {
            return null;
        }

        FederatedCredentialSummaryDTO dto = new FederatedCredentialSummaryDTO();
        dto.setSubscriptionId(summary.getSubscriptionUuid());
        dto.setApplicationId(summary.getApplicationId());
        dto.setApplicationName(summary.getApplicationName());
        dto.setName(summary.getName());
        dto.setSelectedOption(summary.getSelectedOption());
        dto.setIsProvisioned(summary.isProvisioned());
        dto.setSubscriptionStatus(FederatedCredentialSummaryDTO.SubscriptionStatusEnum.fromValue(
                summary.getSubscriptionStatus()));
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
