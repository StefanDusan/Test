package com.intpfyqa.gui.web.selenium.exceptions;

import com.intpfyqa.gui.web.selenium.elements.Element;
import com.intpfyqa.gui.web.selenium.elements.ParentNotFoundException;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.*;

import java.util.Arrays;

public class ElementException extends GUIException {

    private final Element source;
    private final Throwable realReason;

    public ElementException(Element source, String action, Throwable cause, Object... args) {
        super(createMessage(source, action, cause, args), cause);
        this.source = source;
        this.realReason = findRealWebDriverReason(cause);
    }

    private static String createMessage(Element source, String action, Throwable cause, Object[] args) {
        Throwable realReason = findRealWebDriverReason(cause);
        String description = "Error working with item " + source.toString() + " (" + source.locateString() + ")" +
                "\nError while executing action " + action + " with parameters (" + StringUtils.join(args, ", ") + ")";

        description += "\n\tCalled: ";

        if (realReason instanceof WebDriverException) {
            description += humanReadableWebDriverException((WebDriverException) realReason);
        } else {
            description += realReason.getMessage();
        }
        return description;
    }

    private static String humanReadableWebDriverException(WebDriverException exception) {
        if (exception == null) return "";

        String err = exception.getMessage();

        if (exception instanceof InvalidSelectorException) {
            err = "Autotest programming error. Incorrect locator syntax";
        } else if (exception instanceof NoSuchElementException) {
            err = "Element is not found on the page";
        } else if (exception instanceof StaleElementReferenceException) {
            err = "Autotest programming error. Element is changed in DOM";
        } else if (exception instanceof InvalidElementStateException) {
            err = "Element is not visible or overlapped by another element, or not available for interaction (disabled)";
        } else if (exception instanceof ParentNotFoundException) {
            err = "Parent element (or page) not found";
        }

        return err;
    }

    public static Throwable findRealWebDriverReason(Throwable t) {
        if (t instanceof TimeoutException)
            if (null != t.getCause())
                return findRealWebDriverReason(t.getCause());
            else
                return t;

        if (t instanceof GUIException) {
            if (null != t.getCause())
                return findRealWebDriverReason(t.getCause());
            else
                return t;
        }

        return t;
    }

    private static boolean notInstanceOf(Throwable target, Class... exceptions) {
        return Arrays.stream(exceptions).noneMatch(e -> e.isInstance(target));
    }

    public boolean isScriptError() {
        return isScriptError(realReason);
    }

    private static boolean isScriptError(Throwable t) {
        if (!(t instanceof WebDriverException)) return true;
        if (t instanceof InvalidSelectorException) return true;
        if (t instanceof ParentNotFoundException) {
            if (null != t.getCause()) {
                Throwable wdreason = findRealWebDriverReason(t.getCause());
                if (null != wdreason && wdreason != t)
                    return isScriptError(wdreason);
            }
        }

        return notInstanceOf(t, InvalidElementStateException.class, StaleElementReferenceException.class,
                NoSuchElementException.class, TimeoutException.class);
    }

    public boolean isUnhandledAlert() {
        return realReason instanceof UnhandledAlertException;
    }

    public Throwable getRootCause() {
        Throwable cause = realReason;
        Throwable temp = cause;
        while ((cause = cause.getCause()) != null) {
            temp = cause;
        }
        return temp;
    }

    public Element getSource() {
        return source;
    }
}
