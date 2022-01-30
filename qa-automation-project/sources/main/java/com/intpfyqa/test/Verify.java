package com.intpfyqa.test;

import com.intpfyqa.logging.ITakeSnapshot;
import com.intpfyqa.logging.ITestLogger;
import com.intpfyqa.logging.LogManager;
import com.intpfyqa.run.RunSession;
import com.intpfyqa.run.RunSessions;
import org.apache.commons.lang3.StringUtils;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Verifications and assertions
 */
public class Verify {

    private static final String ASSERT_HEADER = "Assertion";
    private static final String VERIFY_HEADER = "Verification";

    private static ITakeSnapshot createSnapshotIfPossible() {
        try {
            RunSession session = RunSessions.current();
            if (null != session.getCurrentContext())
                return session.getCurrentContext().getTakeSnapshot();
            return null;
        } catch (Throwable ignore) {
            return null;
        }
    }

    private static ITestLogger getLogger() {
        return LogManager.getCurrentTestLogger();
    }

    public static void assertTrue(Boolean condition, String message) {

        try {
            Assert.assertTrue(condition);
            getLogger().pass(ASSERT_HEADER, message, createSnapshotIfPossible());
        } catch (AssertionError e) {
            getLogger().error(ASSERT_HEADER, "Assertion failed '" + message + "'\n" + e.getMessage(),
                    createSnapshotIfPossible());
        }
    }

    public static void assertFalse(Boolean condition, String message) {
        try {
            Assert.assertFalse(condition);
            getLogger().pass(ASSERT_HEADER, message, createSnapshotIfPossible());
        } catch (AssertionError e) {
            getLogger().error(ASSERT_HEADER, "Assertion failed '" + message + "'\n" + e.getMessage(),
                    createSnapshotIfPossible());
        }
    }

    public static void assertContains(String actual, String expected, String message) {
        if (StringUtils.containsIgnoreCase(actual, expected)) {
            getLogger().pass(ASSERT_HEADER, message, createSnapshotIfPossible());
        } else {
            getLogger().error(ASSERT_HEADER, "Assertion failed '" + message +
                            "'\nActual value " + actual + " does not contain " + expected,
                    createSnapshotIfPossible());
        }
    }

    public static boolean verifyTrue(Boolean condition, String message) {
        try {
            Assert.assertTrue(condition);
            getLogger().pass(VERIFY_HEADER, message, createSnapshotIfPossible());
            return true;
        } catch (AssertionError e) {
            getLogger().fail(VERIFY_HEADER, message + "\n" + e.getMessage(),
                    createSnapshotIfPossible());
            return false;
        }
    }

    public static boolean verifyFalse(Boolean condition, String message) {
        try {
            Assert.assertFalse(condition);
            getLogger().pass(VERIFY_HEADER, message, createSnapshotIfPossible());
            return true;
        } catch (AssertionError e) {
            getLogger().fail(VERIFY_HEADER, "Verification failed '" + message + "'\n" + e.getMessage(),
                    createSnapshotIfPossible());
            return false;
        }
    }

    public static void assertEquals(Object actual, Object expected, String message) {
        try {
            Assert.assertEquals(actual, expected);
            getLogger().pass(ASSERT_HEADER, message + ". Actual value: " + actual,
                    createSnapshotIfPossible());
        } catch (AssertionError e) {
            getLogger().error(ASSERT_HEADER, "Assertion failed '" + message + "'\n" + e.getMessage(),
                    createSnapshotIfPossible());
        }
    }

    public static void assertNotEquals(Object actual, Object expected, String message) {
        try {
            Assert.assertNotEquals(actual, expected);
            getLogger().pass(ASSERT_HEADER, message + ". Actual value: " + actual + " (is not equal to " + expected + ")",
                    createSnapshotIfPossible());
        } catch (AssertionError e) {
            getLogger().error(ASSERT_HEADER, "Assertion failed '" + message + "\n" + "Expected that value is not equal to " +
                            "[" + expected + "], but received [" + actual + "]",
                    createSnapshotIfPossible());
        }
    }

    public static boolean verifyEquals(Object actual, Object expected, String message) {
        try {
            Assert.assertEquals(actual, expected);
            getLogger().pass(VERIFY_HEADER, message + ". Actual value: " + actual,
                    createSnapshotIfPossible());
            return true;
        } catch (AssertionError e) {
            getLogger().fail(VERIFY_HEADER, "Verification failed '" + message + "'\n" + e.getMessage(),
                    createSnapshotIfPossible());
            return false;
        }
    }

