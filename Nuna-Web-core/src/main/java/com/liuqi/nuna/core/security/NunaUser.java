package com.liuqi.nuna.core.security;

import java.io.Serializable;
import java.util.Date;

/**
 * 类说明 <br>
 * summer 框架 用户基础信息类，为保证用户 shiro 安全认证，使用该框架的应用，如果在 <i>SummerUser</i>
 * 无法满足的情况下扩展字段，需要继承 <i>SummerUser</i>.
 * <p>
 * 构造说明 :
 * <pre>
 *   无
 * </pre>
 *
 * @author : alexliu
 * @version v1.0 , Create at 12:53 PM 2018/11/9
 */
public class NunaUser implements Serializable{

    private Integer id;

    private String vcid;

    private String account;

    private String nickname;

    private String password;

    private String salt;

    private String email;

    private String mobile;

    private String headUrl;

    private Integer locked;

    private Integer admin;

    private Date createAt;

    private Date updateAt;

    /**
     * 用户的私有 cache key ，用来保存到 cache 中的标识。该 cachekey 用以保存用户特有缓存数据
     */
    private String cacheKey;

    public String getCacheKey() {
        return cacheKey;
    }

    public void setCacheKey(String cacheKey) {
        this.cacheKey = cacheKey;
    }

    public Integer getAdmin() {
        return admin;
    }

    public void setAdmin(Integer admin) {
        this.admin = admin;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getVcid() {
        return vcid;
    }

    public void setVcid(String vcid) {
        this.vcid = vcid;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public Integer getLocked() {
        return locked;
    }

    public void setLocked(Integer locked) {
        this.locked = locked;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }
}
