package com.alibaba.aone.artlab.api.service;

import com.alibaba.aone.artlab.api.ArtlabApi;
import com.alibaba.aone.artlab.api.model.ArtlabClientException;
import com.alibaba.aone.artlab.api.model.rsync.RsyncTask;
import com.alibaba.aone.artlab.api.request.rsync.AddRsyncTasksRequest;
import com.alibaba.aone.artlab.api.request.rsync.ListRsyncTasksRequest;
import com.google.common.base.Preconditions;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class RsyncApi extends AbstractBaseApi {

    public RsyncApi(ArtlabApi artlabApi) {
        super(artlabApi);
    }

    /**
     * Get the list of synchronization tasks under the specified repository.
     *
     * @param request list rsync tasks request
     * @return rsync tasks
     */
    public List<RsyncTask> listRsyncTasks(ListRsyncTasksRequest request) throws ArtlabClientException, IOException {
        super.checkReqRepoInfo(request);
        String path = String.format(Locale.ENGLISH, "api/artlab/repo/%s/%s/repos/%s/rsync/list", resolveOrgId(request),
                request.getRepoType().name(), request.getRepoId());
        MultiValuedMap<String, String> params = buildPageParams(request.getPageStart(), request.getPageSize());
        return this.artlabApi.getApiClient().getList(params, path, RsyncTask.class);
    }

    /**
     * Add a rsync task.
     */
    public RsyncTask addRsyncTask(AddRsyncTasksRequest request) throws ArtlabClientException, IOException {
        super.checkReqRepoInfo(request);
        String rsyncType = request.getRsyncType();
        String rsyncSite = request.getRsyncSite();
        boolean isRsyncByType = StringUtils.isNotBlank(rsyncType);
        boolean isRsyncBySite = StringUtils.isNotBlank(rsyncSite);

        // The rsync type and rsync site parameters are mutually exclusive parameters
        Preconditions.checkArgument(!(isRsyncBySite && isRsyncByType),
                "The rsync site and rsync type parameters are mutually exclusive parameters and should not be configured at the same time");
        Preconditions.checkArgument(!(!isRsyncBySite && !isRsyncByType),
                "The required parameters rsync site or rsync type are missing, one of these two parameters should be configured");
        Preconditions.checkArgument(StringUtils.isNotBlank(request.getModuleName()), "rsync module name must be not empty");
        Preconditions.checkArgument(StringUtils.isNotBlank(request.getVersion()), "rsync version must be not empty");

        String path;
        if (isRsyncBySite) {
            path = String.format(Locale.ENGLISH, "/api/artlab/repo/%s/%s/repos/%s/rsync/dest/%s/modules/%s/versions/%s",
                    resolveOrgId(request), request.getRepoType().name(), request.getRepoId(), rsyncSite,
                    joinModuleFullName(request.getModuleOrg(), request.getModuleName()), request.getVersion());
        } else {
            path = String.format(Locale.ENGLISH, "/api/artlab/repo/%s/%s/repos/%s/rsync/dest/type/%s/modules/%s/versions/%s",
                    resolveOrgId(request), request.getRepoType().name(), request.getRepoId(), rsyncType,
                    joinModuleFullName(request.getModuleOrg(), request.getModuleName()), request.getVersion());
        }
        return this.artlabApi.getApiClient().post(null, path, null, RsyncTask.class);
    }

    private MultiValuedMap<String, String> buildPageParams(int pageNum, int pageSize) {
        MultiValuedMap<String, String> params = new ArrayListValuedHashMap<>();
        params.put(PARAM_KEY_PAGE_NUM, String.valueOf(pageNum));
        params.put(PARAM_KEY_PAGE_SIZE, String.valueOf(pageSize));
        return params;
    }

    private String joinModuleFullName(String org, String name) {
        if (StringUtils.isBlank(org)) {
            return name;
        } else {
            return String.format("%s/%s", org, name);
        }
    }
}
