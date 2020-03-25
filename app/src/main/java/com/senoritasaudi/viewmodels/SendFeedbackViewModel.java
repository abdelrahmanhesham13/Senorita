package com.senoritasaudi.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.senoritasaudi.models.FeedbackResponse;
import com.senoritasaudi.repositories.MainRepository;
import com.senoritasaudi.storeutils.StoreManager;

public class SendFeedbackViewModel extends AndroidViewModel {

    MainRepository mainRepository;
    StoreManager storeManager;

    public SendFeedbackViewModel(@NonNull Application application) {
        super(application);
        mainRepository = MainRepository.getInstance();
        storeManager = new StoreManager(application);
    }

    public LiveData<FeedbackResponse> sendFeedBack(String name , String phone , String message) {
        return mainRepository.sendFeedback(name,phone,message);
    }

    public LiveData<FeedbackResponse> sendRequest(String name , String phone , String message) {
        return mainRepository.sendRequest(name,message,phone,storeManager.getUser().getId());
    }

}
