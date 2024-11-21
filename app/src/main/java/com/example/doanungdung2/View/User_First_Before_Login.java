package com.example.doanungdung2.View;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.doanungdung2.R;

public class User_First_Before_Login extends AppCompatActivity {

    ImageView monkeyImage;
    TextView tvXinchao;
    Button btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_first_before_login);

        addControl();

        addEvent();

    }
    void addControl() {
        monkeyImage = findViewById(R.id.monkeyImage);
        tvXinchao = findViewById(R.id.tvXinChao);
        btnNext = findViewById(R.id.btnNext);
    }
    void addEvent() {
        Animation slideIn = AnimationUtils.loadAnimation(this, R.anim.text_slide_in);
        slideIn.setRepeatCount(1);// lap lai
        slideIn.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                Animation shake = AnimationUtils.loadAnimation(User_First_Before_Login.this, R.anim.wave);
                shake.setRepeatCount(1);
                monkeyImage.startAnimation(shake);
            }
            @Override
            public void onAnimationEnd(Animation animation) {
                monkeyImage.clearAnimation(); // Dừng hiệu ứng
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        tvXinchao.startAnimation(slideIn);

//        btnNext.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(User_First_Before_Login.this, User_Choose_Login.class);
//                startActivity(intent);
//                finish();
//            }
//        });

    }
}