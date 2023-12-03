package com.github.dtitar.teamcity.api;

import com.github.dtitar.teamcity.api.models.Project;
import com.github.dtitar.teamcity.api.requests.CheckedRequests;
import com.github.dtitar.teamcity.api.requests.UncheckedRequests;
import com.github.dtitar.teamcity.api.spec.Specifications;
import org.apache.http.HttpStatus;
import org.testng.annotations.Test;

import static java.lang.String.format;
import static org.hamcrest.Matchers.containsString;

public class CreateProjectTest extends BaseApiTest {

    @Test
    public void requestShouldCreateNewProject() {
        var testData = testDataStorage.addTestData();

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
    public void requestWithMissingParentProjectShouldCreateProjectWithRootParentProject() {
        var testData = testDataStorage.addTestData();
        testData.getProject().setParentProject(null);

        checkedWithSuperUser.getUserRequest()
                .create(testData.getUser());

        var project = new CheckedRequests(Specifications.getSpec()
                .authSpec(testData.getUser()))
                .getProjectRequest()
                .create(testData.getProject());

        softly.assertThat(project.getId())
                .isEqualTo(testData.getProject()
                        .getId());
        softly.assertThat(project.getParentProjectId())
                .isEqualTo("_Root");
    }

    @Test
    public void requestWithCustomParentProjectShouldCreateProjectWithThisCustomParentProject() {
        var testData = testDataStorage.addTestData();
        var parentProjectTestData = testDataStorage.addTestData();

        checkedWithSuperUser.getUserRequest()
                .create(testData.getUser());

        var parentProject = new CheckedRequests(Specifications.getSpec()
                .authSpec(testData.getUser()))
                .getProjectRequest()
                .create(parentProjectTestData.getProject());

        testData.getProject()
                .setParentProject(parentProject);

        var project = new CheckedRequests(Specifications.getSpec()
                .authSpec(testData.getUser()))
                .getProjectRequest()
                .create(testData.getProject());

        softly.assertThat(project.getId())
                .isEqualTo(testData.getProject()
                        .getId());
        softly.assertThat(project.getParentProjectId())
                .isEqualTo(parentProjectTestData.getProject()
                        .getId());
    }

    @Test
    public void requestWithNonExistentParentProjectShouldReturnError() {
        var testData = testDataStorage.addTestData();
        testData.getProject()
                .setParentProject(Project.builder()
                        .locator("NonExistentParentProject")
                        .build());

        checkedWithSuperUser.getUserRequest()
                .create(testData.getUser());

        new UncheckedRequests(Specifications.getSpec()
                .authSpec(testData.getUser()))
                .getProjectRequest()
                .create(testData.getProject())
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .body(containsString(format("No project found by name or internal/external id '%s'", testData.getProject()
                        .getParentProject()
                        .getLocator())));
    }

    @Test
    public void requestWithMissingProjectNameShouldReturnError() {
        var testData = testDataStorage.addTestData();
        testData.getProject()
                .setName("");

        checkedWithSuperUser.getUserRequest()
                .create(testData.getUser());

        new UncheckedRequests(Specifications.getSpec()
                .authSpec(testData.getUser()))
                .getProjectRequest()
                .create(testData.getProject())
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body(containsString("Project name cannot be empty"));
    }

    @Test
    public void requestWithMissingProjectIdShouldReturnError() {
        var testData = testDataStorage.addTestData();
        testData.getProject()
                .setId("");

        checkedWithSuperUser.getUserRequest()
                .create(testData.getUser());

        new UncheckedRequests(Specifications.getSpec()
                .authSpec(testData.getUser()))
                .getProjectRequest()
                .create(testData.getProject())
                .then()
                .assertThat()
                //returns 500 (Internal Server Error) but expected 400, looks like a bug
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body(containsString("Project ID must not be empty"));
    }
}
