package com.example.boxkeeper.data.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.boxkeeper.ui.list.model.ImageModel;
import com.example.boxkeeper.ui.list.ImageRepository;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Collections;
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
//                    String date = fileName.substring(4, 12);
//                    String time = fileName.substring(13, 19);

                    String date = fileName.substring(0, 4) + fileName.substring(5, 7) + fileName.substring(8, 10);
                    String time = fileName.substring(11, 13) + fileName.substring(14, 16) + fileName.substring(17, 19);
//                    2023-11-30_21-28-33

                    ImageModel imageModel = new ImageModel(uri.toString(), date, time);
                    imageList.add(imageModel);

                    // If this is the last item, update the LiveData
                    if (imageList.size() == listResult.getItems().size()) {
                        Collections.reverse(imageList);
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
}
