package com.intpfy.gui.components.tables.event_table.row;

import com.intpfy.model.event.Event;
import com.intpfyqa.gui.web.selenium.BaseComponent;
import com.intpfyqa.gui.web.selenium.IParent;
import com.intpfyqa.gui.web.selenium.annotations.ElementInfo;
import com.intpfyqa.gui.web.selenium.elements.Element;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

import java.time.Duration;

public class AudienceAccessComponent extends BaseComponent {

    @ElementInfo(name = "Audience access allowed", findBy = @FindBy(css = "div[ng-if='!event.audienceBlocked']"))
    private Element audienceAccessAllowedElement;

    @ElementInfo(name = "Audience access blocked", findBy = @FindBy(css = "span[ng-if='event.audienceBlocked']"))
    private Element audienceAccessBlockedElement;

    public AudienceAccessComponent(IParent parent) {
        super("Audience access", parent, By.cssSelector("td[title=\"'Audience access to Source'\"]"));
    }

    public boolean isDisabled(Duration timeout) {
        return audienceAccessAllowedElement.notVisible(timeout) && audienceAccessBlockedElement.visible(timeout);
    }

    public boolean isActual(Event event, Duration timeout) {
        return event.isAudienceBlocked() == isDisabled(timeout);
    }
}
