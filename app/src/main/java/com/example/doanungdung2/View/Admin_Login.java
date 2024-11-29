package com.example.doanungdung2.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doanungdung2.Controller.AdminHandler;
import com.example.doanungdung2.Model.Admin;
import com.example.doanungdung2.R;

import java.util.ArrayList;

public class Admin_Login extends AppCompatActivity {

    EditText edtLoginAccount_AD, edtLoginPassword_AD;
    Button btnLoginAD;
    TextView tvLoginUser;
    AdminHandler adminHandler;
    SQLiteDatabase sqLiteDatabase;

    String tenAdmin, emailAdmin;
    private static final String DB_NAME = "AppHocTiengAnh";
    private static final int DB_VERSION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
        addControl();
        adminHandler= new AdminHandler(Admin_Login.this, DB_NAME, null, DB_VERSION);
        adminHandler.onCreate(sqLiteDatabase);
        addEvent();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(Admin_Login.this, User_Login.class));
        super.onBackPressed();
    }

    void addControl() {
        edtLoginAccount_AD = (EditText) findViewById(R.id.edtLoginAccount_AD);
        edtLoginPassword_AD = (EditText) findViewById(R.id.edtLoginPassword_AD);
        btnLoginAD = (Button) findViewById(R.id.btnLoginAD);
        tvLoginUser = (TextView) findViewById(R.id.tvLoginUser);
    }

    void addEvent() {
        tvLoginUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Admin_Login.this,
                        User_Login.class);
                startActivity(intent);
                finish();
            }
        });
        btnLoginAD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String account = edtLoginAccount_AD.getText().toString();
                String pass = edtLoginPassword_AD.getText().toString();
                if (validateInputs(account, pass))
                {
                    boolean isValid = adminHandler.validateLoginAdmin(account, pass);
                    if (isValid) {
                        Toast.makeText(Admin_Login.this, "Login success", Toast.LENGTH_LONG).show();
                        ArrayList<Admin> adminArrayList = adminHandler.getNameAndEmailOfAdmin(account, pass);
                        for (Admin n : adminArrayList) {
                            tenAdmin = n.getTenAdmin();
                            emailAdmin = n.getEmail();
                        }
                        Intent intent = new Intent(Admin_Login.this,
                                Admin_MainPage.class);

                        intent.putExtra("tenAdmin", tenAdmin);
                        intent.putExtra("emailAdmin", emailAdmin);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "Invalid Account or Password", Toast.LENGTH_LONG).show();
                    }
                }else {
                    return;
                }
            }
            //}
        });
    }
    public boolean validateInputs(String account, String pass) {
        if (account.trim().isEmpty()) {
            Toast.makeText(this, "You must input something in the account box!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (pass.trim().isEmpty()) {
            Toast.makeText(this, "You must input something in the password box!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}

