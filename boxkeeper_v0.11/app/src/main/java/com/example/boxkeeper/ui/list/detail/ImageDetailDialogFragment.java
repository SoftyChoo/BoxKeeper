package com.example.boxkeeper.ui.list.detail;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.boxkeeper.databinding.FragmentImageDetailBinding;
import com.example.boxkeeper.ui.list.model.ImageModel;
import com.example.boxkeeper.ui.util.DateTimeUtils;

import java.util.Objects;

public class ImageDetailDialogFragment extends DialogFragment {

    private FragmentImageDetailBinding binding; // View Binding 추가
    private static final String ARG_IMAGE_MODEL = "imageModel";

    public ImageDetailDialogFragment() {
        // Required empty public constructor
    }

    public static ImageDetailDialogFragment newInstance(ImageModel imageModel) {
        ImageDetailDialogFragment fragment = new ImageDetailDialogFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_IMAGE_MODEL,imageModel);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentImageDetailBinding.inflate(inflater, container, false);
        View view = binding.getRoot();


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(binding);
    }

    private void initView(FragmentImageDetailBinding binding) {
        // Dialog의 배경을 투명으로 설정
        Objects.requireNonNull(getDialog()).getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ImageModel imageModel = getArguments() != null ? getArguments().getParcelable(ARG_IMAGE_MODEL) : null;

        // Progress Bar 를 PlaceHolder로 사용
        CircularProgressDrawable circularProgressDrawable = new CircularProgressDrawable(getContext());
        circularProgressDrawable.setStrokeWidth(5f);
        circularProgressDrawable.setCenterRadius(30f);
        circularProgressDrawable.setColorSchemeColors(Color.BLACK);
        circularProgressDrawable.start();

        Glide.with(getContext())
                .load(imageModel.getImageUrl())
                .placeholder(circularProgressDrawable)
                .into(binding.ivImage);

        // parsing, formatting
        String formattedDate = DateTimeUtils.formatDate(imageModel.getDate());
        String formattedTime = DateTimeUtils.formatTime(imageModel.getTime());

        binding.tvDateData.setText(formattedDate);
        binding.tvTimeData.setText(formattedTime);

        binding.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDestroyView();
            }
        });
    }
}