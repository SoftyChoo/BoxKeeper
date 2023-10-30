package com.example.boxkeeper.call;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CallSharedViewModel extends ViewModel {

    public static List<CallModel> commonList = new ArrayList<>(Arrays.asList(
            new CallModel("추민수", "[Box 3]00상가 건물주", "01028179282"),
            new CallModel("박수민", "[Box 1]00상가 경비원 ", "010"),
            new CallModel("김광오", "[Box 2]00오피스텔 관리인", "01036481285"),
            new CallModel("김건희", "[Box 4]00아파트 경비", "01091421163"),
            new CallModel("이진영", "[Box 4]00아파트 관리사무서", "01065889271")
    ));

    private String TAG = "CallSharedViewModel";

    public MutableLiveData<List<CallModel>> _callList = new MutableLiveData<>();
    public LiveData<List<CallModel>> callList = _callList;

    private MutableLiveData<CallModel> _callItem = new MutableLiveData<>();
    LiveData<CallModel> callItem = _callItem;

//    public CallSharedViewModel() {
//        // 더미 데이터 생성
//        List<CallModel> dummyData = new ArrayList<>();
//        dummyData.add(new CallModel("추민수", "상가 건물주", "111111"));
//        dummyData.add(new CallModel("박수민", "아파트 경비실", "222222"));
//        dummyData.add(new CallModel("김건희", "아파트 관리사무서", "333333"));
//
//        // MutableLiveData에 더미 데이터 설정
//        _callList.setValue(dummyData);
//    }


    public void setCallItem(CallModel item) {
        _callItem.setValue(item);
    }

    public void addToCallList(CallModel callModel) {
        Log.d(TAG, "addToCallList callModel num : " + callModel.getPhoneNumber());
        Log.d(TAG, "addToCallList callModel des : " + callModel.getDescription());
        Log.d(TAG, "addToCallList callModel title : " + callModel.getTitle());
        List<CallModel> callList = new ArrayList<>();
        callList = _callList.getValue(); // _callList 값 가져오기
        if (callList == null) {
            callList = new ArrayList<>(); // null일 경우 빈 리스트로 리턴
        }
        callList.add(callModel); // item 추가
        _callList.setValue(callList); // _callList에 데이터 바인딩
        Log.d(TAG, "addToCallList callList :" + _callList);
    }

    public void EditToCallList(CallModel callModel, Integer position) {
        List<CallModel> callList = _callList.getValue(); // _callList 값 가져오기

        if (callList != null && position >= 0 && position < callList.size()) {
            callList.set(position, callModel); // 지정된 위치(position)에 있는 데이터를 callModel로 변경
            _callList.setValue(callList); // _callList에 변경된 데이터 바인딩
        }
    }

}
