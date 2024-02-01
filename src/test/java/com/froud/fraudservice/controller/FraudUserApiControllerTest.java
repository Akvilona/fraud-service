package com.froud.fraudservice.controller;

import com.froud.fraudservice.entity.FraudUserEntity;
import com.froud.fraudservice.server.dto.FraudUser;
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
    void checkFraudUserByEmailFromApi() {
        //GIVEN
        userRepository.save(FraudUserEntity.builder()
            .email("email")
            .name("name")
            .build());

        //WHEN
        Boolean responseEntity = getFraudUserByEmail("email", 200);

        //THEN
        assertThat(responseEntity).isTrue();

    }

    @Test
    void deleteFraudUserByIdFromApi() {
        //GIVEN
        userRepository.save(FraudUserEntity.builder()
            .email("email")
            .name("name")
            .build());

        // WHEN
        deleteFraudUserByEmail("1", 200);

        // THEN
        assertThat(userRepository.findAll()).isEmpty();
    }

    @Test
    void postFraudUserById() {
        //GIVEN
        FraudUser fraudUserEntity = FraudUser.builder()
            .id(1L)
            .firstName("name")
            .userEmail("emal")
            .age(18)
            .build();

        FraudUser fraudUser = postFraudUserByEmail(fraudUserEntity, 200);

        assertThat(fraudUser)
            .isEqualTo(FraudUser.builder()
                .id(fraudUserEntity.getId())
                .firstName(fraudUserEntity.getFirstName())
                .userEmail(fraudUserEntity.getUserEmail())
                .build());
    }

    private FraudUser postFraudUserByEmail(final FraudUser fraudUser, final int status) {
        return webTestClient.post()
            .uri(uriBuilder -> uriBuilder
                .pathSegment("users")
                .build())
            .bodyValue(fraudUser)
            .exchange()
            .expectStatus().isEqualTo(status)
            .expectBody(FraudUser.class)
            .returnResult()
            .getResponseBody();
    }

    private void deleteFraudUserByEmail(final String id, final int status) {
        webTestClient.delete()
            .uri(uriBuilder -> uriBuilder
                .pathSegment("users", id)
                .build())
            .exchange()
            .expectStatus().isEqualTo(status)
            .expectBody()
            .returnResult();
    }

    private Boolean getFraudUserByEmail(final String email, final int status) {
        return webTestClient.get()
            .uri(uriBuilder -> uriBuilder
                .pathSegment("users")
                .queryParam("email", email)
                .build())
            .exchange()
            .expectStatus().isEqualTo(status)
            .expectBody(Boolean.class)
            .returnResult()
            .getResponseBody();
    }
}
