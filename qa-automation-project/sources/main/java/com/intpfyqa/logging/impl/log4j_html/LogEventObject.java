package com.intpfyqa.logging.impl.log4j_html;

import com.intpfyqa.logging.ITestLogger;

public class LogEventObject {

    private ITestLogger.Level level;
    private String message;
    private String header;
    private String snapshotFilePath;
    private boolean snapshotIsImage;

    public boolean isSnapshotIsImage() {
        return snapshotIsImage;
    }

    public void setSnapshotIsImage(boolean snapshotIsImage) {
        this.snapshotIsImage = snapshotIsImage;
    }

    public ITestLogger.Level getLevel() {
        return level;
    }

    public void setLevel(ITestLogger.Level level) {
        this.level = level;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getSnapshotFilePath() {
        return snapshotFilePath;
    }

    public void setSnapshotFilePath(String snapshot) {
        this.snapshotFilePath = snapshot;
    }

    public boolean hasSnapshot() {
        return getSnapshotFilePath() != null;
    }
}
