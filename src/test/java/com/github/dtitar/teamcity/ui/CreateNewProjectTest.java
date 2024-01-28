package com.github.dtitar.teamcity.ui;

import com.github.dtitar.teamcity.api.generators.TestData;
import com.github.dtitar.teamcity.api.requests.checked.CheckedProject;
import com.github.dtitar.teamcity.api.spec.Specifications;
import com.github.dtitar.teamcity.ui.pages.ProjectsPage;
import com.github.dtitar.teamcity.ui.pages.admin.CreateNewProjectPage;
import org.testng.annotations.Test;

public class CreateNewProjectTest extends BaseUiTest {
    @Test
    public void authorizedUserShouldBeAbleCreateProjectFromRepositoryUrl() {
        var testData = testDataStorage.addTestData();
        /* заменяем сгенерированный id проекта и делаем его равным имени проекта без спецсимволов
         * нужно это потому, что при создании проекта через урл Id присваивается автоматически и он равен
         * имени проекта без спецсимволов, регистр так же отличается, например проект с именем test_atsZuiMLun
         * будет иметь id TestAtsZuiMLun и тд. В зависимости от требований это можно учитывать в проверках или опускать
         *
         */
        testData.getProject()
                .setId(testData.getProject()
                        .getName()
                        .replaceAll("\\W|_", ""));


        loginAsUser(testData.getUser());

        new CreateNewProjectPage()
                .open(testData.getProject()
                        .getParentProject()
                        .getLocator())
                .createProjectByUrl(TestData.REPOSITORY_URL)
                .setupProject(testData.getProject()
                        .getName(), testData.getBuildType()
                        .getName());

        new ProjectsPage().open()
                .checkProjectExist(testData.getProject()
                        .getName());


        var projectFromApi = new CheckedProject(Specifications.getSpec()
                .authSpec(testData.getUser())).get(testData.getProject()
                .getId());

        softly.assertThat(projectFromApi.getName())
                .isEqualTo(testData.getProject()
                        .getName());
        softly.assertThat(projectFromApi.getId())
                .isEqualToIgnoringCase(testData.getProject()
                        .getId());
        softly.assertThat(projectFromApi.getParentProjectId())
                .isEqualTo(testData.getProject()
                        .getParentProject().getLocator());
    }

    @Test
    public void authorizedUserShouldBeAbleCreateProjectManually() {
        var testData = testDataStorage.addTestData();

        loginAsUser(testData.getUser());

        new CreateNewProjectPage()
                .open(testData.getProject()
                        .getParentProject()
                        .getLocator())
                .createProjectManually(testData.getProject()
                        .getName());

        new ProjectsPage().open()
                .checkProjectExist(testData.getProject()
                        .getName());
    }
}
