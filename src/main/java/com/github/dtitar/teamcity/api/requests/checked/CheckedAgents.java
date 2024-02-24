package com.github.dtitar.teamcity.api.requests.checked;

import com.github.dtitar.teamcity.api.models.Agents;
import com.github.dtitar.teamcity.api.requests.CrudInterface;
import com.github.dtitar.teamcity.api.requests.Request;
import com.github.dtitar.teamcity.api.requests.unchecked.UncheckedAgents;
import io.restassured.parsing.Parser;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;

public final class CheckedAgents extends Request implements CrudInterface {


    public CheckedAgents(RequestSpecification spec) {
        super(spec);
    }

    @Override
    public Object create(Object object) {
        return null;
    }

    @Override
    public Agents get(String locator) {
        return new UncheckedAgents(spec).get(locator)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .as(Agents.class);
    }

    @Override
    public Object update(String locator, Object object) {
        return null;
    }

    @Override
    public Object delete(String id) {
        return null;
    }

    public boolean authorize(String agentId) {
        var status = new UncheckedAgents(spec).authorize(agentId)
                .then()
                .using()
                .parser("text/plain", Parser.TEXT)
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .asString();
        return Boolean.valueOf(status);
    }
}
