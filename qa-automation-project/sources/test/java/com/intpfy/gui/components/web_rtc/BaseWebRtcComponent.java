package com.intpfy.gui.components.web_rtc;

import com.intpfyqa.gui.web.selenium.BaseComponent;
import com.intpfyqa.gui.web.selenium.IParent;
import com.intpfyqa.gui.web.selenium.annotations.ElementInfo;
import com.intpfyqa.gui.web.selenium.elements.Element;
import com.intpfyqa.gui.web.selenium.elements.util.ElementFromListMatcher;
import com.intpfyqa.settings.WebSettings;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

import java.time.Duration;


public abstract class BaseWebRtcComponent extends BaseComponent {

    private static final String OPEN = "open";

    @ElementInfo(name = "Summary", findBy = @FindBy(tagName = "summary"))
    private Element summaryElement;

    protected BaseWebRtcComponent(String name, IParent parent, By componentLocator, ElementFromListMatcher matcher) {
        super(name, parent, componentLocator, matcher);
    }

    public void open() {

        if (isNotOpened()) {

            clickSummary();
            isOpened();
            scrollToSummary();
        }
    }

    protected double getElementValue(Element element) {
        return Double.parseDouble(element.getText());
    }

    private void clickSummary() {
        summaryElement.click();
    }

    private void scrollToSummary() {
        summaryElement.scrollIntoView(true);
    }

    private boolean isOpened() {
        return getComponentElement().waitPropertyNotEmpty(OPEN, WebSettings.AJAX_TIMEOUT);
    }

    private boolean isNotOpened() {
        return getComponentElement().waitPropertyEmpty(OPEN, Duration.ZERO);
    }
}