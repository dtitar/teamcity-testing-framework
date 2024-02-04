package com.github.dtitar.teamcity.ui.pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.github.dtitar.teamcity.ui.Selectors;
import org.openqa.selenium.By;

import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.element;

public class StartUpPage extends Page {

    private static final String START_UP_URL = "/mnt";

    private SelenideElement startUpHeader = $(By.tagName("h1"));
    private SelenideElement proceedButton = element(Selectors.byId("proceedButton"));
    private SelenideElement startUpLoader = element(Selectors.byId("stageDescription"));
    private SelenideElement acceptLicenceAgreement = element(Selectors.byId("stageDescription"));


    public StartUpPage open() {
        Selenide.open(START_UP_URL);
        this.waitUntilPageIsLoaded();
        return this;
    }

    public SetupAdminPage setupTeamCityServer() {
        return proceedTeamCityFirstStartScreen()
                .proceedDatabaseConnectionSetupScreen()
                .acceptLicenceAgreement();
    }

    private StartUpPage proceedTeamCityFirstStartScreen() {
        startUpHeader.shouldHave(text("TeamCity First Start"));
        proceedButton.click();
        waitUntilPageIsLoaded();
        return this;
    }

    private LicenseAgreementPage proceedDatabaseConnectionSetupScreen() {
        startUpHeader.shouldHave(text("Database connection setup"));
        proceedButton.click();
        waitUntilStartUpPageIsLoaded();
        return new LicenseAgreementPage();
    }

    private void waitUntilStartUpPageIsLoaded() {
        startUpLoader.shouldNotBe(visible, Duration.ofMinutes(2));
    }
}
