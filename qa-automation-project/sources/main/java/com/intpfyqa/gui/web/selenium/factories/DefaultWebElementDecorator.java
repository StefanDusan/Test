package com.intpfyqa.gui.web.selenium.factories;


import com.intpfyqa.gui.web.selenium.annotations.ElementInfo;
import com.intpfyqa.gui.web.selenium.BaseAutomationPage;
import com.intpfyqa.gui.web.selenium.BaseComponent;
import com.intpfyqa.gui.web.selenium.elements.Element;
import com.intpfyqa.gui.web.selenium.elements.util.ElementFromListMatcher;
import org.apache.commons.lang3.ArrayUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ByIdOrName;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import java.lang.reflect.Field;

/**
 * Base elements decorator
 */
public class DefaultWebElementDecorator implements IElementDecorator {

    public DefaultWebElementDecorator() {
    }

    public <T extends BaseAutomationPage> T decoratePage(T instance, IElementFactory elementsFactory,
                                                         IComponentFactory componentsFactory) {
        Class currentClass = instance.getClass();
        Field[] fields = currentClass.getDeclaredFields();

        while (null != currentClass.getSuperclass() && BaseAutomationPage.class.isAssignableFrom(currentClass.getSuperclass())) {
            currentClass = currentClass.getSuperclass();
            fields = ArrayUtils.addAll(fields, currentClass.getDeclaredFields());
        }

        for (Field field : fields) {
            ElementInfo elementAnnotation = field.getAnnotation(ElementInfo.class);
            if (elementAnnotation == null) continue;
            decorateField(field, instance, elementsFactory, componentsFactory);
        }

        return instance;
    }

    public <T extends BaseComponent> T decorateComponent(T instance, IElementFactory elementsFactory,
                                                         IComponentFactory componentsFactory) {

        Class currentClass = instance.getClass();
        Field[] fields = currentClass.getDeclaredFields();

        while (null != currentClass.getSuperclass() &&
                BaseComponent.class.isAssignableFrom(currentClass.getSuperclass())) {
            currentClass = currentClass.getSuperclass();
            fields = ArrayUtils.addAll(fields, currentClass.getDeclaredFields());
        }

        for (Field field : fields) {
            ElementInfo elementAnnotation = field.getAnnotation(ElementInfo.class);
            if (elementAnnotation == null) continue;
            decorateField(field, instance, elementsFactory, componentsFactory);
        }

        return instance;
    }

