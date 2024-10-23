
package com.example.doanungdung2.Model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.doanungdung2.Model.Question;

public class SharedViewModel extends ViewModel {
    private final MutableLiveData<Question> selectedQuestion = new MutableLiveData<>();

    public void select(Question question) {
        selectedQuestion.setValue(question);
    }

    public LiveData<Question> getSelectedQuestion() {
        return selectedQuestion;
    }
}