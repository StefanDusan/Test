package com.intpfyqa.gui.web.selenium.factories;

import com.intpfyqa.gui.web.selenium.BaseComponent;

public interface IComponentFactory {

    <T extends BaseComponent> T createComponent(Class<T> elementClass, Object... args);
}
