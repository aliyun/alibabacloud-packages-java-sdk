package com.alibaba.aone.artlab.api.request.rsync;

import com.alibaba.aone.artlab.api.request.ArtlabRepoResApiRequest;

public class ListRsyncTasksRequest extends ArtlabRepoResApiRequest<ListRsyncTasksRequest> {
    public static final int DEFAULT_PAGE_SIZE = 10;
    public static final int DEFAULT_PAGE_START = 0;

    private int pageSize = DEFAULT_PAGE_SIZE;
    private int pageStart = DEFAULT_PAGE_START;

    public ListRsyncTasksRequest withPage(int pageStart, int pageSize) {
        this.pageStart = pageStart;
        this.pageSize = pageSize;
        return this;
    }

    public ListRsyncTasksRequest withPageStart(int pageStart) {
        this.pageStart = pageStart;
        return this;
    }

    public ListRsyncTasksRequest withPageSize(int pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getPageStart() {
        return pageStart;
    }
}
