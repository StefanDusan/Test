package com.intpfyqa.gui.web.selenium.factories;


import com.intpfyqa.gui.web.selenium.IParent;
import com.intpfyqa.gui.web.selenium.elements.Element;
import com.intpfyqa.gui.web.selenium.elements.util.ElementFromListMatcher;
import org.openqa.selenium.By;

/**
 * Default elements factory
 */
public class DefaultWebElementsFactory implements IElementFactory {

    public DefaultWebElementsFactory() {
    }

    private static CommonFactory _commonFactory = CommonFactory.CommonFactoryBuilder
            .withAllowedClasses(Element.class)
            .withArgumentsExpand(By.class, Element.class, IParent.class, ElementFromListMatcher.class)
            .build();

    public <T extends Element> T createElement(Class<T> elementClass, Object... args) {
        return (T) _commonFactory.createInstance(elementClass, args);
    }
}
