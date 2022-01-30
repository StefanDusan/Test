package com.intpfyqa.gui.web.selenium.browser.launcher.local;

import com.intpfyqa.logging.LogManager;
import com.intpfyqa.utils.FileStorage;
import com.intpfyqa.utils.FileStorageItem;
import com.intpfyqa.utils.TestUtils;
import com.intpfyqa.gui.web.selenium.SeleniumDownloadHelper;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

public class LocalSeleniumDownloadHelper implements SeleniumDownloadHelper {

    private List<File> filesBeforeDownload = new ArrayList<>();

    private boolean locked = false;

    @Override
    public void lockDownloadDir() {
        lockDownloadDir(true);
    }

    @Override
    public FileStorageItem waitForNewFileDownloaded(Duration timeout) {
        if (!locked) throw new IllegalStateException("Download directory must be locked before get any file");
        LocalDateTime endTime = LocalDateTime.now().plus(timeout);
        File[] lastCollectedFiles = new File[]{};
        do {
            try {
                lastCollectedFiles = getDownloadDir().listFiles();
                Optional<File> newFile = Arrays.stream(lastCollectedFiles).filter(f ->
                        !filesBeforeDownload.contains(f) && !ArrayUtils.contains(extensionsDownloadIsInProgress,
                                FilenameUtils.getExtension(f.getName()))).findFirst();
                if (newFile.isPresent()) {
                    locked = false;
                    FileStorageItem item = FileStorage.putArtifactFile(newFile.get());
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
        locked = false;
        return null;
    }

    private void lockDownloadDir(boolean erase) {
        if (locked)
            throw new IllegalStateException("Can't lock download dir. It is locked already");
        if (erase) {
            Arrays.stream(Objects.requireNonNull(getDownloadDir().listFiles())).forEach(f -> f.delete());
        }
        filesBeforeDownload = Arrays.asList(Objects.requireNonNull(getDownloadDir().listFiles()));
        locked = true;
    }

    @Override
    public File getDownloadDir() {
        return downloadDir;
    }

    private final File downloadDir;

    private LocalSeleniumDownloadHelper(File downloadDir) {
        this.downloadDir = downloadDir;
    }

    public static LocalSeleniumDownloadHelper create(boolean isIE, File rootDir) {
        File targetDir;
        if (!isIE) {
            String tempName = UUID.randomUUID().toString();
            targetDir = new File(rootDir, tempName);
        } else {
            targetDir = rootDir;
        }
        targetDir.mkdirs();
        if (!targetDir.exists()) {
            throw new RuntimeException("Error creating a directory for downloading files " + targetDir + ". Access denied?");
        }
        return new LocalSeleniumDownloadHelper(targetDir);
    }
}
