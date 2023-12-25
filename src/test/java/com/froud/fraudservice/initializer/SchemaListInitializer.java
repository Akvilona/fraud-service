package com.froud.fraudservice.initializer;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Collection;
import java.util.List;

@Slf4j
public class SchemaListInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    @SneakyThrows
    private static Collection<String> collectAllSchemas() {
        // the database schemas used in the project
        //SELECT
        //    nspname AS schema_name,
        //    tablename AS table_name
        //FROM
        //    pg_tables INNER JOIN pg_namespace ON pg_tables.schemaname = pg_namespace.nspname
        //WHERE
        //    pg_namespace.nspname NOT LIKE 'pg_%' AND
        //    pg_namespace.nspname <> 'information_schema'
        //ORDER BY
        //    schema_name, table_name;

        return List.of("public", "depo_data");
    }

    @Override
    public void initialize(final ConfigurableApplicationContext applicationContext) {
        TestPropertyValues.of(
            "spring.flyway.schemas=" + String.join(",", collectAllSchemas())
        ).applyTo(applicationContext.getEnvironment());
    }
}
