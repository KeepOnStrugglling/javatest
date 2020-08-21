package com.javatest.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class JacksonUtil {

    private static final Logger log = LoggerFactory.getLogger(JacksonUtil.class);

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

    /**
     * json字符串转为List
     * @param json
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> List<T> json2list(String json, Class<T> clazz){
        if (StringUtils.isBlank(json) || clazz == null){
            return null;
        }
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(List.class, clazz);
        try {
            return objectMapper.readValue(json,javaType);
        } catch (Exception e) {
            log.error("json转为list出错！",e);
        }
        return null;
    }

    /**
     * 对复杂的json深度转为list
     * @param json
     * @return
     */
    public static List<Object> json2listDeep(String json){
        if (StringUtils.isBlank(json)){
            return null;
        }
        try {
            List<Object> list = objectMapper.readValue(json, List.class);
            for (Object obj : list) {
                if (obj instanceof String) {
                    String str = (String) obj;
                    if (str.startsWith("[")) {
                        obj = json2listDeep(str);
                    } else if (obj.toString().startsWith("{")) {
                        obj = json2mapDeep(str);
                    }
                }
            }
            return list;
        } catch (Exception e) {
            log.error("json转为复杂list出错！",e);
        }
        return null;
    }

    /**
     * json字符串转为Set
     * @param json
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> Set<T> json2set(String json, Class<T> clazz){
        if (StringUtils.isBlank(json) || clazz == null){
            return null;
        }
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(Set.class, clazz);
        try {
            return objectMapper.readValue(json,javaType);
        } catch (Exception e) {
            log.error("json转为Set出错！",e);
        }
        return null;
    }

    /**
     * json字符串转为简单的Map
     * @param json
     * @return
     * @throws Exception
     */
    public static Map<String, Object> json2map(String json) {
        if (StringUtils.isBlank(json)){
            return null;
        }
        try {
            return objectMapper.readValue(json,Map.class);
        } catch (Exception e) {
            log.error("json转为Map出错！",e);
        }
        return null;
    }

    /**
     * json字符串转为对象的Map
     * @param json
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> Map<String, T> json2map(String json, Class<T> clazz){
        if (StringUtils.isBlank(json) || clazz == null){
            return null;
        }
        try {
            Map<String, Map<String, Object>> map = objectMapper.readValue(json, new TypeReference<Map<String, T>>(){});
            Map<String, T> result = new HashMap<>();
            for (Map.Entry<String, Map<String, Object>> entry : map.entrySet()) {
                result.put(entry.getKey(), map2pojo(entry.getValue(), clazz));
            }
            return result;
        } catch (IOException e) {
            log.error("json转为pojoMap出错！",e);
        }
        return null;
    }

    /**
     * map转为pojo对象
     * @param value
     * @param clazz
     * @param <T>
     * @return
     */
    private static <T> T map2pojo(Map<String, Object> value, Class<T> clazz) {
        return objectMapper.convertValue(value,clazz);
    }


    public static Map<String, Object> json2mapDeep(String json) {
        if (json == null) {
            return null;
        }

        try {
            Map<String, Object> map = objectMapper.readValue(json, Map.class);
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                Object obj = entry.getValue();
                if (obj instanceof String) {
                    String str = ((String) obj);

                    if (str.startsWith("[")) {
                        List<?> list = json2listDeep(str);
                        map.put(entry.getKey(), list);
                    } else if (str.startsWith("{")) {
                        Map<String, Object> mapDeep = json2mapDeep(str);
                        map.put(entry.getKey(), mapDeep);
                    }
                }
            }
            return map;
        } catch (Exception e) {
            log.error("json转为复杂map出错！",e);
        }
        return null;
    }

}
