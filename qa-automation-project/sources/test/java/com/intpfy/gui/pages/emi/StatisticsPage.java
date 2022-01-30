package com.intpfy.gui.pages.emi;

import com.intpfyqa.gui.web.selenium.IPageContext;
import com.intpfyqa.gui.web.selenium.annotations.ElementInfo;
import com.intpfyqa.gui.web.selenium.elements.Element;
import org.openqa.selenium.support.FindBy;

import java.time.Duration;

public class StatisticsPage extends BaseEmiPage {

    @ElementInfo(name = "Page title", findBy = @FindBy(xpath = "//h1[text() = 'Statistics']"))
    private Element pageTitle;

    public StatisticsPage(IPageContext pageContext) {
        super("Statistics page", pageContext);
    }

    @Override
    public boolean isOpened(Duration timeout) {
        return pageTitle.visible(timeout);
    }
}