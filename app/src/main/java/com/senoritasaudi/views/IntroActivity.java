package com.senoritasaudi.views;

import android.os.Bundle;
import android.view.View;

import com.senoritasaudi.R;
import com.senoritasaudi.adapters.IntroPagerAdapter;
import com.senoritasaudi.databinding.ActivityIntroBinding;
import com.senoritasaudi.navutils.NavigationManager;
import com.senoritasaudi.views.baseviews.BaseActivityWithoutViewModel;
import com.senoritasaudi.events.OnClickListener;

public class IntroActivity extends BaseActivityWithoutViewModel<ActivityIntroBinding> implements OnClickListener {

    IntroPagerAdapter mIntroPagerAdapter;
    private static final String TAG = "IntroActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupView();
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_intro;
    }

    private void setupView() {
        mIntroPagerAdapter = new IntroPagerAdapter(getSupportFragmentManager());
        mIntroPagerAdapter.addFragment(new IntroFragment("","تصفح واحجز افضل العروض الحصرية",R.drawable.splash_offers));
        mIntroPagerAdapter.addFragment(new IntroFragment("","يمكنك الان حجز الميعاد المناسب في ثواني",R.drawable.splash_steps));
        mIntroPagerAdapter.addFragment(new IntroFragment("","امسح الكود لدي مقدم الخدمة لاثبات حضورك واحصل علي نقاط",R.drawable.splash_scan));
        mIntroPagerAdapter.addFragment(new IntroFragment("","تلقي اشعارات باجدد العروض الحصرية",R.drawable.splash_notification));
        getActivityBinding().viewPager.setAdapter(mIntroPagerAdapter);
        getActivityBinding().setClickHandler(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.next_button :
                if (getActivityBinding().viewPager.getCurrentItem() < mIntroPagerAdapter.getCount() - 1) {
                    moveViewPager(getActivityBinding().viewPager.getCurrentItem() + 1);
                } else {
                    NavigationManager.startActivity(IntroActivity.this, LoginActivity.class);
                    finish();
                }
                break;
            case R.id.imageView:
            case R.id.textView:
                NavigationManager.startActivity(IntroActivity.this,LoginActivity.class);
                finish();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (getActivityBinding().viewPager.getCurrentItem() != 0) {
            moveViewPager(getActivityBinding().viewPager.getCurrentItem() - 1);
        } else {
            super.onBackPressed();
        }
    }


    public void moveViewPager(int pageNumber) {
        getActivityBinding().viewPager.setCurrentItem(pageNumber);
    }
}
