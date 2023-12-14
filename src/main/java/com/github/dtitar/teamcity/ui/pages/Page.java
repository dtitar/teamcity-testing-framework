package com.github.dtitar.teamcity.ui.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.github.dtitar.teamcity.ui.Selectors;
import com.github.dtitar.teamcity.ui.elements.PageElement;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static com.codeborne.selenide.Condition.disabled;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.element;

public abstract class Page {
    private final SelenideElement submitButton = element(Selectors.byType("submit"));
    private final SelenideElement savingWaitingMarker = element(Selectors.byId("saving"));
    private final SelenideElement pageWaitingMarker = element(Selectors.byDataTest("ring-loader"));
    private final SelenideElement loadingWarning = element(Selectors.byId("loadingWarning"));

    public void submit() {
        submitButton.click();
        this.waitUntilDataIsSaved();
    }

    public void waitUntilPageIsLoaded() {
        loadingWarning.shouldNotBe(visible, Duration.ofMinutes(1));
        pageWaitingMarker.shouldNotBe(visible, Duration.ofMinutes(1));
    }

    public void waitUntilDataIsSaved() {
        savingWaitingMarker.shouldNotBe(visible, Duration.ofSeconds(30));
    }

    public <T extends PageElement> List<T> generatePageElements(
            ElementsCollection collection,
            Function<SelenideElement, T> creator) {
        var elements = new ArrayList<T>();
        collection.forEach(webElement -> elements.add(creator.apply(webElement)));
        return elements;
    }
}
