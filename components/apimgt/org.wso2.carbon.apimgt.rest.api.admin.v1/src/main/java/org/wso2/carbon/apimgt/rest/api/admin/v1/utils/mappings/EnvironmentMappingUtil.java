/*
 *  Copyright (c) 2021, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package org.wso2.carbon.apimgt.rest.api.admin.v1.utils.mappings;

import org.wso2.carbon.apimgt.api.dto.GatewayVisibilityPermissionConfigurationDTO;
import org.wso2.carbon.apimgt.api.model.Environment;
import org.wso2.carbon.apimgt.api.model.GatewayTierMapping;
import org.wso2.carbon.apimgt.api.model.VHost;
import org.wso2.carbon.apimgt.rest.api.admin.v1.dto.AdditionalPropertyDTO;
import org.wso2.carbon.apimgt.rest.api.admin.v1.dto.EnvironmentDTO;
import org.wso2.carbon.apimgt.rest.api.admin.v1.dto.EnvironmentListDTO;
import org.wso2.carbon.apimgt.rest.api.admin.v1.dto.EnvironmentPermissionsDTO;
import org.wso2.carbon.apimgt.rest.api.admin.v1.dto.GatewayTierMappingDTO;
import org.wso2.carbon.apimgt.rest.api.admin.v1.dto.VHostDTO;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * This class manage Environment mapping to EnvironmentDTO
 */
public class EnvironmentMappingUtil {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    /**
     * Convert list of Environment to EnvironmentListDTO
     *
     * @param envList List of Environment
     * @return EnvironmentListDTO containing Environment list
     */
    public static EnvironmentListDTO fromEnvListToEnvListDTO(List<Environment> envList) {
        EnvironmentListDTO envListDTO = new EnvironmentListDTO();
        envListDTO.setCount(envList.size());
        envListDTO.setList(envList.stream().map(EnvironmentMappingUtil::fromEnvToEnvDTO).collect(Collectors.toList()));
        return envListDTO;
    }

    /**
     * Convert Environment to EnvironmentDTO
     *
     * @param env Environment
     * @return EnvironmentDTO containing Environment
     */
    public static EnvironmentDTO fromEnvToEnvDTO(Environment env) {
        EnvironmentDTO envDTO = new EnvironmentDTO();
        envDTO.setId(env.getUuid());
        envDTO.setName(env.getName());
        envDTO.setType(env.getType());
        envDTO.setDisplayName(env.getDisplayName());
        envDTO.setDescription(env.getDescription());
        envDTO.setProvider(env.getProvider());
        envDTO.setGatewayType(env.getGatewayType());
        envDTO.setIsReadOnly(env.isReadOnly());
        envDTO.setMode(EnvironmentDTO.ModeEnum.valueOf(env.getMode()));
        envDTO.setApiDiscoveryScheduledWindow(env.getApiDiscoveryScheduledWindow());
        envDTO.setVhosts(env.getVhosts().stream().map(EnvironmentMappingUtil::fromVHostToVHostDTO)
                .collect(Collectors.toList()));
        envDTO.setAdditionalProperties(fromAdditionalPropertiesToAdditionalPropertiesDTO
                (env.getAdditionalProperties()));
        envDTO.setTierMappings(fromTierMappingsToTierMappingDTOs(env.getTierMappings()));
        GatewayVisibilityPermissionConfigurationDTO permissions = env.getPermissions();
        if (permissions != null) {
            EnvironmentPermissionsDTO environmentPermissionsDTO = new EnvironmentPermissionsDTO();
            environmentPermissionsDTO.setPermissionType(EnvironmentPermissionsDTO.PermissionTypeEnum
                    .fromValue(permissions.getPermissionType()));
            environmentPermissionsDTO.setRoles(permissions.getRoles());
            envDTO.setPermissions(environmentPermissionsDTO);
        }
        return envDTO;
    }

