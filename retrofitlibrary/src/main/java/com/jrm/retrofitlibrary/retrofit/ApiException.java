package com.jrm.retrofitlibrary.retrofit;

import java.io.IOException;

/**
 * Created by jiangrenming on 2018/6/2.
 */

public class ApiException extends IOException {

    public int code;
    public String message;

    public ApiException(){}

    public ApiException(Throwable throwable, int code,String message) {
        super(throwable);
        this.code = code;
        this.message  = message;
    }
}
