package com.senoritasaudi.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.senoritasaudi.models.UserModel;
import com.senoritasaudi.models.responseModels.UserResponseModel;
import com.senoritasaudi.repositories.MainRepository;
import com.senoritasaudi.storeutils.StoreManager;

public class RegistrationViewModel extends AndroidViewModel {

    MainRepository mainRepository;
    StoreManager mStoreManager;

    public RegistrationViewModel(@NonNull Application application) {
        super(application);
        mainRepository = MainRepository.getInstance();
        mStoreManager = new StoreManager(application);
    }

    public LiveData<UserResponseModel> register(String name , String password , String token , String email , String mobile) {
        return mainRepository.register(name,token,password,email,mobile);
    }

    public String getToken() {
        return mStoreManager.getToken();
    }

    public void saveUser(UserModel userModel) {
        mStoreManager.saveUser(userModel);
    }

}
