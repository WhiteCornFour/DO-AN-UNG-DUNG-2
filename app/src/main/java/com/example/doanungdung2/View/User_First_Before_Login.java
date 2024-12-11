package com.example.doanungdung2.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.ColorUtils;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.doanungdung2.R;

public class User_First_Before_Login extends AppCompatActivity {

    ConstraintLayout mainLayout;
    TextView tvEasy, tvWelcome;
    Button btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_first_before_login);

        addControl();

        addEvent();

    }
    void addControl() {
        mainLayout = findViewById(R.id.mainLayout);
        tvEasy = findViewById(R.id.tvEasy);
        tvWelcome = findViewById(R.id.tvWelcome);
        btnNext = findViewById(R.id.btnNext);
    }
    void addEvent() {
        ConstraintLayout constraintLayout = findViewById(R.id.mainLayout);
        AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2500);
        animationDrawable.setExitFadeDuration(5000);
        animationDrawable.start();

//        Animation slideIn = AnimationUtils.loadAnimation(this, R.anim.text_slide_in);
//        slideIn.setRepeatCount(1);// lap lai
//        slideIn.setAnimationListener(new Animation.AnimationListener() {
//            @Override
//            public void onAnimationStart(Animation animation) {
//                Animation shake = AnimationUtils.loadAnimation(User_First_Before_Login.this, 3R.anim.wave);
//                shake.setRepeatCount(1);
//                monkeyImage.startAnimation(shake);
//            }
//            @Override
//            public void onAnimationEnd(Animation animation) {
//                monkeyImage.clearAnimation(); // Dừng hiệu ứng
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//            }
//        });
//        tvXinchao.startAnimation(slideIn);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(User_First_Before_Login.this, User_Choose_Login.class);
                startActivity(intent);
                finish();
            }
        });

    }
}