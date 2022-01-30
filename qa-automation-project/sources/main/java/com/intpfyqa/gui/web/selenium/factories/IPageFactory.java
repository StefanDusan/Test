package com.intpfyqa.gui.web.selenium.factories;

import com.intpfyqa.gui.web.selenium.BaseAutomationPage;

public interface IPageFactory {

    <T extends BaseAutomationPage> T createPage(Class<T> pageClass, Object... args);
}
