package com.example.doanungdung2.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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
    TextView tvRegisterSignIn;

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

    void addControl () {
        edtRegisterNameUser = (EditText) findViewById(R.id.edtRegisterNameUser);
        edtRegisterAccount = (EditText) findViewById(R.id.edtRegisterAccount);
        edtRegisterPassword = (EditText) findViewById(R.id.edtRegisterPassword);
        edtRegisterRewritePassword = (EditText) findViewById(R.id.edtRegisterRewritePassword);
        edtRegisterPhone = (EditText) findViewById(R.id.edtRegisterPhone);
        edtRegisterEmail = (EditText) findViewById(R.id.edtRegisterEmail);
        btnRegisterUser = (Button) findViewById(R.id.btnRegisterUser);
        tvRegisterSignIn = (TextView) findViewById(R.id.tvSignInForgotPass);
    }

    void addEvent() {
//        tvRegisterSignIn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                Intent intent = new Intent(User_Register.this, User_SignIn.class);
////                startActivity(intent);
////                finish();
//            }
//        });

        btnRegisterUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idUser = generateUserID();
                String nameUser = edtRegisterNameUser.getText().toString();
                String accountUser = edtRegisterAccount.getText().toString();
                String passUser = edtRegisterPassword.getText().toString();
                String passConfirm = edtRegisterRewritePassword.getText().toString();
                String phoneUser = edtRegisterPhone.getText().toString();
                String emailUser = edtRegisterEmail.getText().toString();
                Boolean variateResult = validateInputs(nameUser, accountUser, passUser, passConfirm, phoneUser, emailUser);

                if (variateResult.equals(true)) {
                    for (int i = 0; i < userArrayList.size(); i++) {
                        if (userArrayList.get(i).getSoDienThoai().equals(phoneUser) || userArrayList.get(i).getEmail().equals(emailUser)) {
                            Toast.makeText(User_Register.this, "This phone or email have been used !!! Please try another one.", Toast.LENGTH_SHORT).show();
                            break;
                        } else {
                            User user = new User(idUser, nameUser, accountUser, passUser, phoneUser, emailUser, null);
                            userHandler.registerNewUserAccount(user);
                            resetEditText();
                            AlertDialog alertDialog = createAlertDialogRegister();
                            alertDialog.show();
                    }
                    }
                }
            }
        });
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

    public static String generateUserID() {
        // Lấy ngày và giờ hiện tại
        LocalDateTime now = LocalDateTime.now();

        // Định dạng ngày giờ
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String formattedDateTime = now.format(formatter);

        // Tạo số ngẫu nhiên từ 1000 đến 9999
        Random random = new Random();
        int randomNumber = random.nextInt(9000) + 1000;

        // Kết hợp ngày giờ và số ngẫu nhiên để tạo mã MKH
        String mkh = formattedDateTime + randomNumber;

        return mkh;
    }

    AlertDialog createAlertDialogRegister() {
        AlertDialog.Builder builder = new AlertDialog.Builder(User_Register.this);
        builder.setTitle("Register account successfully !!!");
        builder.setMessage("Please sign in your account !");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
//                Intent intent = new Intent(User_Register.this, User_Login.class);
//                startActivity(intent);
//                finish();
            }
        });
        return builder.create();
    }

}