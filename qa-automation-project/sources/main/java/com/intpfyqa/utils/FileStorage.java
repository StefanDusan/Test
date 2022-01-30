package com.intpfyqa.utils;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;

import java.io.*;

/**
 * Work with file resources
 */
public class FileStorage {

    /**
     * Name of subfolder where snapshots should be stored
     */
    private static final String snapshotsSubDir = "snapshots";
    /**
     * Name of subfolder where artifacts should be stored
     */
    private static final String artifactsSubDir = "artifacts";

    /**
     * Add new file to storage of there no such file (with same name in same dir)
     *
     * @param inputStream Input stream to be stored to file
     * @param fileName    File name
     * @param subDir      Sub directory name where store the file if required
     * @return Create or existing storage item
     */
    public static FileStorageItem putFileIfNotExist(InputStream inputStream, String fileName, String subDir) {
        return _instance.putFileToStorage(inputStream, fileName, subDir, ConflictStrategy.SKIP);
    }

    private static final FileStorage _instance = new FileStorage();

    private final File rootDir;

    private FileStorage() {
        rootDir = new File(System.getProperty("project.build.outputDirectory", "."), "FileStorage");
        if (!rootDir.exists()) {
            if (!rootDir.mkdirs()) {
                throw new RuntimeException("Can't create root folder for file storage");
            }
        }
    }

    static File getRootDir() {
        return _instance.rootDir;
    }

    /**
     * Create new empty file in artifacts storage
     *
     * @param fileName File name to be created.
     * @return Storage item
     * @see #putFile(InputStream, String, String)
     */
    public static FileStorageItem createNewArtifactFile(String fileName) {
        return putFile(new ByteArrayInputStream(new byte[]{}), fileName, artifactsSubDir);
    }

    /**
     * Create new empty file or overwrite existing
     *
     * @param fileName File name to be created
     * @param subDir   Sub directory name to be created
     * @return Storage item
     */
    public static FileStorageItem createNewFileOrReplaceExisting(String fileName, String subDir) {
        return putFile(new ByteArrayInputStream(new byte[]{}), fileName, subDir, ConflictStrategy.OVERRIDE_EXISTING);
    }

    /**
     * Create new file
     *
     * @param inputStream Input stream to be stored to file
     * @param fileName    File name to be created
     * @param subDir      Sub directory name to be created
     * @param strategy    How to resolve case if such file already exists
     * @return Storage item
     */
    private static FileStorageItem putFile(InputStream inputStream, String fileName, String subDir, ConflictStrategy strategy) {
        return _instance.putFileToStorage(inputStream, fileName, subDir, strategy);
    }

    /**
     * Create new file. If file with such name already exists there new will be created with added index in name
     *
     * @param inputStream Input stream to be stored to file
     * @param fileName    File name to be created.
     * @param subDir      Sub directory name to be created
     * @return Storage item
     * @see #putFile(InputStream, String, String, ConflictStrategy)
     * @see ConflictStrategy#CREATE_NEW_INDEXED_NAME
     */
    public static FileStorageItem putFile(InputStream inputStream, String fileName, String subDir) {
        return putFile(inputStream, fileName, subDir, ConflictStrategy.CREATE_NEW_INDEXED_NAME);
    }

