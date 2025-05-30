package com.persons.finder.config

import com.persons.finder.constants.ApiDocs.PERSONS_API_DESCRIPTION
import com.persons.finder.constants.ApiDocs.PERSONS_API_TITLE
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Contact
import io.swagger.v3.oas.models.info.Info
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OpenApiConfig {

    @Bean
    fun customOpenApi(): OpenAPI {
        val contact = Contact()
            .email("andoliv@gmail.com")
            .name("Anderson Oliveira")
            .url("https://linkedin.com/in/andoliv")

        return OpenAPI()
            .info(
                Info()
                    .title(PERSONS_API_TITLE)
                    .version("1.0.0")
                    .description(PERSONS_API_DESCRIPTION)
                    .contact(contact)
            )
    }
}