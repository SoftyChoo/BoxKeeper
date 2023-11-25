package com.example.boxkeeper.ui.call;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.boxkeeper.databinding.CallRecyclerviewItemBinding;

public class CallListAdapter extends ListAdapter<CallModel, CallListAdapter.ViewHolder> {

    private final CallClickListener onClick;
    private final CallLongClickListener onLongClick;


    protected CallListAdapter(CallClickListener onClick,CallLongClickListener onLongClick) {
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
        this.onLongClick = onLongClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(
                CallRecyclerviewItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false),
                onClick,
                onLongClick
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CallModel item = getItem(position);
        holder.bind(item);
    }

    public interface CallClickListener {
        void onItemClick(CallModel item, int adapterPosition);
    }

    public interface CallLongClickListener{
        void onItemLongClick(CallModel item);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final CallRecyclerviewItemBinding binding;

        public ViewHolder(CallRecyclerviewItemBinding binding, final CallClickListener onClick,final CallLongClickListener onLongClick) {
            super(binding.getRoot());
            this.binding = binding;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClick.onItemClick(getItem(getAdapterPosition()),getAdapterPosition());
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    onLongClick.onItemLongClick(getItem(getAdapterPosition()));
                    return false;
                }
            });
        }

        public void bind(CallModel item) {
            String firstCharacter = item.getTitle().substring(0, 1);
            binding.titleText.setText(firstCharacter);
            binding.title.setText(item.getTitle());
//            String phoneNumber = "[" + item.getPhoneNumber() + "]";
//            binding.phoneNumber.setText(phoneNumber);
            binding.description.setText(item.getDescription());
        }
    }
}
