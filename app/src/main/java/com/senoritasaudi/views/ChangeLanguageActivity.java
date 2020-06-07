package com.senoritasaudi.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.senoritasaudi.R;
import com.senoritasaudi.databinding.ActivityChangeLanguageBinding;
import com.senoritasaudi.navutils.NavigationManager;
import com.senoritasaudi.storeutils.StoreManager;
import com.senoritasaudi.views.baseviews.BaseActivityWithoutViewModel;

public class ChangeLanguageActivity extends BaseActivityWithoutViewModel<ActivityChangeLanguageBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getString(R.string.language));
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        if (StoreManager.getAppLanguage(this).equals("ar")) {
            getActivityBinding().arabicButton.setBackgroundResource(R.drawable.bg_change_language_button_activated);
            getActivityBinding().arabicButton.setTextColor(getColor(R.color.colorPrimary));
            getActivityBinding().englishButton.setBackgroundResource(R.drawable.bg_change_language_button);
            getActivityBinding().englishButton.setTextColor(Color.parseColor("#000000"));
        } else {
            getActivityBinding().englishButton.setBackgroundResource(R.drawable.bg_change_language_button_activated);
            getActivityBinding().englishButton.setTextColor(getColor(R.color.colorPrimary));
            getActivityBinding().arabicButton.setBackgroundResource(R.drawable.bg_change_language_button);
            getActivityBinding().arabicButton.setTextColor(Color.parseColor("#000000"));
        }

        getActivityBinding().arabicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StoreManager.setAppLanguage(ChangeLanguageActivity.this,"ar");
                if (!getIntent().hasExtra("intro")) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } else {
                    NavigationManager.startActivity(ChangeLanguageActivity.this,IntroActivity.class);
                }
                finish();
            }
        });

        getActivityBinding().englishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StoreManager.setAppLanguage(ChangeLanguageActivity.this,"en");
                if (!getIntent().hasExtra("intro")) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } else {
                    NavigationManager.startActivity(ChangeLanguageActivity.this,IntroActivity.class);
                }
                finish();
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

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_change_language;
    }
}
