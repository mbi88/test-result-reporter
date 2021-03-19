package com.mbi.swagger;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * Swagger.
 */
@Configuration
public class SwaggerController {

    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/swagger-ui.html**")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    /**
     * Redirects to expected url.
     */
    @Controller
    static class SwaggerRedirection {
        @RequestMapping(method = GET, path = "/swagger", produces = "application/json")
        public String redirect() {
            return "redirect:/swagger-ui.html";
        }
    }
}
