package com.example.doanungdung2.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.doanungdung2.R;

public class User_Choose_Login extends AppCompatActivity {

    Button btnmoveLogin, btnmoveRegister;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_first_before_login);

        addControl();

        addEvent();
    }
    void addControl() {
        btnmoveLogin = findViewById(R.id.button);
        btnmoveRegister = findViewById(R.id.button2);
    }
    void addEvent() {
        btnmoveLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(User_Choose_Login.this, User_Login.class);
                startActivity(intent);
            }
        });

        btnmoveRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(User_Choose_Login.this, User_Register.class);
                startActivity(intent);
            }
        });

    }
}
