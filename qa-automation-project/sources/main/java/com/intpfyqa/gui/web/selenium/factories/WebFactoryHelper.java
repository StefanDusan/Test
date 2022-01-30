package com.intpfyqa.gui.web.selenium.factories;

import com.intpfyqa.settings.WebSettings;

public class WebFactoryHelper {

    public static IPageFactory getPageFactory() {
        try {
            return (IPageFactory) Class.forName(WebSettings.instance()
                    .getProperty("factory.page.class",
                            DefaultPageFactory.class.getName()))
                    .newInstance();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public static IComponentFactory getComponentFactory() {
        try {
            return (IComponentFactory) Class.forName(WebSettings.instance()
                    .getProperty("factory.component.class",
                            DefaultWebComponentsFactory.class.getName()))
                    .newInstance();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public static IElementFactory getElementFactory() {
        try {
            return (IElementFactory) Class.forName(WebSettings.instance()
                    .getProperty("factory.element.class",
                            DefaultWebElementsFactory.class.getName()))
                    .newInstance();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public static IElementDecorator getElementDecorator() {
        try {
            return (IElementDecorator) Class.forName(WebSettings.instance()
                    .getProperty("factory.decorator.class",
                            DefaultWebElementDecorator.class.getName()))
                    .newInstance();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}
