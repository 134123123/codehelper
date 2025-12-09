// 包路径：com.example.codeassistant.network
package com.example.codeassistant.network;

import com.example.codeassistant.model.Banner;
import com.example.codeassistant.model.CodeResponseVO;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiService {
    // 文本提问接口（对应后端TextChatController）
    @FormUrlEncoded
    @POST("ask/text")
    Call<String> askText(@Field("question") String question);

    // 生成代码接口（对应后端CodeAssistantService）
    @FormUrlEncoded
    @POST("code/generate")
    Call<CodeResponseVO> generateCode(
            @Field("userQuestion") String userQuestion,
            @Field("language") String language
    );

    // 获取Banner列表（对应后端BannerController）
    @GET("banner/list")
    Call<List<Banner>> getEnabledBanners();

    // 图片上传接口（对应后端UploadController）
    @Multipart
    @POST("upload/image")
    Call<String> uploadImage(@Part MultipartBody.Part image);
}