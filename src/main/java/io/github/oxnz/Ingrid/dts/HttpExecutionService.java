package io.github.oxnz.Ingrid.dts;

import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.ssl.DefaultHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.FutureRequestExecutionService;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.HttpRequestFutureTask;
import org.apache.http.protocol.HttpContext;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.stereotype.Service;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class HttpExecutionService implements AutoCloseable {

    private final FutureRequestExecutionService executionService;
    private final AtomicBoolean closed = new AtomicBoolean(false);

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
            CloseableHttpClient httpClient = HttpClients.custom()
                    .setDefaultRequestConfig(requestConfig)
                    .setMaxConnTotal(Defaults.MAX_CONN_TOTAL)
                    .setMaxConnPerRoute(Defaults.MAX_CONN_PER_ROUTE)
                    .setKeepAliveStrategy((response, context) -> Defaults.KEEPALIVE_TIME)
                    .setSSLSocketFactory(sslConnectionSocketFactory)
                    .build();
            ExecutorService executor = Executors.newFixedThreadPool(Defaults.WORKER_COUNT);
            executionService = new FutureRequestExecutionService(httpClient, executor);
        } catch (NoSuchAlgorithmException | KeyStoreException | KeyManagementException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> HttpRequestFutureTask<T> submit(final HttpUriRequest request,
                                               final HttpContext context,
                                               final ResponseHandler<T> responseHandler) {
        return executionService.execute(request, context, responseHandler);
    }

    public <T> T execute(final HttpUriRequest request,
                         final HttpContext context,
                         final ResponseHandler<T> responseHandler) throws ExecutionException, InterruptedException {
        HttpRequestFutureTask<T> futureTask = submit(request, context, responseHandler);
        T response = futureTask.get();
        return response;
    }

    @Override
    public void close() throws IOException {
        if (! closed.get()) executionService.close();
    }

    private interface Defaults {
        int TIMEOUT = 10 * 1000;
        int MAX_CONN_TOTAL = 200;
        int MAX_CONN_PER_ROUTE = 5;
        int KEEPALIVE_TIME = 30 * 1000;
        int WORKER_COUNT = 5; // assert(WORKER_COUNT <= MAX_CONN_PER_ROUTE)
        HttpContext HTTP_CONTEXT = HttpClientContext.create();
    }
}

