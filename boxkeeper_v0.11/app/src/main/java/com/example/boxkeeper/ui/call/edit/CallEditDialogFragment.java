package com.example.boxkeeper.ui.call.edit;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.boxkeeper.databinding.FragmentCallEditBinding;
import com.example.boxkeeper.ui.call.CallSharedViewModel;
import com.example.boxkeeper.ui.call.model.CallModel;
import com.example.boxkeeper.ui.common.Key;

import java.util.Objects;

public class CallEditDialogFragment extends DialogFragment {
    private FragmentCallEditBinding binding; // View Binding 추가
    private CallSharedViewModel viewModel;


    private String TAG = "CallEditFragment";

    public static CallEditDialogFragment newInstance() {
        CallEditDialogFragment fragment = new CallEditDialogFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(CallSharedViewModel.class);

//        viewModel = new ViewModelProvider(this, new CallViewModelFactoty())
//                .get(CallSharedViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // View Binding 초기화
        binding = FragmentCallEditBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(binding);
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
        viewModel.addToCallList(callModel);
    }

    private void EditCallList(CallModel callModel, Integer position) {
        viewModel.EditToCallList(callModel, position);
    }


}
