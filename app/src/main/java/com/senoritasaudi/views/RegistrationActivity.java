package com.senoritasaudi.views;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.senoritasaudi.R;
import com.senoritasaudi.databinding.ActivityRegisterationBinding;
import com.senoritasaudi.models.responseModels.UserResponseModel;
import com.senoritasaudi.navutils.NavigationManager;
import com.senoritasaudi.viewmodels.RegistrationViewModel;
import com.senoritasaudi.views.baseviews.BaseActivityWithViewModel;
import com.senoritasaudi.events.OnClickListener;

public class RegistrationActivity extends BaseActivityWithViewModel<RegistrationViewModel, ActivityRegisterationBinding> implements OnClickListener {

    String name;
    String email;
    String phone;
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityBinding().setClickHandler(this);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_registeration;
    }

    @Override
    protected RegistrationViewModel initialiseViewModel() {
        return new ViewModelProvider(this).get(RegistrationViewModel.class);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_now:
                finish();
                break;
            case R.id.next_button:
                checkData();
                break;
        }
    }

    private void checkData() {
        name = getActivityBinding().editText.getText().toString().trim();
        email = getActivityBinding().editText2.getText().toString().trim();
        phone = getActivityBinding().editText3.getText().toString().trim();
        password = getActivityBinding().editText4.getText().toString().trim();
        String confirmPassword = getActivityBinding().editText5.getText().toString().trim();
        if (name.isEmpty()) {
            getActivityBinding().editText.setError(getString(R.string.enter_your_name));
            getActivityBinding().editText.requestFocus();
        } else if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            getActivityBinding().editText2.setError(getString(R.string.email_not_valid));
            getActivityBinding().editText2.requestFocus();
        } else if (phone.length() != 10) {
            getActivityBinding().editText3.setError(getString(R.string.phone_not_valid));
            getActivityBinding().editText3.requestFocus();
        } else if (password.isEmpty() || password.length() < 6) {
            getActivityBinding().editText4.setError(getString(R.string.password_not_valid));
            getActivityBinding().editText4.requestFocus();
        } else if (!password.equals(confirmPassword)) {
            getActivityBinding().editText5.setError(getString(R.string.password_match_error));
            getActivityBinding().editText5.requestFocus();
        } else {
            startActivityForResult(new Intent(RegistrationActivity.this,MobileVerificationActivity.class)
                    .putExtra("phone",phone),5);
        }
    }

    private void register(String name, String email, String phone, String password) {
        getActivityViewModel().register(name,password,getActivityViewModel().getToken(),email,phone).observe(this, new Observer<UserResponseModel>() {
            @Override
            public void onChanged(UserResponseModel userResponseModel) {
                getActivityBinding().progressParent.setVisibility(View.GONE);
                if (userResponseModel != null && userResponseModel.getStatus()) {
                    getActivityViewModel().saveUser(userResponseModel.getUser());
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                } else if (userResponseModel != null && !userResponseModel.getStatus()) {
                    Toast.makeText(RegistrationActivity.this, userResponseModel.getMessage(), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(RegistrationActivity.this, getString(R.string.error), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 5 && resultCode == Activity.RESULT_OK) {
            getActivityBinding().progressParent.setVisibility(View.VISIBLE);
            register(name , email , phone , password);
        } else if (requestCode == 5) {
            Toast.makeText(this, R.string.not_verified, Toast.LENGTH_SHORT).show();
        }
    }
}
