package com.liuqi.core.e;

/**
 * 类说明 <br>
 *     用户登录失败异常
 * <p>
 * 构造说明 :
 * <pre>
 *  new DataResultException();
 * </pre>
 *
 * @author : alexliu
 * @version v1.0 , Create at 10:32 AM 2019/4/26
 */
public class UserLoginException extends Exception {

    private static final long serialVersionUID = -9032322389937253168L;

    public UserLoginException() {
        super();
    }

    /**
     * @param   message 错误的详细信息 {@link #getMessage()} method.
     */
    public UserLoginException(String message) {
        super(message);
    }

}
