package com.mobis.telaPrincipal.ui.Viagem;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ViagemViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public ViagemViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is slideshow fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}