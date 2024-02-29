package com.github.dtitar.teamcity.api.requests.checked;

import com.github.dtitar.teamcity.api.models.User;
import com.github.dtitar.teamcity.api.spec.Specifications;
import io.restassured.RestAssured;
import org.apache.http.HttpStatus;

public final class AuthRequest {

    private User user;

    public AuthRequest(User user) {
        this.user = user;
    }

    public String getCsrfToken() {
        return RestAssured
                .given()
                .spec(Specifications.getSpec()
                        .authSpec(user))
                .get("/authenticationTest.html?csrf")
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .asString();
    }
}
