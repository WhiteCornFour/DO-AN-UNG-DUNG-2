package com.example.doanungdung2.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.doanungdung2.Controller.ExerciseHandler;
import com.example.doanungdung2.Model.Dictionary;
import com.example.doanungdung2.Model.Exercise;
import com.example.doanungdung2.R;

import java.util.ArrayList;

public class User_Quiz_List extends AppCompatActivity {

    private static final String DB_NAME = "AppHocTiengAnh";
    private static final int DB_VERSION = 1;
    TextView tvLevelQuizList;
    ImageView imgBackToQuizFragment;
    RecyclerView recyclerViewQuizList;
    User_Quiz_List_Custom_Adapter_Recycler_View user_quiz_list_custom_adapter_recycler_view;
    ExerciseHandler exerciseHandler;
    ArrayList<Exercise> exerciseArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_quiz_list);
        addControl();
        exerciseHandler = new ExerciseHandler(User_Quiz_List.this, DB_NAME, null, DB_VERSION);

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

            }
        });
        recyclerViewQuizList.setAdapter(user_quiz_list_custom_adapter_recycler_view);
    }

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

}