package com.example.boxkeeper.ui.call;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.boxkeeper.R;
import com.example.boxkeeper.databinding.ActivityCallBinding;
import com.example.boxkeeper.ui.call.edit.CallEditDialogFragment;
import com.example.boxkeeper.ui.call.edit.CallInputType;
import com.example.boxkeeper.ui.call.model.CallModel;
import com.example.boxkeeper.ui.common.Key;
import com.example.boxkeeper.ui.search.SearchActivity;
import com.example.boxkeeper.ui.common.SlideKey;
import com.example.boxkeeper.ui.common.Utils;
import com.example.boxkeeper.ui.list.ImageListActivity;

import java.util.List;

public class CallActivity extends AppCompatActivity {

    private String TAG = "CallActivity";

    private CallSharedViewModel viewModel;

    private CallEditDialogFragment callEditFragment = new CallEditDialogFragment();
    // ViewModelProvider에 Fragment(this)를 전달하여 ViewModel 생성

    private final CallListAdapter listAdapter = new CallListAdapter(
            // onClick
            (item, adapterPosition) -> {
                if (callEditFragment.isAdded()) {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    transaction.remove(callEditFragment);
                    transaction.commit();
                    callEditFragment = new CallEditDialogFragment();
                }

                Bundle args = new Bundle();
                args.putString(Key.INPUT_TYPE, CallInputType.EDIT.name());
                args.putInt(Key.INPUT_POSITION, adapterPosition);
                args.putParcelable(Key.INPUT_MODEL, item);
                callEditFragment.setArguments(args);
                callEditFragment.show(getSupportFragmentManager(), "SampleDialog");

                viewModel.setCallItem(item);
            },
            // onLongClick
            item -> {
                Utils.callPhoneNumber(this, item.getPhoneNumber());
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCallBinding binding = ActivityCallBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(CallSharedViewModel.class);

        initTab(binding);
        initView(binding);
        initViewModel(binding);
    }

    private void initViewModel(ActivityCallBinding binding) {
        viewModel.callList.observe(this, new Observer<List<CallModel>>() {
            @Override
            public void onChanged(List<CallModel> callModels) {
                Toast.makeText(getBaseContext(), "옵저빙", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "listObserve : " + callModels);
                listAdapter.submitList(callModels);
                listAdapter.notifyDataSetChanged();
            }
        });
    }

    private void initView(ActivityCallBinding binding) {
        binding.rvCallList.setLayoutManager(new LinearLayoutManager(this));
        binding.rvCallList.setAdapter(listAdapter);

        binding.fabCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callEditFragment.isAdded()) {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    transaction.remove(callEditFragment);
                    transaction.commit();
                    callEditFragment = new CallEditDialogFragment();
                }

                Bundle args = new Bundle();
                args.putString(Key.INPUT_TYPE, CallInputType.ADD.name());
                callEditFragment.setArguments(args);
                callEditFragment.show(getSupportFragmentManager(), "SampleDialog");

                viewModel.setCallItem(new CallModel(" ", " ", " "));
            }
        });
    }

    public void onBackPressed() {
        super.onBackPressed();

        if (isFinishing()) {
            // back 버튼으로 화면 종료가 야기되면 동작한다.
            overridePendingTransition(R.anim.from_left_enter, R.anim.to_right_exit);
        }
    }

    private void initTab(ActivityCallBinding binding) {

        String slideKey = getIntent().getStringExtra(SlideKey.SLIDE_KEY);

        if (slideKey.equals(SlideKey.SLIDE_RIGHT)) {
            overridePendingTransition(R.anim.from_right_enter, R.anim.to_left_exit);
        } else if (slideKey.equals(SlideKey.SLIDE_LEFT)) {
            overridePendingTransition(R.anim.from_left_enter, R.anim.to_right_exit);
        }
        binding.btnHomeCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        binding.ivCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        binding.btnSearchCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CallActivity.this, SearchActivity.class);
                intent.putExtra(SlideKey.SLIDE_KEY, SlideKey.SLIDE_RIGHT);
                startActivity(intent);
                finish();
            }
        });
        binding.btnListCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CallActivity.this, ImageListActivity.class);
                intent.putExtra(SlideKey.SLIDE_KEY, SlideKey.SLIDE_RIGHT);
                startActivity(intent);
                finish();
            }
        });
    }


}