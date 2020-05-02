package com.senoritasaudi.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.senoritasaudi.R;
import com.senoritasaudi.databinding.ActivityReservationBinding;
import com.senoritasaudi.events.OnClickListener;
import com.senoritasaudi.models.FeedbackResponse;
import com.senoritasaudi.models.OfferModel;
import com.senoritasaudi.models.responseModels.OfferResponseModel;
import com.senoritasaudi.models.responseModels.ReservationResponseModel;
import com.senoritasaudi.storeutils.StoreManager;
import com.senoritasaudi.viewmodels.ReservationViewModel;
import com.senoritasaudi.viewmodels.factory.ViewModelFactory;
import com.senoritasaudi.views.baseviews.BaseActivityWithViewModel;
import com.senoritasaudi.views.baseviews.BaseActivityWithoutViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Objects;

public class ReservationActivity extends BaseActivityWithViewModel<ReservationViewModel,ActivityReservationBinding> implements OnClickListener {

    OfferModel offerModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getString(R.string.offer_number) + " : " + getIntent().getStringExtra("offerId"));
        getActivityBinding().setClickHandler(this);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getActivityBinding().cardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReservationActivity.this,DepartmentOffersActivity.class);
                intent.putExtra("departmentId",offerModel.getCategoryId());
                startActivity(intent);
            }
        });

        getActivityBinding().cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ReservationActivity.this,ClinicDetailsActivity.class).putExtra("clinicId",offerModel.getClinicId()));
            }
        });


        getActivityViewModel().getOffer().observe(this, new Observer<OfferResponseModel>() {
            @Override
            public void onChanged(OfferResponseModel offerResponseModel) {
                getActivityBinding().progressParent.setVisibility(View.GONE);
                offerModel = offerResponseModel.getOffers().get(0);
                if (StoreManager.getAppLanguage(ReservationActivity.this).equals("ar")) {
                    getActivityBinding().textView15.setText(offerModel.getClinicNameAr());
                    getActivityBinding().textView16.setText(offerModel.getCategoryNameAr());
                } else {
                    getActivityBinding().textView15.setText(offerModel.getClinicName());
                    getActivityBinding().textView16.setText(offerModel.getCategoryName());
                }

                getActivityBinding().offerBooked.setText(getString(R.string.offer_booked) + " : " + offerModel.getRequestCount() + " " + getString(R.string.time));
                getActivityBinding().textView17.setText(offerModel.getPlaceName());
                Glide.with(ReservationActivity.this)
                        .load(offerModel.getImage())
                        .placeholder(R.drawable.im_placeholder)
                        .error(R.drawable.im_placeholder)
                        .into(getActivityBinding().imageView29);
                Glide.with(ReservationActivity.this)
                        .load(offerModel.getCategoryImage())
                        .placeholder(R.drawable.im_placeholder)
                        .error(R.drawable.im_placeholder)
                        .into(getActivityBinding().departmentImage);
            }
        });
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_reservation;
    }

    @Override
    protected ReservationViewModel initialiseViewModel() {
        ViewModelFactory viewModelFactory = new ViewModelFactory(getApplication(),"");
        viewModelFactory.setOfferId(getIntent().getStringExtra("offerId"));
        return new ViewModelProvider(this,viewModelFactory).get(ReservationViewModel.class);
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.textView:
                showDateDialog();
                break;
            case R.id.textView2:
                showTimeDialog();
                break;
            case R.id.next_button:
                checkData();
                break;
            case R.id.check_promo_code:
                checkPromoCode();
                break;
        }
    }

    private void checkPromoCode() {
        String promoCode = getActivityBinding().editText3.getText().toString().trim();
        if (promoCode.isEmpty()) {
            getActivityBinding().editText3.setError(getString(R.string.enter_promo_code));
            getActivityBinding().editText3.requestFocus();
        } else {
            getActivityBinding().progressParent.setVisibility(View.VISIBLE);
            getActivityViewModel().checkPromoCode(offerModel.getId(),offerModel.getClinicId(),promoCode).observe(ReservationActivity.this, new Observer<FeedbackResponse>() {
                @Override
                public void onChanged(FeedbackResponse feedbackResponse) {
                    getActivityBinding().progressParent.setVisibility(View.GONE);
                    if (feedbackResponse != null && feedbackResponse.getStatus()) {
                        if (StoreManager.getAppLanguage(ReservationActivity.this).equals("ar")) {
                            Toast.makeText(ReservationActivity.this, feedbackResponse.getMessageAr(), Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(ReservationActivity.this, feedbackResponse.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    } else if (feedbackResponse != null) {
                        if (StoreManager.getAppLanguage(ReservationActivity.this).equals("ar")) {
                            Toast.makeText(ReservationActivity.this, feedbackResponse.getMessageAr(), Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(ReservationActivity.this, feedbackResponse.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(ReservationActivity.this,getString(R.string.error), Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

    private void checkData() {
        String date = getActivityBinding().textView.getText().toString();
        String time = getActivityBinding().textView2.getText().toString();
        String promoCode = getActivityBinding().editText3.getText().toString().trim();
        if (date.isEmpty()) {
            getActivityBinding().textView.setError(getString(R.string.select_date));
            getActivityBinding().textView.requestFocus();
        } else if (time.isEmpty()) {
            getActivityBinding().textView2.setError(getString(R.string.select_time));
            getActivityBinding().textView2.requestFocus();
        } else if (!getActivityViewModel().containsUser()) {
            Toast.makeText(this, getString(R.string.please_login), Toast.LENGTH_SHORT).show();
        } else {
            getActivityBinding().progressParent.setVisibility(View.VISIBLE);
            addRequest(date,time,promoCode);
        }
    }

    private void addRequest(String date, String time, String promoCode) {
        HashMap<String , String> hashMap = new HashMap<>();
        hashMap.put("category_id",offerModel.getCategoryId());
        hashMap.put("clinic_id",offerModel.getClinicId());
        hashMap.put("offer_id",offerModel.getId());
        hashMap.put("date",date);
        hashMap.put("time",time);
        hashMap.put("description","description");
        if (!promoCode.isEmpty()) {
            hashMap.put("promocode",promoCode);
        }
        hashMap.put("user_id",getActivityViewModel().getUser().getId());
        getActivityViewModel().addRequest(hashMap).observe(this, new Observer<ReservationResponseModel>() {
            @Override
            public void onChanged(ReservationResponseModel reservationResponseModel) {
                getActivityBinding().progressParent.setVisibility(View.GONE);
                if (reservationResponseModel != null && reservationResponseModel.getStatus()) {
                    Toast.makeText(ReservationActivity.this, reservationResponseModel.getMessage(), Toast.LENGTH_SHORT).show();
                    showConfirmDialog(reservationResponseModel.getId());
                } else if (reservationResponseModel != null) {
                    Toast.makeText(ReservationActivity.this, reservationResponseModel.getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ReservationActivity.this, getString(R.string.error), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void showConfirmDialog(String reservationId) {
        final Dialog d = new Dialog(this);
        d.setContentView(R.layout.reservation_confirm_dialog);
        d.setCancelable(false);

        Button save = d.findViewById(R.id.dismiss);
        ((TextView)d.findViewById(R.id.text)).setText(getString(R.string.confirm_reservation) + reservationId);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        d.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        d.show();
    }

    private void showTimeDialog() {
        Calendar calendar = Calendar.getInstance();

        TimePickerDialog mTimePickerDialog;
        mTimePickerDialog = new TimePickerDialog(ReservationActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String AM_PM ;
                if(hourOfDay < 12) {
                    AM_PM = "AM";
                } else {
                    AM_PM = "PM";
                }
                SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
                SimpleDateFormat date12Format = new SimpleDateFormat("hh:mm a", Locale.ENGLISH);
                try {
                    getActivityBinding().textView2.setText(dateFormat.format(Objects.requireNonNull(date12Format.parse(hourOfDay + ":" + minute + " " + AM_PM))));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
        },calendar.get(Calendar.HOUR),calendar.get(Calendar.MINUTE),false);
        mTimePickerDialog.setTitle(getString(R.string.select_date));
        mTimePickerDialog.show();
    }

    private void showDateDialog() {
        Calendar calendar = Calendar.getInstance();
        int mYear = calendar.get(Calendar.YEAR);
        int mMonth = calendar.get(Calendar.MONTH);
        int mDay = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog mDatePicker;
        mDatePicker = new DatePickerDialog(ReservationActivity.this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker datepicker, int selectedYear, int selectedMonth, int selectedDay) {
                selectedMonth = selectedMonth + 1;
                getActivityBinding().textView.setText(selectedDay + "_" + selectedMonth + "_" + selectedYear);
            }
        }, mYear, mMonth, mDay);
        mDatePicker.setTitle(getString(R.string.select_date));
        mDatePicker.getDatePicker().setMinDate(Calendar.getInstance().getTimeInMillis());
        mDatePicker.show();
    }
}
