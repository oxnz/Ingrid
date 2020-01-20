package io.github.oxnz.Ingrid.tx;

import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.junit.Before;
import org.junit.Test;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.time.Duration;
import java.util.Base64;
import java.util.Map;
import java.util.concurrent.Executors;

public class TxHttpExecutorTest {
    HttpExecutor httpExecutor;

    private Certificate certificate(String encodedCert) throws CertificateException {
        return
                CertificateFactory.getInstance("X509")
                        .generateCertificate(new ByteArrayInputStream(Base64.getDecoder().decode(encodedCert)));
    }

    @Before
    public void setup() throws CertificateException {
        String cert1 = "MIIJRTCCCC2gAwIBAgIQId276PtDYAkIAAAAAB2KRjANBgkqhkiG9w0BAQsFADBCMQswCQYDVQQGEwJVUzEeMBwGA1UEChMVR29vZ2xlIFRydXN0IFNlcnZpY2VzMRMwEQYDVQQDEwpHVFMgQ0EgMU8xMB4XDTE5MTEwNTA3NDYxNloXDTIwMDEyODA3NDYxNlowZjELMAkGA1UEBhMCVVMxEzARBgNVBAgTCkNhbGlmb3JuaWExFjAUBgNVBAcTDU1vdW50YWluIFZpZXcxEzARBgNVBAoTCkdvb2dsZSBMTEMxFTATBgNVBAMMDCouZ29vZ2xlLmNvbTBZMBMGByqGSM49AgEGCCqGSM49AwEHA0IABETwWPdIiJP2EXiE+YH4oKgNNT/vgfFFf6Ssm7+UgJf0qZ+oY63xqfhEbOLu0J0agXa5oLGYkMedgHhwAgs/F26jggbcMIIG2DAOBgNVHQ8BAf8EBAMCB4AwEwYDVR0lBAwwCgYIKwYBBQUHAwEwDAYDVR0TAQH/BAIwADAdBgNVHQ4EFgQU2VYFohFero6gqXjGUx+pPh8vq3AwHwYDVR0jBBgwFoAUmNH4bhDrz5vsYJ8YkBug630J/SswZAYIKwYBBQUHAQEEWDBWMCcGCCsGAQUFBzABhhtodHRwOi8vb2NzcC5wa2kuZ29vZy9ndHMxbzEwKwYIKwYBBQUHMAKGH2h0dHA6Ly9wa2kuZ29vZy9nc3IyL0dUUzFPMS5jcnQwggSdBgNVHREEggSUMIIEkIIMKi5nb29nbGUuY29tgg0qLmFuZHJvaWQuY29tghYqLmFwcGVuZ2luZS5nb29nbGUuY29tghIqLmNsb3VkLmdvb2dsZS5jb22CGCouY3Jvd2Rzb3VyY2UuZ29vZ2xlLmNvbYIGKi5nLmNvgg4qLmdjcC5ndnQyLmNvbYIRKi5nY3BjZG4uZ3Z0MS5jb22CCiouZ2dwaHQuY26CDiouZ2tlY25hcHBzLmNughYqLmdvb2dsZS1hbmFseXRpY3MuY29tggsqLmdvb2dsZS5jYYILKi5nb29nbGUuY2yCDiouZ29vZ2xlLmNvLmlugg4qLmdvb2dsZS5jby5qcIIOKi5nb29nbGUuY28udWuCDyouZ29vZ2xlLmNvbS5hcoIPKi5nb29nbGUuY29tLmF1gg8qLmdvb2dsZS5jb20uYnKCDyouZ29vZ2xlLmNvbS5jb4IPKi5nb29nbGUuY29tLm14gg8qLmdvb2dsZS5jb20udHKCDyouZ29vZ2xlLmNvbS52boILKi5nb29nbGUuZGWCCyouZ29vZ2xlLmVzggsqLmdvb2dsZS5mcoILKi5nb29nbGUuaHWCCyouZ29vZ2xlLml0ggsqLmdvb2dsZS5ubIILKi5nb29nbGUucGyCCyouZ29vZ2xlLnB0ghIqLmdvb2dsZWFkYXBpcy5jb22CDyouZ29vZ2xlYXBpcy5jboIRKi5nb29nbGVjbmFwcHMuY26CFCouZ29vZ2xlY29tbWVyY2UuY29tghEqLmdvb2dsZXZpZGVvLmNvbYIMKi5nc3RhdGljLmNugg0qLmdzdGF0aWMuY29tghIqLmdzdGF0aWNjbmFwcHMuY26CCiouZ3Z0MS5jb22CCiouZ3Z0Mi5jb22CFCoubWV0cmljLmdzdGF0aWMuY29tggwqLnVyY2hpbi5jb22CECoudXJsLmdvb2dsZS5jb22CEyoud2Vhci5na2VjbmFwcHMuY26CFioueW91dHViZS1ub2Nvb2tpZS5jb22CDSoueW91dHViZS5jb22CFioueW91dHViZWVkdWNhdGlvbi5jb22CESoueW91dHViZWtpZHMuY29tggcqLnl0LmJlggsqLnl0aW1nLmNvbYIaYW5kcm9pZC5jbGllbnRzLmdvb2dsZS5jb22CC2FuZHJvaWQuY29tghtkZXZlbG9wZXIuYW5kcm9pZC5nb29nbGUuY26CHGRldmVsb3BlcnMuYW5kcm9pZC5nb29nbGUuY26CBGcuY2+CCGdncGh0LmNuggxna2VjbmFwcHMuY26CBmdvby5nbIIUZ29vZ2xlLWFuYWx5dGljcy5jb22CCmdvb2dsZS5jb22CD2dvb2dsZWNuYXBwcy5jboISZ29vZ2xlY29tbWVyY2UuY29tghhzb3VyY2UuYW5kcm9pZC5nb29nbGUuY26CCnVyY2hpbi5jb22CCnd3dy5nb28uZ2yCCHlvdXR1LmJlggt5b3V0dWJlLmNvbYIUeW91dHViZWVkdWNhdGlvbi5jb22CD3lvdXR1YmVraWRzLmNvbYIFeXQuYmUwIQYDVR0gBBowGDAIBgZngQwBAgIwDAYKKwYBBAHWeQIFAzAvBgNVHR8EKDAmMCSgIqAghh5odHRwOi8vY3JsLnBraS5nb29nL0dUUzFPMS5jcmwwggEGBgorBgEEAdZ5AgQCBIH3BIH0APIAdwCyHgXMi6LNiiBOh2b5K7mKJSBna9r6cOeySVMt74uQXgAAAW46vlHbAAAEAwBIMEYCIQDpOV89u5EqOAN3utS9vvXK6b9qwnYgsiRvTDPzKj/RMgIhAJtf5vPM60HYJDIMIDreUJ9FJXN1gZ80iPCWa3XJfMv9AHcAXqdz+d9WwOe1Nkh90EngMnqRmgyEoRIShBh1loFxRVgAAAFuOr5SDgAABAMASDBGAiEAluuJTunQX+sOvRtgoGi5FWjLmSyfkR9FoxlTIwI9MPACIQDrvOpik5EGkKCHQYQzjHjEJs/6oMN8snfsFkaspC1pZzANBgkqhkiG9w0BAQsFAAOCAQEAS2pcPhrdskAIb3lACvzVmnxGb/dGBHZ9tIYe3a7UEmcVnl1mlNzWWLakbhXymqJO9XZdD++LVbrS/TTFXkTC8s+D+3xLsA31KcKaCRcs/k3iibnxo6DQMXvo52aadZh4NiocEabMUgzjZy5XPN6+YuC/5UtvRmC2hEOfxNkZK7WhhfJq+MoTuIV5g221GpWCprnFmMb9JCjZg3jR88kAoITUR7rJnKXQLOu7HInluBrcgg9m5/Aum9yfBV3TyeD/LWwi36qL9jh2h8L9+p0jLpQcYOD15YC+jtxzvBizqOnlri6cZB34sHSsW8ZSGFpLvazW8Amlsgtt121wNo1ehQ==";
        String cert2 = "MIIESjCCAzKgAwIBAgINAeO0mqGNiqmBJWlQuDANBgkqhkiG9w0BAQsFADBMMSAwHgYDVQQLExdHbG9iYWxTaWduIFJvb3QgQ0EgLSBSMjETMBEGA1UEChMKR2xvYmFsU2lnbjETMBEGA1UEAxMKR2xvYmFsU2lnbjAeFw0xNzA2MTUwMDAwNDJaFw0yMTEyMTUwMDAwNDJaMEIxCzAJBgNVBAYTAlVTMR4wHAYDVQQKExVHb29nbGUgVHJ1c3QgU2VydmljZXMxEzARBgNVBAMTCkdUUyBDQSAxTzEwggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAwggEKAoIBAQDQGM9F1IvN05zkQO9+tN1pIRvJzzyOTHW5DzEZhD2ePCnvUA0Qk28FgICfKqC9EksC4T2fWBYk/jCfC3R3VZMdS/dN4ZKCEPZRrAzDsiKUDzRrmBBJ5wudgzndIMYcLe/RGGFl5yODIKgjEv/SJH/UL+dEaltN11BmsK+eQmMF++AcxGNhr59qM/9il71I2dN8FGfcddwuaej4bXhp0LcQBbjxMcI7JP0aM3T4I+DsaxmKFsbjzaTNC9uzpFlgOIg7rR25xoynUxv8vNmkq7zdPGHXkxWY7oG9j+JkRyBABk7XrJfoucBZEqFJJSPk7XA0LKW0Y3z5oz2D0c1tJKwHAgMBAAGjggEzMIIBLzAOBgNVHQ8BAf8EBAMCAYYwHQYDVR0lBBYwFAYIKwYBBQUHAwEGCCsGAQUFBwMCMBIGA1UdEwEB/wQIMAYBAf8CAQAwHQYDVR0OBBYEFJjR+G4Q68+b7GCfGJAboOt9Cf0rMB8GA1UdIwQYMBaAFJviB1dnHB7AagbeWbSaLd/cGYYuMDUGCCsGAQUFBwEBBCkwJzAlBggrBgEFBQcwAYYZaHR0cDovL29jc3AucGtpLmdvb2cvZ3NyMjAyBgNVHR8EKzApMCegJaAjhiFodHRwOi8vY3JsLnBraS5nb29nL2dzcjIvZ3NyMi5jcmwwPwYDVR0gBDgwNjA0BgZngQwBAgIwKjAoBggrBgEFBQcCARYcaHR0cHM6Ly9wa2kuZ29vZy9yZXBvc2l0b3J5LzANBgkqhkiG9w0BAQsFAAOCAQEAGoA+Nnn78y6pRjd9XlQWNa7HTgiZ/r3RNGkmUmYHPQq6Scti9PEajvwRT2iWTHQr02fesqOqBY2ETUwgZQ+lltoNFvhsO9tvBCOIazpswWC9aJ9xju4tWDQH8NVU6YZZ/XteDSGU9YzJqPjY8q3MDxrzmqepBCf5o8mw/wJ4a2G6xzUr6Fb6T8McDO22PLRL6u3M4Tzs3A2M1j6bykJYi8wWIRdAvKLWZu/axBVbzYmqmwkm5zLSDW5nIAJbELCQCZwMH56t2Dvqofxs6BBcCFIZUSpxu6x6td0V7SvJCCosirSmIatj/9dSSVDQibet8q/7UK4v4ZUN80atnZz1yg==";
        SSLContext ctx = sslContext(Map.of("www.google.com", certificate(cert1),
                "intermediate", certificate(cert2)));
        httpExecutor = new HttpExecutor(
                NoopHostnameVerifier.INSTANCE,
                ctx,
                Duration.ofSeconds(1), Executors.newSingleThreadExecutor());
    }

    private static SSLContext sslContext(final Map<String, Certificate> certificates) {
        try {
            KeyStore ks = KeyStore.getInstance("PKCS12");
            char[] password = "password".toCharArray();
            ks.load(null, password);
            for (Map.Entry<String, Certificate> entry : certificates.entrySet()) {
                String key = entry.getKey();
                Certificate value = entry.getValue();
                ks.setCertificateEntry(key, value);
            }
            KeyManagerFactory kmf = KeyManagerFactory.getInstance("PKIX");
            kmf.init(ks, password);
            TrustManagerFactory tmf = TrustManagerFactory.getInstance("PKIX");
            tmf.init(ks);
            SSLContext ctx = SSLContext.getInstance("TLS");
            ctx.init(kmf.getKeyManagers(), tmf.getTrustManagers(), new SecureRandom());
            return ctx;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void submit() {
    }

    @Test
    public void execute() throws IOException, InterruptedException {
        URI uri = URI.create("https://www.google.com");
        HttpRequest request = HttpRequest.newBuilder(uri).build();
        String response = httpExecutor.execute(request, r -> {
            System.out.println(r);
            return "HELLO";
        });
        System.out.println(response);
    }
}