package com.example.doanungdung2.View;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.doanungdung2.Model.Dictionary;
import com.example.doanungdung2.R;

public class User_Dictionary_Details extends AppCompatActivity {

    TextView tvTuTiengAnhDictionary, tvTuTiengVietDictionary, tvLoaiTuDictionary, tvGioiTuDictionary, tvCachPhatAmDictionary, tvNguCanhDictionary, tvNguCanhTVDictionary;
    ImageView imgBackToDictionaryFragment, imgTuVung, imgBookMark;
    boolean isBookmarked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dictionary_details);
        addControl();

        Intent intent = getIntent();
        Dictionary dictionary = (Dictionary) intent.getSerializableExtra("dictionary");
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
                if (isBookmarked) {
                    ObjectAnimator fadeOut = ObjectAnimator.ofFloat(imgBookMark, "alpha", 1f, 0f); //hieu ung mo dan roi hien len
                    fadeOut.setDuration(300);
                    fadeOut.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            imgBookMark.setImageResource(R.drawable.baseline_bookmark_border_24); //doi hinh khi bookmark bi doi
                            ObjectAnimator fadeIn = ObjectAnimator.ofFloat(imgBookMark, "alpha", 0f, 1f); // hieu ung mo dan roi mat di
                            fadeIn.setDuration(300); // Thời gian animation
                            fadeIn.start(); //bat dau hieu ung fade in
                        }
                    });
                    fadeOut.start(); //bat dau hieu ung
                } else {
                    ObjectAnimator fadeOut = ObjectAnimator.ofFloat(imgBookMark, "alpha", 1f, 0f); // Fade out
                    fadeOut.setDuration(300);
                    fadeOut.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            imgBookMark.setImageResource(R.drawable.baseline_bookmark_added_24_focus); //doi hinh
                            ObjectAnimator fadeIn = ObjectAnimator.ofFloat(imgBookMark, "alpha", 0f, 1f); // Fade in
                            fadeIn.setDuration(300);
                            fadeIn.start(); //bat dau hieu ung fade in
                        }
                    });
                    fadeOut.start(); //ket thuc hieu ung lam mo
                }

                isBookmarked = !isBookmarked;
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
}