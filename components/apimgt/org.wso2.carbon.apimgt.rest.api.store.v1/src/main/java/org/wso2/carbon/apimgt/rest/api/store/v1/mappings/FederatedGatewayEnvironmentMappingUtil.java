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
import org.wso2.carbon.apimgt.rest.api.store.v1.dto.FederatedGatewayEnvironmentDTO;
import org.wso2.carbon.apimgt.rest.api.store.v1.dto.FederatedGatewayEnvironmentListDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for mapping Environment model to FederatedGatewayEnvironment DTOs.
 */
public class FederatedGatewayEnvironmentMappingUtil {

    /**
     * Convert list of Environment to FederatedGatewayEnvironmentListDTO
     *
     * @param envList List of Environment
     * @return FederatedGatewayEnvironmentListDTO containing environment list
     */
    public static FederatedGatewayEnvironmentListDTO fromEnvListToEnvListDTO(List<Environment> envList) {
        FederatedGatewayEnvironmentListDTO envListDTO = new FederatedGatewayEnvironmentListDTO();
        List<FederatedGatewayEnvironmentDTO> environmentDTOs = new ArrayList<>();
        
        for (Environment env : envList) {
            environmentDTOs.add(fromEnvToEnvDTO(env));
        }
        
        envListDTO.setCount(environmentDTOs.size());
        envListDTO.setList(environmentDTOs);
        return envListDTO;
    }

    /**
     * Convert Environment to FederatedGatewayEnvironmentDTO
     *
     * @param env Environment
     * @return FederatedGatewayEnvironmentDTO containing environment details
     */
    public static FederatedGatewayEnvironmentDTO fromEnvToEnvDTO(Environment env) {
        FederatedGatewayEnvironmentDTO envDTO = new FederatedGatewayEnvironmentDTO();
        envDTO.setId(env.getUuid());
        envDTO.setName(env.getName());
        envDTO.setDisplayName(env.getDisplayName());
        envDTO.setGatewayType(env.getGatewayType());
        envDTO.setDescription(env.getDescription());
        return envDTO;
    }
}
