package com.senoritasaudi.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.senoritasaudi.R;
import com.senoritasaudi.databinding.ListNotificationItemBinding;
import com.senoritasaudi.databinding.ListReviewItemBinding;
import com.senoritasaudi.models.NotificationModel;
import com.senoritasaudi.models.ReviewModel;

import java.util.ArrayList;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ReviewViewHolder> {

    private ArrayList<ReviewModel> reviewModels;
    private Context mContext;

    public ReviewsAdapter(Context context) {
        this.mContext = context;
        reviewModels = new ArrayList<>();
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListReviewItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.list_review_item,parent,false);
        return new ReviewViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        holder.listReviewItemBinding.comment.setText(reviewModels.get(position).getReview());
        holder.listReviewItemBinding.customerName.setText(reviewModels.get(position).getCustomerName());
        holder.listReviewItemBinding.rating.setRating(Float.parseFloat(reviewModels.get(position).getRate()));
    }

    @Override
    public int getItemCount() {
        return reviewModels.size();
    }

    public void addReviews(ArrayList<ReviewModel> reviewModels) {
        this.reviewModels.addAll(reviewModels);
        notifyDataSetChanged();
    }

    class ReviewViewHolder extends RecyclerView.ViewHolder {

        ListReviewItemBinding listReviewItemBinding;

        public ReviewViewHolder(ListReviewItemBinding binding) {
            super(binding.getRoot());
            this.listReviewItemBinding = binding;
        }
    }

}
