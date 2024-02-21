package com.github.dtitar.teamcity.api.spec;

import com.github.dtitar.teamcity.api.config.Config;
import com.github.dtitar.teamcity.api.models.User;
import com.github.viclovsky.swagger.coverage.FileSystemOutputWriter;
import com.github.viclovsky.swagger.coverage.SwaggerCoverageRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.nio.file.Paths;
import java.util.List;

import static com.github.viclovsky.swagger.coverage.SwaggerCoverageConstants.OUTPUT_DIRECTORY;
import static java.lang.String.format;

public class Specifications {
    private static Specifications spec;

    private Specifications() {
    }

    public static Specifications getSpec() {
        if (spec == null) {
            spec = new Specifications();
        }
        return spec;
    }

    private RequestSpecBuilder reqBuilder() {
        var requestBuilder = new RequestSpecBuilder();
        requestBuilder.setBaseUri(format("http://%s", Config.getProperty("host")));
        requestBuilder.addFilters(List.of(
                new RequestLoggingFilter(),
                new ResponseLoggingFilter(),
                new SwaggerCoverageRestAssured(new FileSystemOutputWriter(Paths.get("target/" + OUTPUT_DIRECTORY)))));
        requestBuilder.setContentType(ContentType.JSON);
        requestBuilder.setAccept(ContentType.JSON);
        return requestBuilder;
    }

    public RequestSpecification unauthSpec() {
        var requestBuilder = reqBuilder();
        return requestBuilder.build();
    }

    public RequestSpecification authSpec(User user) {
        var requestBuilder = reqBuilder();
        requestBuilder.setBaseUri(format("http://%s:%s@%s",
                user.getUsername(),
                user.getPassword(),
                Config.getProperty("host")));
        return requestBuilder.build();
    }

    public RequestSpecification superUserSpec() {
        var requestBuilder = reqBuilder();
        requestBuilder.setBaseUri(format("http://:%s@%s",
                Config.getProperty("superUserToken"),
                Config.getProperty("host")));
        return requestBuilder.build();
    }
}
