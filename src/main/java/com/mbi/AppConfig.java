package com.mbi;

import java.util.Properties;

/**
 * Application configuration.
 */
class AppConfig {

    private final String url;
    private final String user;
    private final String password;

    AppConfig() {
        this.user = System.getenv("POSTGRES_USER");
        this.password = System.getenv("POSTGRES_PASSWORD");
        final var dbHost = System.getenv("POSTGRES_HOST");
        final var database = System.getenv("POSTGRES_DB");
        this.url = String.format("jdbc:postgresql://%s:5432/%s?createDatabaseIfNotExist=true", dbHost, database);
    }

    public Properties getProperties() {
        final var props = new Properties();
        props.put("spring.datasource.url", url);
        props.put("spring.datasource.username", user);
        props.put("spring.datasource.password", password);

        return props;
    }
}
