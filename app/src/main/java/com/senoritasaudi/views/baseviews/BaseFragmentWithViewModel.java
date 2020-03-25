package com.senoritasaudi.views.baseviews;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModel;

public abstract class BaseFragmentWithViewModel <V extends ViewModel, D extends ViewDataBinding> extends Fragment {

    private D mFragmentBinding;
    protected Context mContext;
    protected FragmentActivity mActivity;
    protected V mViewModel;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.mContext = context;
        this.mActivity = getActivity();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mFragmentBinding = DataBindingUtil.inflate(
                inflater, getLayoutResourceId(), container, false);
        mViewModel = initialiseViewModel();
        return mFragmentBinding.getRoot();
    }

    public V getViewModel() {
        return mViewModel;
    }

    protected D getFragmentBinding() {
        return mFragmentBinding;
    }

    protected abstract int getLayoutResourceId();

    protected abstract V initialiseViewModel();

}
