package com.app.ace.retrofit;


import com.app.ace.entities.CreatePostEnt;
import com.app.ace.entities.CreaterEnt;
import com.app.ace.entities.HomeResultEnt;
import com.app.ace.entities.ResponseWrapper;
import com.app.ace.entities.RegistrationResult;

import java.io.File;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface WebService {


   /* @FormUrlEncoded
    @POST("user/register")
    Call<ResponseWrapper<RegistrationResult>> resgiterTrainee(
                    @Field("social_media_id") String social_media_id,
                    @Field("social_media_platform") String social_media_platform,
                    @Field("first_name") String first_name,
                    @Field("last_name") String last_name,
                    @Field("phone_number") String mobile_no,
                    @Field("email") String email,
                    @Field("password") String password,
                    @Field("profile_picture") File profile_picture,
                    @Field("user_type") String user_type,
                    @Field("device_type") String device_type);*/

    @Multipart
    @POST("user/register")
    Call<ResponseWrapper<RegistrationResult>> resgiterTrainee(
            @Part("social_media_id") RequestBody social_media_id,
            @Part("social_media_platform") RequestBody social_media_platform,
            @Part("first_name") RequestBody first_name,
            @Part("last_name") RequestBody last_name,
            @Part("phone_number") RequestBody mobile_no,
            @Part("email") RequestBody email,
            @Part("password") RequestBody password,
            @Part MultipartBody.Part profile_picture,
            @Part("user_type") RequestBody user_type,
            @Part("device_type") RequestBody device_type);

    @Multipart
    @POST("user/register")
    Call<ResponseWrapper<RegistrationResult>> resgiterTrainer(
            @Part("social_media_id") RequestBody social_media_id,
            @Part("social_media_platform") RequestBody social_media_platform,
            @Part("first_name") RequestBody first_name,
            @Part("last_name") RequestBody last_name,
            @Part("phone_number") RequestBody phone_number,
            @Part("email") RequestBody email,
            @Part("password") RequestBody password,
            @Part MultipartBody.Part profile_picture,
            @Part("user_type") RequestBody user_type,
            @Part("education") RequestBody education,
            @Part("university") RequestBody university,
            @Part("gym_name") RequestBody gym_name,
            @Part("gym_latitude") RequestBody gym_latitude,
            @Part("gym_longitude") RequestBody gym_longitude,
            @Part("speciality") RequestBody speciality,
            @Part("experience") RequestBody experience,
            @Part("primary_reason") RequestBody primary_reason,
            @Part("gym_days") RequestBody gym_days,
            @Part("gym_timing_from") RequestBody gym_timing_from,
            @Part("gym_timing_to") RequestBody gym_timing_to,
            @Part("device_type") RequestBody device_type);

   /* @FormUrlEncoded
    @POST("user/register")
    Call<ResponseWrapper<RegistrationResult>> resgiterTrainer(
            @Field("social_media_id") String social_media_id,
            @Field("social_media_platform") String social_media_platform,
            @Field("first_name") String first_name,
            @Field("last_name") String last_name,
            @Field("phone_number") String phone_number,
            @Field("email") String email,
            @Field("password") String password,
            @Field("profile_picture") File profile_picture,
            @Field("user_type") String user_type,
            @Field("education") String education,
            @Field("university") String university,
            @Field("gym_name") String gym_name,
            @Field("gym_latitude") String gym_latitude,
            @Field("gym_longitude") String gym_longitude,
            @Field("speciality") String speciality,
            @Field("experience") String experience,
            @Field("primary_reason") String primary_reason,
            @Field("gym_days") String gym_days,
            @Field("gym_timing_from") String gym_timing_from,
            @Field("gym_timing_to") String gym_timing_to,
            @Field("device_type") String device_type);*/

    @FormUrlEncoded
    @POST("user/login")
    Call<ResponseWrapper<RegistrationResult>> loginUser(
            @Field("email") String email,
            @Field("password") String password,
            @Field("device_token") String device_token,
            @Field("device_type") String device_type);


    @FormUrlEncoded
    @POST("user/verify")
    Call<ResponseWrapper<RegistrationResult>> verifyUser(
            @Field("verification_code") String verification_code,
            @Field("user_id") String user_id);


    @GET("post")
    Call<ResponseWrapper<HomeResultEnt>> getAllHomePosts();


    @FormUrlEncoded
    @POST("user/sociallogin")
    Call<ResponseWrapper<RegistrationResult>> socialLogin(
            @Field("social_media_id") String social_media_id,
            @Field("social_media_platform") String social_media_platform,
            @Field("profile_picture") String profile_picture,
            @Field("device_token") String device_token,
            @Field("device_type") String device_type);


    @FormUrlEncoded
    @POST("user/resendcode")
    Call<ResponseWrapper<RegistrationResult>> resencCode(
            @Field("email") String email);

    @Multipart
    @POST("post/create")
    Call<ResponseWrapper<CreatePostEnt>> createPost(
            @Part("caption") RequestBody caption,
            @Part MultipartBody.Part image,
            @Part("user_id") RequestBody user_id);



    @GET("user/{user_id}")
    Call<ResponseWrapper<CreaterEnt>> traineeProfile(
            @Path("user_id") String user_id);


    @GET("post/user/{user_id}")
    Call<ResponseWrapper<CreaterEnt>> UserProfilePosts(
            @Path("user_id") String user_id);


   /* @Multipart
    @GET("user/{user_id}")
    Call<ResponseWrapper<CreaterEnt>> traineeProfile(
            @Part("user_id") RequestBody user_id);
*/

   /* @Part("image") File image,*/

}