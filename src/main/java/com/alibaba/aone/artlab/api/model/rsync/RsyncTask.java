package com.alibaba.aone.artlab.api.model.rsync;

public class RsyncTask {
    private Long id;
    private String rsyncStatus;

    private String artifactName;
    private String artifactVersion;
    private String artifactChecksum;

    private String repoType;
    private String repoId;
    private Long regionId;

    private Long followSIteId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRsyncStatus() {
        return rsyncStatus;
    }

    public void setRsyncStatus(String rsyncStatus) {
        this.rsyncStatus = rsyncStatus;
    }

    public String getArtifactName() {
        return artifactName;
    }

    public void setArtifactName(String artifactName) {
        this.artifactName = artifactName;
    }

    public String getArtifactVersion() {
        return artifactVersion;
    }

    public void setArtifactVersion(String artifactVersion) {
        this.artifactVersion = artifactVersion;
    }

    public String getArtifactChecksum() {
        return artifactChecksum;
    }

    public void setArtifactChecksum(String artifactChecksum) {
        this.artifactChecksum = artifactChecksum;
    }

    public String getRepoType() {
        return repoType;
    }

    public void setRepoType(String repoType) {
        this.repoType = repoType;
    }

    public String getRepoId() {
        return repoId;
    }

    public void setRepoId(String repoId) {
        this.repoId = repoId;
    }

    public Long getRegionId() {
        return regionId;
    }

    public void setRegionId(Long regionId) {
        this.regionId = regionId;
    }

    public Long getFollowSIteId() {
        return followSIteId;
    }

    public void setFollowSIteId(Long followSIteId) {
        this.followSIteId = followSIteId;
    }
}
