package com.example.doanungdung2.View;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.doanungdung2.Controller.ExercisesCategoryHandler;
import com.example.doanungdung2.Model.Question;
import com.example.doanungdung2.Model.SharedViewModel_Questions;
import com.example.doanungdung2.R;

/**
 * A Fragment that allows the admin to manage essay questions.
 */
public class Admin_Question_Essay_Fragment extends Fragment {

    private EditText edtDapAnDungTuLuan;
    private TextView tvTuLuanVaDBT;
    private ExercisesCategoryHandler exercisesCategoryHandler;
    private SharedViewModel_Questions sharedViewModel_questions;

    // Database constants
    private static final String DB_NAME = "AppHocTiengAnh";
    private static final int DB_VERSION = 1;

    public Admin_Question_Essay_Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedViewModel_questions = new ViewModelProvider(requireActivity()).get(SharedViewModel_Questions.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin_question_essay, container, false);
        initializeControls(view);
        exercisesCategoryHandler = new ExercisesCategoryHandler(getContext(), DB_NAME, null, DB_VERSION);

        // Observe the selected question from the SharedViewModel
        sharedViewModel_questions.getSelectedQuestion().observe(getViewLifecycleOwner(), this::updateUIWithQuestionData);

        return view;
    }

    private void initializeControls(View view) {
        edtDapAnDungTuLuan = view.findViewById(R.id.edtDapAnDungTuLuan);
        tvTuLuanVaDBT = view.findViewById(R.id.tvTuLuanVaDBT);
    }

    private void updateUIWithQuestionData(Question question) {
        if (question != null) {
            String maDangBaiTap = question.getMaDangBaiTap();
            String tenDangBaiTap = exercisesCategoryHandler.getExerciseCategoryNameByCode(maDangBaiTap);
            tvTuLuanVaDBT.setText("Dạng bài tập " + tenDangBaiTap);
            edtDapAnDungTuLuan.setText(question.getDapAn());
        }
    }

    public String getEssayData() {
        return edtDapAnDungTuLuan.getText().toString().trim();
    }
}
