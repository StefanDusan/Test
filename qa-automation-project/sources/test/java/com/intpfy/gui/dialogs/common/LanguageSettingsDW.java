package com.intpfy.gui.dialogs.common;

import com.intpfy.gui.complex_elements.PageDropdown;
import com.intpfy.gui.complex_elements.selection.ComplexCheckbox;
import com.intpfy.model.Language;
import com.intpfyqa.gui.web.selenium.BaseComponent;
import com.intpfyqa.gui.web.selenium.IParent;
import com.intpfyqa.gui.web.selenium.annotations.ElementInfo;
import com.intpfyqa.gui.web.selenium.annotations.Matcher;
import com.intpfyqa.gui.web.selenium.elements.Button;
import com.intpfyqa.gui.web.selenium.elements.util.matchers.ElementFromListMatcherByIndex;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

public class LanguageSettingsDW extends BaseComponent {

    @ElementInfo(name = "Incoming language dropdown", findBy =
    @FindBy(xpath = ".//div[contains(@class, 'ng-select-container') and .//div[@class = 'ng-placeholder' and contains(text(), 'Incoming Language')]]"))
    private PageDropdown incomingLanguageDropdown;

    @ElementInfo(name = "Outgoing language dropdown", findBy =
    @FindBy(xpath = ".//div[contains(@class, 'ng-select-container') and .//div[@class = 'ng-placeholder' and contains(text(), 'Outgoing Language')]]"))
    private PageDropdown outgoingLanguageDropdown;

    @ElementInfo(name = "Relay language complex checkbox", findBy = @FindBy(css = "div.form-group._checkbox"))
    private ComplexCheckbox relayLanguageCheckbox;

    @ElementInfo(name = "Relay language dropdown", findBy =
    @FindBy(xpath = ".//div[contains(@class, 'ng-select-container') and .//div[@class = 'ng-placeholder' and contains(text(), 'Outgoing Language')]]"), matcher =
    @Matcher(clazz = ElementFromListMatcherByIndex.class, args = "2"))
    private PageDropdown relayLanguageDropdown;

    @ElementInfo(name = "Save", findBy = @FindBy(xpath = ".//button[text()='SAVE']"))
    private Button saveButton;

    public LanguageSettingsDW(IParent parent) {
        super("Language settings dialog window", parent, By.tagName("language-modal"));
    }

    public void selectOutgoingLanguage(Language language) {
        info(String.format("Select Outgoing language '%s'.", language));
        outgoingLanguageDropdown.selectContainsTextWithAssertion(language.name());
    }

    public void selectRelayLanguage(Language language) {
        info(String.format("Select Relay language '%s'.", language));
        relayLanguageCheckbox.select();
        relayLanguageDropdown.visible();
        relayLanguageDropdown.selectContainsTextWithAssertion(language.name());
    }

    public void selectIncomingLanguage(Language language) {
        info(String.format("Select Incoming language '%s'.", language));
        incomingLanguageDropdown.selectContainsTextWithAssertion(language.name());
    }

    public void selectNoneIncomingLanguage() {
        info("Select 'None' Incoming language.");
        incomingLanguageDropdown.selectContainsTextWithAssertion("none");
    }

    public void save() {
        info("Save.");
        saveButton.click();
    }
}
