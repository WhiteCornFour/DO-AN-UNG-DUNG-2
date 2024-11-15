package com.example.doanungdung2.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.doanungdung2.Controller.QuestionHandler;
import com.example.doanungdung2.Model.Exercise;
import com.example.doanungdung2.Model.Question;
import com.example.doanungdung2.Model.SharedViewModel;
import com.example.doanungdung2.R;

import java.util.ArrayList;

public class User_Quiz_Test extends AppCompatActivity {

    private static  final String DB_NAME = "AppHocTiengAnh";
    private static final int DB_VERSION = 1;
    SharedViewModel sharedViewModel;
    ImageView imgBackToQuizFragment;
    TextView tvTenBaiTapQuizTest, tvThoiGianLamBai;
    RecyclerView rvCauHoiQuizTest;
    Button btnSubmitQuiz;
    FrameLayout frameLayoutQuizTest;
    ArrayList<Question> questionArrayList = new ArrayList<>();
    User_Quiz_Test_Custom_Adapter user_quiz_test_custom_adapter;
    ArrayList<String> dataSource = new ArrayList<>();
    QuestionHandler questionHandler;
    Exercise exercise = new Exercise();
    int currentQuestionPosition = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_quiz_test);
        //-------------------------------//
        addControl();
        questionHandler = new QuestionHandler(User_Quiz_Test.this, DB_NAME, null, DB_VERSION);
        sharedViewModel = new ViewModelProvider(this).get(SharedViewModel.class);
        exercise = getIntentExercise();
        setUpDataForTest(exercise);

        setUpRecyclerView();
        loadAllQuizTestList();
        setUpFirstElementOfList(questionArrayList);
        addEvent();
    }

    ArrayList<String> convertObjectToString (ArrayList<Question> questions) {
        ArrayList<String> data = new ArrayList<>();
        int number = 0;
        for (Question q: questions
             ) {
            number ++;
            String kq = String.valueOf(number);
            data.add(kq);
        }
        return data;
    }

    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStack(); // Quay lại Fragment trước đó
    }

    void addControl() {
        imgBackToQuizFragment = findViewById(R.id.imgBackToQuizFragment);
        tvTenBaiTapQuizTest = findViewById(R.id.tvTenBaiTapQuizTest);
        tvThoiGianLamBai = findViewById(R.id.tvThoiGianLamBai);
        rvCauHoiQuizTest = findViewById(R.id.rvCauHoiQuizTest);
        btnSubmitQuiz = findViewById(R.id.btnSubmitQuiz);
        frameLayoutQuizTest = findViewById(R.id.frameLayoutQuizTest);
    }

    void addEvent() {
        imgBackToQuizFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private Exercise getIntentExercise() {
        Intent intent = getIntent();
        Exercise exercise = (Exercise) intent.getSerializableExtra("exercise");
        return exercise;
    }

    void loadAllQuizTestList() {
        questionArrayList.clear();
        questionArrayList = questionHandler.loadAllDataOfMatchQuestionByExerciseCode(exercise.getMaBaiTap(), exercise.getMucDo());
        dataSource = convertObjectToString(questionArrayList);
        user_quiz_test_custom_adapter.setQuizTestList(questionArrayList, dataSource);
    }

    private void setUpDataForTest(Exercise exercise) {
        tvTenBaiTapQuizTest.setText(exercise.getTenBaiTap());
        tvThoiGianLamBai.setText(exercise.getThoiGian());

        String maDBT = exercise.getMaDangBaiTap();
        Log.d("DEBUG", "Ma dang bai tap: " + maDBT);
        if (maDBT.equals("DBT01")) {
            User_Quiz_Test_Multiple_Choice_Fragment f1 = new User_Quiz_Test_Multiple_Choice_Fragment();
            replaceFragment(f1);
        } else if (maDBT.equals("DBT02")) {
            User_Quiz_Test_True_False_Fragment f2 = new User_Quiz_Test_True_False_Fragment();
            replaceFragment(f2);
        } else {
            User_Quiz_Test_Essay_Fragment f3 = new User_Quiz_Test_Essay_Fragment();
            replaceFragment(f3);
        }
    }

    private void setUpRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(User_Quiz_Test.this, RecyclerView.HORIZONTAL, false);
        rvCauHoiQuizTest.setLayoutManager(layoutManager);
        rvCauHoiQuizTest.setItemAnimator(new DefaultItemAnimator());
        user_quiz_test_custom_adapter = new User_Quiz_Test_Custom_Adapter(dataSource, questionArrayList ,new User_Quiz_Test_Custom_Adapter.ItemClickListener() {
            @Override
            public void onItemClick(Question question) {
                Log.d("Quesiton: ", question.getNoiDungCauHoi());
                FragmentManager fragmentManager = getSupportFragmentManager();
                Fragment fragment = fragmentManager.findFragmentById(R.id.frameLayoutQuizTest);

                if (fragment instanceof User_Quiz_Test_Multiple_Choice_Fragment) {
                    sharedViewModel.select(question);
                }
            }
        });
        rvCauHoiQuizTest.setAdapter(user_quiz_test_custom_adapter);
    }

    private void updateQuestion() {
        Question currentQuestion = new Question();
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.frameLayoutQuizTest);
        if (currentQuestionPosition == 0) {
            currentQuestion = questionArrayList.get(0);
            loadAllQuizTestList();
            if (fragment instanceof User_Quiz_Test_Multiple_Choice_Fragment) {
                sharedViewModel.select(currentQuestion);
            }
        }
        currentQuestion = questionArrayList.get(currentQuestionPosition);
        loadAllQuizTestList();
        if (fragment instanceof User_Quiz_Test_Multiple_Choice_Fragment) {
            sharedViewModel.select(currentQuestion);
        }
    }

    void setUpFirstElementOfList (ArrayList<Question> arrayLists) {
        Question question1 = arrayLists.get(0);
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.frameLayoutQuizTest);

        if (fragment instanceof User_Quiz_Test_Multiple_Choice_Fragment) {
            sharedViewModel.select(question1);
        }
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayoutQuizTest, fragment);
        fragmentTransaction.commit();
    }
}