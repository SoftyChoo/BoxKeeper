package com.example.boxkeeper.ui.list;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import com.example.boxkeeper.data.repository.ImageRepositoryImpl;
import com.google.firebase.storage.StorageReference;

public class ImageViewModelFactory implements ViewModelProvider.Factory {

    private final ImageRepository repository;

    public ImageViewModelFactory(ImageRepository repository) {
        this.repository = repository;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ImageViewModel.class)) {
            return (T) new ImageViewModel(repository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}