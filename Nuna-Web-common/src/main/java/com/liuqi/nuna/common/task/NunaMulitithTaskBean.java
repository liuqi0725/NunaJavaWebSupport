package com.liuqi.nuna.common.task;

/**
 * 类说明 <br>
 * 线程处理对象
 * <p>
 * 构造说明 :
 * <pre>
 *   {@code
 *   SummerMulitithTaskBean task = new SummerMulitithTaskBean();
 *   }
 * </pre>
 *
 * @author : alexliu
 * @version v1.0 , Create at 12:53 PM 2018/11/9
 */
public class NunaMulitithTaskBean<T> {

    /**
     * 调用实现类时，需要传入的参数
     */
    private T[] params;

    /**
     * 线程执行的实现类
     */
    private NunaMulitithTaskService service;

    /**
     *
     * 创建一个线程执行的实体
     *
     * <pre>
     *     应用需要实现业务处理
     *
     *     public testService implements SummerMulitithTaskService{
     *
     *         public void process(T... params){
     *             // TODO
     *         }
     *     }
     *
     *     传递到线程处理类中
     *     new SummerMulitithTaskBean(testService , params);
     *
     * </pre>
     *
     *
     * @param service 实现  {@link NunaMulitithTaskService} 的对象
     * @param params 任意对象
     */
    @SafeVarargs
    public NunaMulitithTaskBean(NunaMulitithTaskService service , T...params) {
        this.service = service;
        this.setParams(params);
    }

    @SafeVarargs
    private final void setParams(T... params){
        this.params = params;
    }

    public T[] getParams() {
        return this.params;
    }

    /**
     * 获取业务类
     * @return 实现 {@link NunaMulitithTaskService} 的对象
     */
    public NunaMulitithTaskService getService() {
        return service;
    }

    /**
     * 设置业务类
     * @param service 实现 {@link NunaMulitithTaskService} 的对象
     */
    public void setService(NunaMulitithTaskService service) {
        this.service = service;
    }
}
