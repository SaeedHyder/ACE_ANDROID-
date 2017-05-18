package com.app.ace.retrofit;


import com.app.ace.entities.BookingSchedule;
import com.app.ace.entities.CreatePostEnt;
import com.app.ace.entities.CreaterEnt;
import com.app.ace.entities.FollowUser;
import com.app.ace.entities.FollowersCountListEnt;
import com.app.ace.entities.FollowingCountListEnt;
import com.app.ace.entities.GetTraineeBookings;
import com.app.ace.entities.GoogleGeoCodeResponse;
import com.app.ace.entities.HomeResultEnt;
import com.app.ace.entities.MsgEnt;
import com.app.ace.entities.NotificationEnt;
import com.app.ace.entities.PostsEnt;
import com.app.ace.entities.RegistrationResult;
import com.app.ace.entities.ResponseWrapper;
import com.app.ace.entities.ScheduleEnt;
import com.app.ace.entities.ShowComments;
import com.app.ace.entities.TrainerBooking;
import com.app.ace.entities.TrainerBookingCalendarJson;
import com.app.ace.entities.TrainerTimingSlots;
import com.app.ace.entities.User;
import com.app.ace.entities.UserNotificatoin;
import com.app.ace.entities.UserProfile;
import com.app.ace.entities.countEnt;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

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
            @Part("phone_number") RequestBody phone_number,
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
            @Part MultipartBody.Part resume,
            @Part("user_type") RequestBody user_type,
            @Part("education") RequestBody education,
            @Part("university") RequestBody university,
            @Part("gym_name") RequestBody gym_name,
            @Part("gym_address") RequestBody gym_address,
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

    @FormUrlEncoded
    @POST("post")
    Call<ResponseWrapper<HomeResultEnt>> getAllHomePosts(
            @Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("user/search")
    Call<ResponseWrapper<ArrayList<UserProfile>>> getSearchUser(
            @Field("keyword") String username,
            @Field("user_type") String user_type);

    @FormUrlEncoded
    @POST("user/search")
    Call<ResponseWrapper<ArrayList<UserProfile>>> getTrainingSearch(
            @Field("keyword") String username,
            @Field("body_building_type") String body_building_type
    );

    @GET("notification/app/{user_id}")
    Call<ResponseWrapper<ArrayList<NotificationEnt>>> getAppNotification(
            @Path("user_id") String userid
    );

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

    @FormUrlEncoded
    @POST("user/profile")
    Call<ResponseWrapper<UserProfile>> UserProfile(
            @Field("user_id") String user_id,
            @Field("visitor_id") String visitor_id);

@FormUrlEncoded
@POST("schedule/delete")
Call<ResponseWrapper> deleteSchedule(
        @Field("trainer_id")String trainer_id,
        @Field("start_date")String start_date,
        @Field("end_date") String end_date
);
    @GET("post/user/{user_id}")
    Call<ResponseWrapper<CreaterEnt>> UserProfilePosts(
            @Path("user_id") String user_id);

    @GET("message/get/{conversation_id}")
    Call<ResponseWrapper<ArrayList<MsgEnt>>> GetConversation(
            @Path("conversation_id") String conversation_id);

    @FormUrlEncoded
    @POST("message/send")
    Call<ResponseWrapper<ArrayList<MsgEnt>>> SendMsg(
            @Field("sender_id") String sender_id,
            @Field("receiver_id") String receiver_id,
            @Field("message_text") String message_text);

    @GET("notification/count/{user_id}")
    Call<ResponseWrapper<countEnt>> getNotificationCount(
            @Path("user_id") String user_id);

    @GET("message/{user_id}")
    Call<ResponseWrapper<ArrayList<MsgEnt>>> userinbox(
            @Path("user_id") String user_id);

    @FormUrlEncoded
    @POST("user/follow")
    Call<ResponseWrapper<FollowUser>> follow(
            @Field("user_id") String user_id,
            @Field("following_id") String following_id);

    @FormUrlEncoded
    @POST("user/unfollow")
    Call<ResponseWrapper<FollowUser>> unfollow(
            @Field("user_id") String user_id,
            @Field("following_id") String following_id);


    @FormUrlEncoded
    @POST("post/like")
    Call<ResponseWrapper<PostsEnt>> likePost(
            @Field("user_id") String user_id,
            @Field("post_id") int post_id);


    @FormUrlEncoded
    @POST("post/comment/create")
    Call<ResponseWrapper<ShowComments>> CreateComment(
            @Field("user_id") String user_id,
            @Field("post_id") String post_id,
            @Field("comment_text") String comment_text,
            @Field("tag_ids") int tag_ids
            //@Field("parent_comment_id") String parent_comment_id
    );


    @GET("post/comment/{post_id}")
    Call<ResponseWrapper<ArrayList<ShowComments>>> ShowComments(
            @Path("post_id") String post_id);


    @Multipart
    @POST("user/update")
    Call<ResponseWrapper<RegistrationResult>> UpdateTrainee(
            @Part("user_id") RequestBody user_id,
            //  @Part("password") RequestBody password,
            @Part("first_name") RequestBody first_name,
            @Part("last_name") RequestBody last_name,
            @Part("user_status") RequestBody user_status,
            @Part("phone_number") RequestBody phone_number,
            @Part MultipartBody.Part profile_picture);

    @Multipart
    @POST("user/update")
    Call<ResponseWrapper<RegistrationResult>> UpdateTrainer(
            @Part("user_id") RequestBody user_id,
            // @Part("password") RequestBody password,
            @Part("first_name") RequestBody first_name,
            @Part("last_name") RequestBody last_name,
            @Part("phone_number") RequestBody phone_number,
            @Part("university") RequestBody university,
            @Part MultipartBody.Part profile_picture,
            @Part("education") RequestBody education,
            @Part("speciality") RequestBody speciality,
            @Part("gym_address") RequestBody gym_address,
            @Part("gym_latitude") RequestBody gym_latitude,
            @Part("gym_longitude") RequestBody gym_longitude

    );

    @Multipart
    @POST("user/update")
    Call<ResponseWrapper<RegistrationResult>> NotificationStatus(
            @Part("user_id") RequestBody user_id,
            @Part("notification_status") RequestBody notification_status,
            @Part("private_account") RequestBody private_account
    );

    @FormUrlEncoded
    @POST("user/rating/add")
    Call<ResponseWrapper<User>> rating(
            @Field("rating_by") String rating_by,
            @Field("rating") int rating,
            @Field("user_id") String user_id);


    @POST("schedule/create")
    Call<ResponseWrapper> createSchedule(
            @Body ArrayList<TrainerBookingCalendarJson> data);

    @FormUrlEncoded
    @POST("user/updatetoken")
    Call<ResponseWrapper> updateToken(
            @Field("user_id") String userid,
            @Field("device_type") String deviceType,
            @Field("device_token") String token
    );

    @POST("schedule/book")
    Call<ResponseWrapper> bookTrainer(
            @Body ArrayList<BookingSchedule> data);

    @GET("schedule/getTrainer/{trainer_id}")
    Call<ResponseWrapper<ArrayList<ScheduleEnt>>> getSchedule(
            @Path("trainer_id") String user_id);

    @FormUrlEncoded
    @POST("schedule/get")
    Call<ResponseWrapper<TrainerBooking>> getScheduleTrainee(
            @Field("trainer_id") String user_id,
            @Field("start_date") String startDate,
            @Field("end_date")String Enddate);

    @GET("schedule/get/{trainer_id}")
    Call<ResponseWrapper<ArrayList<GetTraineeBookings>>> ShowTraineeBookings(
            @Path("trainer_id") String trainer_id);

    @GET("followers/{user_id}")
    Call<ResponseWrapper<ArrayList<FollowersCountListEnt>>> GetFollowersCountList(
            @Path("user_id") String user_id);

    @GET("followings/{user_id}}")
    Call<ResponseWrapper<ArrayList<FollowingCountListEnt>>> GetFollowingCountList(
            @Path("user_id") String user_id);


    @GET("schedule/get/{trainer_id}")
    Call<ResponseWrapper<ArrayList<TrainerTimingSlots>>> TrainerTimingSlots(
            @Path("trainer_id") String trainer_id);

    @GET("/maps/api/geocode/json")
    Call<GoogleServiceResponse<List<GoogleGeoCodeResponse>>> getLatLongInfo(

            @Query("address") String address,
            @Query("sensor") String sensor);


    @FormUrlEncoded
    @POST("message/block")
    Call<ResponseWrapper> muteConversation(
            @Field("conversation_id") String conversation_id,
            @Field("sender_block") int sender_block,
            @Field("receiver_block") int receiver_block);

    @GET("notification/user/{user_id}")
    Call<ResponseWrapper<ArrayList<UserNotificatoin>>> UserNotification(
            @Path("user_id") String user_id);

    @GET("notification/following/{user_id}")
    Call<ResponseWrapper<ArrayList<UserNotificatoin>>> FollowingNotification(
            @Path("user_id") String user_id);

    @FormUrlEncoded
    @POST("changepassword")
    Call<ResponseWrapper> ChangePassword(
            @Query("user_id") String user_id,
            @Field("password") String password,
            @Field("old_password") String old_password);

    @FormUrlEncoded
    @POST("contactus")
    Call<ResponseWrapper> ContactUs(
            @Field("user_id") String user_id,
            @Field("message") String message);














   /* @Multipart
    @GET("user/{user_id}")
    Call<ResponseWrapper<CreaterEnt>> traineeProfile(
            @Part("user_id") RequestBody user_id);
*/

   /* @Part("image") File image,*/

}