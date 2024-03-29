package com.example.boxkeeper.ui.list;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.boxkeeper.ui.list.model.ImageModel;

import java.util.List;

public class ImageViewModel extends ViewModel {

    private final ImageRepository repository;
    private LiveData<List<ImageModel>> imageListLiveData;

    public ImageViewModel(ImageRepository repository) {
        this.repository = repository;
        this.imageListLiveData = repository.getImageList();
    }

    public void loadImageList(){
        imageListLiveData = repository.getImageList();
    }

    public LiveData<List<ImageModel>> getImageListLiveData() {
        return imageListLiveData;
    }
}

//public class ImageViewModel extends ViewModel {
//    private final ImageRepository repository;
//    private final MutableLiveData<List<ImageModel>> _imageItems = new MutableLiveData<>();
//    LiveData<List<ImageModel>> imageItems = _imageItems;
//
//    public ImageViewModel(ImageRepository repository) {
//        this.repository = repository;
//    }
//
//
//    public LiveData<List<ImageModel>> getImageModels() {
//        return imageItems;
//    }
//
//    public void loadImageModels() {
//        repository.getImageModels(new ImageModelsCallback() {
//            @Override
//            public void onSuccess(List<ImageModel> imageModels) {
//                _imageItems.setValue(imageModels);
//                Log.d("ImageViewModel", "Firebase Storage data: " + imageModels);
//            }
//
//            @Override
//            public void onFailure(Exception e) {
//                Log.e("ImageViewModel", "Error loading Firebase Storage data", e);
//            }
//        });
//    }
//}