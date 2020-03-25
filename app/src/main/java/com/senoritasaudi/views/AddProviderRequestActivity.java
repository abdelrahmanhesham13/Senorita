package com.senoritasaudi.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.senoritasaudi.R;
import com.senoritasaudi.databinding.ActivityAddProviderRequestBinding;
import com.senoritasaudi.databinding.ActivityContactUsBinding;
import com.senoritasaudi.events.OnClickListener;
import com.senoritasaudi.models.FeedbackResponse;
import com.senoritasaudi.viewmodels.SendFeedbackViewModel;
import com.senoritasaudi.views.baseviews.BaseActivityWithViewModel;
import com.senoritasaudi.views.baseviews.BaseActivityWithoutViewModel;

public class AddProviderRequestActivity extends BaseActivityWithViewModel<SendFeedbackViewModel,ActivityAddProviderRequestBinding> implements OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getString(R.string.add_provider_request));
        getActivityBinding().setClickHandler(this);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_add_provider_request;
    }

    @Override
    protected SendFeedbackViewModel initialiseViewModel() {
        return new ViewModelProvider(this).get(SendFeedbackViewModel.class);
    }

    @Override
    public void onClick(View v) {
        String providerName = getActivityBinding().editText6.getText().toString().trim();
        String phoneNumber = getActivityBinding().editText8.getText().toString().trim();
        String address = getActivityBinding().editText9.getText().toString().trim();
        switch (v.getId()) {
            case R.id.next_button:
                if (providerName.isEmpty()) {
                    getActivityBinding().editText6.setError(getString(R.string.enter_provider_name));
                    getActivityBinding().editText6.requestFocus();
                } else if (phoneNumber.isEmpty()) {
                    getActivityBinding().editText8.setError(getString(R.string.enter_provider_phne));
                    getActivityBinding().editText8.requestFocus();
                } else if (address.isEmpty()) {
                    getActivityBinding().editText9.setError(getString(R.string.enter_address));
                    getActivityBinding().editText9.requestFocus();
                } else {
                    getActivityBinding().editText6.setError(null);
                    getActivityBinding().editText8.setError(null);
                    getActivityBinding().editText9.setError(null);
                    getActivityBinding().progressParent.setVisibility(View.VISIBLE);
                    sendRequest(providerName,phoneNumber,address);
                }
                break;
        }
    }

    private void sendRequest(String providerName, String phoneNumber, String address) {
        getActivityViewModel().sendRequest(providerName,phoneNumber,address).observe(this, new Observer<FeedbackResponse>() {
            @Override
            public void onChanged(FeedbackResponse feedbackResponse) {
                getActivityBinding().progressParent.setVisibility(View.GONE);
                if (feedbackResponse != null && feedbackResponse.getStatus()) {
                    Toast.makeText(AddProviderRequestActivity.this, feedbackResponse.getMessage(), Toast.LENGTH_LONG).show();
                    finish();
                } else if (feedbackResponse != null && !feedbackResponse.getStatus()) {
                    Toast.makeText(AddProviderRequestActivity.this, feedbackResponse.getMessage(), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(AddProviderRequestActivity.this, getString(R.string.error), Toast.LENGTH_LONG).show();
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
}
