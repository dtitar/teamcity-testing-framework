package com.github.dtitar.teamcity.ui;

import com.github.dtitar.teamcity.api.generators.TestData;
import com.github.dtitar.teamcity.ui.pages.ProjectsPage;
import com.github.dtitar.teamcity.ui.pages.admin.CreateNewProjectPage;
import org.testng.annotations.Test;

public class CreateNewProjectTest extends BaseUiTest {
    @Test
    public void authorizedUserShouldBeAbleCreateProjectFromRepositoryUrl() {
        var testData = testDataStorage.addTestData();

        loginAsUser(testData.getUser());

        new CreateNewProjectPage()
                .open(testData.getProject().getParentProject().getLocator())
                .createProjectByUrl(TestData.REPOSITORY_URL)
                .setupProject(testData.getProject().getName(), testData.getBuildType().getName());

        new ProjectsPage().open()
                .checkProjectExist(testData.getProject()
                        .getName());
    }

    @Test
    public void authorizedUserShouldBeAbleCreateProjectManually() {
        var testData = testDataStorage.addTestData();

        loginAsUser(testData.getUser());

        new CreateNewProjectPage()
                .open(testData.getProject().getParentProject().getLocator())
                .createProjectManually(testData.getProject().getName());

        new ProjectsPage().open()
                .checkProjectExist(testData.getProject()
                        .getName());
    }
}
