package com.javatest.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;

public class JsonUtil {

    private static final Logger log = LoggerFactory.getLogger(JsonUtil.class);

    private static final ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper();
        // 去掉默认的时间戳格式
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        // 忽略空bean转json的错误
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        // 序列化时，日期的统一格式
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

        // 空值不序列化
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        // 反序列化时，属性不存在的兼容处理
        objectMapper.getDeserializationConfig().withoutFeatures(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        // 忽略在json字符串中存在,但是在java对象中不存在对应属性的情况
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    /**
     * 对象转为json字符串
     * @param obj
     * @param <T>
     * @return
     */
    public static <T> String obj2json(T obj){
        if (obj == null){
            return null;
        }
        try {
            return obj instanceof String ? (String) obj : objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            log.error("对象转为json出错！",e);
        }
        return null;
    }

    /**
     * json字符串转为pojo对象
     * @param json json字符串
     * @param clazz 被转对象的class
     * @param <T>
     * @return
     */
    public static <T> T json2pojo(String json, Class<T> clazz){
        if (StringUtils.isBlank(json) || clazz == null){
            return null;
        }
        try {
            return clazz.equals(String.class) ? (T)json : objectMapper.readValue(json,clazz);
        } catch (Exception e) {
            log.error("json转为对象出错！",e);
        }
        return null;
    }

    /**
     * json字符串转为pojo对象
     * @param json json字符串
     * @param typeReference 被转对象的引用类型
     * @param <T>
     * @return
     */
    public static <T> T json2pojo(String json, TypeReference<T> typeReference){
        if (StringUtils.isBlank(json) || typeReference == null){
            return null;
        }
        try {
            return (T)(typeReference.getType().equals(String.class) ? json : objectMapper.readValue(json,typeReference));
        } catch (Exception e) {
            log.error("json转为对象出错！",e);
        }
        return null;
    }


}
