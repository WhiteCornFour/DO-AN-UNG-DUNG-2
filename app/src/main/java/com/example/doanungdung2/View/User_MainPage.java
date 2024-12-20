package com.example.doanungdung2.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doanungdung2.Controller.UserHandler;
import com.example.doanungdung2.Model.User;
import com.example.doanungdung2.R;
import com.example.doanungdung2.databinding.ActivityMainBinding;
import com.example.doanungdung2.databinding.ActivityUserMainPageBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class User_MainPage extends AppCompatActivity {
    private static final String DB_NAME = "AppHocTiengAnh";
    private static final int DB_VERSION = 1;
//    LinearLayout quizLayout, dictionaryLayout, grammarLayout, sentencesLayout;
//    ImageView quizImage, dictionaryImage, grammarImage, sentencesImage;
//    TextView quizTextView, dictionaryTextView, grammarTextView, sentencesTextView;
    ActivityUserMainPageBinding binding;
    BottomNavigationView bottomNavigationViewUser;
    FloatingActionButton floatingActionButton;
    User user = new User();
    UserHandler userHandler;
    long pressbackTime;
//    private int selectedTab = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserMainPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        addControl();
        //Gọi handler để chuẩn bị lấy thông tin User
        userHandler = new UserHandler(User_MainPage.this, DB_NAME, null, DB_VERSION);

        Intent intent = getIntent();
        if (intent != null)
        {
            user = (User) intent.getSerializableExtra("user");
        }else {
            //Khi người dùng ghi nhớ đăng nhập thì vào thẳng trang main
            //Đồng nghĩa sẽ không nhận được intent
            //Tiến hành lấy dữ liệu dưới local ThongTinKhachHang để load lên
            //Dữ liệu này được gán ở ở sự kiện btnLogin của file User_Login
            SharedPreferences sharedPreferences = getSharedPreferences("ThongTinKhachHang", MODE_PRIVATE);
            String userName = sharedPreferences.getString("userName", null);
            String passWord = sharedPreferences.getString("passWord", null);
            user = userHandler.getUserInfo(userName, passWord);
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        Bundle bundle = new Bundle();
        bundle.putSerializable("user", user);
        fragmentManager.setFragmentResult("userResult", bundle);

        replaceFragment(new User_Quiz_MainPage_Fragment());
//        replaceFragment(quiz_mainPage_fragment);
        binding.bottomNavigationViewUser.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) { // Không có lỗi nếu ID tồn tại trong menu
                case R.id.quiz:
                    replaceFragment(new User_Quiz_MainPage_Fragment());
                    break;
                case R.id.dictionary:
                    replaceFragment(new User_Dictionary_MainPage_Fragment());
                    break;
                case R.id.grammar:
                    replaceFragment(new User_Grammar_MainPage_Fragment());
                    break;
                case R.id.sentences:
                    replaceFragment(new User_Sentences_MainPage_Fragment());
                    break;
                default:
                    break;
            }
            return true;
        });
        binding.bottomNavigationViewUser.setBackground(null);
        addEvent();
    }

    @Override
    public void onBackPressed() {
        if (pressbackTime + 200 > System.currentTimeMillis())
        {
            super.onBackPressed();
            SharedPreferences sharedPreferences = getSharedPreferences("User", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.apply();
            return;
        }else {
            Toast.makeText(this, "Press back again to exit.", Toast.LENGTH_SHORT).show();
        }
        pressbackTime = System.currentTimeMillis();
    }

    void addControl() {
        floatingActionButton = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        bottomNavigationViewUser = findViewById(R.id.bottomNavigationViewUser);
//        quizLayout = findViewById(R.id.quizLayout);
//        dictionaryLayout = findViewById(R.id.dictionaryLayout);
//        grammarLayout = findViewById(R.id.grammarLayout);
//        sentencesLayout = findViewById(R.id.sentencesLayout);
//
//        quizImage = findViewById(R.id.quizImage);
//        dictionaryImage = findViewById(R.id.dictionaryImage);
//        grammarImage = findViewById(R.id.grammarImage);
//        sentencesImage = findViewById(R.id.sentencesImage);
//
//        quizTextView = findViewById(R.id.quizTextView);
//        dictionaryTextView = findViewById(R.id.dictionaryTextView);
//        grammarTextView = findViewById(R.id.grammarTextView);
//        sentencesTextView = findViewById(R.id.sentencesTextView);
    }

    void addEvent() {
//        displayFragment(User_Quiz_MainPage_Fragment.class, selectedTab);
//        // Cài đặt sự kiện click cho các tab
//        quizLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                displayFragment(User_Quiz_MainPage_Fragment.class, 1);
//            }
//        });
//
//        dictionaryLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                displayFragment(User_Dictionary_MainPage_Fragment.class, 2);
//            }
//        });
//
//        grammarLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                displayFragment(User_Grammar_MainPage_Fragment.class, 3);
//            }
//        });
//
//        sentencesLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                displayFragment(User_Sentences_MainPage_Fragment.class, 4);
//            }
//        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(User_MainPage.this, User_Profile.class);
                intent.putExtra("userFromMainPageToProfile", user);
                startActivity(intent);
            }
        });
    }

    // Hàm chung để hiển thị fragment và thay đổi giao diện
