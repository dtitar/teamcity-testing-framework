package com.github.dtitar.teamcity.ui;

import com.codeborne.selenide.Configuration;
import com.github.dtitar.teamcity.BaseTest;
import com.github.dtitar.teamcity.api.config.Config;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.annotations.BeforeSuite;

import java.util.HashMap;
import java.util.Map;

import static java.lang.String.format;

public class BaseUiTest extends BaseTest {
    @BeforeSuite
    public void setupUiTests() {
        Configuration.browser = "firefox";
        Configuration.baseUrl = format("http://%s", Config.getProperty("host"));
        Configuration.remote=Config.getProperty("remote");

        Configuration.reportsFolder = "target/surefire-reports";
        Configuration.downloadsFolder ="target/downloads";

        Map<String, Boolean> options = new HashMap<>();
        options.put("enableVNC", true);
        options.put("enableLog", true);

        FirefoxOptions capabilities = new FirefoxOptions();
        Configuration.browserCapabilities = capabilities;
        Configuration.browserCapabilities.setCapability("selenoid:options", options);
    }
}
