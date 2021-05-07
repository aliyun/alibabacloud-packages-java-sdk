package com.alibaba.aone.artlab.api.service;

import com.alibaba.aone.artlab.api.ArtlabApi;
import com.alibaba.aone.artlab.api.model.module.Module;
import com.alibaba.aone.artlab.api.model.module.Version;
import com.alibaba.aone.artlab.api.request.module.ListModulesRequest;
import com.alibaba.aone.artlab.api.request.module.ListVersionsRequest;

import java.util.Collections;
import java.util.List;

public class ModuleApi extends AbstractBaseApi {
    public ModuleApi(ArtlabApi artlabApi) {
        super(artlabApi);
    }

    public List<Module> listModules(ListModulesRequest request) {
        // TODO: 2021/3/29 implement
        return Collections.emptyList();
    }

    public List<Version> listVersions(ListVersionsRequest request) {
        // TODO: 2021/3/29 implement
        return Collections.emptyList();
    }
}
