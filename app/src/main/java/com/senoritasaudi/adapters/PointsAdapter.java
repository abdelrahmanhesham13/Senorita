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
import com.senoritasaudi.databinding.ListPointItemBinding;
import com.senoritasaudi.models.NotificationModel;
import com.senoritasaudi.models.PointModel;
import com.senoritasaudi.storeutils.StoreManager;

import java.util.ArrayList;

public class PointsAdapter extends RecyclerView.Adapter<PointsAdapter.PointViewHolder> {

    private ArrayList<PointModel> pointModels;
    private Context mContext;
    OnExchangeClick onExchangeClick;

    public PointsAdapter(Context context , OnExchangeClick onExchangeClick) {
        this.mContext = context;
        pointModels = new ArrayList<>();
        this.onExchangeClick = onExchangeClick;
    }

    public interface OnExchangeClick {
        void onExchangeClicked(PointModel pointModel);
    }

    @NonNull
    @Override
    public PointViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListPointItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.list_point_item,parent,false);
        return new PointViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PointViewHolder holder, int position) {
        if (StoreManager.getAppLanguage(mContext).equals("ar")) {
            holder.listPointItemBinding.codeText.setText(pointModels.get(position).getNameAr());
        } else {
            holder.listPointItemBinding.codeText.setText(pointModels.get(position).getName());
        }
    }

    public void addPoints(ArrayList<PointModel> pointModels) {
        this.pointModels.addAll(pointModels);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return pointModels.size();
    }

    class PointViewHolder extends RecyclerView.ViewHolder {

        ListPointItemBinding listPointItemBinding;

        PointViewHolder(ListPointItemBinding binding) {
            super(binding.getRoot());
            this.listPointItemBinding = binding;
            binding.nextButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onExchangeClick.onExchangeClicked(pointModels.get(getAdapterPosition()));
                }
            });
        }
    }
}
