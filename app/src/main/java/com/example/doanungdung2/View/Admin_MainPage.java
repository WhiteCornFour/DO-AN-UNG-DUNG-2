package com.example.doanungdung2.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.doanungdung2.R;
import com.google.android.material.navigation.NavigationView;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class Admin_MainPage extends AppCompatActivity {

    DrawerLayout drawerLayout;
    Toolbar toolbar;
    NavigationView navigationView;

    TextView tvTenAD, tvTKAD;

    long backpresstime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main_page);
        addControl();

        //----------------Gắn thông tin đăng nhập cho các text view bên header_layout
        View headerView = navigationView.getHeaderView(0);
        tvTenAD = headerView.findViewById(R.id.tvTenAD);
        tvTKAD = headerView.findViewById(R.id.tvTKAD);

        //Lấy thông tin đăng nhập của admin để hiển thị ở header nhận từ admin_login
        Intent intent = getIntent();
        String tenAdmin = intent.getStringExtra("tenAdmin");
        String emailAdmin = intent.getStringExtra("emailAdmin");

        SharedPreferences sharedPreferences = getSharedPreferences("ThongTinAdmin", MODE_PRIVATE);
        //Kiểm tra thông tin của admin đc gửi bằng intent có null hay không, nếu null thì hiển thị thông tin đã đc lưu dưới local trc đó
        if (tenAdmin != null || emailAdmin != null)
        {
            tvTenAD.setText(tenAdmin);
            tvTKAD.setText(emailAdmin);

            //Lưu trữ thông tin quản trị viên tạm thời vào local tránh mất thông tin để hiển thị
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("tenAdmin", tenAdmin);
            editor.putString("emailAdmin", emailAdmin);
            editor.apply();
        }else
        {
            String tenAd= sharedPreferences.getString("tenAdmin", null);
            String emailAd= sharedPreferences.getString("emailAdmin", null);
            tvTenAD.setText(tenAd);
            tvTKAD.setText(emailAd);
        }

        //Set up cho hamburger menu
        setSupportActionBar(toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new
                ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.open_nav, R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView = (NavigationView) findViewById(R.id.nav_drawer);
        Admin_ManagementCategory_MainPage_Fragment fragment = new Admin_ManagementCategory_MainPage_Fragment();
        replaceFragment(fragment);
        drawerLayout.closeDrawer(GravityCompat.START);

        addEvent();
    }

    @Override
    public void onBackPressed() {
        if(backpresstime + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
            return;
        } else {
            Toast.makeText(this, "Press back again to exit.", Toast.LENGTH_SHORT).show();
        }
        backpresstime = System.currentTimeMillis();
    }

    void addControl()
    {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_drawer);
    }
    void addEvent()
    {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.danhMucQuanLy:
                        Admin_ManagementCategory_MainPage_Fragment fm = new Admin_ManagementCategory_MainPage_Fragment();
                        replaceFragment(fm);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        return true;

                    case R.id.qLDangBaiTap:
                        Admin_ExercisesCategory_MainPage_Fragment admin_dangBaiTap_fragment = new Admin_ExercisesCategory_MainPage_Fragment();
                        replaceFragment(admin_dangBaiTap_fragment);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        return true;

                    case  R.id.qLBaiTap:
                        Admin_Exercise_MainPage_Fragment admin_exercise_mainPage_fragment = new Admin_Exercise_MainPage_Fragment();
                        replaceFragment(admin_exercise_mainPage_fragment);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        return true;

                    case R.id.qLMauCau:
                        Admin_MainPage_SampleSentence admin_mainPage_sampleSentence = new Admin_MainPage_SampleSentence();
                        replaceFragment(admin_mainPage_sampleSentence);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        return true;

                    case R.id.qLChuDeMauCau:
                        Admin_TopicSentence_Fragment admin_topicSentence_fragment = new Admin_TopicSentence_Fragment();
                        replaceFragment(admin_topicSentence_fragment);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        return true;

                    case  R.id.qLCauHoi:
                        Admin_Question_Mainpage_Fragment admin_question_mainpage_fragment = new Admin_Question_Mainpage_Fragment();
                        replaceFragment(admin_question_mainpage_fragment);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        return true;

                    case  R.id.qLDangNguPhap:
                        Admin_GrammarCategory_Mainpage_Fragment fm1 = new Admin_GrammarCategory_Mainpage_Fragment();
                        replaceFragment(fm1);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        return true;

                    case R.id.qLNguPhap:
                        Admin_Grammar_Mainpage_Fragment fm2 = new Admin_Grammar_Mainpage_Fragment();
                        replaceFragment(fm2);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        return true;
                    
                    case  R.id.qLTuDien:
                        Admin_Dictionary_MainPage_Fragment admin_dictionary_mainPage_fragment = new Admin_Dictionary_MainPage_Fragment();
                        replaceFragment(admin_dictionary_mainPage_fragment);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        return true;

                    case R.id.logoutAD:
//                        startActivity(new Intent(Admin_MainPage.this, Admin_Login.class));
//                        drawerLayout.closeDrawer(GravityCompat.START);
//                        finish();
                        createDialog();
                        return true;
                    default:
                        return false;
                }

            }
        });
    }
    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, fragment);
        fragmentTransaction.commit();
    }
    void createDialog() {
        // Tạo đối tượng AlertDialog.Builder để xây dựng hộp thoại
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Thiết lập tiêu đề cho hộp thoại
        builder.setTitle("Xác nhận");
        // Thiết lập thông báo cho hộp thoại
        builder.setMessage("Bạn có chắc muốn đăng xuất tài khoản?");
        //Thiết lập nút "OK" để đóng hộp thoại
        builder.setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss(); // Đóng hộp thoại khi người dùng nhấn "OK"

                //Xóa thông tin admin trong local để tránh trùng lặp thông tin khi có admin đăng nhập
                SharedPreferences sharedPreferences = getSharedPreferences("ThongTinAdmin", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear(); // Xóa tất cả các giá trị trong SharedPreferences
                editor.apply(); // Hoặc editor.commit();

                // Chuyển về trang login
                startActivity(new Intent(Admin_MainPage.this, Admin_Login.class));
                finish();
            }
        });
        // Thiết lập nút "Hủy" để đóng hộp thoại mà không thực hiện hành động gì
        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss(); // Đóng hộp thoại khi người dùng nhấn "Hủy"
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

}