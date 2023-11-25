package com.example.boxkeeper.data.repository;

import android.net.Uri;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.boxkeeper.ui.list.ImageModel;
import com.example.boxkeeper.ui.list.ImageRepository;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;
public class ImageRepositoryImpl implements ImageRepository {

    private final FirebaseStorage storage = FirebaseStorage.getInstance();
    private final MutableLiveData<List<ImageModel>> imageListLiveData = new MutableLiveData<>();

    @Override
    public LiveData<List<ImageModel>> getImageList() {
        StorageReference storageRef = storage.getReference().child("Images/");

        storageRef.listAll().addOnSuccessListener(listResult -> {
            List<ImageModel> imageList = new ArrayList<>();

            for (StorageReference item : listResult.getItems()) {
                // Use getDownloadUrl to obtain the URL
                item.getDownloadUrl().addOnSuccessListener(uri -> {
                    // Extract date and time from the file name
                    String fileName = item.getName();
                    String date = fileName.substring(4, 12);
                    String time = fileName.substring(13, 19);

                    ImageModel imageModel = new ImageModel(uri.toString(), date, time);
                    imageList.add(imageModel);

                    // If this is the last item, update the LiveData
                    if (imageList.size() == listResult.getItems().size()) {
                        imageListLiveData.postValue(imageList);
                    }
                }).addOnFailureListener(e -> {
                    // Handle failure to get download URL
                    Log.e("ImageRepositoryImpl", "Error getting download URL", e);
                });
            }
        }).addOnFailureListener(e -> {
            // Handle failure to list items
            Log.e("ImageRepositoryImpl", "Error listing items", e);
        });

        return imageListLiveData;
    }


//    @Override
//    public LiveData<List<ImageModel>> getImageList() {
//        // Replace "Images/" with your actual Firebase Storage path
//        StorageReference storageRef = storage.getReference().child("Images/");
//
//        storageRef.listAll().addOnSuccessListener(listResult -> {
//            List<ImageModel> imageList = new ArrayList<>();
//
//            for (StorageReference item : listResult.getItems()) {
//                // Extract date and time from the file name
//                String fileName = item.getName();
//                String date = fileName.substring(4, 12);
//                String time = fileName.substring(13, 19);
//
//                ImageModel imageModel = new ImageModel(item.getPath(), date, time);
//                imageList.add(imageModel);
//            }
//
//            imageListLiveData.postValue(imageList);
//        });
//
//        return imageListLiveData;
//    }
}

//    Uri uri = storageRef.getDownloadUrl().getResult();

//
//    // 파일 이름에서 날짜를 추출하는 메서드
//    private String extractDateFromFileName(String fileName) {
//        // 예제: "Ras_20231114_212137.jpg"
//        String[] parts = fileName.split("_");
//        if (parts.length >= 2) {
//            // parts[1]은 "20231114"이 됩니다.
//            return parts[1];
//        }
//        return "Unknown Date";
//    }
//
//    // 파일 이름에서 시간를 추출하는 메서드
//    private String extractTimeFromFileName(String fileName) {
//        // 예제: "Ras_20231115_004229.jpg"
//        String[] parts = fileName.split("_");
//        if (parts.length >= 3) {
//            // parts[2]는 "004229"이 됩니다.
//            return parts[2];
//        }
//        return "Unknown Time";
//    }
//}