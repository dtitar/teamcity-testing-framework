package com.github.dtitar.teamcity.api.generators;

import com.github.dtitar.teamcity.api.models.BuildType;
import com.github.dtitar.teamcity.api.models.NewProjectDescription;
import com.github.dtitar.teamcity.api.models.Project;
import com.github.dtitar.teamcity.api.enums.RoleId;
import com.github.dtitar.teamcity.api.models.Role;
import com.github.dtitar.teamcity.api.models.Roles;
import com.github.dtitar.teamcity.api.models.User;

import java.util.Arrays;

public class TestDataGenerator {

    public static TestData generate() {
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

        var project = NewProjectDescription
                .builder()
                .parentProject(Project.builder()
                        .locator("_Root")
                        .build())
                .id(RandomData.generateString())
                .name(RandomData.generateString())
                .copyAllAssociatedSettings(true)
                .build();

        var buildType = BuildType.builder()
                .id(RandomData.generateString())
                .name(RandomData.generateString())
                .project(project)
                .build();

        var testData = TestData.builder()
                .user(user)
                .project(project)
                .buildType(buildType)
                .build();

        System.out.println(testData);

        return testData;
    }

    public static Roles generateRoles(RoleId roleId, String scope) {
        return Roles.builder()
                .role(Arrays.asList(Role.builder()
                        .roleId(roleId.name())
                        .scope(scope)
                        .build()))
                .build();
    }
}
