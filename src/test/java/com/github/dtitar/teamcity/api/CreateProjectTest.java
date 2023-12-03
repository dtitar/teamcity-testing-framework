package com.github.dtitar.teamcity.api;

import com.github.dtitar.teamcity.api.generators.RandomData;
import com.github.dtitar.teamcity.api.models.Project;
import com.github.dtitar.teamcity.api.requests.CheckedRequests;
import com.github.dtitar.teamcity.api.requests.UncheckedRequests;
import com.github.dtitar.teamcity.api.spec.Specifications;
import org.apache.http.HttpStatus;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static com.github.dtitar.teamcity.api.generators.TestData.MAX_PROJECT_ID_LENGTH;
import static com.github.dtitar.teamcity.api.generators.TestData.MAX_PROJECT_NAME_LENGTH;
import static com.github.dtitar.teamcity.api.generators.TestData.MIN_PROJECT_ID_LENGTH;
import static com.github.dtitar.teamcity.api.generators.TestData.MIN_PROJECT_NAME_LENGTH;
import static java.lang.String.format;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsString;

public class CreateProjectTest extends BaseApiTest {

    @DataProvider(name = "positiveProjectNames")
    public Object[][] positiveProjectNames() {
        return new Object[][]{
                {RandomData.generateString() + RandomData.generateNumericString()},
                {RandomData.generateString(MIN_PROJECT_NAME_LENGTH)},
                {RandomData.generateString()},
                {RandomData.generateString(MAX_PROJECT_NAME_LENGTH)},
                // По хорошему все эти значения не должны проходить, добавил в положительные, как и договорились в чате
                {"!@#$%^"},
                {"'; DROP TABLE users; --"},
                {"<script>alert(\"XSS\");</script>"},
                {"<img src=\"x\" onerror=\"alert('Injected!')\">"},
                {"../../../../etc/passwd"},
                {"; ls /"},
                {"%0A%0D"},
                {"../"},
        };
    }

    @Test(dataProvider = "positiveProjectNames")
    public void positiveProjectNamesTest(String projectName) {
        var testData = testDataStorage.addTestData();
        testData.getProject()
                .setName(projectName);

        checkedWithSuperUser.getUserRequest()
                .create(testData.getUser());

        var project = new CheckedRequests(Specifications.getSpec()
                .authSpec(testData.getUser()))
                .getProjectRequest()
                .create(testData.getProject());

        softly.assertThat(project.getId())
                .isEqualTo(testData.getProject()
                        .getId());
        softly.assertThat(project.getName())
                .isEqualTo(testData.getProject()
                        .getName());
    }

    @DataProvider(name = "negativeProjectNames")
    public Object[][] negativeProjectNames() {
        return new Object[][]{
                {"", "Project name cannot be empty"},
                // Bug. Api doesn't validate project names with the length more than 80 symbols. Frontend cut all project names to 80 symbols max
                {RandomData.generateString(MAX_PROJECT_NAME_LENGTH + 1), "Project name should have length no more than 80 symbols"} //example error message
        };
    }


    @Test(dataProvider = "negativeProjectNames")
    public void negativeProjectNamesTest(String projectName, String errorMessage) {
        var testData = testDataStorage.addTestData();
        testData.getProject()
                .setName(projectName);

        checkedWithSuperUser.getUserRequest()
                .create(testData.getUser());

        new UncheckedRequests(Specifications.getSpec()
                .authSpec(testData.getUser()))
                .getProjectRequest()
                .create(testData.getProject())
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body(containsString(errorMessage));
    }

    @Test
    public void requestWithAlreadyExistentProjectNameShouldReturnError() {
        var firstTestData = testDataStorage.addTestData();
        var secondTestData = testDataStorage.addTestData();

        checkedWithSuperUser.getUserRequest()
                .create(firstTestData.getUser());

        var firstProject = new CheckedRequests(Specifications.getSpec()
                .authSpec(firstTestData.getUser()))
                .getProjectRequest()
                .create(firstTestData.getProject());

        secondTestData.getProject()
                .setName(firstProject.getName());


        new UncheckedRequests(Specifications.getSpec()
                .authSpec(firstTestData.getUser()))
                .getProjectRequest()
                .create(secondTestData.getProject())
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body(containsString(format("Project with this name already exists: %s", secondTestData.getProject()
                        .getName())));
    }

    @Test
    public void requestWithEmptyParentProjectLocatorShouldReturnError() {
        var testData = testDataStorage.addTestData();
        testData.getProject()
                .setParentProject(Project.builder()
                        .locator("")
                        .build());

        checkedWithSuperUser.getUserRequest()
                .create(testData.getUser());

        new UncheckedRequests(Specifications.getSpec()
                .authSpec(testData.getUser()))
                .getProjectRequest()
                .create(testData.getProject())
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body(containsString("No project specified. Either 'id', 'internalId' or 'locator' attribute should be present."));
    }

    @Test
    public void requestShouldCreateProjectWithProvidedParentProject() {
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

    @DataProvider(name = "positiveProjectId")
    public Object[][] positiveProjectId() {
        return new Object[][]{
                {RandomData.generateString(MIN_PROJECT_ID_LENGTH)},
                {RandomData.generateString()},
                {RandomData.generateString(MAX_PROJECT_ID_LENGTH)}
        };
    }

    @Test(dataProvider = "positiveProjectId")
    public void positiveProjectIdTest(String projectId) {
        var testData = testDataStorage.addTestData();
        testData.getProject()
                .setId(projectId);

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

    @DataProvider(name = "negativeProjectId")
    public Object[][] negativeProjectId() {
        return new Object[][]{
                {"", "Project ID must not be empty"},
                {RandomData.generateNumericString() + RandomData.generateString(), "is invalid: starts with non-letter character"},
                {RandomData.generateString(MAX_PROJECT_ID_LENGTH + 1), "characters long while the maximum length is 225"},
                {RandomData.generateNonLatinString(), "is invalid: contains non-latin letter"},
                {RandomData.generateString() + "$", "is invalid: contains unsupported character"}
        };
    }

    @Test(dataProvider = "negativeProjectId")
    public void negativeProjectIdTest(String projectId, String expectedErrorMessage) {
        var testData = testDataStorage.addTestData();
        testData.getProject()
                .setId(projectId);

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
                .body(contains(expectedErrorMessage));
    }
}
