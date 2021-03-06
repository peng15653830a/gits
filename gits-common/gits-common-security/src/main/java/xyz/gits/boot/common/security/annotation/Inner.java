package xyz.gits.boot.common.security.annotation;

import java.lang.annotation.*;

/**
 * 服务间调用注解
 *
 * @author songyinyin
 * @date 2020/8/5 下午 11:50
 */
@Inherited
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Inner {

    /**
     * 是否AOP统一处理
     * @return false, true
     */
    boolean value() default true;
}
