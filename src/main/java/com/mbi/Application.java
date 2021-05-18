package com.mbi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * Application.
 */
@SpringBootApplication
public class Application {

    public static void main(final String[] args) {
        final var application = new SpringApplication(Application.class);
        application.setDefaultProperties(new AppConfig().getProperties());
        application.run(args);
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.OAS_30)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.mbi.api"))
                .paths(PathSelectors.any())
                .build();
    }
}

