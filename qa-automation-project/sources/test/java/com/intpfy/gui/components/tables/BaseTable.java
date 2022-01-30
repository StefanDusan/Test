package com.intpfy.gui.components.tables;

import com.intpfyqa.gui.web.selenium.BaseComponent;
import com.intpfyqa.gui.web.selenium.IParent;
import org.openqa.selenium.By;

public abstract class BaseTable extends BaseComponent {

    private static final By rowLocator = By.cssSelector("tr");

    public BaseTable(String name, IParent parent) {
        this(name, parent, By.cssSelector("table"));
    }

    public BaseTable(String name, BaseComponent component) {
        this(name, component, By.cssSelector("table.generaltable"));
    }

    public BaseTable(String name, IParent parent, By by) {
        super(name, parent, by);
    }

    public BaseTable(String name, BaseComponent component, By by) {
        super(name, component, by);
    }

    protected By getRowLocator() {
        return rowLocator;
    }
}