    public static boolean verifyNotEquals(Object actual, Object expected, String message) {
        try {
            Assert.assertNotEquals(actual, expected);
            getLogger().pass(VERIFY_HEADER, message + ". Actual value: " + actual,
                    createSnapshotIfPossible());
            return true;
        } catch (AssertionError e) {
            getLogger().fail(VERIFY_HEADER, "Verification failed '" + message + "'.\n" +
                            "Expected that actual value is not equal to [" + expected + "], but received [" + expected + "]",
                    createSnapshotIfPossible());
            return false;
        }
    }

    public static boolean verifyEqualsIgnoreOrder(Collection actual, Collection expected, String message) {
        List notFound = new ArrayList();
        List unExpected = new ArrayList();

        notFound.addAll(expected);
        unExpected.addAll(actual);

        for (Object expectedItem : expected) {
            if (actual.contains(expectedItem)) {
                notFound.remove(expectedItem);
                unExpected.remove(expectedItem);
            }
        }

        if (notFound.isEmpty() && unExpected.isEmpty()) {
            getLogger().pass(VERIFY_HEADER, message + ". Actual value:\n" + StringUtils.join(actual, "\n"),
                    createSnapshotIfPossible());
            return true;
        } else {
            getLogger().fail(VERIFY_HEADER, "Verification failed '" + message + "'\n" +
                            (!notFound.isEmpty() ? "\tExpected list items are not found:\n" + StringUtils.join(notFound, "\n") : "") +
                            (!unExpected.isEmpty() ? "\tItems that are not in the expected list:\n" + StringUtils.join(unExpected, "\n") : ""),
                    createSnapshotIfPossible());
            return false;
        }
    }

    public static boolean verifyEquals(Collection actual, Collection expected, String message) {
        try {
            Assert.assertEquals(actual, expected);
            getLogger().pass(VERIFY_HEADER, message + ". Actual value: " + actual,
                    createSnapshotIfPossible());
            return true;
        } catch (AssertionError e) {
            getLogger().fail(VERIFY_HEADER, "Verification failed '" + message + "\n" + e.getMessage(),
                    createSnapshotIfPossible());
            return false;
        }
    }

    public static boolean verifyEquals(int actual, int expected, String message) {
        try {
            Assert.assertEquals(actual, expected);
            getLogger().pass(VERIFY_HEADER, message + ". Actual value: " + actual,
                    createSnapshotIfPossible());
            return true;
        } catch (AssertionError e) {
            getLogger().fail(VERIFY_HEADER, "Verification failed '" + message + "\n" + e.getMessage(),
                    createSnapshotIfPossible());
            return false;
        }
    }

    public static boolean verifyMoreThanOrEqualsTo(int actual, int expected, String message) {
        if (actual >= expected) {
            getLogger().pass(VERIFY_HEADER, message + ". Actual value: " + actual,
                    createSnapshotIfPossible());
            return true;
        } else {
            getLogger().fail(VERIFY_HEADER, "Verification failed '" + message + "\n" +
                            "Actual value " + actual + "\nExpected less than " + expected,
                    createSnapshotIfPossible());
            return false;
        }
    }

    /**
     * asserts if text matched
     *
     * @param current  current text
     * @param expected expected message
     * @param message  messsage to log
     * @return true on success
     */
    public static boolean verifyTextIgnoreCase(String current, String expected, String message) {
        try {
            Assert.assertEquals(current.toLowerCase(), expected.toLowerCase());
            getLogger().pass(VERIFY_HEADER, message + ". Actual value: " + current,
                    createSnapshotIfPossible());
            return true;
        } catch (AssertionError e) {
            getLogger().fail(VERIFY_HEADER, "Verification failed '" + message + "\n" + e.getMessage(),
                    createSnapshotIfPossible());
            return false;
        }
    }

