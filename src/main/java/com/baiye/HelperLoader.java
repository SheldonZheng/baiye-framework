package com.baiye;

import com.baiye.helper.*;
import com.baiye.utils.ClassUtil;

/**
 * Created by Baiye on 2016/7/4.
 */
public final class HelperLoader {

    public static void init()
    {
        Class<?>[] classList = {
                ClassHelper.class,
                BeanHelper.class,
                AopHelper.class,
                IocHelper.class,
                ControllerHelper.class
        };
        for(Class<?> cls : classList)
        {
            ClassUtil.loadClass(cls.getName(),false);
        }


        ClassHelper.init();
        BeanHelper.init();
        AopHelper.init();
        IocHelper.init();
        ControllerHelper.init();


        // AopHelper.init();
       // System.out.println(BeanHelper.getBean(ControllerTest.class).hashCode());
     //   BeanHelper.getBean(ControllerTest.class).test2();

    //   System.out.println( BeanHelper.getBeanMap().get(ControllerTest.class).getClass());

       // ControllerTest.test();
    }

    public static void main(String[] args) {
        init();
    }

}
