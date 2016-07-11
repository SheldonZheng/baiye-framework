package com.baiye.helper;

import com.baiye.annotation.Inject;
import com.baiye.utils.ReflectionUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;

/**
 * Created by Baiye on 2016/7/4.
 */
public final class IocHelper {

    static
    {
        Map<Class<?>,Object> beanMap = BeanHelper.getBeanMap();

                                for(Map.Entry<Class<?>,Object> beanEntry : beanMap.entrySet())
                                {
                                    Class<?> beanClass = beanEntry.getKey();
                                    Object beanInstance = beanEntry.getValue();
                                    Field[] beanFields = beanClass.getDeclaredFields();
                                    if(ArrayUtils.isNotEmpty(beanFields))
                                    {
                                        for(Field beanField : beanFields)
                                        {
                                            if(beanField.isAnnotationPresent(Inject.class))
                                            {
                                                Class<?> beanFieldClass = beanField.getType();
                                                Object beanFieldInstance = beanMap.get(beanFieldClass);
                                                if(beanFieldInstance != null)
                                ReflectionUtil.setField(beanInstance,beanField,beanFieldInstance);
                        }
                                        }
                }
            }

    }

    public static void init()
    {
        return;
    }

}
