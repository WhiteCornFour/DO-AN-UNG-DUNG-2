package com.example.doanungdung2.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.doanungdung2.Controller.ExerciseHandler;
import com.example.doanungdung2.Model.Exercise;
import com.example.doanungdung2.R;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

public class Admin_Delete_Exercise extends AppCompatActivity {

    private static final String DB_NAME = "AppHocTiengAnh";
    private static final int DB_VERSION = 1;

    ExerciseHandler exerciseHandler;

    ArrayList<Exercise> exerciseArrayList = new ArrayList<>();
    ArrayList<Exercise> dataOfSearch = new ArrayList<>();
    Admin_Delete_Exercise_CustomAdapter_LV adapter_lv;
    ImageView imgBackDeleteExerciseToMainPage, imgSerchForDeleteInBT;
    EditText edtSearchForDeleteInBT;
    Button btnResetForListViewInBT, btnDeleteAllForLVInBT;
    ListView lvDSBTTrongXoa;

    boolean[] checkedSates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_delete_exercise);
        addControl();
        exerciseHandler = new ExerciseHandler(Admin_Delete_Exercise.this, DB_NAME, null,
                DB_VERSION);
        loadAllDataExercise();
        addEvent();
    }
    void addControl()
    {
        imgBackDeleteExerciseToMainPage = findViewById(R.id.imgBackDeleteExerciseToMainPage);
        imgSerchForDeleteInBT = findViewById(R.id.imgSerchForDeleteInBT);
        edtSearchForDeleteInBT = findViewById(R.id.edtSearchForDeleteInBT);
        btnResetForListViewInBT = findViewById(R.id.btnResetForListViewInBT);
        btnDeleteAllForLVInBT = findViewById(R.id.btnDeleteAllForLVInBT);
        lvDSBTTrongXoa = findViewById(R.id.lvDSBTTrongXoa);
    }

    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStack();
    }

    void addEvent()
    {
        imgBackDeleteExerciseToMainPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnResetForListViewInBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadAllDataExercise();
                edtSearchForDeleteInBT.setText("");
            }
        });

        imgSerchForDeleteInBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String maBT = edtSearchForDeleteInBT.getText().toString();
                dataOfSearch = exerciseHandler.searchExerciseByNameOrCode(maBT);
                checkedSates = new boolean[dataOfSearch.size()];
                adapter_lv = new Admin_Delete_Exercise_CustomAdapter_LV(Admin_Delete_Exercise.this,
                        R.layout.layout_admin_delete_exercise_customadapter_lv, dataOfSearch, checkedSates);
                lvDSBTTrongXoa.setAdapter(adapter_lv);
            }
        });
    }
    void loadAllDataExercise()
    {
        exerciseArrayList = exerciseHandler.loadAllDataOfExercise();
        checkedSates = new boolean[exerciseArrayList.size()];
        adapter_lv = new Admin_Delete_Exercise_CustomAdapter_LV(Admin_Delete_Exercise.this,
                R.layout.layout_admin_delete_exercise_customadapter_lv, exerciseArrayList, checkedSates);
        lvDSBTTrongXoa.setAdapter(adapter_lv);
    }
}