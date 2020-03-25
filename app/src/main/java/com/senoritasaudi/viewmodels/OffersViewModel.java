package com.senoritasaudi.viewmodels;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.senoritasaudi.models.responseModels.FavoriteResponseModel;
import com.senoritasaudi.models.responseModels.OfferResponseModel;
import com.senoritasaudi.repositories.MainRepository;
import com.senoritasaudi.storeutils.StoreManager;

import java.util.HashMap;

public class OffersViewModel extends ViewModel {

    private String departmentId;
    private StoreManager storeManager;
    private MainRepository mainRepository;

    public OffersViewModel(Application application, String departmentId) {
        this.departmentId = departmentId;
        mainRepository = MainRepository.getInstance();
        storeManager = new StoreManager(application);

    }

    public LiveData<OfferResponseModel> getOfferResponseModelLiveData() {
        if (storeManager.containsUser()) {
            return mainRepository.getOffers(departmentId,storeManager.getUser().getId());
        } else {
            return mainRepository.getOffers(departmentId,"0");
        }
    }

    public LiveData<OfferResponseModel> search(HashMap<String,String> hashMap) {
        if (storeManager.containsUser()) {
            hashMap.put("user_id",storeManager.getUser().getId());
        }
        return mainRepository.search(hashMap);
    }

    public LiveData<OfferResponseModel> getFavorites() {
        return mainRepository.getFavorites(storeManager.getUser().getId());
    }

    public boolean containsUser() {
        return storeManager.containsUser();
    }

    public LiveData<FavoriteResponseModel> addFavorite(String offerId) {
        return mainRepository.addFavorite(storeManager.getUser().getId(),offerId);
    }
}
