package com.github.dtitar.teamcity.ui.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.github.dtitar.teamcity.ui.Selectors;
import com.github.dtitar.teamcity.ui.elements.PageElement;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.element;

public abstract class Page {
    protected static final int PAGE_LOAD_WAIT_TIMEOUT_IN_SECONDS = 60;
    private final SelenideElement submitButton = element(Selectors.byType("submit"));
    private final SelenideElement savingWaitingMarker = element(Selectors.byId("saving"));
    private final SelenideElement pageWaitingMarker = element(Selectors.byDataTest("ring-loader"));
    private final SelenideElement loadingWarning = element(Selectors.byId("loadingWarning"));

    public final void submit() {
        submitButton.click();
        this.waitUntilDataIsSaved();
    }

    public final void waitUntilPageIsLoaded() {
        loadingWarning.shouldNotBe(visible, Duration.ofMinutes(PAGE_LOAD_WAIT_TIMEOUT_IN_SECONDS));
        pageWaitingMarker.shouldNotBe(visible, Duration.ofMinutes(PAGE_LOAD_WAIT_TIMEOUT_IN_SECONDS));
    }

    public final void waitUntilDataIsSaved() {
        savingWaitingMarker.shouldNotBe(visible, Duration.ofSeconds(PAGE_LOAD_WAIT_TIMEOUT_IN_SECONDS));
    }

    public final <T extends PageElement> List<T> generatePageElements(
            ElementsCollection collection,
            Function<SelenideElement, T> creator) {
        var elements = new ArrayList<T>();
        collection.forEach(webElement -> elements.add(creator.apply(webElement)));
        return elements;
    }
}
