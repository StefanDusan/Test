package com.intpfyqa.gui.web.selenium.exceptions;

import com.intpfyqa.gui.web.selenium.browser.BrowserWindow;
import org.apache.commons.lang3.StringUtils;

public class BrowserException extends GUIException {

    public BrowserException(String message, Throwable cause) {
        super(message, cause);
    }

    public static BrowserException onCommand(BrowserWindow source, String commandName, Object[] commandArgs,
                                             Throwable originalError) {
        return new BrowserException(String.format("Error while executing command '%s' with parameters (%s)\nWindow: %s\nText: %s",
                commandName, source, StringUtils.join(commandArgs, ", "),
                (null == originalError ? "" : originalError.getMessage())),
                originalError);
    }
}
