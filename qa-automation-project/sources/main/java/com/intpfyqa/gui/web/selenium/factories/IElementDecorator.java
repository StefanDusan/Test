package com.intpfyqa.gui.web.selenium.factories;

import com.intpfyqa.gui.web.selenium.BaseAutomationPage;
import com.intpfyqa.gui.web.selenium.BaseComponent;

public interface IElementDecorator {

    <T extends BaseAutomationPage> T decoratePage(T instance, IElementFactory elementsFactory,
                                                  IComponentFactory componentsFactory);

    <T extends BaseComponent> T decorateComponent(T instance, IElementFactory elementsFactory,
                                                  IComponentFactory componentsFactory);
}
