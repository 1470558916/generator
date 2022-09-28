package com.project.generator.constant;

import javafx.util.Builder;

/**
 * @Author YM
 * @Date 2022/03/20
 * @Description
 */
public class ResponseResult<T> {

    private Integer status;
    private String msg;
    private T data;

    public ResponseResult() {
        super();
    }

    public ResponseResult(Integer status, String msg, T data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public static ResponseResult success(String msg) {
        ResponseResult responseResult = new ResponseResult();
        responseResult.setMsg(msg);
        responseResult.setStatus(ResultCode.SUCCESS.code());
        return responseResult;
    }

    public static <T> ResponseResult success(String msg,T data) {
        ResponseResult responseResult = new ResponseResult();
        responseResult.setMsg(msg);
        responseResult.setStatus(ResultCode.SUCCESS.code());
        responseResult.setData(data);
        return responseResult;
    }

    public static ResponseResult fail(String msg) {
        ResponseResult responseResult = new ResponseResult();
        responseResult.setMsg(msg);
        responseResult.setStatus(ResultCode.FAIL.code());
        return responseResult;
    }
    public static ResponseResult error(String msg) {
        ResponseResult responseResult = new ResponseResult();
        responseResult.setMsg(msg);
        responseResult.setStatus(ResultCode.ERROR.code());
        return responseResult;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Result{" +
                "status='" + status + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
