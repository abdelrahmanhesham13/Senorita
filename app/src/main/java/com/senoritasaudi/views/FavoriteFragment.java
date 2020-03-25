package com.senoritasaudi.views;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.senoritasaudi.R;
import com.senoritasaudi.adapters.DepartmentOffersAdapter;
import com.senoritasaudi.databinding.FragmentFavoriteBinding;
import com.senoritasaudi.events.OnItemClicked;
import com.senoritasaudi.models.OfferModel;
import com.senoritasaudi.models.responseModels.FavoriteResponseModel;
import com.senoritasaudi.models.responseModels.OfferResponseModel;
import com.senoritasaudi.viewmodels.OffersViewModel;
import com.senoritasaudi.viewmodels.factory.ViewModelFactory;
import com.senoritasaudi.views.baseviews.BaseFragmentWithViewModel;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends BaseFragmentWithViewModel<OffersViewModel , FragmentFavoriteBinding> implements OnItemClicked {

    DepartmentOffersAdapter departmentOffersAdapter;

    public FavoriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = super.onCreateView(inflater,container,savedInstanceState);
        getFragmentBinding().offersRecycler.setHasFixedSize(true);
        getFragmentBinding().offersRecycler.setLayoutManager(new LinearLayoutManager(mContext));
        departmentOffersAdapter = new DepartmentOffersAdapter(mContext, this, new OnItemClicked() {
            @Override
            public void onItemClicked(int position) {
                getViewModel().addFavorite(departmentOffersAdapter.getOfferIdForPosition(position)).observe(mActivity, new Observer<FavoriteResponseModel>() {
                    @Override
                    public void onChanged(FavoriteResponseModel favoriteResponseModel) {
                        if (favoriteResponseModel != null && favoriteResponseModel.getStatus()) {
                            departmentOffersAdapter.deleteOffer(position);
                            Toast.makeText(mContext, favoriteResponseModel.getMessage(), Toast.LENGTH_SHORT).show();
                        } else if (favoriteResponseModel != null) {
                            Toast.makeText(mContext, favoriteResponseModel.getMessage(), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(mContext, getString(R.string.error), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        getFragmentBinding().offersRecycler.setAdapter(departmentOffersAdapter);

        if (getViewModel().containsUser()) {
            getViewModel().getFavorites().observe(mActivity, new Observer<OfferResponseModel>() {
                @Override
                public void onChanged(OfferResponseModel offerResponseModel) {
                    getFragmentBinding().progressBar.setVisibility(View.GONE);
                    if (offerResponseModel != null && offerResponseModel.getStatus()) {
                        departmentOffersAdapter.addOffers((ArrayList<OfferModel>) offerResponseModel.getOffers());
                    } else if (offerResponseModel != null) {
                        Toast.makeText(mContext, getString(R.string.no_favorites), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(mContext, getString(R.string.error), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            getFragmentBinding().progressBar.setVisibility(View.GONE);
            Toast.makeText(mContext, getString(R.string.please_login), Toast.LENGTH_SHORT).show();
        }

        return view;
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_favorite;
    }

    @Override
    protected OffersViewModel initialiseViewModel() {
        ViewModelFactory viewModelFactory = new ViewModelFactory(mActivity.getApplication(),"");
        return new ViewModelProvider(this,viewModelFactory).get(OffersViewModel.class);
    }

    @Override
    public void onItemClicked(int position) {
        Intent intent = new Intent(mContext,ReservationActivity.class);
        intent.putExtra("offerId",departmentOffersAdapter.getOfferIdForPosition(position));
        startActivity(intent);
    }
}
