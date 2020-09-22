package com.senoritasaudi.views;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.senoritasaudi.BuildConfig;
import com.senoritasaudi.R;
import com.senoritasaudi.databinding.ActivityMenuBinding;
import com.senoritasaudi.navutils.NavigationManager;
import com.senoritasaudi.viewmodels.LoginViewModel;
import com.senoritasaudi.views.baseviews.BaseActivityWithViewModel;
import com.senoritasaudi.views.baseviews.BaseActivityWithoutViewModel;
import com.senoritasaudi.events.OnClickListener;

public class MenuActivity extends BaseActivityWithViewModel<LoginViewModel, ActivityMenuBinding> implements OnClickListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityBinding().setClickHandler(this);

        if (getActivityViewModel().containsUser()) {
            if (!getActivityViewModel().getUser().getImage().isEmpty()) {
                String imageUrl = null;
                if (getActivityViewModel().getUser().getImage().contains("https")) {
                    imageUrl = getActivityViewModel().getUser().getImage();
                } else {
                    imageUrl = "https://senoritasaudi.com/admin/user_img/" + getActivityViewModel().getUser().getImage();
                }
                Glide.with(this)
                        .load(imageUrl)
                        .placeholder(R.drawable.im_placeholder)
                        .error(R.drawable.im_placeholder)
                        .into(getActivityBinding().circleImageView);
            }
            getActivityBinding().textView4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    intent.putExtra("profile",true);
                    startActivity(intent);
                }
            });
            getActivityBinding().textView4.setText(getActivityViewModel().getUser().getName());
            getActivityBinding().vipMember.setText(getString(R.string.points) + " : " + getActivityViewModel().getUser().getPoints());
        } else {
            getActivityBinding().imageView26.setVisibility(View.GONE);
            getActivityBinding().logout.setVisibility(View.GONE);
            getActivityBinding().vipMember.setVisibility(View.GONE);
            getActivityBinding().textView15.setVisibility(View.GONE);
            getActivityBinding().imageView27.setVisibility(View.GONE);
            getActivityBinding().textView4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            });
        }
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_menu;
    }

    @Override
    protected LoginViewModel initialiseViewModel() {
        return new ViewModelProvider(this).get(LoginViewModel.class);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (getActivityViewModel().containsUser()) {
            getActivityBinding().vipMember.setText(getString(R.string.points) + " : " + getActivityViewModel().getUser().getPoints());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.notifications:
                NavigationManager.startActivity(MenuActivity.this, NotificationsActivity.class);
                break;
            case R.id.back_button:
                finish();
                break;
            case R.id.home:
                Intent intent1 = new Intent(getApplicationContext(), MainActivity.class);
                intent1.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent1.putExtra("home",true);
                startActivity(intent1);
                break;
            case R.id.exchange_points:
                if (getActivityViewModel().containsUser()) {
                    NavigationManager.startActivity(MenuActivity.this, PointsActivity.class);
                } else {
                    Toast.makeText(this, getString(R.string.please_login), Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.search:
                NavigationManager.startActivity(MenuActivity.this,SearchActivity.class);
                break;
            case R.id.change_language:
                NavigationManager.startActivity(MenuActivity.this,ChangeLanguageActivity.class);
                break;
            case R.id.terms_and_conditions:
                startActivity(new Intent(MenuActivity.this, InformationActivity.class)
                        .putExtra("title", getString(R.string.terms_and_conditions))
                        .putExtra("title_for_api","terms"));
                break;
            case R.id.privacy_policy:
                startActivity(new Intent(MenuActivity.this, InformationActivity.class)
                        .putExtra("title", getString(R.string.privacy_policy))
                        .putExtra("title_for_api","privacy"));
                break;
            case R.id.about_us:
                startActivity(new Intent(MenuActivity.this, InformationActivity.class)
                        .putExtra("title", getString(R.string.about_us))
                        .putExtra("title_for_api","about"));
                break;
            case R.id.contact_us:
                NavigationManager.startActivity(MenuActivity.this, ContactUsActivity.class);
                break;
            case R.id.my_reservations:
                if (getActivityViewModel().containsUser()) {
                    NavigationManager.startActivity(MenuActivity.this, MyReservationsActivity.class);
                } else {
                    Toast.makeText(this, getString(R.string.please_login), Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.departments:
                NavigationManager.startActivity(MenuActivity.this, DepartmentsActivity.class);
                break;
            case R.id.logout:
                getActivityViewModel().removeUser();
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
            case R.id.add_clinic:
                NavigationManager.startActivity(MenuActivity.this,AddProviderRequestActivity.class);
                break;
            case R.id.share:
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT,
                        "استكشف برنامج سينيوريتا : https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID);
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
                break;
        }
    }
}
