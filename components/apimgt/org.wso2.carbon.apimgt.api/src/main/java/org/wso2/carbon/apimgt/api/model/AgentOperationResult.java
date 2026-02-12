/*
 * Copyright (c) 2026, WSO2 LLC. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 LLC. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.wso2.carbon.apimgt.api.model;

/**
 * Bundled result returned by {@link org.wso2.carbon.apimgt.api.FederatedSubscriptionAgent}
 * from mutating operations (create, regenerate).
 * <p>
 * The agent fully owns its artifact lifecycle — the service layer treats
 * {@code referenceArtifact} as an opaque blob and never parses it.
 * </p>
 */
public class AgentOperationResult {

    private final FederatedCredential credential;
    private final InvocationInstruction instruction;
    private final String referenceArtifact;
    private final String externalSubscriptionId;

    private AgentOperationResult(Builder builder) {
        this.credential = builder.credential;
        this.instruction = builder.instruction;
        this.referenceArtifact = builder.referenceArtifact;
        this.externalSubscriptionId = builder.externalSubscriptionId;
    }

    public static Builder builder() {
        return new Builder();
    }

    public FederatedCredential getCredential() {
        return credential;
    }

    public InvocationInstruction getInstruction() {
        return instruction;
    }

    public String getReferenceArtifact() {
        return referenceArtifact;
    }

    public String getExternalSubscriptionId() {
        return externalSubscriptionId;
    }

    public static class Builder {
        private FederatedCredential credential;
        private InvocationInstruction instruction;
        private String referenceArtifact;
        private String externalSubscriptionId;

        public Builder credential(FederatedCredential credential) {
            this.credential = credential;
            return this;
        }

        public Builder instruction(InvocationInstruction instruction) {
            this.instruction = instruction;
            return this;
        }

        public Builder referenceArtifact(String referenceArtifact) {
            this.referenceArtifact = referenceArtifact;
            return this;
        }

        public Builder externalSubscriptionId(String externalSubscriptionId) {
            this.externalSubscriptionId = externalSubscriptionId;
            return this;
        }

        public AgentOperationResult build() {
            return new AgentOperationResult(this);
        }
    }
}
