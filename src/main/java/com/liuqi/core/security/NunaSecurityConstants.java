package com.liuqi.core.security;

/**
 * 类说明 <br>
 * Summer 框架，用户安全使用的基本常量
 * <p>
 * 构造说明 :
 * <pre>
 *   {@code
 *   SummerSecurityConstants.CURRENT_USER_SESSION_NAME
 *   }
 * </pre>
 *
 * @author : alexliu
 * @version v1.0 , Create at 12:53 PM 2018/11/9
 */
public final class NunaSecurityConstants {


    public static final String CURRENT_USER_SESSION_NAME = "loginUser";

    public static final String USER_LOGIN_ATTRS = "user_login_attrs";

    public static final String USERNAME = "username";

    public static final String PASSWORD = "password";

    /**
     * 算法 {@value}
     */
    public static final String HASH_ALGORITHM = "SHA-1";

    public static final int HASH_INTERATIONS = 1024;

    public static final int SALT_SIZE = 8;

}
