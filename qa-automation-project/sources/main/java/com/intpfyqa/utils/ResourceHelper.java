package com.intpfyqa.utils;

import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class ResourceHelper {

    public static File createFileFromResource(String uri) {
        try (InputStream stream = ResourceHelper.class.getResourceAsStream(uri)) {
            return FileStorage.putFile(stream,
                    FilenameUtils.getBaseName(uri) + "." + FilenameUtils.getExtension(uri), "")
                    .getRealFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
