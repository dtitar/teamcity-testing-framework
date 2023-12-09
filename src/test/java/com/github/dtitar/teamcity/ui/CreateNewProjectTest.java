package com.github.dtitar.teamcity.ui;

import com.github.dtitar.teamcity.ui.pages.admin.CreateNewProjectPage;
import org.testng.annotations.Test;

public class CreateNewProjectTest extends BaseUiTest {
    @Test
    public void authorizedUserShouldBeAbleCreateProject() {
        var testData = testDataStorage.addTestData();
        var url = "https://github.com/dtitar/workshop-test-automation-from-scratch";

        loginAsUser(testData.getUser());

        new CreateNewProjectPage()
                .open(testData.getProject().getParentProject().getLocator())
                .createProjectByUrl(url)
                .setupProject(testData.getProject().getName(), testData.getBuildType().getName());
    }
}
