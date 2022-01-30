package com.intpfy.gui.components.emi.event_creation;

import com.intpfy.gui.complex_elements.SearchDropdown;
import com.intpfy.model.Language;
import com.intpfyqa.gui.web.selenium.BaseComponent;
import com.intpfyqa.gui.web.selenium.IParent;
import com.intpfyqa.settings.WebSettings;
import org.openqa.selenium.By;

import java.util.List;

public class LanguageComponent extends BaseComponent {

    private final SearchDropdown dropdown;

    public LanguageComponent(IParent parent) {
        super("Language", parent, By.xpath(".//label[text() = 'Language']/parent::div"));
        dropdown = new SearchDropdown("Language search dropdown", this, By.cssSelector("div[name='languages']"));
    }

    public void select(Language language) {
        selectFromDropdown(language);
        closeDropdown();
    }

    public void select(List<Language> languages) {
        for (Language language : languages) {
            selectFromDropdown(language);
        }
        closeDropdown();
    }

    private void selectFromDropdown(Language language) {
        dropdown.visible();
        dropdown.getComponentElement().scrollIntoView();
        dropdown.selectContainsTextWithAssertion(language.name());
    }

    private void closeDropdown() {
        dropdown.closeOptionsList();
        dropdown.isOptionsListClosed(WebSettings.AJAX_TIMEOUT);
    }
}
