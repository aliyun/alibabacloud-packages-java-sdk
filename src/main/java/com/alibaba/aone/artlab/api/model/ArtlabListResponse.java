package com.alibaba.aone.artlab.api.model;

import java.util.List;

public class ArtlabListResponse<T> extends ArtlabBaseResponse {
    private DataList<T> object;

    public DataList<T> getObject() {
        return object;
    }

    public void setObject(DataList<T> object) {
        this.object = object;
    }

    public static class DataList<M> {
        private List<M> dataList;
        private int total;

        public List<M> getDataList() {
            return dataList;
        }

        public void setDataList(List<M> dataList) {
            this.dataList = dataList;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }
    }
}
