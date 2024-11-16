package com.example.doanungdung2.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doanungdung2.Controller.AssignmentHandler;
import com.example.doanungdung2.Controller.AssignmentDetailHandler;
import com.example.doanungdung2.Controller.QuestionHandler;
import com.example.doanungdung2.Model.AssigmentDetail;
import com.example.doanungdung2.Model.Exercise;
import com.example.doanungdung2.Model.Question;
import com.example.doanungdung2.Model.SharedViewModel_Answer;
import com.example.doanungdung2.Model.SharedViewModel;
import com.example.doanungdung2.Model.SharedViewModel_AfterClickAnswer;
import com.example.doanungdung2.R;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;

public class User_Quiz_Test extends AppCompatActivity {
    private static  final String DB_NAME = "AppHocTiengAnh";
    private static final int DB_VERSION = 1;
    SharedViewModel sharedViewModel;
    SharedViewModel_Answer shareViewModelAnswer;
    SharedViewModel_AfterClickAnswer sharedViewModel_afterClickAnswer;
    ImageView imgBackToQuizFragment;
    TextView tvTenBaiTapQuizTest, tvThoiGianLamBai;
    RecyclerView rvCauHoiQuizTest;
    Button btnSubmitQuiz;
    FrameLayout frameLayoutQuizTest;
    ArrayList<Question> questionArrayList = new ArrayList<>();
    User_Quiz_Test_Custom_Adapter user_quiz_test_custom_adapter;
    ArrayList<String> dataSource = new ArrayList<>();
    ArrayList<AssigmentDetail> assigmentDetailArrayList = new ArrayList<>();
    QuestionHandler questionHandler;
    Exercise exercise = new Exercise();
    CountDownTimer countDownTimer;
    AssignmentHandler assignmentHandler;
    AssignmentDetailHandler assignmentDetailHandler;
    String maCauHoiSelected = "";
    String maBaiLam = "";
    int tongSoCau = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_quiz_test);
        //-------------------------------//
        addControl();
        //Khởi tạo các Handler
        questionHandler = new QuestionHandler(User_Quiz_Test.this, DB_NAME, null, DB_VERSION);
        assignmentHandler = new AssignmentHandler(User_Quiz_Test.this, DB_NAME, null, DB_VERSION);
        assignmentDetailHandler = new AssignmentDetailHandler(User_Quiz_Test.this, DB_NAME, null, DB_VERSION);
        //Khởi tạo shareViewModel
        sharedViewModel = new ViewModelProvider(this).get(SharedViewModel.class);
        shareViewModelAnswer = new ViewModelProvider(this).get(SharedViewModel_Answer.class);
        sharedViewModel_afterClickAnswer = new ViewModelProvider(this).get(SharedViewModel_AfterClickAnswer.class);
        //Lấy câu trả lời từ fragment thông qua ShareViewModel để truyền về activity
        if (shareViewModelAnswer.getAnswer() != null)
        {
            shareViewModelAnswer.getAnswer().observe(User_Quiz_Test.this, this::getAnswerFromQuizTest);
        }
        //Nhận intent từ quiz list activity
        exercise = getIntentExercise();
        setUpDataForTest(exercise);

        //Bat dau thoi gian lam bai 1s = 1000
        startTime(Integer.parseInt(exercise.getThoiGian().trim()) * 1000 * 60);
        //Log.d("ThoiGian lam bai", String.valueOf(Integer.parseInt(exercise.getThoiGian().trim()) * 60));
        //Log.d("Leght of questionArrayList", String.valueOf(questionArrayList.size()));
        //Nhận intent từ quiz list activity
        Intent intent = getIntent();
        maBaiLam = intent.getStringExtra("maBaiLam");
        //Log.d("Ma Bai Lam", maBaiLam);

        setUpRecyclerView();
        loadAllQuizTestList();
        //Lấy danh sách question có trong exercise để tạo AssignmentDetails tương ứng
        int i = 0;
        for (Question q: questionArrayList
        ) {
            String maChiTietBaiLam = Admin_Add_Exercise.createAutoExerciseCode("CTBL");
            String maCauHoi = q.getMaCauHoi();
            AssigmentDetail assigmentDetail = new AssigmentDetail(maChiTietBaiLam, null, "Sai", maCauHoi, maBaiLam);
            assignmentDetailHandler.insertAssignmentDetail(assigmentDetail);
            Log.d("Thanh cong ", String.valueOf(i++));
        }
        //Sự kiện
        addEvent();
    }
    //Hàm truyeenf vào 1 list question sau đó chuyển sang 1 list string số thứ tự từ 1 đến độ dài list questions
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
    //Hàm nhận đáp án từ các fragment gửi qua ShareViewModel_Answer
    void getAnswerFromQuizTest(String dapan)
    {
        String ketQuaCauTraLoi = "Sai";
        Log.d("Dap an from quiz", dapan);
        if (maCauHoiSelected != null)
        {
            boolean check = questionHandler.checkAnswerQuestion(maCauHoiSelected, dapan);
            if (check)
            {
                ketQuaCauTraLoi = "Đúng";
            }else
            {
                ketQuaCauTraLoi = "Sai";
            }
            assignmentDetailHandler.upDateAssignmentDetail(maCauHoiSelected, maBaiLam, dapan, ketQuaCauTraLoi);
        }
    }
    void addEvent() {
        //Xử lý khi ng dùng thoát đột ngột
        imgBackToQuizFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAlertDialogSubmitTest().show();
            }
        });
        //Xử lý khi ng dùng nộp bài
        btnSubmitQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAlertDialogSubmitTest().show();
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
                updateTestPoint();
                finish();
            }
        }.start();
    }
    //Hàm nhận Intent để setup data
    private Exercise getIntentExercise() {
        Intent intent = getIntent();
        Exercise exercise = (Exercise) intent.getSerializableExtra("exercise");
        return exercise;
    }

    void loadAllQuizTestList() {
        questionArrayList.clear();
        questionArrayList = questionHandler.loadAllDataOfMatchQuestionByExerciseCode(exercise.getMaBaiTap(), exercise.getMucDo());
        assigmentDetailArrayList = assignmentDetailHandler.loadAssignmentDetail();
        dataSource = convertObjectToString(questionArrayList);
        user_quiz_test_custom_adapter.setQuizTestList(questionArrayList,dataSource);
    }
    //Mở fragment quiz test tương ứng
    private void setUpDataForTest(Exercise exercise) {
        tvTenBaiTapQuizTest.setText(exercise.getTenBaiTap());
        tvThoiGianLamBai.setText(exercise.getThoiGian());
        tongSoCau = exercise.getSoCau();
        String maDBT = exercise.getMaDangBaiTap();

        Log.d("DEBUG", "Ma dang bai tap: " + maDBT);
        if (maDBT.equals("DBT01")) {
            User_Quiz_Test_Multiple_Choice_Fragment f1 = new User_Quiz_Test_Multiple_Choice_Fragment();
            replaceFragment(f1);
        } else if (maDBT.equals("DBT03")) {
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
                    maCauHoiSelected = question.getMaCauHoi();
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    Fragment fragment = fragmentManager.findFragmentById(R.id.frameLayoutQuizTest);
                    String cauTraLoi = assignmentDetailHandler.getSelectedAnswerForQuestion(maCauHoiSelected, maBaiLam);

                    if (fragment instanceof User_Quiz_Test_Multiple_Choice_Fragment) {
                        sharedViewModel.select(question);
                        if (cauTraLoi != null){
                            sharedViewModel_afterClickAnswer.setSelectedAnswer(cauTraLoi);
                        }
                        else {
                            sharedViewModel_afterClickAnswer.setSelectedAnswer(null);
                        }
                    } else if (fragment instanceof User_Quiz_Test_True_False_Fragment) {
                        sharedViewModel.select(question);
                        if (cauTraLoi != null){
                            sharedViewModel_afterClickAnswer.setSelectedAnswer(cauTraLoi);
                        }
                        else {
                            sharedViewModel_afterClickAnswer.setSelectedAnswer(null);
                        }
                    }else if (fragment instanceof User_Quiz_Test_Essay_Fragment) {
                        sharedViewModel.select(question);
                        if (cauTraLoi != null){
                            sharedViewModel_afterClickAnswer.setSelectedAnswer(cauTraLoi);
                        }
                        else {
                            sharedViewModel_afterClickAnswer.setSelectedAnswer(null);
                        }
                    }
                }
            }
        });
        rvCauHoiQuizTest.setAdapter(user_quiz_test_custom_adapter);
    }
  
    //Hàm chuyển đổi fragment
    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayoutQuizTest, fragment);
        fragmentTransaction.commit();
    }

    public static String tinhTongThoiGian(String thoiGianBatDau, String thoiGianKetThuc) {
        // Định dạng DateTime ISO-8601
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        LocalDateTime startTime = LocalDateTime.parse(thoiGianBatDau, formatter);
        LocalDateTime endTime = LocalDateTime.parse(thoiGianKetThuc, formatter);

        // Tính tổng thời gian làm bài
        Duration duration = Duration.between(startTime, endTime);
        long totalSeconds = duration.getSeconds(); // Thay thế toSeconds()

        long hours = totalSeconds / 3600;
        long minutes = (totalSeconds % 3600) / 60;
        long seconds = totalSeconds % 60;

        // Trả về chuỗi định dạng hh:mm:ss
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    private AlertDialog createAlertDialogSubmitTest() {
        AlertDialog.Builder builder = new AlertDialog.Builder(User_Quiz_Test.this);
        builder.setTitle("Submit Assigment");
        builder.setMessage("Nộp bài tập ");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                updateTestPoint();
                dialogInterface.dismiss();
                finish();
            }
        });
        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        return builder.create();
    }
    //Cập nhật bài tập sau khi người dùng nộp bài hoặc thoát
    public void updateTestPoint() {
        String thoiGianKetThuc = String.valueOf(LocalDateTime.now());
        String thoiGianBatDau = User_Quiz_List.getThoiGianBatDau();
        String tongThoiGianLamBai = tinhTongThoiGian(thoiGianBatDau, thoiGianKetThuc);
        int soLuongCauDung = assignmentDetailHandler.countRightAnswer(maBaiLam);
        float diem = (soLuongCauDung * 10f) / tongSoCau;
        String maNguoiDung = User_Quiz_MainPage_Fragment.getIdMaNguoiDungStatic();
//        Log.d("thoiGianKetThuc: ", thoiGianKetThuc);
//        Log.d("thoiGianBatDau", thoiGianBatDau);
//        Log.d("tongThoiGianLamBai", tongThoiGianLamBai);
//        Log.d("soLuongCauDung", String.valueOf(soLuongCauDung));
//        Log.d("diem", String.valueOf(diem));
//        Log.d("maNguoiDung", maNguoiDung);
//        Log.d("maBaiTap", exercise.getMaBaiTap());
//        Log.d("manguoiDung ", maBaiLam);
        assignmentHandler.updateAssignmentPoint(thoiGianKetThuc, tongThoiGianLamBai, soLuongCauDung, diem,
                maBaiLam, exercise.getMaBaiTap(), maNguoiDung);
    }

}