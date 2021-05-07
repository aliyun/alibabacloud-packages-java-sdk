package com.alibaba.aone.artlab.api.model.repository;

import com.alibaba.aone.artlab.api.model.RepoType;

public class Repository {
    private Long regionId;
    private RepoType repoType;
    private String repoId;
    private String repoName;

    public Long getRegionId() {
        return regionId;
    }

    public void setRegionId(Long regionId) {
        this.regionId = regionId;
    }

    public RepoType getRepoType() {
        return repoType;
    }

    public void setRepoType(RepoType repoType) {
        this.repoType = repoType;
    }

    public String getRepoId() {
        return repoId;
    }

    public void setRepoId(String repoId) {
        this.repoId = repoId;
    }

    public String getRepoName() {
        return repoName;
    }

    public void setRepoName(String repoName) {
        this.repoName = repoName;
    }
}
