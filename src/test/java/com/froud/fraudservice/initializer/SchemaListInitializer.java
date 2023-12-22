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
        return List.of("public", "depo_data");
    }

    @Override
    public void initialize(final ConfigurableApplicationContext applicationContext) {
        TestPropertyValues.of(
            "spring.flyway.schemas=" + String.join(",", collectAllSchemas())
        ).applyTo(applicationContext.getEnvironment());
    }
}
