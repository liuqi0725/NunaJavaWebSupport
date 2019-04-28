package com.liuqi.core.security.service;

import com.liuqi.core.security.NunaUser;

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
