package com.intpfy.gui.dialogs.moderator;

import com.intpfy.gui.complex_elements.PageDropdown;
import com.intpfy.gui.dialogs.Cancelable;
import com.intpfy.gui.dialogs.Closeable;
import com.intpfy.gui.dialogs.Savable;
import com.intpfy.model.Language;
import com.intpfyqa.gui.web.selenium.BaseComponent;
import com.intpfyqa.gui.web.selenium.IParent;
import com.intpfyqa.gui.web.selenium.annotations.ElementInfo;
import com.intpfyqa.gui.web.selenium.elements.Element;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

public class SetActiveChannelsDW extends BaseComponent implements Closeable, Savable, Cancelable {

    @ElementInfo(name = "Username", findBy = @FindBy(css = "div.active-languages span"))
    private Element usernameElement;

    private final LanguageComponent incomingLanguage;
    private final LanguageComponent outgoingLanguage;

    public SetActiveChannelsDW(IParent parent) {
        super("'Set Active Channels' dialog window", parent,
                By.xpath("//div[@class='modal-content' and .//h4[text()='SET ACTIVE CHANNELS']]"));
        incomingLanguage = new LanguageComponent(this, LanguageType.INCOMING);
        outgoingLanguage = new LanguageComponent(this, LanguageType.OUTGOING);
    }

    public String getUsername() {
        return usernameElement.getText();
    }

    public void selectOutgoingLanguage(Language language) {
        info(String.format("Select Outgoing language '%s'.", language));
        outgoingLanguage.select(language);
    }

    public void selectIncomingLanguage(Language language) {
        info(String.format("Select Incoming language '%s'.", language));
        incomingLanguage.select(language);
    }

    private static class LanguageComponent extends BaseComponent {

        @ElementInfo(name = "Dropdown", findBy = @FindBy(css = "div.ng-select-container"))
        private PageDropdown dropdown;

        private LanguageComponent(IParent parent, LanguageType type) {
            super(type + " language component", parent,
                    By.xpath(".//div[contains(@class, 'modal-half') and .//div[text() = '" + type + "']]"));
        }

        private void select(Language language) {
            dropdown.selectContainsTextWithAssertion(language.name());
        }
    }

    private enum LanguageType {
        INCOMING,
        OUTGOING
    }
}
