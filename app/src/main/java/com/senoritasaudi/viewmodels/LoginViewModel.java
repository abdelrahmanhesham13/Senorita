package com.senoritasaudi.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.senoritasaudi.models.FeedbackResponse;
import com.senoritasaudi.models.UserModel;
import com.senoritasaudi.models.responseModels.ImageResponse;
import com.senoritasaudi.models.responseModels.UserResponseModel;
import com.senoritasaudi.repositories.MainRepository;
import com.senoritasaudi.storeutils.StoreManager;

import java.io.File;

public class LoginViewModel extends AndroidViewModel {

    MainRepository mainRepository;
    StoreManager storeManager;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        mainRepository = MainRepository.getInstance();
        storeManager = new StoreManager(application);
    }

    public LiveData<UserResponseModel> login(String phone , String password , String token) {
        return mainRepository.login(token,password,phone);
    }

    public LiveData<FeedbackResponse> updateToken() {
        return mainRepository.updateToken(getUser().getId(),storeManager.getToken());
    }

    public LiveData<FeedbackResponse> changePassword(String mobile , String password) {
        return mainRepository.changePassword(mobile,password);
    }

    public LiveData<ImageResponse> uploadImage(File imageFile) {
        return mainRepository.uploadImage(imageFile);
    }

    public LiveData<UserResponseModel> register(String name , String password , String token , String email , String mobile , String image) {
        return mainRepository.register(name,token,password,email,mobile,image);
    }

    public LiveData<UserResponseModel> editProfileWithImage(String name , String email , String phone , String password , String image) {
        return mainRepository.editProfileWithImage(name,password,email,phone,image,getUser().getId());
    }

    public LiveData<UserResponseModel> editProfile(String name , String email , String phone , String password) {
        return mainRepository.editProfile(name,password,email,phone,getUser().getId());
    }

    public LiveData<UserResponseModel> checkInput(String email) {
        return mainRepository.checkInput(email);
    }

    public void saveUser(UserModel userModel) {
        storeManager.saveUser(userModel);
    }

    public void removeUser() {
        storeManager.removeUser();
    }

    public UserModel getUser() {
        return storeManager.getUser();
    }

    public String getToken() {
        return storeManager.getToken();
    }

    public boolean containsUser() {
        return storeManager.containsUser();
    }
}
