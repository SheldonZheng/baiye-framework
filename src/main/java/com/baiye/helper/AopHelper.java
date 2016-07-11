package com.baiye.helper;

import com.baiye.annotation.Aspect;
import com.baiye.proxy.AspectProxy;
import com.baiye.proxy.Proxy;
import com.baiye.proxy.ProxyManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.util.*;

/**
 * Created by Baiye on 2016/7/7.
 */
public final class AopHelper {


    private static final Logger LOGGER = LoggerFactory.getLogger(AopHelper.class);

    //init
    static
    {
        try{

            Map<Class<?>,Set<Class<?>>> proxyMap = createProxyMap();

            Map<Class<?>,List<Proxy>> targetMap = createTargetMap(proxyMap);

            for (Map.Entry<Class<?>, List<Proxy>> targetEntry : targetMap.entrySet()) {
                Class<?> targetClass = targetEntry.getKey();
                List<Proxy> proxyList = targetEntry.getValue();
               // info = proxyList.toString();
                Object proxy = ProxyManager.createProxy(targetClass,proxyList);

              //  info = proxy.toString();
             //   System.out.println(proxy.hashCode());

                BeanHelper.setBean(targetClass,proxy);

              //  info = targetClass.toString() + ";;;;" + ControllerTest.class.toString();

          //  List<Class<?>> aspectClassList = ClassHelper.getClassSetByAnnotation(Aspect.class);

            }

        } catch (Exception e) {
            LOGGER.error("init aop failure",e);
        }
    }


    public static void init()
    {
        return;
    }

    private static Set<Class<?>> createTargetClassSet(Aspect aspect) throws Exception
    {

        Set<Class<?>> targetClassSet = new HashSet<Class<?>>();
        Class<? extends Annotation> annotation = aspect.value();
        if(annotation != null && !annotation.equals(Aspect.class))
        {
            //info = aspect.toString();
            targetClassSet.addAll(ClassHelper.getClassSetByAnnotation(annotation));
            //info = ClassHelper.getInfo();
        }


        return targetClassSet;

    }

    private static Map<Class<?>,Set<Class<?>>> createProxyMap() throws Exception
    {
        Map<Class<?>,Set<Class<?>>> proxyMap = new HashMap<Class<?>, Set<Class<?>>>();
        Set<Class<?>> proxyClassSet = ClassHelper.getClassSetBySuper(AspectProxy.class);

        for (Class<?> proxyClass : proxyClassSet) {

            if(proxyClass.isAnnotationPresent(Aspect.class))
            {
                Aspect aspect = proxyClass.getAnnotation(Aspect.class);
                Set<Class<?>> targetClassSet = createTargetClassSet(aspect);
                proxyMap.put(proxyClass,targetClassSet);
            }
        }
        return proxyMap;
    }

    public static Map<Class<?>,List<Proxy>> createTargetMap(Map<Class<?>,Set<Class<?>>> proxyMap) throws Exception
    {
        Map<Class<?>,List<Proxy>> targetMap = new HashMap<Class<?>, List<Proxy>>();
        for (Map.Entry<Class<?>, Set<Class<?>>> proxyEntry : proxyMap.entrySet())
        {
            Class<?> proxyClass = proxyEntry.getKey();
            Set<Class<?>> targetClassSet = proxyEntry.getValue();

            for (Class<?> targetClass : targetClassSet)
            {
                Proxy proxy = (Proxy) proxyClass.newInstance();
                if(targetMap.containsKey(targetClass))
                    targetMap.get(targetClass).add(proxy);
                else
                {
                    List<Proxy> proxyList = new ArrayList<Proxy>();
                    proxyList.add(proxy);
                    targetMap.put(targetClass,proxyList);
                }
            }
        }
        return targetMap;
    }


 }
