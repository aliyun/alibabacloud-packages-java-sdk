package com.alibaba.aone.artlab.api.request;

public abstract class ArtlabApiRequest<T> implements Cloneable {
    private String orgId;
    // Other configuration items can be added

    public T withOrgId(String orgId) {
        this.orgId = orgId;
        @SuppressWarnings("unchecked")
        T t = (T) this;
        return t;
    }

    public String getOrgId() {
        return orgId;
    }
}
