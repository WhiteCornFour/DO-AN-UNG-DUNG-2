package com.example.doanungdung2.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doanungdung2.Controller.UserHandler;
import com.example.doanungdung2.Model.User;
import com.example.doanungdung2.R;

public class User_Login extends AppCompatActivity {

    EditText edtLoginAccount, edtLoginPassword;
    Button btnLogin;
    TextView tvRegisterNow, tvForgetPasswordLogin, tvLoginAD;
    UserHandler userHandler;
    SQLiteDatabase sqLiteDatabase;

    long backpresstime;
    private static final String DB_NAME = "AppHocTiengAnh";
    private static final int DB_VERSION = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        addControl();
        //--------
        userHandler = new UserHandler(User_Login.this, DB_NAME, null, DB_VERSION);
        userHandler.onCreate(sqLiteDatabase);
        //----------------------
        addEvent();
    }

    @Override
    public void onBackPressed() {
        if(backpresstime + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
            resetEdt();
            return;
        } else {
            Toast.makeText(this, "Press back again to exit.", Toast.LENGTH_SHORT).show();
        }
        backpresstime = System.currentTimeMillis();
    }

    void addControl(){
        edtLoginAccount = (EditText) findViewById(R.id.edtLoginAccount);
        edtLoginPassword = (EditText) findViewById(R.id.edtLoginPassword);
        btnLogin = (Button) findViewById(R.id.btnLoginUser);
        tvRegisterNow = (TextView) findViewById(R.id.tvRegisterNow);
        tvForgetPasswordLogin = (TextView) findViewById(R.id.tvForgotyourpassword);
        tvLoginAD = (TextView) findViewById(R.id.tvLoginAD);
    }
    void addEvent() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String account = edtLoginAccount.getText().toString().trim();
                String password = edtLoginPassword.getText().toString().trim();

                boolean isValid = userHandler.validateLogin(account, password);
                if (validateInputs(account, password))
                {
                    if (isValid) {
                        Toast.makeText(User_Login.this, "Login success", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(User_Login.this, User_MainPage.class);
                        User us = userHandler.getUserInfo(account, password);
                        intent.putExtra("user", us);
                        startActivity(intent);
                        resetEdt();
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "Invalid account or password", Toast.LENGTH_LONG).show();
                    }
                }else {
                    Toast.makeText(User_Login.this, "Check your information before Login", Toast.LENGTH_SHORT).show();
                }

            }
        });
        tvRegisterNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(User_Login.this, User_Register.class);
                startActivity(intent);
                finish();
            }
        });
        tvForgetPasswordLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(User_Login.this, User_ForgotPassword.class);
                startActivity(intent);
                resetEdt();
                finish();
            }
        });
        tvLoginAD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(User_Login.this, Admin_Login.class);
                startActivity(intent);
                resetEdt();
                finish();
            }
        });


    }

    public boolean validateInputs(String account, String password) {
        if (account.trim().isEmpty() || account.trim().length() <= 8) {
            return false;
        }
        // Kiểm tra password có hơn 8 ký tự
        if (password.trim().isEmpty() || password.trim().length() <= 8) {
            return false;
        }
        return true;
    }

    void resetEdt() {
        edtLoginAccount.setText("");
        edtLoginPassword.setText("");
    }
}



