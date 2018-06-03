package com.jrm.retrofitlibrary.retrofit;

import java.io.IOException;

/**
 * Created by jiangrenming on 2018/6/2.
 */

public class TokenExpireException extends IOException {

    public int code;
    public String message;
    public TokenExpireException(){}

    public TokenExpireException(Throwable throwable, int code,String message) {
        super(throwable);
        this.code = code;
        this.message  = message;
    }
}
