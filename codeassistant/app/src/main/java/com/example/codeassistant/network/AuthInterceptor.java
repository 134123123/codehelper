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

        // 关键修复：使用 blockingGet() 同步获取 Token
        // 注意：DataStoreUtil.getToken() 需要处理可能为空的情况
        String token = "";
        try {
            token = DataStoreUtil.getToken().blockingGet();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Request.Builder builder = originalRequest.newBuilder();
        if (token != null && !token.isEmpty()) {
            builder.addHeader("Authorization", "Bearer " + token);
        }

        return chain.proceed(builder.build());
    }
}