    @SuppressWarnings("unchecked")
    private static void decorateField(Field field, Object instance, IElementFactory elementsFactory,
                                      IComponentFactory componentsFactory) {
        ElementInfo elementAnnotation = field.getAnnotation(ElementInfo.class);
        if (elementAnnotation == null) return;
        String name = elementAnnotation.name();
        By locator = resolveLocator(elementAnnotation.findBy());
        Object parent;

        try {
            field.setAccessible(true);
            Object fieldInstance = field.get(instance);
            if (null != fieldInstance) return;
        } catch (Exception ignore) {
        }


        if (elementAnnotation.parent().equals("") || (instance instanceof BaseComponent &&
                elementAnnotation.parent().equals("OWNER_PAGE"))) {
            if (instance instanceof BaseAutomationPage) {
                parent = instance;
            } else {
                if (instance instanceof BaseComponent) {
                    if (!elementAnnotation.parent().equals("OWNER_PAGE")) {
                        parent = instance;
                    } else {
                        parent = ((BaseComponent) instance).getPage();
                    }
                } else {
                    parent = instance;
                }
            }
        } else {
            try {
                Field field1 = findFieldInHierarchy(instance.getClass(), elementAnnotation.parent());
                if (null == field1) {
                    throw new NoSuchFieldException(elementAnnotation.parent());
                }
                field1.setAccessible(true);
                parent = field1.get(instance);
                if (null == parent) {
                    decorateField(field1, instance, elementsFactory, componentsFactory);
                    parent = field1.get(instance);
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }

        ElementFromListMatcher matcher = null;
        if (null != elementAnnotation.matcher() &&
                !elementAnnotation.matcher().clazz().getName().equals(ElementFromListMatcher.class.getName())) {
            matcher = (ElementFromListMatcher) CommonFactory.CommonFactoryBuilder
                    .withAllowedClasses(ElementFromListMatcher.class)
                    .useOnlySpecifiedArgumentsCount()
                    .build()
                    .createInstance(elementAnnotation.matcher().clazz(), elementAnnotation.matcher().args());
        }

        try {
            field.setAccessible(true);
            if (null == matcher) {
                if (Element.class.isAssignableFrom(field.getType())) {
                    field.set(instance, elementsFactory.createElement(
                            (Class<? extends Element>) field.getType(), name, locator, parent));
                } else {
                    if (null != locator) {
                        field.set(instance, WebFactoryHelper.getComponentFactory().createComponent(
                                (Class<? extends BaseComponent>) field.getType(), name, locator, parent));
                    } else {
                        field.set(instance, WebFactoryHelper.getComponentFactory().createComponent(
                                (Class<? extends BaseComponent>) field.getType(), name, parent));
                    }
                }
            } else {
                if (Element.class.isAssignableFrom(field.getType())) {
                    field.set(instance, elementsFactory.createElement(
                            (Class<? extends Element>) field.getType(), name, locator, parent, matcher));
                } else {
                    if (null != locator) {
                        field.set(instance, componentsFactory.createComponent(
                                (Class<? extends BaseComponent>) field.getType(), name, locator, parent, matcher));
                    } else {
                        field.set(instance, componentsFactory.createComponent(
                                (Class<? extends BaseComponent>) field.getType(), name, parent, matcher));
                    }
                }
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

    }

    private static Field findFieldInHierarchy(Class startFromClass, String fieldName) {
        if (null == startFromClass)
            return null;
        try {
            return startFromClass.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            return findFieldInHierarchy(startFromClass.getSuperclass(), fieldName);
        }
    }

    private static By resolveLocator(FindBy findBy) {
        By ans = buildByFromShortFindBy(findBy);
        if (ans == null) {
            ans = buildByFromLongFindBy(findBy);
        }
        return ans;
    }

    protected static By buildByFromLongFindBy(FindBy findBy) {
        How how = findBy.how();
        String using = findBy.using();

        switch (how) {
            case CLASS_NAME:
                return By.className(using);

            case CSS:
                return By.cssSelector(using);

            case ID:
                return By.id(using);
            case UNSET:
                return null;

            case ID_OR_NAME:
                return new ByIdOrName(using);

            case LINK_TEXT:
                return By.linkText(using);

            case NAME:
                return By.name(using);

            case PARTIAL_LINK_TEXT:
                return By.partialLinkText(using);

            case TAG_NAME:
                return By.tagName(using);

            case XPATH:
                return By.xpath(using);

            default:
                // Note that this shouldn't happen (eg, the above matches all
                // possible values for the How enum)
                throw new IllegalArgumentException("Cannot determine how to locate element ");
        }
    }

    protected static By buildByFromShortFindBy(FindBy findBy) {
        if (!"".equals(findBy.className()))
            return By.className(findBy.className());

        if (!"".equals(findBy.css()))
            return By.cssSelector(findBy.css());

        if (!"".equals(findBy.id()))
            return By.id(findBy.id());

        if (!"".equals(findBy.linkText()))
            return By.linkText(findBy.linkText());

        if (!"".equals(findBy.name()))
            return By.name(findBy.name());

        if (!"".equals(findBy.partialLinkText()))
            return By.partialLinkText(findBy.partialLinkText());

        if (!"".equals(findBy.tagName()))
            return By.tagName(findBy.tagName());

        if (!"".equals(findBy.xpath()))
            return By.xpath(findBy.xpath());

        // Fall through
        return null;
    }
}
