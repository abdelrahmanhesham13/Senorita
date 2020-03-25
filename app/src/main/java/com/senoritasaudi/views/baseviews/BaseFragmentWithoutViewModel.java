package com.senoritasaudi.views.baseviews;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;

import com.senoritasaudi.R;

public abstract class BaseFragmentWithoutViewModel<D extends ViewDataBinding> extends Fragment {

    private D mFragmentBinding;
    protected Context mContext;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         mFragmentBinding = DataBindingUtil.inflate(
                inflater, getLayoutResourceId(), container, false);
         return mFragmentBinding.getRoot();
    }

    protected D getFragmentBinding() {
        return mFragmentBinding;
    }

    protected abstract int getLayoutResourceId();
}
