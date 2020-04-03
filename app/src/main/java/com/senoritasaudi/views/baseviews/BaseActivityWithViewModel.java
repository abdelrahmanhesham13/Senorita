package com.senoritasaudi.views.baseviews;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModel;

import java.util.Locale;

public abstract class BaseActivityWithViewModel<V extends ViewModel , D extends ViewDataBinding> extends AppCompatActivity {

    protected D mActivityBinding;
    protected V mActivityViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        ViewCompat.setLayoutDirection(getWindow().getDecorView(),ViewCompat.LAYOUT_DIRECTION_RTL);
        super.onCreate(savedInstanceState);
        mActivityBinding = DataBindingUtil.setContentView(this,getLayoutResourceId());
        mActivityViewModel = initialiseViewModel();
    }

    protected V getActivityViewModel() {
        return mActivityViewModel;
    }

    public D getActivityBinding() {
        return mActivityBinding;
    }

    protected abstract int getLayoutResourceId();

    protected abstract V initialiseViewModel();

}
