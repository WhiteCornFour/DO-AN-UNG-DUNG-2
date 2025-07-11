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
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doanungdung2.Controller.AssignmentHandler;
import com.example.doanungdung2.Controller.AssignmentDetailHandler;
import com.example.doanungdung2.Controller.QuestionHandler;
import com.example.doanungdung2.Model.AssignmentDetail;
import com.example.doanungdung2.Model.Exercise;
import com.example.doanungdung2.Model.Question;
import com.example.doanungdung2.Model.SharedViewModel_Answer;
import com.example.doanungdung2.Model.SharedViewModel_Questions;
import com.example.doanungdung2.Model.SharedViewModel_AfterClickAnswer;
import com.example.doanungdung2.R;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

public class User_Quiz_Test extends AppCompatActivity {
    private static  final String DB_NAME = "AppHocTiengAnh";
    private static final int DB_VERSION = 1;
    SharedViewModel_Questions sharedViewModel_questions;
    SharedViewModel_Answer shareViewModelAnswer;
    SharedViewModel_AfterClickAnswer sharedViewModel_afterClickAnswer;
    ImageView imgBackToQuizFragment;
    TextView tvTenBaiTapQuizTest, tvThoiGianLamBai;
    RecyclerView rvCauHoiQuizTest;
    Button btnSubmitQuiz;
    GifImageView gifTopLeft, gifTopRight, gifBottomLeft, gifBottomRight;
    GifDrawable gifTopLeftDrawable, gifTopRightDrawable, gifBottomLeftDrawable, gifBottomRightDrawable;
    FrameLayout frameLayoutQuizTest;
    ArrayList<Question> questionArrayList = new ArrayList<>();
    User_Quiz_Test_Custom_Adapter user_quiz_test_custom_adapter;
    ArrayList<String> dataSource = new ArrayList<>();
    ArrayList<AssignmentDetail> assigmentDetailArrayList = new ArrayList<>();
    CountDownTimer countDownTimer;
    QuestionHandler questionHandler;
    Exercise exercise = new Exercise();
    AssignmentHandler assignmentHandler;
    AssignmentDetailHandler assignmentDetailHandler;
    int[] gifTopLeftResources = {
            R.drawable.octopus_gif,
            R.drawable.koala_gif,
            R.drawable.elephant_gif,
            R.drawable.frog_gif,
            R.drawable.jaguar_gif
    };

    int[] gifTopRightResources = {
            R.drawable.leaf_gif,
            R.drawable.leaves_gif,
            R.drawable.curry_gif
    };

    int[] gifBottomLeftResources = {
            R.drawable.cactus_gif,
            R.drawable.flower_pot_gif,
            R.drawable.mint_gif,

    };

