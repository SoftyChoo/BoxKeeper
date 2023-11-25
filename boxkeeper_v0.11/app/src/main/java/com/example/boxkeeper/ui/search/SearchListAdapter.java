package com.example.boxkeeper.ui.search;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.boxkeeper.R;
import com.example.boxkeeper.databinding.SearchRecyclerviewItemBinding;
import com.example.boxkeeper.data.api.TrackingDetail;

public class SearchListAdapter extends ListAdapter<TrackingDetail, SearchListAdapter.ViewHolder> {

    protected SearchListAdapter() {
        super(new DiffUtil.ItemCallback<TrackingDetail>() {
            @Override
            public boolean areItemsTheSame(@NonNull TrackingDetail oldItem, @NonNull TrackingDetail newItem) {
                return oldItem.getTimeString().equals(newItem.getTimeString());
            }

            @Override
            public boolean areContentsTheSame(@NonNull TrackingDetail oldItem, @NonNull TrackingDetail newItem) {
                return oldItem.equals(newItem);
            }
        });
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(
                SearchRecyclerviewItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TrackingDetail item = getItem(position);
        holder.bind(item);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final SearchRecyclerviewItemBinding binding;

        public ViewHolder(SearchRecyclerviewItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(TrackingDetail item) {
            binding.tvAddress.setText(item.getWhere());
            binding.tvState.setText(item.getKind());
            binding.tvTime.setText(item.getTimeString());
            if(getAdapterPosition() == 0){
                binding.imageView.setImageResource(R.drawable.ic_dot_fill);
            }
            //telno는 기사님 전화번호
        }
    }
}
