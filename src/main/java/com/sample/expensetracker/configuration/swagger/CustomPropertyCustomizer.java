package com.sample.expensetracker.configuration.swagger;

import io.swagger.v3.core.converter.AnnotatedType;
import io.swagger.v3.oas.models.media.Schema;
import org.springdoc.core.customizers.PropertyCustomizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author M.khoshnevisan
 * @since 9/13/2024
 */
@Component
public class CustomPropertyCustomizer implements PropertyCustomizer {

    @Value("${document.lang}")
    private Locale docLang;

    @Override
    public Schema customize(Schema property, AnnotatedType type) {
        if (property != null && property.getDescription() != null && property.getDescription().startsWith("${")) {
            String substring = property.getDescription().substring(2, property.getDescription().length() - 1);
            String translation = ResourceBundle.getBundle("documentation.documentation", docLang).getString(substring);
            property.description(translation);
        }
        return property;
    }
}