package com.example.doanungdung2.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doanungdung2.Controller.UserHandler;
import com.example.doanungdung2.Model.Report;
import com.example.doanungdung2.Model.SharedViewModel_User;
import com.example.doanungdung2.Model.User;
import com.example.doanungdung2.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;
import java.util.regex.Pattern;

public class User_Change_My_Password extends AppCompatActivity {
    private static final String DB_NAME = "AppHocTiengAnh";
    private static final int DB_VERSION = 1;
    SharedViewModel_User sharedViewModel_user;
    ImageView imgBackToPrivacyMainPage;
    TextInputLayout layoutConfirmNewPassword;
    TextInputEditText edtOldPassword_CMP, edtNewPassword_CMP, edtConfirmNewPassword_CMP;
    Button btnChangeMyPassword;
    UserHandler userHandler;
    User user = new User();
    String OTPCode = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_change_my_password);
        addControl();
        sharedViewModel_user = new ViewModelProvider(this).get(SharedViewModel_User.class);
        userHandler = new UserHandler(User_Change_My_Password.this, DB_NAME, null, DB_VERSION);
        user = getIntentUser();
        addEvent();
    }

    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStack();
        super.onBackPressed();
    }

    void addControl() {
        imgBackToPrivacyMainPage = findViewById(R.id.imgBackToPrivacyMainPage);
        edtOldPassword_CMP = findViewById(R.id.edtOldPassword_CMP);
        edtNewPassword_CMP = findViewById(R.id.edtNewPassword_CMP);
        edtConfirmNewPassword_CMP = findViewById(R.id.edtConfirmNewPassword_CMP);
        btnChangeMyPassword = findViewById(R.id.btnChangeMyPassword);
    }

    void addEvent() {
        imgBackToPrivacyMainPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(User_Change_My_Password.this, User_Privacy.class);
                intent.putExtra("userBackFromPageToPrivacy", user);
                startActivity(intent);
                finish();
            }
        });

        btnChangeMyPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldPassword = edtOldPassword_CMP.getText().toString().trim();
                String newPassword = edtNewPassword_CMP.getText().toString().trim();
                String confirmNewPassword = edtConfirmNewPassword_CMP.getText().toString().trim();
                String maNguoiDung = user.getMaNguoiDung();

                if(oldPassword.isEmpty() || newPassword.isEmpty() || confirmNewPassword.isEmpty()) {
                    Toast.makeText(User_Change_My_Password.this, "Please fill all the blank before submit!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(!userHandler.checkEnterOldPasswordIsRight(maNguoiDung, oldPassword)) {
                    Toast.makeText(User_Change_My_Password.this, "Your password is incorrect!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(!newPassword.equals(confirmNewPassword)) {
                    Toast.makeText(User_Change_My_Password.this, "Your new password doesn't match the confirm password!", Toast.LENGTH_SHORT).show();
                    return;
                }
//                User userWithNewPassword = new User();
//                userWithNewPassword.setMaNguoiDung(maNguoiDung);
//                userWithNewPassword.setTenNguoiDung(user.getTenNguoiDung());
//                userWithNewPassword.setTaiKhoan(user.getTaiKhoan());
//                userWithNewPassword.setMatKhau(newPassword);
//                userWithNewPassword.setSoDienThoai(user.getSoDienThoai());
//                userWithNewPassword.setEmail(user.getEmail());
//                userWithNewPassword.setAnhNguoiDung(user.getAnhNguoiDung());
                checkAndSendOTP();
                showEnterOTPDialog(newPassword, maNguoiDung);
            }
        });

