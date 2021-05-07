package com.alibaba.aone.artlab.api.request.rsync;

import com.alibaba.aone.artlab.api.request.ArtlabRepoResApiRequest;

public class AddRsyncTasksRequest extends ArtlabRepoResApiRequest<AddRsyncTasksRequest> {
    private String moduleOrg;
    private String moduleName;
    private String version;

    // rsyncSite and rsyncType are mutually exclusive parameters,
    // only one of them is allowed to be configured
    private String rsyncSite;
    private String rsyncType;

    public AddRsyncTasksRequest withModule(String moduleOrg, String moduleName) {
        this.moduleOrg = moduleOrg;
        this.moduleName = moduleName;
        return this;
    }

    public AddRsyncTasksRequest withVersion(String version) {
        this.version = version;
        return this;
    }

    public AddRsyncTasksRequest withRsyncSite(String rsyncSite) {
        this.rsyncSite = rsyncSite;
        return this;
    }

    public AddRsyncTasksRequest withRsyncType(String rsyncType) {
        this.rsyncType = rsyncType;
        return this;
    }

    public String getModuleOrg() {
        return moduleOrg;
    }

    public String getModuleName() {
        return moduleName;
    }

    public String getVersion() {
        return version;
    }

    public String getRsyncSite() {
        return rsyncSite;
    }

    public String getRsyncType() {
        return rsyncType;
    }
}
