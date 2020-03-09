package com.liuqi.nuna.common.log;

/**
 * 类说明 <br>
 *     日志常量
 * <p>
 * 构造说明 :
 * <pre>
 *   在使用自定义日志的类或函数上
 *
 * </pre>
 *
 * @author : alexliu
 * @version v1.0 , Create at 10:32 AM 2019/4/26
 */
public enum NunaLogOperationType {

    LOGIN("login","登陆操作"),
    LOGINOUT("loginout","退出登陆操作"),
    ADD("add","添加操作"),
    DELETE("delete","删除操作"),
    UPDATE("update","修改操作"),
    QUERY("query","查询操作"),
    BATCH("batch","批量操作");

    private String val;

    private String description;

    private NunaLogOperationType(String val , String description){
        this.val = val;
        this.description = description;
    }

    public String getVal(){
        return this.val;
    }


    @Override
    public String toString() {
        return this.val;
    }
}
