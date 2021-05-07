package com.alibaba.aone.artlab.api.request.module;

import com.alibaba.aone.artlab.api.request.ArtlabRepoResApiRequest;

public class ListVersionsRequest extends ArtlabRepoResApiRequest<ListVersionsRequest> {
    private Long moduleId;

    private String moduleOrg;
    private String moduleName;

    public ListVersionsRequest withModuleId(Long moduleId) {
        this.moduleId = moduleId;
        return this;
    }

    public ListVersionsRequest withModule(String moduleOrg, String moduleName) {
        this.moduleOrg = moduleOrg;
        this.moduleName = moduleName;
        return this;
    }

    public Long getModuleId() {
        return moduleId;
    }

    public String getModuleOrg() {
        return moduleOrg;
    }

    public String getModuleName() {
        return moduleName;
    }
}
