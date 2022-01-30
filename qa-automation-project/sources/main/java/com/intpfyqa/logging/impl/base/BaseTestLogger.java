package com.intpfyqa.logging.impl.base;

import com.intpfyqa.logging.impl.log4j_html.UnloggingException;
import com.intpfyqa.logging.ITakeSnapshot;
import com.intpfyqa.logging.ITestLogger;
import com.intpfyqa.utils.TestUtils;

public abstract class BaseTestLogger implements ITestLogger {


    protected abstract void logMessage(Level level, String header, String message, ITakeSnapshot takeSnapshot);

    @Override
    public void info(String header, String message) {
        logMessage(Level.INFO, header, message, null);
    }

    @Override
    public void info(String header, String message, ITakeSnapshot takeSnapshot) {
        logMessage(Level.INFO, header, message, takeSnapshot);
    }


    @Override
    public void pass(String header, String message) {
        logMessage(Level.PASS, header, message, null);
    }

    @Override
    public void pass(String header, String message, ITakeSnapshot takeSnapshot) {
        logMessage(Level.PASS, header, message, takeSnapshot);
    }

    @Override
    public void debug(String header, String message) {
        logMessage(Level.DEBUG, header, message, null);
    }

    @Override
    public void debug(String header, String message, ITakeSnapshot takeSnapshot) {
        logMessage(Level.DEBUG, header, message, takeSnapshot);
    }

    @Override
    public void trace(String header, String message) {
        logMessage(Level.TRACE, header, message, null);
    }

    @Override
    public void trace(String header, String message, ITakeSnapshot takeSnapshot) {
        logMessage(Level.TRACE, header, message, takeSnapshot);
    }

    @Override
    public void fail(String header, String message) {
        logMessage(Level.FAIL, header, message,
                null);
    }

    @Override
    public void fail(String header, String message, ITakeSnapshot takeSnapshot) {
        logMessage(Level.FAIL, header, message,
                takeSnapshot);
    }

    @Override
    public void fail(String header, String message, Throwable throwable) {
        logMessage(Level.FAIL, header, message + "\n" + TestUtils.getThrowableFullDescription(throwable),
                null);
    }

    @Override
    public void fail(String header, String message, Throwable throwable, ITakeSnapshot takeSnapshot) {
        logMessage(Level.FAIL, header, message + "\n" + TestUtils.getThrowableFullDescription(throwable),
                takeSnapshot);
    }

    @Override
    public void fail(String header, Throwable throwable) {
        fail(header, TestUtils.getThrowableFullDescription(throwable));
    }

    @Override
    public void fail(String header, Throwable throwable, ITakeSnapshot takeSnapshot) {
        fail(header, TestUtils.getThrowableFullDescription(throwable), takeSnapshot);
    }

    @Override
    public void fail(Throwable throwable) {
        fail("ERROR", throwable);
    }

    @Override
    public void fail(Throwable throwable, ITakeSnapshot takeSnapshot) {
        fail("ERROR", throwable, takeSnapshot);
    }

    @Override
    public void error(String header, String message) {
        logMessage(Level.FAIL, header, message, null);
        throw new UnloggingException(message);
    }

    @Override
    public void error(String header, String message, ITakeSnapshot takeSnapshot) {
        logMessage(Level.ERROR, header, message, takeSnapshot);
        throw new UnloggingException(message);
    }

    @Override
    public void error(String header, String message, Throwable throwable) {
        logMessage(Level.FAIL, header, message + "\n" + TestUtils.getThrowableFullDescription(throwable),
                null);
        throw new UnloggingException(message);
    }

    @Override
    public void error(String header, String message, Throwable throwable, ITakeSnapshot takeSnapshot) {
        logMessage(Level.FAIL, header, message + "\n" + TestUtils.getThrowableFullDescription(throwable),
                takeSnapshot);
        throw new UnloggingException(message);
    }

    @Override
    public void error(String header, Throwable throwable) {
        error(header, TestUtils.getThrowableFullDescription(throwable));
    }

    @Override
    public void error(String header, Throwable throwable, ITakeSnapshot takeSnapshot) {
        error(header, TestUtils.getThrowableFullDescription(throwable), takeSnapshot);
    }

    @Override
    public void error(Throwable throwable) {
        error("ERROR", throwable);
    }

    @Override
    public void error(Throwable throwable, ITakeSnapshot takeSnapshot) {
        error("ERROR", throwable, takeSnapshot);
    }


}
