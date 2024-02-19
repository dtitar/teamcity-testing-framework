package com.github.dtitar.teamcity.api;

import com.github.dtitar.teamcity.api.models.Agent;
import org.awaitility.Awaitility;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class SetupAgentTest extends BaseApiTest {

    @Test
    public void authorizeAgent() {
        var agentId = getUnauthorizedAgent().getId();
        softly.assertThat(checkedWithSuperUser.getAgentsRequest()
                        .authorize(agentId))
                .isEqualTo(true);
    }

    private Agent getUnauthorizedAgent() {
        var agents = new AtomicReference<List<Agent>>();
        Awaitility.await()
                .atMost(Duration.ofSeconds(20))
                .pollInterval(Duration.ofSeconds(1))
                .until(() -> {
                    agents.set(checkedWithSuperUser.getAgentsRequest()
                            .get("authorized:false")
                            .getAgent());
                    return !agents.get()
                            .isEmpty();
                });
        return agents.get()
                .get(0);
    }
}
