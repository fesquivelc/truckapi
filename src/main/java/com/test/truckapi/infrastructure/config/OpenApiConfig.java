package com.test.truckapi.infrastructure.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Trucks Management API")
                        .version("1.0")
                        .description("API para la gestión de camiones de transporte de escombros")
                        .contact(new Contact()
                                .name("Team")
                                .email("team@company.com"))
                );
    }
}
