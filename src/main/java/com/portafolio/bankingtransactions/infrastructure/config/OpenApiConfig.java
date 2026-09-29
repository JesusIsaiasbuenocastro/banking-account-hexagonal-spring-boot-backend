package com.portafolio.bankingtransactions.infrastructure.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI bankingTransactionsOpenApi(){
        return new OpenAPI()
                .info(new Info()
                        .title("Banking Transactions API")
                        .description("API REST para la gestión de cuentas bancarias y sus transacciones (depósitos y retiros " +
                                "implementa una arquitectura hexagonal")
                        .version("v1.0.0")
                        .contact( new Contact()
                                .name("Portafolio")
                                .url("https://github.com/JesusIsaiasbuenocastro/banking-account-hexagonal-spring-boot-backend"))
                );
    }
}
