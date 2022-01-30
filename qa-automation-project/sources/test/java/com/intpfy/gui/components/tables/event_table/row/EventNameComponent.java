package com.intpfy.gui.components.tables.event_table.row;

import com.intpfy.model.event.Event;
import com.intpfyqa.gui.web.selenium.BaseComponent;
import com.intpfyqa.gui.web.selenium.IParent;
import com.intpfyqa.gui.web.selenium.annotations.ElementInfo;
import com.intpfyqa.gui.web.selenium.elements.Button;
import com.intpfyqa.utils.TestUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

import java.time.Duration;
import java.time.LocalDateTime;

public class EventNameComponent extends BaseComponent {

    @ElementInfo(name = "Event name", findBy = @FindBy(tagName = "button"))
    private Button nameButton;

    public EventNameComponent(IParent parent) {
        super("Event name", parent, By.cssSelector("td[title=\"'Event name'\"]"));
    }

    public String getName() {
        return nameButton.getText().trim();
    }

    public String getNameToDisplay() {
        return getComponentElement().getText().replaceFirst(getName(), "").trim();
    }

    public boolean isNameToDisplayActual(Event event, Duration timeout) {

        String expectedNameToDisplay = event.getDisplayName();

        LocalDateTime endDateTime = LocalDateTime.now().plus(timeout);
        do {
            if (getNameToDisplay().equals(expectedNameToDisplay)) {
                return true;
            }
            TestUtils.sleep(150);
        }
        while (LocalDateTime.now().isBefore(endDateTime));

        return false;
    }
}
