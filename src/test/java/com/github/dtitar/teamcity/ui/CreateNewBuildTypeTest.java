package com.github.dtitar.teamcity.ui;

import com.github.dtitar.teamcity.api.generators.TestData;
import com.github.dtitar.teamcity.api.requests.CheckedRequests;
import com.github.dtitar.teamcity.api.requests.checked.CheckedBuildType;
import com.github.dtitar.teamcity.api.spec.Specifications;
import com.github.dtitar.teamcity.ui.pages.ProjectPage;
import com.github.dtitar.teamcity.ui.pages.admin.CreateBuildTypePage;
import org.testng.annotations.Test;

import static java.lang.String.format;

public class CreateNewBuildTypeTest extends BaseUiTest {
    @Test
    public void userShouldBeAbleToCreateBuildTypeByUrl() {
        var testData = testDataStorage.addTestData();
        /* заменяем сгенерированный id билдконфигурации и делаем его равным id проекта_имя конфигурации(без спецсимволов)
         * нужно это потому, что при создании билд конфигурации через урл Id присваивается автоматически и он равен
         * id проекта + имени проекта без спецсимволов, например билд конфигурация с именем test_kGDjUdchvv
         * будет иметь id test_lVCHebVnFC_TestKGDjUdchvv и тд.
         */
        testData.getBuildType()
                .setId(format("%s_%s", testData.getProject()
                        .getId(), testData.getBuildType()
                        .getName()
                        .replaceAll("\\W|_", "")));

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
                .checkBuildTypeExist(testData.getBuildType()
                        .getName());

        var buildTypeFromApi = new CheckedBuildType(Specifications.getSpec()
                .authSpec(testData.getUser())).get(testData.getBuildType()
                .getId());

        softly.assertThat(buildTypeFromApi.getName())
                .isEqualTo(testData.getBuildType()
                        .getName());
        softly.assertThat(buildTypeFromApi.getId())
                .isEqualToIgnoringCase(testData.getBuildType()
                        .getId());
    }

    @Test
    public void userShouldBeAbleToCreateBuildTypeManually() {
        var testData = testDataStorage.addTestData();
        loginAsUser(testData.getUser());

        new CheckedRequests(Specifications.getSpec()
                .authSpec(testData.getUser()))
                .getProjectRequest()
                .create(testData.getProject());

        new CreateBuildTypePage().open(testData.getProject()
                        .getId())
                .createBuildTypeManually(testData.getBuildType()
                        .getName(), testData.getBuildType()
                        .getId());

        new ProjectPage().open(testData.getProject()
                        .getId())
                .checkBuildTypeExist(testData.getBuildType()
                        .getName());

        var buildTypeFromApi = new CheckedBuildType(Specifications.getSpec()
                .authSpec(testData.getUser())).get(testData.getBuildType()
                .getId());

        softly.assertThat(buildTypeFromApi.getName())
                .isEqualTo(testData.getBuildType()
                        .getName());
        softly.assertThat(buildTypeFromApi.getId())
                .isEqualToIgnoringCase(testData.getBuildType()
                        .getId());
    }
}
