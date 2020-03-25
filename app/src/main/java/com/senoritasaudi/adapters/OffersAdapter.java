package com.senoritasaudi.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.senoritasaudi.R;
import com.senoritasaudi.databinding.ListDepartmentItemBinding;
import com.senoritasaudi.databinding.ListOfferItemBinding;
import com.senoritasaudi.events.OnItemClicked;
import com.senoritasaudi.models.OfferModel;

import java.util.ArrayList;

public class OffersAdapter extends RecyclerView.Adapter<OffersAdapter.OfferViewHolder> {

    private ArrayList<OfferModel> offerModels;
    private Context mContext;
    OnItemClicked mOnItemClicked;

    public OffersAdapter(Context context , OnItemClicked onItemClicked) {
        this.mContext = context;
        this.mOnItemClicked = onItemClicked;
        offerModels = new ArrayList<>();
    }

    @NonNull
    @Override
    public OfferViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListOfferItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),R.layout.list_offer_item,parent,false);
        return new OfferViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull OfferViewHolder holder, int position) {
        Glide.with(mContext)
                .load(offerModels.get(position).getImage())
                .placeholder(R.drawable.im_placeholder)
                .error(R.drawable.im_placeholder)
                .into(holder.listOfferItemBinding.offerImage);
    }

    public void addOffers(ArrayList<OfferModel> offerModels) {
        this.offerModels.addAll(offerModels);
        notifyDataSetChanged();
    }

    public String getOfferIdForPosition(int position) {
        return offerModels.get(position).getId();
    }

    @Override
    public int getItemCount() {
        return offerModels.size();
    }

    class OfferViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ListOfferItemBinding listOfferItemBinding;

        public OfferViewHolder(ListOfferItemBinding listOfferItemBinding) {
            super(listOfferItemBinding.getRoot());
            this.listOfferItemBinding = listOfferItemBinding;
            listOfferItemBinding.getRoot().setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mOnItemClicked.onItemClicked(getAdapterPosition());
        }
    }
}
