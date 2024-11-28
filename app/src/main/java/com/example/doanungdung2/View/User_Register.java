package com.example.doanungdung2.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doanungdung2.Controller.UserHandler;
import com.example.doanungdung2.Model.User;
import com.example.doanungdung2.R;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Random;

public class User_Register extends AppCompatActivity {

    private static final String DB_NAME = "AppHocTiengAnh";
    private static final int DB_VERSION = 1;

    private static final String TABLE_NAME = "NguoiDung";
    private static final String maNguoiDung = "MaNguoiDung";
    private static final String tenNguoiDung = "TenNguoiDung";
    private static final String taiKhoan = "TaiKhoan";
    private static final String matKhau = "MatKhau";
    private static final String soDienThoai = "SoDienThoai";
    private static final String email = "Email";
    private static final String anhNguoiDung = "AnhNguoiDung";
    private static final String PATH = "/data/data/com.example.doanungdung2/database/AppHocTiengAnh.db";

    EditText edtRegisterNameUser, edtRegisterAccount, edtRegisterPassword, edtRegisterRewritePassword, edtRegisterPhone, edtRegisterEmail;
    Button btnRegisterUser;
    TextView tvSignInRegister;

    UserHandler userHandler;

    ArrayList<User> userArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);
        addControl();
        userHandler = new UserHandler(User_Register.this, DB_NAME, null, DB_VERSION);
        userArrayList = userHandler.loadAllDataOfUser();
        addEvent();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(User_Register.this, User_Login.class));
        super.onBackPressed();
    }

    void addControl () {
        edtRegisterNameUser = (EditText) findViewById(R.id.edtRegisterNameUser);
        edtRegisterAccount = (EditText) findViewById(R.id.edtRegisterAccount);
        edtRegisterPassword = (EditText) findViewById(R.id.edtRegisterPassword);
        edtRegisterRewritePassword = (EditText) findViewById(R.id.edtRegisterRewritePassword);
        edtRegisterPhone = (EditText) findViewById(R.id.edtRegisterPhone);
        edtRegisterEmail = (EditText) findViewById(R.id.edtRegisterEmail);
        btnRegisterUser = (Button) findViewById(R.id.btnRegisterUser);
        tvSignInRegister = (TextView) findViewById(R.id.tvSignInRegister);

    }

    void addEvent() {
        tvSignInRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(User_Register.this, User_Login.class);
                startActivity(intent);
                finish();
            }
        });

        btnRegisterUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idUser = createAutoUserCode("ND");
                String nameUser = edtRegisterNameUser.getText().toString();
                String accountUser = edtRegisterAccount.getText().toString();
                String passUser = edtRegisterPassword.getText().toString();
                String passConfirm = edtRegisterRewritePassword.getText().toString();
                String phoneUser = edtRegisterPhone.getText().toString();
                String emailUser = edtRegisterEmail.getText().toString();
                Boolean variateResult = validateInputs(nameUser, accountUser, passUser, passConfirm, phoneUser, emailUser);

                if (variateResult) {
                    // Kiểm tra số điện thoại hoặc email có tồn tại không trước khi thêm mới
                    boolean isDuplicate = false;

                    for (User user : userArrayList) {
                        if (user.getSoDienThoai().equals(phoneUser)) {
                            Toast.makeText(User_Register.this, "This phone number has already been used!", Toast.LENGTH_SHORT).show();
                            isDuplicate = true;
                            break;
                        }
                        if (user.getEmail().equals(emailUser)) {
                            Toast.makeText(User_Register.this, "This email has already been used!", Toast.LENGTH_SHORT).show();
                            isDuplicate = true;
                            break;
                        }
                    }

                    // Nếu không trùng lặp, thực hiện thêm người dùng
                    if (!isDuplicate) {
                        User user = new User(idUser, nameUser, accountUser, passUser, phoneUser, emailUser, "Off", null, null);
                        userHandler.registerNewUserAccount(user);
                        resetEditText();
                        showSuccessRegisterDialog();
                    }
                }
            }
        });
    }

    private void showSuccessRegisterDialog() {
        ConstraintLayout successConstraintLayout = findViewById(R.id.successConstraintLayout);
        View view = LayoutInflater.from(User_Register.this).inflate(R.layout.custom_register_success_dialog, successConstraintLayout);
        Button successDone = view.findViewById(R.id.successDone);

        AlertDialog.Builder builder = new AlertDialog.Builder(User_Register.this);
        builder.setView(view);
        final AlertDialog alertDialog = builder.create();

        successDone.findViewById(R.id.successDone).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                Intent intent = new Intent(User_Register.this, User_Login.class);
                startActivity(intent);
                finish();
                Toast.makeText(User_Register.this, "Success!", Toast.LENGTH_SHORT).show();
            }
        });
        if (alertDialog.getWindow() != null) {
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        alertDialog.show();
    }

    void resetEditText() {
        edtRegisterNameUser.setText("");
        edtRegisterAccount.setText("");
        edtRegisterPassword.setText("");
        edtRegisterPhone.setText("");
        edtRegisterEmail.setText("");
    }

    public boolean validateInputs(String ten, String taikhoan, String matkhau, String xacnhanmk, String sdt, String email) {

        if (ten.trim().isEmpty()) {
            Toast.makeText(this, "Input Your Name!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (taikhoan.trim().isEmpty()) {
            Toast.makeText(this, "Input Your Account!", Toast.LENGTH_SHORT).show();
            return false;
        }
        // Kiểm tra password có hơn 8 ký tự
        if (matkhau.trim().isEmpty() || matkhau.trim().length() <= 8) {
            Toast.makeText(this, "Password must have at least 8 letters", Toast.LENGTH_SHORT).show();
            return false;
        }
        // Kiểm tra số điện thoại có đủ 10 số
        if (sdt.trim().isEmpty() || sdt.trim().length() != 10) {
            Toast.makeText(this, "Phone number must have 10 number", Toast.LENGTH_SHORT).show();
            return false;
        }
        // Kiểm tra email có đúng cấu trúc
        String emailPattern = "[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}";
        if (email.trim().isEmpty() || !email.trim().matches(emailPattern)) {
            Toast.makeText(this, "Incorrect Email !", Toast.LENGTH_LONG).show();
            return false;
        }
        // Kiểm tra password và confirmPassword giống nhau
        if (!matkhau.equals(xacnhanmk)) {
            Toast.makeText(this, "Please checking your password and confirm password is correct", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public static String createAutoUserCode(String kyTuDau)
    {
        Random random = new Random();
        // Tạo chuỗi số ngẫu nhiên 9 chữ số
        StringBuilder code = new StringBuilder(kyTuDau);
        for (int i = 0; i < 9; i++) {
            int digit = random.nextInt(10); // Tạo số ngẫu nhiên từ 0 đến 9
            code.append(digit);
        }
        return code.toString();
    }



}