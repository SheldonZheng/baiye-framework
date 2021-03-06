package com.baiye.annotation;

import java.lang.annotation.*;

/**
 * Created by Baiye on 2016/7/6.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Aspect {


    String packageName() default "";

    String className() default "";

    Class<? extends Annotation> value() default Aspect.class;
}
