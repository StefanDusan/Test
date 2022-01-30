package com.intpfyqa.gui.web.selenium;

import org.openqa.selenium.By;

public abstract class BaseIFramePage extends BaseAutomationPage {

    private final By iframeLocator;

    protected BaseIFramePage(String name, IPageContext pageContext, By iframeLocator) {
        super(name, pageContext);
        this.iframeLocator = iframeLocator;
    }

    By getIframeLocator() {
        return iframeLocator;
    }
}
