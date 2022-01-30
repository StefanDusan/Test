package com.intpfyqa.gui.web.selenium;

import com.intpfyqa.logging.LogManager;
import com.intpfyqa.utils.TestUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Pattern;

public class VerifyPage<T extends IParent> {

    public static boolean ENABLED = true;
    public static boolean DISABLED = false;
    public static boolean VISIBLE = true;
    public static boolean NOT_VISIBLE = false;

    public static <T extends IParent> VerifyPage<T> onPage(T pageContext, String messageHeader) {
        return new VerifyPage<T>(pageContext, messageHeader);
    }

    private final T pageContext;
    private final String messageHeader;

    private final List<String> success = new ArrayList<>();
    private final List<String> failures = new ArrayList<>();
    private final List<String> errors = new ArrayList<>();

    private VerifyPage(T pageContext, String messageHeader) {
        this.pageContext = pageContext;
        this.messageHeader = messageHeader;
    }

    private <V> VerifyPage<T> verifyState(Function<T, V> getResult, Function<V, Boolean> checkResult, V expectedResult,
                                          String message) {
        try {
            V res = getResult.apply(pageContext);
            try {
                boolean compare = checkResult.apply(res);
                if (compare)
                    success.add(message + " Actual value is equal to expected: " + res);
                else
                    failures.add("Assertion error '" + message + "'. " +
                            "Actual value (" + res + ") is not equals to expected (" + expectedResult + ")");
            } catch (Throwable t) {
                errors.add(message + " Error occurred while comparing actual and expected value:\n" +
                        TestUtils.getThrowableShortDescription(t));
                LogManager.getCurrentTestLogger().debug(message, "Error occurred while getting actual value:\n" +
                        TestUtils.getThrowableFullDescription(t));
            }

        } catch (
                Throwable t) {
            errors.add(message + " Error occurred while getting actual value:\n" +
                    TestUtils.getThrowableShortDescription(t));
            LogManager.getCurrentTestLogger().debug(message, "Error occurred while getting actual value:\n" +
                    TestUtils.getThrowableFullDescription(t));
        }
        return this;
    }

    public <V> VerifyPage<T> doubleValueShouldBeMore(Function<T, Double> function, final double expectedValue, String message) {
        return verifyState(function, (res) -> res > expectedValue, expectedValue, message);
    }

    public <V> VerifyPage<T> shouldBeEquals(Function<T, V> func, final V expectedResult, String message) {
        return verifyState(func, (res) -> {
            if (null == res && null == expectedResult) {
                return true;
            } else if (null == res || null == expectedResult) {
                return false;
            } else {
                boolean compare;
                if (res instanceof Enum && expectedResult instanceof Enum)
                    compare = res == expectedResult;
                else if (res instanceof String && expectedResult instanceof String)
                    compare = ((String) res).equalsIgnoreCase((String) expectedResult);
                else
                    compare = res.equals(expectedResult);
                return compare;
            }

        }, expectedResult, message);
    }

    public <V> VerifyPage<T> shouldNotBeEquals(Function<T, V> func, final V expectedResult, String message) {
        return verifyState(func, (res) -> {
            if (null == res && null == expectedResult) {
                return false;
            } else if (null == res || null == expectedResult) {
                return true;
            } else {
                boolean compare;
                if (res instanceof Enum && expectedResult instanceof Enum)
                    compare = res == expectedResult;
                else if (res instanceof String && expectedResult instanceof String)
                    compare = ((String) res).equalsIgnoreCase((String) expectedResult);
                else
                    compare = res.equals(expectedResult);
                return !compare;
            }

        }, expectedResult, message);
    }

    public VerifyPage<T> stringValueShouldBeEqual(Function<T, String> function, String
            expectedResult, String message) {
        return shouldBeEquals(function, expectedResult, message);
    }

    public VerifyPage<T> intValueShouldBeEqual(Function<T, Integer> function, int
            expectedResult, String message) {
        return shouldBeEquals(function, expectedResult, message);
    }

    public VerifyPage<T> doubleValueShouldBeEqual(Function<T, Double> function, double
            expectedResult, String message) {
        return shouldBeEquals(function, expectedResult, message);
    }

    public VerifyPage<T> booleanValueShouldBeEqual(Function<T, Boolean> function, boolean
            expectedResult, String message) {
        return shouldBeEquals(function, expectedResult, message);
    }

    public VerifyPage<T> stringValueShouldNotBeEqual(Function<T, String> function, String
            expectedResult, String message) {
        return shouldNotBeEquals(function, expectedResult, message);
    }

    public VerifyPage<T> intValueShouldNotBeEqual(Function<T, Integer> function, int
            expectedResult, String message) {
        return shouldNotBeEquals(function, expectedResult, message);
    }

    public VerifyPage<T> booleanValueShouldNotBeEqual(Function<T, Boolean> function, boolean
            expectedResult, String message) {
        return shouldNotBeEquals(function, expectedResult, message);
    }

    public VerifyPage<T> stringValueShouldMatch(Function<T, String> function, Pattern
            expectedResult, String message) {
        return verifyState(function, (res) -> {
            if (null == res && null == expectedResult) {
                return true;
            } else if (null == res || null == expectedResult) {
                return false;
            } else {
                return res.matches(expectedResult.pattern());
            }

        }, expectedResult.pattern(), message);
    }

