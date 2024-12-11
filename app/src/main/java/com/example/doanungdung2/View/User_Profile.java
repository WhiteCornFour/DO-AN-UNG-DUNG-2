package com.example.doanungdung2.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.doanungdung2.Controller.UserHandler;
import com.example.doanungdung2.Model.FileManager;
import com.example.doanungdung2.Model.SharedViewModel_User;
import com.example.doanungdung2.Model.User;
import com.example.doanungdung2.R;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

public class User_Profile extends AppCompatActivity {
    private static final String DB_NAME = "AppHocTiengAnh";
    private static final int DB_VERSION = 1;
    ImageView imgBackToMainPageUser, imgUserAvatar;
    LinearLayout linearLayoutMoveToTestListResult, linearLayoutMoveToBookmarkWord,
            linearLayoutMoveToEditUserAccount, linearLayoutMoveToPrivacy, linearLayoutMoveToReport, linearLayoutLogOut;
    GifImageView gifImageViewTestResult_User, gifImageViewBookMark_User, gifImageViewEdit_User,
            gifImageViewPrivacy_User, gifImageViewWarning_User, gifImageViewLogout_User;
    TextView tvUserNameProfile;
    UserHandler userHandler;
    SharedViewModel_User sharedViewModel_user;
    User user = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        addControl();
        sharedViewModel_user = new ViewModelProvider(this).get(SharedViewModel_User.class);
        userHandler = new UserHandler(User_Profile.this, DB_NAME, null, DB_VERSION);
        user = getUserIntent();
        if (user == null)
        {
            //lấy dữ liệu từ local lên để load thông tin cho người dùng
            SharedPreferences sharedPreferences = getSharedPreferences("ThongTinKhachHang", MODE_PRIVATE);
            String userName = sharedPreferences.getString("userName", null);
            String passWord = sharedPreferences.getString("passWord", null);
            user = userHandler.getUserInfo(userName, passWord);
        }
        //gan user vao trong sharedViewModel
        sharedViewModel_user.setUser(user);
//        Log.d("Ten nguoi dung profile: ", user.getTenNguoiDung());
        //dung Observe de lang nghe su kien cua SharedViewModel,sau do moi set up duoc thong tin ben trong
        sharedViewModel_user.getUser().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                setUpDataForProfile(user);
            }
        });
        addEvent();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStack();
    }

    void addControl() {
        imgBackToMainPageUser = findViewById(R.id.imgBackToMainPageUser);
        imgUserAvatar = findViewById(R.id.imgUserAvatar);
        tvUserNameProfile = findViewById(R.id.tvUserNameProfile);
        linearLayoutMoveToTestListResult = findViewById(R.id.linearLayoutMoveToTestListResult);
        linearLayoutMoveToBookmarkWord = findViewById(R.id.linearLayoutMoveToBookmarkWord);
        linearLayoutMoveToEditUserAccount = findViewById(R.id.linearLayoutMoveToEditUserAccount);
        linearLayoutMoveToPrivacy = findViewById(R.id.linearLayoutMoveToPrivacy);
        linearLayoutMoveToReport = findViewById(R.id.linearLayoutMoveToReport);
        linearLayoutLogOut = findViewById(R.id.linearLayoutLogOut);
        gifImageViewTestResult_User = findViewById(R.id.gifImageViewTestResult_User);
        gifImageViewBookMark_User = findViewById(R.id.gifImageViewBookMark_User);
        gifImageViewEdit_User = findViewById(R.id.gifImageViewEdit_User);
        gifImageViewPrivacy_User = findViewById(R.id.gifImageViewPrivacy_User);
        gifImageViewWarning_User = findViewById(R.id.gifImageViewWarning_User);
        gifImageViewLogout_User = findViewById(R.id.gifImageViewLogout_User);
    }

    void addEvent() {
        imgBackToMainPageUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        linearLayoutMoveToTestListResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(User_Profile.this, User_Test_Result.class);
                intent.putExtra("userFromProfileToTestListResult", user);
                startActivity(intent);
                //finish();
            }
        });

        linearLayoutMoveToBookmarkWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(User_Profile.this, User_BookmarkWord.class);
                intent.putExtra("userFromProfileToBookmarkWord", user);
                startActivity(intent);
                finish();
            }
        });

        linearLayoutMoveToEditUserAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(User_Profile.this, User_Details_Information.class);
                intent.putExtra("userFromProfileToUDI", user);
                startActivity(intent);
                finish();
            }
        });

        linearLayoutMoveToPrivacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(User_Profile.this, User_Privacy.class);
                intent.putExtra("userFromProfileToPrivacy", user);
                startActivity(intent);
                finish();
            }
        });

        linearLayoutMoveToReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(User_Profile.this, User_Report.class);
                intent.putExtra("userFromProfileToReport", user);
                startActivity(intent);
                finish();
            }
        });

        linearLayoutLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAlertDialogLogOut().show();
                //Khi logout cần clear thông tin của người dùng trong local để tránh trùng lập thông tin
                FileManager.updateAccountStatus(User_Profile.this, "AccountStatus.txt", "0");
                SharedPreferences sharedPreferences = getSharedPreferences("ThongTinKhachHang", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();
            }
        });
    }

    private User getUserIntent() {
        Intent intent = getIntent();
        User userIntent = null;
        userIntent = (User) intent.getSerializableExtra("userFromQuizFragmentToProfile");
        if (userIntent == null) {
            userIntent = (User) intent.getSerializableExtra("userFromMainPageToProfile");
        }
        if (userIntent == null) {
            userIntent = (User) intent.getSerializableExtra("userBackFromPageToProfile");
        }
        return userIntent;
    }

    private void setUpDataForProfile(User user) {
        //Thong tin của User được lấy từ ShareViewModel
        //Tuy nhiên nếu nó tui thì tiến hành lấy từ local
        if (user != null)
        {
            tvUserNameProfile.setText(user.getTenNguoiDung());
            byte[] anhNguoiDung = user.getAnhNguoiDung();
            if (anhNguoiDung == null || anhNguoiDung.length == 0) {
                imgUserAvatar.setImageResource(R.drawable.avt);
            } else {
                Bitmap bitmap = BitmapFactory.decodeByteArray(anhNguoiDung, 0, anhNguoiDung.length);
                imgUserAvatar.setImageBitmap(bitmap);
            }
        }else {
            //Tiến hành lấy thông tin từ local tải lên trang profile hiện tại
            SharedPreferences sharedPreferences = getSharedPreferences("ThongTinKhachHang", MODE_PRIVATE);
            String userName = sharedPreferences.getString("userName", null);
            String passWord = sharedPreferences.getString("passWord", null);
            user = userHandler.getUserInfo(userName, passWord);
            tvUserNameProfile.setText(user.getTenNguoiDung());
            byte[] anhNguoiDung = user.getAnhNguoiDung();
            if (anhNguoiDung == null || anhNguoiDung.length == 0) {
                imgUserAvatar.setImageResource(R.drawable.avt);
            } else {
                Bitmap bitmap = BitmapFactory.decodeByteArray(anhNguoiDung, 0, anhNguoiDung.length);
                imgUserAvatar.setImageBitmap(bitmap);
            }
        }
    }

    private AlertDialog createAlertDialogLogOut() {
        AlertDialog.Builder builder = new AlertDialog.Builder(User_Profile.this);
        builder.setTitle("Wanna Log Out ?");
        builder.setMessage("Are you sure you want to log out of your account?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                startActivity(new Intent(User_Profile.this,
                        User_Choose_Login.class));
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        return builder.create();
    }
}