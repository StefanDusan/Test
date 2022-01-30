package com.intpfyqa.utils;

import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * An item in file storage
 */
public class FileStorageItem {

    private final File realFile;
    private final String referenceName;

    FileStorageItem(File realFile, String referenceName) {
        this.realFile = realFile;
        this.referenceName = referenceName;
    }

    public static String createRelativePath(String filePath, String relativeTo) {
        Path pathAbsolute = Paths.get(new File(filePath).getAbsolutePath());
        Path pathBase = Paths.get(new File(relativeTo).getAbsolutePath());
        Path pathRelative = pathBase.relativize(pathAbsolute);

        return pathRelative.toString();
    }

    /**
     * Reference to the file in filesystem
     *
     * @return Reference to the file in filesystem
     */
    public File getRealFile() {
        return realFile;
    }

    /**
     * Name which was used during adding this file to storage (may not be same as file name)
     *
     * @return Name which was used during adding this file to storage
     */
    public String getOriginalName() {
        return referenceName;
    }

    /**
     * Construct path to this real file (on filesystem) relatively to another one
     * @param anotherDir Path relatively to which need to create result path
     * @return Relative path if possible
     */
    public String pathRelativeTo(File anotherDir) {

        Path pathAbsolute = Paths.get(realFile.getAbsolutePath());
        Path pathBase = Paths.get(anotherDir.getAbsolutePath());
        Path pathRelative = pathBase.relativize(pathAbsolute);

        return pathRelative.toString();
    }

    /**
     * Construct path to this real file (on filesystem) relatively to another one
     * @param dirOfAnotherItem Path relatively to which need to create result path
     * @return Relative path if possible
     */
    public String pathRelativeTo(FileStorageItem dirOfAnotherItem) {
        return pathRelativeTo(dirOfAnotherItem.realFile.getParentFile());
    }

    /**
     * Extension of this real file
     * @return Extension of this real file
     */
    public String getFileExtension() {
        return FilenameUtils.getExtension(getOriginalName());
    }


    public String toString() {
        return String.format("%s (%s)", referenceName, pathRelativeTo(FileStorage.getRootDir()));
    }
}
