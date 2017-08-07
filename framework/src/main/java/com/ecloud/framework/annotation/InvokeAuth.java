package com.ecloud.framework.annotation;

import java.lang.annotation.*;

/**
 * 忽略认证
 * @author gaogao
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface InvokeAuth {

}
