package com.intpfyqa.gui.web.selenium.elements.util;

import com.intpfyqa.gui.web.selenium.elements.Element;

public interface ScrollableContainer {

    void scrollIntoView(Element targetElement, boolean verifyMadeVisible, int xShift, int yShift,
                        int widthShift, int heightShift);

    default void scrollIntoView(Element element) {
        this.scrollIntoView(element, true, 0, 0, 0, 0);
    }
}