package com.example.boxkeeper.call;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.boxkeeper.databinding.CallRecyclerviewItemBinding;

public class CallListAdapter extends ListAdapter<CallModel, CallListAdapter.ViewHolder> {

    private final ArenaClickListener onClick;

    protected CallListAdapter(ArenaClickListener onClick) {
        super(new DiffUtil.ItemCallback<CallModel>() {
            @Override
            public boolean areItemsTheSame(@NonNull CallModel oldItem, @NonNull CallModel newItem) {
                return oldItem.getPhoneNumber().equals(newItem.getPhoneNumber());
            }

            @Override
            public boolean areContentsTheSame(@NonNull CallModel oldItem, @NonNull CallModel newItem) {
                return oldItem.equals(newItem);
            }
        });
        this.onClick = onClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(
                CallRecyclerviewItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false),
                onClick
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CallModel item = getItem(position);
        holder.bind(item);
    }

    public interface ArenaClickListener {
        void onItemClick(CallModel item);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final CallRecyclerviewItemBinding binding;

        public ViewHolder(CallRecyclerviewItemBinding binding, final ArenaClickListener onClick) {
            super(binding.getRoot());
            this.binding = binding;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClick.onItemClick(getItem(getAdapterPosition()));
                }
            });
        }

        public void bind(CallModel item) {
            binding.title.setText(item.getTitle());
            binding.description.setText(item.getDescription());
        }
    }
}
