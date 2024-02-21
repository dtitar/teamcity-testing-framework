package com.github.dtitar.teamcity.api.requests;

import com.github.dtitar.teamcity.api.requests.unchecked.UncheckedAgents;
import com.github.dtitar.teamcity.api.requests.unchecked.UncheckedBuildConfig;
import com.github.dtitar.teamcity.api.requests.unchecked.UncheckedProject;
import com.github.dtitar.teamcity.api.requests.unchecked.UncheckedUser;
import io.restassured.specification.RequestSpecification;
import lombok.Getter;

@Getter
public class UncheckedRequests {
    private UncheckedAgents agentsRequest;
    private UncheckedUser userRequest;
    private UncheckedProject projectRequest;
    private UncheckedBuildConfig buildConfigRequest;

    public UncheckedRequests(RequestSpecification spec) {
        this.userRequest = new UncheckedUser(spec);
        this.projectRequest = new UncheckedProject(spec);
        this.buildConfigRequest = new UncheckedBuildConfig(spec);
        this.agentsRequest = new UncheckedAgents(spec);
    }
}
