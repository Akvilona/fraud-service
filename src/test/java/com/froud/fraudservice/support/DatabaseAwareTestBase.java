package com.froud.fraudservice.support;

import com.froud.fraudservice.initializer.PostgreSqlInitializer;
import com.froud.fraudservice.initializer.SchemaListInitializer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Objects;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(initializers = {
    PostgreSqlInitializer.class,
    SchemaListInitializer.class
})
@ActiveProfiles("test")
public abstract class DatabaseAwareTestBase {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private TransactionTemplate transactionTemplate;

    protected abstract Set<String> getTables();

    protected void executeInTransaction(final Runnable runnable) {
        transactionTemplate.execute(status -> {
            runnable.run();
            return null;
        });
    }

    @BeforeEach
    void check() {
        getTables().stream()
            .map(this::countRecordsInTable)
            .forEach(count -> assertThat(count)
                .isZero());
    }

    @AfterEach
    void truncateTables() {
        String sql = "TRUNCATE TABLE "
            + String.join(", ", getTables())
            + " RESTART IDENTITY CASCADE";
        jdbcTemplate.execute(sql);
    }

    protected long countRecordsInTable(final String tableName) {
        final var queryResult = jdbcTemplate.queryForObject("select count(*) from " + tableName, Long.class);
        return Objects.requireNonNullElse(queryResult, 0L);
    }

}
