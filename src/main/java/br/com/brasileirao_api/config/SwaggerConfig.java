package br.com.brasileirao_api.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig  {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Brasileirao API - Scraping")
                        .description("API REST que obtem dados de partidas do Brasileirão em tempo real")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Edvaldo Vitor De Castro Souza")
                                .url("https://github.com/edvaldovitor250")
                                .email("edvaldovitor250@gmail.com")));
    }
}
