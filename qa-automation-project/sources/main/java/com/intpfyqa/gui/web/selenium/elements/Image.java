package com.intpfyqa.gui.web.selenium.elements;

import com.intpfyqa.gui.web.selenium.IParent;
import com.intpfyqa.gui.web.selenium.elements.util.ElementFromListMatcher;
import com.intpfyqa.gui.web.selenium.exceptions.ElementException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriverException;

import java.io.File;

/**
 * Image
 */
public class Image extends Element {

    public Image(String logicalName, IParent page, By locator) {
        super(logicalName, page, locator);
    }

    public Image(String logicalName, IParent page, By locator, ElementFromListMatcher matcher) {
        super(logicalName, page, locator, matcher);
    }

    public String getSrc() {
        return getProperty("src");
    }

    public String getSrcBase64() {
        trace("Getting image base64 string");
        String src = getSrc();
        try {
            try {
                if (src.contains("base64,")) {
                    src = src.substring(src.indexOf("base64,") + "base64,".length());
                }
                debug("Got base64: %s", src);
                return src;
            } catch (Exception e) {
                debug("Failed to parse image source to getValues file. Source is: " + src);
                throw new ElementException(this, "Get src base64", e);
            }
        } catch (Throwable e) {
            throw new ElementException(this, "Get src base64", e);
        }
    }

    public String getSrcFileName() {
        trace("Getting src file name");
        String src = getSrc();
        try {
            try {
                String file = new File(src).getName();
                debug("Got file: %s", file);
                return file;
            } catch (Exception e) {
                debug("Failed to parse image source to getValues file. Source is: " + src);
                throw new ElementException(this, "Get image file", e);
            }
        } catch (WebDriverException e) {
            throw new ElementException(this, "Get image file", e);
        }
    }
}