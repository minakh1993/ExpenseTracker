package com.sample.expensetracker.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.introspect.ClassIntrospector;
import com.sample.expensetracker.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Map;

/**
 * @author M.khoshnevisan
 * @since 9/12/2024
 */
@Slf4j
public class JsonUtil {

    private static ObjectMapper OBJECT_MAPPER = null;
    public static final String DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ssZ";

    public static ObjectMapper getMapperInstance() {
        if (OBJECT_MAPPER == null) {
            OBJECT_MAPPER = Jackson2ObjectMapperBuilder.json().build();
            fillDefaultConfig(OBJECT_MAPPER);
        }
        return OBJECT_MAPPER;
    }

    private static void fillDefaultConfig(ObjectMapper objectMapper) {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.getSerializerProvider().setNullKeySerializer(new MyNullKeySerializer());
        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        objectMapper.setDateFormat(new SimpleDateFormat(DATE_PATTERN));
    }

    public static <T> T jsonToObject(String string, Class<T> type) {
        try {
            return getMapperInstance().readValue(string, type);
        } catch (Exception e) {
            log.error("exception in converting json to object: {}", e);
            throw new ServiceException("exception in converting Json to object with message: " + e.getMessage());
        }
    }

    public static class MyNullKeySerializer extends JsonSerializer<Object> {
        @Override
        public void serialize(Object nullKey, JsonGenerator jsonGenerator, SerializerProvider unused)
                throws IOException {
            jsonGenerator.writeFieldName("nullKey");
        }
    }

    public static <T> Map<String, Object> convertToExceptionParam(T object, Class ignorePropertiesRestException) {
        return jsonToMap(objectToJsonForRestException(object, ignorePropertiesRestException));
    }

    public static Map<String, Object> jsonToMap(String jsonString) {
        try {
            TypeReference<Map<String, Object>> typeRef = new TypeReference<>() {
            };
            return getMapperInstance().readValue(jsonString, typeRef);
        } catch (Exception e) {
            throw new ServiceException("error in converting Json to map JSONObject", e);
        }
    }

    public static <T> String objectToJsonForRestException(T object, Class ignorePropertiesRestException) {
        ObjectMapper objectMapperForRestException = createObjectMapperWthMixedInResolver(object, ignorePropertiesRestException);
        try {
            return objectMapperForRestException.writer().writeValueAsString(object);
        } catch (Exception e) {
            throw new ServiceException("error in converting object to Json", e);
        }
    }

    public static <T> ObjectMapper createObjectMapperWthMixedInResolver(T object, Class ignoreProperties) {
        ObjectMapper objectMapperWithMixedIn = getMapperInstance().copy();
        objectMapperWithMixedIn.setMixInResolver(new ClassIntrospector.MixInResolver() {
            @Override
            public Class<?> findMixInClassFor(Class<?> cls) {
                return ignoreProperties;
            }

            @Override
            public ClassIntrospector.MixInResolver copy() {
                return this;
            }
        });
        objectMapperWithMixedIn.addMixIn(object.getClass(), ignoreProperties);
        return objectMapperWithMixedIn;
    }
}