    /**
     * Converts AdditionalProperties into a AdditionalPropertiesDTO.
     *
     * @param additionalProperties Set of additional properties
     * @return List<AdditionalPropertyDTO>
     */
    public static List<AdditionalPropertyDTO> fromAdditionalPropertiesToAdditionalPropertiesDTO(Map<String, String>
                                                                                                        additionalProperties) {
        List<AdditionalPropertyDTO> additionalPropertyDTOList = new ArrayList<>();
        for (Map.Entry<String, String> entry : additionalProperties.entrySet()) {
            AdditionalPropertyDTO additionalPropertyDTO = new AdditionalPropertyDTO();
            additionalPropertyDTO.setKey(entry.getKey());
            additionalPropertyDTO.setValue(entry.getValue());
            additionalPropertyDTOList.add(additionalPropertyDTO);
        }
        return additionalPropertyDTOList;
    }
    /**
     * Convert VHost to VHostDTO
     *
     * @param vHost VHost
     * @return VHostDTO
     */
    public static VHostDTO fromVHostToVHostDTO(VHost vHost) {
        VHostDTO vHostDTO = new VHostDTO();
        vHostDTO.setHost(vHost.getHost());
        vHostDTO.setHttpContext(vHost.getHttpContext());
        vHostDTO.setHttpPort(vHost.getHttpPort());
        vHostDTO.setHttpsPort(vHost.getHttpsPort());
        vHostDTO.setWsPort(vHost.getWsPort());
        vHostDTO.setWssPort(vHost.getWssPort());
        return vHostDTO;
    }

    /**
     * Convert EnvironmentListDTO to list of Environment
     *
     * @param envListDto EnvironmentListDTO
     * @return EnvironmentListDTO containing Environment list
     */
    public static List<Environment> fromEnvListDtoToEnvList(EnvironmentListDTO envListDto) {
        List<Environment> envList = new ArrayList<>(envListDto.getCount());
        for (EnvironmentDTO envDto : envListDto.getList()) {
            envList.add(fromEnvDtoToEnv(envDto));
        }
        return envList;
    }

    /**
     * Convert EnvironmentDTO to Environment
     *
     * @param envDTO EnvironmentDTO
     * @return Environment
     */
    public static Environment fromEnvDtoToEnv(EnvironmentDTO envDTO) {
        Environment env = new Environment();
        env.setUuid(envDTO.getId());
        env.setName(envDTO.getName());
        env.setType(envDTO.getType());
        env.setDisplayName(envDTO.getDisplayName());
        env.setDescription(envDTO.getDescription());
        env.setProvider(envDTO.getProvider());
        env.setGatewayType(envDTO.getGatewayType());
        env.setReadOnly(envDTO.isIsReadOnly());
        env.setMode(envDTO.getMode().toString());
        env.setApiDiscoveryScheduledWindow(envDTO.getApiDiscoveryScheduledWindow());
        env.setVhosts(envDTO.getVhosts().stream().map(EnvironmentMappingUtil::fromVHostDtoToVHost)
                .collect(Collectors.toList()));
        env.setAdditionalProperties(fromAdditionalPropertiesDTOToAdditionalProperties
                (envDTO.getAdditionalProperties()));
        env.setTierMappings(fromTierMappingDTOsToTierMappings(envDTO.getTierMappings()));
        EnvironmentPermissionsDTO permissions = envDTO.getPermissions();
        if (permissions != null && permissions.getPermissionType() != null) {
            GatewayVisibilityPermissionConfigurationDTO permissionsConfiguration = new GatewayVisibilityPermissionConfigurationDTO();
            permissionsConfiguration.setPermissionType(permissions.getPermissionType().toString());
            permissionsConfiguration.setRoles(permissions.getRoles());
            env.setPermissions(permissionsConfiguration);
        } else {
            env.setPermissions(new GatewayVisibilityPermissionConfigurationDTO());
        }
        return env;
    }

