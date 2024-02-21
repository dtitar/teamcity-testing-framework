package com.github.dtitar.teamcity.ui.pages;

import com.codeborne.selenide.Selenide;

public class AgentsPage extends Page {
    private static final String AGENTS_URL = "/agents/overview";

    public AgentsPage open() {
        Selenide.open(AGENTS_URL);
        this.waitUntilPageIsLoaded();
        return this;
    }


}
