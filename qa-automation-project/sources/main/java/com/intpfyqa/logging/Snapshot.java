package com.intpfyqa.logging;

import com.intpfyqa.utils.FileStorageItem;

public interface Snapshot {

    FileStorageItem saveToStorage();

    FileStorageItem getSavedFile();

    boolean isImage();

    byte[] getBytes();
}
