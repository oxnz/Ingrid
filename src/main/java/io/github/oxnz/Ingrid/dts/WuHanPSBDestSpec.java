package io.github.oxnz.Ingrid.dts;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.oxnz.Ingrid.dts.mq.TxEvent;
import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.net.URI;

@Component
@Region(state = "CA", city = "SF")
public class WuHanPSBDestSpec implements DestSpec {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private final URI CHECKIN_URI = URI.create("http://localhost:8000/echo");

    @Override
    public ResponseHandler<? extends HttpExecutionResult> responseHandler() {
        return (ResponseHandler<HttpExecutionResult>) response -> {
            log.debug("resp: {}", response);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if (statusCode != HttpStatus.SC_OK)
                throw new ClientProtocolException("unexpected status code: " + statusCode);
            HttpEntity entity = response.getEntity();
            if (entity == null)
                throw new RuntimeException("no entity");
            return new HttpExecutionResult(true, "succ");
        };
    }

    @Override
    public RequestBuilder requestBuilder() {
        return (record, destSpec) -> {
            HttpPost request = new HttpPost(CHECKIN_URI);
            try {
                HttpEntity entity = new StringEntity(record.toString());
                request.addHeader(HttpHeaders.CONTENT_TYPE, "text/plain");
                request.setEntity(entity);
                log.debug("req: {}, entity: {}", request, entity);
                return request;
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        };
    }
}
