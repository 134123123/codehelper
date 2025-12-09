package com.example.codeassistant.network;

import com.example.codeassistant.model.Banner; // 确保有 Banner 模型
import com.example.codeassistant.model.ImageAnalysisVO;
import com.example.codeassistant.model.Memo;
import com.example.codeassistant.model.MemoAddDTO;
import com.example.codeassistant.model.MemoUpdateDTO;
import com.example.codeassistant.model.UserLoginDTO;
import com.example.codeassistant.model.UserRegisterDTO;
import java.util.List;
import java.util.Map;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.*;

public interface ApiService {
    // --- 练习模块 (新增) ---
    @POST("exercise/generate")
    Call<String> generateExercise(@Body Map<String, String> params);

    @POST("exercise/check")
    Call<String> checkExercise(@Body Map<String, String> params);

    // --- Banner 模块 (确保存在) ---
    @GET("banner/list")
    Call<List<Banner>> getBanners();

    // --- 原有接口保持不变 ---
    @POST("auth/login")
    Call<String> login(@Body UserLoginDTO dto);

    @POST("auth/register")
    Call<String> register(@Body UserRegisterDTO dto);

    @POST("ask/text")
    Call<String> askText(@Body Map<String, String> request);

    @POST("analyze/image")
    Call<ImageAnalysisVO> analyzeImage(@Body Map<String, String> request);

    @Multipart
    @POST("upload/image")
    Call<String> uploadImage(@Part MultipartBody.Part file);

    @GET("memo")
    Call<List<Memo>> getMemos();

    @POST("memo")
    Call<String> addMemo(@Body MemoAddDTO dto);

    @PUT("memo")
    Call<String> updateMemo(@Body MemoUpdateDTO dto);

    @DELETE("memo/{id}")
    Call<String> deleteMemo(@Path("id") Long id);
}