    public VerifyPage<T> stringValueShouldNotMatch(Function<T, String> function, Pattern
            expectedResult, String message) {
        return verifyState(function, (res) -> {
            if (null == res && null == expectedResult) {
                return false;
            } else if (null == res || null == expectedResult) {
                return true;
            } else {
                return !res.matches(expectedResult.pattern());
            }

        }, expectedResult.pattern(), message);
    }

    public VerifyPage<T> stringValueShouldContain(Function<T, String> function, String
            expectedResult, String message) {
        return verifyState(function, (res) -> {
            if (null == res && null == expectedResult) {
                return true;
            } else if (null == res || null == expectedResult) {
                return false;
            } else {
                return res.toLowerCase().contains(expectedResult.toLowerCase());
            }

        }, expectedResult, message);
    }

    public VerifyPage<T> stringValueShouldNotContain(Function<T, String> function, String
            expectedResult, String message) {
        return verifyState(function, (res) -> {
            if (null == res && null == expectedResult) {
                return true;
            } else if (null == res || null == expectedResult) {
                return false;
            } else {
                return !res.toLowerCase().contains(expectedResult.toLowerCase());
            }

        }, expectedResult, message);
    }

    public VerifyPage<T> stringValueShouldStartWith(Function<T, String> function, String
            expectedResult, String message) {
        return verifyState(function, (res) -> {
            if (null == res && null == expectedResult) {
                return true;
            } else if (null == res || null == expectedResult) {
                return false;
            } else {
                return res.toLowerCase().startsWith(expectedResult.toLowerCase());
            }

        }, expectedResult, message);
    }

    public VerifyPage<T> stringValueShouldNotStartWith(Function<T, String> function, String
            expectedResult, String message) {
        return verifyState(function, (res) -> {
            if (null == res && null == expectedResult) {
                return true;
            } else if (null == res || null == expectedResult) {
                return false;
            } else {
                return !res.toLowerCase().startsWith(expectedResult.toLowerCase());
            }

        }, expectedResult, message);
    }

    public VerifyPage<T> stringValueShouldEndWith(Function<T, String> function, String
            expectedResult, String message) {
        return verifyState(function, (res) -> {
            if (null == res && null == expectedResult) {
                return true;
            } else if (null == res || null == expectedResult) {
                return false;
            } else {
                return !res.toLowerCase().endsWith(expectedResult.toLowerCase());
            }

        }, expectedResult, message);
    }

    public VerifyPage<T> stringValueShouldNotEndWith(Function<T, String> function, String
            expectedResult, String message) {
        return verifyState(function, (res) -> {
            if (null == res && null == expectedResult) {
                return true;
            } else if (null == res || null == expectedResult) {
                return false;
            } else {
                return !res.toLowerCase().endsWith(expectedResult.toLowerCase());
            }

        }, expectedResult, message);
    }

    public VerifyPage<T> stringValueShouldBeEmpty(Function<T, String> function, String message) {
        return verifyState(function, (res) -> {
            if (null == res) {
                return true;
            } else {
                return res.trim().isEmpty();
            }

        }, "", message);
    }

    public VerifyPage<T> stringValueShouldNotBeEmpty(Function<T, String> function, String message) {
        return verifyState(function, (res) -> {
            if (null == res) {
                return false;
            } else {
                return !res.trim().isEmpty();
            }

        }, "Not empty value", message);
    }

    public VerifyPage<T> fieldEnabledStateShouldBe(Function<T, Boolean> function, boolean expected, String fieldName) {
        return verifyState(function, (res) -> {
            if (null == res) {
                return false;
            } else {
                return res == expected;
            }

        }, expected, "Field " + fieldName + " should be " + (expected ? "enabled" : "disabled") +
                " for editing");
    }

    public VerifyPage<T> fieldVisibilityShouldBe(Function<T, Boolean> function, boolean expected, String fieldName) {
        return verifyState(function, (res) -> {
            if (null == res) {
                return false;
            } else {
                return res == expected;
            }

        }, expected, "Field " + fieldName + " should be " + (expected ? "visible" : "not visible"));
    }

    private void done(boolean treatFailuresAsError) {
        String successMessage = StringUtils.join(success, "\n\t");
        String failMessage = StringUtils.join(failures, "\n\t");
        if (!errors.isEmpty()) {
            failMessage += "\n\t" + StringUtils.join(errors, "\n\t");
        }

        if (!failures.isEmpty() || !errors.isEmpty()) {
            try {
                LogManager.getCurrentTestLogger().debug(messageHeader + " PAGE SOURCE",
                        pageContext.getPage().getPageContext().getBrowserWindow().getPageSource());
            } catch (Exception ignore) {

            }
        }

        if (failures.isEmpty() && errors.isEmpty()) {
            LogManager.getCurrentTestLogger().pass(messageHeader,
                    "All check are successful\n\t" + successMessage, pageContext.getPage()
                            .getPageContext().getBrowserWindow());
        } else {
            if (!successMessage.isEmpty())
                LogManager.getCurrentTestLogger().pass(messageHeader,
                        successMessage);
            if (!errors.isEmpty() || treatFailuresAsError) {
                LogManager.getCurrentTestLogger().error(messageHeader,
                        failMessage, pageContext.getPage()
                                .getPageContext().getBrowserWindow());
            } else {
                LogManager.getCurrentTestLogger().fail(messageHeader,
                        failMessage, pageContext.getPage()
                                .getPageContext().getBrowserWindow());
            }
        }
    }

    public void completeVerify() {
        done(false);
    }

    public void completeAssert() {
        done(true);
    }
}