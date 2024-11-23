package com.example.doanungdung2.View;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.doanungdung2.Controller.TopicSentenceHandler;
import com.example.doanungdung2.Model.TopicSentence;
import com.example.doanungdung2.R;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class Admin_Add_Topic_SampleSentence extends AppCompatActivity {

    ImageView imgBackCDMC, imgAnhChuDe;;
    ListView lvAddCDMC;
    EditText edtTenAddCDMC, edtMoTaAddCDMC, edtMaAddCDMC;
    Button btnThemCDMC, btnLamMoi;
    ArrayList<TopicSentence> topicSentenceArrayList = new ArrayList<>();
    Admin_Add_Topic_SampleSentence_CustomAdapter_LV admin_Add_Topic_SampleSentence_CustomAdapter_LV;
    TopicSentenceHandler topicSentenceHandler;

    private static final String DB_NAME = "AppHocTiengAnh";
    private static final int DB_VERSION = 1;
    private static final int PICK_IMAGE_REQUEST = 1;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_topic_sample_sentence);

        addControl();
        addEvent();

        topicSentenceHandler = new TopicSentenceHandler(this, DB_NAME, null, DB_VERSION);
        topicSentenceArrayList = topicSentenceHandler.loadAllDataOfTopicSentence();
        admin_Add_Topic_SampleSentence_CustomAdapter_LV = new Admin_Add_Topic_SampleSentence_CustomAdapter_LV(this, R.layout.activity_admin_add_topic_sample_sentence_custom_adapter_lv, topicSentenceArrayList);
        lvAddCDMC.setAdapter(admin_Add_Topic_SampleSentence_CustomAdapter_LV);

        String maCDMC = generateMaChuDeMauCau();
        edtMaAddCDMC.setText(maCDMC);

    }

    void addControl() {
        lvAddCDMC = (ListView) findViewById(R.id.lvAdd_CDMC);
        edtMaAddCDMC = (EditText) findViewById(R.id.edtMaAdd_CDMC);
        imgBackCDMC = (ImageView) findViewById(R.id.imgBack_CDMC);
        imgAnhChuDe = (ImageView) findViewById(R.id.imgAnhChuDe);
        edtTenAddCDMC = (EditText) findViewById(R.id.edtTenAdd_CDMC);
        edtMoTaAddCDMC = (EditText) findViewById(R.id.edtMoTaAdd_CDMC);
        btnThemCDMC = (Button) findViewById(R.id.btnThem_CDMC);
        btnLamMoi = (Button) findViewById(R.id.btnLamMoi_CDMC);
    }

    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStack();
    }

    void addEvent() {
        imgBackCDMC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnThemCDMC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTopicSentence();
            }
        });

        lvAddCDMC.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TopicSentence mc = topicSentenceArrayList.get(position);
                edtTenAddCDMC.setText(mc.getTenChuDeMauCau());
                edtMoTaAddCDMC.setText(mc.getMoTa());
                edtMaAddCDMC.setText(mc.getMaChuDeMauCau());

                byte[] imageBytes = mc.getAnhChuDeMauCau();
                if (imageBytes != null && imageBytes.length > 0) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                    imgAnhChuDe.setImageBitmap(bitmap);
                } else {
                    imgAnhChuDe.setImageResource(R.drawable.image_default);
                }
                edtTenAddCDMC.setFocusable(false);
                edtTenAddCDMC.setClickable(false);
                edtTenAddCDMC.setInputType(InputType.TYPE_NULL); // Vô hiệu hóa bàn phím

                edtMoTaAddCDMC.setFocusable(false);
                edtMoTaAddCDMC.setClickable(false);
                edtMoTaAddCDMC.setInputType(InputType.TYPE_NULL);

                imgAnhChuDe.setClickable(false); // Không cho phép đổi ảnh
            }
        });

        btnLamMoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Reset();
            }
        });

        imgAnhChuDe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImageChoose();
            }
        });
    }

    private void openImageChoose() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }


    private void Reset() {
        if (edtTenAddCDMC.getText().toString().trim().isEmpty()
                && edtMoTaAddCDMC.getText().toString().trim().isEmpty()
                && imgAnhChuDe.getDrawable() == null) {

            Toast.makeText(this, "Không có thông tin để làm mới", Toast.LENGTH_SHORT).show();
        } else {
            edtTenAddCDMC.setText("");
            edtMoTaAddCDMC.setText("");
            edtMaAddCDMC.setText(generateMaChuDeMauCau());
            imgAnhChuDe.setImageResource(R.drawable.image_default);
        }
        edtTenAddCDMC.setFocusable(true);
        edtTenAddCDMC.setFocusableInTouchMode(true);
        edtTenAddCDMC.setClickable(true);
        edtTenAddCDMC.setInputType(InputType.TYPE_CLASS_TEXT);

        edtMoTaAddCDMC.setFocusable(true);
        edtMoTaAddCDMC.setFocusableInTouchMode(true);
        edtMoTaAddCDMC.setClickable(true);
        edtMoTaAddCDMC.setInputType(InputType.TYPE_CLASS_TEXT);
        imgAnhChuDe.setClickable(true);
}

    private void addTopicSentence() {
        String maCDMC = edtMaAddCDMC.getText().toString().trim();
        String tenCDMC = edtTenAddCDMC.getText().toString().trim();
        String moTa = edtMoTaAddCDMC.getText().toString().trim();


        if (tenCDMC.isEmpty() || moTa.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (topicSentenceHandler.searchResultTopicSentence(tenCDMC).size() > 0) {
            Toast.makeText(this, "Tên chủ đề đã tồn tại!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Lấy ảnh và chuyển thành byte array
        Bitmap image = ((BitmapDrawable) imgAnhChuDe.getDrawable()).getBitmap();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] imageBytes = byteArrayOutputStream.toByteArray();

        //
        TopicSentence mc = new TopicSentence();
        mc.setTenChuDeMauCau(tenCDMC);
        mc.setMoTa(moTa);
        mc.setMaChuDeMauCau(maCDMC);
        mc.setAnhChuDeMauCau(imageBytes);


        boolean isAdded = topicSentenceHandler.addTopicSentence(mc, image);
        if (isAdded) {
            Toast.makeText(this, "Thêm thành công!", Toast.LENGTH_SHORT).show();

            topicSentenceArrayList.add(mc);
            admin_Add_Topic_SampleSentence_CustomAdapter_LV.notifyDataSetChanged();

            edtTenAddCDMC.setText("");
            edtMoTaAddCDMC.setText("");
            edtMaAddCDMC.setText(generateMaChuDeMauCau());
            imgAnhChuDe.setImageResource(R.drawable.image_default);
        } else {
            Toast.makeText(this, "Thêm thất bại!", Toast.LENGTH_SHORT).show();
        }
    }

    private String generateMaChuDeMauCau() {
        int randomNum = (int)(Math.random() * (999 - 10 + 1)) + 10;
        return "CDMC" + String.valueOf(randomNum);
    }

    //ham xu ly khi chon anh
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            try {
                Bitmap selectedImage = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                imgAnhChuDe.setImageBitmap(selectedImage);

                // Lưu ảnh vào byte array để lưu trong cơ sở dữ liệu
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                selectedImage.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                byte[] imageBytes = byteArrayOutputStream.toByteArray();

                // Cập nhật đối tượng TopicSentence với ảnh mới
                int position = lvAddCDMC.getCheckedItemPosition();
                if (position >= 0 && position < topicSentenceArrayList.size()) {
                    TopicSentence currentTopicSentence = topicSentenceArrayList.get(position);
                    currentTopicSentence.setAnhChuDeMauCau(imageBytes);

                    admin_Add_Topic_SampleSentence_CustomAdapter_LV.notifyDataSetChanged();
                }
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "Lỗi khi chọn ảnh", Toast.LENGTH_SHORT).show();
            }
        }
    }


}
