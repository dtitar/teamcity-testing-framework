package com.github.dtitar.teamcity.ui;

import com.codeborne.selenide.Condition;
import com.github.dtitar.teamcity.ui.pages.StartUpPage;
import org.testng.annotations.Test;

public class SetupTest extends BaseUiTest{

    @Test
    public void startUpTest() {
        new StartUpPage().open()
                .setupTeamCityServer()
                .getHeader()
                .shouldHave(Condition.text("Create Administrator Account"));
    }
}
