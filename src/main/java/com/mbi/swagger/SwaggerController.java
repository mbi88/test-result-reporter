package com.mbi.swagger;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Configuration
public class SwaggerController {

    @Controller
    class SwaggerRedirection {
        @RequestMapping(method = GET, path = "/swagger", produces = "application/json")
        public String redirect() {
            return "redirect:/swagger-ui.html";
        }
    }
}