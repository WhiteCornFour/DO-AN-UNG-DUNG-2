package com.example.doanungdung2.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.doanungdung2.Controller.AssignmentHandler;
import com.example.doanungdung2.Controller.ExerciseHandler;
import com.example.doanungdung2.Model.Assignment;
import com.example.doanungdung2.Model.Exercise;
import com.example.doanungdung2.R;

public class User_Quiz_Result extends AppCompatActivity {
    private final static String DB_NAME = "AppHocTiengAnh";
    private final static int DB_VERSION = 1;
    ImageView imgBackToQuizListTR;
    TextView tvPointTestResult, tvCongratulationText, tvAssignmentName, tvAssignmentTotalRightAnswer, tvTotalTimeSpending, tvAttempt, tvGuideLineFooter;
    AssignmentHandler assignmentHandler;
    ExerciseHandler exerciseHandler;
    Assignment assignment = new Assignment();
    Exercise exercise = new Exercise();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_quiz_result);
        addControl();
        assignmentHandler = new AssignmentHandler(User_Quiz_Result.this, DB_NAME, null, DB_VERSION);
        exerciseHandler = new ExerciseHandler(User_Quiz_Result.this, DB_NAME, null, DB_VERSION);

        Intent intent = getIntent();
        String maBaiLam = intent.getStringExtra("maBaiLamTR");
        assignment = assignmentHandler.loadAssignmentResult(maBaiLam);

        setUpDataForTestResult();
        addEvent();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStack();
    }

    void addControl() {
        imgBackToQuizListTR = findViewById(R.id.imgBackToQuizListTR);
        tvPointTestResult = findViewById(R.id.tvPointTestResult);
        tvCongratulationText = findViewById(R.id.tvCongratulationText);
        tvAssignmentName = findViewById(R.id.tvAssignmentName);
        tvAssignmentTotalRightAnswer = findViewById(R.id.tvAssignmentTotalRightAnswer);
        tvTotalTimeSpending = findViewById(R.id.tvTotalTimeSpending);
        tvAttempt = findViewById(R.id.tvAttempt);
        tvGuideLineFooter = findViewById(R.id.tvGuideLineFooter);
    }

    void addEvent() {
        imgBackToQuizListTR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    void setUpDataForTestResult() {
        tvPointTestResult.setText(String.valueOf(assignment.getDiem()));
        if (assignment.getDiem() >= 9) {
            tvCongratulationText.setText("Outstanding Performance!");
            tvGuideLineFooter.setText("Your dedication is truly inspiring. Keep it up!");
        } else if (assignment.getDiem() >= 7) {
            tvCongratulationText.setText("Well Done!");
            tvGuideLineFooter.setText("You did a great job! Strive for even better results!");
        } else if (assignment.getDiem() >= 5) {
            tvCongratulationText.setText("Good Effort!");
            tvGuideLineFooter.setText("You’re on the right track. Keep improving!");
        } else if (assignment.getDiem() >= 3) {
            tvCongratulationText.setText("Don't Give Up!");
            tvGuideLineFooter.setText("Every step forward counts. Learn and try again!");
        } else {
            tvCongratulationText.setText("Better Luck Next Time!");
            tvGuideLineFooter.setText("Mistakes are proof that you are trying. \nKeep learning!");
        }
        exercise = exerciseHandler.convertCodeToExercise(assignment.getMaBaiTap());
        tvAssignmentName.setText(exercise.getTenBaiTap());
        tvAssignmentTotalRightAnswer.setText(String.valueOf(assignment.getSoLuongCauDung() + "/" + exercise.getSoCau()));
        tvTotalTimeSpending.setText(assignment.getTongThoiGianLamBai());
        tvAttempt.setText(String.valueOf(assignment.getLanLam()));
    }



}