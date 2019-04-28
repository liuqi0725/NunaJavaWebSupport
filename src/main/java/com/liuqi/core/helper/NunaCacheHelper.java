package com.liuqi.core.helper;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.ehcache.EhCacheCacheManager;

import java.io.Serializable;

/**
 * 类说明 <br>
 * Summer 框架 ehcache 类,提供了获取，添加...缓存的操作
 * <p>
 * 构造说明 :
 * <pre>
 *     通过 spring 注入
 *     <bean id="setCache" class="com.liuqi.core.cache.NunaCacheHelper">
 *         <property name="cacheCacheManager" ref="cacheManager" />
 *     </bean>
 * </pre>
 *
 * @author : alexliu
 * @version v1.0 , Create at 12:53 PM 2018/11/9
 */
public class NunaCacheHelper implements Serializable{

    private static final long serialVersionUID = -8026518670329749932L;

    private Logger logger = LoggerFactory.getLogger(NunaCacheHelper.class);

    private static EhCacheCacheManager cacheCacheManager = null;

    private static EhCacheCacheManager getCacheCacheManager() {
        return cacheCacheManager;
    }

    public void setCacheCacheManager(EhCacheCacheManager cacheCacheManager) {
        logger.info("Nuna EhCacheCacheManager success set on.");
        NunaCacheHelper.cacheCacheManager = cacheCacheManager;
    }

    /**
     * 获取缓存
     * @param cacheName 缓存名称
     * @return ehcache 实体
     */
    private Cache getEhCache(String cacheName){
        //获取ehcache的CacheManager类
        CacheManager cacheManager = cacheCacheManager.getCacheManager();

        return cacheManager.getCache(cacheName);
    }

    /**
     * 添加数据到 cache 中
     * @param cacheName 缓存名称
     * @param key 缓存数据的 key
     * @param data 缓存数据的 data
     */
    public void add(String cacheName , String key ,Object data){
        Cache cache = getEhCache(cacheName);
        cache.put(new Element(key,data));
    }

    /**
     * 从 cache 中获取数据
     * @param cacheName 缓存名称
     * @param key 缓存数据的 key
     * @return 返回结果
     */
    public Object get(String cacheName , String key){
        Cache cache = getEhCache(cacheName);
        return cache.get(key).getObjectValue();
    }

    /**
     * 从 cache 中移除数据
     * @param cacheName 缓存名称
     * @param key 缓存数据的 key
     * @return 返回结果
     */
    public Object remove(String cacheName , String key){
        Cache cache = getEhCache(cacheName);
        return cache.remove(key);
    }

    /**
     * 在缓存中是否存在
     * @param cacheName 缓存名称
     * @param key 缓存数据的 key
     * @return 是否存在
     */
    public boolean isExist(String cacheName , String key) {
    	Cache cache = getEhCache(cacheName);
    	Element element = cache.getQuiet(key);

    	return (element == null);
    }
}
