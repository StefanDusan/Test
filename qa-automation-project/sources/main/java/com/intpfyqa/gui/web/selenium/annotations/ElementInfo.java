package com.intpfyqa.gui.web.selenium.annotations;

import com.intpfyqa.gui.web.selenium.elements.util.ElementFromListMatcher;
import org.openqa.selenium.support.FindBy;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Decorate instructions for element
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface ElementInfo {

    /**
     * Logical name
     *
     * @return Logical name of element
     */
    String name();

    /**
     * Locator
     *
     * @return locator
     */
    FindBy findBy() default @FindBy();

    /**
     * Parent of element. By default instance where element is declared
     * @return Parent of element
     */
    String parent() default "";

    /**
     * Matcher for this element location
     * @return Matcher for this element location
     */
    Matcher matcher() default @Matcher(clazz = ElementFromListMatcher.class);
}