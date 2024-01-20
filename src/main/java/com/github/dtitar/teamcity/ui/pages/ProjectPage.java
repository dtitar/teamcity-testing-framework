package com.github.dtitar.teamcity.ui.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.selector.ByAttribute;
import com.github.dtitar.teamcity.ui.elements.BuildTypeElement;
import com.github.dtitar.teamcity.ui.pages.favorites.FavoritesPage;

import java.time.Duration;
import java.util.List;

import static com.codeborne.selenide.Selenide.elements;

public class ProjectPage extends FavoritesPage {
    private final ElementsCollection buildTypes = elements(ByAttribute.cssSelector("[class*=BuildTypeLine__root]"));

    public ProjectPage open(String projectName) {
        Selenide.open("/project/" + projectName + "?mode=builds");
        this.waitUntilFavoritePageIsLoaded();
        return this;
    }

    public List<BuildTypeElement> getBuildTypes() {
        return generatePageElements(buildTypes, BuildTypeElement::new);
    }

    public ProjectPage checkBuildTypeExist(String buildTypeName) {
        getBuildTypes()
                .stream()
                .reduce((first, second) -> second)
                .get()
                .getHeader()
                .shouldHave(Condition.text(buildTypeName), Duration.ofSeconds(30));
        return this;
    }
}
