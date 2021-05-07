package com.alibaba.aone.artlab.api;

import com.alibaba.aone.artlab.api.model.ArtlabClientException;
import com.alibaba.aone.artlab.api.model.ArtlabListResponse;
import com.alibaba.aone.artlab.api.model.ArtlabResponse;
import com.alibaba.aone.artlab.api.util.UrlUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.google.common.base.Preconditions;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

public class ArtlabApiClient implements AutoCloseable {
    public static final String ERROR_CODE_UNKNOWN = "-1";
    private final String serverUrl;
    private final String username;
    private final String password;
    private final String orgId;
    private final ArtlabApiClientConfiguration clientConfiguration;

    private volatile PoolingHttpClientConnectionManager connManager;

    public ArtlabApiClient(String serverUrl, String username, String password, String orgId, ArtlabApiClientConfiguration clientConfiguration) {
        Preconditions.checkArgument(UrlUtil.isValidHttpUrl(serverUrl), "illegal artlab server url " + serverUrl);
        Preconditions.checkArgument(StringUtils.isNotBlank(orgId), "organization id should not be empty");
        this.serverUrl = (serverUrl.endsWith("/") ? serverUrl.replaceAll("/$", "") : serverUrl);
        this.username = username;
        this.password = password;
        this.orgId = orgId;
        this.clientConfiguration = clientConfiguration;
    }

    public <T> T get(MultiValuedMap<String, String> queryParams, String path, Class<T> resultClazz) throws ArtlabClientException, IOException {
        HttpGet httpGet = new HttpGet(buildApiURL(queryParams, path));
        httpGet.setHeader(HttpHeaders.AUTHORIZATION, buildBasicAuthHeaderValue());
        try {
            return buildHttpClient().execute(httpGet, new ArtlabResponseHandler<>(resultClazz));
        } catch (ClientProtocolException e) {
            throw this.handle(e);
        }
    }

    public <T> T post(MultiValuedMap<String, String> queryParams, String path, Object body, Class<T> resultClazz) throws ArtlabClientException, IOException {
        HttpPost httpPost = new HttpPost(buildApiURL(queryParams, path));
        httpPost.setHeader(HttpHeaders.AUTHORIZATION, buildBasicAuthHeaderValue());
        if (body != null) {
            httpPost.setEntity(new StringEntity(JSONObject.toJSONString(body)));
        }
        try {
            return buildHttpClient().execute(httpPost, new ArtlabResponseHandler<>(resultClazz));
        } catch (ClientProtocolException e) {
            throw this.handle(e);
        }
    }

    public <T> List<T> getList(MultiValuedMap<String, String> queryParams, String path, Class<T> resultClazz) throws ArtlabClientException, IOException {
        HttpGet httpGet = new HttpGet(buildApiURL(queryParams, path));
        httpGet.setHeader(HttpHeaders.AUTHORIZATION, buildBasicAuthHeaderValue());
        try {
            return buildHttpClient().execute(httpGet, new ArtlabListResponseHandler<>(resultClazz));
        } catch (ClientProtocolException e) {
            throw this.handle(e);
        }
    }

    public InputStream getStream(MultiValuedMap<String, String> queryParams, String path) throws IOException, ArtlabClientException {
        HttpGet httpGet = new HttpGet(buildApiURL(queryParams, path));
        httpGet.setHeader(HttpHeaders.AUTHORIZATION, buildBasicAuthHeaderValue());
        try {
            CloseableHttpResponse response = buildHttpClient().execute(httpGet);
            return handleStreamResponse(response);
        } catch (ClientProtocolException e) {
            throw this.handle(e);
        }
    }

    public <T> T upload(File file, MultiValuedMap<String, String> queryParams, String path, Class<T> resultClazz) throws IOException, ArtlabClientException {
        HttpPost post = new HttpPost(buildApiURL(queryParams, path));
        post.setHeader(HttpHeaders.AUTHORIZATION, buildBasicAuthHeaderValue());
        HttpEntity entity = MultipartEntityBuilder.create()
                .setMode(HttpMultipartMode.BROWSER_COMPATIBLE)
                .addBinaryBody("file", file, ContentType.DEFAULT_BINARY, file.getName())
                .build();
        post.setEntity(entity);
        try {
            return buildHttpClient().execute(post, new ArtlabResponseHandler<>(resultClazz));
        } catch (ClientProtocolException e) {
            throw this.handle(e);
        }
    }

    private ArtlabClientException handle(Exception e) {
        if (e.getCause() instanceof ArtlabClientException) {
            return (ArtlabClientException) e.getCause();
        }
        return new ArtlabClientException(ERROR_CODE_UNKNOWN, e.getMessage());
    }

