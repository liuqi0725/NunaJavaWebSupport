package com.liuqi.nuna.core;


import com.liuqi.nuna.core.helper.NunaCacheHelper;
import com.liuqi.nuna.core.helper.NunaUserHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Nuna.core 是 Nuna 的核心包<br>
 *
 * <p>NunaBootStrap 是 Nuna 项目的初始化类.需要配置在 Springmvc 的配置文件中.</p>
 *
 * For example:
 * <pre>
 * {@code
 *     <!-- 初始化 Summer -->
 *    <bean class="com.summer.core.SummerBootstrap" init-method="init">
 *          <!-- 是否启动任务模块 yes|no   on|off    1|0 -->
 *          <property name="task_module_enable" value="no"></property>
 *          <!-- 任务模块线程数，仅在 task_module_enable 启动情况下生效-->
 *          <property name="task_module_threads" value="5"></property>
 *          <!-- 同一时间内，队列的最大容量。 默认500000 不能超过 Integer 最大值-->
 *          <property name="queue_max_size" value="500000"></property>
 *    </bean>
 * }
 * </pre>
 */
public class NunaBootStrap implements ApplicationContextAware {

    private static Logger logger = LogManager.getLogger(NunaBootStrap.class);

    private ApplicationContext appContext = null;

    private EhCacheCacheManager cacheCacheManager = null;


    /**
     * summer 初始化
     */
    public void init(){

        logger.info("----------------------------------");
        logger.info("Nuna 初始化.");
        logger.info("----------------------------------");

        logger.info("初始化 Custom Cache .");
        cacheInit();

        logger.info("初始化 Nuna.UserUtils .");
        summerUserInit();

        logger.info("----------------------------------");
        logger.info("Summer 初始化.结束");
        logger.info("----------------------------------");

    }


    /**
     * 用静态方法获取Spring Context
     * @return ApplicationContext
     */
    public ApplicationContext getSpringContext() {
        return appContext;
    }

    /**
     * 此方法由 spring 注入时调用
     * @param applicationContext spring 上下文
     * @throws BeansException 异常
     */
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        logger.info("Nuna Spring Context success set on. {}",applicationContext);
        Nuna.SpringContext = applicationContext;
    }

    public EhCacheCacheManager getCacheCacheManager() {
        return cacheCacheManager;
    }

    public void setCacheCacheManager(EhCacheCacheManager cacheCacheManager) {
        this.cacheCacheManager = cacheCacheManager;
    }

    private void cacheInit(){
        logger.info("Nuna CacheManager set on. {}",cacheCacheManager);
        Nuna.cache = NunaCacheHelper.getInstance(cacheCacheManager);
    }

    private void summerUserInit(){
        Nuna.UserUtils = NunaUserHelper.getInstance();
        logger.info("Nuna UserManager set on. {}", Nuna.UserUtils);
    }

}