    int[] gifBottomRightResources = {
            R.drawable.agave_gif,
            R.drawable.olives_gif
    };
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
        sharedViewModel_questions = new ViewModelProvider(this).get(SharedViewModel_Questions.class);
        shareViewModelAnswer = new ViewModelProvider(this).get(SharedViewModel_Answer.class);
        sharedViewModel_afterClickAnswer = new ViewModelProvider(this).get(SharedViewModel_AfterClickAnswer.class);
        //Lấy câu trả lời từ fragment thông qua ShareViewModel để truyền về activity
        Log.d("Answer", "Câu trả lời đã chọn: " + shareViewModelAnswer.getAnswer());
        if (shareViewModelAnswer.getAnswer() != null)
        {
            shareViewModelAnswer.getAnswer().observe(User_Quiz_Test.this, this::getAnswerFromQuizTest);
            Log.d("Answer", "Câu trả lời đã chọn: " + shareViewModelAnswer.getAnswer());
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
            AssignmentDetail assigmentDetail = new AssignmentDetail(maChiTietBaiLam, null, "Sai", maCauHoi, maBaiLam);
            assignmentDetailHandler.insertAssignmentDetail(assigmentDetail);
            Log.d("Thanh cong ", String.valueOf(i++));
        }
        pauseGif();
        //Sự kiện
        try {
            addEvent();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
        super.onBackPressed();
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
        gifTopLeft = findViewById(R.id.gifTopLeft);
        gifTopRight = findViewById(R.id.gifTopRight);
        gifBottomLeft = findViewById(R.id.gifBottomLeft);
        gifBottomRight = findViewById(R.id.gifBottomRight);
        gifTopLeftDrawable = (GifDrawable) gifTopLeft.getDrawable();
        gifTopRightDrawable = (GifDrawable) gifTopRight.getDrawable();
        gifBottomLeftDrawable = (GifDrawable) gifBottomLeft.getDrawable();
        gifBottomRightDrawable = (GifDrawable) gifBottomRight.getDrawable();
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
    void addEvent() throws IOException {
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
        applyRandomGif(gifTopLeft, gifTopLeftResources);
        applyRandomGif(gifTopRight, gifTopRightResources);
        applyRandomGif(gifBottomLeft,gifBottomLeftResources);
        applyRandomGif(gifBottomRight, gifBottomRightResources);

        applyTouchListener(gifTopLeft, gifTopLeftResources);
        applyTouchListener(gifTopRight, gifTopRightResources);
        applyTouchListener(gifBottomLeft, gifBottomLeftResources);
        applyTouchListener(gifBottomRight, gifBottomRightResources);

        startGifWithRandomInterval(gifTopLeft, gifTopLeftResources);
        startGifWithRandomInterval(gifTopRight, gifTopRightResources);
        startGifWithRandomInterval(gifBottomLeft, gifBottomLeftResources);
        startGifWithRandomInterval(gifBottomRight, gifBottomRightResources);
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
        Log.d("Tong so cau ", String.valueOf(tongSoCau));
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
            public void onItemClick(Question question) throws IOException {
                applyRandomGif(gifTopLeft, gifTopLeftResources);
                applyRandomGif(gifTopRight, gifTopRightResources);
                applyRandomGif(gifBottomLeft,gifBottomLeftResources);
                applyRandomGif(gifBottomRight, gifBottomRightResources);
                Log.d("Quesiton: ", question.getNoiDungCauHoi());
                maCauHoiSelected = question.getMaCauHoi();
                FragmentManager fragmentManager = getSupportFragmentManager();
                Fragment fragment = fragmentManager.findFragmentById(R.id.frameLayoutQuizTest);
                String cauTraLoi = assignmentDetailHandler.getSelectedAnswerForQuestion(maCauHoiSelected, maBaiLam);

                if (fragment instanceof User_Quiz_Test_Multiple_Choice_Fragment) {
                    sharedViewModel_questions.select(question);
                    if (cauTraLoi != null){
                        sharedViewModel_afterClickAnswer.setSelectedAnswer(cauTraLoi);
                    }
                    else {
                        sharedViewModel_afterClickAnswer.setSelectedAnswer(null);
                    }
                } else if (fragment instanceof User_Quiz_Test_True_False_Fragment) {
                    sharedViewModel_questions.select(question);
                    if (cauTraLoi != null){
                        sharedViewModel_afterClickAnswer.setSelectedAnswer(cauTraLoi);
                    }
                    else {
                        sharedViewModel_afterClickAnswer.setSelectedAnswer(null);
                    }
                }else if (fragment instanceof User_Quiz_Test_Essay_Fragment) {
                    sharedViewModel_questions.select(question);
                    if (cauTraLoi != null){
                        sharedViewModel_afterClickAnswer.setSelectedAnswer(cauTraLoi);
                    }
                    else {
                        sharedViewModel_afterClickAnswer.setSelectedAnswer(null);
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
                //Lưu lại điểm
                updateTestPoint();
                //Đóng bộ đếm giờ
                countDownTimer.cancel();
                //Phát nhạc kết thúc làm bài
                MediaPlayer mediaPlayer = MediaPlayer.create(User_Quiz_Test.this, R.raw.success);
                mediaPlayer.start();

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
//        Log.d("Tong thoi gian lam bai", tongThoiGianLamBai);
        int soLuongCauDung = assignmentDetailHandler.countRightAnswer(maBaiLam);
//        Log.d("So Luong Cau Dung", String.valueOf(soLuongCauDung));
        float diem = (soLuongCauDung * 10f) / tongSoCau;
//        Log.d("Diem", String.valueOf(diem));
        String maNguoiDung = User_Quiz_MainPage_Fragment.getIdMaNguoiDungStatic();
//        if (maNguoiDung != null) {
//            Toast.makeText(User_Quiz_Test.this, "Ma Nguoi Dung khong null", Toast.LENGTH_LONG).show();
//        } else {
//            Toast.makeText(User_Quiz_Test.this, "Ma Nguoi Dung null", Toast.LENGTH_LONG).show();
//        }
//        Log.d("Ma Bai Lam", maBaiLam);
//        Log.d("Ma Bai Tap", exercise.getMaBaiTap());
        assignmentHandler.updateAssignmentPoint(thoiGianKetThuc, tongThoiGianLamBai, soLuongCauDung, diem,
                maBaiLam, exercise.getMaBaiTap(), maNguoiDung);
        Intent intent = new Intent(User_Quiz_Test.this, User_Quiz_Result.class);
        intent.putExtra("maBaiLamTR", maBaiLam);
        startActivity(intent);
    }

    private void pauseGif() {
        if (gifTopLeftDrawable != null) gifTopLeftDrawable.stop();
        if (gifTopRightDrawable != null) gifTopRightDrawable.stop();
        if (gifBottomLeftDrawable != null) gifBottomLeftDrawable.stop();
        if (gifBottomRightDrawable != null) gifBottomRightDrawable.stop();
    }

    private int getRandomGifResource(int[] gifResources) {
        Random random = new Random();
        return gifResources[random.nextInt(gifResources.length)];
    }

    private void applyRandomGif(final GifImageView gifImageView, final int[] gifResources) throws IOException {
        int randomGifResource = getRandomGifResource(gifResources);
        GifDrawable gifDrawable = new GifDrawable(getResources(), randomGifResource);
        gifImageView.setImageDrawable(gifDrawable);

        gifDrawable.pause();
        gifImageView.setAlpha(1.0f);
    }


    //phat lai gif
    private void applyTouchListener(final GifImageView gifImageView, final int[] gifResources) {
        gifImageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final GifDrawable gifDrawable = (GifDrawable) gifImageView.getDrawable();

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        gifImageView.setAlpha(0.7f);
                        if (gifDrawable != null) {
                            gifDrawable.start();  //bat dau phat gif khi nhan
                        }

                        //tao handler dung gif sau 10s
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (gifDrawable != null) {
                                    gifDrawable.stop();  //dung gif sau 10s
                                }
                            }
                        }, 10000);

                    case MotionEvent.ACTION_UP:
                        gifImageView.setAlpha(1.0f);
                        break;

                    default:
                        return false;
                }
                return true;
            }
        });
    }

    // khoi dong gif trong khaon thoi gian ngau nhien tu 20s toi 60s thi chay 1 lan
    private void startGifWithRandomInterval(final GifImageView gifImageView, final int[] gifResources) {
        final Random random = new Random();
        int delayTime = 20000 + random.nextInt(40000);  //random trong khoang 20 tơi 60s
        //tao handler chay lai gif
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                GifDrawable gifDrawable = (GifDrawable) gifImageView.getDrawable();

                gifDrawable.start();

                // tao handler de dung gif
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (gifDrawable != null) {
                            gifDrawable.stop();  //dung gif sau 10s
                        }
                    }
                }, 10000);
            }
        }, delayTime);
    }
}