package com.senoritasaudi.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.senoritasaudi.models.UserModel;
import com.senoritasaudi.models.responseModels.OfferResponseModel;
import com.senoritasaudi.models.responseModels.ReservationResponseModel;
import com.senoritasaudi.repositories.MainRepository;
import com.senoritasaudi.storeutils.StoreManager;

import java.util.HashMap;

public class ReservationViewModel extends ViewModel {

    private String offerId;
    private MainRepository mainRepository;
    private StoreManager storeManager;

    public ReservationViewModel(Application application,String offerId) {
        this.offerId = offerId;
        mainRepository = MainRepository.getInstance();
        storeManager = new StoreManager(application);
    }

    public LiveData<OfferResponseModel> getOffer() {
        if (storeManager.containsUser()) {
            return mainRepository.getOffer(offerId, storeManager.getUser().getId());
        } else {
            return mainRepository.getOffer(offerId, "0");
        }
    }

    public LiveData<ReservationResponseModel> addRequest(HashMap<String , String> hashMap) {
        return mainRepository.addRequest(hashMap);
    }

    public UserModel getUser() {
        return storeManager.getUser();
    }

    public boolean containsUser() {
        return storeManager.containsUser();
    }

}
