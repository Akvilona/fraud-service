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


    /* У меня тесты падают вот с такой ошибкой:
    Caused by: java.lang.IllegalStateException: Failed to verify that image ' :latest' is a compatible substitute for 'postgres'. This generally means that you are trying to use an image that Testcontainers has not been designed to use. If this is deliberate, and if you are confident that the image is compatible, you should declare compatibility in code using the `asCompatibleSubstituteFor` method. For example:
   DockerImageName myImage = DockerImageName.parse(" :latest").asCompatibleSubstituteFor("postgres");
    and then use `myImage` instead.
	at org.testcontainers.utility.DockerImageName.assertCompatibleWith(DockerImageName.java:279)
	at org.testcontainers.containers.PostgreSQLContainer.<init>(PostgreSQLContainer.java:56)
	at com.froud.fraudservice.initializer.PostgreSqlInitializer.<clinit>(PostgreSqlInitializer.java:15)
	... 89 common frames omitted
    16:03:22.451 [main] ERROR org.springframework.test.context.TestContextManager -- Caught exception while allowing TestExecutionListener [org.springframework.test.context.support.DependencyInjectionTestExecutionListener] to prepare test instance [com.froud.fraudservice.controller.FraudUserApiControllerTest@45545e7a]
    java.lang.NoClassDefFoundError: Could not initialize class com.froud.fraudservice.initializer.PostgreSqlInitializer
	at java.base/jdk.internal.misc.Unsafe.ensureClassInitialized0(Native Method)
	at java.base/jdk.internal.misc.Unsafe.ensureClassInitialized(Unsafe.java:1160)
	at java.base/jdk.internal.reflect.MethodHandleAccessorFactory.ensureClassInitialized(MethodHandleAccessorFactory.java:300)
	at java.base/jdk.internal.reflect.MethodHandleAccessorFactory.newConstructorAccessor(MethodHandleAccessorFactory.java:103)
	at java.base/jdk.internal.reflect.ReflectionFactory.newConstructorAccessor(ReflectionFactory.java:201)
	...
    Caused by: java.lang.ExceptionInInitializerError: Exception java.lang.IllegalStateException: Failed to verify that image ' :latest' is a compatible substitute for 'postgres'. This generally means that you are trying to use an image that Testcontainers has not been designed to use. If this is deliberate, and if you are confident that the image is compatible, you should declare compatibility in code using the `asCompatibleSubstituteFor` method. For example:
       DockerImageName myImage = DockerImageName.parse(" :latest").asCompatibleSubstituteFor("postgres");
    and then use `myImage` instead. [in thread "main"]
        at org.testcontainers.utility.DockerImageName.assertCompatibleWith(DockerImageName.java:279)
        at org.testcontainers.containers.PostgreSQLContainer.<init>(PostgreSQLContainer.java:56)
        at com.froud.fraudservice.initializer.PostgreSqlInitializer.<clinit>(PostgreSqlInitializer.java:15)
        ... 89 common frames omitted
    [ERROR] Tests run: 2, Failures: 0, Errors: 2, Skipped: 0, Time elapsed: 1.579 s <<< FAILURE! -- in com.froud.fraudservice.controller.FraudUserApiControllerTest
    [ERROR] com.froud.fraudservice.controller.FraudUserApiControllerTest.checkFraudUserByEmail -- Time elapsed: 0.031 s <<< ERROR!
     */
    @Test
    void checkFraudUserByEmail() {

        //GIVEN
        userRepository.save(FraudUserEntity.builder()
            .email(EMAIL)
            .build());

        //WHEN
        ResponseEntity<Boolean> responseEntity = fraudUserApiControllerUnderTest.checkFraudUserByEmail(EMAIL);

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
        ResponseEntity<Boolean> responseEntity = fraudUserApiControllerUnderTest.checkFraudUserByEmail(EMAIL);

        //THEN
        assertThat(responseEntity.getBody())
            .isTrue();

    }

}
