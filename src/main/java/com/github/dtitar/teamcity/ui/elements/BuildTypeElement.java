package com.github.dtitar.teamcity.ui.elements;

import com.codeborne.selenide.SelenideElement;
import com.github.dtitar.teamcity.ui.Selectors;
import lombok.Getter;

@Getter
public class BuildTypeElement extends PageElement {

    private final SelenideElement header;

    public BuildTypeElement(SelenideElement element) {
        super(element);
        this.header = findElement(Selectors.byDataTest("ring-link"));
    }
}
