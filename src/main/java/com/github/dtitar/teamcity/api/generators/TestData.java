package com.github.dtitar.teamcity.api.generators;

import com.github.dtitar.teamcity.api.models.BuildType;
import com.github.dtitar.teamcity.api.models.NewProjectDescription;
import com.github.dtitar.teamcity.api.models.User;
import com.github.dtitar.teamcity.api.requests.unchecked.UncheckedProject;
import com.github.dtitar.teamcity.api.requests.unchecked.UncheckedUser;
import com.github.dtitar.teamcity.api.spec.Specifications;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TestData {

    //Не уверен, это должно быть тут или в классе с тестами. Решил оставить здесь.
    public static final int MIN_PROJECT_NAME_LENGTH = 1;
    public static final int MAX_PROJECT_NAME_LENGTH = 80;
    public static final int MIN_PROJECT_ID_LENGTH = 1;
    public static final int MAX_PROJECT_ID_LENGTH = 225;

    private User user;
    private NewProjectDescription project;
    private BuildType buildType;

    public void delete() {
        new UncheckedProject(Specifications.getSpec()
                .authSpec(user)).delete(project.getId());
        new UncheckedUser(Specifications.getSpec()
                .superUserSpec()).delete(user.getUsername());
    }

    @Override
    public String toString() {
        return "TestData{\n" +
                "user=" + user +
                ", \nproject=" + project +
                ", \nbuildType=" + buildType +
                "\n}\n";
    }
}
