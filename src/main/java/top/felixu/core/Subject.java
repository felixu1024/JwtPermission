package top.felixu.core;

import io.jsonwebtoken.Claims;
import org.springframework.util.ObjectUtils;

import java.util.*;

/**
 * @Author felixu
 * @Date 2018/6/14
 */
public enum  Subject {
    INSTANCE;

    private static final String KEY_PRE_PS = "permission-";
    private static final String KEY_PRE_RS = "role-";
    private static final String KEY_PRE_TOKEN = "token-";

    private static IUserRealm userRealm;
    private static IJwtCache cache;

    public void setUserRealm(IUserRealm realm) {
        userRealm = realm;
    }

    public IUserRealm getUserRealm() {
        return userRealm;
    }

    protected void setCache(IJwtCache jwtCache) {
        cache = jwtCache;
    }

    public IJwtCache getCache() {
        return cache;
    }

    /**
     * 获取用户的角色
     *
     * @param userInfo
     * @return
     */
    public List<String> getUserRoles(String userInfo){
        /**
         * 1. 查找缓存，看缓存中是否存在
         * 2. 如果没有就调用realm里面的方法并加入缓存
         * 3. 返回角色列表
         */
        List<String> cacheRoles = cache.getSet(KEY_PRE_RS + userInfo);
        if (ObjectUtils.isEmpty(cacheRoles) || cacheRoles.size() == 0) {
            cacheRoles = new ArrayList<String>();
            List<String> userRoles = userRealm.getUserRoles(userInfo);
            if (!ObjectUtils.isEmpty(userRoles) && userRoles.size() > 0) {
                cacheRoles.addAll(userRoles);
                cache.putSet(KEY_PRE_RS + userInfo, userRoles);
            }
        }

        return cacheRoles;
    }

    /**
     * 获取用户的权限
     *
     * @param userInfo
     * @return
     */
    public List<String> getUserPermissions(String userInfo){
        /**
         * 1. 查找缓存，看缓存中是否存在
         * 2. 如果没有就调用realm里面的方法并加入缓存
         * 3. 返回角色列表
         */
        List<String> cachePermissions = cache.getSet(KEY_PRE_PS + userInfo);
        if (ObjectUtils.isEmpty(cachePermissions) || cachePermissions.size() == 0) {
            cachePermissions = new ArrayList<String>();
            List<String> userPermissions = userRealm.getUserPermissions(userInfo);
            if (!ObjectUtils.isEmpty(userPermissions) && userPermissions.size()>0) {
                cachePermissions.addAll(userPermissions);
                cache.putSet(KEY_PRE_PS + userInfo, userPermissions);
            }
        }
        return cachePermissions;
    }

    /**
     * 更新user的权限缓存
     *
     * @param userId
     * @return
     */
    public boolean updateCachePermission(String userId) {
        return cache.delete(KEY_PRE_PS + userId);
    }

    public boolean updateCachePermission(){
        return cache.delete(cache.keys(KEY_PRE_PS+"*"));
    }

    /**
     * 更新user的角色缓存
     *
     * @param userId
     * @return
     */
    public boolean updateCacheRoles(String userId) {
        return cache.delete(KEY_PRE_RS + userId);
    }

    public boolean updateCacheRoles(){
        return cache.delete(cache.keys(KEY_PRE_RS+"*"));
    }

    /**
     * 检查token是否有效
     *
     * @param userId
     * @param token
     * @return
     */
    protected boolean isValidToken(String userId, String token) {
        // FIXME: 2018/6/19 缓存中取出的是一个，而不是list
        List<String> tokens = cache.getSet(KEY_PRE_TOKEN + userId);
        return tokens != null && tokens.contains(token);
    }

    /**
     * 缓存token
     *
     * @param userId
     * @param token
     */
    private boolean setCacheToken(String userId, String token) {
        cache.delete(KEY_PRE_TOKEN + userId);
        List<String> tokens = new ArrayList<>();
        tokens.add(token);
        return cache.putSet(KEY_PRE_TOKEN + userId, tokens);
    }

    /**
     * 主动让user的所有token失效
     *
     * @param userId
     * @return
     */
    public boolean expireToken(String userId) {
        return cache.delete(KEY_PRE_TOKEN + userId);
    }

    /**
     * 移除user的某一个token
     *
     * @param userId
     * @param token
     * @return
     */
    public boolean expireToken(String userId, String token) {
        return cache.removeSet(KEY_PRE_TOKEN + userId, token);
    }
}
