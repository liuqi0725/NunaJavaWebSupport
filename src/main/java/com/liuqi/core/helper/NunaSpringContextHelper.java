package com.liuqi.core.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;


/**
 * 类说明 <br>
 * Summer 框架 Spring 上下问帮助类
 * <p>
 * 构造说明 :
 * <pre>
 *     注入 spring 实体
 * </pre>
 *
 * @author : alexliu
 * @version v1.0 , Create at 12:53 PM 2018/11/9
 */
public class NunaSpringContextHelper implements ApplicationContextAware {


    private Logger logger = LoggerFactory.getLogger(NunaSpringContextHelper.class);

    private static ApplicationContext appContext = null;

    /**
     * 用静态方法获取Spring Context
     * @return ApplicationContext
     */
    protected static ApplicationContext getSpringContext() {
        return appContext;
    }

    /**
     * 此方法由 spring 注入时调用
     * @param applicationContext spring 上下文
     * @throws BeansException 异常
     */
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        logger.info("Nuna Spring Context success set on.");
        NunaSpringContextHelper.appContext = applicationContext;
    }
}
