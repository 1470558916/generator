package com.project.generator.constant;

/**
 * @Author YM
 * @Date 2022/03/20
 * @Description
 */
public enum ResultCode {

    UNKNOWN_ERROR(400,"未知错误"),
    SUCCESS(200,"成功"),
    ERROR(500,"失败"),
    FAIL(404,"访问失败");

    private Integer code;
    private String msg;

    ResultCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer code(){
        return this.code;
    }

    public String msg(){
        return this.msg;
    }

}
