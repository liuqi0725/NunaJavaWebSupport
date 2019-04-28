package com.liuqi.nuna.mvc.utils;

import freemarker.ext.beans.BeansWrapper;
import freemarker.template.TemplateModel;
import freemarker.template.Version;
import org.springframework.context.annotation.Configuration;
import org.springframework.ui.ModelMap;

/**
 * 类说明 <br>
 *     freemarker的加载静态类资源
 * <p>
 * 构造说明 :
 * <pre>
 *   在需要 freemarker 页面使用 java 静态类时，应该添加此类
 *   SummerFreeMarker.initStatics
 *
 *   建议在拦截器中加载
 *
 * </pre>
 *
 * @author : alexliu
 * @version v1.0 , Create at 10:32 AM 2019/4/26
 */
@Configuration
public abstract class NunaFreeMarkerUtils {

    /**
     * freemark 加载系统中所有静态资源，可以在模板（ftl）中调用
     * @param map map
     */
    public static void initStatics(final ModelMap map) {
        // you can also create the Version like: new Version("2.3.27");
        BeansWrapper wrapper = new BeansWrapper(new Version(2, 3, 27));
        TemplateModel statics = wrapper.getStaticModels();
        map.addAttribute("statics", statics);
    }


}
