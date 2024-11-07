package com.example.doanungdung2.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.doanungdung2.Controller.UserHandler;
import com.example.doanungdung2.Model.Dictionary;
import com.example.doanungdung2.Model.User;
import com.example.doanungdung2.R;
import com.google.android.material.imageview.ShapeableImageView;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class User_Edit_Detail_Informations extends AppCompatActivity {
    private static final String DB_NAME = "AppHocTiengAnh";
    private static final int DB_VERSION = 1;
    ImageView backToProfile, imgEditImg_User, imgEditAVT_User;
    EditText edtFullName_User, edtPhoneNumber_User, edtEmail_User;
    Button btnComfirmEdit_User;
    UserHandler userHandler;
    User userOld;
    User userNew;
    byte[] imageBytes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_edit_detail_informations);
        addControl();
        userHandler = new UserHandler(User_Edit_Detail_Informations.this, DB_NAME, null, DB_VERSION);
        Intent intent = getIntent();
        userOld = (User) intent.getSerializableExtra("UserEdit");
        edtFullName_User.setText(userOld.getTenNguoiDung());
        edtPhoneNumber_User.setText(userOld.getSoDienThoai());
        edtEmail_User.setText(userOld.getEmail());
        byte[] anhNguoiDung = userOld.getAnhNguoiDung();
        if (anhNguoiDung == null || anhNguoiDung.length == 0) {
            imgEditAVT_User.setImageResource(R.drawable.avt);
        } else {
            Bitmap bitmap = BitmapFactory.decodeByteArray(anhNguoiDung, 0, anhNguoiDung.length);
            imgEditAVT_User.setImageBitmap(bitmap);
        }
        addEvent();
    }
    void addControl()
    {
        backToProfile = findViewById(R.id.backToProfile);
        imgEditImg_User = findViewById(R.id.imgEditImg_User);
        imgEditAVT_User = findViewById(R.id.imgEditAVT_User);
        edtFullName_User = findViewById(R.id.edtFullName_User);
        edtPhoneNumber_User = findViewById(R.id.edtPhoneNumber_User);
        edtEmail_User = findViewById(R.id.edtEmail_User);
        btnComfirmEdit_User = findViewById(R.id.btnComfirmEdit_User);
    }
    void addEvent()
    {
        backToProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(User_Edit_Detail_Informations.this, User_Details_Information.class);
                intent.putExtra("UserUpdated", userNew);
                startActivity(intent);
                finish();
            }
        });
        imgEditImg_User.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 1);
            }
        });
        btnComfirmEdit_User.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tenNguoiDung = edtFullName_User.getText().toString();
                String sdt = edtPhoneNumber_User.getText().toString();
                String email = edtEmail_User.getText().toString();
                userNew = new User(userOld.getMaNguoiDung(), tenNguoiDung, userOld.getTaiKhoan(), userOld.getMatKhau(),
                        sdt, email, imageBytes);
                //Kiểm tra dữ liệu nhập vào có trống hay sai định dạng hay không
                if (validInforInput(userNew))
                {
                    //Kiểm tra tên và ảnh có thay đổi mới hay chỉ là tên và ảnh cũ
                    if (!Objects.equals(userNew.getTenNguoiDung(), userOld.getTenNguoiDung()) || userNew.getAnhNguoiDung() != userOld.getAnhNguoiDung())
                    {
                        //Kiểm tra xem người dùng có nhập số điện mới hay không
                        if (!Objects.equals(userNew.getSoDienThoai(), userOld.getSoDienThoai()))
                        {
                            //Nếu người dùng nhập số điện thoại mới thì kiểm tra xem trong data đã tồn tại 1 sdt như vậy chưa
                            boolean checkPhone = userHandler.checkPhoneAndEmailBeforeUpdate(sdt);
                            //Nếu trong db chưa tồn tại 1 sdt mới mà người dùng nhập vào thì cho ng dùng cập nhật
                            if (!checkPhone)
                            {
                                createAlertDialog(userNew).show();
                            }else
                            {
                                Toast.makeText(User_Edit_Detail_Informations.this,
                                        "This phone number already exists!", Toast.LENGTH_SHORT).show();
                            }
                            //Nếu người dùng nhập vào 1 email mới thì kiểm tra xem trong data đã có email này chưa
                        }else if (!Objects.equals(userNew.getEmail(), userOld.getEmail()))
                        {
                            //Nếu chưa tồn tại email nào như vậy thì cho người dùng cập nhật với email mới
                            boolean checkEmail = userHandler.checkPhoneAndEmailBeforeUpdate(email);
                            if (!checkEmail)
                            {
                                createAlertDialog(userNew).show();
                            }else
                            {
                                Toast.makeText(User_Edit_Detail_Informations.this,
                                        "This email already exists!", Toast.LENGTH_SHORT).show();
                            }
                            //nếu như email và sđt cũ thì không cần kiểm tra cho phép người dùng cập những thông tin thay đổi
                            //như là tên or ảnh
                        }else {
                            createAlertDialog(userNew).show();
                        }
                    }else
                    {
                        Toast.makeText(User_Edit_Detail_Informations.this, "Your information has not changed!",
                                Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(User_Edit_Detail_Informations.this,
                            "Please check your information again!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            // Get the image from the gallery
            Uri selectedImage = data.getData();
            try {
                // Decode the image and display it in the ImageView
                Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImage));
                imgEditAVT_User.setImageBitmap(bitmap);

                // Optional: Convert the bitmap to byte array if you need to store it in the database
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                imageBytes = byteArrayOutputStream.toByteArray();

                // You can now use imageBytes to save the image in the database
                // For example, you can set it to the product object
                // products.setImageProduct(imageBytes);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(this, "Unable to load image", Toast.LENGTH_SHORT).show();
            }
        }
    }
    boolean validInforInput(User u)
    {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}";
        if (u.getTenNguoiDung().isEmpty())
            return false;
        else if (u.getEmail().isEmpty() || !u.getEmail().trim().matches(emailPattern))
            return false;
        else if (u.getSoDienThoai().isEmpty() || Integer.parseInt(String.valueOf(u.getSoDienThoai().length())) != 10)
            return false;
        return true;
    }
    private AlertDialog createAlertDialog(User user) {
        AlertDialog.Builder builder = new AlertDialog.Builder(User_Edit_Detail_Informations.this);
        builder.setTitle("Update information");
        builder.setMessage("Are you sure you want to update your information?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                userHandler.upDateUserInfor(userNew);
                Toast.makeText(User_Edit_Detail_Informations.this, "Updated information successfully!", Toast.LENGTH_SHORT).show();
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