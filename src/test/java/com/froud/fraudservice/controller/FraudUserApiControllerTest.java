package com.froud.fraudservice.controller;

import com.froud.fraudservice.entity.FraudUserEntity;
import com.froud.fraudservice.initializer.PostgreSqlInitializer;
import com.froud.fraudservice.initializer.SchemaListInitializer;
import com.froud.fraudservice.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("LineLength")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(initializers = {
    PostgreSqlInitializer.class,
    SchemaListInitializer.class
})
class FraudUserApiControllerTest {

    private static final String EMAIL = "email";

    @Autowired
    private FraudUserApiController fraudUserApiControllerUnderTest;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void beforeEach() {
        getTables().stream()
            .map(this::countRecordsInTable)
            .forEach(count -> assertThat(count)
                .isZero());
    }

    protected long countRecordsInTable(final String tableName) {
        final var queryResult = jdbcTemplate.queryForObject("select count(*) from " + tableName, Long.class);
        return Objects.requireNonNullElse(queryResult, 0L);
    }

    @AfterEach
    void truncateTables() {
        String sql = "TRUNCATE TABLE "
            + String.join(", ", getTables())
            + " RESTART IDENTITY CASCADE";
        jdbcTemplate.execute(sql);
    }

    private List<String> getTables() {
        /*
        Эта выборка получает имя схемы и мия таблицы
        SELECT
                nspname AS schema_name,
                tablename AS table_name
        FROM
                pg_tables
                INNER JOIN pg_namespace ON pg_tables.schemaname = pg_namespace.nspname
        WHERE
                pg_namespace.nspname NOT LIKE 'pg_%' AND
                pg_namespace.nspname <> 'information_schema'
        ORDER BY
                schema_name, table_name;
        */
        return List.of("public.fraud_users", "depo_data.debt", "depo_data.depositor", "depo_data.pay");
    }

    @Test
    void checkFraudUserByEmail() {

        //GIVEN
        userRepository.save(FraudUserEntity.builder()
            .email(EMAIL)
            .build());

        //WHEN
        ResponseEntity<Boolean> responseEntity = fraudUserApiControllerUnderTest._checkFraudUserByEmail(EMAIL);

        //THEN
        assertThat(responseEntity.getBody())
            .isTrue();

    }

    @Test
    void checkFraudUserByEmail2() {
        //GIVEN
        userRepository.save(FraudUserEntity.builder()
            .email(EMAIL)
            .build());

        //WHEN
        ResponseEntity<Boolean> responseEntity = fraudUserApiControllerUnderTest._checkFraudUserByEmail(EMAIL);

        //THEN
        assertThat(responseEntity.getBody())
            .isTrue();

    }

}
