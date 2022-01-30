package com.intpfyqa.logging.impl;

import com.intpfyqa.logging.Snapshot;
import com.intpfyqa.utils.FileStorage;
import com.intpfyqa.utils.FileStorageItem;

import java.io.ByteArrayInputStream;

public class ImageSnapshot implements Snapshot {

    private final String imageBase64;
    private FileStorageItem savedFile = null;

    public ImageSnapshot(String imageBase64) {
        this.imageBase64 = imageBase64;
    }

    @Override
    public FileStorageItem saveToStorage() {
        savedFile = FileStorage.putSnapshotFile(new ByteArrayInputStream(getBytes()), "screenshot.png");
        return savedFile;
    }

    @Override
    public FileStorageItem getSavedFile() {
        return savedFile;
    }

    @Override
    public boolean isImage() {
        return true;
    }

    @Override
    public byte[] getBytes() {
        byte[] decodedString;
        try {
            decodedString = java.util.Base64.getDecoder().decode(imageBase64);
        } catch (Exception ignore) {
            decodedString = java.util.Base64.getMimeDecoder().decode(imageBase64);
        }

        return decodedString;
    }
}
