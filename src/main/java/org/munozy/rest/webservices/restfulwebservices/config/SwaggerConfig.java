package org.munozy.rest.webservices.restfulwebservices.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    public static final Contact CONTACT = new Contact("Yupanqui Munoz", "https://munozy.github.io/", "");

    public static final ApiInfo API_INFO =
            new ApiInfo("Rest Demo Api Documentation", "Rest Demo Api Documentation",
                    "1.0", "urn:tos" ,CONTACT, "Apache 2.0",
                    "http://www.apache.org/licenses/LICENSE-2.0", new ArrayList());

    public static final Set<String> PRODUCES_AND_CONSUMES  = new HashSet<>(Arrays.asList("application/json",
            "application/xml"));

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(API_INFO)
                .produces(PRODUCES_AND_CONSUMES)
                .consumes(PRODUCES_AND_CONSUMES);
    }

}
