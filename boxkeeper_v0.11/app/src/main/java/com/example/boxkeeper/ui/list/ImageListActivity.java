package com.example.boxkeeper.ui.list;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.boxkeeper.R;
import com.example.boxkeeper.data.repository.ImageRepositoryImpl;
import com.example.boxkeeper.databinding.ActivityImageListBinding;
import com.example.boxkeeper.ui.call.CallActivity;
import com.example.boxkeeper.ui.list.detail.ImageDetailDialogFragment;
import com.example.boxkeeper.ui.search.SearchActivity;
import com.example.boxkeeper.ui.common.SlideKey;

public class ImageListActivity extends AppCompatActivity {

    private ImageViewModel viewModel;

    private final ImageListAdapter listAdapter = new ImageListAdapter(
            // onClick
            (item, adapterPosition) -> {
                ImageDetailDialogFragment dialogFragment = ImageDetailDialogFragment.newInstance(item);
                dialogFragment.show(getSupportFragmentManager(), "SampleDialog");
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityImageListBinding binding = ActivityImageListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initTab(binding);
        initView(binding);
        initViewModel(binding);
    }

    private void initView(ActivityImageListBinding binding) {

        ImageRepository repository = new ImageRepositoryImpl();
        viewModel = new ViewModelProvider(this, new ImageViewModelFactory(repository)).get(ImageViewModel.class);

        binding.rvImages.setLayoutManager(new LinearLayoutManager(this));
        binding.rvImages.setAdapter(listAdapter);

        binding.btnRefresh.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                viewModel.loadImageList();{
                    binding.rvImages.smoothScrollToPosition(0);
                };
                Toast.makeText(getBaseContext(), "새로고침 되었습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initViewModel(ActivityImageListBinding binding) {
        viewModel.getImageListLiveData().observe(this, imageModels -> {
            listAdapter.submitList(imageModels);
            binding.progressBar.setVisibility(View.GONE);
        });
    }

    public void onBackPressed() {
        super.onBackPressed();

        if (isFinishing()) {
            // back 버튼으로 화면 종료가 야기되면 동작한다.
            overridePendingTransition(R.anim.from_left_enter, R.anim.to_right_exit);
        }
    }

    private void initTab(ActivityImageListBinding binding) {


        String slideKey = getIntent().getStringExtra(SlideKey.SLIDE_KEY);

        if (slideKey.equals(SlideKey.SLIDE_RIGHT)) {
            overridePendingTransition(R.anim.from_right_enter, R.anim.to_left_exit);
        } else if (slideKey.equals(SlideKey.SLIDE_LEFT)) {
            overridePendingTransition(R.anim.from_left_enter, R.anim.to_right_exit);
        }
        binding.btnHomeList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        binding.btnCallList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ImageListActivity.this, CallActivity.class);
                intent.putExtra(SlideKey.SLIDE_KEY, SlideKey.SLIDE_LEFT);
                startActivity(intent);
                finish();
            }
        });
        binding.btnSearchList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ImageListActivity.this, SearchActivity.class);
                intent.putExtra(SlideKey.SLIDE_KEY, SlideKey.SLIDE_LEFT);
                startActivity(intent);
                finish();
            }
        });
        binding.btnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
            }
        });
    }
}