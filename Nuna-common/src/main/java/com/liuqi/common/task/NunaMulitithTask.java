package com.liuqi.common.task;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * 类说明 <br>
 *     多任务处理
 * <p>
 * 构造说明 :
 * <pre>
 *   需要时调用
 * </pre>
 *
 * @author : alexliu
 * @version v1.0 , Create at 10:32 AM 2019/4/26
 */
public class NunaMulitithTask {


    private static Logger logger = LogManager.getLogger(NunaMulitithTask.class);


    /**
     * 线程池个数 30为默认值
     */
    private int thread_pool_num = 5;

    /**
     * 单线程最大队列数
     */
    private int max_queue_size_default = 50000;

    /**
     * 启动任务管理
     * @param taskNumber 启动的线程数
     */
    public NunaMulitithTask(Integer taskNumber){
        this.thread_pool_num = taskNumber;
        NunaMulitithTaskHandler.setQueue(max_queue_size_default);
    }

    /**
     * 启动任务管理
     * @param taskNumber 启动的线程数
     * @param max_queue_size 线程队列最大排队数
     */
    public NunaMulitithTask(Integer taskNumber , Integer max_queue_size){
        this.thread_pool_num = taskNumber;
        NunaMulitithTaskHandler.setQueue(max_queue_size);
    }

    private List<NunaMulitithTaskProcess> list;

    /**
     * 启动多线程处理，执行完排队队列中的数据后，停止。
     */
    public void run() {
        runMultithThreads();
    }

    public void close(){
        for(NunaMulitithTaskProcess t : list){
            NunaMulitithTaskHandler.putDone();
        }
    }

    private List<NunaMulitithTaskProcess> runMultithThreads(){
        list = new ArrayList<NunaMulitithTaskProcess>();
        try {
            logger.error("=========================== THE TASK THREAD START. ============================ ");

            String name = "TASK_THREAD_";

            //启动任务线程
            NunaMulitithTaskProcess<?> poolThread = null;
            NunaMulitithTaskListener listen = new NunaMulitithTaskListener();

            for(int i=1; i < this.thread_pool_num; i++){
                poolThread = new NunaMulitithTaskProcess<>();
                poolThread.addObserver(listen);

                Thread t = new Thread(poolThread,name+i);
                t.start();

                list.add(poolThread);
            }

        } catch (Exception e) {
            logger.error("=========================== THE TASK THREAD FAILD. ============================");
            e.printStackTrace();
        }

        return list;
    }

}
