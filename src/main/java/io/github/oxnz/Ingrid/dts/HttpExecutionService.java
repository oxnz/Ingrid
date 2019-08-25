package io.github.oxnz.Ingrid.dts;

import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ssl.DefaultHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

public class HttpExecutionService implements AutoCloseable {

    private final CloseableHttpClient httpClient;

    public HttpExecutionService() {
        this(new TrustSelfSignedStrategy(), new DefaultHostnameVerifier());
    }

    public HttpExecutionService(TrustStrategy trustStrategy, HostnameVerifier hostnameVerifier) {
        try {
            SSLContext sslcontext = SSLContextBuilder.create().loadTrustMaterial(trustStrategy).build();
            SSLConnectionSocketFactory sslConnectionSocketFactory =
                    new SSLConnectionSocketFactory(sslcontext, hostnameVerifier);
            RequestConfig requestConfig =
                    RequestConfig.custom()
                            .setConnectTimeout(Defaults.TIMEOUT)
                            .setConnectionRequestTimeout(Defaults.TIMEOUT)
                            .setSocketTimeout(Defaults.TIMEOUT)
                            .build();
            httpClient =
                    HttpClients.custom()
                            .setDefaultRequestConfig(requestConfig)
                            .setMaxConnTotal(Defaults.MAX_CONN_TOTAL)
                            .setMaxConnPerRoute(Defaults.MAX_CONN_PER_ROUTE)
                            .setKeepAliveStrategy((response, context) -> Defaults.KEEPALIVE_TIME)
                            .setSSLSocketFactory(sslConnectionSocketFactory)
                            .build();
        } catch (NoSuchAlgorithmException | KeyStoreException | KeyManagementException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> T execute(HttpUriRequest request, ResponseHandler<? extends T> responseHandler)
            throws IOException {
        return httpClient.execute(request, responseHandler);
    }

    @Override
    public void close() throws IOException {
        httpClient.close();
    }

    private interface Defaults {
        int TIMEOUT = 10 * 1000;
        int MAX_CONN_TOTAL = 200;
        int MAX_CONN_PER_ROUTE = 20;
        int KEEPALIVE_TIME = 30 * 1000;
    }
}

