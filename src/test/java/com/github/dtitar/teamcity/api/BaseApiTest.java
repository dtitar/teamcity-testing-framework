package com.github.dtitar.teamcity.api;

import com.github.dtitar.teamcity.BaseTest;
import com.github.dtitar.teamcity.api.generators.TestData;
import com.github.dtitar.teamcity.api.generators.TestDataGenerator;
import com.github.dtitar.teamcity.api.generators.TestDataStorage;
import com.github.dtitar.teamcity.api.requests.CheckedRequests;
import com.github.dtitar.teamcity.api.requests.UncheckedRequests;
import com.github.dtitar.teamcity.api.spec.Specifications;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseApiTest extends BaseTest {

    CheckedRequests checkedWithSuperUser = new CheckedRequests(Specifications.getSpec()
            .superUserSpec());
    UncheckedRequests unCheckedWithSuperUser = new UncheckedRequests(Specifications.getSpec()
            .superUserSpec());
}
