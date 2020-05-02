package com.senoritasaudi.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.senoritasaudi.R;
import com.senoritasaudi.adapters.NotificationsAdapter;
import com.senoritasaudi.adapters.PointsAdapter;
import com.senoritasaudi.databinding.ActivityPointsBinding;
import com.senoritasaudi.models.PointModel;
import com.senoritasaudi.models.UserModel;
import com.senoritasaudi.models.responseModels.ExchangeResponseModel;
import com.senoritasaudi.models.responseModels.PointResponseModel;
import com.senoritasaudi.storeutils.StoreManager;
import com.senoritasaudi.viewmodels.MainViewModel;
import com.senoritasaudi.views.baseviews.BaseActivityWithViewModel;

import java.util.ArrayList;

public class PointsActivity extends BaseActivityWithViewModel<MainViewModel, ActivityPointsBinding> {

    PointsAdapter pointsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getString(R.string.points));
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        pointsAdapter = new PointsAdapter(this, new PointsAdapter.OnExchangeClick() {
            @Override
            public void onExchangeClicked(PointModel pointModel) {
                getActivityViewModel().addPoints(pointModel.getId(),pointModel.getPercent()).observe(PointsActivity.this, new Observer<ExchangeResponseModel>() {
                    @Override
                    public void onChanged(ExchangeResponseModel exchangeResponseModel) {
                        if (exchangeResponseModel != null) {
                            if (StoreManager.getAppLanguage(PointsActivity.this).equals("ar")) {
                                Toast.makeText(PointsActivity.this,exchangeResponseModel.getMessageAr(),Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(PointsActivity.this,exchangeResponseModel.getMessage(),Toast.LENGTH_LONG).show();
                            }
                            UserModel userModel = getActivityViewModel().getUser();
                            userModel.setPoints(String.valueOf(exchangeResponseModel.getPoints()));
                            getActivityViewModel().saveUser(userModel);
                        }
                    }
                });
            }
        });
        getActivityBinding().pointsRecycler.setHasFixedSize(true);
        getActivityBinding().pointsRecycler.setLayoutManager(new GridLayoutManager(this,2));
        getActivityBinding().pointsRecycler.setAdapter(pointsAdapter);
        getActivityViewModel().getPoints().observe(this, new Observer<PointResponseModel>() {
            @Override
            public void onChanged(PointResponseModel pointResponseModel) {
                getActivityBinding().progressParent.setVisibility(View.GONE);
                if (pointResponseModel != null) {
                    pointsAdapter.addPoints((ArrayList<PointModel>) pointResponseModel.getPoints());
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return false;
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_points;
    }

    @Override
    protected MainViewModel initialiseViewModel() {
        return new ViewModelProvider(this).get(MainViewModel.class);
    }
}
