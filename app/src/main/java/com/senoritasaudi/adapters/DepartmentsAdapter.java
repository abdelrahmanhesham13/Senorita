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
import com.senoritasaudi.databinding.ListDepartmentItemForPageBinding;
import com.senoritasaudi.events.OnItemClicked;
import com.senoritasaudi.models.DepartmentModel;

import java.util.ArrayList;
import java.util.List;

public class DepartmentsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<DepartmentModel> mDepartmentModels;
    private Context mContext;
    private OnItemClicked onItemClicked;
    private int type;

    public DepartmentsAdapter(Context context , OnItemClicked onItemClicked , int type) {
        this.mContext = context;
        this.onItemClicked = onItemClicked;
        mDepartmentModels = new ArrayList<>();
        this.type = type;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (type == 1) {
            ListDepartmentItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.list_department_item, parent, false);
            return new DepartmentViewHolder(binding);
        } else {
            ListDepartmentItemForPageBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.list_department_item_for_page, parent, false);
            return new DepartmentForPagerViewHolder(binding);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (type == 1) {
            DepartmentViewHolder departmentImage = (DepartmentViewHolder)holder;
            Glide.with(mContext)
                    .load(mDepartmentModels.get(position).getImage())
                    .placeholder(R.drawable.im_placeholder)
                    .error(R.drawable.im_placeholder)
                    .into(departmentImage.listDepartmentItemBinding.departmentImage);
        } else {
            DepartmentForPagerViewHolder departmentForPagerViewHolder = (DepartmentForPagerViewHolder)holder;
            Glide.with(mContext)
                    .load(mDepartmentModels.get(position).getImage())
                    .placeholder(R.drawable.im_placeholder)
                    .error(R.drawable.im_placeholder)
                    .into(departmentForPagerViewHolder.listDepartmentItemForPageBinding.departmentImage);
            departmentForPagerViewHolder.listDepartmentItemForPageBinding.departmentName.setText(mDepartmentModels.get(position).getName());
        }
    }

    public void addAll(List<DepartmentModel> models) {
        mDepartmentModels.addAll(models);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mDepartmentModels.size();
    }

    class DepartmentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ListDepartmentItemBinding listDepartmentItemBinding;

        public DepartmentViewHolder(ListDepartmentItemBinding binding) {
            super(binding.getRoot());
            this.listDepartmentItemBinding = binding;
            binding.getRoot().setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onItemClicked.onItemClicked(getAdapterPosition());
        }
    }

    public int getDepartmentIdForPosition(int position) {
        return Integer.parseInt(mDepartmentModels.get(position).getId());
    }

    class DepartmentForPagerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ListDepartmentItemForPageBinding listDepartmentItemForPageBinding;

        public DepartmentForPagerViewHolder(ListDepartmentItemForPageBinding binding) {
            super(binding.getRoot());
            this.listDepartmentItemForPageBinding = binding;
            binding.getRoot().setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onItemClicked.onItemClicked(getAdapterPosition());
        }
    }
}
