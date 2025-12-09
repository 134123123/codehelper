package com.example.codeassistant.network;

import com.google.gson.GsonBuilder;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory; // 导入

public class RetrofitClient {
    private static Retrofit sRetrofit;

    public static Retrofit getInstance() {
        if (sRetrofit == null) {
            sRetrofit = new Retrofit.Builder()
                    .baseUrl(ApiConfig.BASE_URL)
                    .client(OkHttpProvider.getClient())
                    // 关键：先添加 ScalarsConverter 处理字符串，再添加 GsonConverter 处理 JSON 对象
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
                    .build();
        }
        return sRetrofit;
    }
}