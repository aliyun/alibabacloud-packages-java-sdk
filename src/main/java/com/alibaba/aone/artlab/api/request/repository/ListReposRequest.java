package com.alibaba.aone.artlab.api.request.repository;

import com.alibaba.aone.artlab.api.model.RepoType;
import com.alibaba.aone.artlab.api.request.ArtlabApiRequest;

public class ListReposRequest extends ArtlabApiRequest<ListReposRequest> {
    public static final int DEFAULT_PAGE_SIZE = 10;
    public static final int DEFAULT_PAGE_NUM = 0;

    private RepoType repoType;
    private int pageSize = DEFAULT_PAGE_SIZE;
    private int pageNum = DEFAULT_PAGE_NUM;

    public ListReposRequest withRepoType(RepoType repoType) {
        this.repoType = repoType;
        return this;
    }

    public ListReposRequest withPage(int pageNum, int pageSize) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        return this;
    }

    public ListReposRequest withPageNum(int pageNum) {
        this.pageNum = pageNum;
        return this;
    }

    public ListReposRequest withPageSize(int pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    public RepoType getRepoType() {
        return repoType;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getPageNum() {
        return pageNum;
    }
}
