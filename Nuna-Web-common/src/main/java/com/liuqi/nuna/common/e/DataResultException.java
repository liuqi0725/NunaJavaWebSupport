package com.liuqi.nuna.common.e;

/**
 * 类说明 <br>
 *     DataResult 的相关自定义异常
 * <p>
 * 构造说明 :
 * <pre>
 *  new DataResultException();
 * </pre>
 *
 * @author : alexliu
 * @version v1.0 , Create at 10:32 AM 2019/4/26
 */
public class DataResultException extends Exception {

    private static final long serialVersionUID = -2930241434631521423L;

    public DataResultException() {
        super();
    }

    /**
     * @param   message 错误的详细信息 {@link #getMessage()} method.
     */
    public DataResultException(String message) {
        super(message);
    }

}
