package com.froud.fraudservice.controller;

import com.froud.fraudservice.entity.FraudUserEntity;
import com.froud.fraudservice.support.IntegrationTestBase;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

class FraudUserApiControllerTest extends IntegrationTestBase {

    @Test
    void checkFraudUserByEmail() {
        //GIVEN
        userRepository.save(FraudUserEntity.builder()
            .email("email")
            .build());

        //WHEN
        ResponseEntity<Boolean> responseEntity = fraudUserApiControllerUnderTest._checkFraudUserByEmail("email");

        //THEN
        assertThat(responseEntity.getBody())
            .isTrue();

    }

    @Test
    void checkFraudUserByEmail2() {
        //GIVEN
        userRepository.save(FraudUserEntity.builder()
            .email("email")
            .build());

        //WHEN
        ResponseEntity<Boolean> responseEntity = fraudUserApiControllerUnderTest._checkFraudUserByEmail("email");

        //THEN
        assertThat(responseEntity.getBody())
            .isTrue();

    }

}
