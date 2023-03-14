package com.example.sportapp.ui.ShowRecord;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ShowRecordViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ShowRecordViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is notifications fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}