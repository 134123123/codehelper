package com.example.codeassistant.network;

import com.example.codeassistant.model.*;
import java.util.List;
import java.util.Map;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.*;

public interface ApiService {

    // --- AuthController ---
    @POST("auth/login")
    Call<String> login(@Body UserLoginDTO dto); // 后端直接返回 String (Token)

    @POST("auth/register")
    Call<String> register(@Body UserRegisterDTO dto);

    // --- BannerController ---
    @GET("banner/list")
    Call<List<Banner>> getBanners();

    // --- TextChatController ---
    @POST("ask/text") //
    Call<String> askText(@Body Map<String, String> request); // 后端接收 Map<String,String>

    // --- ImageAnalysisController ---
    @POST("analyze/image") //
    Call<ImageAnalysisVO> analyzeImage(@Body Map<String, String> request);

    // --- UploadController ---
    @Multipart
    @POST("upload/image")
    Call<String> uploadImage(@Part MultipartBody.Part file);

    // --- MemoController ---
    @GET("memo") //
    Call<List<Memo>> getMemos();

    @POST("memo")
    Call<String> addMemo(@Body MemoAddDTO dto);

    @PUT("memo")
    Call<String> updateMemo(@Body MemoUpdateDTO dto);

    @DELETE("memo/{id}")
    Call<String> deleteMemo(@Path("id") Long id);
}