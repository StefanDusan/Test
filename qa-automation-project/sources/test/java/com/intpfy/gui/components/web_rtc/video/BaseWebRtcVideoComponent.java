package com.intpfy.gui.components.web_rtc.video;

import com.intpfy.gui.components.web_rtc.BaseWebRtcComponent;
import com.intpfyqa.gui.web.selenium.IParent;
import com.intpfyqa.gui.web.selenium.annotations.ElementInfo;
import com.intpfyqa.gui.web.selenium.elements.Element;
import com.intpfyqa.gui.web.selenium.elements.util.ElementFromListMatcher;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

public abstract class BaseWebRtcVideoComponent extends BaseWebRtcComponent {

    @ElementInfo(name = "Qp sum", findBy = @FindBy(xpath = ".//td[text() = 'qpSum']/following-sibling::td"))
    protected Element qpSumElement;

    protected BaseWebRtcVideoComponent(String name, IParent parent, By componentLocator, ElementFromListMatcher matcher) {
        super(name, parent, componentLocator, matcher);
    }

    public double getQpSum() {
        return getElementValue(qpSumElement);
    }
}