package com.liuqi.nuna.mvc.base;

import com.liuqi.nuna.common.res.DataResult;
import com.liuqi.nuna.common.res.PageDataResult;
import com.liuqi.nuna.core.c.ResponseMessageType;
import org.apache.commons.lang3.StringUtils;
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
     * @param params 入参，参数最大长度：3 <br>
     *        支持如下类型参数:<br>
     *        <ul>
     *               <li>{@link java.lang.Boolean} 代表接口执行的结果，默认为 true</li>
     *               <li>{@link java.lang.String} 代表返回的消息，默认为空 </li>
     *               <li>{@link java.util.Map Map&lt;String, Object&gt;} 代表返回的数据，默认为空</li>
     *        </ul>
     * @return map 类型
     */
    protected Map<String, Object> renderJSON(Object... params){

        boolean success = true;
        String msg = "";
        Map data = null;

        int index = 0;

        for(Object o : params){
            if(o instanceof Boolean){
                success = (Boolean) o;
            }else if(o instanceof String){
                msg = (String)o;
            }else if(o instanceof Map){
                data = (Map)o;
            }else{
                throw new RuntimeException("The params Only support [Boolean] , [String] , [Map] Type. but ["+o.getClass().getName()+"] given." );
            }
            index++;
        }

        if(index > 3){
            throw new RuntimeException("The method max support 3 param. but ["+index+"] given." );
        }

        Map<String, Object> map = new HashMap<String,Object>();
        map.put("success", success);
        map.put("data", data);
        map.put("msg", msg);
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
     * 返回 layui table 的数据类型
     * @param res 分页查询返回值 {@link PageDataResult}
     * @return map
     */
    protected Map<String, Object> renderLayuiTableData(PageDataResult res){
        Map<String, Object> ret = new HashMap<>();
        ret.put("code", res.getStatus() ? 0 : 1);
        ret.put("msg", res.getMsg());
        ret.put("count", res.getTotal() == null ? 0 : res.getTotal());
        ret.put("data", res.getPageData());
        return ret;
    }

    /**
     * 重定向到指定路径
     * @param redirectUrl <b>不能为空</b>。<br>
     *                   <b>比如:</b> <br>
     *                   /user/list <br> /user/info/1 <br> /user/info?id=1&amp;name=admin;
     * @return 重定向的路径
     */
    protected String redirectURL(String redirectUrl){
        return "redirect:"+redirectUrl;
    }

    /**
     * 重定向到指定路径
     * @param params 例如 /test/getUser
     *        支持如下类型参数:<br>
     *        <ul>
     *               <li>{@link ModelAndView} 如果不传，由方法内部新建。</li>
     *               <li>{@link java.lang.String} redirectUrl <b>不能为空</b>。<br>
     *                   <b>比如:</b> <br>
     *                   /user/list <br> /user/info/1 <br> /user/info?id=1&amp;name=admin;
     *               </li>
     *        </ul>
     * @return {@link ModelAndView}
     */
    protected ModelAndView redirect(Object... params){

        ModelAndView mv = null;
        String redirectUrl = "";

        for(Object o : params){
            if(o instanceof ModelAndView){
                mv = (ModelAndView)o;
            }else if(o instanceof String){
                redirectUrl = (String)o;
            }else{
                throw new RuntimeException("The params Only support [ModelAndView] , [String] Type. but ["+o.getClass().getName()+"] given." );
            }
        }

        if(StringUtils.isEmpty(redirectUrl)){
            throw new RuntimeException("Not found redirect URL." );
        }

        if(mv == null){
            mv = new ModelAndView();
        }

        mv.setViewName("redirect:" + redirectUrl);

        return mv;
    }

    /**
     *
     * @param model 重定向 map
     * @param msg_type 消息类型 {@link ResponseMessageType}
     * @param msg spring mvc 返回消息
     */
    protected void setRediectMessage(RedirectAttributesModelMap model,ResponseMessageType msg_type , String msg){

        if(msg_type == ResponseMessageType.SUCCESS){
            setRediectSuccessMessage(model,msg);
        }else if(msg_type == ResponseMessageType.ERROR){
            setRediectErrorMessage(model,msg);
        }else if(msg_type == ResponseMessageType.WARN){
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
     * @param msg_type 消息类型 {@link ResponseMessageType}
     * @param msg 消息体
     */
    protected void setForwardSuccessMessage(ModelAndView mav , ResponseMessageType msg_type , String msg){

        if(msg_type == ResponseMessageType.SUCCESS){
            setForwardSuccessMessage(mav,msg);
        }else if(msg_type == ResponseMessageType.ERROR){
            setForwardErrorMessage(mav,msg);
        }else if(msg_type == ResponseMessageType.WARN){
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
     * 过滤前台直接传 bean 参数处理
     * @param binder {@link WebDataBinder}
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
