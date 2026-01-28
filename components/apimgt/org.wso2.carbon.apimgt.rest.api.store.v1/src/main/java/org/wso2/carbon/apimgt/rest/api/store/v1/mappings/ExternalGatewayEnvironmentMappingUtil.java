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

import org.wso2.carbon.apimgt.api.model.Environment;
import org.wso2.carbon.apimgt.rest.api.store.v1.dto.ExternalGatewayEnvironmentDTO;
import org.wso2.carbon.apimgt.rest.api.store.v1.dto.ExternalGatewayEnvironmentListDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for mapping Environment model to ExternalGatewayEnvironment DTOs.
 */
public class ExternalGatewayEnvironmentMappingUtil {

    /**
     * Convert list of Environment to ExternalGatewayEnvironmentListDTO
     *
     * @param envList List of Environment
     * @return ExternalGatewayEnvironmentListDTO containing environment list
     */
    public static ExternalGatewayEnvironmentListDTO fromEnvListToEnvListDTO(List<Environment> envList) {
        ExternalGatewayEnvironmentListDTO envListDTO = new ExternalGatewayEnvironmentListDTO();
        List<ExternalGatewayEnvironmentDTO> environmentDTOs = new ArrayList<>();
        
        for (Environment env : envList) {
            environmentDTOs.add(fromEnvToEnvDTO(env));
        }
        
        envListDTO.setCount(environmentDTOs.size());
        envListDTO.setList(environmentDTOs);
        return envListDTO;
    }

    /**
     * Convert Environment to ExternalGatewayEnvironmentDTO
     *
     * @param env Environment
     * @return ExternalGatewayEnvironmentDTO containing environment details
     */
    public static ExternalGatewayEnvironmentDTO fromEnvToEnvDTO(Environment env) {
        ExternalGatewayEnvironmentDTO envDTO = new ExternalGatewayEnvironmentDTO();
        envDTO.setId(env.getUuid());
        envDTO.setName(env.getName());
        envDTO.setDisplayName(env.getDisplayName());
        envDTO.setType(env.getType());
        envDTO.setGatewayType(env.getGatewayType());
        envDTO.setDescription(env.getDescription());
        return envDTO;
    }
}
