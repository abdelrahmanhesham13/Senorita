package com.senoritasaudi.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.MenuItem;

import com.senoritasaudi.R;
import com.senoritasaudi.databinding.ActivityNotificationsBinding;
import com.senoritasaudi.viewmodels.MainViewModel;
import com.senoritasaudi.views.baseviews.BaseActivityWithViewModel;
import com.senoritasaudi.views.baseviews.BaseActivityWithoutViewModel;

public class NotificationsActivity extends BaseActivityWithViewModel<MainViewModel,ActivityNotificationsBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getString(R.string.notifications));
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_notifications;
    }

    @Override
    protected MainViewModel initialiseViewModel() {
        return new ViewModelProvider(this).get(MainViewModel.class);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return false;
    }
}
