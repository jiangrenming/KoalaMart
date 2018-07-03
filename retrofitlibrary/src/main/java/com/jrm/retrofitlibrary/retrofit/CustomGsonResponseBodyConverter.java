package com.jrm.retrofitlibrary.retrofit;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Created by jiangrenming on 2018/4/19.
 */

public class CustomGsonResponseBodyConverter <T> implements Converter<ResponseBody, T> {

    private final Gson gson;
    private final TypeAdapter<T> adapter;



    CustomGsonResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        String json = value.string();
        Log.i("解析返回的数据",json);
        BaseResponseBean responseBean = gson.fromJson(json, BaseResponseBean.class);
        if (responseBean.getCode() != 200){
            value.close();
            switch (responseBean.getCode()){
                case 300:
                    ApiException apiException = new ApiException();
                    apiException.setMessage(responseBean.getMsg());
                    throw apiException;
                case 401:
                    throw new TokenExpireException();
            }
        }
        MediaType contentType = value.contentType();
        Charset charset = contentType != null ? contentType.charset() :Charset.forName("UTF-8");
        InputStream inputStream = new ByteArrayInputStream(json.getBytes());
        Reader reader = new InputStreamReader(inputStream, charset);
        JsonReader jsonReader = gson.newJsonReader(reader);
        try {
            return adapter.read(jsonReader);
        } finally {
            value.close();
        }
    }

}
