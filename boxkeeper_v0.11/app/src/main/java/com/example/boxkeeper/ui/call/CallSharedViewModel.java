package com.example.boxkeeper.ui.call;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.boxkeeper.ui.call.model.CallModel;

import java.util.ArrayList;
import java.util.List;

public class CallSharedViewModel extends ViewModel {
    private String TAG = "CallSharedViewModel";

    public MutableLiveData<List<CallModel>> _callList = new MutableLiveData<>();
    public LiveData<List<CallModel>> callList = _callList;

    private MutableLiveData<CallModel> _callItem = new MutableLiveData<>();
    LiveData<CallModel> callItem = _callItem;

    public CallSharedViewModel() {
        // 더미 데이터 생성
        List<CallModel> dummyData = new ArrayList<>();
        dummyData.add(new CallModel("추민수", "[Box 3]00상가 건물주", "01028179282"));
        dummyData.add( new CallModel("박수민", "[Box 1]00상가 경비원 ", "01000000000"));
        dummyData.add(new CallModel("김광오", "[Box 2]00오피스텔 관리인", "01036481285"));
        dummyData.add(new CallModel("김건희", "[Box 4]00아파트 경비", "01091421163"));
        dummyData.add(new CallModel("이진영", "[Box 4]00아파트 관리사무서", "01065889271"));

        // MutableLiveData에 더미 데이터 설정
        _callList.setValue(dummyData);
    }

    public void setCallItem(CallModel item) {
        _callItem.setValue(item);
    }

    public void addToCallList(CallModel callModel) {
        Log.d(TAG, "addToCallList callModel num : " + callModel.getPhoneNumber());
        Log.d(TAG, "addToCallList callModel des : " + callModel.getDescription());
        Log.d(TAG, "addToCallList callModel title : " + callModel.getTitle());
        List<CallModel> currentList;
        currentList = callList.getValue(); // callList 값 가져오기
        if (currentList == null) {
            currentList = new ArrayList<>(); // null일 경우 빈 리스트로 리턴
        }
        currentList.add(callModel); // item 추가
        _callList.setValue(currentList); // _callList에 데이터 바인딩
        Log.d(TAG, "addToCallList _callList :" + _callList.getValue());
        Log.d(TAG, "addToCallList _callList size :" + callList.getValue());
    }

    public void EditToCallList(CallModel callModel, Integer position) {
        List<CallModel> currentList = callList.getValue(); // callList 값 가져오기

        if (currentList != null && position >= 0 && position < currentList.size()) {
            currentList.set(position, callModel); // 지정된 위치(position)에 있는 데이터를 callModel로 변경
            _callList.setValue(currentList); // _callList에 변경된 데이터 바인딩
        }
    }

}
