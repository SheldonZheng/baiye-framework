package com.baiye.helper;

import com.baiye.bean.Handler;
import com.baiye.bean.Request;
import com.baiye.annotation.Action;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by Baiye on 2016/7/4.
 */
public final class ControllerHelper {

    private static final Map<Request,Handler> ACTION_MAP = new HashMap<Request,Handler>();

    static
    {
        Set<Class<?>> controllerClassSet = ClassHelper.getControllerClassSet();

        if(CollectionUtils.isNotEmpty(controllerClassSet))
        {
            for(Class<?> controllerClass : controllerClassSet)
            {
                Method[] methods = controllerClass.getDeclaredMethods();
                if(ArrayUtils.isNotEmpty(methods))
                {
                    for(Method method : methods)
                    {
                        if(method.isAnnotationPresent(Action.class))
                        {
                            Action action = method.getAnnotation(Action.class);
                            String mapping = action.value();
                            if(mapping.matches("\\w+:/\\w*"))
                            {
                                String[] array = mapping.split(":");
                                if(ArrayUtils.isNotEmpty(array) && array.length == 2)
                                {
                                    String requestMethod = array[0];
                                    String requestPath = array[1];
                                    Request request = new Request(requestMethod,requestPath);
                                    Handler handler = new Handler(controllerClass,method);

                                    ACTION_MAP.put(request,handler);
                                }
                            }
                        }
                    }
                }
            }
        }

    }

    public static void init()
    {
        return;
    }

    public static Handler getHanlder(String requestMethod,String requestPath)
    {
        Request request = new Request(requestMethod,requestPath);
        return ACTION_MAP.get(request);
    }

}
