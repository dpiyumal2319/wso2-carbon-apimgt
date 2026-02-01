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

import org.wso2.carbon.apimgt.api.model.InvocationInstruction;
import org.wso2.carbon.apimgt.rest.api.store.v1.dto.InvocationInstructionDTO;

/**
 * Mapping utility for invocation instruction related DTOs.
 * Maps opaque body field directly without parsing. All JSON structure
 * interpretation is the responsibility of gateway connectors and frontend.
 */
public class InvocationInstructionMappingUtil {

    /**
     * Converts InvocationInstruction model to DTO.
     * Simply passes through the opaque body field without interpretation.
     */
    public static InvocationInstructionDTO fromInvocationInstructionToDTO(InvocationInstruction instruction) {
        if (instruction == null) {
            return null;
        }

        InvocationInstructionDTO dto = new InvocationInstructionDTO();
        dto.setBody(instruction.getBody());

        return dto;
    }
}
