package com.github.dtitar.teamcity.api.generators;

import java.util.ArrayList;
import java.util.List;

public final class TestDataStorage {
    private static TestDataStorage testDataStorage;
    private List<TestData> testDataList;

    private TestDataStorage() {
        this.testDataList = new ArrayList<>();
    }

    public static TestDataStorage getStorage() {
        if (null == testDataStorage) {
            testDataStorage = new TestDataStorage();
        }
        return testDataStorage;
    }

    public TestData addTestData() {
        var testData = TestDataGenerator.generate();
        addTestData(testData);
        return testData;
    }

    public TestData addTestData(TestData testData) {
        getStorage().testDataList.add(testData);
        return testData;
    }

    public void delete() {
        testDataList.forEach(TestData::delete);
    }
}
