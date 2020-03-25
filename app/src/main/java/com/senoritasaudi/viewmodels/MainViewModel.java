package com.senoritasaudi.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.senoritasaudi.models.FeedbackResponse;
import com.senoritasaudi.models.UserModel;
import com.senoritasaudi.models.responseModels.ClinicsResponseModel;
import com.senoritasaudi.models.responseModels.DepartmentResponseModel;
import com.senoritasaudi.models.responseModels.OfferResponseModel;
import com.senoritasaudi.models.responseModels.QRCodeResponse;
import com.senoritasaudi.models.responseModels.RequestsModelResponse;
import com.senoritasaudi.models.responseModels.SliderResponseModel;
import com.senoritasaudi.repositories.MainRepository;
import com.senoritasaudi.storeutils.StoreManager;

public class MainViewModel extends AndroidViewModel {

    private MainRepository mainRepository;
    StoreManager storeManager;

    public MainViewModel(@NonNull Application application) {
        super(application);
        mainRepository = MainRepository.getInstance();
        storeManager = new StoreManager(application);
    }

    public LiveData<SliderResponseModel> getSliderResponseModelLiveData() {
        return mainRepository.getSliderImages();
    }

    public LiveData<ClinicsResponseModel> getClinics() {
        return mainRepository.getClinics();
    }

    public LiveData<RequestsModelResponse> getRequests() {
        return mainRepository.getRequests(storeManager.getUser().getId());
    }

    public LiveData<QRCodeResponse> confirmRequest(String id) {
        return mainRepository.confirmRequest(id,storeManager.getUser().getId());
    }

    public LiveData<FeedbackResponse> addReview(String id,String review,String rate) {
        return mainRepository.addReview(review,rate,getUser().getId(),id);
    }

    public LiveData<OfferResponseModel> getOfferResponseModelLiveData() {
        if (storeManager.containsUser()) {
            return mainRepository.getOffers(storeManager.getUser().getId());
        } else {
            return mainRepository.getOffers("0");
        }
    }

    public UserModel getUser() {
        return storeManager.getUser();
    }

    public boolean containsUser() {
        return storeManager.containsUser();
    }

    public LiveData<DepartmentResponseModel> getDepartmentResponseModelLiveData() {
        return mainRepository.getDepartments();
    }
}
