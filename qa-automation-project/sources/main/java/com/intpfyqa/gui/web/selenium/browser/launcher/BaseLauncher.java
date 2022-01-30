package com.intpfyqa.gui.web.selenium.browser.launcher;

import com.intpfyqa.gui.web.selenium.SeleniumDownloadHelper;
import com.intpfyqa.gui.web.selenium.browser.BrowserWindow;
import com.intpfyqa.logging.LogManager;
import com.intpfyqa.run.RunSessions;
import com.intpfyqa.utils.TestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.SystemUtils;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.concurrent.*;

import static java.lang.Runtime.getRuntime;

public abstract class BaseLauncher {

    private final static List<BaseLauncher> drivers = new ArrayList<>();

    static {
        getRuntime().addShutdownHook(
                new Thread(() -> {
                    List<BaseLauncher> driversLocal = new ArrayList<>(drivers);
                    driversLocal.stream().filter(Objects::nonNull).forEach(driver -> {
                        try {
                            driver.shutdown();
                        } catch (Exception ignore) {
                            driver.executable = null;
                        }
                    });
                }));
    }

    private File executable;
    private RemoteWebDriver driver;
    private Throwable launchException = null;

    public RemoteWebDriver getDriver() {
        return driver;
    }

    private void removeExecutable() {
        executable.delete();
        executable = null;
    }

    public abstract Capabilities getStartupCapabilities();

    protected File setupExecutable(String path) {

        if (executable == null) executable = new File(path);

        if (!executable.exists() || !executable.isFile()) {

            try (InputStream is = BrowserWindow.class.getClassLoader().getResourceAsStream(path)) {
                if (is == null) throw new RuntimeException("Driver executable setup is incorrect. " +
                        "No file or resource found by path " + path);
                String extension = FilenameUtils.getExtension(executable.getName());
                String baseName = FilenameUtils.getBaseName(executable.getName());
                executable = File.createTempFile("drv", (extension.length() > 0 ? "." : "") + extension);
                executable.deleteOnExit();
                FileUtils.copyInputStreamToFile(is, executable);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            if (!executable.exists() || !executable.isFile()) {
                throw new RuntimeException("Failed to prepare executable driver. File not found: " + executable);
            }
        }

        if (!SystemUtils.IS_OS_WINDOWS && !executable.canExecute()) {
            try {
                getRuntime().exec("chmod +x " + executable.getAbsolutePath());
            } catch (IOException e) {
                throw new RuntimeException("Failed to set file as executable", e);
            }
        }

        TestUtils.sleep(500); //sometimes file may not appear
        return executable;
    }

    protected abstract RemoteWebDriver startDriver();

    private void setLaunchException(Throwable t) {
        this.launchException = t;
    }

    public synchronized void launch() {

        setLaunchException(null);
        Throwable t = executeInThreadWithTimeout(() -> {
            try {
                driver = startDriver();
            } catch (Throwable e) {
                setLaunchException(e);
            }
        });

        if (null == launchException)
            launchException = t;

        if (null != this.launchException) {
            shutdown();
            if (launchException instanceof RuntimeException)
                throw (RuntimeException) launchException;
            throw new RuntimeException(launchException);
        } else {
            drivers.add(this);
        }
    }

    public void shutdown() {

        if (null == driver) return;

        try {

            Set<String> windows = new HashSet<>();
            executeInThreadWithTimeout(() -> windows.addAll(getDriver().getWindowHandles()));
            for (String window : windows) {
                Throwable t = executeInThreadWithTimeout(() -> {
                    try {
                        getDriver().switchTo().window(window);
                        getDriver().close();
                    } catch (UnhandledAlertException e) {
                        getDriver().switchTo().alert().accept();
                    } catch (Exception ignore) {
                    }
                });
                if (t instanceof TimeoutException) {
                    try {
                        LogManager.getCurrentTestLogger().fail("BrowserSession",
                                "Error during closing session: " + TestUtils.getThrowableShortDescription(t));
                    } catch (Exception ignore) {

                    }
                }
            }
        } catch (Throwable ignore) {

        }

        Throwable throwable = executeInThreadWithTimeout(() -> getDriver().quit());
        if (throwable instanceof TimeoutException) {
            try {
                LogManager.getCurrentTestLogger().fail("BrowserSession",
                        "Error during closing session: " + TestUtils.getThrowableShortDescription(throwable));
            } catch (Exception ignore) {

            }
        }

        try {
            removeExecutable();
        } catch (Throwable ignore) {
            executable = null;
        }
        driver = null;
    }


    public abstract SeleniumDownloadHelper getDownloadHelper();

    private Throwable executeInThreadWithTimeout(Runnable runnable) {

        ExecutorService executor = Executors.newSingleThreadExecutor(RunSessions.threadFactory());
        Future future = executor.submit(runnable);

        try {
            future.get(getStartTimeoutSeconds(), TimeUnit.SECONDS);
        } catch (TimeoutException e) {
            future.cancel(true);
            return e;
        } catch (ExecutionException | InterruptedException e) {
            return e;
        }
        return null;
    }

    protected long getStartTimeoutSeconds() {
        return 60;
    }
}
