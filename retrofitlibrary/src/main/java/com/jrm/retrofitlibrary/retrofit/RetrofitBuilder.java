package com.jrm.retrofitlibrary.retrofit;

import android.content.Context;

/**
 * @author jiangrenming
 * @date 2018/1/3
 */

public class RetrofitBuilder {


    private String baseUrl;
    private Context mContext;

    public Context getmContext() {
        return mContext;
    }

    public String getBaseUrl() {
        return baseUrl;
    }


    public RetrofitBuilder(){}
    /**
     * 构建器
     */
    public static class Builder {

        private RetrofitBuilder config;
        public Builder() {
            config = new RetrofitBuilder();
        }
        public RetrofitBuilder build() {
            return config;
        }
        public Builder setBaseUrl(String url){
            config.baseUrl = url;
            return this;
        }
        public Builder setContext(Context context){
            config.mContext = context;
            return this;
        }
    }
}
