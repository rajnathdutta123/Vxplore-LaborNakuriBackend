package com.app.LaborNakuriBackend.config;



import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

@Bean
public OpenAPI customOpenAPI() {
//    return new OpenAPI()
//            .info(new Info()
//                    .title("My Spring Boot API")
//                    .version("1.0")
//                    .description("This is a sample Spring Boot application with Swagger OpenAPI"));
    return new OpenAPI()
            .addSecurityItem(new SecurityRequirement().addList("basicAuth"))
            .components(new io.swagger.v3.oas.models.Components()
                    .addSecuritySchemes("basicAuth", new SecurityScheme()
                            .type(SecurityScheme.Type.HTTP)
                            .scheme("basic")
                            .description("Basic Authentication for Swagger UI")));

}
}

