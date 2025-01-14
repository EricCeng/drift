package org.drift.common.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.drift.common.exception.ApiException;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @author jiakui_zeng
 * @date 2025/1/8 16:37
 */
@Slf4j
public class JsonUtil {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    /**
     * 日期格式
     */
    private static final String STANDARD_FORMAT = "yyyy-MM-dd HH:mm:ss";

    static {
        // 对象的所有字段全部列入
        objectMapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);
        // 取消默认转换timestamps形式
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        // 忽略空Bean转json的错误
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        // 所有的日期格式都统一为以下的样式，即yyyy-MM-dd HH:mm:ss
        // 是否线程安全？
        objectMapper.setDateFormat(new SimpleDateFormat(STANDARD_FORMAT));
        // 忽略 在json字符串中存在，但是在java对象中不存在对应属性的情况。防止错误
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // 设置 转换Java Instant
        objectMapper.registerModule(new JavaTimeModule());
    }

    @SneakyThrows
    public static byte[] toJsonByte(Object object) {
        return objectMapper.writeValueAsBytes(object);
    }

    public static <T> T parseObject(String jsonString, Class<T> clazz) {
        if (StringUtils.hasLength(jsonString) && !ObjectUtils.isEmpty(clazz)) {
            try {
                return objectMapper.readValue(jsonString, clazz);
            } catch (JsonProcessingException e) {
                log.error("parse json string to object failed", e);
            }
        }
        return null;
    }

    public static <T> T parseObject(String jsonString, TypeReference<T> typeReference) {
        if (StringUtils.hasLength(jsonString) && !ObjectUtils.isEmpty(typeReference)) {
            try {
                return objectMapper.readValue(jsonString, typeReference);
            } catch (JsonProcessingException e) {
                log.error("parse json string to object failed", e);
            }
        }
        return null;
    }

    public static JsonNode parseObject(String jsonString) {
        if (StringUtils.hasLength(jsonString)) {
            try {
                return objectMapper.readValue(jsonString, JsonNode.class);
            } catch (JsonProcessingException e) {
                log.error("parse json string to object failed", e);
            }
        }
        return null;
    }

    public static <T> List<T> parseList(String jsonString, Class<T> clazz) {
        if (StringUtils.hasLength(jsonString) && !ObjectUtils.isEmpty(clazz)) {
            try {
                return objectMapper.readValue(jsonString, objectMapper.getTypeFactory().constructCollectionType(List.class, clazz));
            } catch (JsonProcessingException e) {
                log.error("parse json string to object list failed", e);
            }
        }
        return null;
    }

    public static String toJson(Object object) {
        String jsonStr = "";
        if (object != null) {
            try {
                jsonStr = objectMapper.writeValueAsString(object);
            } catch (JsonProcessingException e) {
                log.error("parse Object to json String failed", e);
            }
        }
        return jsonStr;
    }

    public static boolean checkObjAllFieldsIsNull(Object object) {
        if (object == null) {
            return true;
        }
        boolean isNull = true;
        try {
            for (Field field : object.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                if (field.get(object) != null) {
                    isNull = false;
                    break;
                }
            }
        } catch (IllegalAccessException e) {
            throw new ApiException(e.getMessage());
        }
        return isNull;
    }
}
