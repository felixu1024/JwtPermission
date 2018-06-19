package top.felixu.annotation;

import java.lang.annotation.*;

/**
 * 所需权限点
 *
 * @Author felixu
 * @Date 2018/6/17
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequirePermissions {
    /**
     * 指定权限点
     *
     * @return
     */
    String[] value();
}
