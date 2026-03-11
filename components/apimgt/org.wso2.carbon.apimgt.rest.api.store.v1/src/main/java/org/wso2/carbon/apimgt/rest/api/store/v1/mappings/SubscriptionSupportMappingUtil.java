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

import org.wso2.carbon.apimgt.api.model.FederatedSubscriptionOptions;
import org.wso2.carbon.apimgt.api.model.InvocationInstruction;
import org.wso2.carbon.apimgt.api.model.SubscriptionSupportInfo;
import org.wso2.carbon.apimgt.rest.api.store.v1.dto.FederatedSubscriptionOptionsDTO;
import org.wso2.carbon.apimgt.rest.api.store.v1.dto.InvocationTemplateDTO;
import org.wso2.carbon.apimgt.rest.api.store.v1.dto.SubscriptionSupportInfoDTO;

import java.util.Arrays;

/**
 * Mapping utility for subscription support information.
 * Converts SubscriptionSupportInfo POJO to DTO with enum mapping.
 */
public class SubscriptionSupportMappingUtil {

    /**
     * Converts SubscriptionSupportInfo model to DTO.
     * Maps the POJO status enum to DTO enum and passes through auth types, options,
     * and invocation template.
     */
    public static SubscriptionSupportInfoDTO toDTO(SubscriptionSupportInfo info) {
        if (info == null) {
            return null;
        }

        SubscriptionSupportInfoDTO dto = new SubscriptionSupportInfoDTO();

        // Map status enum
        if (info.getStatus() != null) {
            dto.setSubscriptionStatus(
                    SubscriptionSupportInfoDTO.SubscriptionStatusEnum.fromValue(info.getStatus().name()));
        }

        // Map supported auth types
        if (info.getSupportedAuthTypes() != null) {
            dto.setSupportedAuthTypes(Arrays.asList(info.getSupportedAuthTypes()));
        }

        // Map subscription options if present
        if (info.getSubscriptionOptions() != null) {
            dto.setSubscriptionOptions(fromFederatedSubscriptionOptionsToDTO(info.getSubscriptionOptions()));
        }

        // Map invocation template if present
        if (info.getInvocationTemplate() != null) {
            InvocationInstruction template = info.getInvocationTemplate();
            InvocationTemplateDTO templateDTO = new InvocationTemplateDTO();
            templateDTO.setSchemaName(template.getSchemaName());
            templateDTO.setBody(template.getBodyAsJson());
            dto.setInvocationTemplate(templateDTO);
        }

        return dto;
    }

    /**
     * Converts FederatedSubscriptionOptions model to DTO.
     * Passes through opaque body and schema name.
     */
    private static FederatedSubscriptionOptionsDTO fromFederatedSubscriptionOptionsToDTO(
            FederatedSubscriptionOptions options) {
        if (options == null) {
            return null;
        }

        FederatedSubscriptionOptionsDTO dto = new FederatedSubscriptionOptionsDTO();
        dto.setBody(options.getBodyAsJson());
        dto.setSchemaName(options.getSchemaName());

        return dto;
    }
}
