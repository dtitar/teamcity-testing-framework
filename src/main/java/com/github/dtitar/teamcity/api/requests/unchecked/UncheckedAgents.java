package com.github.dtitar.teamcity.api.requests.unchecked;

import com.github.dtitar.teamcity.api.requests.CrudInterface;
import com.github.dtitar.teamcity.api.requests.Request;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;
import static java.lang.String.format;

public final class UncheckedAgents extends Request implements CrudInterface {

    private static final String AGENTS_ENDPOINT = "/app/rest/agents";

    public UncheckedAgents(RequestSpecification spec) {
        super(spec);
    }

    @Override
    public Response create(Object object) {
        return null;
    }

    @Override
    public Response get(String locator) {
        return given().spec(spec)
                .param("locator", locator)
                .get(AGENTS_ENDPOINT);
    }

    @Override
    public Response update(String locator, Object object) {
        return null;
    }

    @Override
    public Response delete(String id) {
        return null;
    }

    public Response authorize(String agentId) {
        return given().spec(spec)
                .contentType("text/plain")
                .accept("*/*")
                .body("true")
                .put(format("%s/id:%s/authorized", AGENTS_ENDPOINT, agentId));
    }
}
