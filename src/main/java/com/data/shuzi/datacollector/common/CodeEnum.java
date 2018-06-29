package com.data.shuzi.datacollector.common;

public enum CodeEnum {
    OK(200,"OK"),FAIL(500,"sever error");

    private int code;

    private String value;
     CodeEnum(int code,String value){
        this.code=code;
        this.value=value;
    }
    public int getCode(){
         return code;
    }

}
