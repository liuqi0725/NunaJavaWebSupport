package com.liuqi.nuna.core;

import com.liuqi.nuna.core.helper.NunaCacheHelper;
import com.liuqi.nuna.core.helper.NunaUserHelper;
import org.springframework.context.ApplicationContext;

/**
 * 类说明 <br>
 * <p>
 * 构造说明 :
 * <pre>
 * 由 spring 启动
 * </pre>
 *
 * @author : alexliu
 * @version v1.0 , Create at 4:25 PM 2019/4/28
 */
public class Nuna {

    /**
     * cache, 缓存实体 {@link NunaCacheHelper}
     */
    public static NunaCacheHelper cache = null;

    /**
     * spring 上下文  {@link ApplicationContext}
     */
    public static ApplicationContext SpringContext = null;

    /**
     * 登陆安全用户 {@link NunaUserHelper}
     */
    public static NunaUserHelper UserUtils = null;

}
