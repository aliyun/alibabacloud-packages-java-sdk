package com.alibaba.aone.artlab.api.model;

public class ArtlabResponse<T> extends ArtlabBaseResponse {
    private T object;

    public T getObject() {
        return object;
    }

    public void setObject(T object) {
        this.object = object;
    }
}
