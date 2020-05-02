package com.senoritasaudi.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.bumptech.glide.Glide;
import com.senoritasaudi.R;
import com.senoritasaudi.adapters.NotificationsAdapter;
import com.senoritasaudi.adapters.ReviewsAdapter;
import com.senoritasaudi.databinding.ActivityClinicDetailsBinding;
import com.senoritasaudi.models.ReviewModel;
import com.senoritasaudi.models.responseModels.ClinicsResponseModel;
import com.senoritasaudi.models.responseModels.ReviewResponseModel;
import com.senoritasaudi.storeutils.StoreManager;
import com.senoritasaudi.viewmodels.MainViewModel;
import com.senoritasaudi.views.baseviews.BaseActivityWithViewModel;

import java.util.ArrayList;

public class ClinicDetailsActivity extends BaseActivityWithViewModel<MainViewModel, ActivityClinicDetailsBinding> {

    String clinicId;
    ReviewsAdapter reviewsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        clinicId = getIntent().getStringExtra("clinicId");
        reviewsAdapter = new ReviewsAdapter(this);
        getActivityBinding().reviews.setHasFixedSize(true);
        getActivityBinding().reviews.setLayoutManager(new LinearLayoutManager(this));
        getActivityBinding().reviews.setAdapter(reviewsAdapter);
        getActivityViewModel().getClinic(clinicId).observe(this, new Observer<ClinicsResponseModel>() {
            @Override
            public void onChanged(ClinicsResponseModel clinicsResponseModel) {
                if (clinicsResponseModel != null) {
                    if (StoreManager.getAppLanguage(ClinicDetailsActivity.this).equals("ar")) {
                        getActivityBinding().clinicName.setText(clinicsResponseModel.getClinics().get(0).getNameAr());
                        setTitle(clinicsResponseModel.getClinics().get(0).getNameAr());
                    } else {
                        getActivityBinding().clinicName.setText(clinicsResponseModel.getClinics().get(0).getName());
                        setTitle(clinicsResponseModel.getClinics().get(0).getName());
                    }
                    try {
                        getActivityBinding().rating.setRating(Float.parseFloat(clinicsResponseModel.getClinics().get(0).getReview()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    getActivityBinding().location.setText(clinicsResponseModel.getClinics().get(0).getAddress());
                    Glide.with(ClinicDetailsActivity.this)
                            .load(clinicsResponseModel.getClinics().get(0).getImage())
                            .placeholder(R.drawable.im_placeholder)
                            .error(R.drawable.im_placeholder)
                            .into(getActivityBinding().clinicImage);
                    getActivityViewModel().getReviews(clinicId).observe(ClinicDetailsActivity.this, new Observer<ReviewResponseModel>() {
                        @Override
                        public void onChanged(ReviewResponseModel reviewResponseModel) {
                            getActivityBinding().progressParent.setVisibility(View.GONE);
                            reviewsAdapter.addReviews((ArrayList<ReviewModel>) reviewResponseModel.getRequests());
                        }
                    });
                    getActivityBinding().showLocation.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?daddr=" + clinicsResponseModel.getClinics().get(0).getLatitude() + "," + clinicsResponseModel.getClinics().get(0).getLongitude()));
                            startActivity(intent);
                        }
                    });
                    getActivityBinding().showOffers.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(ClinicDetailsActivity.this, DepartmentOffersActivity.class).
                                    putExtra("type", "search").
                                    putExtra("departmentId", "0").
                                    putExtra("clinicId", clinicId).
                                    putExtra("offerId",""));
                        }
                    });
                } else {
                    getActivityBinding().progressParent.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_clinic_details;
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
    protected MainViewModel initialiseViewModel() {
        return new ViewModelProvider(this).get(MainViewModel.class);
    }
}
