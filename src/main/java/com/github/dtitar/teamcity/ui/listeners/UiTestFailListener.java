package com.github.dtitar.teamcity.ui.listeners;

import com.codeborne.selenide.Selenide;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import static com.github.dtitar.teamcity.ui.BrowserSettings.addVideo;
import static com.github.dtitar.teamcity.ui.BrowserSettings.isVideoOn;
import static java.lang.String.format;

public final class UiTestFailListener extends TestListenerAdapter {
    private static Logger log = LoggerFactory.getLogger(UiTestFailListener.class);

    @Override
    public void onTestFailure(ITestResult testResult) {
        if (isVideoOn()) {
            log.info(format("Test %s not succeeded, result is %s", testResult.getTestName(), testResult.getStatus()));
            log.info("Adding video...");
            addVideo(String.valueOf(Selenide.sessionId()));
        }
    }
}
