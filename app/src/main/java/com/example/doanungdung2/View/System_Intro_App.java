package com.example.doanungdung2.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.doanungdung2.Model.FileManager;
import com.example.doanungdung2.R;

public class System_Intro_App extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_intro_app);
        //Tạo file theo PATH data/data/files để quản lý đăng nhập và lần đầu mở app
        boolean check = FileManager.checkAndCreateFiles(this);
        //Kiểm tra status của tài khoản nếu 1 là chưa đăng xuất
        //Ngước lại 0 là đã đăng xuất
        String status = FileManager.readAccountStatusFile(this, "AccountStatus.txt");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!check)
                {
                    //Đây là lần đâu tiên khi người dùng mở app thì chạy intro kèm trang chào mừng
                    startActivity(new Intent(System_Intro_App.this, User_First_Before_Login.class));
                    finish();
                }else {
                    if (status.equals("0")) //Nguoi dung da dang xuat truoc do, bat nguoi dung phai chon phuong thuc login
                    {
                        //Không phải là lần đâu tiên khi người dùng mở app thì chạy intro kèm trang chọn login or register
                        startActivity(new Intent(System_Intro_App.this, User_Choose_Login.class));
                        finish();
                    }else if (status.equals("1")) //Nguoi dung van chua dang xuat vao thang trang chu cua app
                    {
                        startActivity(new Intent(System_Intro_App.this, User_MainPage.class));
                        finish();
                    }
                }
            }
        }, 6000);
    }
}