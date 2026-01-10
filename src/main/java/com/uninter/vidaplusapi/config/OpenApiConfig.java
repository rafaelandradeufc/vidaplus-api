package com.uninter.vidaplusapi.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Value("${openapi.servers.dev:http://localhost:8080}")
    private String devServerUrl;

    @Value("${openapi.servers.prod:https://api.vidaplus.com}")
    private String prodServerUrl;

    @Value("${app.name:VidaPlus API}")
    private String appName;

    @Value("${app.version:1.0.0}")
    private String appVersion;

    @Value("${app.description:API de Gestão Hospitalar e Serviços de Saúde}")
    private String appDescription;

    @Bean
    public OpenAPI customOpenAPI() {
        // Servidores
        Server devServer = new Server()
                .url(devServerUrl)
                .description("Servidor de Desenvolvimento");

        Server prodServer = new Server()
                .url(prodServerUrl)
                .description("Servidor de Produção");

        // Esquema de segurança JWT
        SecurityScheme securityScheme = new SecurityScheme()
                .name("JWT")
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .description("""
                    Para autenticar, use:
                    ```
                    Authorization: Bearer seu_token_jwt_aqui
                    ```
                    
                    **Como obter o token:**
                    1. Faça login em `/api/auth/login`
                    2. Copie somente o token retornado
                    3. Cole no campo abaixo
                    """)
                .in(SecurityScheme.In.HEADER);

        // Requisito de segurança global
        SecurityRequirement securityRequirement = new SecurityRequirement()
                .addList("JWT");


        // Informações da API
        Info info = new Info()
                .title(appName)
                .version(appVersion)
                .description(appDescription);

        return new OpenAPI()
                .info(info)
                .servers(List.of(devServer, prodServer))
                .addSecurityItem(securityRequirement)
                .components(new Components()
                        .addSecuritySchemes("JWT", securityScheme))
                .externalDocs(new io.swagger.v3.oas.models.ExternalDocumentation()
                        .url("https://docs.vidaplus.com"));
    }
}