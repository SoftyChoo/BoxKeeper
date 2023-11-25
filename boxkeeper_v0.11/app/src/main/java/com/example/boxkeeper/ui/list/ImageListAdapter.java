package com.example.boxkeeper.ui.list;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.bumptech.glide.Glide;
import com.example.boxkeeper.databinding.ImageListRecyclerviewItemBinding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

public class ImageListAdapter extends ListAdapter<ImageModel, ImageListAdapter.ViewHolder> {

    private final ImageClickListener onClick;

    protected ImageListAdapter(ImageClickListener onClick) {
        super(new DiffUtil.ItemCallback<ImageModel>() {
            @Override
            public boolean areItemsTheSame(@NonNull ImageModel oldItem, @NonNull ImageModel newItem) {
                return oldItem.getImageUrl().equals(newItem.getImageUrl());
            }

            @Override
            public boolean areContentsTheSame(@NonNull ImageModel oldItem, @NonNull ImageModel newItem) {
                return oldItem.equals(newItem);
            }
        });
        this.onClick = onClick;
    }

    @NonNull
    @Override
    public ImageListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ImageListAdapter.ViewHolder(
                ImageListRecyclerviewItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false),
                onClick
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ImageListAdapter.ViewHolder holder, int position) {
        ImageModel item = getItem(position);
        holder.bind(item);
    }

    public interface ImageClickListener {
        void onItemClick(ImageModel item, int adapterPosition);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageListRecyclerviewItemBinding binding;

        public ViewHolder(ImageListRecyclerviewItemBinding binding, final ImageClickListener onClick) {
            super(binding.getRoot());
            this.binding = binding;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClick.onItemClick(getItem(getAdapterPosition()),getAdapterPosition());
                }
            });
        }

        public void bind(ImageModel item) {

            // Progress Bar 를 PlaceHolder로 사용
            CircularProgressDrawable circularProgressDrawable = new CircularProgressDrawable(itemView.getContext());
            circularProgressDrawable.setStrokeWidth(5f);
            circularProgressDrawable.setCenterRadius(30f);
            circularProgressDrawable.setColorSchemeColors(Color.BLACK);
            circularProgressDrawable.start();

            Glide.with(itemView.getContext())
                    .load(item.getImageUrl())
                    .placeholder(circularProgressDrawable)
                    .into(binding.ivImage);


            try {
                // 날짜, 시간 데이터 파싱
                SimpleDateFormat inputDateFormat = new SimpleDateFormat("MMddyy", Locale.getDefault());
                SimpleDateFormat inputTimeFormat = new SimpleDateFormat("HHmmss", Locale.getDefault());
                Date parsedDate = inputDateFormat.parse(item.getDate());
                Date parsedTime = inputTimeFormat.parse(item.getTime());

                // 날짜, 시간 데이터 포맷
                SimpleDateFormat outputDateFormat = new SimpleDateFormat("yy년 MM월 dd일", Locale.getDefault());
                SimpleDateFormat outputTimeFormat = new SimpleDateFormat("HH시 mm분 ss초", Locale.getDefault());
                String formattedDate = outputDateFormat.format(parsedDate);
                String formattedTime = outputTimeFormat.format(parsedTime);

                binding.tvDateData.setText(formattedDate);
                binding.tvTimeData.setText(formattedTime);
            }catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }
}