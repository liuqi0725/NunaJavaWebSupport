package com.liuqi.nuna.common.log;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;


/**
 * 类说明 <br>
 *     定义一个切点,在使用注解方式使用 log 时调用函数
 * <p>
 * 构造说明 :
 * <pre>
 *   通过实现该类，子系统可以记录日志
 * </pre>
 *
 * @author : alexliu
 * @version v1.0 , Create at 10:32 AM 2019/4/26
 */
@Aspect
@Component
public class NunaLogAspect {

    /**
     * 第三方实现的 log 记录，通过 spring 注入
     * <pre>
     *  {@code
     *
     *
     *     <bean id="systemlog" class="com.fangxu.app.core.SystemLog" />
     *     <bean id="nunalog" class="com.liuqi.summer.core.log.SummerLogAspect">
     *         <property name="appLogs" ref="systemlog"/>
     *     </bean>
     *
     *     }
     * </pre>
     */
    private NunaLogAppImpl appLogs = null;

    /**
     * 定义切点为所有使用 SummerLog 注解的时候
     */
    @Pointcut("@annotation(com.liuqi.nuna.common.log.interceptor.NunaLog)")
    private void logPoint(){

    }

    /**
     * 事件完毕后的通知
     * @param jp 所有使用切点进入此切面
     * @param object 执行后的返回
     */
    @AfterReturning(pointcut = "logPoint()" , returning = "object")
    public void saveLog(JoinPoint jp, Object object){

        if(this.appLogs != null){
            this.appLogs.afterMethodSave(jp,object);
        }

    }

    public void setAppLogs(NunaLogAppImpl appLogs) {
        this.appLogs = appLogs;
    }

}
