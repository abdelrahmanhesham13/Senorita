package com.senoritasaudi.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.senoritasaudi.R;
import com.senoritasaudi.databinding.ActivityInformationBinding;
import com.senoritasaudi.models.responseModels.InformationResponseModel;
import com.senoritasaudi.viewmodels.InformationViewModel;
import com.senoritasaudi.viewmodels.factory.ViewModelFactory;
import com.senoritasaudi.views.baseviews.BaseActivityWithViewModel;
import com.senoritasaudi.views.baseviews.BaseActivityWithoutViewModel;

public class InformationActivity extends BaseActivityWithViewModel<InformationViewModel,ActivityInformationBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getIntent().getStringExtra("title"));
        getActivityBinding().textView4.setText(getIntent().getStringExtra("title"));
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getActivityViewModel().getInformation().observe(this, new Observer<InformationResponseModel>() {
            @Override
            public void onChanged(InformationResponseModel informationResponseModel) {
                getActivityBinding().progressBar.setVisibility(View.GONE);
                getActivityBinding().registerNow.setText(informationResponseModel.getData());
            }
        });
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_information;
    }

    @Override
    protected InformationViewModel initialiseViewModel() {
        ViewModelFactory viewModelFactory = new ViewModelFactory(getApplication(),"");
        viewModelFactory.setPage(getIntent().getStringExtra("title_for_api"));
        return new ViewModelProvider(this,viewModelFactory).get(InformationViewModel.class);
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
