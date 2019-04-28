package com.liuqi.mvc.base;

import java.util.Observable;

/**
 * 类说明 <br>
 *     分页数据
 * <p>
 * 构造说明 :
 * <pre>
 *   用于实现分页数据，根据各类框架定义page 和 limit
 * </pre>
 *
 * @author : alexliu
 * @version v1.0 , Create at 10:32 AM 2019/4/26
 */
public class NunaPagebean extends Observable{

    //分页数据，查询用
    private Integer page;

    //分页数据，查询用
    private Integer limit;

    public Integer getPage() {
        return page;
    }

    public Integer getLimit() {
        return limit;
    }

    /**
     * 一般通用分页设置
     *
     * 适用于 page 以 1开始 的框架
     *
     * @param page 第几页
     * @param limit 每页数量
     */
    public void setPageGeneral(int page,int limit){

        if(page == 1){
            this.page = page-1;
        }else if(page > 1){
            this.page = (page-1)*limit;
        }
        this.limit = limit;
    }
}
