package com.senoritasaudi.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.senoritasaudi.R;
import com.senoritasaudi.databinding.ActivitySplashBinding;
import com.senoritasaudi.models.FeedbackResponse;
import com.senoritasaudi.models.responseModels.UserResponseModel;
import com.senoritasaudi.navutils.NavigationManager;
import com.senoritasaudi.viewmodels.SplashViewModel;
import com.senoritasaudi.views.baseviews.BaseActivityWithViewModel;
import com.senoritasaudi.views.baseviews.BaseActivityWithoutViewModel;

public class SplashActivity extends BaseActivityWithViewModel<SplashViewModel,ActivitySplashBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getActivityViewModel().showIntro()) {
            NavigationManager.startActivity(this,IntroActivity.class);
            getActivityViewModel().setShowIntro(false);
            finish();
        } else if (getActivityViewModel().containsUser()) {
            refreshUser();
        } else {
            NavigationManager.startActivity(this, LoginActivity.class);
            finish();
        }
    }

    private void refreshUser() {
        getActivityViewModel().refreshUser(getActivityViewModel().getUser().getId()).observe(this, new Observer<UserResponseModel>() {
            @Override
            public void onChanged(UserResponseModel userResponseModel) {
                getActivityViewModel().saveUser(userResponseModel.getUser());
                getActivityViewModel().updateToken().observe(SplashActivity.this, new Observer<FeedbackResponse>() {
                    @Override
                    public void onChanged(FeedbackResponse feedbackResponse) {
                        NavigationManager.startActivity(SplashActivity.this, MainActivity.class);
                        finish();

                    }
                });
            }
        });
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_splash;
    }

    @Override
    protected SplashViewModel initialiseViewModel() {
        return new ViewModelProvider(this).get(SplashViewModel.class);
    }
}
