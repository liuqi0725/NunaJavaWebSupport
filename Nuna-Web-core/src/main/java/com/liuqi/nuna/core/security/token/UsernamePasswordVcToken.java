package com.liuqi.nuna.core.security.token;

import org.apache.shiro.authc.UsernamePasswordToken;

import java.io.Serializable;

/**
 * 类说明 <br>
 *     允许用户登录不同的虚拟中心
 * <p>
 * 构造说明 :
 * <pre>
 *      传统与带虚拟中心的 2 个构造方式
 * </pre>
 *
 * @author : alexliu
 * @version v1.0 , Create at 10:05 AM 2019/5/8
 */
public class UsernamePasswordVcToken extends UsernamePasswordToken implements Serializable{


    private static final long serialVersionUID = -2918242848855052633L;

    /**
     * 虚拟中心 id
     */
    private String vcid;

    public UsernamePasswordVcToken(String username , String password){
        super(username,password);
    }

    public UsernamePasswordVcToken(String username , String password , String vcid){
        super(username,password);
        this.vcid = vcid;
    }

    public void setVcid(String vcid) {
        this.vcid = vcid;
    }

    public String getVcid() {
        return vcid;
    }

}
