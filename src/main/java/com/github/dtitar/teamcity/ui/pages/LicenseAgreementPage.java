package com.github.dtitar.teamcity.ui.pages;

import com.codeborne.selenide.SelenideElement;
import com.github.dtitar.teamcity.ui.Selectors;

import static com.codeborne.selenide.Condition.checked;
import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Selenide.element;

public class LicenseAgreementPage extends Page {

    private SelenideElement acceptLicenceAgreementCheckBox = element(Selectors.byId("accept"));
    private SelenideElement continueButton = element(Selectors.byAttribute("name", "Continue"));

    public SetupAdminPage acceptLicenceAgreement() {
        acceptLicenceAgreementCheckBox.scrollTo()
                .click();
        acceptLicenceAgreementCheckBox.shouldBe(checked);
        continueButton.shouldBe(enabled);
        submit();
        return new SetupAdminPage();
    }
}
