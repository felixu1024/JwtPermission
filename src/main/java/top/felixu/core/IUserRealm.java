package top.felixu.core;

import java.util.List;

/**
 * 获取用户角色和权限的接口
 * 需要用户自行实现
 *
 * @Author felixu
 * @Date 2018/6/14
 */
public interface IUserRealm {

    /**
     * get user roles
     *
     * @param userInfo
     * @return
     */
    List<String> getUserRoles(String userInfo);

    /**
     * get user permission
     *
     * @param userInfo
     * @return
     */
    List<String> getUserPermissions(String userInfo);
}
