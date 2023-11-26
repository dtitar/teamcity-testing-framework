package com.github.dtitar.teamcity.api.generators;

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

    private User user;
    private NewProjectDescription project;

    public void delete() {
        new UncheckedProject(Specifications.getSpec()
                .authSpec(user)).delete(project.getId());
        new UncheckedUser(Specifications.getSpec()
                .superUserSpec()).delete(user.getUsername());
    }
}
