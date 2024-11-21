
package com.example.doanungdung2.Model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SharedViewModel_Questions extends ViewModel {
    private final MutableLiveData<Question> selectedQuestion = new MutableLiveData<>();

    public void select(Question question) {
        selectedQuestion.setValue(question);
    }

    public LiveData<Question> getSelectedQuestion() {
        return selectedQuestion;
    }
}