    public static boolean verifyArrays(String[] actual, String[] expected, boolean ignoreOrder, String message) {
        List<String> errors = new ArrayList<>();

        if (!ignoreOrder) {
            for (int i = 0; i < actual.length; i++) {
                if (expected.length <= i) break;
                if (!actual[i].equalsIgnoreCase(expected[i])) {
                    errors.add("Item #" + (i + 1) + " does not match expected. Actual value '" + actual[i] + "', " +
                            "expected: '" + expected[i] + "'");
                }
            }
        } else {
            List<String> notFound = new ArrayList<>();
            List<String> unknown = new ArrayList<>();
            List<String> allActual = new ArrayList<>();
            notFound.addAll(Arrays.asList(expected));
            unknown.addAll(Arrays.asList(actual));
            allActual.addAll(Arrays.asList(actual));

            for (String expectedItem : expected) {
                String toRemove = null;
                for (String actualItem : allActual) {
                    if (expectedItem.equalsIgnoreCase(actualItem)) {
                        toRemove = actualItem;
                        notFound.remove(notFound.indexOf(expectedItem));
                        unknown.remove(unknown.indexOf(actualItem));
                        break;
                    }
                }

                if (toRemove != null) allActual.remove(allActual.indexOf(toRemove));
            }

            for (String notFoundItem : notFound) {
                errors.add("Item '" + notFoundItem + "' from expected list is not found in actual list");
            }

            for (String unknownItem : unknown) {
                errors.add("Item '" + unknownItem + "' is present in actual list, but missing in expected");
            }
        }

        if (expected.length != actual.length) {
            errors.add("Quantity of elements in actual list does not match expected. Actual quantity '" + actual.length + ", " +
                    "expected '" + expected.length + "'");
        }

        if (errors.size() > 0) {
            getLogger().fail(VERIFY_HEADER, "Verification failed '" + message + "\nActual list:\n" + StringUtils.join(actual, ", ") +
                            "\nExpected list:\n" + StringUtils.join(expected, ", ") +
                            "\n\n" + StringUtils.join(errors, "\n"),
                    createSnapshotIfPossible());
        } else {
            getLogger().pass(VERIFY_HEADER, message + "\n" + StringUtils.join(actual, "\n"),
                    createSnapshotIfPossible());
        }

        return errors.size() == 0;
    }


    public static boolean verifyMatches(String actual, Pattern expected, String message) {
        if (actual.matches(expected.pattern())) {
            getLogger().pass(VERIFY_HEADER, message + ". Actual value: " + actual,
                    createSnapshotIfPossible());
            return true;
        } else {
            getLogger().fail(VERIFY_HEADER, "Verification failed '" + message + ". Actual value: " + actual +
                            "\nExpected mask: " + expected.pattern(),
                    createSnapshotIfPossible());
            return false;
        }
    }

    public static boolean verifyListContains(List bigList, List expectedToContains, String message) {
        List notFound = new ArrayList();
        notFound.addAll(expectedToContains);

        List actuals = new ArrayList();
        actuals.addAll(bigList);

        for (Object expectedItem : expectedToContains) {
            if (actuals.contains(expectedItem)) {
                notFound.remove(expectedItem);
                actuals.remove(expectedItem);
            }
        }

        if (notFound.isEmpty()) {
            getLogger().pass(VERIFY_HEADER, message + ". Actual list contains all required values\n" +
                            StringUtils.join(expectedToContains, "\n"),
                    createSnapshotIfPossible());
            return true;
        } else {
            getLogger().fail(VERIFY_HEADER, "Verification failed '" + message + ". " +
                            "Actual list does not contain values listed below\n" +
                            StringUtils.join(notFound, "\n"),
                    createSnapshotIfPossible());
            return false;
        }
    }

    public static boolean verifyListNotContain(List bigList, List expectedToNotContains, String message) {
        List found = new ArrayList();
        found.addAll(expectedToNotContains);

        List actuals = new ArrayList();
        actuals.addAll(bigList);

        for (Object expectedItem : expectedToNotContains) {
            if (!actuals.contains(expectedItem)) {
                found.remove(expectedItem);
                actuals.remove(expectedItem);
            }
        }

        if (found.isEmpty()) {
            getLogger().pass(VERIFY_HEADER, message + ". Actual list contains none of listed values\n" +
                            StringUtils.join(expectedToNotContains, "\n"),
                    createSnapshotIfPossible());
            return true;
        } else {
            getLogger().fail(VERIFY_HEADER, "Verification failed '" + message + ". " +
                            "Actual list contains some of the prohibited values\n" +
                            StringUtils.join(found, "\n"),
                    createSnapshotIfPossible());
            return false;
        }
    }
}
