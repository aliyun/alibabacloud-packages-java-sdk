package com.alibaba.aone.artlab.api.service;

import com.alibaba.aone.artlab.api.ArtlabApi;
import com.alibaba.aone.artlab.api.request.ArtlabApiRequest;
import com.alibaba.aone.artlab.api.request.ArtlabRepoResApiRequest;
import com.google.common.base.Preconditions;
import org.apache.commons.lang3.StringUtils;

public abstract class AbstractBaseApi {
    public static final String PARAM_KEY_VERSION = "version";
    public static final String PARAM_KEY_PAGE_NUM = "pageNum";
    public static final String PARAM_KEY_PAGE_SIZE = "pageSize";

    protected final ArtlabApi artlabApi;

    protected AbstractBaseApi(ArtlabApi artlabApi) {
        this.artlabApi = artlabApi;
    }

    public void checkReqRepoInfo(ArtlabRepoResApiRequest<?> request) {
        Preconditions.checkNotNull(request.getRepoType(), "repo type must not be null");
        Preconditions.checkArgument(StringUtils.isNotBlank(request.getRepoId()), "repo id must not be null");
    }

    public String resolveOrgId(ArtlabApiRequest<?> request) {
        return StringUtils.isNotBlank(request.getOrgId()) ? request.getOrgId() : artlabApi.getOrgId();
    }
}
