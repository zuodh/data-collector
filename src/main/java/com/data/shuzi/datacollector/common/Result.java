package com.data.shuzi.datacollector.common;

import lombok.Data;

/**
 * @author zizuo.zdh
 * @ClassName Result
 * @Description TODO
 * @Date 2018/6/25 14:50
 * @Version 1.0
 **/
@Data
public class Result  {
    private Integer code;
    private String msg;
    private Object data;
    
    private Result(Integer code,String msg,Object data){
        this.code=code;
        this.msg=msg;
        this.data=data;
    }
    /**
    *@author zizuo.zdh
    *@Description TODO
    *@Date 2018/6/25 14:59
    *@Param [code, msg, data]
    *@return com.data.shuzi.datacollector.common.Result
    *
    **/
    public static Result isOK(Integer code,String msg,Object data){
     return new Result(code,msg,data);
    }
    /**
    *@author zizuo.zdh
    *@Description TODO
    *@Date 2018/6/25 14:59
    *@Param [code, msg]
    *@return com.data.shuzi.datacollector.common.Result
    *
    **/
    public static Result isFail(Integer code,String msg){
        return new Result(code,msg,null);
    }
}
