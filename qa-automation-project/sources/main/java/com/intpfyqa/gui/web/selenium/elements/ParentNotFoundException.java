package com.intpfyqa.gui.web.selenium.elements;

import com.intpfyqa.gui.web.selenium.IParent;
import org.openqa.selenium.WebDriverException;

public class ParentNotFoundException extends WebDriverException {

    public ParentNotFoundException(IParent parent, Throwable reason) {
        super("Parent elements " + parent.toString() + " (" + parent.locateString() + ") not found", reason);
    }
}