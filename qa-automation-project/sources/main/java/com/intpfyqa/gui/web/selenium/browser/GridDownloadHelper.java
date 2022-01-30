package com.intpfyqa.gui.web.selenium.browser;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.intpfyqa.gui.web.selenium.SeleniumDownloadHelper;
import com.intpfyqa.logging.LogManager;
import com.intpfyqa.utils.FileStorage;
import com.intpfyqa.utils.FileStorageItem;
import com.intpfyqa.utils.TestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.SessionId;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

public class GridDownloadHelper implements SeleniumDownloadHelper {

    private final String gridUrl;
    private List<String> filesBeforeDownload = new ArrayList<>();
    private File downloadDir;

    public GridDownloadHelper(String gridUrl) {
        this.gridUrl = gridUrl;
    }

    private WebDriver driver;

    public void setDriver(WebDriver driver) {
        this.driver = driver;
    }


    @Override
    public void lockDownloadDir() {
        filesBeforeDownload = Arrays.asList(listFiles());
    }

    @Override
    public FileStorageItem waitForNewFileDownloaded(Duration timeout) {

        LocalDateTime endTime = LocalDateTime.now().plus(timeout);
        String[] lastCollectedFiles = new String[]{};
        do {
            try {
                lastCollectedFiles = listFiles();
                Optional<String> newFile = Arrays.stream(lastCollectedFiles).filter(f ->
                        !filesBeforeDownload.contains(f) && !ArrayUtils.contains(extensionsDownloadIsInProgress,
                                FilenameUtils.getExtension(f))).findFirst();
                if (newFile.isPresent()) {
                    FileStorageItem item = downloadFile(newFile.get());
                    LogManager.getCurrentTestLogger().debug("Files downloading", "File downloaded: " + item);
                    return item;
                }
            } catch (Throwable ignore) {

            }
            TestUtils.sleep(500);
        } while (LocalDateTime.now().isBefore(endTime));

        LogManager.getCurrentTestLogger().debug("Files downloading",
                "New files not found in dir " + getDownloadDir() + ". All files there\n" +
                        StringUtils.join(lastCollectedFiles, "\n"));

        return null;


    }

    private String[] listFiles() {
        URL externalUrl = currentGridNodeLocation((RemoteWebDriver) driver);
        try {
            externalUrl = new URL(externalUrl.getProtocol(), externalUrl.getHost(), externalUrl.getPort(),
                    "/extra/ListFilesServlet/" + getDownloadDir());
            StringBuffer response = new StringBuffer();

            try (InputStream is = externalUrl.openConnection().getInputStream()) {
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);

                String line;
                while ((line = br.readLine()) != null) {
                    response.append(line);
                }
                br.close();
            } catch (IOException e) {
                if (e.getMessage().toLowerCase().contains("response code: 400")) {
                    return new String[]{};
                }
                throw e;
            }

            JsonElement responseElement = new JsonParser().parse(response.toString());
            JsonArray filesArray = responseElement.getAsJsonArray();
            String[] outArray = new String[filesArray.size()];
            for (int i = 0; i < filesArray.size(); i++) {
                outArray[i] = filesArray.get(i).getAsString();
            }
            return outArray;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private FileStorageItem downloadFile(String name) {
        URL externalUrl = currentGridNodeLocation((RemoteWebDriver) driver);
        try {
            externalUrl = new URL(externalUrl.getProtocol(), externalUrl.getHost(), externalUrl.getPort(),
                    "/extra/DownloadFileServlet/" + getDownloadDir() + "\\" + name);
            FileStorageItem item = FileStorage.createNewArtifactFile(FilenameUtils.getName(name));
            FileUtils.copyURLToFile(externalUrl, item.getRealFile());
            return item;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public File getDownloadDir() {
        if (null == downloadDir)
            downloadDir = new File("C:\\downloads\\" + UUID.randomUUID());
        return downloadDir;
    }

    private URL currentGridNodeLocation(RemoteWebDriver remoteWebDriver) {
        try {
            URL urlWithPrivateIp = currentGridNodeLocation(remoteWebDriver.getSessionId());
            return new URL(urlWithPrivateIp.getProtocol(), urlWithPrivateIp.getHost(),
                    urlWithPrivateIp.getPort(), urlWithPrivateIp.getFile());
        } catch (MalformedURLException e) {
            throw new RuntimeException("Can not create correct selenium grid hub URL.");
        } catch (IOException e) {
            throw new RuntimeException("IOException has been thrown while getting data from Selenium Hub.");
        }
    }

    public URL currentGridNodeLocation(SessionId session) throws IOException {
        String hubLocation = gridUrl.replace("/wd/hub", "");
        URL url = new URL(hubLocation + "/grid/api/testsession?session=" + session);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        InputStream is = conn.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        StringBuffer response = new StringBuffer();

        String line;
        while ((line = br.readLine()) != null) {
            response.append(line);
        }
        br.close();
        JsonElement jsonResponse = new JsonParser().parse(response.toString());
        String urlAsString = jsonResponse.getAsJsonObject().get("proxyId").getAsString();
        if (urlAsString != null) {
            return new URL(jsonResponse.getAsJsonObject().get("proxyId").getAsString());
        } else {
            throw new IllegalStateException("It is not possible to extract instance private ip from hub server.");
        }
    }
}
