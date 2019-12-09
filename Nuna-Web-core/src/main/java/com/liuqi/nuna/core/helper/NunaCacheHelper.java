package com.liuqi.nuna.core.helper;

import com.liuqi.nuna.core.Nuna;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import net.sf.ehcache.store.MemoryStoreEvictionPolicy;
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
 *     {@code
 *     <bean id="setCache" class="com.liuqi.core.cache.NunaCacheHelper">
 *         <property name="cacheCacheManager" ref="cacheManager" />
 *     </bean>
 *     }
 * </pre>
 *
 * @author : alexliu
 * @version v1.0 , Create at 12:53 PM 2018/11/9
 */
public class NunaCacheHelper implements Serializable{

    private static final long serialVersionUID = -8026518670329749932L;

    private Logger logger = LoggerFactory.getLogger(NunaCacheHelper.class);

    private EhCacheCacheManager cacheCacheManager = null;

    private NunaCacheHelper(){
    }

    protected void setCacheCacheManager(EhCacheCacheManager cacheCacheManager){
        this.cacheCacheManager = cacheCacheManager;
    }

    private static class CacheManageHolder{
        private final static NunaCacheHelper instance = new NunaCacheHelper();
    }

    /**
     * 获取类的实体
     * @param cacheCacheManager 实体 {@link EhCacheCacheManager} 由 spring 注入
     * @return NunaCacheHelper 实体 {@link NunaCacheHelper}
     */
    public static NunaCacheHelper getInstance(EhCacheCacheManager cacheCacheManager){
        NunaCacheHelper.CacheManageHolder.instance.setCacheCacheManager(cacheCacheManager);
        return NunaCacheHelper.CacheManageHolder.instance;
    }


    /**
     * 获取缓存
     * @param cacheName 缓存名称
     * @return ehcache 实体
     * @deprecated 使用 getCacheOrAdd(String cacheName);
     */
    private Cache getEhCache(String cacheName){
        //获取ehcache的CacheManager类
        CacheManager cacheManager = cacheCacheManager.getCacheManager();

        return cacheManager.getCache(cacheName);
    }

    /**
     * 获取缓存，如果不存在即创建
     * @param cacheName 缓存名称
     * @return ehcache 实体
     */
    private Cache getCacheOrAdd(String cacheName) {
        // 创建myCache缓存
        CacheManager cacheManager = cacheCacheManager.getCacheManager();

        Cache cache = null;
        if (!CacheManager.getInstance().cacheExists(cacheName)) {
            /*
             maxElementsInMemory 内存中保持的对象数量
             maxElementsOnDisk 	 DiskStore中保持的对象数量，默认值为0，表示不限制
             eternal  是否是永恒数据，如果是，则它的超时设置会被忽略
             overflowToDisk 如果内存中数据数量超过maxElementsInMemory限制，是否要缓存到磁盘上
             timeToIdleSeconds 对象空闲时间，指对象在多长时间没有被访问就会失效。只对eternal为false的有效。默认值0，表示一直可以访问 单位秒
             timeToLiveSeconds  对象存活时间，指对象从创建到失效所需要的时间。只对eternal为false的有效。默认值0，表示一直可以访问
             diskPersistent 	 是否在磁盘上持久化。指重启jvm后，数据是否有效。默认为false
             diskExpiryThreadIntervalSeconds  对象检测线程运行时间间隔。标识对象状态的线程多长时间运行一次
             diskSpoolBufferSizeMB 	 DiskStore使用的磁盘大小，默认值30MB。每个cache使用各自的DiskStore
             memoryStoreEvictionPolicy  如果内存中数据超过内存限制，向磁盘缓存时的策略。默认值LRU，可选FIFO、LFU
             */
            cache = new Cache(cacheName, 10000, MemoryStoreEvictionPolicy.LFU, true, null, true,
                    120, 120, false, 1000, null, null,
                    10000000, 30,
                    true, true, "serialization", true);

            //method 2: create new one via CacheManager
            //CacheManager.getInstance().addCache("CACHE_NEW");

        }else{
            cache = cacheManager.getCache(cacheName);
        }

        return cache;
    }

    /**
     * 添加数据到 cache 中
     * @param cacheName 缓存名称
     * @param key 缓存数据的 key
     * @param data 缓存数据的 data
     */
    public void add(String cacheName , String key ,Object data){
        Cache cache = getCacheOrAdd(cacheName);
        cache.put(new Element(key,data));
    }

    /**
     * 从 cache 中获取数据
     * @param cacheName 缓存名称
     * @param key 缓存数据的 key
     * @return 返回结果
     */
    public Object get(String cacheName , String key){
        Cache cache = getCacheOrAdd(cacheName);
        return cache.get(key).getObjectValue();
    }

    /**
     * 从 cache 中移除数据
     * @param cacheName 缓存名称
     * @param key 缓存数据的 key
     * @return 返回结果
     */
    public Object remove(String cacheName , String key){
        Cache cache = getCacheOrAdd(cacheName);
        return cache.remove(key);
    }

    /**
     * 在缓存中是否存在
     * @param cacheName 缓存名称
     * @param key 缓存数据的 key
     * @return 是否存在
     */
    public boolean isExist(String cacheName , String key) {
        Cache cache = getCacheOrAdd(cacheName);
        Element element = cache.getQuiet(key);

        return (element == null);
    }
}
