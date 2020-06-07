package com.senoritasaudi.views;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.senoritasaudi.R;
import com.senoritasaudi.databinding.ActivityMainBinding;
import com.senoritasaudi.navutils.NavigationManager;
import com.senoritasaudi.viewmodels.MainViewModel;
import com.senoritasaudi.views.baseviews.BaseActivityWithViewModel;
import com.senoritasaudi.events.OnClickListener;

public class MainActivity extends BaseActivityWithViewModel<MainViewModel, ActivityMainBinding> implements OnClickListener {

    BottomNavigationViewEx mBottomNavigationViewEx;
    NavController mNavController;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupViews();
    }

    private void setupViews() {
        getActivityBinding().setClickHandler(this);
        mBottomNavigationViewEx = getActivityBinding().bottomNavigation;
        mBottomNavigationViewEx.enableAnimation(false);
        mBottomNavigationViewEx.enableShiftingMode(false);
        mBottomNavigationViewEx.enableItemShiftingMode(false);
        Typeface typeface = ResourcesCompat.getFont(this, R.font.jf_flat);
        mBottomNavigationViewEx.setTypeface(typeface);
        mNavController = Navigation.findNavController(this, R.id.fragNavHost);
        NavigationUI.setupWithNavController(mBottomNavigationViewEx, mNavController);
        toolbar = getActivityBinding().toolbar;
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent.hasExtra("profile")) {
            mBottomNavigationViewEx.setCurrentItem(4);
        } else if (intent.hasExtra("home")) {
            mBottomNavigationViewEx.setCurrentItem(0);
        }
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_main;
    }

    @Override
    protected MainViewModel initialiseViewModel() {
        return new ViewModelProvider(this).get(MainViewModel.class);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.search) {
            NavigationManager.startActivity(MainActivity.this,SearchActivity.class);
            return true;
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public void onClick(View v) {
        NavigationManager.startActivity(MainActivity.this,MenuActivity.class);
    }
}
