package com.senoritasaudi.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.senoritasaudi.R;
import com.senoritasaudi.databinding.ActivityMobileVerificationBinding;
import com.senoritasaudi.views.baseviews.BaseActivityWithoutViewModel;

import java.util.concurrent.TimeUnit;

public class MobileVerificationActivity extends BaseActivityWithoutViewModel<ActivityMobileVerificationBinding> {

    String mPhoneNumber;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getString(R.string.mobile_verification));
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential credential) {
                signInWithPhoneAuthCredential(credential);
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                e.printStackTrace();
            }

            @Override
            public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken token) {
                mVerificationId = verificationId;
                mResendToken = token;
            }
        };
        if (getIntent().hasExtra("phone")) {
            mPhoneNumber = getIntent().getStringExtra("phone");
            PhoneAuthProvider.getInstance().verifyPhoneNumber(
                    "+966" + mPhoneNumber,
                    30,
                    TimeUnit.SECONDS,
                    MobileVerificationActivity.this,
                    mCallbacks);
        } else {
            getActivityBinding().editText.setHint(getString(R.string.phone_number));
            getActivityBinding().editText.setInputType(InputType.TYPE_CLASS_PHONE);
        }

        getActivityBinding().nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPhoneNumber != null) {
                    if (getActivityBinding().editText.getText().toString().trim().isEmpty()) {
                        getActivityBinding().editText.setError(getString(R.string.enter_code));
                        getActivityBinding().editText.requestFocus();
                    } else {
                        getActivityBinding().progressParent.setVisibility(View.VISIBLE);
                        if (mVerificationId != null) {
                            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, getActivityBinding().editText.getText().toString());
                            signInWithPhoneAuthCredential(credential);
                        } else {
                            getActivityBinding().progressParent.setVisibility(View.GONE);
                            Toast.makeText(MobileVerificationActivity.this, getString(R.string.error), Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    if (getActivityBinding().editText.getText().toString().trim().length() != 10) {
                        getActivityBinding().editText.setError(getString(R.string.phone_not_valid));
                        getActivityBinding().editText.requestFocus();
                    } else {
                        mPhoneNumber = getActivityBinding().editText.getText().toString().trim();
                        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                                "+966" + mPhoneNumber,
                                30,
                                TimeUnit.SECONDS,
                                MobileVerificationActivity.this,
                                mCallbacks);
                        getActivityBinding().editText.setHint(getString(R.string.enter_code));
                        getActivityBinding().editText.setInputType(InputType.TYPE_CLASS_NUMBER);
                        getActivityBinding().editText.setText("");
                        getActivityBinding().editText.clearFocus();
                    }
                }
            }
        });
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_mobile_verification;
    }

    @Override
    public void onBackPressed() {
        setResult(Activity.RESULT_CANCELED,new Intent());
        super.onBackPressed();
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

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        FirebaseAuth.getInstance().signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        getActivityBinding().progressParent.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
                            Intent returnIntent = new Intent();
                            returnIntent.putExtra("result", "verified");
                            returnIntent.putExtra("phone",mPhoneNumber);
                            setResult(Activity.RESULT_OK, returnIntent);
                            finish();
                        } else {
                            getActivityBinding().editText.setError(getString(R.string.code_not_valid));
                        }
                    }
                });
    }
}
