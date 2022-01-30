package com.intpfy.gui.components.tables.event_table.row;

import com.intpfy.model.Language;
import com.intpfy.model.event.Event;
import com.intpfyqa.gui.web.selenium.BaseComponent;
import com.intpfyqa.gui.web.selenium.IParent;
import com.intpfyqa.gui.web.selenium.elements.Element;
import com.intpfyqa.utils.TestUtils;
import org.openqa.selenium.By;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class LanguagesComponent extends BaseComponent {

    public LanguagesComponent(IParent parent) {
        super("Languages", parent, By.cssSelector("td[title=\"'Languages'\"]"));
    }

    public List<Language> getLanguages() {
        return getLanguagesElements().stream()
                .map(e -> Language.getLanguage(e.getText()))
                .collect(Collectors.toList());
    }

    public boolean areActual(Event event, Duration timeout) {

        LocalDateTime endDateTime = LocalDateTime.now().plus(timeout);
        do {
            if (getLanguages().equals(event.getLanguages())) {
                return true;
            }
            TestUtils.sleep(150);
        }
        while (LocalDateTime.now().isBefore(endDateTime));

        return false;
    }

    private List<Element> getLanguagesElements() {
        return getComponentElement().children(By.cssSelector("button.btn-xs.ng-scope"));
    }
}
