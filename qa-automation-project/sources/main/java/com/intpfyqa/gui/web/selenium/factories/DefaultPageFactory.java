package com.intpfyqa.gui.web.selenium.factories;


import com.intpfyqa.gui.web.selenium.BaseAutomationPage;
import com.intpfyqa.gui.web.selenium.IPageContext;

/**
 * Base page factory
 */
public class DefaultPageFactory implements IPageFactory {

    private static CommonFactory _commonFactory = CommonFactory.CommonFactoryBuilder
            .withAllowedClasses(BaseAutomationPage.class)
            .withArgumentsExpand(BaseAutomationPage.class, IPageContext.class)
            .build();

    private final IElementFactory useElementsFactory;
    private final IElementDecorator decorator;
    private final IComponentFactory webComponentsFactory;

    public DefaultPageFactory() {
        this(WebFactoryHelper.getElementFactory(), WebFactoryHelper.getElementDecorator(),
                WebFactoryHelper.getComponentFactory());
    }

    public DefaultPageFactory(IElementFactory useFactory,
                              IElementDecorator decorator,
                              IComponentFactory webComponentsFactory) {
        this.useElementsFactory = useFactory;
        this.decorator = decorator;
        this.webComponentsFactory = webComponentsFactory;
    }

    public <T extends BaseAutomationPage> T createPage(Class<T> pageClass, Object... args) {
        T instance = (T) _commonFactory.createInstance(pageClass, args);
        instance = decorator.decoratePage(instance, useElementsFactory, webComponentsFactory);
        return instance;
    }
}
