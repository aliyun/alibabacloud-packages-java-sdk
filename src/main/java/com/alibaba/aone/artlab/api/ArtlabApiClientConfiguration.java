package com.alibaba.aone.artlab.api;

/**
 * The configuration properties of Artlab http client, such as timeout span, connection pool size, etc.
 */
public class ArtlabApiClientConfiguration {
    public static final ArtlabApiClientConfiguration DEFAULT = new ArtlabApiClientConfiguration();

    public static final int DEFAULT_CONNECT_TIMEOUT_IN_SECOND = 10; // 15 seconds
    public static final int DEFAULT_READ_TIMEOUT_IN_SECOND = 50; // 50 seconds
    public static final int DEFAULT_CONNECTION_POOL_SIZE = 100; // 100 seconds

    private int connectTimeoutInSecond = DEFAULT_CONNECT_TIMEOUT_IN_SECOND;
    private int readTimeoutInSecond = DEFAULT_READ_TIMEOUT_IN_SECOND;
    private int connectionPoolSize = DEFAULT_CONNECTION_POOL_SIZE;

    private ArtlabApiClientConfiguration() {
    }

    public int getConnectTimeoutInSecond() {
        return connectTimeoutInSecond;
    }

    public void setConnectTimeoutInSecond(int connectTimeoutInSecond) {
        this.connectTimeoutInSecond = connectTimeoutInSecond;
    }

    public ArtlabApiClientConfiguration withConnectionTimeoutInSecond(int connectTimeoutInSecond) {
        this.connectTimeoutInSecond = connectTimeoutInSecond;
        return this;
    }

    public int getReadTimeoutInSecond() {
        return readTimeoutInSecond;
    }

    public void setReadTimeoutInSecond(int readTimeoutInSecond) {
        this.readTimeoutInSecond = readTimeoutInSecond;
    }

    public ArtlabApiClientConfiguration withReadTimeoutInSecond(int readTimeoutInSecond) {
        this.readTimeoutInSecond = readTimeoutInSecond;
        return this;
    }

    public int getConnectionPoolSize() {
        return connectionPoolSize;
    }

    public void setConnectionPoolSize(int connectionPoolSize) {
        this.connectionPoolSize = connectionPoolSize;
    }

    public ArtlabApiClientConfiguration withConnectionPoolSize(int connectionPoolSize) {
        this.connectionPoolSize = connectionPoolSize;
        return this;
    }
}
