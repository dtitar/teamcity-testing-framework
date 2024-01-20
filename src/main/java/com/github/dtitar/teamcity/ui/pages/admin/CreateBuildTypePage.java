package com.github.dtitar.teamcity.ui.pages.admin;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.github.dtitar.teamcity.ui.Selectors;
import com.github.dtitar.teamcity.ui.pages.Page;

import java.time.Duration;

import static com.codeborne.selenide.Condition.attributeMatching;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.element;

public class CreateBuildTypePage extends Page {

    private SelenideElement connectionSuccessfulMessage = element(Selectors.byClass("connectionSuccessful"));

    private SelenideElement fromRepositoryUrlTab = element(Selectors.byAttribute("href", "#createFromUrl"));
    private SelenideElement repositoryUrlInput = element(Selectors.byId("url"));
    private SelenideElement buildTypeNameInput = element(Selectors.byId("buildTypeName"));
    private SelenideElement manuallyTab = element(Selectors.byAttribute("href", "#createManually"));
    private SelenideElement projectNameManuallyInput = element(Selectors.byId("name"));


    public CreateBuildTypePage open(String projectName) {
        Selenide.open("/admin/createObjectMenu.html?projectId=" + projectName + "&showMode=createBuildTypeMenu");
        waitUntilPageIsLoaded();
        return this;
    }

    public CreateBuildTypePage createBuildTypeByUrl(String repositoryUrl) {
        fromRepositoryUrlTab.click();
        repositoryUrlInput.setValue(repositoryUrl);
        submit();
        return this;
    }

    public void setupBuildType(String buildTypeName) {
        connectionSuccessfulMessage.shouldBe(visible, Duration.ofSeconds(30));
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
