package com.senoritasaudi.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.senoritasaudi.models.FeedbackResponse;
import com.senoritasaudi.models.UserModel;
import com.senoritasaudi.models.responseModels.UserResponseModel;
import com.senoritasaudi.repositories.MainRepository;
import com.senoritasaudi.storeutils.Constants;
import com.senoritasaudi.storeutils.StoreManager;

public class SplashViewModel extends AndroidViewModel {

    private StoreManager mStoreManager;
    private MainRepository mainRepository;

    public SplashViewModel(@NonNull Application application) {
        super(application);
        mStoreManager = new StoreManager(application);
        mainRepository = MainRepository.getInstance();
    }

    public boolean showIntro() {
        return mStoreManager.getBooleanValueForKey(Constants.SharedPreferenceConstants.SHOW_INTRO,Constants.SharedPreferenceConstants.SHOW_INTRO_DEFAULT_VALUE);
    }



    public void setShowIntro(boolean showIntro) {
        mStoreManager.setBooleanValueForKey(Constants.SharedPreferenceConstants.SHOW_INTRO,showIntro);
    }

    public LiveData<FeedbackResponse> updateToken() {
        return mainRepository.updateToken(getUser().getId(),mStoreManager.getToken());
    }

    public LiveData<UserResponseModel> refreshUser(String id) {
        return mainRepository.getUser(id);
    }

    public void saveUser(UserModel userModel) {
        mStoreManager.saveUser(userModel);
    }

    public boolean containsUser() {
        return mStoreManager.containsUser();
    }

    public UserModel getUser() {
        return mStoreManager.getUser();
    }
}
