package com.liuqi.nuna.core.security.service;

import com.liuqi.nuna.core.security.NunaUser;

import java.util.Set;


/**
 * 类说明 <br>
 *     使用 summer 框架，子项目的用户 service 必须继承该类
 *     并实现该类的函数，以便 shiro 调用
 * <p>
 * 构造说明 :
 * <pre>
 *   public interface UserService extends SummerUserService{
 *      ...
 *   }
 * </pre>
 *
 * @author : alexliu
 * @version v1.0 , Create at 10:32 AM 2019/4/26
 */
public interface NunaUserService {

    /**
     * 查询用户
     * @param account 登录账号
     * @return 用户
     */
    NunaUser summerFindUserByName(String account);

    /**
     * 查询用户
     * @param account 登录账号
     * @param vcid 虚拟中心
     *             支持多虚拟中心统一登录到系统，虚拟中心不影响角色，权限，只是限定用户的操作区间
     *             设置虚拟中心的用户，必须在其他使用范围内规范 vcid
     * @return 用户
     */
    NunaUser summerFindUserByName(String account , String vcid);

    /**
     * 查找当前登录用户的权限集合
     * @return 权限名称集合
     */
    Set<String> summerFindUserPermissions();

    /**
     * 查找当前登录用户的角色集合
     * @param uid 角色 id
     * @return 角色名称集合
     */
    Set<String> summerFindUserRoles(Integer uid);
}
