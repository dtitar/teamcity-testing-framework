package com.github.dtitar.teamcity.ui.pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.github.dtitar.teamcity.api.config.Config;
import com.github.dtitar.teamcity.ui.Selectors;
import lombok.Getter;

import static com.codeborne.selenide.Selenide.element;

@Getter
public class LoginAsSuperUserPage extends Page {
    private static final String LOGIN_AS_SUPER_USER_PAGE_URL = "/login.html?super=1";
    private static final SelenideElement authenticationTokenInput = element(Selectors.byId("password"));

    public LoginAsSuperUserPage open() {
        Selenide.open(LOGIN_AS_SUPER_USER_PAGE_URL);
        return this;
    }

    public void login() {
        authenticationTokenInput.sendKeys(Config.getProperty("superUserToken"));
        this.submit();
    }
}