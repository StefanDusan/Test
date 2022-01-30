package com.intpfyqa.gui.web.selenium.factories;

import com.intpfyqa.gui.web.selenium.BaseComponent;
import com.intpfyqa.gui.web.selenium.IPageContext;
import com.intpfyqa.gui.web.selenium.IParent;
import com.intpfyqa.gui.web.selenium.elements.util.ElementFromListMatcher;
import org.openqa.selenium.By;

/**
 * Components factory
 */
public class DefaultWebComponentsFactory implements IComponentFactory {

    private static CommonFactory _commonFactory = CommonFactory.CommonFactoryBuilder
            .withAllowedClasses(BaseComponent.class)
            .withArgumentsExpand(By.class, IParent.class, IPageContext.class, ElementFromListMatcher.class)
            .build();

    private final IElementFactory elementsFactory;
    private final IElementDecorator decorator;

    public DefaultWebComponentsFactory() {
        this(WebFactoryHelper.getElementFactory(), WebFactoryHelper.getElementDecorator());
    }

    public DefaultWebComponentsFactory(IElementFactory elementsFactory, IElementDecorator decorator) {
        this.elementsFactory = elementsFactory;
        this.decorator = decorator;
    }

    public <T extends BaseComponent> T createComponent(Class<T> elementClass, Object... args) {
        T instance = (T) _commonFactory.createInstance(elementClass, args);
        instance = decorator.decorateComponent(instance, elementsFactory, this);
        return instance;
    }
}
