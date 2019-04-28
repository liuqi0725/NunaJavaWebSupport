package com.liuqi.common.task;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Observable;
import java.util.Observer;


/**
 * 类说明 <br>
 *     线程监听
 * <p>
 * 构造说明 :
 * <pre>
 *   观察者模式，观察者在线程运行中设置
 * </pre>
 *
 * @author : alexliu
 * @version v1.0 , Create at 10:32 AM 2019/4/26
 */
public class NunaMulitithTaskListener implements Observer{

    private static Logger logger = LogManager.getLogger(NunaMulitithTaskListener.class);
    
	@Override
	public void update(Observable o, Object arg) {

        String name = Thread.currentThread().getName();
        NunaMulitithTaskProcess<?> poolThread = new NunaMulitithTaskProcess<>();

        logger.debug("[{}] : Thread was dead , rebuild it . ",name);

		poolThread.addObserver(this);
        new Thread(poolThread , name).start();

        logger.debug("[{}] : Thread was dead , rebuild it over . ",name);
	}

	
}
