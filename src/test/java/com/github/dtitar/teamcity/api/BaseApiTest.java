package com.github.dtitar.teamcity.api;

import com.github.dtitar.teamcity.BaseTest;
import com.github.dtitar.teamcity.api.requests.CheckedRequests;
import com.github.dtitar.teamcity.api.requests.UncheckedRequests;
import com.github.dtitar.teamcity.api.spec.Specifications;

public class BaseApiTest extends BaseTest {

    protected CheckedRequests checkedWithSuperUser = new CheckedRequests(Specifications.getSpec()
            .superUserSpec());
    protected UncheckedRequests unCheckedWithSuperUser = new UncheckedRequests(Specifications.getSpec()
            .superUserSpec());
}
