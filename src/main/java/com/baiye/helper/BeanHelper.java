package com.baiye.helper;

import com.baiye.utils.ReflectionUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by Baiye on 2016/7/4.
 */
public final class BeanHelper {

    private static final Map<Class<?>,Object> BEAN_MAP = new HashMap<Class<?>, Object>();

    static
        {
            Set<Class<?>> beanClassSet = ClassHelper.getBeanClassSet();
            for(Class<?> beanClass : beanClassSet)
            {
                Object obj = ReflectionUtil.newInstance(beanClass);
                BEAN_MAP.put(beanClass,obj);
            }
    }

    public static void init()
    {
        return;
    }

    public static Map<Class<?>,Object> getBeanMap()
    {
        return BEAN_MAP;
    }

    public static <T> T getBean(Class<T> cls)
    {
        if(!BEAN_MAP.containsKey(cls))
            throw new RuntimeException("can not get bean by class" + cls);

        return (T) BEAN_MAP.get(cls);
    }

    public static void setBean(Class<?> cls,Object obj)
    {
        BEAN_MAP.put(cls,obj);
    }



}
