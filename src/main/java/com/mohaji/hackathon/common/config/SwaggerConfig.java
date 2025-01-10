package com.mohaji.hackathon.common.config;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.*;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "NOVOWEL",
                        url = "https://github.com/hackathone-mohaji/BackEnd"
                ),
                description = "NOVOWEL application",
                title = "NOVOWEL",
                version = "v0.0.1"
        ),
        security = {
                @SecurityRequirement(name = "bearerAuth")
        }
)
@SecurityScheme(
        name = "bearerAuth",
        description = "JWT를 사용한 인증",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        Set<String> protocols = new HashSet<>();
        protocols.add("http");

        return new OpenAPI()
                .info(new io.swagger.v3.oas.models.info.Info().title("NOVOWEL")
                        .description("NOVOWEL application")
                        .version("v0.0.1")
                        .contact(new io.swagger.v3.oas.models.info.Contact()
                                .name("VoQal")
                                .url("https://github.com/hackathone-mohaji/BackEnd")))
                .servers(List.of(
                        new io.swagger.v3.oas.models.servers.Server().url("http://localhost:8888").description("Local HTTP server")

                ));
    }

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("public")
                .pathsToMatch("/**")
                .build();
    }


}

