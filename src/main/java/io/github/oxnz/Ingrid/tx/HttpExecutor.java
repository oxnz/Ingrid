package io.github.oxnz.Ingrid.tx;

import javax.net.ssl.*;
import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class HttpExecutor {
    private final HttpClient httpClient;

    @FunctionalInterface
    public interface ResponseHandler<T> {
        T handleResponse(HttpResponse<String> response);
    }

    public HttpExecutor(HostnameVerifier hostnameVerifier, SSLContext ctx, Duration connectTimeout, Executor executor) {
        this(HttpClient.newBuilder()
                .connectTimeout(connectTimeout)
                .executor(executor)
                .sslContext(ctx)
                .build());
        HttpsURLConnection.setDefaultSSLSocketFactory(ctx.getSocketFactory());
        HttpsURLConnection.setDefaultHostnameVerifier(hostnameVerifier);
    }

    public HttpExecutor(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public <T> T execute(HttpRequest request, ResponseHandler<T> responseHandler) throws IOException, InterruptedException {
        return responseHandler.handleResponse(httpClient.send(request, HttpResponse.BodyHandlers.ofString()));
    }

    public <T> CompletableFuture<T> submit(HttpRequest request, ResponseHandler<T> responseHandler) {
        return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString()).thenApply(responseHandler::handleResponse);
    }
}
