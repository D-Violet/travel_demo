package com.dingyu.travle_demo.core;


/**
 * 响应结果生成工具
 */
public class ResultGenerator {
    public static final String DEFAULT_SUCCESS_MESSAGE="success";

    public static Result genSuccessResult() {
        return  new  Result().setCode(ResultCode.SUCCESS).setMessage(DEFAULT_SUCCESS_MESSAGE);
    }

    public static Result genSuccessResult(Object data){
        return new Result()
                .setCode(ResultCode.SUCCESS)
                .setMessage(DEFAULT_SUCCESS_MESSAGE)
                .setData(data);
    }

    public static Result genFailResult(String message) {
        return new Result()
                .setCode(ResultCode.FAIL)
                .setMessage(message);
    }
    public static Result genResult(String message) {
        return new Result()
                .setCode(ResultCode.DATA_ERROR)
                .setMessage(message);
    }

    public static Result genSuccessMsgResult(String msg) {
        return new Result()
                .setCode(ResultCode.SUCCESS)
                .setMessage(msg);
    }
}
