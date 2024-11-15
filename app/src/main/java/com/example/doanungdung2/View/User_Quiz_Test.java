package com.example.doanungdung2.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doanungdung2.Controller.QuestionHandler;
import com.example.doanungdung2.Model.Exercise;
import com.example.doanungdung2.Model.Question;
import com.example.doanungdung2.Model.ShareViewModel_Answer;
import com.example.doanungdung2.Model.SharedViewModel;
import com.example.doanungdung2.R;

import java.util.ArrayList;
import java.util.Locale;

public class User_Quiz_Test extends AppCompatActivity {
    private static  final String DB_NAME = "AppHocTiengAnh";
    private static final int DB_VERSION = 1;
    SharedViewModel sharedViewModel;
    ShareViewModel_Answer shareViewModelAnswer;
    ImageView imgBackToQuizFragment;
    TextView tvTenBaiTapQuizTest, tvThoiGianLamBai;
    RecyclerView rvCauHoiQuizTest;
    ImageButton btnPrevious, btnNext;
    Button btnSubmitQuiz;
    FrameLayout frameLayoutQuizTest;
    ArrayList<Question> questionArrayList = new ArrayList<>();
    User_Quiz_Test_Custom_Adapter user_quiz_test_custom_adapter;
    ArrayList<String> dataSource = new ArrayList<>();
    QuestionHandler questionHandler;
    Exercise exercise = new Exercise();
    CountDownTimer countDownTimer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_quiz_test);
        //-------------------------------//
        addControl();
        questionHandler = new QuestionHandler(User_Quiz_Test.this, DB_NAME, null, DB_VERSION);
        sharedViewModel = new ViewModelProvider(this).get(SharedViewModel.class);
        shareViewModelAnswer = new ViewModelProvider(this).get(ShareViewModel_Answer.class);

        if (shareViewModelAnswer.getAnswer() != null)
        {
            shareViewModelAnswer.getAnswer().observe(User_Quiz_Test.this, this::getAnswerFromQuizTest);
        }

        exercise = getIntentExercise();
        setUpDataForTest(exercise);

        setUpRecyclerView();
        loadAllQuizTestList();

        //Bat dau thoi gian lam bai 1s = 1000
        startTime(Integer.parseInt(exercise.getThoiGian().trim()) * 1000 * 60);
        //Log.d("ThoiGian lam bai", String.valueOf(Integer.parseInt(exercise.getThoiGian().trim()) * 60));
        //Log.d("Leght of questionArrayList", String.valueOf(questionArrayList.size()));
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
        Log.d("Lenght of data String", String.valueOf(data.size()));
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
        btnPrevious = findViewById(R.id.btnPrevious);
        btnNext = findViewById(R.id.btnNext);
        btnSubmitQuiz = findViewById(R.id.btnSubmitQuiz);
        frameLayoutQuizTest = findViewById(R.id.frameLayoutQuizTest);
    }
    void getAnswerFromQuizTest(String dapan)
    {
        Log.d("Dap an from quiz", dapan);
    }
    void addEvent() {
        imgBackToQuizFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    //function count down time
    private void startTime(int time)
    {
        countDownTimer = new CountDownTimer(time, 1000) {
            @Override
            public void onTick(long l) {
                long hours = (l / 1000) / 3600;
                long minutes = ((l / 1000) % 3600) / 60;
                long seconds = (l / 1000) % 60;
                String timeFormatted = String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, seconds);
                tvThoiGianLamBai.setText(timeFormatted);
            }

            @Override
            public void onFinish() {
                tvThoiGianLamBai.setText("00:00:00");
                Toast.makeText(User_Quiz_Test.this, "Time's up!", Toast.LENGTH_SHORT).show();
                MediaPlayer mediaPlayer = MediaPlayer.create(User_Quiz_Test.this, R.raw.success);
                mediaPlayer.start();
                finish();
            }
        }.start();
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
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    Fragment fragment = fragmentManager.findFragmentById(R.id.frameLayoutQuizTest);

                    if (fragment instanceof User_Quiz_Test_Multiple_Choice_Fragment) {
                        sharedViewModel.select(question);

                    }
                }
            });
            rvCauHoiQuizTest.setAdapter(user_quiz_test_custom_adapter);
        }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayoutQuizTest, fragment);
        fragmentTransaction.commit();
    }
}