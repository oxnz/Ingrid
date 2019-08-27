package io.github.oxnz.Ingrid.tx;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.net.URI;

@Component
@Region(state = "CA", city = "SF")
public class TestDestSpec implements DestSpec {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private final URI CHECKIN_URI = URI.create("http://localhost:8000/echo");

    @Override
    public boolean isInterested(TxCategory cat) {
        return true;
    }

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
    public TxRequestBuilder requestBuilder() {
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
