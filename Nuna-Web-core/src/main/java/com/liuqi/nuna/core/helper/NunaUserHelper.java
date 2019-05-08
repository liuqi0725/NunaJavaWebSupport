package com.liuqi.nuna.core.helper;


import com.liuqi.nuna.core.e.UserLoginException;
import com.liuqi.nuna.core.security.NunaSecurityConstants;
import com.liuqi.nuna.core.security.NunaUser;
import com.liuqi.nuna.core.security.token.UsernamePasswordVcToken;
import com.liuqi.nuna.core.utils.EncodesUtils;
import com.liuqi.nuna.core.utils.crypt.SimpleCryptUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;

import java.io.Serializable;

/**
 **
 * 类说明 <br>
 * Summer 用户帮助类
 * <p>
 * 构造说明 :
 * <pre>
 *     通过 spring 注入
 *     {@code
 *     <bean id="initNunaUserHelper" class="com.liuqi.core.helper.NunaUserHelper" init-method="getInstance" />
 *     }
 * </pre>
 *
 * @author : alexliu
 * @version v1.0 , Create at 12:53 PM 2018/11/9
 */
public class NunaUserHelper implements Serializable{

    private static final long serialVersionUID = -2941650420211162028L;

    private Logger logger = LogManager.getLogger(NunaUserHelper.class);

    private NunaUserHelper(){

    }

    private static class SummerUserHolder{
        private final static NunaUserHelper instance = new NunaUserHelper();
    }

    /**
     * 获取类的实体
     * @return 实体 {@link NunaUserHelper}
     */
    public static NunaUserHelper getInstance(){
        return NunaUserHelper.SummerUserHolder.instance;
    }

    /**
     * 用户是否有该权限
     * @param perm 权限名称
     * @return 是否拥有
     */
    public boolean hasPermission(String perm){

        Subject subject = SecurityUtils.getSubject();
        return subject.isPermitted(perm);
    }

    /**
     * 重新获取用户的权限
     * @deprecated 尚未启用，默认返回 false
     * @return 是否成功
     */
    public boolean reloadUserPermissions(){

        return false;
    }

    /**
     * 生成密码盐
     * @return 返回用于计算的盐
     */
    public String generatePasswordSalt(){
        // 得到8位盐
        byte[] salts = SimpleCryptUtils.generateSalt(NunaSecurityConstants.SALT_SIZE);
        // 将8位byte数组装换为string 保存数据库
        return EncodesUtils.encodeHex(salts);
    }

    /**
     * 生成密码
     * @param password 密码明文
     * @param salt 加密盐
     * @return 加密后的密码
     */
    public String generatePassword(String password , String salt){
        // 将string数组转化为8位byte数组
        byte[] salts = EncodesUtils.decodeHex(salt);
        // 对密码加盐进行1024次SHA1加密
        byte[] hashPassword = SimpleCryptUtils.sha1(password.getBytes(), salts, NunaSecurityConstants.HASH_INTERATIONS);
        // 将加密后的密码数组转换成字符串
        return EncodesUtils.encodeHex(hashPassword);
    }

    /**
     * 获取当前用户
     * @return 用户 {@link NunaUser}
     */
    public NunaUser getCurrentUser(){

        return (NunaUser) SecurityUtils.getSubject().getPrincipal();
        //return (SummerUser) SecurityUtils.getSubject().getSession().getAttribute(SummerSecurityConstants.CURRENT_USER_SESSION_NAME);
    }

    /**
     * 用户是否登陆
     * @return 登陆状态
     */
    public boolean isLogin(){
        Subject subject = SecurityUtils.getSubject();
        return subject.isAuthenticated();
    }

    /**
     * 用户登录
     * @param account 账号
     * @param password 密码
     * @param vcid 虚拟中心
     * @throws UserLoginException 登陆异常
     */
    public void login(String account,String password ,String vcid) throws UserLoginException {
        UsernamePasswordVcToken token = new UsernamePasswordVcToken(account, password , vcid);
        this.login(token);
    }

    /**
     * 用户登录
     * @param account 账号
     * @param password 密码
     * @throws UserLoginException 登陆异常
     */
    public void login(String account,String password) throws UserLoginException {
        UsernamePasswordVcToken token = new UsernamePasswordVcToken(account,password);
        this.login(token);
    }

    private void login(UsernamePasswordVcToken token) throws UserLoginException {
        Subject subject = SecurityUtils.getSubject();
        subject.login(token);
        try {
            subject.login(token);
        } catch (AuthenticationException e) {
            throw new UserLoginException("登陆失败，用户名或密码错误！");
        }
    }


}
