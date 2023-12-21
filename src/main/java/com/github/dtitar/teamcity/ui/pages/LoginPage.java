package com.github.dtitar.teamcity.ui.pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.selector.ByAttribute;
import com.github.dtitar.teamcity.api.models.User;
import lombok.Getter;

import static com.codeborne.selenide.Selenide.element;

@Getter
public class LoginPage extends Page {
    private static final String LOGIN_PAGE_URL = "/login.html";
    private SelenideElement usernameInput = element(new ByAttribute("id", "username"));
    private SelenideElement passwordInput = element(new ByAttribute("id", "password"));
    private SelenideElement submitButton = element(new ByAttribute("type", "submit"));


    public LoginPage open() {
        Selenide.open(LOGIN_PAGE_URL);
        return this;
    }

    public void login(User user) {
        usernameInput.sendKeys(user.getUsername());
        passwordInput.sendKeys(user.getPassword());
        this.submit();
    }
}