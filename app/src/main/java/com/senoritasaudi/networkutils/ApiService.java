package com.senoritasaudi.networkutils;

import com.senoritasaudi.models.FeedbackResponse;
import com.senoritasaudi.models.responseModels.ClinicsResponseModel;
import com.senoritasaudi.models.responseModels.DepartmentResponseModel;
import com.senoritasaudi.models.responseModels.FavoriteResponseModel;
import com.senoritasaudi.models.responseModels.ImageResponse;
import com.senoritasaudi.models.responseModels.InformationResponseModel;
import com.senoritasaudi.models.responseModels.NotificationResponseModel;
import com.senoritasaudi.models.responseModels.OfferResponseModel;
import com.senoritasaudi.models.responseModels.QRCodeResponse;
import com.senoritasaudi.models.responseModels.RequestsModelResponse;
import com.senoritasaudi.models.responseModels.ReservationResponseModel;
import com.senoritasaudi.models.responseModels.SliderResponseModel;
import com.senoritasaudi.models.responseModels.UserResponseModel;

import java.util.HashMap;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface ApiService {

    @GET("get_sliders")
    Call<SliderResponseModel> getSliderImages();

    @GET("get_category")
    Call<DepartmentResponseModel> getDepartments();

    @GET("get_offers")
    Call<OfferResponseModel> getOffersForDepartment(@Query("category_id") String categoryId , @Query("user_id") String userId);

    @GET("get_offers")
    Call<OfferResponseModel> getOffers(@Query("user_id") String userId);

    @GET("get_offers")
    Call<OfferResponseModel> search(@QueryMap HashMap<String,String> hashMap);

    @GET("get_clinics")
    Call<ClinicsResponseModel> getClinics();

    @GET("get_offers")
    Call<OfferResponseModel> getFavorites(@Query("user_id") String userId , @Query("favourite") Boolean favourite);

    @GET("get_offers")
    Call<OfferResponseModel> getOffer(@Query("id") String offerId , @Query("user_id") String userId);

    @GET("add_favourite")
    Call<FavoriteResponseModel> addFavorite(@Query("id") String offerId , @Query("user_id") String userId);

    @GET("get_notifications")
    Call<NotificationResponseModel> getNotifications(@Query("user_id") String userId);

    @GET("send_feedback")
    Call<FeedbackResponse> sendFeedBack(@Query("name") String name, @Query("mobile") String mobile, @Query("message") String message);

    @GET("add_clinic")
    Call<FeedbackResponse> sendRequest(@Query("name") String name, @Query("mobile") String mobile, @Query("message") String message ,
                                        @Query("user_id") String userId);

    @GET("signup")
    Call<UserResponseModel> register(@Query("name") String name, @Query("password") String password , @Query("token") String token,
                                     @Query("username") String email , @Query("mobile") String mobile);

    @GET("signup")
    Call<UserResponseModel> register(@Query("name") String name, @Query("password") String password , @Query("token") String token,
                                     @Query("username") String email , @Query("mobile") String mobile, @Query("image") String image,
                                     @Query("social") Boolean social);

    @GET("signup")
    Call<UserResponseModel> registerWithImage(@Query("name") String name, @Query("password") String password , @Query("token") String token,
                                     @Query("username") String email , @Query("mobile") String mobile , @Query("image") String image);

    @GET("edit")
    Call<UserResponseModel> editProfileWithImage(@QueryMap HashMap<String,String> hashMap);

    @GET("edit")
    Call<UserResponseModel> editProfile(@QueryMap HashMap<String,String> hashMap);

    @GET("confirm_request")
    Call<QRCodeResponse> confirmRequest(@Query("id") String id, @Query("user_id") String user_id);

    @GET("add_review")
    Call<FeedbackResponse> addReview(@Query("id") String id, @Query("user_id") String user_id , @Query("rate") String rate ,
                                     @Query("review") String review);

    @GET("login")
    Call<UserResponseModel> login(@Query("password") String password,
                                     @Query("token") String token,
                                     @Query("mobile") String mobile);

    @GET("update_token")
    Call<FeedbackResponse> updateToken(@Query("id") String userId,
                                  @Query("token") String token);

    @GET("check_input")
    Call<UserResponseModel> checkInput(@Query("social") Boolean social , @Query("username") String email);

    @GET("get_user")
    Call<UserResponseModel> getUser(@Query("id") String userId);

    @GET("get_requests")
    Call<RequestsModelResponse> getRequests(@Query("user_id") String userId);

    @GET("add_request")
    Call<ReservationResponseModel> addRequest(@QueryMap HashMap<String,String> hashMap);

    @GET("settings")
    Call<InformationResponseModel> getInformation(@Query("type") String type);

    @GET("change_password")
    Call<FeedbackResponse> changePassword(@Query("mobile") String mobile , @Query("new_password") String password);

    @Multipart
    @POST("upload_image_api")
    Call<ImageResponse> uploadImage(@Part MultipartBody.Part image);
}
