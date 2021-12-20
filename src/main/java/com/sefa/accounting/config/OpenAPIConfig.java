package com.sefa.accounting.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI customOpenAPI(
            @Value("${springdoc.customProps.title}") String title,
            @Value("${springdoc.customProps.version}") String version,
            @Value("${springdoc.customProps.author.name}") String name,
            @Value("${springdoc.customProps.author.email}") String email

    ) {
        return new OpenAPI().info(new Info()
                .title(title)
                .contact(new Contact().name(name).email(email))
                .version(version));
    }
}
