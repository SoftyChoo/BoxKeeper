package com.example.boxkeeper.call;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.boxkeeper.MainActivity;
import com.example.boxkeeper.common.Key;
import com.example.boxkeeper.databinding.FragmentCallEditBinding;

import java.util.Objects;

public class CallEditFragment extends DialogFragment {
    private FragmentCallEditBinding binding; // View Binding 추가
    private CallSharedViewModel viewModel;


    private String TAG = "CallEditFragment";

    public static CallEditFragment newInstance() {
        CallEditFragment fragment = new CallEditFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this, new CallViewModelFactoty())
                .get(CallSharedViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // View Binding 초기화
        binding = FragmentCallEditBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        initView(binding);
        initViewModel(binding);

        return view;
    }

    private void initViewModel(FragmentCallEditBinding binding) {
        viewModel.callItem.observe(getViewLifecycleOwner(), new Observer<CallModel>() {
            @Override
            public void onChanged(CallModel callModel) {
                binding.etName.setText(callModel.getTitle());
                binding.etPhoneNumber.setText(callModel.getPhoneNumber());
                binding.etDescription.setText(callModel.getDescription());
            }
        });
    }

    private void initView(FragmentCallEditBinding binding) {

        // Dialog의 배경을 투명으로 설정
        Objects.requireNonNull(getDialog()).getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        String inputType = getArguments() != null ? getArguments().getString(Key.INPUT_TYPE) : null;
        Integer inputPosition = getArguments() != null ? getArguments().getInt(Key.INPUT_POSITION) : null;
        CallModel inputModel = getArguments() != null ? getArguments().getParcelable(Key.INPUT_MODEL) : null;

        if (inputType != null) {
            if (inputType.equals(CallInputType.ADD.name())) {
                binding.tvTitle.setText("연락처 추가");

            } else if (inputType.equals(CallInputType.EDIT.name())) {
                assert inputModel != null;
                binding.etName.setText(inputModel.getTitle());
                binding.etDescription.setText(inputModel.getDescription());
                binding.etPhoneNumber.setText(inputModel.getPhoneNumber());
                binding.tvTitle.setText("연락처 수정");
            }
        }

        binding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (binding.etName.getText().toString().isEmpty() || binding.etPhoneNumber.getText().toString().isEmpty() || binding.etDescription.getText().toString().isEmpty()) {
                        Toast.makeText(getContext(), "빈칸을 모두 채워주세요", Toast.LENGTH_SHORT).show();
                    } else {
                        if (inputType.equals(CallInputType.ADD.name())) {
                            addCallList(
                                    new CallModel(
                                            binding.etName.getText().toString(),
                                            binding.etDescription.getText().toString(),
                                            binding.etPhoneNumber.getText().toString()
                                    )
                            );
                            Log.d(TAG, "추가 로직");

                        } else if (inputType.equals(CallInputType.EDIT.name())) {
                            EditCallList(
                                    new CallModel(
                                            binding.etName.getText().toString(),
                                            binding.etDescription.getText().toString(),
                                            binding.etPhoneNumber.getText().toString()
                                    ),
                                    inputPosition
                            );
                            Log.d(TAG, "수정 로직");
                        }
                        onDestroyView();
                    }
                } catch (Exception e) {
                    Log.e(TAG, "Error: " + e.getMessage());
                }

            }
        });

        binding.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDestroyView();
            }
        });
    }


    private void addCallList(CallModel callModel) {
//        MainActivity.commonList.add(callModel);
        CallSharedViewModel.commonList.add(callModel);

//        viewModel.addToCallList(callModel);
    }

    private void EditCallList(CallModel callModel, Integer position) {
//        MainActivity.commonList.set(position,callModel);
        CallSharedViewModel.commonList.set(position, callModel);
        viewModel.EditToCallList(callModel, position);
    }


}
