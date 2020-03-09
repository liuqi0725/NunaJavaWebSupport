package com.liuqi.nuna.common.log.interceptor;

import com.liuqi.nuna.common.log.NunaLogOperationType;

import java.lang.annotation.*;

/**
 * 类说明 <br>
 *     日志记录，自定义注解
 * <p>
 * 构造说明 :
 * <pre>
 *   在类、方法上使用
 *
 * </pre>
 *
 * @author : alexliu
 * @version v1.0 , Create at 10:32 AM 2019/4/26
 */
@Target({ElementType.PARAMETER,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface NunaLog {

    /**
     * 操作类型, 详见： {@link NunaLogOperationType}
     */
    NunaLogOperationType operationType();


    /**
     * 操作内容，文字
     */
    String operationDesc() default "";

}
