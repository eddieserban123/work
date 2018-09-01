package com.demo.fetcher.httpclient;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthSchemeProvider;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.AuthSchemes;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.auth.SPNegoSchemeFactory;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

public class KerberosHttpClient extends Client {
    private CloseableHttpClient httpClient;
    private static final Logger LOGGER = LoggerFactory.getLogger(KerberosHttpClient.class);

    public KerberosHttpClient() {
        try {
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, (chain, authType) -> true).build();
            HostnameVerifier hostnameVerifier = new NoopHostnameVerifier();
            Registry<AuthSchemeProvider> authSchemeRegistry = RegistryBuilder.<AuthSchemeProvider>create()
                    .register(AuthSchemes.SPNEGO, new SPNegoSchemeFactory())
                    .build();
            Credentials dummyCredentials = new NullCredentials();
            CredentialsProvider credProv = new BasicCredentialsProvider();
            credProv.setCredentials(AuthScope.ANY, dummyCredentials);
            this.httpClient = HttpClientBuilder.create()
                    .setDefaultAuthSchemeRegistry(authSchemeRegistry)
                    .setDefaultCredentialsProvider(credProv)
                    .setSSLContext(sslContext)
                    .setSSLHostnameVerifier(hostnameVerifier)
                    .build();
        } catch (NoSuchAlgorithmException | KeyStoreException | KeyManagementException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    protected Response executeGetRequestT(String url, Map<String, String> queryParams) throws IOException {
        String expUrl = encodeGetUrlWithParams(url, queryParams);
        HttpUriRequest request = new HttpGet(expUrl);
        HttpResponse response = httpClient.execute(request);
        HttpEntity entity = response.getEntity();
        String body = response.getEntity() != null ? EntityUtils.toString(entity) : null;
        EntityUtils.consumeQuietly(entity);
        LOGGER.debug("Request to {} returned status code {} and body {}",
                expUrl, response.getStatusLine().getStatusCode(), body);
        return new Response(response.getStatusLine().getStatusCode(), body);
    }

    @Override
    public Response executePostRequest(String url, HttpEntity httpEntity) throws IOException {
        HttpPost request = new HttpPost(url);
        request.setEntity(httpEntity);
        HttpResponse response = httpClient.execute(request);
        HttpEntity entity = response.getEntity();
        String body = response.getEntity() != null ? EntityUtils.toString(entity) : null;
        EntityUtils.consumeQuietly(entity);
        LOGGER.debug("Request to {} returned status code {} and body {}",
                url, response.getStatusLine().getStatusCode(), body);
        return new Response(response.getStatusLine().getStatusCode(), body);
    }

    @Override
    public void close() {
        if (httpClient != null) {
            try {
                httpClient.close();
            } catch (IOException e) {
                LOGGER.error("Exception while closing http client: {}-{}", e.getClass().getSimpleName(),
                        e.getMessage());
            }
        }
    }
}
