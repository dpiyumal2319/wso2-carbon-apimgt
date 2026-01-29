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
import org.wso2.carbon.apimgt.api.model.InvocationInstruction;
import org.wso2.carbon.apimgt.rest.api.store.v1.dto.FederatedCredentialDTO;
import org.wso2.carbon.apimgt.rest.api.store.v1.dto.FederatedSubscriptionInfoDTO;
import org.wso2.carbon.apimgt.rest.api.store.v1.dto.InvocationInstructionDTO;

import java.util.HashMap;
import java.util.Map;

/**
 * Mapping utility for federated subscription related DTOs
 */
public class FederatedSubscriptionMappingUtil {

    /**
     * Converts FederatedCredential model to DTO
     */
    public static FederatedCredentialDTO fromFederatedCredentialToDTO(FederatedCredential credential) {
        if (credential == null) {
            return null;
        }

        FederatedCredentialDTO dto = new FederatedCredentialDTO();
        dto.setCredentialType(FederatedCredentialDTO.CredentialTypeEnum.fromValue(credential.getCredentialType()));
        dto.setCredentialValue(credential.getCredentialValue());
        dto.setHeaderName(credential.getHeaderName());
        dto.setIsValueRetrievable(false); // Hardcoded as false for security
        dto.setExternalSubscriptionId(credential.getExternalSubscriptionId());
        dto.setExternalContainerId(credential.getExternalContainerId());

        return dto;
    }

    /**
     * Converts InvocationInstruction model to DTO
     */
    public static InvocationInstructionDTO fromInvocationInstructionToDTO(InvocationInstruction instruction) {
        if (instruction == null) {
            return null;
        }

        InvocationInstructionDTO dto = new InvocationInstructionDTO();
        dto.setGatewayType(instruction.getGatewayType());
        dto.setCredentialHeaderName(instruction.getHeaderName());
        dto.setBaseUrl(instruction.getBaseUrl());
        dto.setBasePath(instruction.getBasePath());
        dto.setFullUrl(instruction.getFullEndpointUrl());
        dto.setCurlExample(instruction.getCurlExample());
        dto.setNotes(instruction.getNotes());

        // Convert Map<String, String> to Map<String, String> (already compatible)
        if (instruction.getAdditionalHeaders() != null) {
            Map<String, String> headers = new HashMap<>();
            headers.putAll(instruction.getAdditionalHeaders());
            dto.setAdditionalHeaders(headers);
        }

        return dto;
    }

    /**
     * Converts complete federated subscription info to DTO
     */
    public static FederatedSubscriptionInfoDTO fromFederatedSubscriptionInfoToDTO(
            FederatedCredential credential, InvocationInstruction instruction, 
            String gatewayType, String gatewayEnvironmentId) {
        
        if (credential == null && instruction == null) {
            return null;
        }

        FederatedSubscriptionInfoDTO dto = new FederatedSubscriptionInfoDTO();
        
        if (credential != null) {
            dto.setCredential(fromFederatedCredentialToDTO(credential));
        }
        
        if (instruction != null) {
            dto.setInvocationInstruction(fromInvocationInstructionToDTO(instruction));
        }
        
        dto.setGatewayType(FederatedSubscriptionInfoDTO.GatewayTypeEnum.fromValue(
                gatewayType != null ? gatewayType.toLowerCase() : null));
        dto.setGatewayEnvironmentId(gatewayEnvironmentId);

        return dto;
    }

    /**
     * Converts DTO to FederatedCredential model
     */
    public static FederatedCredential fromDTOtoFederatedCredential(FederatedCredentialDTO dto) {
        if (dto == null) {
            return null;
        }

        FederatedCredential credential = new FederatedCredential();
        if (dto.getCredentialType() != null) {
            credential.setCredentialType(dto.getCredentialType().name());
        }
        credential.setCredentialValue(dto.getCredentialValue());
        credential.setHeaderName(dto.getHeaderName());
        credential.setExternalSubscriptionId(dto.getExternalSubscriptionId());
        credential.setExternalContainerId(dto.getExternalContainerId());

        return credential;
    }
}
