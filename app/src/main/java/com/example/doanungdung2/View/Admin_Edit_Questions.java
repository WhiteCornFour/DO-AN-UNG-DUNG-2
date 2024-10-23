package com.example.doanungdung2.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.doanungdung2.Controller.ExercisesCategoryHandler;
import com.example.doanungdung2.Controller.QuestionHandler;
import com.example.doanungdung2.Model.ExercisesCategory;
import com.example.doanungdung2.Model.Question;
import com.example.doanungdung2.R;

import java.util.ArrayList;

public class Admin_Edit_Questions extends AppCompatActivity {

    private static final String DB_NAME = "AppHocTiengAnh";
    private static final int DB_VERSION = 1;

    EditText edtSuaCHSearch, edtSuaMaCauHoi, edtSuaNoiDungCauHoi;
    ImageView imgBackToMainPageSCH,imgSuaCHSearch;
    RecyclerView rvSuaCHSearch;
    Spinner spinnerDangBaiTapCH, spinnerMucDoCH;
    FrameLayout frameLayoutCauHoiSDBT;
    Button btnSuaCH;
    String [] dsMucDo = new String[]{"Beginner", "Starter", "Intermediate", "Proficient", "Master"};

    QuestionHandler questionHandler;
    ExercisesCategoryHandler exercisesCategoryHandler;
    ArrayList<Question> questionArrayListResult = new ArrayList<>();
    ArrayList<ExercisesCategory> dsDangBaiTap = new ArrayList<>();
    Admin_Edit_Questions_CustomAdapter admin_edit_questions_customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_edit_questions);
        addControl();

        questionHandler = new QuestionHandler(Admin_Edit_Questions.this, DB_NAME, null, DB_VERSION);
        exercisesCategoryHandler = new ExercisesCategoryHandler(Admin_Edit_Questions.this, DB_NAME, null, DB_VERSION);

        dsDangBaiTap = exercisesCategoryHandler.loadAllDataOfExercisesCategory();

        spinnerMucDoCHCreate();
        spinnerDangBaiTapCHCreate();

        setUpRecyclerView();
        loadAllQuestions();
        addEvent();
    }

    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStack(); // Quay lại Fragment trước đó
    }


    void addControl() {
        edtSuaCHSearch = findViewById(R.id.edtSuaCHSearch);
        edtSuaMaCauHoi = findViewById(R.id.edtSuaMaCauHoi);
        edtSuaNoiDungCauHoi = findViewById(R.id.edtSuaNoiDungCauHoi);
        imgBackToMainPageSCH = findViewById(R.id.imgBackToMainPageSCH);
        imgSuaCHSearch = findViewById(R.id.imgSuaCHSearch);
        rvSuaCHSearch = findViewById(R.id.rvSuaCHSearch);
        spinnerDangBaiTapCH = findViewById(R.id.spinnerDangBaiTapCH);
        spinnerMucDoCH = findViewById(R.id.spinnerMucDoCH);
        frameLayoutCauHoiSDBT = findViewById(R.id.frameLayoutCauHoiSDBT);
        btnSuaCH = findViewById(R.id.btnSuaCH);
    }

    void addEvent() {
        imgBackToMainPageSCH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        imgSuaCHSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String questionsSearchValue = edtSuaCHSearch.getText().toString().trim();
                if (questionsSearchValue.isEmpty()) {
                    loadAllQuestions();
                } else {
                    edtSuaCHSearch.setText("");
                }
            }
        });
    }

    //Chuc nang
    void loadAllQuestions() {
        questionArrayListResult.clear();
        questionArrayListResult = questionHandler.loadAllDataOfQuestion();
        Log.d("DEBUG", "Size of questionArrayListResult: " + questionArrayListResult.size());
        admin_edit_questions_customAdapter.setQuestionsList(questionArrayListResult);
    }

    //Thiết lập nút, spinner, checkbox
    void spinnerMucDoCHCreate() {
        String[] dsCauString = new String[dsMucDo.length];
        for (int i = 0; i < dsMucDo.length; i++) {
            dsCauString[i] = String.valueOf(dsMucDo[i]);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, dsCauString);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMucDoCH.setAdapter(adapter);
    }


    void spinnerDangBaiTapCHCreate() {
        ArrayList<String> dsDangBaiTapCHString = exercisesCategoryHandler.returnNameOfCategoriesSpinner();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, dsDangBaiTapCHString);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDangBaiTapCH.setAdapter(adapter);
    }

    void setUpRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(Admin_Edit_Questions.this, RecyclerView.VERTICAL, false);
        rvSuaCHSearch.addItemDecoration(new DividerItemDecoration(Admin_Edit_Questions.this, DividerItemDecoration.VERTICAL));
        rvSuaCHSearch.setLayoutManager(layoutManager);
        rvSuaCHSearch.setItemAnimator(new DefaultItemAnimator());
        admin_edit_questions_customAdapter = new Admin_Edit_Questions_CustomAdapter(questionArrayListResult,new Admin_Edit_Questions_CustomAdapter.ItemClickListener() {
            @Override
            public void onItemClick(Question question) {
                edtSuaMaCauHoi.setText(question.getMaCauHoi());
                Log.d("DEBUG", "MaCauHoi: " + question.getMaCauHoi());
                edtSuaNoiDungCauHoi.setText(question.getNoiDungCauHoi());

                int index = -1;
                for (int i = 0; i < dsMucDo.length; i++) {
                    if (dsMucDo[i] == question.getMucDo()) {
                        index = i;
                        break;
                    }
                }
                if (index != -1) {
                    spinnerMucDoCH.setSelection(index);
                }
            }
        });
        rvSuaCHSearch.setAdapter(admin_edit_questions_customAdapter );
    }

}