package com.alibaba.aone.artlab.api.service;

import com.alibaba.aone.artlab.api.ArtlabApi;
import com.alibaba.aone.artlab.api.model.ArtlabClientException;
import com.alibaba.aone.artlab.api.model.generic.GenericFile;
import com.alibaba.aone.artlab.api.request.generic.DownloadGenericFileRequest;
import com.alibaba.aone.artlab.api.request.generic.UploadGenericFileRequest;
import com.alibaba.aone.artlab.api.service.AbstractBaseApi;
import com.google.common.base.Preconditions;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

public class GenericApi extends AbstractBaseApi {
    public GenericApi(ArtlabApi artlabApi) {
        super(artlabApi);
    }

    public InputStream download(DownloadGenericFileRequest request) throws IOException, ArtlabClientException {
        super.checkReqRepoInfo(request);
        Preconditions.checkArgument(StringUtils.isNotBlank(request.getFilepath()), "download filepath must not be empty");
        String version = request.getVersion();

        // The requested version can be empty
        MultiValuedMap<String, String> params = buildVersionParams(version);
        String path = String.format(Locale.ENGLISH, "api/protocol/%s/%s/%s/files/%s", resolveOrgId(request),
                request.getRepoType().name(), request.getRepoId(), request.getFilepath());
        return this.artlabApi.getApiClient().getStream(params, path);
    }

    public GenericFile upload(UploadGenericFileRequest request) throws IOException, ArtlabClientException {
        super.checkReqRepoInfo(request);
        Preconditions.checkArgument(StringUtils.isNotBlank(request.getUploadPath()), "upload filepath must not be empty");
        File uploadFile = request.getUploadFile();
        Preconditions.checkArgument(uploadFile != null, "upload file muse not be null");
        Preconditions.checkArgument(uploadFile.exists() && uploadFile.isFile(), "upload file can not find");
        String version = request.getVersion();

        // The requested version can be empty
        MultiValuedMap<String, String> params = buildVersionParams(version);
        String path = String.format(Locale.ENGLISH, "api/protocol/%s/%s/%s/files/%s", resolveOrgId(request),
                request.getRepoType().name(), request.getRepoId(), request.getUploadPath());
        return this.artlabApi.getApiClient().upload(uploadFile, params, path, GenericFile.class);
    }

    private MultiValuedMap<String, String> buildVersionParams(String version) {
        if (StringUtils.isBlank(version)) {
            return null;
        }
        MultiValuedMap<String, String> params = new ArrayListValuedHashMap<>();
        params.put(PARAM_KEY_VERSION, version);
        return params;
    }
}
