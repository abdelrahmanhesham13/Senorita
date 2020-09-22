package com.senoritasaudi.views;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.features.ReturnMode;
import com.esafirm.imagepicker.model.Image;
import com.senoritasaudi.R;
import com.senoritasaudi.databinding.FragmentProfileBinding;
import com.senoritasaudi.models.responseModels.ImageResponse;
import com.senoritasaudi.models.responseModels.UserResponseModel;
import com.senoritasaudi.viewmodels.LoginViewModel;
import com.senoritasaudi.views.baseviews.BaseFragmentWithViewModel;
import com.senoritasaudi.views.baseviews.BaseFragmentWithoutViewModel;

import java.io.File;


/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class ProfileFragment extends BaseFragmentWithViewModel<LoginViewModel, FragmentProfileBinding> {

    File imageFile = null;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = super.onCreateView(inflater, container, savedInstanceState);
        if (getViewModel().containsUser()) {
            getFragmentBinding().editText6.setText(getViewModel().getUser().getName());
            getFragmentBinding().editText7.setText(getViewModel().getUser().getUsername());
            getFragmentBinding().editText8.setText(getViewModel().getUser().getMobile());
            if (!getViewModel().getUser().getImage().isEmpty()) {
                String imageUrl = null;
                if (getViewModel().getUser().getImage().contains("https")) {
                    imageUrl = getViewModel().getUser().getImage();
                } else {
                    imageUrl = "https://senoritasaudi.com/admin/user_img/" + getViewModel().getUser().getImage();
                }
                Glide.with(this)
                        .load(imageUrl)
                        .placeholder(R.drawable.im_placeholder)
                        .error(R.drawable.im_placeholder)
                        .into(getFragmentBinding().circleImageView);
            }
        }

        getFragmentBinding().relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setImage();
            }
        });

        getFragmentBinding().nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkData();
            }
        });

        return v;
    }

    private void checkData() {
        String name = getFragmentBinding().editText6.getText().toString().trim();
        String email = getFragmentBinding().editText7.getText().toString().trim();
        String phone = getFragmentBinding().editText8.getText().toString().trim();
        String password = getFragmentBinding().editText9.getText().toString().trim();
        if (name.isEmpty()) {
            getFragmentBinding().editText6.setError(getString(R.string.enter_your_name));
            getFragmentBinding().editText6.requestFocus();
        } else if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            getFragmentBinding().editText7.setError(getString(R.string.email_not_valid));
            getFragmentBinding().editText7.requestFocus();
        } else if (phone.isEmpty() || phone.length() < 10) {
            getFragmentBinding().editText8.setError(getString(R.string.phone_not_valid));
            getFragmentBinding().editText8.requestFocus();
        } else if (!password.isEmpty() && password.length() < 6) {
            getFragmentBinding().editText9.setError(getString(R.string.password_not_valid));
            getFragmentBinding().editText9.requestFocus();
        } else if (!getViewModel().containsUser()) {
            Toast.makeText(mContext, getString(R.string.please_login), Toast.LENGTH_SHORT).show();
        } else {
            getFragmentBinding().progressParent.setVisibility(View.VISIBLE);
            editProfile(name,email,phone,password);
        }
    }

    private void editProfile(String name, String email, String phone, String password) {
        if (imageFile != null) {
            getViewModel().uploadImage(imageFile).observe(mActivity, new Observer<ImageResponse>() {
                @Override
                public void onChanged(ImageResponse imageResponse) {
                    getViewModel().editProfileWithImage(name,email,phone,password,imageResponse.getImages().get(0)).observe(mActivity, new Observer<UserResponseModel>() {
                        @Override
                        public void onChanged(UserResponseModel userResponseModel) {
                            getFragmentBinding().progressParent.setVisibility(View.GONE);
                            if (userResponseModel != null && userResponseModel.getStatus()) {
                                getViewModel().saveUser(userResponseModel.getUser());
                                Toast.makeText(mContext, getString(R.string.edited), Toast.LENGTH_LONG).show();
                            } else if (userResponseModel != null) {
                                Toast.makeText(mContext, userResponseModel.getMessage(), Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(mContext,getString(R.string.error), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            });
        } else {
            getViewModel().editProfile(name,email,phone,password).observe(mActivity, new Observer<UserResponseModel>() {
                @Override
                public void onChanged(UserResponseModel userResponseModel) {
                    getFragmentBinding().progressParent.setVisibility(View.GONE);
                    if (userResponseModel != null && userResponseModel.getStatus()) {
                        getViewModel().saveUser(userResponseModel.getUser());
                        Toast.makeText(mContext, getString(R.string.edited), Toast.LENGTH_LONG).show();
                    } else if (userResponseModel != null) {
                        Toast.makeText(mContext, userResponseModel.getMessage(), Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(mContext,getString(R.string.error), Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

    public void setImage() {
        ImagePicker.create(this)
                .returnMode(ReturnMode.ALL) // set whether pick and / or camera action should return immediate result or not.
                .folderMode(true) // folder mode (false by default)
                .toolbarFolderTitle("Folder") // folder selection title
                .toolbarImageTitle("Tap to select") // image selection title
                .toolbarArrowColor(Color.WHITE) // Toolbar 'up' arrow color
                .single() // single mode
                .showCamera(true) // show camera or not (true by default)
                .start(); // start image picker activity with request code
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {
            // or get a single image only
            Image image = ImagePicker.getFirstImageOrNull(data);
            imageFile = new File(image.getPath());
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            Bitmap bitmap = BitmapFactory.decodeFile(image.getPath(), bmOptions);
            getFragmentBinding().circleImageView.setImageBitmap(bitmap);
            //uploadImageService(imageFile);
        }
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_profile;
    }

    @Override
    protected LoginViewModel initialiseViewModel() {
        return new ViewModelProvider(mActivity).get(LoginViewModel.class);
    }
}
