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
}
