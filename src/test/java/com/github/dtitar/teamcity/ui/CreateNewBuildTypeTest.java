package com.github.dtitar.teamcity.ui;

import com.codeborne.selenide.Condition;
import com.github.dtitar.teamcity.api.generators.TestData;
import com.github.dtitar.teamcity.api.requests.CheckedRequests;
import com.github.dtitar.teamcity.api.spec.Specifications;
import com.github.dtitar.teamcity.ui.pages.ProjectPage;
import com.github.dtitar.teamcity.ui.pages.admin.CreateBuildTypePage;
import org.testng.annotations.Test;

public class CreateNewBuildTypeTest extends BaseUiTest {
    @Test
    public void userShouldBeAbleToCreateBuildTypeByUrl() {
        var testData = testDataStorage.addTestData();
        loginAsUser(testData.getUser());

        new CheckedRequests(Specifications.getSpec()
                .authSpec(testData.getUser()))
                .getProjectRequest()
                .create(testData.getProject());

        new CreateBuildTypePage().open(testData.getProject()
                        .getId())
                .createBuildTypeByUrl(TestData.REPOSITORY_URL)
                .setupBuildType(testData.getBuildType()
                        .getName());

        new ProjectPage().open(testData.getProject()
                        .getId())
                .getBuildTypes().get(0)
                .getHeader()
                .shouldHave(Condition.text(testData.getBuildType()
                        .getName()));
    }
}
