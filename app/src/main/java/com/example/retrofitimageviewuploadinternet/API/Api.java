package com.example.retrofitimageviewuploadinternet.API;

import com.example.retrofitimageviewuploadinternet.Example;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface Api {
//    public static final String BASE_URL = "http://192.168.11.93/banhang/";
//
//    Api api = new Retrofit.Builder()
//            .baseUrl(BASE_URL)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//            .create(Api.class);
//
//    @FormUrlEncoded
//    @POST("upload.php")
//    Call<User> uploadImage(@Part(Const.KEY_USERNAME) RequestBody username,
//                           @Part(Const.KEY_PASSWORD) RequestBody pasdword,
//                           @Part MultipartBody.Part avt);

    public static final String DOMAIN = "https://api.imgur.com/";
    Api api = new Retrofit.Builder()
            .baseUrl(DOMAIN) // API base url
            .addConverterFactory(GsonConverterFactory.create()) // Factory phụ thuộc vào format JSON trả về
            .build()
            .create(Api.class);

    @POST("3/upload/")
    @FormUrlEncoded
    Call<Example> uploadImage(@Header("Authorization") String clientID, @Field("image") String image);
}
