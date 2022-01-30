package com.intpfy.util;

import java.util.Locale;

public final class XpathUtil {

    private static final String TRANSLATE_TEXT_TO_LOWER_CASE = "translate(.,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz')";

    public static String createEqualsTextIgnoreCaseLocator(String text) {
        return String.format("%s = '%s'", TRANSLATE_TEXT_TO_LOWER_CASE, text.toLowerCase(Locale.ROOT));
    }

    public static String createContainsTextIgnoreCaseLocator(String text) {
        return String.format("contains(%s, '%s')", TRANSLATE_TEXT_TO_LOWER_CASE, text.toLowerCase(Locale.ROOT));
    }

    private XpathUtil() {
    }
}
