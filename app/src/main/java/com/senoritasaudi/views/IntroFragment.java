package com.senoritasaudi.views;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.senoritasaudi.R;
import com.senoritasaudi.databinding.FragmentIntroBinding;
import com.senoritasaudi.views.baseviews.BaseFragmentWithoutViewModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class IntroFragment extends BaseFragmentWithoutViewModel<FragmentIntroBinding> {

    private String mText;
    private String mDetail;
    private int mImageResId;

    public IntroFragment() {
        // Required empty public constructor
    }

    IntroFragment(String text, String detail, int imageResId) {
        this.mText = text;
        this.mDetail = detail;
        this.mImageResId = imageResId;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = super.onCreateView(inflater,container,savedInstanceState);
        getFragmentBinding().textView2.setText(mText);
        getFragmentBinding().textView3.setText(mDetail);
        getFragmentBinding().imageView2.setImageResource(mImageResId);

        return v;
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_intro;
    }
}
