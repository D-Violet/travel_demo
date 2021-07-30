package com.dingyu.travle_demo.enums;

public enum StatusEnum implements CodeEnum {
    /**
     * 启用
     */
    UP_STATUS(0,"启用"),
//    dsad
    DOWM_STATUS(1, "停用"),
    /**
     *
     */
    Third_STATUS(2, " "),
    ;

    private Integer  code;

    private String message;

    StatusEnum(Integer code, String message){
        this.code=code;
        this.message=message;
    }
    @Override
    public Integer getCode() {
        return null;
    }
}
