package com.senoritasaudi.views.baseviews;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import java.util.Locale;

public abstract class BaseActivityWithoutViewModel<D extends ViewDataBinding> extends AppCompatActivity {

    protected D mActivityBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        ViewCompat.setLayoutDirection(getWindow().getDecorView(),ViewCompat.LAYOUT_DIRECTION_RTL);
        super.onCreate(savedInstanceState);
        mActivityBinding = DataBindingUtil.setContentView(this,getLayoutResourceId());
    }

    protected abstract int getLayoutResourceId();

    public D getActivityBinding() {
        return mActivityBinding;
    }
}
