package com.github.dtitar.teamcity.api;

import com.github.dtitar.teamcity.api.models.Role;
import com.github.dtitar.teamcity.api.models.RoleId;
import com.github.dtitar.teamcity.api.models.Roles;
import com.github.dtitar.teamcity.api.requests.checked.CheckedProject;
import com.github.dtitar.teamcity.api.requests.checked.CheckedUser;
import com.github.dtitar.teamcity.api.requests.unchecked.UncheckedProject;
import com.github.dtitar.teamcity.api.spec.Specifications;
import org.apache.http.HttpStatus;
import org.testng.annotations.Test;

import java.util.Arrays;

import static java.lang.String.format;
import static org.hamcrest.Matchers.containsString;

public class RolesTest extends BaseApiTest {
    @Test
    public void unauthorizedUserShouldNotHaveRightsToCreateProject() {
        new UncheckedProject(Specifications.getSpec()
                .unauthSpec()).create(testdata.getProject())
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_UNAUTHORIZED)
                .body(containsString("Authentication required"));

        new UncheckedProject(Specifications.getSpec()
                .authSpec(testdata.getUser())).get(testdata.getProject()
                        .getId())
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .body(containsString(format("No project found by locator 'count:1,id:%s'", testdata.getProject()
                        .getId())));
    }

    @Test
    public void systemAdminShouldHaveRightsToCreateProject() {
        testdata.getUser()
                .setRoles(Roles.builder()
                        .role(Arrays.asList(Role.builder()
                                .roleId(RoleId.SYSTEM_ADMIN.name())
                                .scope("g")
                                .build()))
                        .build());

        new CheckedUser(Specifications.getSpec()
                .superUserSpec()).create(testdata.getUser());

        var project = new CheckedProject(Specifications.getSpec()
                .authSpec(testdata.getUser())).create(testdata.getProject());

        softly.assertThat(project.getId())
                .isEqualTo(testdata.getProject()
                        .getId());
    }
}
