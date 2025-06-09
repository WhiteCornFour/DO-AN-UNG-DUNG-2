package com.example.doanungdung2.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.doanungdung2.Model.SharedViewModel_User;
import com.example.doanungdung2.Model.User;
import com.example.doanungdung2.R;

public class User_Privacy extends AppCompatActivity {

    ImageView imgBackToProfileFromPrivacy;
    SharedViewModel_User sharedViewModel_user;
    LinearLayout layoutChangeMyPassWord, layoutTwoStepVerification;
    User user = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_privacy);
        sharedViewModel_user = new ViewModelProvider(this).get(SharedViewModel_User.class);
        addControl();
        user = getIntentUser();
        Log.d("Ten nguoi dung Privacy: ",user.getTenNguoiDung());
        sharedViewModel_user.setUser(user);
        addEvent();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStack();
    }

    void addControl() {
        imgBackToProfileFromPrivacy = findViewById(R.id.imgBackToProfileFromPrivacy);
        layoutChangeMyPassWord = findViewById(R.id.layoutChangeMyPassWord);
        layoutTwoStepVerification = findViewById(R.id.layoutTwoStepVerification);
    }

    void addEvent() {
        imgBackToProfileFromPrivacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(User_Privacy.this, User_Profile.class);
                intent.putExtra("userBackFromPageToProfile", user);
                startActivity(intent);
                finish();
            }
        });

        layoutChangeMyPassWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(User_Privacy.this, User_Change_My_Password.class);
                intent.putExtra("userFromPrivacyToChangeMyPassword", user);
                startActivity(intent);
                finish();
            }
        });

        layoutTwoStepVerification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(User_Privacy.this, User_Two_Step_Verification.class);
                intent.putExtra("userFromPrivacyToTwoStepVerification", user);
                startActivity(intent);
                finish();
            }
        });
    }

    private User getIntentUser() {
        Intent intent = getIntent();
        User userIntent = (User) intent.getSerializableExtra("userFromProfileToPrivacy");
        if (userIntent == null) {
            userIntent = (User) intent.getSerializableExtra("userBackFromPageToPrivacy");
        }
        return userIntent;
    }

}