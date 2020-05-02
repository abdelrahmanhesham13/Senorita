package com.senoritasaudi.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.senoritasaudi.R;
import com.senoritasaudi.databinding.ActivityChangePasswordBinding;
import com.senoritasaudi.databinding.ActivityMobileVerificationBinding;
import com.senoritasaudi.models.FeedbackResponse;
import com.senoritasaudi.viewmodels.LoginViewModel;
import com.senoritasaudi.views.baseviews.BaseActivityWithViewModel;
import com.senoritasaudi.views.baseviews.BaseActivityWithoutViewModel;

public class ChangePasswordActivity extends BaseActivityWithViewModel<LoginViewModel, ActivityChangePasswordBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getString(R.string.change_password));
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        getActivityBinding().nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = getActivityBinding().editText.getText().toString().trim();
                if (password.isEmpty() || password.length() < 6) {
                    getActivityBinding().editText.setError(getString(R.string.password_not_valid));
                    getActivityBinding().editText.requestFocus();
                } else {
                    getActivityBinding().progressParent.setVisibility(View.VISIBLE);
                    getActivityViewModel().changePassword(getIntent().getStringExtra("phone"),password).observe(ChangePasswordActivity.this, new Observer<FeedbackResponse>() {
                        @Override
                        public void onChanged(FeedbackResponse feedbackResponse) {
                            if (feedbackResponse != null && feedbackResponse.getStatus()) {
                                Toast.makeText(ChangePasswordActivity.this, R.string.password_changed_successfully, Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(ChangePasswordActivity.this, getString(R.string.error), Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }
                    });
                }
            }
        });
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_change_password;
    }

    @Override
    protected LoginViewModel initialiseViewModel() {
        return new ViewModelProvider(this).get(LoginViewModel.class);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            setResult(Activity.RESULT_CANCELED, new Intent());
            finish();
            return true;
        }
        return false;
    }
}
