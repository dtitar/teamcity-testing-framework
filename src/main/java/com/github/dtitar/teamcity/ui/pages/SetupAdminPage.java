package com.github.dtitar.teamcity.ui.pages;

import com.codeborne.selenide.SelenideElement;
import com.github.dtitar.teamcity.ui.Selectors;
import lombok.Getter;

import static com.codeborne.selenide.Selenide.element;

@Getter
public class SetupAdminPage extends Page {
    private static final String SETUP_ADMIN_URL = "/setupAdmin";
    private SelenideElement header = element(Selectors.byId("header"));
}
