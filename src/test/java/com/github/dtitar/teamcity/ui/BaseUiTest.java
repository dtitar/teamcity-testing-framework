package com.github.dtitar.teamcity.ui;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import com.github.dtitar.teamcity.BaseTest;
import com.github.dtitar.teamcity.api.config.Config;
import com.github.dtitar.teamcity.api.models.User;
import com.github.dtitar.teamcity.api.requests.checked.CheckedUser;
import com.github.dtitar.teamcity.api.spec.Specifications;
import com.github.dtitar.teamcity.ui.listeners.UiTestFailListener;
import com.github.dtitar.teamcity.ui.pages.LoginPage;
import io.qameta.allure.selenide.AllureSelenide;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;

import java.time.Duration;

import static java.lang.String.format;

@Listeners({UiTestFailListener.class})
public class BaseUiTest extends BaseTest {

    private static Logger log = LoggerFactory.getLogger(BaseUiTest.class);

    @BeforeSuite
    public void setupUiTests() {
        Configuration.browser = Config.getProperty("browser");
        Configuration.baseUrl = format("http://%s", Config.getProperty("host"));
        Configuration.remote = Config.getProperty("remote");
        Configuration.reportsFolder = "target/surefire-reports";
        Configuration.downloadsFolder = "target/downloads";
        Configuration.timeout = Duration.ofSeconds(15)
                .toMillis();;
        BrowserSettings.setup(Config.getProperty("browser"));
    }

    @BeforeMethod
    public void beforeUiTest() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide().
                includeSelenideSteps(true));
    }

    public void loginAsUser(User user) {
        new CheckedUser(Specifications.getSpec()
                .superUserSpec()).create(user);
        new LoginPage().open()
                .login(user);
    }
}
