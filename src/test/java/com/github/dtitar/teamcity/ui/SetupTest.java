package com.github.dtitar.teamcity.ui;

import com.github.dtitar.teamcity.ui.pages.AgentPage;
import com.github.dtitar.teamcity.ui.pages.LoginAsSuperUserPage;
import com.github.dtitar.teamcity.ui.pages.StartUpPage;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Condition.text;

public class SetupTest extends BaseUiTest {

    @Test
    public void startUpTest() {
        new StartUpPage().open()
                .setupTeamCityServer()
                .getHeader()
                .shouldHave(text("Create Administrator Account"));
    }

    @Test
    public void setupTeamCityAgentTest() {
        new LoginAsSuperUserPage().open()
                .login();
        new AgentPage().open(1)
                .authorizeAgent()
                .getAgentAuthorizationStatus()
                .shouldHave(text("Authorized"));
    }
}