package com.intpfyqa.gui.web.selenium.elements;

import java.time.Duration;

public interface Unselectable {

    void unselect();

    boolean waitIsNotSelected(Duration timeout);
}
