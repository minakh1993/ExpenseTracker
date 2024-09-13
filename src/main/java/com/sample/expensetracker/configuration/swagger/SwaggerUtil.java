package com.sample.expensetracker.configuration.swagger;

import com.google.common.io.Resources;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * @author M.khoshnevisan
 * @since 9/13/2024
 */
@Component
public class SwaggerUtil {

    public static String getMarkDownFileContent(String filePath) {
        String content;
        try {
            final URL url = SwaggerUtil.class.getClassLoader().getResource(filePath);
            content = Resources.toString(url, StandardCharsets.UTF_8);
        } catch (IOException | IllegalArgumentException e) {
            content = String.format("Markdown file %s not loaded", filePath);
        }
        return content;
    }
}