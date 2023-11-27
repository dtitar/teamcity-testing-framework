package com.github.dtitar.teamcity.api;

import com.github.dtitar.teamcity.api.requests.checked.CheckedProject;
import com.github.dtitar.teamcity.api.requests.checked.CheckedUser;
import com.github.dtitar.teamcity.api.spec.Specifications;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;

@Slf4j
public class BuildConfigurationTest extends BaseApiTest {
    @Test
    public void buildConfigurationTest() {
        var testData = testDataStorage.addTestData();

        new CheckedUser(Specifications.getSpec()
                .superUserSpec()).create(testData.getUser());

        var project = new CheckedProject(Specifications.getSpec()
                .authSpec(testData.getUser())).create(testData.getProject());

        softly.assertThat(project.getId())
                .isEqualTo(testData.getProject()
                        .getId());
    }
}