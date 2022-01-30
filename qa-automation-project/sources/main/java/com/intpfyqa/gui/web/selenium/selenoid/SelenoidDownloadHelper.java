package com.intpfyqa.gui.web.selenium.selenoid;

import com.intpfyqa.http.ok.HttpHelper;
import com.intpfyqa.logging.LogManager;
import com.intpfyqa.utils.FileStorage;
import com.intpfyqa.utils.FileStorageItem;
import com.intpfyqa.utils.TestUtils;
import com.intpfyqa.gui.web.selenium.SeleniumDownloadHelper;
import okhttp3.HttpUrl;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.ArrayUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class SelenoidDownloadHelper implements SeleniumDownloadHelper {

    private String driverSessionId;
    private boolean locked = false;

    public SelenoidDownloadHelper() {
    }

    public void setDriverSessionId(String driverSessionId) {
        this.driverSessionId = driverSessionId;
        locked = false;
    }

    private List<String> previousFiles = new ArrayList<>();

    @Override
    public void lockDownloadDir() {
        if (locked)
            throw new IllegalStateException("Can't lock download dir. It is locked already!");
        locked = true;
        previousFiles = listExistingFiles();
    }

    @Override
    public FileStorageItem waitForNewFileDownloaded(Duration timeout) {
        if (!locked) {
            throw new RuntimeException("Can't download a file. Download directory is not locked!");
        }
        LocalDateTime endTime = LocalDateTime.now().plus(timeout);

        List<String> newList = new ArrayList<>();

        do {
            try {
                newList = listExistingFiles();
                List<String> newFiles = new ArrayList<>(newList);
                newFiles.removeAll(previousFiles);
                newFiles = newFiles.stream().filter(f -> !ArrayUtils.contains(extensionsDownloadIsInProgress,
                        FilenameUtils.getExtension(f))).collect(Collectors.toList());
                if (!newFiles.isEmpty()) {
                    locked = false;
                    FileStorageItem item = createDownloadedFile(newFiles.get(0));
                    LogManager.getCurrentTestLogger().debug("Files downloading", "File downloaded: " + item);
                    return item;
                }
            } catch (Exception ignore) {

            }
            TestUtils.sleep(500);
        } while (LocalDateTime.now().isBefore(endTime));

        locked = false;
        return null;
    }

    private FileStorageItem createDownloadedFile(String fileName) {

        try (InputStream is = HttpHelper.download(HttpUrl.get(getDownloadServiceUrl() + fileName),
                null)) {
            return FileStorage.putArtifactFile(is, fileName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public File getDownloadDir() {
        return null;
    }

    private String getDownloadServiceUrl() {
        return SelenoidHelper.getSelenoidServerUrl() + "/download/" + driverSessionId + "/";
    }

    private List<String> listExistingFiles() {
        String htmlString = HttpHelper.getStringResponse(HttpUrl.get(getDownloadServiceUrl()), null);
        Matcher m = Pattern.compile(">([^>]*)</a>", Pattern.DOTALL).matcher(htmlString);
        List<String> l = new ArrayList<>();
        while (m.find())
            l.add(m.group(1));
        return l;

    }


}
