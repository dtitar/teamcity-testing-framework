package com.github.dtitar.teamcity.api;

import com.github.dtitar.teamcity.api.enums.RoleId;
import com.github.dtitar.teamcity.api.generators.TestDataGenerator;
import com.github.dtitar.teamcity.api.requests.CheckedRequests;
import com.github.dtitar.teamcity.api.requests.UncheckedRequests;
import com.github.dtitar.teamcity.api.spec.Specifications;
import org.apache.http.HttpStatus;
import org.testng.annotations.Test;

import static java.lang.String.format;
import static org.hamcrest.Matchers.containsString;

public class RolesTest extends BaseApiTest {

    @Test(groups = "regress")
    public void unauthorizedUserShouldNotHaveRightsToCreateProject() {
        var testData = testDataStorage.addTestData();

        new UncheckedRequests(Specifications.getSpec()
                .unauthSpec()).getProjectRequest()
                .create(testData.getProject())
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_UNAUTHORIZED)
                .body(containsString("Authentication required"));

        unCheckedWithSuperUser.getProjectRequest()
                .get(testData.getProject()
                        .getId())
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .body(containsString(format("No project found by locator 'count:1,id:%s'", testData.getProject()
                        .getId())));
    }

    @Test
    public void systemAdminShouldHaveRightsToCreateProject() {
        var testData = testDataStorage.addTestData();

        testData.getUser()
                .setRoles(TestDataGenerator.generateRoles(RoleId.SYSTEM_ADMIN, "g"));

        checkedWithSuperUser.getUserRequest()
                .create(testData.getUser());

        var project = new CheckedRequests(Specifications.getSpec()
                .authSpec(testData.getUser()))
                .getProjectRequest()
                .create(testData.getProject());

        softly.assertThat(project.getId())
                .isEqualTo(testData.getProject()
                        .getId());
    }

    @Test
    public void projectAdminShouldHaveRightsToCreateBuildConfigToHisProject() {
        var testData = testDataStorage.addTestData();

        checkedWithSuperUser.getProjectRequest()
                .create(testData.getProject());

        testData.getUser()
                .setRoles(TestDataGenerator.generateRoles(RoleId.PROJECT_ADMIN, "p:" + testData.getProject()
                        .getId()));

        checkedWithSuperUser.getUserRequest()
                .create(testData.getUser());

        var buildConfig = new CheckedRequests(Specifications.getSpec()
                .authSpec(testData.getUser()))
                .getBuildTypeRequest()
                .create(testData.getBuildType());

        softly.assertThat(buildConfig.getId())
                .isEqualTo(testData.getBuildType()
                        .getId());
    }

    @Test
    public void projectAdminShouldNotHaveRightsToCreateBuildConfigToAnotherProject() {
        var firstTestData = testDataStorage.addTestData();
        var secondTestData = testDataStorage.addTestData();

        checkedWithSuperUser.getProjectRequest()
                .create(firstTestData.getProject());

        checkedWithSuperUser.getProjectRequest()
                .create(secondTestData.getProject());

        firstTestData.getUser()
                .setRoles(TestDataGenerator
                        .generateRoles(RoleId.PROJECT_ADMIN, "p:" + firstTestData.getProject()
                                .getId()));

        checkedWithSuperUser.getUserRequest()
                .create(firstTestData.getUser());


        secondTestData.getUser()
                .setRoles(TestDataGenerator
                        .generateRoles(RoleId.PROJECT_ADMIN, "p:" + secondTestData.getProject()
                                .getId()));

        checkedWithSuperUser.getUserRequest()
                .create(secondTestData.getUser());

        new UncheckedRequests(Specifications.getSpec()
                .authSpec(secondTestData.getUser())).getBuildConfigRequest()
                .create(firstTestData.getBuildType())
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_BAD_REQUEST);
    }
}
