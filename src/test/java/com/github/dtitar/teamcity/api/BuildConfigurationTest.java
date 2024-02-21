package com.github.dtitar.teamcity.api;

import com.github.dtitar.teamcity.api.requests.CheckedRequests;
import com.github.dtitar.teamcity.api.spec.Specifications;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;

@Slf4j
public class BuildConfigurationTest extends BaseApiTest {

    @Test
    public void userShouldHaveRightsToCreateBuildConfiguration() {
        var testData = testDataStorage.addTestData();
        CheckedRequests checkedRequests;

        checkedWithSuperUser.getUserRequest()
                .create(testData.getUser());

        checkedRequests = new CheckedRequests(Specifications.getSpec()
                .authSpec(testData.getUser()));

        checkedRequests
                .getProjectRequest()
                .create(testData.getProject());

        var buildConfig = checkedRequests
                .getBuildTypeRequest()
                .create(testData.getBuildType());

        softly.assertThat(buildConfig.getId())
                .isEqualTo(testData.getBuildType()
                        .getId());
    }
}