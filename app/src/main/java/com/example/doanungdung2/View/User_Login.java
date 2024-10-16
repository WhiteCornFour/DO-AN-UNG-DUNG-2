package com.example.doanungdung2.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doanungdung2.Controller.UserHandler;
import com.example.doanungdung2.R;

public class User_Login extends AppCompatActivity {

    EditText edtLoginAccount, edtLoginPassword;
    Button btnLogin;
    TextView tvRegisterNow, tvForgetPasswordLogin, tvLoginAD;
    UserHandler UserHandler;
    SQLiteDatabase sqLiteDatabase;

    private static final String DB_NAME = "AppHocTiengAnh";
    private static final int DB_VERSION = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        addControll();
        //--------
        UserHandler = new UserHandler(User_Login.this, DB_NAME, null, DB_VERSION);
        UserHandler.onCreate(sqLiteDatabase);
        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        String pn = sharedPreferences.getString("tài khoản", null);
        String ps = sharedPreferences.getString("mật khẩu", null);
        edtLoginAccount.setText(pn);
        edtLoginPassword.setText(ps);
        //----------------------
        addEvent();
    }
    void addControll(){
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

                boolean isValid = UserHandler.validateLogin(account, password);
                if (isValid) {
                    Toast.makeText(User_Login.this, "Login success", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(User_Login.this, MainActivity.class);
                    intent.putExtra("account", account);
                    intent.putExtra("password", password);

                    // Lưu ID user xuống local storage
                    String idUser = UserHandler.getIdUserr(account, password);
                    SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("idUser", idUser); //
                    editor.apply();

                    startActivity(intent);
                    resetEdt();
                } else {
                    Toast.makeText(getApplicationContext(), "Invalid account or password", Toast.LENGTH_LONG).show();
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
                finish();
            }
        });
        tvLoginAD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(User_Login.this, Admin_Login.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public boolean validateInputs(String account, String password) {
        if (account.trim().isEmpty() || account.trim().length() <= 8) {
            Toast.makeText(this, "Username must have at least 8 letters", Toast.LENGTH_SHORT).show();
            return false;
        }
        // Kiểm tra password có hơn 8 ký tự
        if (password.trim().isEmpty() || password.trim().length() <= 8) {
            Toast.makeText(this, "Password must have at least 8 letters", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        //---------------------------
        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        String pn = edtLoginAccount.getText().toString();
        String ps = edtLoginPassword.getText().toString();

        editor.putString("số điện thoại", pn);
        editor.putString("mật khẩu", ps);
        editor.apply();
    }

    void resetEdt() {
        edtLoginAccount.setText("");
        edtLoginPassword.setText("");
    }
}