    /**
     * Convert VHostDTO to VHost
     *
     * @param vhostDTO VHostDTO
     * @return VHostDTO
     */
    public static VHost fromVHostDtoToVHost(VHostDTO vhostDTO) {
        VHost vhost = new VHost();
        vhost.setHost(vhostDTO.getHost());
        vhost.setHttpContext(vhostDTO.getHttpContext());
        vhost.setHttpPort(vhostDTO.getHttpPort());
        vhost.setHttpsPort(vhostDTO.getHttpsPort());
        vhost.setWsPort(vhostDTO.getWsPort());
        vhost.setWssPort(vhostDTO.getWssPort());
        if (vhostDTO.getWsHost() == null) {
            vhost.setWsHost(vhostDTO.getHost());
        } else {
            vhost.setWsHost(vhostDTO.getWsHost());
        }
        if (vhostDTO.getWssHost() == null) {
            vhost.setWssHost(vhostDTO.getHost());
        } else {
            vhost.setWssHost(vhostDTO.getWssHost());
        }
        return vhost;
    }

    /**
     * Converts AdditionalPropertiesDTO into a AdditionalProperties map.
     *
     * @param additionalPropertiesDTOs Set of additional propertyDTOs
     * @return Map<String, String> of Additional properties
     */
    public static Map<String, String>  fromAdditionalPropertiesDTOToAdditionalProperties(List<AdditionalPropertyDTO>
                                                                                                 additionalPropertiesDTOs) {
        Map<String,String> additionalProperties = new HashMap<>();
        for (AdditionalPropertyDTO entry : additionalPropertiesDTOs) {
            additionalProperties.putIfAbsent(entry.getKey(),entry.getValue());
        }
        return additionalProperties;
    }

    /**
     * Converts a list of GatewayTierMapping model objects to GatewayTierMappingDTOs.
     * The remotePlanReference JSON string is deserialized to a Map for the DTO.
     */
    public static List<GatewayTierMappingDTO> fromTierMappingsToTierMappingDTOs(
            List<GatewayTierMapping> tierMappings) {
        List<GatewayTierMappingDTO> dtos = new ArrayList<>();
        if (tierMappings == null) {
            return dtos;
        }
        for (GatewayTierMapping mapping : tierMappings) {
            GatewayTierMappingDTO dto = new GatewayTierMappingDTO();
            dto.setLocalTierName(mapping.getLocalTierName());
            Map<String, Object> refMap = Collections.emptyMap();
            if (mapping.getRemotePlanReference() != null) {
                try {
                    refMap = OBJECT_MAPPER.readValue(mapping.getRemotePlanReference(),
                            new TypeReference<Map<String, Object>>() {});
                } catch (JsonProcessingException e) {
                    // Stored value is not valid JSON; expose as-is under "raw" key
                    refMap = new HashMap<>();
                    refMap.put("raw", mapping.getRemotePlanReference());
                }
            }
            dto.setRemotePlanReference(refMap);
            dtos.add(dto);
        }
        return dtos;
    }

    /**
     * Converts a list of GatewayTierMappingDTOs to GatewayTierMapping model objects.
     * The remotePlanReference Map is serialized to a JSON string for storage.
     */
    public static List<GatewayTierMapping> fromTierMappingDTOsToTierMappings(
            List<GatewayTierMappingDTO> dtos) {
        List<GatewayTierMapping> mappings = new ArrayList<>();
        if (dtos == null) {
            return mappings;
        }
        for (GatewayTierMappingDTO dto : dtos) {
            String refJson = null;
            if (dto.getRemotePlanReference() != null) {
                try {
                    refJson = OBJECT_MAPPER.writeValueAsString(dto.getRemotePlanReference());
                } catch (JsonProcessingException e) {
                    refJson = "{}";
                }
            }
            GatewayTierMapping mapping = new GatewayTierMapping();
            mapping.setLocalTierName(dto.getLocalTierName());
            mapping.setRemotePlanReference(refJson);
            mappings.add(mapping);
        }
        return mappings;
    }

}
