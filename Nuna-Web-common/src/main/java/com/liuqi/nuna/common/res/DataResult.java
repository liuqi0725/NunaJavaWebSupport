package com.liuqi.nuna.common.res;


import com.liuqi.nuna.common.e.DataResultConvertException;
import com.liuqi.nuna.common.e.DataResultException;
import com.liuqi.nuna.core.c.ResponseMessageType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类说明 <br>
 * 业务层操作返回值，可以直接返回到页面
 * <p>
 * 构造说明 :
 * <pre>
 *   {@code
 *   DataResult ret = new DataResult();
 *   }
 * </pre>
 *
 * @author : alexliu
 * @version v1.0 , Create at 12:53 PM 2018/11/9
 */
public class DataResult {

    private boolean status = true;

    private String msg = "";

    private ResponseMessageType msg_type = ResponseMessageType.SUCCESS;

    private Map<String ,Object> data = new HashMap<String , Object>();

    public boolean getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

    public ResponseMessageType getMsg_type() {
        return msg_type;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public String getDataAsString (String key){
        return (String)this.getData().get(key);
    }

    public boolean getDataAsBoolean (String key){
        return (boolean)this.getData().get(key);
    }

    public Integer getDataAsInteger (String key){
        return (Integer)this.getData().get(key);
    }

    public <T> T getDataAsObject (String key , Class<T> classz) throws DataResultConvertException {

        Object obj = this.getData().get(key);

        // 判断 obj 是否能强转为参数的泛型。不能强转就抛锅。
        if (classz != null && !classz.isInstance(obj)) {
            throw new DataResultConvertException("key >> " + key +", get obj >> " + obj.getClass() + " is not " + classz);
        }

        return (T)obj;
    }

    public <T> List<T> getDataAsList (String key , Class<T> classz) throws DataResultConvertException {

        List<Object> objList = (List)this.getData().get(key);

        Object obj = objList.get(0);
        // 判断 obj 是否能强转为参数的泛型。不能强转就抛锅。
        if (classz != null && !classz.isInstance(obj)) {
            throw new DataResultConvertException("key >> " + key +", get obj >> " + obj.getClass() + " is not " + classz);
        }

        return (List<T>)objList;
    }

    public Map getDataAsMap (String key) {
        return (Map)this.getData().get(key);
    }

    public void setData(String key, Object dataVal) {
        this.data.put(key, dataVal);
    }

    /**
     *
     * 当业务处理成功，使用<i>success</i>可以快速帮助形成业务的返回值
     *
     * <pre>
     *     调用示例
     *     DataResult ret = new DataResult();
     *     ret.success();
     *     ret.success("process msg");
     *     ret.success("process msg" , new Object[]{"key1",data1,...});
     *
     *     你甚至可以在 <i>controller</i> 层通过继承 SummerController 直接返回
     * </pre>
     *
     *
     * @param params 参数可以为 null， 如果需要传递，请严格按照 msg ,data 的顺序传递。
     *               data 必须是数组，并且以键值对形式，单数值为 key，双数值为 data，比如：
     *               {@code new Object[]{"key1",data1,...}} 此类型
     *
     */
    public void success(Object ...params) {
        this.status = true;
        this.msg_type = ResponseMessageType.SUCCESS;
        try {
            setParams(params);
        } catch (DataResultException e) {
            setDefaultParam(e.getMessage());
        }
    }

    /**
     *
     * 当业务处理过程中出现警告，单不影响业务时，可以使用此函数，使用<i>warning</i>可以快速帮助形成业务的返回值
     *
     * <pre>
     *     调用示例
     *     DataResult ret = new DataResult();
     *     ret.warning();
     *     ret.warning("process msg");
     *     ret.warning("process msg" , new Object[]{"key1",data1,...});
     *
     *     你甚至可以在 <i>controller</i> 层通过继承 SummerController 直接返回
     * </pre>
     *
     *
     * @param params 参数可以为 null， 如果需要传递，请严格按照 msg ,data 的顺序传递。
     *               data 必须是数组，并且以键值对形式，单数值为 key，双数值为 data，比如：
     *               {@code new Object[]{"key1",data1,...}} 此类型
     *
     */
    public void warning(Object ...params) {
        this.status = true;
        this.msg_type = ResponseMessageType.WARN;
        try {
            setParams(params);
        } catch (DataResultException e) {
            setDefaultParam(e.getMessage());
        }
    }

    /**
     *
     * 当业务处理过程出现严重错误，可以使用此函数，使用<i>error</i>可以快速帮助形成业务的返回值
     *
     * <pre>
     *     调用示例
     *     DataResult ret = new DataResult();
     *     ret.warning();
     *     ret.warning("process msg");
     *     ret.warning("process msg" , new Object[]{"key1",data1,...});
     *
     *     你甚至可以在 <i>controller</i> 层通过继承 SummerController 直接返回
     * </pre>
     *
     *
     * @param params 参数可以为 null， 如果需要传递，请严格按照 msg ,data 的顺序传递。
     *               data 必须是数组，并且以键值对形式，单数值为 key，双数值为 data，比如：
     *               {@code new Object[]{"key1",data1,...}} 此类型
     *
     */
    public void error(Object ...params) {
        this.status = false;
        this.msg_type = ResponseMessageType.ERROR;
        try {
            setParams(params);
        } catch (DataResultException e) {
            setDefaultParam(e.getMessage());
        }

    }

    private void setDefaultParam(String errorMsg){
        this.status = false;
        this.msg_type = ResponseMessageType.ERROR;
        this.msg = errorMsg;
    }

    /**
     * 内部函数，处理 params
     * @param params success、warning、error 传递的可变参数
     * @throws DataResultException 不处理异常，由外部处理
     */
    private void setParams(Object[] params) throws DataResultException {

        boolean wantSetParam = false;

        if(params != null && params.length >0){
            wantSetParam = true;
        }

        //设置消息
        if(wantSetParam){
            //判断消息
            if(!(params[0] instanceof String)){
                throw new DataResultException("The params message must be String. but "+params[0].getClass()+" given");
            }

            this.msg = (String)params[0];

            //数据
            Object data_temp = null;

            //是否有数据参数
            if(params.length > 1){
                data_temp = params[1];
            }

            // 1. 是否数组
            if(data_temp != null && !data_temp.getClass().isArray()){
                throw new DataResultException("The result data must be array. but "+data_temp.getClass()+" given");
            }else if(data_temp != null){
                //转换数组
                Object[] dataArray = (Object[])data_temp;

                // 2. 是否键值对，必须是 key ， data
                if(dataArray.length%2 != 0){
                    throw new DataResultException("unknown your data params . The data must be key & data array. like new Object[]{key, data ,...}. ");
                }

                //设置值
                for(int i=0; i<dataArray.length; i++){
                    // 单数 key 双数 data
                    Object key = dataArray[i];
                    if((i+1)%2 == 1){
                        if(!(key instanceof String)){
                            throw new DataResultException("The result data key must be String. but "+key.getClass()+" given.");
                        }

                        this.setData((String)key,dataArray[i+1]);
                    }
                }
            }
        }

    }


}
