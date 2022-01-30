package com.intpfyqa.gui.web.selenium.annotations;


import com.intpfyqa.gui.web.selenium.elements.util.ElementFromListMatcher;

/**
 * Created by RakitskyAK on 23.10.2018.
 */
public @interface Matcher {

    Class<? extends ElementFromListMatcher> clazz();

    String[] args() default {};
}