package com.example.doanungdung2.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.doanungdung2.Controller.UserHandler;
import com.example.doanungdung2.R;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class User_Enter_OTP extends AppCompatActivity {

    private static final String DB_NAME = "AppHocTiengAnh";
    private static final int DB_VERSION = 1;
    EditText edtEnterOTP;
    Button btnBackToForgotPass, btnComfirmOTP;
    
    String otpRecive, phoneNumber;

    UserHandler userHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_enter_otp);
        addControl();

        userHandler = new UserHandler(User_Enter_OTP.this, DB_NAME, null, DB_VERSION);
        Intent intent = getIntent();
        otpRecive = intent.getStringExtra("otp");
        phoneNumber = intent.getStringExtra("phoneNumber");

        addEvent();
    }

    void addControl()
    {
        edtEnterOTP = findViewById(R.id.edtEnterOTP);
        btnBackToForgotPass = findViewById(R.id.btnBackToForgotPass);
        btnComfirmOTP = findViewById(R.id.btnComfirmOTP);
    }

    void addEvent()
    {
        btnBackToForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(User_Enter_OTP.this, User_ForgotPassword.class));
                finish();
            }
        });
        
        btnComfirmOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String otpEnter = edtEnterOTP.getText().toString();
                if (otpEnter.isEmpty())
                {
                    Toast.makeText(User_Enter_OTP.this, "Please enter OTP!", Toast.LENGTH_SHORT).show();
                }else if (!otpEnter.equals(otpRecive))
                {
                    Toast.makeText(User_Enter_OTP.this, "Please check the OTP code again!", Toast.LENGTH_SHORT).show();
                }else
                {
                    showSuccessRegisterDialog();
                }
            }
        });
    }
    @SuppressLint("MissingInflatedId")
    private void showSuccessRegisterDialog() {
        ConstraintLayout successConstraintLayout = findViewById(R.id.successConstraintLayout);
        View view = LayoutInflater.from(User_Enter_OTP.this).inflate(R.layout.custom_forgotpassword_success_dialog, successConstraintLayout);
        Button successDone = view.findViewById(R.id.successDone);
        TextView tv = view.findViewById(R.id.showPassword);
        AlertDialog.Builder builder = new AlertDialog.Builder(User_Enter_OTP.this);
        tv.setText("Your password is " + userHandler.returnPasswordForUser(phoneNumber));
        builder.setView(view);
        final AlertDialog alertDialog = builder.create();
        successDone.findViewById(R.id.successDone).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                startActivity(new Intent(User_Enter_OTP.this, User_ForgotPassword.class));
                finish();
                Toast.makeText(User_Enter_OTP.this, "Success!", Toast.LENGTH_SHORT).show();
            }
        });
        if (alertDialog.getWindow() != null) {
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        alertDialog.show();
    }
    void createDialog()
    {
        // Tạo dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // Nút "OK" để đóng dialog
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss(); // Đóng dialog khi người dùng nhấn OK

            }
        });
        // Hiển thị dialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}