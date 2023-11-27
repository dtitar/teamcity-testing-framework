package com.github.dtitar.teamcity.api.requests.checked;

import com.github.dtitar.teamcity.api.models.BuildType;
import com.github.dtitar.teamcity.api.requests.CrudInterface;
import com.github.dtitar.teamcity.api.requests.Request;
import com.github.dtitar.teamcity.api.requests.unchecked.UncheckedBuildConfig;
import com.github.dtitar.teamcity.api.spec.Specifications;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;

import static io.restassured.RestAssured.given;

public class CheckedBuildConfig extends Request implements CrudInterface {

    public CheckedBuildConfig(RequestSpecification spec) {
        super(spec);
    }

    @Override
    public BuildType create(Object object) {
        return new UncheckedBuildConfig(spec).create(object)
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .as(BuildType.class);
    }

    @Override
    public Object get(String id) {
        return new UncheckedBuildConfig(spec).get(id)
                .then().assertThat().statusCode(HttpStatus.SC_OK)
                .extract().as(BuildType.class);
    }

    @Override
    public Object update(String id, Object obj) {
        return new UncheckedBuildConfig(spec).update(id, obj)
                .then().assertThat().statusCode(HttpStatus.SC_OK)
                .extract().as(BuildType.class);
    }

    @Override
    public String delete(String id) {
        return new UncheckedBuildConfig(spec).delete(id)
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_NO_CONTENT)
                .extract()
                .asString();
    }
}
