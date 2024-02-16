package com.github.dtitar.teamcity.api.requests;

import com.github.dtitar.teamcity.api.requests.checked.CheckedAgents;
import com.github.dtitar.teamcity.api.requests.checked.CheckedBuildType;
import com.github.dtitar.teamcity.api.requests.checked.CheckedProject;
import com.github.dtitar.teamcity.api.requests.checked.CheckedUser;
import io.restassured.specification.RequestSpecification;
import lombok.Getter;

@Getter
public class CheckedRequests {
    private CheckedUser userRequest;
    private CheckedProject projectRequest;
    private CheckedBuildType buildTypeRequest;
    private CheckedAgents agentsRequest;

    public CheckedRequests(RequestSpecification spec) {
        this.userRequest = new CheckedUser(spec);
        this.projectRequest = new CheckedProject(spec);
        this.buildTypeRequest = new CheckedBuildType(spec);
        this.agentsRequest = new CheckedAgents(spec);
    }
}
