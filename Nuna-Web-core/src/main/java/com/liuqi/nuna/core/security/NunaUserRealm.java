package com.liuqi.nuna.core.security;

import com.liuqi.nuna.core.security.service.NunaUserService;
import com.liuqi.nuna.core.security.token.UsernamePasswordVcToken;
import com.liuqi.nuna.core.utils.EncodesUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.Set;

/**
 * 类说明 <br>
 *     用户 shiro 相关
 * <p>
 * 构造说明 :
 * <pre>
 *   需要在 spring 中引入该类
 *   {@code
 *    <bean id="shiroDbRealm" class="com.liuqi.summer.security.SummerUserRealm" />
 *   }
 * </pre>
 *
 * @author : alexliu
 * @version v1.0 , Create at 10:32 AM 2019/4/26
 */
public class NunaUserRealm extends AuthorizingRealm {

    private Logger logger = LogManager.getLogger(NunaUserRealm.class);

    @Autowired
    private NunaUserService userService;

    /**
     * 认证回调函数,登录时调用.
     * @param authcToken 认证 token 在 login 时生成
     * @return 用户认证信息
     * @throws AuthenticationException 异常
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {

        //获取页面参数
        // username password rememberMe
        /*
         * 0.0.2 更新为可通过虚拟中心 vcid 登录
         */
        UsernamePasswordVcToken token = (UsernamePasswordVcToken) authcToken;
        //查询用户
        NunaUser user = null;
        if(StringUtils.isEmpty(token.getVcid())){
            user = userService.nunaFindUserByName(token.getUsername());
        }else{
            user = userService.nunaFindUserByName(token.getUsername() , token.getVcid());
        }

        if (user == null) {
            throw new UnknownAccountException();// 没有找到账号，会返回 controller
        }

        // 判断帐号是否锁定
//        if (Boolean.TRUE.equals(user.getLocked())) {
//            // 抛出 帐号锁定异常
//            throw new LockedAccountException();
//        }

        //处理用户的盐
        byte[] salt = EncodesUtils.decodeHex(user.getSalt());

        /*
            验证用户
            1. Principals(身份) , 用于标识当前用户的的内容，可以是 username ，可以是 user 对象
            2. Credentials(凭证), 通常是用户在数据库的加密后的 password ，通过 @see #initCredentialsMatcher() 规则进行验证密码
            3. 当前实体类名称
         */
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, user.getPassword(), ByteSource.Util.bytes(salt), getName());

//        //当前用户保存在 session 中
//        SecurityUtils.getSubject().getSession().setAttribute(SummerSecurityConstants.CURRENT_USER_SESSION_NAME, user);
        return info;
    }

    /**
     * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用.
     * @param principals 信息
     * @return 认证信息
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
//        ShiroUser shiroUser = (ShiroUser) principals.getPrimaryPrincipal();
//        User user = userService.findUserByName(shiroUser.loginName);
        //通过当前用户查找权限

//        SummerUser user = (SummerUser) principals.getPrimaryPrincipal();

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

//        Set<String> roleNames = userService.findUserRoles(user.getId());

        //暂不引用角色
//        if(roleNames == null){
//            throw new RuntimeException("用户角色查询为空!");
//        }else{
//            info.addRoles(roleNames);
//        }

        Set<String> perms = userService.nunaFindUserPermissions();
        if(perms == null){
            throw new AuthenticationException("用户权限查询为空!");
        }else{
            info.setStringPermissions(perms);
        }

        //info.setRoles(this.getUserService().findRolesByUsername(username));
//		info.setStringPermissions(this.getUserService().findPermissionsByUsername(username));

        return info;
    }

    /**
     * 清空用户权限缓存
     * 用户权限修改时，需要清空
     *
     * @param account 账号
     */
    public void removeUserAuthorizationInfoCache(String account) {
        SimplePrincipalCollection pc = new SimplePrincipalCollection();
        pc.add(account, super.getName());
        super.clearCachedAuthorizationInfo(pc);
    }

    /**
     * 设定Password校验的Hash算法与迭代次数.
     */
    @PostConstruct
    public void initCredentialsMatcher() {
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher(NunaSecurityConstants.HASH_ALGORITHM);
        matcher.setHashIterations(NunaSecurityConstants.HASH_INTERATIONS);

        setCredentialsMatcher(matcher);
    }
}
