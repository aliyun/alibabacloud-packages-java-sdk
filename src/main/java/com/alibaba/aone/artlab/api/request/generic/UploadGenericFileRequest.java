package com.alibaba.aone.artlab.api.request.generic;

import com.alibaba.aone.artlab.api.model.RepoType;
import com.alibaba.aone.artlab.api.request.ArtlabRepoResApiRequest;

import java.io.File;
import java.io.Serializable;

public class UploadGenericFileRequest extends ArtlabRepoResApiRequest<UploadGenericFileRequest> implements Serializable {
    private String uploadPath;
    private String version;
    private File uploadFile;

    public UploadGenericFileRequest withUploadPath(String uploadPath) {
        this.uploadPath = uploadPath;
        return this;
    }

    public UploadGenericFileRequest withVersion(String version) {
        this.version = version;
        return this;
    }

    public UploadGenericFileRequest withUploadFile(File uploadFile) {
        this.uploadFile = uploadFile;
        return this;
    }

    public String getUploadPath() {
        return uploadPath;
    }

    public String getVersion() {
        return version;
    }

    public File getUploadFile() {
        return uploadFile;
    }

    @Override
    public RepoType getRepoType() {
        return RepoType.GENERIC;
    }
}
