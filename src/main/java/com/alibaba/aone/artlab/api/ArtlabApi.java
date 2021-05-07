package com.alibaba.aone.artlab.api;

import com.alibaba.aone.artlab.api.service.GenericApi;
import com.alibaba.aone.artlab.api.service.ModuleApi;
import com.alibaba.aone.artlab.api.service.RepositoryApi;
import com.alibaba.aone.artlab.api.service.RsyncApi;
import com.alibaba.aone.artlab.api.util.UrlUtil;
import com.google.common.base.Preconditions;
import org.apache.commons.lang3.StringUtils;

public class ArtlabApi implements AutoCloseable {
    private final ArtlabApiClient apiClient;
    private final String artlabServerUrl;
    private final String orgId;

    private volatile GenericApi genericApi;
    private volatile RepositoryApi repositoryApi;
    private volatile ModuleApi moduleApi;
    private volatile RsyncApi rsyncApi;

    public ArtlabApi(String artlabServerUrl, String username, String password, String orgId,
                     ArtlabApiClientConfiguration clientConfiguration) {
        Preconditions.checkNotNull(clientConfiguration, "artlab client configuration should not be null");
        Preconditions.checkArgument(UrlUtil.isValidHttpUrl(artlabServerUrl), "illegal artlab server url " + artlabServerUrl);
        Preconditions.checkArgument(StringUtils.isNotBlank(orgId), "organization id should not be empty");
        this.apiClient = new ArtlabApiClient(artlabServerUrl, username, password, orgId, clientConfiguration);
        this.artlabServerUrl = artlabServerUrl;
        this.orgId = orgId;
    }

    public ArtlabApiClient getApiClient() {
        return apiClient;
    }

    public String getArtlabServerUrl() {
        return artlabServerUrl;
    }

    public String getOrgId() {
        return orgId;
    }

    @Override
    public void close() throws Exception {
        this.apiClient.close();
    }

    public GenericApi getGenericApi() {
        if (genericApi == null) {
            synchronized (this) {
                if (genericApi == null) {
                    genericApi = new GenericApi(this);
                }
            }
        }
        return genericApi;
    }

    public RepositoryApi getRepositoryApi() {
        if (repositoryApi == null) {
            synchronized (this) {
                if (repositoryApi == null) {
                    repositoryApi = new RepositoryApi(this);
                }
            }
        }
        return repositoryApi;
    }

    public ModuleApi getModuleApi() {
        if (moduleApi == null) {
            synchronized (this) {
                if (moduleApi == null) {
                    moduleApi = new ModuleApi(this);
                }
            }
        }
        return moduleApi;
    }

    public RsyncApi getRsyncApi() {
        if (rsyncApi == null) {
            synchronized (this) {
                if (rsyncApi == null) {
                    rsyncApi = new RsyncApi(this);
                }
            }
        }
        return rsyncApi;
    }

    public static class Builder {
        private String artlabServerUrl;
        private String username = "";
        private String password = "";
        private String orgId;
        private ArtlabApiClientConfiguration clientConfiguration = ArtlabApiClientConfiguration.DEFAULT;

        public Builder withServer(String artlabServerUrl) {
            this.artlabServerUrl = artlabServerUrl;
            return this;
        }

        public Builder withBasicCredential(String username, String password) {
            this.username = username;
            this.password = password;
            return this;
        }


        public Builder withOrg(String orgId) {
            this.orgId = orgId;
            return this;
        }

        public Builder withClientConfiguration(ArtlabApiClientConfiguration clientConfiguration) {
            this.clientConfiguration = clientConfiguration;
            return this;
        }

        public ArtlabApi build() {
            return new ArtlabApi(artlabServerUrl, username, password, orgId, clientConfiguration);
        }
    }
}
