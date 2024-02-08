package com.github.dtitar.teamcity.ui;

import com.codeborne.selenide.Configuration;
import com.github.dtitar.teamcity.BaseTest;
import com.github.dtitar.teamcity.api.config.Config;
import com.github.dtitar.teamcity.api.models.User;
import com.github.dtitar.teamcity.api.requests.checked.CheckedUser;
import com.github.dtitar.teamcity.api.spec.Specifications;
import com.github.dtitar.teamcity.ui.pages.LoginPage;
import org.testng.annotations.BeforeSuite;

import static java.lang.String.format;

public class BaseUiTest extends BaseTest {
    @BeforeSuite
    public void setupUiTests() {
        Configuration.browser = Config.getProperty("browser");
        Configuration.baseUrl = format("http://%s", Config.getProperty("host"));
        Configuration.remote=Config.getProperty("remote");
        Configuration.reportsFolder = "target/surefire-reports";
        Configuration.downloadsFolder = "target/downloads";
        BrowserSettings.setup(Config.getProperty("browser"));
    }

    public void loginAsUser(User user) {
        new CheckedUser(Specifications.getSpec()
                .superUserSpec()).create(user);
        new LoginPage().open()
                .login(user);
    }
}
