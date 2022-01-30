package com.intpfy.gui.pages;

import com.intpfyqa.gui.web.selenium.BaseAutomationPage;
import com.intpfyqa.gui.web.selenium.IPageContext;

public abstract class BaseAuthorizedPage extends BaseAutomationPage {

    protected BaseAuthorizedPage(String name, IPageContext pageContext) {
        super(name, pageContext);
    }

    public abstract LoginPage logOut();
}