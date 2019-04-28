package com.liuqi.nuna.common.task;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * 类说明 <br>
 *     线程池操作处理程序
 * <p>
 * 构造说明 :
 * <pre>
 *   静态函数，直接调用
 * </pre>
 *
 * @author : alexliu
 * @version v1.0 , Create at 10:32 AM 2019/4/26
 */
public class NunaMulitithTaskHandler {

    /*
     * 关于阻塞队列的函数说明
     *
     * 入列：
     *      add 当队列中数据达到上限时，抛出IllegalStateException 异常
     *      offer 当队列中数据达到上限时，返回 false，反之返回 true
     *      put 阻塞式
     *
     * 出列：
     *      remove 为空时，抛出IllegalStateException 异常
     *      peek   取出为 true， 为空为 false
     *      take 阻塞式 为空时，阻塞
     */

    /**
     * 排队队列
     */
    private static ArrayBlockingQueue<NunaMulitithTaskBean> queueingQueue = null;

    /**
     * 构造一个可关闭阻塞线程的包
     */
    private static final NunaMulitithTaskBean DONE = new NunaMulitithTaskBean(null);


    /**
     *
     * @param service 实现 TaskProcess 的类对象
     * @param params 希望在排队后，返回处理的对象
     * @param <T> 任意类型
     */
    @SafeVarargs
    public static <T> void put(NunaMulitithTaskService service , T...params) {

        checkNull(service ,params);

        //组装参数
        NunaMulitithTaskBean<T> mqb = new NunaMulitithTaskBean<T>(service , params);

        try {
            queueingQueue.put(mqb);
        } catch (InterruptedException e) {
            //异常中断处理
            e.printStackTrace();
        }
    }

    /**
     * 从排队队列中获取数据
     * @return SummerMulitithTaskBean
     * @throws InterruptedException 异常不处理
     */
    public static NunaMulitithTaskBean takeFromQueueingQueue() throws InterruptedException {
        return queueingQueue.take();
    }

    /**
     * 获取排队队列的数据
     * @return 获取队列数量
     */
    public static int getQueueingQueueSize(){
        return queueingQueue.size();
    }

    /**
     * 添加线程关闭的包
     * @param <T> 任意类型
     */
    public static<T> void putDone(){
        try {
            queueingQueue.put(DONE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取线程关闭的包，用于在线程中进行比较
     * @param <T> 任意类型
     * @return SummerMulitithTaskBean
     */
    public static<T> NunaMulitithTaskBean getDone(){
        return DONE;
    }

    /**
     * put 时，检查空
     * @param service SummerMulitithTaskService
     * @param args 参数
     * @param <T> 任意类型
     */
    @SafeVarargs
    private static <T> void checkNull(NunaMulitithTaskService service , T...args){
        if (service == null) {
            throw new NullPointerException("Task process can`t be null. plz check !!!!");
        }
    }

    protected static void setQueue(int queue_max_size){
        queueingQueue = new ArrayBlockingQueue<NunaMulitithTaskBean>(queue_max_size);
    }

}
