package com.liuqi.nuna.common.task;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Observable;

/**
 * 类说明 <br>
 *     任务处理
 * <p>
 * 构造说明 :
 * <pre>
 *   线程的处理类
 * </pre>
 *
 * @author : alexliu
 * @param <T> 任意类型
 * @version v1.0 , Create at 10:32 AM 2019/4/26
 */
public class NunaMulitithTaskProcess<T> extends Observable implements Runnable {

	/**
     * 日志引入
     */
    private static Logger logger = LogManager.getLogger(NunaMulitithTaskProcess.class);

	/**
	 * 关闭线程标志
	 */
	public volatile boolean exit = false;

	/**
	 * 线程启动.
	 * @see Runnable#run()
	 */
	public void run() {
    	
    	String threadName = Thread.currentThread().getName();
    	    	
    	try {

            NunaMulitithTaskBean<?> task = null;
            
            logger.info(" [{}] : Thread is start . ",threadName);
        	while(!exit){
        		
        		logger.debug("[{}] : Thread status. [{}] .",threadName,Thread.currentThread().getState().name());


				logger.debug("[{}] : Current queue num is [{}]" ,
						threadName,
						String.valueOf(NunaMulitithTaskHandler.getQueueingQueueSize())
				);

				logger.debug("[{}] : Get Object from QueueingQueue ." , threadName);

				/*
				 * 从排队队列获取一条数据
				 * 此处会抛出中断[InterruptedException]异常
				 */
				task = NunaMulitithTaskHandler.takeFromQueueingQueue();


				/*
				 * 如果是关闭线程的包，关闭线程
				 */
				if(task == NunaMulitithTaskHandler.getDone()){
					//结束此线程
					exit = true;
					break;
				}


				logger.debug("[{}] : Do process with [{}] .",threadName,task.getService().getClass().getName());

				/*
				 * 回调对应处理
				 */
				try {
					task.getService().process(task.getParams());
				} catch (Exception e) {
					logger.error("[{}] : Do process with [{}] has problems .",threadName,task.getService().getClass().getName());
					e.printStackTrace();
				}

				if(task.getParams().length == 5){
					//模拟异常，触发观察者
					throw new InterruptedException("模拟异常.");
				}

				/*
				 * 判断当前线程状态是否终止
				 */
				if(Thread.currentThread().isInterrupted()){
					throw new InterruptedException("Thread is Interrupted .");
				}
        	}

			logger.debug("[{}] : Thread exit ..",threadName);

		} catch (Exception e) {
			logger.error("[{}] : A serious thread error occurred and we will restart ! ",threadName);
			e.printStackTrace();
			this.reBuild();
		}
    }

	/**
	 * 告诉监听者，线程出错，重启线程
	 */
	private void reBuild(){

        if(true){
            super.setChanged();
        }
        //通知观察者
        notifyObservers();
    }

}

