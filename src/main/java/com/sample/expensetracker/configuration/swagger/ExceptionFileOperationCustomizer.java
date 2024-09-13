package com.sample.expensetracker.configuration.swagger;

import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;

import java.util.Map;

/**
 * @author M.khoshnevisan
 * @since 9/13/2024
 */
@Component
public class ExceptionFileOperationCustomizer implements OperationCustomizer {

    @Value("${document.lang}")
    private String locale;

    private static final String ERROR_FILE_PATH = "documentation/md/error";
    private static final String HTTP_SERVER_EXCEPTION_REF = "#/components/schemas/HttpServerException";


    @Override
    public Operation customize(Operation operation, HandlerMethod handlerMethod) {
        ApiResponses responses = operation.getResponses();
        for (Map.Entry<String, ApiResponse> responseEntry : responses.entrySet()) {
            String statusCode = responseEntry.getKey();
            ApiResponse value = responseEntry.getValue();
            String description = value.getDescription();
            Content content = value.getContent();
            if (!statusCode.equals("200")) {
                if (content != null) {
                    addServerExceptionSchema(content);
                }
                if (description != null && description.startsWith("md:")) {
                    String messageFile = description.substring(3);
                    description = getMdFileContent(messageFile);
                    value.setDescription(description);
                }
            }
        }
        return operation;
    }

    private String getMdFileContent(String mdFileName) {
        String messagePath = ERROR_FILE_PATH + "/" + locale + "/" + mdFileName;
        String description = SwaggerUtil.getMarkDownFileContent(messagePath);
        return description;
    }

    private void addServerExceptionSchema(Content content) {
        content.get("application/json").getSchema().set$ref(HTTP_SERVER_EXCEPTION_REF);
    }
}