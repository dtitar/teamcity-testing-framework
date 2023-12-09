package com.github.dtitar.teamcity.ui;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.selector.ByAttribute;
import com.github.dtitar.teamcity.api.requests.checked.CheckedUser;
import com.github.dtitar.teamcity.api.spec.Specifications;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Selenide.element;

public class CreateNewProjectTest extends BaseUiTest {
    @Test
    public void authorizedUserShouldBeAbleCreateProject() {
        var testData = testDataStorage.addTestData();
        new CheckedUser(Specifications.getSpec()
                .superUserSpec()).create(testData.getUser());

        Selenide.open("/login.html");
        var userNameInput = element(new ByAttribute("id", "username"));
        var passwordInput = element(new ByAttribute("id", "password"));
        var submitButton = element(new ByAttribute("type", "submit"));

        userNameInput.sendKeys(testData.getUser().getUsername());
        passwordInput.sendKeys(testData.getUser().getPassword());
        submitButton.click();
    }
}
