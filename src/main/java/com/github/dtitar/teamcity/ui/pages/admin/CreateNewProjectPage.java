package com.github.dtitar.teamcity.ui.pages.admin;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.github.dtitar.teamcity.ui.Selectors;
import com.github.dtitar.teamcity.ui.pages.Page;

import java.time.Duration;

import static com.codeborne.selenide.Condition.attribute;
import static com.codeborne.selenide.Condition.attributeMatching;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.element;

public class CreateNewProjectPage extends Page {
    private SelenideElement connectionSuccessfulMessage = element(Selectors.byClass("connectionSuccessful"));
    private SelenideElement urlInput = element(Selectors.byId("url"));
    private SelenideElement projectNameInput = element(Selectors.byId("projectName"));
    private SelenideElement buildTypeNameInput = element(Selectors.byId("buildTypeName"));
    private SelenideElement manuallyTab = element(Selectors.byAttribute("href", "#createManually"));
    private SelenideElement projectNameManuallyInput = element(Selectors.byId("name"));

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

    public void setupProject(String projectName, String buildTypeName) {
        connectionSuccessfulMessage.shouldBe(visible, Duration.ofSeconds(30));
        projectNameInput.setValue(projectName);
        buildTypeNameInput.setValue(buildTypeName);
        submit();
    }

    public void createProjectManually(String projectName) {
        manuallyTab.click();
        manuallyTab.shouldHave(attributeMatching("class", ".*expanded.*"));
        projectNameManuallyInput.setValue(projectName);
        submit();
    }
}
