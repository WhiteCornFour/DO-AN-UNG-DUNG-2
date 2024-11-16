package com.example.doanungdung2.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doanungdung2.Controller.AssignmentHandler;
import com.example.doanungdung2.Controller.ExerciseHandler;
import com.example.doanungdung2.Model.Assigment;
import com.example.doanungdung2.Model.Dictionary;
import com.example.doanungdung2.Model.Exercise;
import com.example.doanungdung2.Model.Question;
import com.example.doanungdung2.R;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

public class User_Quiz_List extends AppCompatActivity {

    private static final String DB_NAME = "AppHocTiengAnh";
    private static final int DB_VERSION = 1;
    TextView tvLevelQuizList;
    ImageView imgBackToQuizFragment;
    RecyclerView recyclerViewQuizList;
    User_Quiz_List_Custom_Adapter_Recycler_View user_quiz_list_custom_adapter_recycler_view;
    ExerciseHandler exerciseHandler;
    AssignmentHandler assignmentHandler;
    ArrayList<Exercise> exerciseArrayList = new ArrayList<>();
    public static String thoiGianBatDau = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_quiz_list);
        addControl();
        exerciseHandler = new ExerciseHandler(User_Quiz_List.this, DB_NAME, null, DB_VERSION);
        assignmentHandler = new AssignmentHandler(User_Quiz_List.this, DB_NAME, null, DB_VERSION);

        setupRecyclerView();
        loadAllQuizList();

        tvLevelQuizList.setText(getMucDoFromFragment() + " Level");

        addEvent();
    }

    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStack(); // Quay lại Fragment trước đó
    }

    void addControl() {
        tvLevelQuizList = findViewById(R.id.tvLevelQuizList);
        imgBackToQuizFragment = findViewById(R.id.imgBackToQuizFragment);
        recyclerViewQuizList = findViewById(R.id.recyclerViewQuizList);
    }

    void addEvent() {
        imgBackToQuizFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    void setupRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(User_Quiz_List.this, RecyclerView.VERTICAL, false);
        recyclerViewQuizList.setLayoutManager(layoutManager);
        recyclerViewQuizList.setItemAnimator(new DefaultItemAnimator());
        user_quiz_list_custom_adapter_recycler_view = new User_Quiz_List_Custom_Adapter_Recycler_View(exerciseArrayList, new User_Quiz_List_Custom_Adapter_Recycler_View.ItemClickListener() {
            @Override
            public void onItemClick(Exercise exercise) {
                showTakingQuizDialog(exercise);
            }
        });
        recyclerViewQuizList.setAdapter(user_quiz_list_custom_adapter_recycler_view);
    }

    public static String getThoiGianBatDau() {return thoiGianBatDau;}

    void loadAllQuizList() {
        exerciseArrayList.clear();
        exerciseArrayList = exerciseHandler.loadAllDataOfExerciseByLevel(getMucDoFromFragment());
        user_quiz_list_custom_adapter_recycler_view.setQuizList(exerciseArrayList);
    }

    private String getMucDoFromFragment() {
        Intent intent = getIntent();
        String mucDo = intent.getStringExtra("mucDo");
        return mucDo;
    }

    private void showTakingQuizDialog(Exercise exercise) {
        ConstraintLayout successConstraintLayout = findViewById(R.id.takingQuizConstraintLayout);
        View view = LayoutInflater.from(User_Quiz_List.this).inflate(R.layout.custom_taking_quiz_dialog, successConstraintLayout);
        Button enterQuiz = view.findViewById(R.id.btnEnterQuiz);
        Button combackQuiz = view.findViewById(R.id.btnComebackQuiz);

        AlertDialog.Builder builder = new AlertDialog.Builder(User_Quiz_List.this);
        builder.setView(view);
        final AlertDialog alertDialog = builder.create();

        enterQuiz.findViewById(R.id.btnEnterQuiz).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                String maBaiLam = Admin_Add_Exercise.createAutoExerciseCode("BL");
                String maBaiTap = exercise.getMaBaiTap();
                String maNguoiDung = User_Quiz_MainPage_Fragment.getIdMaNguoiDungStatic();
                thoiGianBatDau = String.valueOf(LocalDateTime.now());
                int lanLam = assignmentHandler.countTimeDoTest(maBaiTap, maNguoiDung);
//                Log.d("MabaiTap", maBaiTap);
//                Log.d("ma Nguoi Dung", maNguoiDung);
//                Log.d("lan lam", String.valueOf(lanLam));
                Assigment as = new Assigment(maBaiLam, thoiGianBatDau, null,
                        null, 0, 0f, lanLam, maBaiTap, maNguoiDung);
                assignmentHandler.insertAssignment(as);

                Intent intent = new Intent(User_Quiz_List.this, User_Quiz_Test.class);
                intent.putExtra("exercise", exercise);
                intent.putExtra("maBaiLam", maBaiLam);
                startActivity(intent);
                finish();
                Toast.makeText(User_Quiz_List.this, "Start quiz !!!", Toast.LENGTH_SHORT).show();
            }
        });

        combackQuiz.findViewById(R.id.btnComebackQuiz).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        if (alertDialog.getWindow() != null) {
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        alertDialog.show();
    }

}