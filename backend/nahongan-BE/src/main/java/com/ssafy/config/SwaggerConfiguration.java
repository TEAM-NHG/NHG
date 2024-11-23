package com.ssafy.config;

import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

//Swagger-UI 확인
//http://localhost/swagger-ui.html

@Configuration
public class SwaggerConfiguration {

	@Bean
	public OpenAPI openAPI() {
		SecurityScheme jwtScheme = new SecurityScheme()
				.type(SecurityScheme.Type.HTTP)
				.scheme("bearer")
				.bearerFormat("JWT") // JWT 형식임을 명시
				.name("Authorization");

		SecurityRequirement securityRequirement = new SecurityRequirement().addList("BearerAuth");

		Info info = new Info().title("API 명세서").description(
				"<h3>팀 NHG 파이팅~~ </h3>")
				.version("v1").contact(new io.swagger.v3.oas.models.info.Contact().name("hissam")
						.email("hissam@ssafy.com").url("http://edu.ssafy.com"));
		return new OpenAPI()
				.components(new Components())
				.info(info)
				.addSecurityItem(securityRequirement);
	}

	@Bean
	public GroupedOpenApi publicApi() {
		return GroupedOpenApi.builder().group("ssafy-public").pathsToMatch("/api/**").pathsToExclude("/api/admin/**").build();
	}
	
	@Bean
	public GroupedOpenApi memberApi() {
		return GroupedOpenApi.builder().group("ssafy-user").pathsToMatch("/api/member/**").build();
	}

	@Bean
	public GroupedOpenApi companionBoardApi() {
		return GroupedOpenApi.builder().group("ssafy-board-comment").pathsToMatch("/api/companion-board/**").build();
	}

	@Bean
	public GroupedOpenApi tripApi() {
		return GroupedOpenApi.builder().group("ssafy-trip").pathsToMatch("/api/tripinfo/**").build();
	}

	@Bean
	public GroupedOpenApi aiPlannerApi() {
		return GroupedOpenApi.builder().group("ssafy-ai").pathsToMatch("/api/plan/**").build();
	}

	@Bean
	public GroupedOpenApi adminApi() {
		return GroupedOpenApi.builder().group("ssafy-admin").pathsToMatch("/api/admin/**").build();
	}

}