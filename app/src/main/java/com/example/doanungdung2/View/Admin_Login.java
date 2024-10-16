package com.example.doanungdung2.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doanungdung2.Controller.AdminHandler;
import com.example.doanungdung2.R;

public class Admin_Login extends AppCompatActivity {

    EditText edtLoginAccount_AD, edtLoginPassword_AD;
    Button btnLoginAD;
    TextView tvLoginUser;
    AdminHandler adminHandler;
    SQLiteDatabase sqLiteDatabase;

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

                boolean isValid = adminHandler.validateLoginAdmin(account, pass);
                if (isValid) {
                    Toast.makeText(Admin_Login.this, "Login success", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(Admin_Login.this,
                           MainActivity.class);
                    intent.putExtra("account", account);
                    intent.putExtra("pass", pass);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Invalid Account or Password", Toast.LENGTH_LONG).show();
                }
            }
            //}
        });
    }
    public boolean validateInputs(String account, String pass) {
        if (account.trim().isEmpty() || account.trim().length() <= 8) {
            Toast.makeText(this, "Account must have at least 8 letters", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (pass.trim().isEmpty() || pass.trim().length() <= 8) {
            Toast.makeText(this, "Password must have at least 8 letters", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}

