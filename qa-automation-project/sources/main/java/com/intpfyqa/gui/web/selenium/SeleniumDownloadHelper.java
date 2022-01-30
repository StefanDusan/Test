package com.intpfyqa.gui.web.selenium;

import com.intpfyqa.utils.FileStorageItem;

import java.io.File;
import java.time.Duration;

/**
 * Provide downloaded  from browser files
 * <p>
 * Way of usage:<br/>
 * SeleniumDownloadHelper.lockDownloadDir();<br/>
 * //exec actions to init download in browser<br/>
 * SeleniumDownloadHelper.waitForNewFileDownloaded();<br/>
 * </p>
 */
public interface SeleniumDownloadHelper {

    /**
     * List of extensions determine that download is still in progress
     */
    String[] extensionsDownloadIsInProgress = new String[]{"crdownload", "tmp"};

    /**
     * Lock download dir - identify downloading is starting
     */
    void lockDownloadDir();

    /**
     * Wait until new file is downloaded into locked download directory<p>
     * Before starting download the download directory must be locked {@link #lockDownloadDir()}
     * </p>
     *
     * @param timeout Timeout of wait for new file
     * @return An downloaded file or <code>null</code> if no new files were downloaded during timeout
     * @see #lockDownloadDir()
     */
    FileStorageItem waitForNewFileDownloaded(Duration timeout);

    /**
     * Reference to download directory where files are being stored
     *
     * @return Directory where expect to see downloaded files
     */
    File getDownloadDir();

}
