package top.felixu.core;

import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import top.felixu.annotation.RequireRoles;
import top.felixu.properties.JwtProperties;
import top.felixu.utils.JwtUtils;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Optional;

/**
 * @Author felixu
 * @Date 2018/6/14
 */

public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    JwtProperties properties;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader(properties.getAuthorization());
        if (StringUtils.isEmpty(token)) {
            throw new JwtException("Token is empty!");
        }
        String userInfo = JwtUtils.parseToken(token, properties.getKey()).getSubject();
        if (!Subject.INSTANCE.isValidToken(userInfo, token)) {
            // FIXME: 2018/6/19 need valid token?
        }
        if (handler instanceof HandlerMethod) {
            Optional.of(((HandlerMethod) handler).getMethod())
                    .ifPresent(method -> check(method, userInfo));
        }
        return true;
    }

    private void check(Method method, String userInfo) {
        if (!checkPermissions(method, userInfo) || !checkRoles(method, userInfo)) {
            throw new JwtException("Sorry, you don't have permission to access this resource!");
        }
    }

    private boolean checkRoles(Method method, String userInfo) {
        RequireRoles requireRoles = method.getClass().getAnnotation(RequireRoles.class);
        if (ObjectUtils.isEmpty(requireRoles)) {
            requireRoles = method.getAnnotation(RequireRoles.class);
            if (ObjectUtils.isEmpty(requireRoles)) {
                return true;
            }
        }
        String value = requireRoles.value();
        // FIXME: 2018/6/17 判断用户是否有注解中所需要的角色
        return true;
    }

    private boolean checkPermissions(Method method, String userInfo) {
        RequireRoles requireRoles = method.getClass().getAnnotation(RequireRoles.class);
        if (ObjectUtils.isEmpty(requireRoles)) {
            requireRoles = method.getAnnotation(RequireRoles.class);
            if (ObjectUtils.isEmpty(requireRoles)) {
                return true;
            }
        }
        String value = requireRoles.value();
        // FIXME: 2018/6/17 判断用户是否有注解中所需要的角色
        return true;
    }
}
