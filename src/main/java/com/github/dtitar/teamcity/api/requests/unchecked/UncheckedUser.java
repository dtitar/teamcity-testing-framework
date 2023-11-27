package com.github.dtitar.teamcity.api.requests.unchecked;

import com.github.dtitar.teamcity.api.requests.CrudInterface;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class UncheckedUser implements CrudInterface {

    private static final String USER_ENDPOINT = "/app/rest/users";
    RequestSpecification spec;

    public UncheckedUser(RequestSpecification spec) {
        this.spec = spec;
    }

    @Override
    public Response create(Object obj) {
        return given().spec(spec)
                .body(obj)
                .post(USER_ENDPOINT);
    }

    @Override
    public Response get(String id) {
        return given().spec(spec)
                .get(USER_ENDPOINT + "/id:" + id);
    }

    @Override
    public Response update(String id, Object obj) {
        return given()
                .spec(spec)
                .body(obj)
                .put(USER_ENDPOINT + "/username:" + id);
    }

    @Override
    public Response delete(String id) {
        return given().spec(spec)
                .delete(USER_ENDPOINT + "/username:" + id);
    }
}
