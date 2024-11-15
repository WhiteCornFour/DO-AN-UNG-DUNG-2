package com.example.doanungdung2.Model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ShareViewModel_Answer extends ViewModel {
    private final MutableLiveData<String> answerSelected = new MutableLiveData<>();
    public void setAnswer(String answer) { answerSelected.setValue(answer); }
    public LiveData<String> getAnswer () { return answerSelected; }
}
