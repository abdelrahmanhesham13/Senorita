package com.senoritasaudi.views;

import android.content.Context;
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
import com.senoritasaudi.adapters.NotificationsAdapter;
import com.senoritasaudi.databinding.FragmentNotificationBinding;
import com.senoritasaudi.models.NotificationModel;
import com.senoritasaudi.models.responseModels.NotificationResponseModel;
import com.senoritasaudi.viewmodels.MainViewModel;
import com.senoritasaudi.viewmodels.NotificationsViewModel;
import com.senoritasaudi.viewmodels.factory.ViewModelFactory;
import com.senoritasaudi.views.baseviews.BaseFragmentWithViewModel;
import com.senoritasaudi.views.baseviews.BaseFragmentWithoutViewModel;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class NotificationFragment extends BaseFragmentWithViewModel<NotificationsViewModel,FragmentNotificationBinding> {

    NotificationsAdapter notificationsAdapter;

    public NotificationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = super.onCreateView(inflater,container,savedInstanceState);
        notificationsAdapter = new NotificationsAdapter(mContext);
        getFragmentBinding().notificationsRecycler.setHasFixedSize(true);
        getFragmentBinding().notificationsRecycler.setLayoutManager(new LinearLayoutManager(mContext));
        getFragmentBinding().notificationsRecycler.setAdapter(notificationsAdapter);
        getViewModel().getNotificationResponseModelLiveData().observe(mActivity, new Observer<NotificationResponseModel>() {
            @Override
            public void onChanged(NotificationResponseModel notificationResponseModel) {
                getFragmentBinding().notificationProgress.setVisibility(View.GONE);
                if (notificationResponseModel != null) {
                    notificationsAdapter.addNotifications((ArrayList<NotificationModel>) notificationResponseModel.getNotifications());
                    if (notificationResponseModel.getCount() == 0) {
                        Toast.makeText(mContext, R.string.no_notifications, Toast.LENGTH_LONG).show();
                    }
                } else if (!getViewModel().containsUser()) {
                    Toast.makeText(mContext, R.string.please_login, Toast.LENGTH_SHORT).show();
                }
            }
        });

        return v;
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_notification;
    }

    @Override
    protected NotificationsViewModel initialiseViewModel() {
        return new ViewModelProvider(mActivity).get(NotificationsViewModel.class);
    }
}
