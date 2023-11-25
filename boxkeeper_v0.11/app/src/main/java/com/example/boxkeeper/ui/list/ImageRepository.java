package com.example.boxkeeper.ui.list;

import androidx.lifecycle.LiveData;

import java.util.List;

public interface ImageRepository {
    LiveData<List<ImageModel>> getImageList();
}
