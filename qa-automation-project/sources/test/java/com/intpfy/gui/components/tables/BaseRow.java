package com.intpfy.gui.components.tables;

import com.intpfyqa.gui.web.selenium.BaseAutomationPage;
import com.intpfyqa.gui.web.selenium.BaseComponent;
import com.intpfyqa.gui.web.selenium.IParent;
import com.intpfyqa.gui.web.selenium.elements.Element;
import com.intpfyqa.gui.web.selenium.elements.util.ElementFromListMatcher;
import org.openqa.selenium.By;

public class BaseRow extends BaseComponent {

    public BaseRow(String name, IParent parent, By componentLocator) {
        super(name, parent, componentLocator);
    }

    public BaseRow(String name, IParent parent, By componentLocator, ElementFromListMatcher matcher) {
        super(name, parent, componentLocator, matcher);
    }

    public BaseAutomationPage getPage() {
        return getRowElement().getPage();
    }

    public Element getRowElement() {
        return getComponentElement();
    }
}