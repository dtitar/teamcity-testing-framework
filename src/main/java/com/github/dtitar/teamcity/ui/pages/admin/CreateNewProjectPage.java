package com.github.dtitar.teamcity.ui.pages.admin;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.github.dtitar.teamcity.ui.Selectors;
import com.github.dtitar.teamcity.ui.pages.Page;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.element;

public class CreateNewProjectPage extends Page {
    private SelenideElement createProjectForm = element(Selectors.byId("mainContent"));
    private SelenideElement connectionSuccessfulMessage = element(Selectors.byClass("connectionSuccessful"));
    private SelenideElement urlInput = element(Selectors.byId("url"));
    private SelenideElement projectNameInput = element(Selectors.byId("projectName"));
    private SelenideElement buildTypeNameInput = element(Selectors.byId("buildTypeName"));

    public CreateNewProjectPage open(String parentProjectId) {
        Selenide.open("/admin/createObjectMenu.html?projectId=" + parentProjectId + "&showMode=createProjectMenu");
        this.waitUntilPageIsLoaded();
        return this;
    }

    public CreateNewProjectPage createProjectByUrl(String url) {
        urlInput.setValue(url);
        submit();
        return this;
    }

    public void setupProject(String projectName, String buildTypeName) {    System.out.println("Method setupProject() started");
        connectionSuccessfulMessage.shouldBe(visible, Duration.ofSeconds(20));
        projectNameInput.setValue(projectName);
        buildTypeNameInput.setValue(buildTypeName);
        submit();
    }
}
