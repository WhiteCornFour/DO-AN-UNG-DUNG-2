package com.example.doanungdung2.View;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.doanungdung2.Model.Question;
import com.example.doanungdung2.Model.SharedViewModel;
import com.example.doanungdung2.R;

import java.util.ArrayList;

public class Admin_Question_True_False_Fragment extends Fragment {

    private static final String[] ANSWER_OPTIONS = {"True", "False"};
    private Spinner spinnerCorrectAnswer;
    private SharedViewModel sharedViewModel;

    public Admin_Question_True_False_Fragment() {
        // Required empty public constructor
    }

    public static Admin_Question_True_False_Fragment newInstance(String param1, String param2) {
        Admin_Question_True_False_Fragment fragment = new Admin_Question_True_False_Fragment();
        Bundle args = new Bundle();
        args.putString("param1", param1);
        args.putString("param2", param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initialize the ViewModel
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_question_true_false, container, false);
        initializeViews(view);
        setupSpinner();
        observeViewModel();
        return view;
    }

    private void initializeViews(View view) {
        // Initialize the spinner for correct answer selection
        spinnerCorrectAnswer = view.findViewById(R.id.spinnerDapAnTFCH);
    }

    private void setupSpinner() {
        // Set up the spinner with answer options
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, ANSWER_OPTIONS);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCorrectAnswer.setAdapter(adapter);
    }

    private void observeViewModel() {
        // Observe selected question from the ViewModel
        sharedViewModel.getSelectedQuestion().observe(getViewLifecycleOwner(), this::updateSpinnerSelection);
    }

    private void updateSpinnerSelection(Question question) {
        // Update the spinner selection based on the correct answer
        if (question != null) {
            int position = question.getDapAn().equals("True") ? 0 : 1;
            spinnerCorrectAnswer.setSelection(position);
        }
    }

    public String getTrueFalseData() {
        return spinnerCorrectAnswer.getSelectedItem().toString();
    }
}
