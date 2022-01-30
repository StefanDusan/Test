package com.intpfyqa.test_rail.api;

import com.intpfyqa.http.ok.HttpHelper;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.auth.BasicScheme;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;


public class APIClient {
    private String m_user;
    private String m_password;
    private String m_url;

    public APIClient(String base_url) {
        if (!base_url.endsWith("/")) {
            base_url += "/";
        }

        this.m_url = base_url + "index.php?/api/v2/";
    }

    public String getUser() {
        return this.m_user;
    }

    public void setUser(String user) {
        this.m_user = user;
    }

    public String getPassword() {
        return this.m_password;
    }

    public void setPassword(String password) {
        this.m_password = password;
    }

    public Object sendGet(String uri)
            throws IOException, APIException {
        return this.sendRequest("GET", uri, null);
    }

    public Object sendPost(String uri, Object data)
            throws IOException, APIException {
        return this.sendRequest("POST", uri, data);
    }

    private Object sendRequest(String method, String uri, Object data)
            throws IOException, APIException {
        URL url = new URL(this.m_url + uri);

        // Create the connection object and set the required HTTP method
        // (GET/POST) and headers (content type and basic auth).
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.addRequestProperty("Content-Type", "application/json");

        String auth = getAuthorization(this.m_user, this.m_password);
        conn.addRequestProperty("Authorization", "Basic " + auth);

        if (method == "POST") {
            // Add the POST arguments, if any. We just serialize the passed
            // data object (i.e. a dictionary) and then add it to the
            // request body.
            if (data != null) {
                byte[] block = JSONValue.toJSONString(data).
                        getBytes(StandardCharsets.UTF_8);

                conn.setDoOutput(true);
                OutputStream ostream = conn.getOutputStream();
                ostream.write(block);
                ostream.flush();
            }
        }

        // Execute the actual web request (if it wasn't already initiated
        // by getOutputStream above) and record any occurred errors (we use
        // the error stream in this case).
        int status = conn.getResponseCode();

        InputStream istream;
        if (status != 200) {
            istream = conn.getErrorStream();
            if (istream == null) {
                throw new APIException(
                        "TestRail API return HTTP " + status +
                                " (No additional error message received)"
                );
            }
        } else {
            istream = conn.getInputStream();
        }

        // Read the response body, if any, and deserialize it from JSON.
        String text = "";
        if (istream != null) {
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(
                            istream,
                            StandardCharsets.UTF_8
                    )
            );

            String line;
            while ((line = reader.readLine()) != null) {
                text += line;
                text += System.getProperty("line.separator");
            }

            reader.close();
        }

        Object result;
        if (text != "") {
            result = JSONValue.parse(text);
        } else {
            result = new JSONObject();
        }

        // Check for any occurred errors and add additional details to
        // the exception message, if any (e.g. the error message returned
        // by TestRail).
        if (status != 200) {
            String error = "No additional error message received";
            if (result != null && result instanceof JSONObject) {
                JSONObject obj = (JSONObject) result;
                if (obj.containsKey("error")) {
                    error = '"' + (String) obj.get("error") + '"';
                }
            }

            throw new APIException(
                    "TestRail API returned HTTP " + status +
                            "(" + error + ")"
            );
        }

        return result;
    }

    private static String getAuthorization(String user, String password) {
        try {
            return new String(Base64.getEncoder().encode((user + ":" + password).getBytes("UTF-8")));
        } catch (UnsupportedEncodingException e) {
            // Not thrown
        }

        return "";
    }

    public boolean uploadAttachment(String uri, String base64File) throws IOException, URISyntaxException {
        HttpEntityEnclosingRequestBase requestBase = new HttpPost();
        HttpEntity entity = MultipartEntityBuilder
                .create()
                .setMode(HttpMultipartMode.BROWSER_COMPATIBLE)
                .addBinaryBody("attachment", Base64.getDecoder().decode(base64File),
                        ContentType.MULTIPART_FORM_DATA, String.format("screenshot%s.png", RandomStringUtils.randomNumeric(8)))
                .build();
        requestBase.setEntity(entity);

        requestBase.addHeader(BasicScheme.authenticate(
                new UsernamePasswordCredentials(this.m_user, this.m_password),
                "UTF-8", false));

        requestBase.setURI(new URI(this.m_url + uri));
        CloseableHttpResponse response = HttpHelper.getClient().execute(requestBase);
        boolean success = response.getStatusLine().getStatusCode() == 200;
        response.close();
        return success;
    }
}
