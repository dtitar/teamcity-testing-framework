package com.github.dtitar.teamcity.api.generators;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.RandomStringUtils;

@UtilityClass
public class RandomData {
    private static final int START_POSITION_IN_SET_OF_CHARS = 0x0400;
    private static final int END_POSITION_IN_SET_OF_CHARS = 0x04FF;

    private static final int LENGTH = 10;

    public static String generateString() {
        return "test_" + RandomStringUtils.randomAlphabetic(LENGTH);
    }

    public static String generateString(int length) {
        return RandomStringUtils.randomAlphabetic(length);
    }

    public static String generateNumericString() {
        return RandomStringUtils.randomNumeric(LENGTH);
    }

    public static String generateNonLatinString() {

        return "test_" + RandomStringUtils.random(LENGTH, START_POSITION_IN_SET_OF_CHARS, END_POSITION_IN_SET_OF_CHARS,
                true, true);
    }
}
