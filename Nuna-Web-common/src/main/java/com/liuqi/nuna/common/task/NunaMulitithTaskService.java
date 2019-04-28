package com.liuqi.nuna.common.task;

/**
 * 类说明 <br>
 *     任务处理 service
 * <p>
 * 构造说明 :
 * <pre>
 *   任务处理类，由第三方实现，线程池取出对象后，会执行该方法
 * </pre>
 *
 * @author : alexliu
 * @version v1.0 , Create at 10:32 AM 2019/4/26
 */
public interface NunaMulitithTaskService {

	/**
	 * task 处理后会回调该方法，由第三方实现，并传回对象
	 * 2017年12月8日 上午11:02:02
	 * @author alexliu | liuqi_0725@aliyun.com
	 * @param params 回调时，会将加入队列的对象返回
	 * @param <T> 返回 T 类型，需要自己转型
	 *           比如 String a = (String) object;
	 */
	<T> void process(T... params) ;
}
