package com.liuqi.nuna.mvc.base;

import com.liuqi.nuna.common.res.DataResult;
import com.liuqi.nuna.common.res.PageDataResult;
import com.liuqi.nuna.core.c.ResponseMessageType;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import java.text.SimpleDateFormat;
import java.util.Date;
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
     *
     * @param model 重定向 map
     * @param msg_type 绑定 {@link DataResult#getMsg_type()}
     * @param msg spring mvc 返回消息
     */
    protected void setRediectMessage(RedirectAttributesModelMap model,String msg_type , String msg){

        if(msg_type.equalsIgnoreCase(ResponseMessageType.SUCCESS)){
            setRediectSuccessMessage(model,msg);
        }else if(msg_type.equalsIgnoreCase(ResponseMessageType.ERROR)){
            setRediectErrorMessage(model,msg);
        }else if(msg_type.equalsIgnoreCase(ResponseMessageType.WARN)){
            setRediectWarnMessage(model,msg);
        }
    }
    /**
     * 设置跨重定向的 spring mvc 返回消息
     * @param model 重定向 map
     * @param msg 消息
     */
    protected void setRediectErrorMessage(RedirectAttributesModelMap model, String msg){
        model.addFlashAttribute("msg", msg);
        model.addFlashAttribute("msg_type", ResponseMessageType.ERROR);
    }

    /**
     * 设置跨重定向的 spring mvc 返回消息
     * @param model 重定向 map
     * @param msg 消息
     */
    protected void setRediectSuccessMessage(RedirectAttributesModelMap model,String msg ){
        model.addFlashAttribute("msg", msg);
        model.addFlashAttribute("msg_type", ResponseMessageType.SUCCESS);
    }

    /**
     * 设置跨重定向的 spring mvc 返回消息
     * @param model 重定向 map
     * @param msg 消息
     */
    protected void setRediectWarnMessage(RedirectAttributesModelMap model,String msg){
        model.addFlashAttribute("msg", msg);
        model.addFlashAttribute("msg_type", ResponseMessageType.WARN);
    }

    /**
     * 设置视图 spring mvc 返回消息
     * @param mav map
     * @param msg_type 绑定 {@link DataResult#getMsg_type()}
     * @param msg 消息体
     */
    protected void setForwardSuccessMessage(ModelAndView mav , String msg_type , String msg){

        if(msg_type.equalsIgnoreCase(ResponseMessageType.SUCCESS)){
            setForwardSuccessMessage(mav,msg);
        }else if(msg_type.equalsIgnoreCase(ResponseMessageType.ERROR)){
            setForwardErrorMessage(mav,msg);
        }else if(msg_type.equalsIgnoreCase(ResponseMessageType.WARN)){
            setForwardWarningMessage(mav,msg);
        }
    }

    /**
     * 设置视图 spring mvc 返回消息
     * @param mav map
     * @param msg 消息
     */
    protected void setForwardSuccessMessage(ModelAndView mav , String msg){
        mav.addObject("msg",msg);
        mav.addObject("msg_type",ResponseMessageType.SUCCESS);
    }

    /**
     * 设置视图 spring mvc 返回消息
     * @param mav map
     * @param msg 消息
     */
    protected void setForwardWarningMessage(ModelAndView mav , String msg){
        mav.addObject("msg",msg);
        mav.addObject("msg_type",ResponseMessageType.WARN);
    }

    /**
     * 设置视图 spring mvc 返回消息
     * @param mav map
     * @param msg 消息
     */
    protected void setForwardErrorMessage(ModelAndView mav , String msg){
        mav.addObject("msg",msg);
        mav.addObject("msg_type",ResponseMessageType.ERROR);
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
     * 返回 layui table 的数据类型
     * @param res 分页查询返回值 {@link PageDataResult}
     * @return map
     */
    protected Map<String, Object> renderLayuiTableData(PageDataResult res){
        Map<String, Object> ret = new HashMap<String,Object>();
        ret.put("code", res.getStatus() ? 0 : 1);
        ret.put("msg", res.getMsg());
        ret.put("count", res.getTotal() == null ? 0 : res.getTotal());
        ret.put("data", res.getPageData());
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

    /**
     * 过滤前台直接传 bean 参数处理
     * @param binder
     */
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setLenient(false);

        //第二个参数是控制是否支持传入的值是空，这个值很关键，如果指定为false，那么如果前台没有传值的话就会报错
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
        binder.registerCustomEditor(Integer.class, new CustomNumberEditor(Integer.class, true));

    }



}
