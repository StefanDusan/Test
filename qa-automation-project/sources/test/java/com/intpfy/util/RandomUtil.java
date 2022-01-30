package com.intpfy.util;

import org.apache.commons.lang3.RandomStringUtils;

public final class RandomUtil {

    private static final int DEFAULT_LENGTH = 10;

    private RandomUtil() {
    }

    public static String getRandomNumericStringWithPrefix(String prefix) {
        return getRandomNumericStringWithPrefix(prefix, DEFAULT_LENGTH);
    }

    public static String getRandomNumericStringWithPrefix(String prefix, int length) {
        return prefix + RandomStringUtils.randomNumeric(length);
    }

    public static String createRandomChatMessage() {
        String charactersPool = RandomStringUtils.randomAlphanumeric(10) + "!@#$%^&*()";
        return RandomStringUtils.random(charactersPool.length(), charactersPool);
    }

    public static String createRandomAudienceName() {
        return RandomUtil.getRandomNumericStringWithPrefix("A-");
    }

    public static String createRandomInterpreterName() {
        return RandomUtil.getRandomNumericStringWithPrefix("I-");
    }

    public static String createRandomSpeakerName() {
        return RandomUtil.getRandomNumericStringWithPrefix("S-");
    }

    public static String createRandomHostName() {
        return RandomUtil.getRandomNumericStringWithPrefix("H-");
    }

    public static String createRandomModeratorName() {
        return RandomUtil.getRandomNumericStringWithPrefix("M-");
    }

    public static String createRandomDomainName() {
        return RandomStringUtils.randomNumeric(DEFAULT_LENGTH);
    }
}
