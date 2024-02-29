package com.github.dtitar.teamcity.ui.pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.github.dtitar.teamcity.ui.Selectors;
import lombok.Getter;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.element;
import static java.lang.String.format;

public final class AgentPage extends Page {
    private static final String AGENT_URL = "/agent";

    private SelenideElement authorizeButton = $("[class*=AuthorizeAgent__authorizeAgent--Xr]");
    private SelenideElement authorizationPopup = element(Selectors.byDataTest("ring-popup"));
    private SelenideElement authorizePopupButton = $x("//*[@data-test='cancel']/preceding-sibling::button");
    @Getter
    private SelenideElement agentAuthorizationStatus = $("[data-agent-authorization-status]");

    public AgentPage open(int agentId) {
        Selenide.open(format("%s/%d", AGENT_URL, agentId));
        this.waitUntilPageIsLoaded();
        return this;
    }

    public AgentPage authorizeAgent() {
        authorizeButton.click();
        authorizationPopup.shouldBe(visible);
        authorizePopupButton.click();
        waitUntilDataIsSaved();
        return this;
    }





}
