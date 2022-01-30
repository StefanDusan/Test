package com.intpfy.gui.pages.emi.event_creation;

import com.intpfy.gui.components.emi.event_creation.ManagerAndModeratorComponent;
import com.intpfyqa.gui.web.selenium.IPageContext;
import com.intpfyqa.gui.web.selenium.annotations.ElementInfo;
import com.intpfyqa.gui.web.selenium.elements.Element;
import org.openqa.selenium.support.FindBy;

import java.time.Duration;

public class ManagerAndModeratorPage extends BaseEventCreationPage {

    @ElementInfo(name = "Page title", findBy = @FindBy(xpath = "//span[contains(@class, 'step-title') and text()=' Step 2 of 3']"))
    private Element pageTitle;

    private final ManagerAndModeratorComponent managerAndModerator;

    public ManagerAndModeratorPage(IPageContext pageContext) {
        super("Add Event (Manager And Moderator) page", pageContext);
        managerAndModerator = new ManagerAndModeratorComponent(this);
    }

    @Override
    public boolean isOpened(Duration timeout) {
        return pageTitle.visible(timeout);
    }

    public TokensAndNameCustomizationPage proceedToAddEventTokensPage() {
        info("Proceed to 'Tokens and name customization' page.");
        continueButton.click();
        return new TokensAndNameCustomizationPage(getPageContext());
    }
}
