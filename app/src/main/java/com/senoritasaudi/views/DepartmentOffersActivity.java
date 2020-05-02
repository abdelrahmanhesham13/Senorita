package com.senoritasaudi.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.senoritasaudi.R;
import com.senoritasaudi.adapters.DepartmentOffersAdapter;
import com.senoritasaudi.databinding.ActivityDepartmentOffersBinding;
import com.senoritasaudi.events.OnItemClicked;
import com.senoritasaudi.models.OfferModel;
import com.senoritasaudi.models.UserModel;
import com.senoritasaudi.models.responseModels.ExchangeResponseModel;
import com.senoritasaudi.models.responseModels.FavoriteResponseModel;
import com.senoritasaudi.models.responseModels.OfferResponseModel;
import com.senoritasaudi.storeutils.StoreManager;
import com.senoritasaudi.viewmodels.MainViewModel;
import com.senoritasaudi.viewmodels.OffersViewModel;
import com.senoritasaudi.viewmodels.factory.ViewModelFactory;
import com.senoritasaudi.views.baseviews.BaseActivityWithViewModel;

import java.util.ArrayList;
import java.util.HashMap;

public class DepartmentOffersActivity extends BaseActivityWithViewModel<OffersViewModel, ActivityDepartmentOffersBinding> implements OnItemClicked {

    int departmentId = 0;
    DepartmentOffersAdapter departmentOffersAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getString(R.string.offers));
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setupViews();
    }

    private void setupViews() {
        getActivityBinding().departmentOffersRecycler.setHasFixedSize(true);
        getActivityBinding().departmentOffersRecycler.setLayoutManager(new LinearLayoutManager(this));

        departmentOffersAdapter = new DepartmentOffersAdapter(this, this, new OnItemClicked() {
            @Override
            public void onItemClicked(int position) {
                if (getActivityViewModel().containsUser()) {
                    getActivityViewModel().addFavorite(departmentOffersAdapter.getOfferIdForPosition(position)).observe(DepartmentOffersActivity.this, new Observer<FavoriteResponseModel>() {
                        @Override
                        public void onChanged(FavoriteResponseModel favoriteResponseModel) {
                            if (favoriteResponseModel != null && favoriteResponseModel.getStatus()) {
                                Toast.makeText(DepartmentOffersActivity.this, favoriteResponseModel.getMessage(), Toast.LENGTH_SHORT).show();
                            } else if (favoriteResponseModel != null) {

                                Toast.makeText(DepartmentOffersActivity.this, favoriteResponseModel.getMessage(), Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(DepartmentOffersActivity.this, getString(R.string.error), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(DepartmentOffersActivity.this, getString(R.string.please_login), Toast.LENGTH_SHORT).show();
                }
            }
        }, new DepartmentOffersAdapter.OnShareClick() {
            @Override
            public void onShareClicked() {
                getActivityViewModel().updatePoints().observe(DepartmentOffersActivity.this, new Observer<ExchangeResponseModel>() {
                    @Override
                    public void onChanged(ExchangeResponseModel exchangeResponseModel) {
                        if (exchangeResponseModel != null) {
                            if (StoreManager.getAppLanguage(DepartmentOffersActivity.this).equals("ar")) {
                                Toast.makeText(DepartmentOffersActivity.this, exchangeResponseModel.getMessageAr(), Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(DepartmentOffersActivity.this, exchangeResponseModel.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                            UserModel userModel = getActivityViewModel().getUser();
                            userModel.setPoints(String.valueOf(exchangeResponseModel.getPoints()));
                            getActivityViewModel().saveUser(userModel);
                        }
                    }
                });
            }
        });
        getActivityBinding().departmentOffersRecycler.setAdapter(departmentOffersAdapter);
        if (!getIntent().hasExtra("type")) {
            getActivityViewModel().getOfferResponseModelLiveData().observe(this, new Observer<OfferResponseModel>() {
                @Override
                public void onChanged(OfferResponseModel offerResponseModel) {
                    getActivityBinding().progressBar.setVisibility(View.GONE);
                    if (offerResponseModel != null) {
                        if (!offerResponseModel.getStatus()) {
                            Toast.makeText(DepartmentOffersActivity.this, R.string.no_offers, Toast.LENGTH_LONG).show();
                        } else {
                            departmentOffersAdapter.addOffers((ArrayList<OfferModel>) offerResponseModel.getOffers());
                        }
                    }
                }
            });
        } else {
            String departmentId = getIntent().getStringExtra("departmentId");
            String clinicId = getIntent().getStringExtra("clinicId");
            String offerId = getIntent().getStringExtra("offerId");
            HashMap<String,String> hashMap = new HashMap<>();
            if (!departmentId.equals("0")) {
                hashMap.put("category_id",departmentId);
            }
            if (!offerId.isEmpty()) {
                hashMap.put("id",offerId);
            }
            if (!clinicId.equals("0")) {
                hashMap.put("clinic_id",clinicId);
            }
            getActivityViewModel().search(hashMap).observe(DepartmentOffersActivity.this, new Observer<OfferResponseModel>() {
                @Override
                public void onChanged(OfferResponseModel offerResponseModel) {
                    getActivityBinding().progressBar.setVisibility(View.GONE);
                    if (offerResponseModel != null) {
                        if (!offerResponseModel.getStatus()) {
                            Toast.makeText(DepartmentOffersActivity.this, R.string.no_offers, Toast.LENGTH_LONG).show();
                        } else {
                            departmentOffersAdapter.addOffers((ArrayList<OfferModel>) offerResponseModel.getOffers());
                        }
                    }
                }
            });
        }
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_department_offers;
    }

    @Override
    protected OffersViewModel initialiseViewModel() {
        departmentId = getIntent().getIntExtra("departmentId",0);
        return new ViewModelProvider(this,new ViewModelFactory(getApplication(),String.valueOf(departmentId))).get(OffersViewModel.class);
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
    public void onItemClicked(int position) {
        Intent intent = new Intent(this,ReservationActivity.class);
        intent.putExtra("offerId",departmentOffersAdapter.getOfferIdForPosition(position));
        startActivity(intent);
    }
}
