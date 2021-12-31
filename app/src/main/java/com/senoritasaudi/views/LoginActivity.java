package com.senoritasaudi.views;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.Login;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.senoritasaudi.R;
import com.senoritasaudi.databinding.ActivityLoginBinding;
import com.senoritasaudi.models.FeedbackResponse;
import com.senoritasaudi.models.responseModels.UserResponseModel;
import com.senoritasaudi.navutils.NavigationManager;
import com.senoritasaudi.viewmodels.LoginViewModel;
import com.senoritasaudi.views.baseviews.BaseActivityWithViewModel;
import com.senoritasaudi.events.OnClickListener;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Collections;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends BaseActivityWithViewModel<LoginViewModel, ActivityLoginBinding> implements OnClickListener {

    private static final int RC_SIGN_IN = 15;
    GoogleSignInClient mGoogleSignInClient;
    private static final String TAG = "LoginActivity";
    CallbackManager callbackManager;

    String email;
    String name;
    String phoneNumber;
    String image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityBinding().setClickHandler(this);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestProfile()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        getActivityBinding().google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGoogleSignInClient.signOut();
                signInWithGoogle();
            }
        });

        getActivityBinding().imageView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this, Arrays.asList("public_profile","email"));
            }
        });


        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        getFacebookData(loginResult.getAccessToken());
                    }

                    @Override
                    public void onCancel() {
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        exception.printStackTrace();
                    }
                });

        getActivityBinding().textView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(LoginActivity.this,MobileVerificationActivity.class),55);
            }
        });
    }

    private void getFacebookData(AccessToken accessToken) {
        GraphRequest request = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                try {
                    email = object.getString("email");
                    name = object.getString("name");
                    image = "https://graph.facebook.com/" + object.get("id") + "/picture?type=large";
                    Log.d(TAG, "onCompleted: " + email);
                    loginSocial();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "name,email");
        request.setParameters(parameters);
        request.executeAsync();
    }

    private void signInWithGoogle() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }

        if (requestCode == 5 && resultCode == Activity.RESULT_OK) {
            getActivityBinding().progressParent.setVisibility(View.VISIBLE);
            phoneNumber = data.getStringExtra("phone");
            register(name , email , phoneNumber,image);
        } else if (requestCode == 5) {
            Toast.makeText(this, R.string.not_verified, Toast.LENGTH_SHORT).show();
        }


        if (requestCode == 55 && resultCode == Activity.RESULT_OK) {
            startActivity(new Intent(LoginActivity.this,ChangePasswordActivity.class).putExtra("phone",data.getStringExtra("phone")));
        } else if (requestCode == 55) {
            Toast.makeText(this, R.string.not_verified, Toast.LENGTH_SHORT).show();
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            if (account != null) {
                name = account.getDisplayName();
                email = account.getEmail();
                image = null;
                if (account.getPhotoUrl() != null) {
                    image = account.getPhotoUrl().toString();
                }
                Log.d(TAG, "handleSignInResult: " + email);
                loginSocial();
            }
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }

    private void loginSocial() {
        getActivityBinding().progressParent.setVisibility(View.VISIBLE);
        getActivityViewModel().checkInput(email).observe(this, new Observer<UserResponseModel>() {
            @Override
            public void onChanged(UserResponseModel userResponseModel) {
                getActivityBinding().progressParent.setVisibility(View.GONE);
                if (userResponseModel != null && userResponseModel.getStatus()) {
                    getActivityViewModel().saveUser(userResponseModel.getUser());
                    getActivityViewModel().updateToken().observe(LoginActivity.this, new Observer<FeedbackResponse>() {
                        @Override
                        public void onChanged(FeedbackResponse feedbackResponse) {
                            NavigationManager.startActivity(LoginActivity.this, MainActivity.class);
                            finish();
                        }
                    });
                } else if (userResponseModel != null) {
                    startActivityForResult(new Intent(LoginActivity.this,MobileVerificationActivity.class),5);
                } else {
                    Toast.makeText(LoginActivity.this, getString(R.string.error), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void register(String name, String email, String phoneNumber , String image) {
        getActivityViewModel().register(name,"123456",getActivityViewModel().getToken(),email,phoneNumber,image).observe(this, new Observer<UserResponseModel>() {
            @Override
            public void onChanged(UserResponseModel userResponseModel) {
                getActivityBinding().progressParent.setVisibility(View.GONE);
                if (userResponseModel != null && userResponseModel.getUser() != null) {
                    getActivityViewModel().saveUser(userResponseModel.getUser());
                    NavigationManager.startActivity(LoginActivity.this, MainActivity.class);
                    finish();
                } else if (userResponseModel != null) {
                    Toast.makeText(LoginActivity.this, userResponseModel.getMessage(), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(LoginActivity.this, getString(R.string.error), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_login;
    }

    @Override
    protected LoginViewModel initialiseViewModel() {
        return new ViewModelProvider(this).get(LoginViewModel.class);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register_now:
                NavigationManager.startActivity(LoginActivity.this, RegistrationActivity.class);
                break;
            case R.id.next_button:
                checkData();
                break;
            case R.id.imageView:
            case R.id.textView:
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
            case R.id.accept_privacy_policy:
                openWebPage("https://senoritasaudi.com/privacy");
                break;
        }
    }

    public void openWebPage(String url) {
        Uri webpage = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    private void checkData() {
        String phone = getActivityBinding().editText.getText().toString().trim();
        String password = getActivityBinding().editText2.getText().toString().trim();
        if (phone.length() != 10) {
            getActivityBinding().editText.setError(getString(R.string.phone_not_valid));
            getActivityBinding().editText.requestFocus();
        } else if (password.isEmpty() || password.length() < 6) {
            getActivityBinding().editText2.setError(getString(R.string.password_not_valid));
            getActivityBinding().editText2.requestFocus();
        } else {
            getActivityBinding().progressParent.setVisibility(View.VISIBLE);
            login(phone, password);
        }
    }

    private void login(String phone, String password) {
        Log.d(TAG, "login: " + getActivityViewModel().getToken());
        getActivityViewModel().login(phone, password, getActivityViewModel().getToken()).observe(this, new Observer<UserResponseModel>() {
            @Override
            public void onChanged(UserResponseModel userResponseModel) {
                getActivityBinding().progressParent.setVisibility(View.GONE);
                if (userResponseModel != null && userResponseModel.getUser() != null) {
                    getActivityViewModel().saveUser(userResponseModel.getUser());
                    NavigationManager.startActivity(LoginActivity.this, MainActivity.class);
                    finish();
                } else if (userResponseModel != null) {
                    Toast.makeText(LoginActivity.this, userResponseModel.getMessage(), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(LoginActivity.this, getString(R.string.error), Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
