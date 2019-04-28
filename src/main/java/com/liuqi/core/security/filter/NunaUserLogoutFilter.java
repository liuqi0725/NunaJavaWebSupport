package com.liuqi.core.security.filter;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.springframework.stereotype.Service;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;


/**
 * 类说明 <br>
 *     Shrio 退出登录业务处理
 * <p>
 * 构造说明 :
 * <pre>
 *     需要在 spring 中配置 {@code
 *     <!-- 退出登录的过滤器 -->
 *         <bean id="logoutFilter" class="com.liuqi.summer.security.filter.SummerUserLogoutFilter">
 *             <property name="redirectUrl" value="/login" />
 *         </bean>
 *     }
 *
 * </pre>
 *
 * @author : alexliu
 * @version v1.0 , Create at 10:32 AM 2019/4/26
 */
@Service
public class NunaUserLogoutFilter extends LogoutFilter{

    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {

        Subject subject = getSubject(request,response);

        String redirectUrl = getRedirectUrl(request,response,subject);

        subject.logout();

        issueRedirect(request,response,redirectUrl);

        //返回false表示不执行后续的过滤器，直接返回跳转到登录页面
        return false;
    }
}
