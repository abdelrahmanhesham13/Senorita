package com.senoritasaudi.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.senoritasaudi.models.FeedbackResponse;
import com.senoritasaudi.models.RequestModel;
import com.senoritasaudi.models.responseModels.ClinicsResponseModel;
import com.senoritasaudi.models.responseModels.DepartmentResponseModel;
import com.senoritasaudi.models.responseModels.ExchangeResponseModel;
import com.senoritasaudi.models.responseModels.FavoriteResponseModel;
import com.senoritasaudi.models.responseModels.ImageResponse;
import com.senoritasaudi.models.responseModels.InformationResponseModel;
import com.senoritasaudi.models.responseModels.NotificationResponseModel;
import com.senoritasaudi.models.responseModels.OfferResponseModel;
import com.senoritasaudi.models.responseModels.PointResponseModel;
import com.senoritasaudi.models.responseModels.QRCodeResponse;
import com.senoritasaudi.models.responseModels.RequestsModelResponse;
import com.senoritasaudi.models.responseModels.ReservationResponseModel;
import com.senoritasaudi.models.responseModels.ReviewResponseModel;
import com.senoritasaudi.models.responseModels.SliderResponseModel;
import com.senoritasaudi.models.responseModels.UserResponseModel;
import com.senoritasaudi.networkutils.ApiService;
import com.senoritasaudi.networkutils.RetrofitService;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainRepository {
    private static MainRepository mainRepository;
    private ApiService apiService;

    public static MainRepository getInstance() {
        if (mainRepository == null) {
            mainRepository = new MainRepository();
        }
        return mainRepository;
    }


    private MainRepository() {
        apiService = RetrofitService.getService();
    }


    public LiveData<SliderResponseModel> getSliderImages() {
        final MutableLiveData<SliderResponseModel> sliderResponseModelMutableLiveData = new MutableLiveData<>();
        apiService.getSliderImages().enqueue(new Callback<SliderResponseModel>() {
            @Override
            public void onResponse(@NotNull Call<SliderResponseModel> call, @NotNull Response<SliderResponseModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getStatus())
                        sliderResponseModelMutableLiveData.setValue(response.body());
                    else
                        sliderResponseModelMutableLiveData.setValue(null);
                } else {
                    sliderResponseModelMutableLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(@NotNull Call<SliderResponseModel> call, @NotNull Throwable t) {
                t.printStackTrace();
                sliderResponseModelMutableLiveData.setValue(null);
            }
        });
        return sliderResponseModelMutableLiveData;
    }

    public LiveData<ClinicsResponseModel> getClinics() {
        final MutableLiveData<ClinicsResponseModel> clinicsResponseModelMutableLiveData = new MutableLiveData<>();
        apiService.getClinics().enqueue(new Callback<ClinicsResponseModel>() {
            @Override
            public void onResponse(@NotNull Call<ClinicsResponseModel> call, @NotNull Response<ClinicsResponseModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    clinicsResponseModelMutableLiveData.setValue(response.body());
                } else {
                    clinicsResponseModelMutableLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(@NotNull Call<ClinicsResponseModel> call, @NotNull Throwable t) {
                t.printStackTrace();
                clinicsResponseModelMutableLiveData.setValue(null);
            }
        });
        return clinicsResponseModelMutableLiveData;
    }

    public LiveData<ClinicsResponseModel> getClinic(String clinicId) {
        final MutableLiveData<ClinicsResponseModel> clinicsResponseModelMutableLiveData = new MutableLiveData<>();
        apiService.getClinic(clinicId).enqueue(new Callback<ClinicsResponseModel>() {
            @Override
            public void onResponse(@NotNull Call<ClinicsResponseModel> call, @NotNull Response<ClinicsResponseModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    clinicsResponseModelMutableLiveData.setValue(response.body());
                } else {
                    clinicsResponseModelMutableLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(@NotNull Call<ClinicsResponseModel> call, @NotNull Throwable t) {
                t.printStackTrace();
                clinicsResponseModelMutableLiveData.setValue(null);
            }
        });
        return clinicsResponseModelMutableLiveData;
    }

    public LiveData<FeedbackResponse> changePassword(String mobile , String password) {
        final MutableLiveData<FeedbackResponse> sliderResponseModelMutableLiveData = new MutableLiveData<>();
        apiService.changePassword(mobile,password).enqueue(new Callback<FeedbackResponse>() {
            @Override
            public void onResponse(@NotNull Call<FeedbackResponse> call, @NotNull Response<FeedbackResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    sliderResponseModelMutableLiveData.setValue(response.body());
                } else {
                    sliderResponseModelMutableLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(@NotNull Call<FeedbackResponse> call, @NotNull Throwable t) {
                t.printStackTrace();
                sliderResponseModelMutableLiveData.setValue(null);
            }
        });
        return sliderResponseModelMutableLiveData;
    }

    public LiveData<FeedbackResponse> checkPromoCode(String offerId , String clinicId,String userId,String code) {
        final MutableLiveData<FeedbackResponse> sliderResponseModelMutableLiveData = new MutableLiveData<>();
        apiService.checkPromoCode(code,offerId,clinicId,userId).enqueue(new Callback<FeedbackResponse>() {
            @Override
            public void onResponse(@NotNull Call<FeedbackResponse> call, @NotNull Response<FeedbackResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    sliderResponseModelMutableLiveData.setValue(response.body());
                } else {
                    sliderResponseModelMutableLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(@NotNull Call<FeedbackResponse> call, @NotNull Throwable t) {
                t.printStackTrace();
                sliderResponseModelMutableLiveData.setValue(null);
            }
        });
        return sliderResponseModelMutableLiveData;
    }

    public LiveData<FeedbackResponse> updateToken(String userId , String token) {
        final MutableLiveData<FeedbackResponse> sliderResponseModelMutableLiveData = new MutableLiveData<>();
        apiService.updateToken(userId,token).enqueue(new Callback<FeedbackResponse>() {
            @Override
            public void onResponse(@NotNull Call<FeedbackResponse> call, @NotNull Response<FeedbackResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    sliderResponseModelMutableLiveData.setValue(response.body());
                } else {
                    sliderResponseModelMutableLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(@NotNull Call<FeedbackResponse> call, @NotNull Throwable t) {
                t.printStackTrace();
                sliderResponseModelMutableLiveData.setValue(null);
            }
        });
        return sliderResponseModelMutableLiveData;
    }

    public LiveData<ImageResponse> uploadImage(File imageFile) {
        final MutableLiveData<ImageResponse> imageResponseMutableLiveData = new MutableLiveData<>();
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("parameters[0]", imageFile.getName(), RequestBody.create(MediaType.parse("image/*"), imageFile));
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);
        builder.addPart(filePart);

        apiService.uploadImage(filePart).enqueue(new Callback<ImageResponse>() {
            @Override
            public void onResponse(@NotNull Call<ImageResponse> call, @NotNull Response<ImageResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    imageResponseMutableLiveData.setValue(response.body());
                } else {
                    imageResponseMutableLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(@NotNull Call<ImageResponse> call, @NotNull Throwable t) {
                t.printStackTrace();
                imageResponseMutableLiveData.setValue(null);
            }
        });
        return imageResponseMutableLiveData;
    }

    public LiveData<ReservationResponseModel> addRequest(HashMap<String, String> hashMap) {
        final MutableLiveData<ReservationResponseModel> reservationResponseModelMutableLiveData = new MutableLiveData<>();
        apiService.addRequest(hashMap).enqueue(new Callback<ReservationResponseModel>() {
            @Override
            public void onResponse(@NotNull Call<ReservationResponseModel> call, @NotNull Response<ReservationResponseModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    reservationResponseModelMutableLiveData.setValue(response.body());
                } else {
                    reservationResponseModelMutableLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(@NotNull Call<ReservationResponseModel> call, @NotNull Throwable t) {
                t.printStackTrace();
                reservationResponseModelMutableLiveData.setValue(null);
            }
        });
        return reservationResponseModelMutableLiveData;
    }

    public LiveData<UserResponseModel> register(String name, String token, String password, String email, String mobile) {
        final MutableLiveData<UserResponseModel> userResponseModelMutableLiveData = new MutableLiveData<>();
        apiService.register(name, password, token, email, mobile).enqueue(new Callback<UserResponseModel>() {
            @Override
            public void onResponse(@NotNull Call<UserResponseModel> call, @NotNull Response<UserResponseModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    userResponseModelMutableLiveData.setValue(response.body());
                } else {
                    userResponseModelMutableLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(@NotNull Call<UserResponseModel> call, @NotNull Throwable t) {
                t.printStackTrace();
                userResponseModelMutableLiveData.setValue(null);
            }
        });
        return userResponseModelMutableLiveData;
    }

    public LiveData<UserResponseModel> register(String name, String token, String password, String email, String mobile , String image) {
        final MutableLiveData<UserResponseModel> userResponseModelMutableLiveData = new MutableLiveData<>();
        apiService.register(name, password, token, email, mobile,image,true).enqueue(new Callback<UserResponseModel>() {
            @Override
            public void onResponse(@NotNull Call<UserResponseModel> call, @NotNull Response<UserResponseModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    userResponseModelMutableLiveData.setValue(response.body());
                } else {
                    userResponseModelMutableLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(@NotNull Call<UserResponseModel> call, @NotNull Throwable t) {
                t.printStackTrace();
                userResponseModelMutableLiveData.setValue(null);
            }
        });
        return userResponseModelMutableLiveData;
    }

    public LiveData<QRCodeResponse> confirmRequest(String id , String userId) {
        final MutableLiveData<QRCodeResponse> qrCodeResponseMutableLiveData = new MutableLiveData<>();
        apiService.confirmRequest(id,userId).enqueue(new Callback<QRCodeResponse>() {
            @Override
            public void onResponse(@NotNull Call<QRCodeResponse> call, @NotNull Response<QRCodeResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    qrCodeResponseMutableLiveData.setValue(response.body());
                } else {
                    qrCodeResponseMutableLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(@NotNull Call<QRCodeResponse> call, @NotNull Throwable t) {
                t.printStackTrace();
                qrCodeResponseMutableLiveData.setValue(null);
            }
        });
        return qrCodeResponseMutableLiveData;
    }

    public LiveData<UserResponseModel> editProfileWithImage(String name, String password, String email, String mobile, String image, String userId) {
        final MutableLiveData<UserResponseModel> userResponseModelMutableLiveData = new MutableLiveData<>();
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("name",name);
        hashMap.put("username",email);
        hashMap.put("mobile",mobile);
        hashMap.put("id",userId);
        hashMap.put("image",image);
        if (!password.isEmpty()) {
            hashMap.put("password",password);
        }
        apiService.editProfileWithImage(hashMap).enqueue(new Callback<UserResponseModel>() {
            @Override
            public void onResponse(@NotNull Call<UserResponseModel> call, @NotNull Response<UserResponseModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    userResponseModelMutableLiveData.setValue(response.body());
                } else {
                    userResponseModelMutableLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(@NotNull Call<UserResponseModel> call, @NotNull Throwable t) {
                t.printStackTrace();
                userResponseModelMutableLiveData.setValue(null);
            }
        });
        return userResponseModelMutableLiveData;
    }

    public LiveData<UserResponseModel> editProfile(String name, String password, String email, String mobile, String userId) {
        final MutableLiveData<UserResponseModel> userResponseModelMutableLiveData = new MutableLiveData<>();
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("name",name);
        hashMap.put("username",email);
        hashMap.put("mobile",mobile);
        hashMap.put("id",userId);
        if (!password.isEmpty()) {
            hashMap.put("password",password);
        }
        apiService.editProfile(hashMap).enqueue(new Callback<UserResponseModel>() {
            @Override
            public void onResponse(@NotNull Call<UserResponseModel> call, @NotNull Response<UserResponseModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    userResponseModelMutableLiveData.setValue(response.body());
                } else {
                    userResponseModelMutableLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(@NotNull Call<UserResponseModel> call, @NotNull Throwable t) {
                t.printStackTrace();
                userResponseModelMutableLiveData.setValue(null);
            }
        });
        return userResponseModelMutableLiveData;
    }

    public LiveData<UserResponseModel> login(String token, String password, String mobile) {
        final MutableLiveData<UserResponseModel> userResponseModelMutableLiveData = new MutableLiveData<>();
        apiService.login(password, token, mobile).enqueue(new Callback<UserResponseModel>() {
            @Override
            public void onResponse(@NotNull Call<UserResponseModel> call, @NotNull Response<UserResponseModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    userResponseModelMutableLiveData.setValue(response.body());
                } else {
                    userResponseModelMutableLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(@NotNull Call<UserResponseModel> call, @NotNull Throwable t) {
                t.printStackTrace();
                userResponseModelMutableLiveData.setValue(null);
            }
        });
        return userResponseModelMutableLiveData;
    }

    public LiveData<UserResponseModel> checkInput(String email) {
        final MutableLiveData<UserResponseModel> userResponseModelMutableLiveData = new MutableLiveData<>();
        apiService.checkInput(true,email).enqueue(new Callback<UserResponseModel>() {
            @Override
            public void onResponse(@NotNull Call<UserResponseModel> call, @NotNull Response<UserResponseModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    userResponseModelMutableLiveData.setValue(response.body());
                } else {
                    userResponseModelMutableLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(@NotNull Call<UserResponseModel> call, @NotNull Throwable t) {
                t.printStackTrace();
                userResponseModelMutableLiveData.setValue(null);
            }
        });
        return userResponseModelMutableLiveData;
    }

    public LiveData<UserResponseModel> getUser(String id) {
        final MutableLiveData<UserResponseModel> userResponseModelMutableLiveData = new MutableLiveData<>();
        apiService.getUser(id).enqueue(new Callback<UserResponseModel>() {
            @Override
            public void onResponse(@NotNull Call<UserResponseModel> call, @NotNull Response<UserResponseModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    userResponseModelMutableLiveData.setValue(response.body());
                } else {
                    userResponseModelMutableLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(@NotNull Call<UserResponseModel> call, @NotNull Throwable t) {
                t.printStackTrace();
                userResponseModelMutableLiveData.setValue(null);
            }
        });
        return userResponseModelMutableLiveData;
    }

    public LiveData<FeedbackResponse> sendFeedback(String name, String message, String mobile , String type) {
        final MutableLiveData<FeedbackResponse> feedbackResponseMutableLiveData = new MutableLiveData<>();
        apiService.sendFeedBack(name, mobile, message,type).enqueue(new Callback<FeedbackResponse>() {
            @Override
            public void onResponse(@NotNull Call<FeedbackResponse> call, @NotNull Response<FeedbackResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    feedbackResponseMutableLiveData.setValue(response.body());
                } else {
                    feedbackResponseMutableLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(@NotNull Call<FeedbackResponse> call, @NotNull Throwable t) {
                t.printStackTrace();
                feedbackResponseMutableLiveData.setValue(null);
            }
        });
        return feedbackResponseMutableLiveData;
    }

    public LiveData<FeedbackResponse> addReview(String review, String rate, String userId , String id) {
        final MutableLiveData<FeedbackResponse> feedbackResponseMutableLiveData = new MutableLiveData<>();
        apiService.addReview(id, userId, rate,review).enqueue(new Callback<FeedbackResponse>() {
            @Override
            public void onResponse(@NotNull Call<FeedbackResponse> call, @NotNull Response<FeedbackResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    feedbackResponseMutableLiveData.setValue(response.body());
                } else {
                    feedbackResponseMutableLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(@NotNull Call<FeedbackResponse> call, @NotNull Throwable t) {
                t.printStackTrace();
                feedbackResponseMutableLiveData.setValue(null);
            }
        });
        return feedbackResponseMutableLiveData;
    }

    public LiveData<FeedbackResponse> deleteRequest(String clinicId, String userId, String offerId , String requestId) {
        final MutableLiveData<FeedbackResponse> feedbackResponseMutableLiveData = new MutableLiveData<>();
        apiService.deleteRequest(requestId, clinicId, userId,offerId).enqueue(new Callback<FeedbackResponse>() {
            @Override
            public void onResponse(@NotNull Call<FeedbackResponse> call, @NotNull Response<FeedbackResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    feedbackResponseMutableLiveData.setValue(response.body());
                } else {
                    feedbackResponseMutableLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(@NotNull Call<FeedbackResponse> call, @NotNull Throwable t) {
                t.printStackTrace();
                feedbackResponseMutableLiveData.setValue(null);
            }
        });
        return feedbackResponseMutableLiveData;
    }

    public LiveData<InformationResponseModel> getInformation(String type) {
        final MutableLiveData<InformationResponseModel> informationResponseModelMutableLiveData = new MutableLiveData<>();
        apiService.getInformation(type).enqueue(new Callback<InformationResponseModel>() {
            @Override
            public void onResponse(@NotNull Call<InformationResponseModel> call, @NotNull Response<InformationResponseModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    informationResponseModelMutableLiveData.setValue(response.body());
                } else {
                    informationResponseModelMutableLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(@NotNull Call<InformationResponseModel> call, @NotNull Throwable t) {
                t.printStackTrace();
                informationResponseModelMutableLiveData.setValue(null);
            }
        });
        return informationResponseModelMutableLiveData;
    }

    public LiveData<FeedbackResponse> sendRequest(String name, String message, String mobile, String userId) {
        final MutableLiveData<FeedbackResponse> feedbackResponseMutableLiveData = new MutableLiveData<>();
        apiService.sendRequest(name, mobile, message, userId).enqueue(new Callback<FeedbackResponse>() {
            @Override
            public void onResponse(@NotNull Call<FeedbackResponse> call, @NotNull Response<FeedbackResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    feedbackResponseMutableLiveData.setValue(response.body());
                } else {
                    feedbackResponseMutableLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(@NotNull Call<FeedbackResponse> call, @NotNull Throwable t) {
                t.printStackTrace();
                feedbackResponseMutableLiveData.setValue(null);
            }
        });
        return feedbackResponseMutableLiveData;
    }

    public LiveData<NotificationResponseModel> getNotifications(String userId) {
        final MutableLiveData<NotificationResponseModel> notificationResponseModelMutableLiveData = new MutableLiveData<>();
        apiService.getNotifications(userId).enqueue(new Callback<NotificationResponseModel>() {
            @Override
            public void onResponse(@NotNull Call<NotificationResponseModel> call, @NotNull Response<NotificationResponseModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getStatus())
                        notificationResponseModelMutableLiveData.setValue(response.body());
                    else
                        notificationResponseModelMutableLiveData.setValue(null);
                } else {
                    notificationResponseModelMutableLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(@NotNull Call<NotificationResponseModel> call, @NotNull Throwable t) {
                t.printStackTrace();
                notificationResponseModelMutableLiveData.setValue(null);
            }
        });
        return notificationResponseModelMutableLiveData;
    }

    public LiveData<RequestsModelResponse> getRequests(String userId) {
        final MutableLiveData<RequestsModelResponse> requestsModelResponseMutableLiveData = new MutableLiveData<>();
        apiService.getRequests(userId).enqueue(new Callback<RequestsModelResponse>() {
            @Override
            public void onResponse(@NotNull Call<RequestsModelResponse> call, @NotNull Response<RequestsModelResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getStatus())
                        requestsModelResponseMutableLiveData.setValue(response.body());
                    else
                        requestsModelResponseMutableLiveData.setValue(null);
                } else {
                    requestsModelResponseMutableLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(@NotNull Call<RequestsModelResponse> call, @NotNull Throwable t) {
                t.printStackTrace();
                requestsModelResponseMutableLiveData.setValue(null);
            }
        });
        return requestsModelResponseMutableLiveData;
    }

    public LiveData<PointResponseModel> getPoints() {
        final MutableLiveData<PointResponseModel> pointResponseModelMutableLiveData = new MutableLiveData<>();
        apiService.getPoints().enqueue(new Callback<PointResponseModel>() {
            @Override
            public void onResponse(@NotNull Call<PointResponseModel> call, @NotNull Response<PointResponseModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getStatus())
                        pointResponseModelMutableLiveData.setValue(response.body());
                    else
                        pointResponseModelMutableLiveData.setValue(null);
                } else {
                    pointResponseModelMutableLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(@NotNull Call<PointResponseModel> call, @NotNull Throwable t) {
                t.printStackTrace();
                pointResponseModelMutableLiveData.setValue(null);
            }
        });
        return pointResponseModelMutableLiveData;
    }

    public LiveData<OfferResponseModel> search(HashMap<String,String> hashMap) {
        final MutableLiveData<OfferResponseModel> offerResponseModelMutableLiveData = new MutableLiveData<>();
        apiService.search(hashMap).enqueue(new Callback<OfferResponseModel>() {
                @Override
                public void onResponse(@NotNull Call<OfferResponseModel> call, @NotNull Response<OfferResponseModel> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        offerResponseModelMutableLiveData.setValue(response.body());
                    } else {
                        offerResponseModelMutableLiveData.setValue(null);
                    }
                }

                @Override
                public void onFailure(@NotNull Call<OfferResponseModel> call, @NotNull Throwable t) {
                    t.printStackTrace();
                    offerResponseModelMutableLiveData.setValue(null);
                }
        });
        return offerResponseModelMutableLiveData;
    }

    public LiveData<OfferResponseModel> getOffers(String departmentId, String userId) {
        final MutableLiveData<OfferResponseModel> offerResponseModelMutableLiveData = new MutableLiveData<>();
        if (departmentId.equals("0")) {
            apiService.getOffers(userId).enqueue(new Callback<OfferResponseModel>() {
                @Override
                public void onResponse(@NotNull Call<OfferResponseModel> call, @NotNull Response<OfferResponseModel> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        offerResponseModelMutableLiveData.setValue(response.body());
                    } else {
                        offerResponseModelMutableLiveData.setValue(null);
                    }
                }

                @Override
                public void onFailure(@NotNull Call<OfferResponseModel> call, @NotNull Throwable t) {
                    t.printStackTrace();
                    offerResponseModelMutableLiveData.setValue(null);
                }
            });
        } else {
            apiService.getOffersForDepartment(departmentId, userId).enqueue(new Callback<OfferResponseModel>() {
                @Override
                public void onResponse(@NotNull Call<OfferResponseModel> call, @NotNull Response<OfferResponseModel> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        offerResponseModelMutableLiveData.setValue(response.body());
                    } else {
                        offerResponseModelMutableLiveData.setValue(null);
                    }
                }

                @Override
                public void onFailure(@NotNull Call<OfferResponseModel> call, @NotNull Throwable t) {
                    t.printStackTrace();
                    offerResponseModelMutableLiveData.setValue(null);
                }
            });
        }
        return offerResponseModelMutableLiveData;
    }

    public LiveData<OfferResponseModel> getOffer(String offerId, String userId) {
        final MutableLiveData<OfferResponseModel> offerResponseModelMutableLiveData = new MutableLiveData<>();
        apiService.getOffer(offerId, userId).enqueue(new Callback<OfferResponseModel>() {
            @Override
            public void onResponse(@NotNull Call<OfferResponseModel> call, @NotNull Response<OfferResponseModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getStatus())
                        offerResponseModelMutableLiveData.setValue(response.body());
                    else
                        offerResponseModelMutableLiveData.setValue(null);
                } else {
                    offerResponseModelMutableLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(@NotNull Call<OfferResponseModel> call, @NotNull Throwable t) {
                t.printStackTrace();
                offerResponseModelMutableLiveData.setValue(null);
            }
        });

        return offerResponseModelMutableLiveData;
    }

    public LiveData<OfferResponseModel> getOffers(String userId) {
        final MutableLiveData<OfferResponseModel> offerResponseModelMutableLiveData = new MutableLiveData<>();
        apiService.getOffers(userId).enqueue(new Callback<OfferResponseModel>() {
            @Override
            public void onResponse(@NotNull Call<OfferResponseModel> call, @NotNull Response<OfferResponseModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getStatus())
                        offerResponseModelMutableLiveData.setValue(response.body());
                    else
                        offerResponseModelMutableLiveData.setValue(null);
                } else {
                    offerResponseModelMutableLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(@NotNull Call<OfferResponseModel> call, @NotNull Throwable t) {
                t.printStackTrace();
                offerResponseModelMutableLiveData.setValue(null);
            }
        });
        return offerResponseModelMutableLiveData;
    }

    public LiveData<OfferResponseModel> getFavorites(String userId) {
        final MutableLiveData<OfferResponseModel> offerResponseModelMutableLiveData = new MutableLiveData<>();
        apiService.getFavorites(userId, true).enqueue(new Callback<OfferResponseModel>() {
            @Override
            public void onResponse(@NotNull Call<OfferResponseModel> call, @NotNull Response<OfferResponseModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    offerResponseModelMutableLiveData.setValue(response.body());
                } else {
                    offerResponseModelMutableLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(@NotNull Call<OfferResponseModel> call, @NotNull Throwable t) {
                t.printStackTrace();
                offerResponseModelMutableLiveData.setValue(null);
            }
        });
        return offerResponseModelMutableLiveData;
    }

    public LiveData<FavoriteResponseModel> addFavorite(String userId, String offerId) {
        final MutableLiveData<FavoriteResponseModel> favoriteResponseModelMutableLiveData = new MutableLiveData<>();
        apiService.addFavorite(offerId, userId).enqueue(new Callback<FavoriteResponseModel>() {
            @Override
            public void onResponse(@NotNull Call<FavoriteResponseModel> call, @NotNull Response<FavoriteResponseModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    favoriteResponseModelMutableLiveData.setValue(response.body());
                } else {
                    favoriteResponseModelMutableLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(@NotNull Call<FavoriteResponseModel> call, @NotNull Throwable t) {
                t.printStackTrace();
                favoriteResponseModelMutableLiveData.setValue(null);
            }
        });
        return favoriteResponseModelMutableLiveData;
    }

    public LiveData<DepartmentResponseModel> getDepartments() {
        final MutableLiveData<DepartmentResponseModel> departmentResponseModelMutableLiveData = new MutableLiveData<>();
        apiService.getDepartments().enqueue(new Callback<DepartmentResponseModel>() {
            @Override
            public void onResponse(@NotNull Call<DepartmentResponseModel> call, @NotNull Response<DepartmentResponseModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getStatus())
                        departmentResponseModelMutableLiveData.setValue(response.body());
                    else
                        departmentResponseModelMutableLiveData.setValue(null);
                } else {
                    departmentResponseModelMutableLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(@NotNull Call<DepartmentResponseModel> call, @NotNull Throwable t) {
                t.printStackTrace();
                departmentResponseModelMutableLiveData.setValue(null);
            }
        });
        return departmentResponseModelMutableLiveData;
    }


    public LiveData<ReviewResponseModel> getReviews(String clinicId) {
        final MutableLiveData<ReviewResponseModel> reviewResponseModelMutableLiveData = new MutableLiveData<>();
        apiService.getReview(clinicId).enqueue(new Callback<ReviewResponseModel>() {
            @Override
            public void onResponse(@NotNull Call<ReviewResponseModel> call, @NotNull Response<ReviewResponseModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getStatus())
                        reviewResponseModelMutableLiveData.setValue(response.body());
                    else
                        reviewResponseModelMutableLiveData.setValue(null);
                } else {
                    reviewResponseModelMutableLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(@NotNull Call<ReviewResponseModel> call, @NotNull Throwable t) {
                t.printStackTrace();
                reviewResponseModelMutableLiveData.setValue(null);
            }
        });
        return reviewResponseModelMutableLiveData;
    }

    public LiveData<ExchangeResponseModel> addPoints(String id, String pointId, String percent) {
        final MutableLiveData<ExchangeResponseModel> exchangeResponseModelMutableLiveData = new MutableLiveData<>();
        apiService.addPoints(id,pointId,percent).enqueue(new Callback<ExchangeResponseModel>() {
            @Override
            public void onResponse(@NotNull Call<ExchangeResponseModel> call, @NotNull Response<ExchangeResponseModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getStatus())
                        exchangeResponseModelMutableLiveData.setValue(response.body());
                    else
                        exchangeResponseModelMutableLiveData.setValue(null);
                } else {
                    exchangeResponseModelMutableLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(@NotNull Call<ExchangeResponseModel> call, @NotNull Throwable t) {
                t.printStackTrace();
                exchangeResponseModelMutableLiveData.setValue(null);
            }
        });
        return exchangeResponseModelMutableLiveData;
    }

    public LiveData<ExchangeResponseModel> updatePoints(String id) {
        final MutableLiveData<ExchangeResponseModel> exchangeResponseModelMutableLiveData = new MutableLiveData<>();
        apiService.updatePoints(id).enqueue(new Callback<ExchangeResponseModel>() {
            @Override
            public void onResponse(@NotNull Call<ExchangeResponseModel> call, @NotNull Response<ExchangeResponseModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getStatus())
                        exchangeResponseModelMutableLiveData.setValue(response.body());
                    else
                        exchangeResponseModelMutableLiveData.setValue(null);
                } else {
                    exchangeResponseModelMutableLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(@NotNull Call<ExchangeResponseModel> call, @NotNull Throwable t) {
                t.printStackTrace();
                exchangeResponseModelMutableLiveData.setValue(null);
            }
        });
        return exchangeResponseModelMutableLiveData;
    }
}
