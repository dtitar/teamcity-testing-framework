package com.github.dtitar.teamcity.ui;

import com.codeborne.selenide.Configuration;
import com.github.dtitar.teamcity.api.config.Config;
import io.qameta.allure.Allure;
import lombok.experimental.UtilityClass;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.codeborne.selenide.Selenide.sleep;
import static java.lang.String.format;

@UtilityClass
public class BrowserSettings {

    private static Logger log = LoggerFactory.getLogger(BrowserSettings.class);

    public static void setup(String browser) {
        Configuration.browser = browser;

        switch (browser) {
            case "firefox":
                setFirefoxOptions();
                break;

            case "chrome":
                setChromeOptions();
                break;
            default:
                throw new IllegalArgumentException(format("Invalid '%s' parameter value", browser));
        }
    }

    private static void setFirefoxOptions() {
        Configuration.browserCapabilities = new FirefoxOptions();
        Configuration.browserCapabilities.setCapability("selenoid:options", getSelenoidOptions());
    }

    private static void setChromeOptions() {
        Configuration.browserCapabilities = new ChromeOptions();
        Configuration.browserCapabilities.setCapability("selenoid:options", getSelenoidOptions());
    }

    private static Map<String, Object> getSelenoidOptions() {
        Map<String, Object> options = new HashMap<>();
        options.put("enableVNC", true);
        options.put("enableLog", true);
        if (isVideoOn()) {
            options.put("enableVideo", true);
        }
        return options;
    }

    public static void addVideo(String sessionId) {
        final var videoAdditionTimeout = 1000;
        final var videoAdditionAttempts = 10;
        URL videoUrl = getVideoUrl(sessionId);
        if (videoUrl != null) {
            sleep(videoAdditionTimeout);
            for (int i = 0; i < videoAdditionAttempts; i++) {
                try (InputStream videoInputStream = videoUrl.openStream()) {
                    Allure.addAttachment("Video", "video/mp4", videoInputStream, "mp4");
                    break;
                } catch (FileNotFoundException e) {
                    sleep(videoAdditionTimeout);
                } catch (IOException e) {
                    log.warn("[ALLURE VIDEO ATTACHMENT ERROR] Cant attach allure video, {}", videoUrl);
                    e.printStackTrace();
                }
            }
        }
    }

    private static URL getVideoUrl(String sessionId) {
        String videoUrl = format("%s/%s.mp4", Config.getProperty("videoStorage"), sessionId);
        log.info("Video URL: " + videoUrl);
        try {
            return new URL(videoUrl);
        } catch (MalformedURLException e) {
            log.warn("[ALLURE VIDEO ATTACHMENT ERROR] Wrong test video url, {}", videoUrl);
            e.printStackTrace();
        }
        return null;
    }

    public static boolean isVideoOn() {
        return Optional.of(!Config.getProperty("videoStorage")
                        .isEmpty())
                .orElse(false);
    }
}
