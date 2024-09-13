package com.sample.expensetracker.configuration.swagger;

import com.sample.expensetracker.advice.dto.HttpServerException;
import com.tosan.http.server.starter.util.Constants;
import io.swagger.v3.core.converter.AnnotatedType;
import io.swagger.v3.core.converter.ModelConverters;
import io.swagger.v3.core.converter.ResolvedSchema;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.parameters.HeaderParameter;
import lombok.AllArgsConstructor;
import org.springdoc.core.customizers.GlobalOpenApiCustomizer;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

/**
 * @author M.khoshnevisan
 * @since 9/13/2024
 */
@Configuration
@AllArgsConstructor
@PropertySource(value = "classpath:documentation/documentation_${document.lang}.properties", encoding = "UTF-8")
public class SwaggerConfiguration {

    private final BuildProperties buildProperties;
    private final Environment environment;
    private final ExceptionFileOperationCustomizer exceptionFileOperationCustomizer;

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("expenseTrackerApi")
                .packagesToScan("com.sample.expensetracker")
                .addOperationCustomizer(exceptionFileOperationCustomizer)
                .build();
    }

    @Bean
    public OpenAPI expenseOpenApi() {
        return new OpenAPI()
                .components(new Components()
                        .addSchemas("HttpServerException", addSchema(HttpServerException.class, "http exception")))
                .info(new Info().title(environment.getProperty("title"))
                        .description("expense tracker application")
                        .version(buildProperties.getVersion()));
    }

    private Schema addSchema(Class className, String description){
        AnnotatedType annotatedType = new AnnotatedType(className);
        annotatedType.setSchemaProperty(false);
        ResolvedSchema resolvedSchema = ModelConverters.getInstance()
                .resolveAsResolvedSchema(annotatedType.resolveAsRef(false));
        return resolvedSchema.schema.description(description);
    }

    @Bean
    public GlobalOpenApiCustomizer globalHeaderOpenApiCustomizer() {
        GlobalOpenApiCustomizer context = openApi ->
                openApi.getPaths().values().stream().flatMap(pathItem -> pathItem.readOperations().stream())
                        .forEach(operation -> {
                            operation.addParametersItem(new HeaderParameter().name(Constants.CONTENT_LANGUAGE));
                            operation.addParametersItem(new HeaderParameter().name(Constants.X_REQUEST_ID));
                            operation.addParametersItem(new HeaderParameter().name(Constants.X_FORWARDED_FOR));
                            operation.addParametersItem(new HeaderParameter().name(Constants.X_USER_IP));
                        });
        return context;
    }
}