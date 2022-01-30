package com.intpfy.gui.pages.emi.event_creation;

import com.intpfy.gui.pages.emi.BaseEmiPage;
import com.intpfyqa.gui.web.selenium.IPageContext;
import com.intpfyqa.gui.web.selenium.annotations.ElementInfo;
import com.intpfyqa.gui.web.selenium.elements.Button;
import org.openqa.selenium.support.FindBy;

public abstract class BaseEventCreationPage extends BaseEmiPage {

    @ElementInfo(name = "Continue / Save", findBy = @FindBy(css = "button[type='submit']"))
    protected Button continueButton;

    protected BaseEventCreationPage(String name, IPageContext pageContext) {
        super(name, pageContext);
    }
}
