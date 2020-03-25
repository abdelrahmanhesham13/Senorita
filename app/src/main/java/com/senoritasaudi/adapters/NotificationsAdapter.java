package com.senoritasaudi.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.senoritasaudi.R;
import com.senoritasaudi.databinding.ListNotificationItemBinding;
import com.senoritasaudi.models.NotificationModel;

import java.util.ArrayList;

public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.NotificationViewHolder>  {

    private ArrayList<NotificationModel> notificationModels;
    private Context mContext;

    public NotificationsAdapter(Context context) {
        this.mContext = context;
        notificationModels = new ArrayList<>();
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListNotificationItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),R.layout.list_notification_item,parent,false);
        return new NotificationViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
        holder.listNotificationItemBinding.textView8.setText(notificationModels.get(position).getTitle());
        holder.listNotificationItemBinding.textView15.setText(notificationModels.get(position).getMessage());
        holder.listNotificationItemBinding.time.setText(notificationModels.get(position).getDate());
    }

    @Override
    public int getItemCount() {
        return notificationModels.size();
    }

    public void addNotifications(ArrayList<NotificationModel> notificationModels) {
        this.notificationModels.addAll(notificationModels);
        notifyDataSetChanged();
    }

    class NotificationViewHolder extends RecyclerView.ViewHolder {

        ListNotificationItemBinding listNotificationItemBinding;

        public NotificationViewHolder(ListNotificationItemBinding binding) {
            super(binding.getRoot());
            this.listNotificationItemBinding = binding;
        }
    }
}
