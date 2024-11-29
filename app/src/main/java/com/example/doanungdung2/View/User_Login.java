package com.example.doanungdung2.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doanungdung2.Controller.UserHandler;
import com.example.doanungdung2.Model.FileManager;
import com.example.doanungdung2.Model.User;
import com.example.doanungdung2.R;

public class User_Login extends AppCompatActivity {
    CheckBox cbswitchStatusLogin;
    EditText edtLoginAccount, edtLoginPassword;
    Button btnLogin;
    TextView tvRegisterNow, tvForgetPasswordLogin, tvLoginAD;
    UserHandler userHandler;
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
        cbswitchStatusLogin = findViewById(R.id.cbswitchStatusLogin);
    }
    void addEvent() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Khai báo đối tượng để lưu acc, pass của người dùng vào local
                SharedPreferences sharedPreferences = getSharedPreferences("ThongTinKhachHang", MODE_PRIVATE);

                String account = edtLoginAccount.getText().toString().trim();
                String password = edtLoginPassword.getText().toString().trim();
                User us = userHandler.getUserInfo(account, password);

                String verificationMode = us.getCheDoXacNhan();
                String verificationCode = us.getMaXacNhan();

                boolean isValid = userHandler.validateLogin(account, password);
                Log.d("code va mod verification mode", verificationCode + " , " + verificationMode);
                if (validateInputs(account, password))
                {
                    if (isValid) {
                        if (verificationMode.equals("On") && verificationCode != null) {
                            //Tiến hành lưu 2 thông tin của ng dùng vừa nhập vào local
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("userName", account);
                            editor.putString("passWord", password);
                            editor.apply();
                            showCheckingVerificationCodeDialog(us);
                        } else {
                            //Tiến hành lưu 2 thông tin của ng dùng vừa nhập vào local
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("userName", account);
                            editor.putString("passWord", password);
                            editor.apply();

                            Toast.makeText(User_Login.this, "Login success", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(User_Login.this, User_MainPage.class);
                            intent.putExtra("user", us);
                            startActivity(intent);
                            resetEdt();
                            finish();
                        }

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
        //Sự kiện bật/tắt ghi nhớ đăng nhập
        cbswitchStatusLogin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                //Nếu switch ở trạng thái On (Đang được chọn)
                if (compoundButton.isChecked())
                {
                    //Tiến hành cập nhật lại file AccountStatus với trạng thái 1
                    FileManager.updateAccountStatus(User_Login.this, "AccountStatus.txt", "1");
                }else {
                    //Nếu người dùng không ghi nhớ đăng nhập thì cập nhật trạng thái file AccountStatus là 0
                    FileManager.updateAccountStatus(User_Login.this, "AccountStatus.txt", "0");
                }
            }
        });

    }

    public boolean validateInputs(String account, String password) {
        if (account.trim().isEmpty()) {
            return false;
        }
        // Kiểm tra password có hơn 8 ký tự
        if (password.trim().isEmpty()) {
            return false;
        }
        return true;
    }

    void resetEdt() {
        edtLoginAccount.setText("");
        edtLoginPassword.setText("");
    }

    private void showCheckingVerificationCodeDialog(User us) {
        ConstraintLayout successConstraintLayout = findViewById(R.id.verificationCodeConstraintLayout);
        View view = LayoutInflater.from(User_Login.this).inflate(R.layout.custom_enter_verification_code_dialog, successConstraintLayout);

        EditText edtFirstNumber = view.findViewById(R.id.edtFirstNumber);
        EditText edtSecondNumber = view.findViewById(R.id.edtSecondNumber);
        EditText edtThirdNumber = view.findViewById(R.id.edtThirdNumber);
        EditText edtFourthNumber = view.findViewById(R.id.edtFourthNumber);
        EditText edtFifthNumber = view.findViewById(R.id.edtFifthNumber);
        Button btnEnterCode = view.findViewById(R.id.btnEnterCode);
        Button btnComeback = view.findViewById(R.id.btnComeback);
        String maXacNhan = us.getMaXacNhan();

        AlertDialog.Builder builder = new AlertDialog.Builder(User_Login.this);
        builder.setView(view);
        final AlertDialog alertDialog = builder.create();

        btnEnterCode.findViewById(R.id.btnEnterCode).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String verificationCode = edtFirstNumber.getText().toString().trim() +
                        edtSecondNumber.getText().toString().trim() +
                        edtThirdNumber.getText().toString().trim() +
                        edtFourthNumber.getText().toString().trim() +
                        edtFifthNumber.getText().toString().trim();
                if (verificationCode.isEmpty())
                {
                    Toast.makeText(User_Login.this, "Please enter code!", Toast.LENGTH_SHORT).show();
                    return;
                } else if (maXacNhan == null & !maXacNhan.equals(verificationCode)) {
                    Toast.makeText(User_Login.this, "Verification code is not valid!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    Toast.makeText(User_Login.this, "Login success", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(User_Login.this, User_MainPage.class);
                    intent.putExtra("user", us);
                    startActivity(intent);
                    resetEdt();
                    finish();
                    alertDialog.dismiss();
                }
            }

        });
        btnComeback.findViewById(R.id.btnComeback).setOnClickListener(new View.OnClickListener() {
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
}



