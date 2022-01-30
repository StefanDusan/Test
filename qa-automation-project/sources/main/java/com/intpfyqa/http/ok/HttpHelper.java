package com.intpfyqa.http.ok;

import okhttp3.*;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class HttpHelper {

    private static PoolingHttpClientConnectionManager cm;
    private static CloseableHttpClient client = null;

    private static OkHttpClient.Builder createDefaultBuilder() {
        try {
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
            return new OkHttpClient.Builder()
                    .sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0])
                    .hostnameVerifier((s, session) -> true);
        } catch (Throwable t) {
            throw new RuntimeException("An error occurred while trying to create HTTP client",
                    t);
        }
    }

    public static String getStringResponse(HttpUrl fromUrl, Authenticator authenticator) {
        OkHttpClient.Builder builder = createDefaultBuilder();

        if (null != authenticator)
            builder.authenticator(authenticator);

        OkHttpClient client = builder.build();

        try {
            Request req = new Request.Builder().get().url(fromUrl).build();
            Response response = client.newCall(req).execute();
            String responseString = response.body().string();
            response.close();
            return responseString;
        } catch (Throwable t) {
            throw new RuntimeException("An error occurred while trying to get response by link " + fromUrl, t);
        }
    }

    static {
        client = HttpClients.custom().setConnectionManager(cm).build();
    }

    public static CloseableHttpClient getClient() {
        return client;
    }

    public static InputStream download(HttpUrl fromUrl, Authenticator authenticator) {

        OkHttpClient.Builder builder = createDefaultBuilder();

        if (null != authenticator)
            builder.authenticator(authenticator);

        OkHttpClient client = builder.build();

        try {
            Request req = new Request.Builder().get().url(fromUrl).build();
            Response response = client.newCall(req).execute();
            InputStream stream = new ByteArrayInputStream(response.body().bytes());
            response.close();
            return stream;
        } catch (Throwable t) {
            throw new RuntimeException("An error occurred while trying to get file by link " + fromUrl, t);
        }
    }
}