//    private void displayFragment(Class fragmentClass, int tabNumber) {
//        if (selectedTab != tabNumber) {
//            // Thay đổi fragment
//            getSupportFragmentManager().beginTransaction()
//                    .setReorderingAllowed(true)
//                    .replace(R.id.fragmentContainer, fragmentClass, null)
//                    .commit();
//
//            // Ẩn TextView của tất cả các tab
//            quizTextView.setVisibility(View.GONE);
//            dictionaryTextView.setVisibility(View.GONE);
//            grammarTextView.setVisibility(View.GONE);
//            sentencesTextView.setVisibility(View.GONE);
//
//            // Đặt icon và nền mặc định cho tất cả các tab
//            quizImage.setImageResource(R.drawable.quiz_icon);
//            dictionaryImage.setImageResource(R.drawable.dictionary_icon);
//            grammarImage.setImageResource(R.drawable.grammar_icon);
//            sentencesImage.setImageResource(R.drawable.sentence_icon);
//
//            quizLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
//            dictionaryLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
//            grammarLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
//            sentencesLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
//
//            // Thay đổi TextView và icon của tab được chọn
//            switch (tabNumber) {
//                case 1:
//                    quizTextView.setVisibility(View.VISIBLE);
//                    quizImage.setImageResource(R.drawable.quiz_icon_focus);
//                    quizLayout.setBackgroundResource(R.drawable.navigation_background_item);
//                    animateTab(quizLayout);
//                    break;
//                case 2:
//                    dictionaryTextView.setVisibility(View.VISIBLE);
//                    dictionaryImage.setImageResource(R.drawable.dictionary_icon_focus);
//                    dictionaryLayout.setBackgroundResource(R.drawable.navigation_background_item);
//                    animateTab(dictionaryLayout);
//                    break;
//                case 3:
//                    grammarTextView.setVisibility(View.VISIBLE);
//                    grammarImage.setImageResource(R.drawable.grammar_icon_focus);
//                    grammarLayout.setBackgroundResource(R.drawable.navigation_background_item);
//                    animateTab(grammarLayout);
//                    break;
//                case 4:
//                    sentencesTextView.setVisibility(View.VISIBLE);
//                    sentencesImage.setImageResource(R.drawable.sentence_icon_focus);
//                    sentencesLayout.setBackgroundResource(R.drawable.navigation_background_item);
//                    animateTab(sentencesLayout);
//                    break;
//            }
//
//            selectedTab = tabNumber;
//        }
//    }

    // Hàm để tạo hiệu ứng Scale Animation
//    private void animateTab(LinearLayout layout) {
//        ScaleAnimation scaleAnimation = new ScaleAnimation(0.8f, 1.0f, 1f, 1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
//        scaleAnimation.setDuration(200);
//        scaleAnimation.setFillAfter(true);
//        layout.startAnimation(scaleAnimation);
//    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }
}
