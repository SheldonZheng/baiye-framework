package com.baiye.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Baiye on 2016/6/16.
 */
public class PropsUtil{

    private static final Logger logger = LoggerFactory.getLogger(PropsUtil.class);

    private Properties props = null;

    public PropsUtil(String propsPath)
    {
        this(propsPath,"UTF-8");
    }

    public PropsUtil(String propsPath, String encoding)
    {
        InputStream is = null;
        try
        {
            if(StringUtils.isBlank(propsPath))
                throw new IllegalArgumentException();
            String suffix = ".properties";
            if(propsPath.lastIndexOf(suffix) == -1)
                propsPath += suffix;
            is = Thread.currentThread().getContextClassLoader()
                    .getResourceAsStream(propsPath);
            if(is != null)
            {
                props = new Properties();
                props.load(new InputStreamReader(is,encoding));
            }

        }
          catch (UnsupportedEncodingException e) {
              logger.error("加载属性文件出错，不支持的编码格式；",e);
              throw new RuntimeException(e);
        } catch (IOException e) {
            logger.error("加载属性文件出错，IO异常；",e);
            throw new RuntimeException(e);
        }
        finally
        {
            if(is != null)
                try {
                    is.close();
                } catch (IOException e) {
                    logger.error("加载属性文件释放资源出错；",e);
                }
        }

    }

    public Map<String,String> loadPropsToMap(Properties props)
    {
        Map<String,String> map = new ConcurrentHashMap<String, String>();
        for(String key : props.stringPropertyNames())
            map.put(key,props.getProperty(key));

        return map;
    }

    public String getString(String key)
    {
        return props.getProperty(key);
    }

    public String getString(String key,String defaultValue)
    {
        return props.getProperty(key,defaultValue);
    }

    public static String getString(Properties props,String key,String defaultValue)
    {
        String value = defaultValue;
        if(props.containsKey(key))
            value = props.getProperty(key);
        return value;
    }

    public Integer getInt(String key)
    {
        return getInt(key,null);
    }

    public Integer getInt(String key,Integer defaultValue)
    {
        String value = props.getProperty(key);
        if(value != null)
            return Integer.parseInt(value.trim());
        return defaultValue;
    }

    public Long getLong(String key)
    {
        return getLong(key,null);
    }

    public Long getLong(String key,Long defaultValue)
    {
        String value = props.getProperty(key);
        if(value != null)
            return Long.parseLong(value.trim());
        return defaultValue;
    }

    public Boolean getBoolean(String key)
    {
        return getBoolean(key,null);
    }

    public Boolean getBoolean(String key,Boolean defaultValue)
    {
        String value = props.getProperty(key);
        if(value != null)
        {
            value = value.toLowerCase().trim();
            if("true".equals(value))
                return true;
            else if("false".equals(value))
                return false;
            throw new RuntimeException("This value can not parse to Boolean : " + value);
        }
        return defaultValue;
    }

    public boolean containsKey(String key)
    {
        return props.containsKey(key);
    }

    public Properties getProperties()
    {
        return props;
    }

    public static Properties loadProps(String propsPath)
    {
        Properties props = new Properties();
        InputStream is = null;

        try
        {
            if(StringUtils.isEmpty(propsPath))
                throw new IllegalArgumentException();

            if(propsPath.lastIndexOf(".properties") == -1)
                propsPath += ".properties";

            is = ClassUtil.getClassLoader().getResourceAsStream(propsPath);

            if(is != null)
                props.load(is);

        } catch (IOException e) {
            e.printStackTrace();
        }
        finally
        {
            if(is != null)
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }

        return props;

    }


}