    /**
     * Copy file to storage. If file with such name already exists there new will be created with added index in name
     *
     * @param file   File to be copied
     * @param subDir Sub directory name to be created
     * @return Storage item
     * @see #putFile(InputStream, String, String, ConflictStrategy)
     * @see ConflictStrategy#CREATE_NEW_INDEXED_NAME
     */
    public static FileStorageItem putFile(File file, String subDir) {
        try {
            return _instance.putFileToStorage(new FileInputStream(file), file.getName(), subDir,
                    ConflictStrategy.CREATE_NEW_INDEXED_NAME);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Create new snapshot file. If file with such name already exists there new will be created with added index in name
     *
     * @param file     Input stream to be stored to file
     * @param fileName File name to be created.
     * @return Storage item
     * @see #putFile(InputStream, String, String)
     * @see ConflictStrategy#CREATE_NEW_INDEXED_NAME
     */
    public static FileStorageItem putSnapshotFile(InputStream file, String fileName) {
        return putFile(file, fileName, snapshotsSubDir);
    }

    /**
     * Copy file to artifacts storage. If file with such name already exists there new will be created with added index in name
     *
     * @param file File to be copied
     * @return Storage item
     * @see #putFile(File, String)
     * @see ConflictStrategy#CREATE_NEW_INDEXED_NAME
     */
    public static FileStorageItem putArtifactFile(File file) {
        return putFile(file, artifactsSubDir);
    }

    /**
     * Create file in artifacts storage. If file with such name already exists there new will be created with added index in name
     *
     * @param file     Input stream to be stored to file
     * @param fileName File name to be created.
     * @return Storage item
     * @see #putFile(InputStream, String, String)
     * @see ConflictStrategy#CREATE_NEW_INDEXED_NAME
     */
    public static FileStorageItem putArtifactFile(InputStream file, String fileName) {
        return putFile(file, fileName, artifactsSubDir);
    }

    /**
     * Create subdir in storage
     *
     * @param subDir Sub directory path
     * @return Sub directory
     */
    public static File createSubDir(String subDir) {
        File subDirFile = new File(_instance.rootDir, subDir);
        if (!subDirFile.mkdirs() && !subDirFile.exists()) {
            throw new RuntimeException("Can't create subdir " + subDir + ".");
        }
        return subDirFile;
    }

    /**
     * Add file to storage from input stream
     *
     * @param inputStream      Stream to be stored to file
     * @param fileName         Name of file to be created/overwritten
     * @param subDir           Sub directory name where to store the file
     * @param conflictStrategy How to resolve case if such file already exists
     * @return Created storage item
     */
    private FileStorageItem putFileToStorage(InputStream inputStream, String fileName, String subDir,
                                             ConflictStrategy conflictStrategy) {

        if (null == inputStream) {
            throw new IllegalArgumentException("Unable to copy file " + fileName + ". Original file / resource not found.");
        }

        File subDirFile;
        if (!subDir.isEmpty())
            subDirFile = new File(rootDir, subDir);
        else
            subDirFile = rootDir;

        if (!subDirFile.exists()) {
            if (!subDirFile.mkdirs())
                throw new RuntimeException("Can't create subdir " + subDir + ".");
        }

        File targetFile = new File(subDirFile, fileName);
        if (targetFile.exists()) {
            switch (conflictStrategy) {
                case SKIP:
                    return new FileStorageItem(targetFile, targetFile.getName());
                case OVERRIDE_EXISTING:
                    break;
                case RAISE_ERROR:
                    throw new RuntimeException("File " + targetFile.getName() + " already exists");
                default:
                    int index = 1;
                    while (targetFile.exists()) {
                        String baseName = FilenameUtils.getBaseName(fileName);
                        String extension = FilenameUtils.getExtension(fileName);
                        if (!extension.isEmpty()) extension = "." + extension;
                        targetFile = new File(subDirFile, baseName + "_" + index + extension);
                        index++;
                    }
                    try {
                        targetFile.createNewFile();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
            }
        }

        try {

            try (FileOutputStream stream = new FileOutputStream(targetFile)) {
                IOUtils.copy(inputStream, stream);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error while creating local storage file", e);
        }

        return new FileStorageItem(targetFile, fileName);
    }

    /**
     * In case if file with same name exists in storage
     */
    private enum ConflictStrategy {
        /**
         * If file with such name exists there will be created new one with same name and indexed postfix: myfile.txt->myfile.1.txt
         */
        CREATE_NEW_INDEXED_NAME,
        /**
         * Replace existing file with such name with this one
         */
        OVERRIDE_EXISTING,
        /**
         * if such file already exists do nothing
         */
        SKIP,
        /**
         * Raise exception if such file already exists
         */
        RAISE_ERROR
    }
}