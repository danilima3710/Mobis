package com.mobis.telaPrincipal.ui.Passageiro;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PassageiroViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public PassageiroViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}