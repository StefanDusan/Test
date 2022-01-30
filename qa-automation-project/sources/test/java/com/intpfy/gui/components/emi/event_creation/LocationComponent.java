package com.intpfy.gui.components.emi.event_creation;

import com.intpfy.gui.complex_elements.SearchDropdown;
import com.intpfy.model.event.Location;
import com.intpfyqa.gui.web.selenium.BaseComponent;
import com.intpfyqa.gui.web.selenium.IParent;
import org.openqa.selenium.By;

public class LocationComponent extends BaseComponent {

    private final SearchDropdown dropdown;

    public LocationComponent(IParent parent) {
        super("Location", parent, By.xpath(".//label[text() = 'Location (nearest server is used)']/parent::div"));
        dropdown = new SearchDropdown("Location search dropdown", this, By.cssSelector("div[name='location']"));
    }

    public void select(Location location) {
        dropdown.selectContainsTextWithAssertion(location.getCityName());
    }
}
