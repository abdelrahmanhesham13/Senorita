package com.senoritasaudi.views;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.senoritasaudi.R;
import com.senoritasaudi.adapters.DepartmentsAdapter;
import com.senoritasaudi.adapters.OffersAdapter;
import com.senoritasaudi.databinding.FragmentHomeBinding;
import com.senoritasaudi.events.OnItemClicked;
import com.senoritasaudi.models.OfferModel;
import com.senoritasaudi.models.SliderModel;
import com.senoritasaudi.models.responseModels.DepartmentResponseModel;
import com.senoritasaudi.models.responseModels.OfferResponseModel;
import com.senoritasaudi.models.responseModels.SliderResponseModel;
import com.senoritasaudi.navutils.NavigationManager;
import com.senoritasaudi.viewmodels.MainViewModel;
import com.senoritasaudi.views.baseviews.BaseFragmentWithViewModel;
import com.senoritasaudi.views.baseviews.BaseFragmentWithoutViewModel;
import com.synnapps.carouselview.ImageListener;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends BaseFragmentWithViewModel<MainViewModel, FragmentHomeBinding> implements ImageListener {

    private DepartmentsAdapter mDepartmentsAdapter;
    private OffersAdapter mOffersAdapter;
    SliderResponseModel sliderResponseModel;
    private static final String TAG = "HomeFragment";

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = super.onCreateView(inflater, container, savedInstanceState);
        mDepartmentsAdapter = new DepartmentsAdapter(mContext, new OnItemClicked() {
            @Override
            public void onItemClicked(int position) {
                Intent intent = new Intent(mContext,DepartmentOffersActivity.class);
                intent.putExtra("departmentId",mDepartmentsAdapter.getDepartmentIdForPosition(position));
                startActivity(intent);
            }
        },1);
        mOffersAdapter = new OffersAdapter(mContext, new OnItemClicked() {
            @Override
            public void onItemClicked(int position) {
                Intent intent = new Intent(mContext,ReservationActivity.class);
                intent.putExtra("offerId",mOffersAdapter.getOfferIdForPosition(position));
                startActivity(intent);
            }
        });
        getFragmentBinding().departmentsRecycler.setHasFixedSize(true);
        getFragmentBinding().departmentsRecycler.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        getFragmentBinding().departmentsRecycler.setAdapter(mDepartmentsAdapter);
        getFragmentBinding().offersRecycler.setHasFixedSize(true);
        getFragmentBinding().offersRecycler.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        getFragmentBinding().offersRecycler.setAdapter(mOffersAdapter);
        getViewModel().getSliderResponseModelLiveData().observe(mActivity, new Observer<SliderResponseModel>() {
            @Override
            public void onChanged(SliderResponseModel sliderResponseModel) {
                getFragmentBinding().sliderProgress.setVisibility(View.GONE);
                if (sliderResponseModel != null) {
                    HomeFragment.this.sliderResponseModel = sliderResponseModel;
                    getFragmentBinding().imageSlider.setImageListener(HomeFragment.this);
                    getFragmentBinding().imageSlider.setPageCount(sliderResponseModel.getSliders().size());
                }
            }
        });
        getViewModel().getDepartmentResponseModelLiveData().observe(mActivity, new Observer<DepartmentResponseModel>() {
            @Override
            public void onChanged(DepartmentResponseModel departmentResponseModel) {
                getFragmentBinding().departmentsProgress.setVisibility(View.GONE);
                if (departmentResponseModel != null)
                    mDepartmentsAdapter.addAll(departmentResponseModel.getCategories());
            }
        });

        getViewModel().getOfferResponseModelLiveData().observe(mActivity, new Observer<OfferResponseModel>() {
            @Override
            public void onChanged(OfferResponseModel offerResponseModel) {
                getFragmentBinding().offersProgress.setVisibility(View.GONE);
                if (offerResponseModel != null) {
                    mOffersAdapter.addOffers((ArrayList<OfferModel>) offerResponseModel.getOffers());
                }
            }
        });

        getFragmentBinding().more2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavigationManager.startActivity(mContext,DepartmentOffersActivity.class);
            }
        });

        getFragmentBinding().more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavigationManager.startActivity(mContext,DepartmentsActivity.class);
            }
        });

        return v;
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_home;
    }

    @Override
    protected MainViewModel initialiseViewModel() {
        return new ViewModelProvider(mActivity).get(MainViewModel.class);
    }

    @Override
    public void setImageForPosition(int position, ImageView imageView) {
        Log.d(TAG, "setImageForPosition: " + position);
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        Glide.with(mContext)
                .load(sliderResponseModel.getSliders().get(position).getImage())
                .placeholder(R.drawable.im_placeholder)
                .error(R.drawable.im_placeholder)
                .into(imageView);

    }
}
