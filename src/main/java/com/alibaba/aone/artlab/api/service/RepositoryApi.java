package com.alibaba.aone.artlab.api.service;

import com.alibaba.aone.artlab.api.ArtlabApi;
import com.alibaba.aone.artlab.api.model.RepoType;
import com.alibaba.aone.artlab.api.model.repository.Repository;
import com.alibaba.aone.artlab.api.request.repository.ListReposRequest;

import java.util.Collections;
import java.util.List;

public class RepositoryApi extends AbstractBaseApi {
    public RepositoryApi(ArtlabApi artlabApi) {
        super(artlabApi);
    }

    public List<Repository> listRepos(ListReposRequest request) {
        // TODO: 2021/3/29 implement
        return Collections.emptyList();
    }

    public List<Repository> listRepos(RepoType repoType) {
        return this.listRepos(new ListReposRequest().withRepoType(repoType));
    }

    public List<Repository> listRepos(RepoType repoType, int pageNum, int pageSize) {
        return this.listRepos(new ListReposRequest().withRepoType(repoType).withPage(pageNum, pageSize));
    }
}
