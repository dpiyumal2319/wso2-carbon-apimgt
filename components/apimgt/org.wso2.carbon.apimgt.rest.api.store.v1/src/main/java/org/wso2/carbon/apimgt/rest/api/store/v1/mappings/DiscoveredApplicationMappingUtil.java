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

package org.wso2.carbon.apimgt.rest.api.store.v1.mappings;

import org.wso2.carbon.apimgt.api.model.DiscoveredApplication;
import org.wso2.carbon.apimgt.api.model.DiscoveredApplicationKeyInfo;
import org.wso2.carbon.apimgt.api.model.DiscoveredApplicationResult;
import org.wso2.carbon.apimgt.rest.api.store.v1.dto.DiscoveredApplicationDTO;
import org.wso2.carbon.apimgt.rest.api.store.v1.dto.DiscoveredApplicationKeyInfoDTO;
import org.wso2.carbon.apimgt.rest.api.store.v1.dto.DiscoveredApplicationListDTO;
import org.wso2.carbon.apimgt.rest.api.store.v1.dto.PaginationDTO;
import org.wso2.carbon.apimgt.rest.api.common.RestApiCommonUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Mapping utility for Discovered Applications
 */
public class DiscoveredApplicationMappingUtil {

    /**
     * Converts DiscoveredApplication model to DiscoveredApplicationDTO
     *
     * @param discoveredApplication DiscoveredApplication model
     * @return DiscoveredApplicationDTO object
     */
    public static DiscoveredApplicationDTO fromDiscoveredApplicationToDTO(DiscoveredApplication discoveredApplication) {
        DiscoveredApplicationDTO dto = new DiscoveredApplicationDTO();
        dto.setExternalId(discoveredApplication.getExternalId());
        dto.setName(discoveredApplication.getName());
        dto.setDescription(discoveredApplication.getDescription());
        dto.setTier(discoveredApplication.getThrottlingTier());
        dto.setOwner(discoveredApplication.getOwner());
        dto.setCreatedTime(discoveredApplication.getCreatedTime());
        dto.setAttributes(discoveredApplication.getAttributes());
        dto.setAlreadyImported(discoveredApplication.isAlreadyImported());
        dto.setImportedApplicationId(discoveredApplication.getImportedApplicationId());

        if (discoveredApplication.getKeyInfoList() != null) {
            List<DiscoveredApplicationKeyInfoDTO> keyInfoDTOS = new ArrayList<>();
            for (DiscoveredApplicationKeyInfo keyInfo : discoveredApplication.getKeyInfoList()) {
                keyInfoDTOS.add(fromKeyInfoToDTO(keyInfo));
            }
            dto.setKeyInfoList(keyInfoDTOS);
        }
        return dto;
    }

    /**
     * Converts DiscoveredApplicationKeyInfo model to DiscoveredApplicationKeyInfoDTO
     *
     * @param keyInfo DiscoveredApplicationKeyInfo model
     * @return DiscoveredApplicationKeyInfoDTO object
     */
    public static DiscoveredApplicationKeyInfoDTO fromKeyInfoToDTO(DiscoveredApplicationKeyInfo keyInfo) {
        DiscoveredApplicationKeyInfoDTO dto = new DiscoveredApplicationKeyInfoDTO();
        dto.setKeyType(keyInfo.getKeyType());
        dto.setKeyName(keyInfo.getKeyName());
        dto.setMaskedKeyValue(keyInfo.getMaskedKeyValue());
        dto.setExternalKeyReference(keyInfo.getExternalKeyReference());
        dto.setCreatedTime(keyInfo.getCreatedTime());
        dto.setExpiryTime(keyInfo.getExpiryTime());
        dto.setState(keyInfo.getState());
        return dto;
    }

    /**
     * Converts DiscoveredApplicationResult to DiscoveredApplicationListDTO
     *
     * @param result DiscoveredApplicationResult model
     * @return DiscoveredApplicationListDTO object
     */
    public static DiscoveredApplicationListDTO fromDiscoveredApplicationListToDTO(DiscoveredApplicationResult result) {
        DiscoveredApplicationListDTO listDTO = new DiscoveredApplicationListDTO();
        listDTO.setCount(result.getReturnedCount());
        
        List<DiscoveredApplicationDTO> dtos = new ArrayList<>();
        if (result.getDiscoveredApplications() != null) {
            for (DiscoveredApplication app : result.getDiscoveredApplications()) {
                dtos.add(fromDiscoveredApplicationToDTO(app));
            }
        }
        listDTO.setList(dtos);

        // Build pagination with next/previous links
        String nextUrl = null;
        String previousUrl = null;
        
        if (result.isHasMoreResults()) {
            nextUrl = "";
        }
        
        if (result.getOffset() > 0) {
            // Indicate there are previous results
            previousUrl = "";
        }
        
        PaginationDTO paginationDTO = CommonMappingUtil.getPaginationDTO(
                result.getLimit(), 
                result.getOffset(), 
                result.getTotalCount(),
                nextUrl,
                previousUrl);
        
        listDTO.setPagination(paginationDTO);
        
        return listDTO;
    }
}
