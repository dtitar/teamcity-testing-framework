package com.github.dtitar.teamcity;

import com.github.dtitar.teamcity.api.generators.TestDataStorage;
import org.assertj.core.api.SoftAssertions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseTest {

    protected TestDataStorage testDataStorage;
    protected SoftAssertions softly;

    @BeforeMethod
    public void beforeTest() {
        softly = new SoftAssertions();
        testDataStorage = TestDataStorage.getStorage();
    }

    @AfterMethod
    public void afterTest() {
        testDataStorage.delete();
        softly.assertAll();
    }
}
