package com.example.doanungdung2.Model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SharedViewModel_AfterClickAnswer extends ViewModel {
    private MutableLiveData<String> selectedAnswer = new MutableLiveData<>();

    public LiveData<String> getSelectedAnswer() {
        return selectedAnswer;
    }

    public void setSelectedAnswer(String answer) {
        selectedAnswer.setValue(answer);
    }
}
