package com.alibaba.aone.artlab.api.request.generic;

import com.alibaba.aone.artlab.api.model.RepoType;
import com.alibaba.aone.artlab.api.request.ArtlabRepoResApiRequest;

import java.io.Serializable;

public class DownloadGenericFileRequest extends ArtlabRepoResApiRequest<DownloadGenericFileRequest> implements Serializable {
    private String filepath;
    private String version;

    public DownloadGenericFileRequest withFilepath(String filepath) {
        this.filepath = filepath;
        return this;
    }

    public DownloadGenericFileRequest withVersion(String version) {
        this.version = version;
        return this;
    }

    public String getFilepath() {
        return filepath;
    }

    public String getVersion() {
        return version;
    }

    @Override
    public RepoType getRepoType() {
        return RepoType.GENERIC;
    }
}
