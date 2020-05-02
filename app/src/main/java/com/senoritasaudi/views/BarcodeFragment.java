package com.senoritasaudi.views;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.PointF;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.dlazaro66.qrcodereaderview.QRCodeReaderView;
import com.senoritasaudi.R;
import com.senoritasaudi.databinding.FragmentBarcodeBinding;
import com.senoritasaudi.models.UserModel;
import com.senoritasaudi.models.responseModels.QRCodeResponse;
import com.senoritasaudi.storeutils.StoreManager;
import com.senoritasaudi.viewmodels.MainViewModel;
import com.senoritasaudi.views.baseviews.BaseFragmentWithViewModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class BarcodeFragment extends BaseFragmentWithViewModel<MainViewModel, FragmentBarcodeBinding> implements QRCodeReaderView.OnQRCodeReadListener {

    private static final String TAG = "BarcodeFragment";

    public BarcodeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = super.onCreateView(inflater,container,savedInstanceState);
        if (getViewModel().containsUser()) {
            getFragmentBinding().qrdecoderview.setOnQRCodeReadListener(this);
            getFragmentBinding().qrdecoderview.setQRDecodingEnabled(true);
            getFragmentBinding().qrdecoderview.setTorchEnabled(true);
            getFragmentBinding().qrdecoderview.setAutofocusInterval(2000L);
            getFragmentBinding().qrdecoderview.setBackCamera();
        } else {
            Toast.makeText(mContext, getString(R.string.please_login), Toast.LENGTH_SHORT).show();
        }

        if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, 5);
        }

        return v;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 5: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getFragmentBinding().qrdecoderview.setVisibility(View.GONE);
                    getFragmentBinding().qrdecoderview.setVisibility(View.VISIBLE);
                } else {
                    Toast.makeText(mContext, R.string.permission_denied, Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_barcode;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getViewModel().containsUser()) {
            getFragmentBinding().qrdecoderview.startCamera();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (getViewModel().containsUser()) {
            getFragmentBinding().qrdecoderview.stopCamera();
        }
    }

    @Override
    protected MainViewModel initialiseViewModel() {
        return new ViewModelProvider(mActivity).get(MainViewModel.class);
    }

    @Override
    public void onQRCodeRead(String text, PointF[] points) {
        getViewModel().confirmRequest(text.trim()).observe(mActivity, new Observer<QRCodeResponse>() {
            @Override
            public void onChanged(QRCodeResponse qrCodeResponse) {
                if (qrCodeResponse != null) {
                    if (StoreManager.getAppLanguage(mContext).equals("ar")) {
                        Toast.makeText(mContext, qrCodeResponse.getMessageAr(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(mContext, qrCodeResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    if (qrCodeResponse.getPoints() != null) {
                        UserModel userModel = getViewModel().getUser();
                        userModel.setPoints(String.valueOf(qrCodeResponse.getPoints()));
                        getViewModel().saveUser(userModel);
                    }
                }
            }
        });
        getFragmentBinding().pointsOverlayView.setPoints(points);
        getFragmentBinding().qrdecoderview.stopCamera();
    }
}
