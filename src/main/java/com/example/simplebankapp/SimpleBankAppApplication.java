package com.example.simplebankapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@SpringBootApplication
@Configuration
@EnableSwagger2
public class SimpleBankAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(SimpleBankAppApplication.class, args);
    }



    public static final String AUTHORIZATION_HEADER = "Authorization";

    private ApiKey apiKey(){
        return new ApiKey("JWT", AUTHORIZATION_HEADER, "header");

    }
    private SecurityContext securityContext(){
        return SecurityContext.builder().securityReferences(defaultAuth()).build();
    }


    @Bean
    public Docket SwaggerConfiguration() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .securityContexts(Arrays.asList(securityContext()))
                .securitySchemes(Arrays.asList(apiKey()))
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())

                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "Hotel Booking API",
                "<h2 style='margin:2px'> Author: Ofunrein Iyaghe.</h2> " +
                        "<article> <h2>Description </h2>" +
                        "The Api endpoints help with performing banking operations<br/>" +
                        "<h2>Demo Usage</h2> " +
                        "<h1>Authorization Header setting: Bearer +token</h1> " +
                        "<article>.<br/>",
                "1.0",
                "Free to use",
                new springfox.documentation.service.Contact("Ofunrein Iyaghe", "https://github.com/iyage/simple-banking-api", "yahg.concept@gmail.com"),
                "API License",
                "https://github.com/iyage",
                Collections.emptyList());
    }


    private List<SecurityReference> defaultAuth(){
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Arrays.asList(new SecurityReference("JWT", authorizationScopes));
    }
}
