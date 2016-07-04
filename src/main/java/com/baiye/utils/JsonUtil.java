package com.baiye.utils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by Baiye on 2016/7/4.
 */
public final class JsonUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(JsonUtil.class);

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static <T> String toJson(T obj)
    {
        String json;
        try
        {
            json = OBJECT_MAPPER.writeValueAsString(obj);
        } catch (Exception e) {
            LOGGER.error("conver POJO to JSON failure",e);
            throw new RuntimeException(e);
        }
        return json;
    }

    public static <T> T fromJson(String json,Class<T> type)
    {
        T pojo;
        try
        {
            pojo = OBJECT_MAPPER.readValue(json,type);
        } catch (Exception e) {
            LOGGER.error("convert JSON to POJO failure",e);
            throw new RuntimeException(e);
        }
        return pojo;
    }
}
