package com.github.dtitar.teamcity.ui.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.github.dtitar.teamcity.ui.Selectors;
import com.github.dtitar.teamcity.ui.elements.ProjectElement;
import com.github.dtitar.teamcity.ui.pages.favorites.FavoritesPage;

import java.util.List;

import static com.codeborne.selenide.Selenide.elements;

public class ProjectsPage extends FavoritesPage {
    private static final String FAVORITE_PROJECTS_URL = "/favorite/projects";
    private ElementsCollection subProjects = elements(Selectors.byClass("Subproject__container--WE"));

    public ProjectsPage open() {
        Selenide.open(FAVORITE_PROJECTS_URL);
        this.waitUntilFavoritePageIsLoaded();
        return this;
    }

    public List<ProjectElement> getSubProjects() {
        return generatePageElements(subProjects, ProjectElement::new);
    }
}
