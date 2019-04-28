package com.liuqi.nuna.mvc.base;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;

import javax.annotation.Resource;

/**
 * 类说明 <br>
 *     dao 层基类
 * <p>
 * 构造说明 :
 * <pre>
 *   所有的子项目dao 层,涉及到自定义比较复杂的操作，应该继承该类
 * </pre>
 *
 * @author : alexliu
 * @version v1.0 , Create at 10:32 AM 2019/4/26
 */
public class NunaDao extends SqlSessionDaoSupport {

    @Resource
    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
        super.setSqlSessionFactory(sqlSessionFactory);
    }
}
