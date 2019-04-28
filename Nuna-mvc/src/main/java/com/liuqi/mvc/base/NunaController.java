package com.liuqi.mvc.base;

import com.liuqi.commons.res.DataResult;
import com.liuqi.core.c.ResponseMessageType;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import java.util.HashMap;
import java.util.Map;

/**
 * 类说明 <br>
 *     controller 基类
 * <p>
 * 构造说明 :
 * <pre>
 *  new DataResultException();
 * </pre>
 *
 * @author : alexliu
 * @version v1.0 , Create at 10:32 AM 2019/4/26
 */
public class NunaController {

    /**
     * 返回 json 对象
     * @author alexliu
     * @param params 按照 status 状态 、data 数据、message、传递
     * @return map 类型
     */
    protected Map<String, Object> renderJSON(Object... params){

        Map<String, Object> map = new HashMap<String,Object>();
        map.put("success", params[0]);
        map.put("data", params.length >= 2 ? params[1] : null);
        map.put("msg", params.length >= 3 ? params[2] : null);
        return map;
    }

    /**
     * 返回 json 对象
     * @param result {@link DataResult} 类型
     * @return map
     */
    protected Map<String, Object> renderJSON(DataResult result){

        Map<String, Object> map = new HashMap<String,Object>();
        map.put("success", result.getStatus());
        map.put("data", result.getData());
        map.put("msg", result.getMsg());
        map.put("msg_type", result.getMsg_type());

        return map;
    }

    /**
     * springMVC 重定向
     * @param action 例如 /test/getUser
     * @return 重定向的路径
     */
    protected String redirect(String action){
        return "redirect:"+action;
    }

    /**
     * 设置跨重定向的 spring mvc 返回消息
     * @param model 重定向 map
     * @param msg 消息
     */
    public void setRediectErrorMessage(RedirectAttributesModelMap model, String msg){
        model.addFlashAttribute("msg", msg);
        model.addFlashAttribute("msg_type", ResponseMessageType.ERROR);
    }

    /**
     * 设置跨重定向的 spring mvc 返回消息
     * @param model 重定向 map
     * @param msg 消息
     */
    public void setRediectSuccessMessage(RedirectAttributesModelMap model,String msg ){
        model.addFlashAttribute("msg", msg);
        model.addFlashAttribute("msg_type", ResponseMessageType.SUCCESS);
    }

    /**
     * 设置跨重定向的 spring mvc 返回消息
     * @param model 重定向 map
     * @param msg 消息
     */
    public void setRediectWarnMessage(RedirectAttributesModelMap model,String msg){
        model.addFlashAttribute("msg", msg);
        model.addFlashAttribute("msg_type", ResponseMessageType.WARN);
    }

    /**
     * 返回 layui table 的数据类型
     * @param status 状态
     * @param data 数据
     * @param dataCount 总行数
     * @param msg 消息 ,仅在失败时有效
     * @return map
     */
    protected Map<String, Object> renderLayuiTableData(boolean status,Object data,Integer dataCount,String msg){
        Map<String, Object> ret = new HashMap<String,Object>();
        ret.put("code", status ? 0 : 1);
        ret.put("msg", msg);
        ret.put("count", dataCount == null ? 0 : dataCount);
        ret.put("data", data);
        return ret;
    }

    /**
     * 返回 layui fileupload 标准数据
     * @param status 众泰
     * @param data 数据
     * @param msg 消息
     * @return map
     */
    protected Map<String,Object> renderLayuiFileUpload(boolean status,Object data,String msg){

        Map<String, Object> ret = new HashMap<String,Object>();
        ret.put("code", status ? 0 : 1);
        ret.put("msg", msg);
        ret.put("data", data);
        return ret;
    }

}
