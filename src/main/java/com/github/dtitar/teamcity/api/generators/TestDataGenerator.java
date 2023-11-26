package com.github.dtitar.teamcity.api.generators;

import com.github.dtitar.teamcity.api.models.NewProjectDescription;
import com.github.dtitar.teamcity.api.models.Project;
import com.github.dtitar.teamcity.api.models.Role;
import com.github.dtitar.teamcity.api.models.RoleId;
import com.github.dtitar.teamcity.api.models.Roles;
import com.github.dtitar.teamcity.api.models.User;

import java.util.Arrays;

public class TestDataGenerator {

    public TestData generate() {
        var user = User.builder()
                .username(RandomData.generateString())
                .password(RandomData.generateString())
                .email(RandomData.generateString() + "@gmail.com")
                .roles(Roles.builder()
                        .role(Arrays.asList(Role.builder()
                                .roleId(RoleId.SYSTEM_ADMIN.name())
                                .scope("g")
                                .build()))
                        .build())
                .build();

        var projectDescription = NewProjectDescription
                .builder()
                .parentProject(Project.builder()
                        .locator("_Root")
                        .build())
                .id(RandomData.generateString())
                .name(RandomData.generateString())
                .copyAllAssociatedSettings(true)
                .build();

        return TestData.builder()
                .user(user)
                .project(projectDescription)
                .build();
    }
}
