package com.github.dtitar.teamcity.api.requests.checked;

import com.github.dtitar.teamcity.api.models.Project;
import com.github.dtitar.teamcity.api.models.User;
import com.github.dtitar.teamcity.api.requests.CrudInterface;
import com.github.dtitar.teamcity.api.requests.unchecked.UncheckedProject;
import com.github.dtitar.teamcity.api.requests.unchecked.UncheckedUser;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;

public class CheckedUser implements CrudInterface {
    private final RequestSpecification spec;

    public CheckedUser(RequestSpecification spec) {
        this.spec = spec;
    }

    @Override
    public User create(Object obj) {
        return new UncheckedUser(spec).create(obj)
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .as(User.class);
    }

    @Override
    public Project get(String id) {
        return new UncheckedUser(spec).get(id)
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .as(Project.class);
    }

    @Override
    public Object update(Object object) {
        return null;
    }

    @Override
    public String delete(String id) {
        return new UncheckedUser(spec).delete(id)
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_NO_CONTENT)
                .extract()
                .asString();
    }
}
