package com.senoritasaudi.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.senoritasaudi.R;
import com.senoritasaudi.databinding.ListOfferItemBinding;
import com.senoritasaudi.databinding.ListReservationItemBinding;
import com.senoritasaudi.events.OnItemClicked;
import com.senoritasaudi.models.RequestModel;

import java.util.ArrayList;

public class ReservationsAdapter extends RecyclerView.Adapter<ReservationsAdapter.ReservationViewHolder> {

    private ArrayList<RequestModel> requestModels;
    private Context mContext;
    OnItemClicked mOnItemClicked;
    String username;
    OnRateClicked onRateClicked;

    public ReservationsAdapter(Context context , OnItemClicked onItemClicked , String username , OnRateClicked onRateClicked) {
        this.mContext = context;
        this.mOnItemClicked = onItemClicked;
        requestModels = new ArrayList<>();
        this.username = username;
        this.onRateClicked = onRateClicked;
    }

    @NonNull
    @Override
    public ReservationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListReservationItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),R.layout.list_reservation_item,parent,false);
        return new ReservationViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ReservationViewHolder holder, int position) {
        holder.listReservationItemBinding.clinicName.setText(requestModels.get(position).getClinicName());
        try {
            holder.listReservationItemBinding.rating.setRating(Float.parseFloat(requestModels.get(position).getRate()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        holder.listReservationItemBinding.reservationNumber.setText(String.format("%s : %s", mContext.getString(R.string.reservation_number), requestModels.get(position).getId()));
        holder.listReservationItemBinding.date.setText(requestModels.get(position).getSelectedDate() + "\n" + requestModels.get(position).getSelectedTime());
        holder.listReservationItemBinding.textView21.setText("رقم العرض : " + requestModels.get(position).getOfferId());
        Glide.with(mContext)
                .load(requestModels.get(position).getClinicImage())
                .placeholder(R.drawable.im_placeholder)
                .error(R.drawable.im_placeholder)
                .into(holder.listReservationItemBinding.imageView30);
        switch (requestModels.get(position).getStatus()){
            case "0":
                holder.listReservationItemBinding.confirmationStatus.setText(R.string.waiting_confirmation);
                holder.listReservationItemBinding.confirmationStatus.setTextColor(Color.parseColor("#C793C8"));
                holder.listReservationItemBinding.circle.setImageResource(R.drawable.bg_circle_waiting);
                break;
            case "1":
                holder.listReservationItemBinding.confirmationStatus.setText(R.string.confirmed);
                holder.listReservationItemBinding.confirmationStatus.setTextColor(Color.parseColor("#2392A0"));
                holder.listReservationItemBinding.circle.setImageResource(R.drawable.bg_circle_confirmed);
                break;
            case "2":
                holder.listReservationItemBinding.confirmationStatus.setText(R.string.visit_completed);
                holder.listReservationItemBinding.confirmationStatus.setTextColor(Color.parseColor("#8A2332"));
                holder.listReservationItemBinding.circle.setImageResource(R.drawable.bg_circle_visit_completed);
                break;
            case "3":
                holder.listReservationItemBinding.confirmationStatus.setText(R.string.visit_not_completed);
                holder.listReservationItemBinding.confirmationStatus.setTextColor(Color.parseColor("#C87938"));
                holder.listReservationItemBinding.circle.setImageResource(R.drawable.bg_circle_visit_not_completed);
                break;
            case "4":
                holder.listReservationItemBinding.confirmationStatus.setText(R.string.canceled);
                holder.listReservationItemBinding.confirmationStatus.setTextColor(Color.parseColor("#A0171E"));
                holder.listReservationItemBinding.circle.setImageResource(R.drawable.bg_circle_canceled);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return requestModels.size();
    }

    public void addRequests(ArrayList<RequestModel> requestModels) {
        this.requestModels.addAll(requestModels);
        notifyDataSetChanged();
    }

    public void removeAll() {
        this.requestModels.clear();
    }

    public interface OnRateClicked {
        void onRateClicked(String reservationId);
    }

    class ReservationViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ListReservationItemBinding listReservationItemBinding;

        public ReservationViewHolder(ListReservationItemBinding listReservationItemBinding) {
            super(listReservationItemBinding.getRoot());
            listReservationItemBinding.getRoot().setOnClickListener(this);
            this.listReservationItemBinding = listReservationItemBinding;
            listReservationItemBinding.imageView34.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Float.parseFloat(requestModels.get(getAdapterPosition()).getRate()) == 0 && requestModels.get(getAdapterPosition()).getStatus().equals("2")) {
                        onRateClicked.onRateClicked(requestModels.get(getAdapterPosition()).getId());
                    }
                }
            });
        }

        @Override
        public void onClick(View v) {
            mOnItemClicked.onItemClicked(getAdapterPosition());
        }
    }
}
