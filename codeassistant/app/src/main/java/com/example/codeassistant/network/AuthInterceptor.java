// 包路径：com.example.codeassistant.network
package com.example.codeassistant.network;

import com.example.codeassistant.util.DataStoreUtil;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        String token = String.valueOf(DataStoreUtil.getToken()); // 从DataStore获取Token

        // 如果有Token，添加到请求头
        Request newRequest = originalRequest;
        if (token != null && !token.isEmpty()) {
            newRequest = originalRequest.newBuilder()
                    .addHeader("Authorization", "Bearer " + token)
                    .build();
        }
        return chain.proceed(newRequest);
    }
}