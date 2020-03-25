package com.senoritasaudi.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.senoritasaudi.R;
import com.senoritasaudi.adapters.ReservationsAdapter;
import com.senoritasaudi.databinding.ActivityMyReservationsBinding;
import com.senoritasaudi.events.OnItemClicked;
import com.senoritasaudi.models.FeedbackResponse;
import com.senoritasaudi.models.RequestModel;
import com.senoritasaudi.models.responseModels.RequestsModelResponse;
import com.senoritasaudi.viewmodels.MainViewModel;
import com.senoritasaudi.views.baseviews.BaseActivityWithViewModel;

import java.util.ArrayList;

public class MyReservationsActivity extends BaseActivityWithViewModel<MainViewModel, ActivityMyReservationsBinding> implements OnItemClicked {

    Observer<RequestsModelResponse> addRequests = new Observer<RequestsModelResponse>() {
        @Override
        public void onChanged(RequestsModelResponse requestsModelResponse) {
            getActivityBinding().progressBar.setVisibility(View.GONE);
            if (requestsModelResponse != null) {
                reservationsAdapter.removeAll();
                reservationsAdapter.addRequests((ArrayList<RequestModel>) requestsModelResponse.getRequests());
                if (requestsModelResponse.getCount() == 0) {
                    Toast.makeText(MyReservationsActivity.this, R.string.no_reservations, Toast.LENGTH_SHORT).show();
                }
            }
        }
    };

    ReservationsAdapter reservationsAdapter;
    private float mRatingNumber = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getString(R.string.my_reservations));
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setupViews();
    }

    private void setupViews() {
        getActivityBinding().reservationsRecycler.setLayoutManager(new LinearLayoutManager(this));
        getActivityBinding().reservationsRecycler.setHasFixedSize(true);
        reservationsAdapter = new ReservationsAdapter(this, this, getActivityViewModel().getUser().getName(), new ReservationsAdapter.OnRateClicked() {
            @Override
            public void onRateClicked(String reservationId) {
                show(reservationId);
            }
        });
        getActivityBinding().reservationsRecycler.setAdapter(reservationsAdapter);
        getActivityViewModel().getRequests().observe(this, addRequests);
    }

    private void show(String id) {
        mRatingNumber = 0;
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);
        View dialogView = inflater.inflate(R.layout.dialog_rating, null);
        dialogBuilder.setView(dialogView);
        final RatingBar rating = dialogView.findViewById(R.id.rating_bar_2);
        final Button rate = dialogView.findViewById(R.id.btn_rate);
        final EditText comment = dialogView.findViewById(R.id.comment);
        rating.setIsIndicator(false);
        AlertDialog alertDialog = dialogBuilder.create();
        if (alertDialog.getWindow() != null) {
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        }
        alertDialog.show();
        rating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                mRatingNumber = rating;
            }
        });
        rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String commentText = comment.getText().toString();
                if (mRatingNumber < 1) {
                    Toast.makeText(MyReservationsActivity.this, R.string.rate_valid, Toast.LENGTH_SHORT).show();
                } else {
                    alertDialog.dismiss();
                    getActivityViewModel().addReview(id, commentText, String.valueOf(mRatingNumber)).observe(MyReservationsActivity.this, new Observer<FeedbackResponse>() {
                        @Override
                        public void onChanged(FeedbackResponse feedbackResponse) {
                            getActivityViewModel().getRequests().observe(MyReservationsActivity.this, addRequests);
                            if (feedbackResponse != null) {
                                Toast.makeText(MyReservationsActivity.this, feedbackResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });


    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_my_reservations;
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

    @Override
    public void onItemClicked(int position) {

    }
}
