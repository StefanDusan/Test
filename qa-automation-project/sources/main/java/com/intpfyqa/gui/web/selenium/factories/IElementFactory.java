package com.intpfyqa.gui.web.selenium.factories;

import com.intpfyqa.gui.web.selenium.elements.Element;

public interface IElementFactory {

    <T extends Element> T createElement(Class<T> elementClass, Object... args);
}
