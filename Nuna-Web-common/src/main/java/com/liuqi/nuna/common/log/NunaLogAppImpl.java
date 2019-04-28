package com.liuqi.nuna.common.log;

import org.aspectj.lang.JoinPoint;

/**
 * 类说明 <br>
 *     提供给第三方实现日志接口
 * <p>
 * 构造说明 :
 * <pre>
 *   通过实现该类，子系统可以记录日志
 *
 * </pre>
 *
 * @author : alexliu
 * @version v1.0 , Create at 10:32 AM 2019/4/26
 */
public interface NunaLogAppImpl {

    /**
     * @param jp 切点
     * @param result 数据
     */
    public void afterMethodSave(JoinPoint jp, Object result) ;

    /**
     *
     * @param jp 切点
     * @param result 数据
     */
    public void query(JoinPoint jp, Object result);
}
