package com.github.dtitar.teamcity.api;

import com.github.dtitar.teamcity.BaseTest;
import com.github.dtitar.teamcity.api.generators.TestData;
import com.github.dtitar.teamcity.api.generators.TestDataGenerator;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseApiTest extends BaseTest {
    TestData testdata;

    @BeforeMethod
    public void setupTest() {
        testdata = new TestDataGenerator().generate();
    }

    @AfterMethod
    public void cleanTest() {
        testdata.delete();
    }
}
