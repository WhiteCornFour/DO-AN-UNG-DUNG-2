package com.example.doanungdung2.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.example.doanungdung2.Controller.UserHandler;
import com.example.doanungdung2.Model.Dictionary;
import com.example.doanungdung2.Model.SharedViewModel_User;
import com.example.doanungdung2.Model.User;
import com.example.doanungdung2.R;
import com.google.android.material.textfield.TextInputEditText;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
    ImageView backToQuizMainPage, imgAVT_User;
    TextInputEditText edtNameUser, edtPhoneUser, edtEmailUser;
    Button btnEditUser;
    User user;
    UserHandler userHandler;
    SharedViewModel_User sharedViewModel_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details_information);
        addControl();

        sharedViewModel_user = new ViewModelProvider(this).get(SharedViewModel_User.class);

        userHandler = new UserHandler(User_Details_Information.this, DB_NAME, null, DB_VERSION);

        user = getIntentUser();
        sharedViewModel_user.setUser(user);
        sharedViewModel_user.getUser().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
//                Log.d("Ten Nguoi deung: Your Profile: ", user.getTenNguoiDung());
//                if (user.getTaiKhoan() != null && user.getMatKhau() != null)
//                {
//                    loadDataUser(user);
//                }else {
//                    //Intent intent = getIntent();
//                    String tk = intent.getStringExtra("username");
//                    String mk = intent.getStringExtra("password");
//                    if (tk != null && mk != null)
//                    {
//                        Log.d("tk on Resume details", tk);
//                        Log.d("mk on Resume details", mk);
//                        //userLoad = new User();
//                        userLoad = userHandler.getUserInfo(tk, mk);
//                        loadDataUser(userLoad);
//                    }else
//                    {
//                        loadDataUser(user);
//                    }
//                }
                loadDataUser(user);
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

    void addControl() {
        backToQuizMainPage = findViewById(R.id.backToQuizMainPage);
        imgAVT_User = findViewById(R.id.imgAVT_User);
        edtNameUser = findViewById(R.id.edtNameUser);
        edtPhoneUser = findViewById(R.id.edtPhoneUser);
        edtEmailUser = findViewById(R.id.edtEmailUser);
        btnEditUser = findViewById(R.id.btnEditUser);
    }

    void addEvent() {
        backToQuizMainPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(User_Details_Information.this, User_Profile.class);
                intent.putExtra("userBackFromPageToProfile", user);
                startActivity(intent);
                finish();
            }
        });
        btnEditUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(User_Details_Information.this, User_Edit_Detail_Informations.class);
                intent.putExtra("userToEditProfile", user);
//                if (userLoad != null)
//                {
//                    intent.putExtra("tkFromDetailToEdit", userLoad.getTaiKhoan());
//                    intent.putExtra("mkFromDetailToEdit", userLoad.getMatKhau());
//                    Log.d("tkFromDetailToEdit: ",userLoad.getTaiKhoan());
//                    Log.d("mkFromDetailToEdit: ",userLoad.getMatKhau());
//                }else if (user != null)
//                {
//                    intent.putExtra("tkFromDetailToEdit", user.getTaiKhoan());
//                    intent.putExtra("mkFromDetailToEdit", user.getMatKhau());
//                    Log.d("tkFromDetailToEdit: ",user.getTaiKhoan());
//                    Log.d("mkFromDetailToEdit: ",user.getMatKhau());
//                }
                startActivity(intent);
                finish();
            }
        });
    }

    void loadDataUser(User user) {
        if (user != null) {
            edtNameUser.setText(user.getTenNguoiDung());
            edtPhoneUser.setText(user.getSoDienThoai());
            edtEmailUser.setText(user.getEmail());
            byte[] anhNguoiDung = user.getAnhNguoiDung();
            if (anhNguoiDung == null || anhNguoiDung.length == 0) {
                imgAVT_User.setImageResource(R.drawable.avt);
            } else {
                Bitmap bitmap = BitmapFactory.decodeByteArray(anhNguoiDung, 0, anhNguoiDung.length);
                imgAVT_User.setImageBitmap(bitmap);
            }
        }
    }

    private User getIntentUser() {
        Intent intent = getIntent();
        User userIntent = (User) intent.getSerializableExtra("userFromProfileToUDI");
        if (userIntent == null) {
            userIntent = (User) intent.getSerializableExtra("userBackFromUEDIToUDI");
        }
        return userIntent;
    }

}