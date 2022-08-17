package org.hsbc.homework.msg;

import org.hsbc.homework.utils.exception.ErrorCode;

import java.io.Serializable;

public class BaseResponse<T> implements Serializable {
    private static final long serialVersionUID = -809064970535126613L;

    private String errorCode;
    private String errorMsg;
    private String token;
    private T data;

    public BaseResponse success() {
        this.errorCode = ErrorCode.SUCCESS.getCode();
        this.errorMsg = ErrorCode.SUCCESS.getMsg();

        return this;
    }

    public BaseResponse<T> success(T data) {
        this.errorCode = ErrorCode.SUCCESS.getCode();
        this.errorMsg = ErrorCode.SUCCESS.getMsg();
        this.data = data;

        return this;
    }

    public BaseResponse<T> success(String token, T data) {
        this.errorCode = ErrorCode.SUCCESS.getCode();
        this.errorMsg = ErrorCode.SUCCESS.getMsg();
        this.data = data;
        this.token = token;

        return this;
    }

    public BaseResponse fail(ErrorCode errorCode) {
        this.errorCode = errorCode.getCode();
        this.errorMsg = errorCode.getMsg();

        return this;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
