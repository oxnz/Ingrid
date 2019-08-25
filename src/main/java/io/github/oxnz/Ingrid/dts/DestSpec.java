package io.github.oxnz.Ingrid.dts;

public class DestSpec {
    public RequestBuilder getRequestBuilder() {
        return requestBuilder;
    }

    final RequestBuilder requestBuilder;

    public DestSpec(RequestBuilder requestBuilder) {
        this.requestBuilder = requestBuilder;
    }
}
