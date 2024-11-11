package com.example.doanungdung2.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.doanungdung2.Controller.UserHandler;
import com.example.doanungdung2.Model.Dictionary;
import com.example.doanungdung2.Model.User;
import com.example.doanungdung2.R;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class User_Details_Information extends AppCompatActivity {
    private static final String DB_NAME = "AppHocTiengAnh";
    private static final int DB_VERSION = 1;
    ImageView backToQuizMainPage, imgAVT_User, imgLogoutAcc;
    TextView tvTenUser, tvSDTUser, tvEmailUser;
    Button btnEditUser;
    User user;
    User userLoad;
    UserHandler userHandler;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details_information);
        addControl();
        userHandler = new UserHandler(User_Details_Information.this, DB_NAME, null, DB_VERSION);
        Intent intent = getIntent();
        //user = new User();
        String tkReciveFromQuiz = intent.getStringExtra("tkFromQuizToDetail");
        String mkReciveFromQuiz = intent.getStringExtra("mkFromQuizToDetail");
        if (tkReciveFromQuiz != null && mkReciveFromQuiz != null)
        {
            user = userHandler.getUserInfo(tkReciveFromQuiz, mkReciveFromQuiz);
            loadDataUser(user);
        }else {
            //Intent intent = getIntent();
            String tk = intent.getStringExtra("username");
            String mk = intent.getStringExtra("password");
            if (tk != null && mk != null)
            {
                Log.d("tk on Resume details", tk);
                Log.d("mk on Resume details", mk);
                //userLoad = new User();
                userLoad = userHandler.getUserInfo(tk, mk);
                loadDataUser(userLoad);
            }else
            {
                loadDataUser(user);
            }
        }
        addEvent();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStack();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        Intent intent = getIntent();
//        String tk = intent.getStringExtra("username");
//        String mk = intent.getStringExtra("password");
//        if (tk != null && mk != null)
//        {
//            Log.d("tk on Resume details", tk);
//            Log.d("mk on Resume details", mk);
//            userLoad = new User();
//            userLoad = userHandler.getUserInfo(tk, mk);
//            loadDataUser(userLoad);
//        }else
//        {
//            loadDataUser(userLoad);
//        }
    }

    void addControl()
    {
        backToQuizMainPage = findViewById(R.id.backToQuizMainPage);
        imgAVT_User = findViewById(R.id.imgAVT_User);
        imgLogoutAcc = findViewById(R.id.imgLogoutAcc);
        tvTenUser = findViewById(R.id.tvTenUser);
        tvSDTUser = findViewById(R.id.tvSDTUser);
        tvEmailUser = findViewById(R.id.tvEmailUser);
        btnEditUser = findViewById(R.id.btnEditUser);
    }
    void addEvent()
    {
        backToQuizMainPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        imgLogoutAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAlertDialog().show();
            }
        });
        btnEditUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(User_Details_Information.this, User_Edit_Detail_Informations.class);
                if (userLoad != null)
                {
                    intent.putExtra("tkFromDetailToEdit", userLoad.getTaiKhoan());
                    intent.putExtra("mkFromDetailToEdit", userLoad.getMatKhau());
                    Log.d("tkFromDetailToEdit: ",userLoad.getTaiKhoan());
                    Log.d("mkFromDetailToEdit: ",userLoad.getMatKhau());
                }else if (user != null)
                {
                    intent.putExtra("tkFromDetailToEdit", user.getTaiKhoan());
                    intent.putExtra("mkFromDetailToEdit", user.getMatKhau());
                    Log.d("tkFromDetailToEdit: ",user.getTaiKhoan());
                    Log.d("mkFromDetailToEdit: ",user.getMatKhau());
                }
                startActivity(intent);
                finish();
            }
        });
    }
    @SuppressLint("SetTextI18n")
    void loadDataUser(User user)
    {
        if (user != null) {
            tvTenUser.setText("Full name: " + user.getTenNguoiDung());
            tvEmailUser.setText("Email: " + user.getEmail());
            tvSDTUser.setText("Phone number: " + user.getSoDienThoai());
            byte[] anhNguoiDung = user.getAnhNguoiDung();
            if (anhNguoiDung == null || anhNguoiDung.length == 0) {
                imgAVT_User.setImageResource(R.drawable.avt);
            } else {
                Bitmap bitmap = BitmapFactory.decodeByteArray(anhNguoiDung, 0, anhNguoiDung.length);
                imgAVT_User.setImageBitmap(bitmap);
            }
        }
    }
    private AlertDialog createAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(User_Details_Information.this);
        builder.setTitle("COMFIRM");
        builder.setMessage("Are you sure you want to sign out of your account?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                startActivity(new Intent(User_Details_Information.this,
                        User_Login.class));
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