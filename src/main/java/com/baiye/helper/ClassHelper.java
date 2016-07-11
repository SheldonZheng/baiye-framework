package com.baiye.helper;

import com.baiye.annotation.Controller;
import com.baiye.annotation.Service;
import com.baiye.utils.ClassUtil;
import com.baiye.utils.ConfigConstant;
import com.baiye.utils.PropsUtil;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Baiye on 2016/7/1.
 */
public final class ClassHelper {
    private static final Set<Class<?>> CLASS_SET;

    static
    {

        String basePackage = ConfigHelper.getAppBasePackage();
        CLASS_SET = ClassUtil.getClassSet(basePackage);
    }

    public static void init()
    {
        return;
    }

    public static Set<Class<?>> getClassSet()
    {
        return CLASS_SET;
    }

    public static Set<Class<?>> getServiceClassSet()
    {
        Set<Class<?>> classSet = new HashSet<Class<?>>();

        for(Class<?> cls : CLASS_SET)
        {
            if(cls.isAnnotationPresent(Service.class))
                classSet.add(cls);
        }

        return classSet;
    }

    public static Set<Class<?>> getControllerClassSet()
    {
        Set<Class<?>> classSet = new HashSet<Class<?>>();
        for(Class<?> cls : CLASS_SET)
        {
            if(cls.isAnnotationPresent(Controller.class))
                classSet.add(cls);
        }
        return classSet;
    }

    public static Set<Class<?>> getBeanClassSet()
    {
        Set<Class<?>> beanClassSet = new HashSet<Class<?>>();
        beanClassSet.addAll(getServiceClassSet());
        beanClassSet.addAll(getControllerClassSet());
        return beanClassSet;
    }


    public static Set<Class<?>> getClassSetBySuper(Class<?> superClass)
    {
        Set<Class<?>> classSet = new HashSet<Class<?>>();
        for(Class<?> cls : CLASS_SET)
        {
            if(superClass.isAssignableFrom(cls) && !superClass.equals(cls))
                classSet.add(cls);
        }

        return classSet;
    }

   /* private static String info = null;

    public static String getInfo()
    {
        return info;
    }*/

    public static Set<Class<?>> getClassSetByAnnotation(Class<? extends Annotation> annotationClass)
    {
        Set<Class<?>> classSet = new HashSet<Class<?>>();
        for (Class<?> cls : CLASS_SET) {

            if(cls.isAnnotationPresent(annotationClass))
            {
                classSet.add(cls);
            }

        }

        return classSet;
        
    }


    public static Set<Class<?>> getClassSetByannotation(String packageName,Class<? extends Annotation> annotationClass)
    {
        Set<Class<?>> allClassSet = ClassUtil.getClassSet(packageName);
        Set<Class<?>> classSet = new HashSet<Class<?>>();

        for (Class<?> cls : allClassSet) {

            if(cls.isAnnotationPresent(annotationClass))
            {
                classSet.add(cls);
            }

        }

        return classSet;
    }




}
