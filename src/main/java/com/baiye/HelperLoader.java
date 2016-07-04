package com.baiye;

import com.baiye.helper.BeanHelper;
import com.baiye.helper.ClassHelper;
import com.baiye.helper.ControllerHelper;
import com.baiye.helper.IocHelper;
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
                IocHelper.class,
                ControllerHelper.class
        };

        for(Class<?> cls : classList)
        {
            ClassUtil.loadClass(cls.getName(),false);
        }

    }
}
