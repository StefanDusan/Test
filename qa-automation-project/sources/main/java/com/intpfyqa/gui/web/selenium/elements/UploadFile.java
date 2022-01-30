package com.intpfyqa.gui.web.selenium.elements;

import com.intpfyqa.gui.web.selenium.IParent;
import com.intpfyqa.gui.web.selenium.elements.util.ElementFromListMatcher;
import com.intpfyqa.gui.web.selenium.exceptions.ElementException;
import com.intpfyqa.settings.WebSettings;
import com.intpfyqa.utils.TestUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.testng.util.Strings;

import java.awt.*;
import java.io.File;

/**
 * Element for upload file
 */
public class UploadFile extends Element {
    /**
     * Constructor
     *
     * @param logicalName element logical name
     * @param page        page
     * @param locator     locator
     */
    public UploadFile(String logicalName, IParent page, By locator) {
        super(logicalName, page, locator);
    }

    public UploadFile(String logicalName, IParent page, By locator, ElementFromListMatcher matcher) {
        super(logicalName, page, locator, matcher);
    }

    public void upload(File file) {
        trace("Uploading file %s", file.getAbsolutePath());
        try {
            getNativeElement().sendKeys(file.getAbsolutePath());
            debug("Set path to file %s", file);
        } catch (Throwable e) {
            throw new ElementException(this, "Upload file", e, file);
        }

        //in IE sometimes (often in fact) dialog is not closed
        if (isInIE()) {
            try {
                exists();
            } catch (ElementException ignore) {
                if (getPage().getPageContext().getBrowserWindow().hasAlert()) {
                    Alert alert = getPage().getPageContext().getBrowserWindow()
                            .getAlert(WebSettings.AJAX_TIMEOUT);
                    TestUtils.sleep(1000);
                    alert.sendKeys(file.getAbsolutePath());
                    TestUtils.sleep(1000);
                    try {
                        new Robot().keyPress('\n');
                    } catch (AWTException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }

    public void clear() {
        if (!Strings.isNullOrEmpty(getProperty("value")))
            executeScriptInBrowser("try {\n" +
                    "    arguments[0].value = null;\n" +
                    "  } catch(ex) { }\n" +
                    "  if (arguments[0].value) {\n" +
                    "    arguments[0].parentNode.replaceChild(arguments[0].cloneNode(true), arguments[0]);\n" +
                    "  }", getNativeElement());
    }
}