    private CloseableHttpClient buildHttpClient() {
        if (connManager == null) {
            synchronized (this) {
                connManager = new PoolingHttpClientConnectionManager();
                connManager.setMaxTotal(100);
                connManager.setDefaultMaxPerRoute(20);
            }
        }
        // Config the timeout
        RequestConfig reqConfig = RequestConfig.custom()
                .setConnectTimeout(clientConfiguration.getConnectTimeoutInSecond() * 1000)
                .setConnectionRequestTimeout(clientConfiguration.getConnectTimeoutInSecond() * 1000)
                .setSocketTimeout(clientConfiguration.getReadTimeoutInSecond() * 1000)
                .build();

        return HttpClients.custom()
                .setConnectionManager(connManager)
                .setDefaultRequestConfig(reqConfig)
                .build();
    }

    private String buildBasicAuthHeaderValue() {
        String auth = this.username + ":" + this.password;
        byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(StandardCharsets.ISO_8859_1));
        return "Basic " + new String(encodedAuth);
    }

    private URI buildApiURL(MultiValuedMap<String, String> params, String path) throws IOException {
        try {
            URIBuilder uriBuilder = new URIBuilder(this.serverUrl + "/" + path);
            if (params != null && !params.isEmpty()) {
                for (Map.Entry<String, String> entry : params.entries()) {
                    uriBuilder.setParameter(entry.getKey(), entry.getValue());
                }
            }
            return uriBuilder.build();
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("illegal server url " + serverUrl, e);
        }
    }

    private InputStream handleStreamResponse(HttpResponse response) throws IOException {
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode >= 400) {
            throw new ClientProtocolException("request failed, status code: " + statusCode);
        }

        HttpEntity entity = response.getEntity();
        ContentType contentType = ContentType.get(entity);
        if (StringUtils.equals(contentType.getMimeType(), ContentType.APPLICATION_JSON.getMimeType())) {
            // Try to parse the returned content
            String content = EntityUtils.toString(entity);
            try {
                ArtlabResponse<Object> artlabResponse = JSON.parseObject(content, new TypeReference<ArtlabResponse<Object>>() {
                });
                if (!artlabResponse.isSuccessful() && StringUtils.isNotBlank(artlabResponse.getErrorMsg())) {
                    // Something wrong when get the stream
                    ArtlabClientException artlabClientException = new ArtlabClientException(artlabResponse.getErrorMsg(), artlabResponse.getErrorCode());
                    throw new ClientProtocolException(artlabClientException.getMessage(), artlabClientException);
                }
            } catch (JSONException ignore) {
                // illegal content
            }
            return new ByteArrayInputStream(content.getBytes());
        }
        return entity.getContent();
    }

    private static class ArtlabListResponseHandler<T> implements ResponseHandler<List<T>> {
        private final Class<T> responseClazz;

        private ArtlabListResponseHandler(Class<T> responseClazz) {
            this.responseClazz = responseClazz;
        }

        @Override
        public List<T> handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode >= 200 && statusCode < 400) {
                HttpEntity entity = response.getEntity();
                String content = EntityUtils.toString(entity);
                ArtlabListResponse<T> artlabListResponse = deserializeListResponse(content);

                if (!artlabListResponse.isSuccessful()) {
                    ArtlabClientException artlabClientException = new ArtlabClientException(
                            artlabListResponse.getErrorMsg(), artlabListResponse.getErrorCode());
                    throw new ClientProtocolException(artlabClientException);
                }
                return artlabListResponse.getObject().getDataList();
            } else {
                throw new ClientProtocolException("request failed, status code: " + statusCode);
            }
        }

        private ArtlabListResponse<T> deserializeListResponse(String content) throws JSONException {
            return JSON.parseObject(content, new TypeReference<ArtlabListResponse<T>>(responseClazz) {
            });
        }
    }

    private static class ArtlabResponseHandler<T> implements ResponseHandler<T> {
        private final Class<T> responseClazz;

        private ArtlabResponseHandler(Class<T> responseClazz) {
            this.responseClazz = responseClazz;
        }

        @Override
        public T handleResponse(HttpResponse response) throws IOException {
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode >= 200 && statusCode < 400) {
                HttpEntity entity = response.getEntity();
                String content = EntityUtils.toString(entity);
                ArtlabResponse<T> artlabResponse = deserializeResponse(content);

                if (!artlabResponse.isSuccessful()) {
                    ArtlabClientException artlabClientException = new ArtlabClientException(
                            artlabResponse.getErrorMsg(), artlabResponse.getErrorCode());
                    throw new ClientProtocolException(artlabClientException);
                }
                return artlabResponse.getObject();
            } else {
                throw new ClientProtocolException("request failed, status code: " + statusCode);
            }
        }

        private ArtlabResponse<T> deserializeResponse(String content) throws JSONException {
            return JSON.parseObject(content, new TypeReference<ArtlabResponse<T>>(responseClazz) {
            });
        }
    }

    @Override
    public void close() throws Exception {
        connManager.close();
    }
}
