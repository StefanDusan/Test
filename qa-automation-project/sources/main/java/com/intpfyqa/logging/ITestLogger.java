package com.intpfyqa.logging;

public interface ITestLogger {

    void info(String header, String message);

    void info(String header, String message, ITakeSnapshot takeSnapshot);

    void pass(String header, String message);

    void pass(String header, String message, ITakeSnapshot takeSnapshot);

    void debug(String header, String message);

    void debug(String header, String message, ITakeSnapshot takeSnapshot);

    void trace(String header, String message);

    void trace(String header, String message, ITakeSnapshot takeSnapshot);

    void fail(String header, String message);

    void fail(String header, String message, ITakeSnapshot takeSnapshot);

    void fail(String header, String message, Throwable throwable);

    void fail(String header, String message, Throwable throwable, ITakeSnapshot takeSnapshot);

    void fail(String header, Throwable throwable);

    void fail(String header, Throwable throwable, ITakeSnapshot takeSnapshot);

    void fail(Throwable throwable);

    void fail(Throwable throwable, ITakeSnapshot takeSnapshot);

    void error(String header, String message);

    void error(String header, String message, ITakeSnapshot takeSnapshot);

    void error(String header, String message, Throwable throwable);

    void error(String header, String message, Throwable throwable, ITakeSnapshot takeSnapshot);

    void error(String header, Throwable throwable);

    void error(String header, Throwable throwable, ITakeSnapshot takeSnapshot);

    void error(Throwable throwable);

    void error(Throwable throwable, ITakeSnapshot takeSnapshot);

    enum Level {
        INFO, DEBUG, TRACE, PASS, FAIL, ERROR
    }
}