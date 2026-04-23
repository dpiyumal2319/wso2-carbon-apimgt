/*
 * Copyright (c) 2026, WSO2 LLC. (https://www.wso2.com/).
 *
 * WSO2 LLC. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.wso2.carbon.apimgt.impl.notifier;

import org.apache.commons.lang3.StringUtils;
import org.wso2.carbon.apimgt.api.APIManagementException;
import org.wso2.carbon.apimgt.api.model.APIKeyInfo;
import org.wso2.carbon.apimgt.impl.APIConstants;
import org.wso2.carbon.apimgt.impl.dao.ApiKeyMgtDAO;
import org.wso2.carbon.apimgt.impl.notifier.events.APIKeyAssociationEvent;
import org.wso2.carbon.apimgt.impl.notifier.events.APIKeyEvent;
import org.wso2.carbon.apimgt.impl.notifier.events.APIKeyRegenerationEvent;
import org.wso2.carbon.apimgt.impl.notifier.events.Event;
import org.wso2.carbon.apimgt.impl.notifier.exceptions.NotifierException;
import org.wso2.carbon.apimgt.impl.utils.APIUtil;

/**
 * APIKeyNotifier is responsible for publishing events related to API Keys. Whenever there is a change in the API Key.
 */
public class APIKeyNotifier extends AbstractNotifier {


    @Override
    public boolean publishEvent(Event event) throws NotifierException {
        if (isFederatedApiKeyEvent(event)) {
            return true;
        }
        publishEventToEventHub(event);
        return true;
    }

    @Override
    public String getType() {

        return APIConstants.NotifierType.API_KEY.name();
    }

    private boolean isFederatedApiKeyEvent(Event event) throws NotifierException {

        try {
            String apiUuid = resolveApiUuid(event);
            return StringUtils.isNotBlank(apiUuid) && APIUtil.isFederatedGatewayApi(apiUuid);
        } catch (APIManagementException e) {
            throw new NotifierException("Failed to resolve API key event gateway type", e);
        }
    }

    private String resolveApiUuid(Event event) throws APIManagementException {

        if (event instanceof APIKeyEvent) {
            APIKeyEvent apiKeyEvent = (APIKeyEvent) event;
            String apiUuid = apiKeyEvent.getApiUUId();
            if (StringUtils.isBlank(apiUuid)) {
                apiUuid = resolveApiUuidFromKey(apiKeyEvent.getUuid(), apiKeyEvent.getTenantDomain());
            }
            return apiUuid;
        }
        if (event instanceof APIKeyAssociationEvent) {
            APIKeyAssociationEvent associationEvent = (APIKeyAssociationEvent) event;
            String apiUuid = associationEvent.getApiUUId();
            if (StringUtils.isBlank(apiUuid)) {
                apiUuid = resolveApiUuidFromKey(associationEvent.getApiKeyUUId(), associationEvent.getTenantDomain());
            }
            return apiUuid;
        }
        if (event instanceof APIKeyRegenerationEvent) {
            APIKeyRegenerationEvent regenerationEvent = (APIKeyRegenerationEvent) event;
            String apiUuid = regenerationEvent.getApiUuid();
            if (StringUtils.isBlank(apiUuid)) {
                apiUuid = resolveApiUuidFromKey(regenerationEvent.getNewApiKeyUuid(),
                        regenerationEvent.getTenantDomain());
            }
            if (StringUtils.isBlank(apiUuid)) {
                apiUuid = resolveApiUuidFromKey(regenerationEvent.getOldApiKeyUuid(),
                        regenerationEvent.getTenantDomain());
            }
            return apiUuid;
        }
        return null;
    }

    private String resolveApiUuidFromKey(String apiKeyUuid, String tenantDomain) throws APIManagementException {

        if (StringUtils.isAnyBlank(apiKeyUuid, tenantDomain)) {
            return null;
        }
        APIKeyInfo apiKeyInfo = ApiKeyMgtDAO.getInstance().getAPIKeyForTenantAnyStatus(apiKeyUuid, tenantDomain);
        return apiKeyInfo != null ? apiKeyInfo.getApiUUId() : null;
    }
}
