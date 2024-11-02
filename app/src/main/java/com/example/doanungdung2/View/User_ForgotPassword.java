package com.example.doanungdung2.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doanungdung2.Controller.UserHandler;
import com.example.doanungdung2.R;

import java.util.ArrayList;
import java.util.Random;

public class User_ForgotPassword extends AppCompatActivity {

    private static final String DB_NAME = "AppHocTiengAnh";

    private static final int DB_VERSION = 1;
    EditText edtPhoneForgotPass;
    TextView tvSignInForgotPass;
    Button btnSendForgotPass;
    UserHandler userHandler;
    String otp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_forgotpassword);
        addControl();

        userHandler = new UserHandler(User_ForgotPassword.this, DB_NAME, null, DB_VERSION);

        addEvent();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(User_ForgotPassword.this, User_Login.class));
        super.onBackPressed();
    }

    void addControl()
    {
        edtPhoneForgotPass = findViewById(R.id.edtPhoneForgotPass);
        tvSignInForgotPass = findViewById(R.id.tvSignInForgotPass);
        btnSendForgotPass = findViewById(R.id.btnSendForgotPass);
    }

    void addEvent()
    {
        btnSendForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(User_ForgotPassword.this,
                        Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED){
                    sendOTP();

                }else {
                    ActivityCompat.requestPermissions(User_ForgotPassword.this,
                            new String[]{Manifest.permission.SEND_SMS}, 100);
                }
            }
        });
        tvSignInForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(User_ForgotPassword.this, User_Login.class);
                startActivity(intent);
                finish();
            }
        });
    }
    @SuppressLint("MissingSuperCall")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 100) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                sendOTP();
            }else {
                Toast.makeText(this, "Permisson Denied!", Toast.LENGTH_SHORT).show();
            }
        }
    }
    void sendOTP()
    {
        otp = generateCode();
        String phoneNumber = edtPhoneForgotPass.getText().toString();
        Boolean kq = userHandler.checkEnterPhoneNumberMatchPhoneDB(phoneNumber);

        if (phoneNumber.isEmpty() || !kq.equals(true) || !phoneNumber.matches("^\\d{10,}$"))
        {
            Toast.makeText(this, "Please check your numberphone again!", Toast.LENGTH_SHORT).show();
            return;
        }

        SmsManager smsManager = SmsManager.getDefault();
        ArrayList<String> parts = smsManager.divideMessage(otp + " is your OTP!");
        smsManager.sendMultipartTextMessage(phoneNumber, null, parts, null, null);

        //Mở activity Enter OTP và truyền otp qua activity đó để so sánh
        Intent intent = new Intent(User_ForgotPassword.this, User_Enter_OTP.class);
        intent.putExtra("otp", otp);
        intent.putExtra("phoneNumber", phoneNumber);
        startActivity(intent);
        finish();
    }
    //Hàm tạo mã tự động với mã là 5 chữ số
    public static String generateCode() {
        Random random = new Random();
        int code = 10000 + random.nextInt(90000); // Tạo số ngẫu nhiên từ 10000 đến 99999
        return String.valueOf(code);
    }
}