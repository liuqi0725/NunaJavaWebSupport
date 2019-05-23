package com.liuqi.nuna.common.res;


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
public class PageDataResult extends DataResult{

    private List pageData;

    private Integer total;

    public List getPageData() {
        return pageData;
    }

    public void setPageData(List pageData) {
        this.pageData = pageData;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }


}
