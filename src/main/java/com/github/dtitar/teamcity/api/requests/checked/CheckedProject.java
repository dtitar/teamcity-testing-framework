package com.github.dtitar.teamcity.api.requests.checked;

import com.github.dtitar.teamcity.api.models.Project;
import com.github.dtitar.teamcity.api.models.User;
import com.github.dtitar.teamcity.api.requests.CrudInterface;
import com.github.dtitar.teamcity.api.requests.unchecked.UncheckedProject;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;

public class CheckedProject implements CrudInterface {
    private final RequestSpecification spec;

    public CheckedProject(RequestSpecification spec) {
        this.spec = spec;
    }

    @Override
    public Project create(Object obj) {
        return new UncheckedProject(spec).create(obj)
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .as(Project.class);
    }

    @Override
    public Project get(String id) {
        return new UncheckedProject(spec).get(id)
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .as(Project.class);
    }

    @Override
    public Object update(String id, Object obj) {
        return null;
    }

    @Override
    public String delete(String id) {
        return new UncheckedProject(spec).delete(id)
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .asString();
    }
}