//        edtConfirmNewPassword_CMP.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                String confirmPassword = s.toString();
//                if(confirmPassword.equals(edtNewPassword_CMP.toString().trim())) {
//                    layoutConfirmNewPassword.setHelperText("Your password is the same with confirm password!");
//                    layoutConfirmNewPassword.setError("");
//                } else {
//                    layoutConfirmNewPassword.setHelperText("");
//                    layoutConfirmNewPassword.setError("The confirm password does not match the new password.");
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });
    }

    private User getIntentUser() {
        Intent intent = getIntent();
        User userIntent = (User) intent.getSerializableExtra("userFromPrivacyToChangeMyPassword");
        return userIntent;
    }

    //bat dau thoi gian dem OTP
    private void startCountdownTimer(TextView tvTimeSMSCountDown, Button sendSMSAgain) {
        new CountDownTimer(60000, 1000) { // 60000ms = 60s, 1000ms = 1s
            @Override
            public void onTick(long millisUntilFinished) {
                long seconds = millisUntilFinished / 1000;
                tvTimeSMSCountDown.setText(String.format(Locale.getDefault(), "%02d:%02d", seconds / 60, seconds % 60));
            }

            @Override
            public void onFinish() {
                tvTimeSMSCountDown.setText("00:00");
                sendSMSAgain.setEnabled(true);
                sendSMSAgain.setBackgroundResource(R.color.blue_pastel);
                Toast.makeText(User_Change_My_Password.this, "You can now send new OTP if you didn't the old one.", Toast.LENGTH_SHORT).show();
            }
        }.start();
    }

    private void showEnterOTPDialog(String newPassword, String maNguoiDung) {
        ConstraintLayout successConstraintLayout = findViewById(R.id.takingReportConstraintLayout);
        View view = LayoutInflater.from(User_Change_My_Password.this).inflate(R.layout.custom_submit_otp_new_password, successConstraintLayout);
        Button confirmNewPassword = view.findViewById(R.id.btnConfirmNewPassword);
        Button sendSMSAgain = view.findViewById(R.id.btnSendSMSAgain);
        ImageView imgExitDialog = view.findViewById(R.id.imgExitDialog);
        TextView tvTimeSMSCountDown = view.findViewById(R.id.tvTimeSMSCountDown);
        EditText edtFirstNumber = view.findViewById(R.id.edtFirstNumber);
        EditText edtSecondNumber = view.findViewById(R.id.edtSecondNumber);
        EditText edThirdNumber = view.findViewById(R.id.edtThirdNumber);
        EditText edtFourthNumber = view.findViewById(R.id.edtFourthNumber);
        EditText edtFifthNumber = view.findViewById(R.id.edtFifthNumber);

        AlertDialog.Builder builder = new AlertDialog.Builder(User_Change_My_Password.this);
        builder.setView(view);
        final AlertDialog alertDialog = builder.create();

        startCountdownTimer(tvTimeSMSCountDown, sendSMSAgain);

        confirmNewPassword.findViewById(R.id.btnConfirmNewPassword).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                String OTPSend = edtFirstNumber.getText().toString().trim() +
                        edtSecondNumber.getText().toString().trim() +
                        edThirdNumber.getText().toString().trim() +
                        edtFourthNumber.getText().toString().trim() +
                        edtFifthNumber.getText().toString().trim();
                if (OTPSend.isEmpty())
                {
                    Toast.makeText(User_Change_My_Password.this, "Please enter your OTP!", Toast.LENGTH_SHORT).show();
                    return;
                }else if (!OTPSend.equals(OTPCode))
                {
                    Toast.makeText(User_Change_My_Password.this, "Please check the OTP code again!", Toast.LENGTH_SHORT).show();
                    return;
                }else
                {
                    userHandler.updateUserPassword(newPassword, maNguoiDung);
                    sharedViewModel_user.setUser(user);
                    clearInputFields();
                    Toast.makeText(User_Change_My_Password.this, "You have change password successfully", Toast.LENGTH_SHORT).show();
                }
            }
        });

        sendSMSAgain.findViewById(R.id.btnSendSMSAgain).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAndSendOTP();
                startCountdownTimer(tvTimeSMSCountDown, sendSMSAgain);//khoi dong lai dem nguoc
            }
        });

        imgExitDialog.findViewById(R.id.imgExitDialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        if (alertDialog.getWindow() != null) {
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        alertDialog.show();
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

    void checkAndSendOTP() {
        if (checkSelfPermission(android.Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
            sendOTP();
        } else {
            requestPermissions(new String[]{android.Manifest.permission.SEND_SMS}, 100);
        }
    }

    void sendOTP()
    {
        OTPCode = generateCode();
        SmsManager smsManager = SmsManager.getDefault();
        ArrayList<String> parts = smsManager.divideMessage(OTPCode + " is your OTP!");
        smsManager.sendMultipartTextMessage(user.getSoDienThoai(), null, parts, null, null);
    }

    public static String generateCode() {
        Random random = new Random();
        int code = 10000 + random.nextInt(90000); // Tạo số ngẫu nhiên từ 10000 đến 99999
        return String.valueOf(code);
    }

    private void clearInputFields() {
        edtOldPassword_CMP.setText("");
        edtNewPassword_CMP.setText("");
        edtConfirmNewPassword_CMP.setText("");
    }
}