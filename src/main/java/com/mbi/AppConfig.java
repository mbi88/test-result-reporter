package com.mbi;

import java.util.Properties;

class AppConfig {

    private final String url;
    private final String user;
    private final String password;
    private final String dbHost;
    private final String database;

    AppConfig() {
        this.user = System.getenv("POSTGRES_USER");
        this.password = System.getenv("POSTGRES_PASSWORD");
        this.dbHost = System.getenv("POSTGRES_HOST");
        this.database = System.getenv("POSTGRES_DB");
        this.url = String.format("jdbc:postgresql://%s:5432/%s?createDatabaseIfNotExist=true", dbHost, database);
    }

    Properties getProperties() {
        Properties props = new Properties();
        props.put("spring.datasource.url", url);
        props.put("spring.datasource.username", user);
        props.put("spring.datasource.password", password);

        return props;
    }
}
