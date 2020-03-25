package com.senoritasaudi.viewmodels.factory;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.senoritasaudi.viewmodels.InformationViewModel;
import com.senoritasaudi.viewmodels.NotificationsViewModel;
import com.senoritasaudi.viewmodels.OffersViewModel;
import com.senoritasaudi.viewmodels.ReservationViewModel;

public class ViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private Application application;
    String departmentId;
    String userId;
    String offerId;
    String page;

    public ViewModelFactory(Application application, String departmentId) {
        this.departmentId = departmentId;
        this.application = application;
    }

    public ViewModelFactory() {
    }

    public void setPage(String page) {
        this.page = page;
    }

    public void setOfferId(String offerId) {
        this.offerId = offerId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.getSimpleName().equals("OffersViewModel")) {
            return (T) new OffersViewModel(application,departmentId);
        } else if (modelClass.getSimpleName().equals("ReservationViewModel")) {
            return (T) new ReservationViewModel(application,offerId);
        } else {
            return (T) new InformationViewModel(page);
        }
    }
}
