package com.backend_learning.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;

@Configuration
public class SwaggerConfig {

	//for sending jwt token
    public static final String AUTHORIZATION_HEADER = "Authorization";

    // Grouping APIs with a specific path
    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("public-apis") // Group name for the API documentation
                .pathsToMatch("/api/**") // Adjust this to your API paths
                .build();
    }

    // Adding custom metadata and JWT security scheme to the API documentation
    @Bean
    public OpenAPI customOpenAPI() {
        // Define the security scheme for JWT
        SecurityScheme securityScheme = new SecurityScheme()
                .name(AUTHORIZATION_HEADER)
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .in(SecurityScheme.In.HEADER);

        // Define the security requirement for the APIs
        SecurityRequirement securityRequirement = new SecurityRequirement()
                .addList(AUTHORIZATION_HEADER);

        return new OpenAPI()
                .info(new Info()
                        .title("Blogging Application : Backend Course")
                        .description("This project is developed by Shivaraj")
                        .version("1.0")
                        .termsOfService("Terms of Service")
                        .contact(new Contact()
                                .name("Shivaraj")
                                .url("https://abc.com")
                                .email("shivaraj@gmail.com"))
                        .license(new License()
                                .name("License of APIs")
                                .url("API license URL")))
                .addSecurityItem(securityRequirement) // Attach security requirement
                .components(new io.swagger.v3.oas.models.Components().addSecuritySchemes(AUTHORIZATION_HEADER, securityScheme)); // Attach security scheme
    }
}
