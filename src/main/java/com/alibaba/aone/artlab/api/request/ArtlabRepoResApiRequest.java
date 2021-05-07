package com.alibaba.aone.artlab.api.request;

import com.alibaba.aone.artlab.api.model.RepoType;

import java.io.Serializable;

public abstract class ArtlabRepoResApiRequest<T> extends ArtlabApiRequest<T> implements Serializable {
    private RepoType repoType;
    private String repoId;

    public T withRepoType(RepoType repoType) {
        this.repoType = repoType;
        @SuppressWarnings("unchecked")
        T t = (T) this;
        return t;
    }

    public T withRepoId(String repoId) {
        this.repoId = repoId;
        @SuppressWarnings("unchecked")
        T t = (T) this;
        return t;
    }

    public RepoType getRepoType() {
        return repoType;
    }

    public String getRepoId() {
        return repoId;
    }
}
