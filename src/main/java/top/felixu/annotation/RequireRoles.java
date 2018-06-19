package top.felixu.annotation;

import java.lang.annotation.*;

/**
 * 所需角色
 *
 * @Author felixu
 * @Date 2018/6/17
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequireRoles {
    /**
     * 指定需要的角色
     *
     * @return
     */
    String value();
}
