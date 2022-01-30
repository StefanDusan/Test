package com.intpfyqa.gui.web.selenium.elements;

import java.time.Duration;

public interface Selectable {

    void select();

    boolean isSelected();

    boolean waitIsSelected(Duration timeout);
}
