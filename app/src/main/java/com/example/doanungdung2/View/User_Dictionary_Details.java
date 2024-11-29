package com.example.doanungdung2.View;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.doanungdung2.Controller.HistoryHandler;
import com.example.doanungdung2.Controller.UserHandler;
import com.example.doanungdung2.Model.Dictionary;
import com.example.doanungdung2.Model.User;
import com.example.doanungdung2.R;

public class User_Dictionary_Details extends AppCompatActivity {
    private static final String DB_NAME = "AppHocTiengAnh";
    private static final int DB_VERSION = 1;
    TextView tvTuTiengAnhDictionary, tvTuTiengVietDictionary, tvLoaiTuDictionary, tvGioiTuDictionary, tvCachPhatAmDictionary, tvNguCanhDictionary, tvNguCanhTVDictionary;
    ImageView imgBackToDictionaryFragment, imgTuVung, imgBookMark;
    HistoryHandler historyHandler;
    String maNguoiDung = "";
    String maTuVung= "";
    boolean isBookmarked = false;
    UserHandler userHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dictionary_details);
        addControl();
        historyHandler = new HistoryHandler(User_Dictionary_Details.this, DB_NAME, null, DB_VERSION);
        userHandler = new UserHandler(User_Dictionary_Details.this, DB_NAME, null, DB_VERSION);
        Intent intent = getIntent();
        Dictionary dictionary = (Dictionary) intent.getSerializableExtra("dictionary");

        maNguoiDung = User_Quiz_MainPage_Fragment.getIdMaNguoiDungStatic();
        if (maNguoiDung == null)
        {
            //lấy dữ liệu từ local lên để load thông tin cho người dùng
            SharedPreferences sharedPreferences = getSharedPreferences("ThongTinKhachHang", Context.MODE_PRIVATE);
            String userName = sharedPreferences.getString("userName", null);
            String passWord = sharedPreferences.getString("passWord", null);
            User user = userHandler.getUserInfo(userName, passWord);
            maNguoiDung = user.getMaNguoiDung();
        }
        maTuVung = dictionary.getMaTuVung();
        checkBookmarkStatus();
        setUpTextView(dictionary);
        addEvent();
    }

    void addControl() {
        imgBackToDictionaryFragment = findViewById(R.id.imgBackToDictionaryFragment);
        imgTuVung = findViewById(R.id.imgTuVung);
        imgBookMark = findViewById(R.id.imgBookMark);
        tvTuTiengAnhDictionary = findViewById(R.id.tvTuTiengAnhDictionary);
        tvTuTiengVietDictionary = findViewById(R.id.tvTuTiengVietDictionary);
        tvLoaiTuDictionary = findViewById(R.id.tvLoaiTuDictionary);
        tvGioiTuDictionary = findViewById(R.id.tvGioiTuDictionary);
        tvCachPhatAmDictionary = findViewById(R.id.tvCachPhatAmDictionary);
        tvNguCanhDictionary = findViewById(R.id.tvNguCanhDictionary);
        tvNguCanhTVDictionary = findViewById(R.id.tvNguCanhTVDictionary);
    }

    void addEvent() {
        imgBackToDictionaryFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        imgBookMark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ObjectAnimator fadeOut = ObjectAnimator.ofFloat(imgBookMark, "alpha", 1f, 0f);
                fadeOut.setDuration(300);

                fadeOut.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        if (isBookmarked) {
                            imgBookMark.setImageResource(R.drawable.baseline_bookmark_border_24);
                            historyHandler.updateDictionaryBookmark(null, maTuVung, maNguoiDung); // Bỏ bookmark
                        } else {
                            imgBookMark.setImageResource(R.drawable.baseline_bookmark_added_24_focus);
                            historyHandler.updateDictionaryBookmark("Save", maTuVung, maNguoiDung); // Thêm bookmark
                        }

                        ObjectAnimator fadeIn = ObjectAnimator.ofFloat(imgBookMark, "alpha", 0f, 1f);
                        fadeIn.setDuration(300);
                        fadeIn.start();

                        // Cập nhật trạng thái
                        isBookmarked = !isBookmarked;
                    }
                });

                fadeOut.start();
            }
        });



    }

    void setUpTextView(Dictionary dictionary) {
        tvTuTiengAnhDictionary.setText(dictionary.getTuTiengAnh());
        tvTuTiengVietDictionary.setText(dictionary.getTuTiengViet());
        tvLoaiTuDictionary.setText(dictionary.getLoaiTu());
        tvGioiTuDictionary.setText(dictionary.getGioiTuDiKem());
        tvCachPhatAmDictionary.setText(dictionary.getCachPhatAm());
        tvNguCanhDictionary.setText(dictionary.getViDu());
        tvNguCanhTVDictionary.setText(dictionary.getViDuTiengViet());

        byte[] imageBytes = dictionary.getAnhTuVung(); // Lấy ảnh từ đối tượng Dictionary
        if (imageBytes != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            imgTuVung.setImageBitmap(bitmap); // Thiết lập hình ảnh cho ImageView
        } else {
            imgTuVung.setImageResource(R.drawable.no_img); // Hình ảnh mặc định nếu không có
        }
    }

    private void checkBookmarkStatus() {
        String currentStatus = historyHandler.getDictionaryBookmarkStatus(maTuVung, maNguoiDung);

        if ("Save".equals(currentStatus)) {
            imgBookMark.setImageResource(R.drawable.baseline_bookmark_added_24_focus);
            isBookmarked = true;
        } else {
            imgBookMark.setImageResource(R.drawable.baseline_bookmark_border_24);
            isBookmarked = false;
        }

    }
}