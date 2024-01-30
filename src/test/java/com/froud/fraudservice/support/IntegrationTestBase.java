package com.froud.fraudservice.support;

import com.froud.fraudservice.controller.FraudUserApiController;
import com.froud.fraudservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;

import java.util.Set;

@AutoConfigureWebTestClient(timeout = "360000")
public class IntegrationTestBase extends DatabaseAwareTestBase {
    @Autowired
    protected FraudUserApiController fraudUserApiControllerUnderTest;
    @Autowired
    protected UserRepository userRepository;

    @Override
    protected Set<String> getTables() {
        return Set.of("public.fraud_users", "depo_data.debt", "depo_data.depositor", "depo_data.pay");
    }
}
