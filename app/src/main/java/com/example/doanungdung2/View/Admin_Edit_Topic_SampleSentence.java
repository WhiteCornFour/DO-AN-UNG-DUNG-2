package com.example.doanungdung2.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.doanungdung2.Controller.TopicSentenceHandler;
import com.example.doanungdung2.Model.TopicSentence;
import com.example.doanungdung2.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class Admin_Edit_Topic_SampleSentence extends AppCompatActivity {

    EditText edtSuaSearch_CDMC, edtSuaTen_CDMC, edtSuaMoTa_CDMC, edtSuaMa_CDMC;
    Button btnSua_CDMC;
    RecyclerView rvSua_CDMC;
    ImageView imgBackToMainPage_CDMC, imgSuaSearch_CDMC, imgSuaAnhChuDe;

    TopicSentence topicSentence;
    TopicSentenceHandler topicSentenceHandler;

    ArrayList<TopicSentence> topicSentenceArrayList = new ArrayList<>();
    ArrayList<TopicSentence> topicSentencesArrayListSearchResult = new ArrayList<>();
    Admin_Edit_Topic_SampleSentence_CustomAdapter_RV admin_edit_topic_sample_sentence_custom_adapter_rv;

    private static final String DB_NAME = "AppHocTiengAnh";
    private static final int DB_VERSION = 1;

    private static final int PICK_IMAGE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_edit_topic_sample_sentence);

        addControl();

        topicSentenceHandler = new TopicSentenceHandler(
                Admin_Edit_Topic_SampleSentence.this, DB_NAME, null, DB_VERSION);

        setupRecyclerView();
        loadAllTopicSentences();
        addEvent();
    }

    void addControl() {
        edtSuaSearch_CDMC = (EditText) findViewById(R.id.edtSuaSearch_CDMC);
        edtSuaTen_CDMC = (EditText) findViewById(R.id.edtSuaTen_CDMC);
        edtSuaMoTa_CDMC = (EditText) findViewById(R.id.edtSuaMoTa_CDMC);
        edtSuaMa_CDMC = (EditText) findViewById(R.id.edtSuaMa_CDMC);
        btnSua_CDMC = (Button) findViewById(R.id.btnSua_CDMC);
        rvSua_CDMC = (RecyclerView) findViewById(R.id.rvSuaSearch_CDMC);
        imgBackToMainPage_CDMC = (ImageView) findViewById(R.id.imgBackToMainPage_CDMC);
        imgSuaSearch_CDMC = (ImageView) findViewById(R.id.imgSuaSearch_CDMC);
        imgSuaAnhChuDe = (ImageView) findViewById(R.id.imgSuaAnhChuDe);
    }

    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStack();
    }

    void addEvent() {
        imgBackToMainPage_CDMC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        imgSuaSearch_CDMC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String topicSampleSentenceSearch = edtSuaSearch_CDMC.getText().toString().trim();
                if (topicSampleSentenceSearch.isEmpty()) {
                    edtSuaSearch_CDMC.setText("");
                    loadAllTopicSentences();
                } else {
                    searchTopicSentences(topicSampleSentenceSearch);
                }
            }
        });

        imgSuaAnhChuDe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(intent, PICK_IMAGE_REQUEST);
            }
        });

        btnSua_CDMC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String maCDMC = edtSuaMa_CDMC.getText().toString().trim();
                String tenCDMC = edtSuaTen_CDMC.getText().toString().trim();
                String moTaCDMC = edtSuaMoTa_CDMC.getText().toString().trim();

                if (tenCDMC.isEmpty() || moTaCDMC.isEmpty()) {
                    Toast.makeText(Admin_Edit_Topic_SampleSentence.this, "Vui lòng không để trống thông tin.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (topicSentenceHandler.checkCodeAndNameTopicSentence(maCDMC, tenCDMC)) {
                    Toast.makeText(Admin_Edit_Topic_SampleSentence.this, "Không được để trùng tên chủ đề mẫu câu.", Toast.LENGTH_SHORT).show();
                    return;
                }

                Bitmap imageBitmap = ((BitmapDrawable) imgSuaAnhChuDe.getDrawable()).getBitmap();
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                byte[] imageBytes = byteArrayOutputStream.toByteArray();

                TopicSentence topicSentence = new TopicSentence();
                topicSentence.setMaChuDeMauCau(maCDMC);
                topicSentence.setTenChuDeMauCau(tenCDMC);
                topicSentence.setMoTa(moTaCDMC);
                topicSentence.setAnhChuDeMauCau(imageBytes);
                createAlertDialogEditTopicSentence(topicSentence).show();
            }
        });
    }

    void loadAllTopicSentences() {
        topicSentencesArrayListSearchResult.clear();
        topicSentencesArrayListSearchResult = topicSentenceHandler.loadAllDataOfTopicSentence();
        admin_edit_topic_sample_sentence_custom_adapter_rv.setTopicSentenceList(topicSentencesArrayListSearchResult);
    }

    void searchTopicSentences(String searchQuery) {
        ArrayList<TopicSentence> searchResults = topicSentenceHandler.searchResultTopicSentence(searchQuery);
        if (searchResults != null && !searchResults.isEmpty()) {
            topicSentencesArrayListSearchResult.clear();
            topicSentencesArrayListSearchResult.addAll(searchResults);
            admin_edit_topic_sample_sentence_custom_adapter_rv.setTopicSentenceList(topicSentencesArrayListSearchResult);
        } else {
            Toast.makeText(this, "Không tìm thấy chủ đề mẫu câu với mã này.", Toast.LENGTH_SHORT).show();
        }
    }


    void setupRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rvSua_CDMC.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        rvSua_CDMC.setLayoutManager(layoutManager);
        rvSua_CDMC.setItemAnimator(new DefaultItemAnimator());

        admin_edit_topic_sample_sentence_custom_adapter_rv = new Admin_Edit_Topic_SampleSentence_CustomAdapter_RV(topicSentencesArrayListSearchResult, new Admin_Edit_Topic_SampleSentence_CustomAdapter_RV.ItemClickListener() {
            @Override
            public void onItemClick(TopicSentence topicSentence) {

                edtSuaTen_CDMC.setText(topicSentence.getTenChuDeMauCau());
                edtSuaMoTa_CDMC.setText(topicSentence.getMoTa());
                edtSuaMa_CDMC.setText(topicSentence.getMaChuDeMauCau());

                if (topicSentence.getAnhChuDeMauCau() != null) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(topicSentence.getAnhChuDeMauCau(), 0, topicSentence.getAnhChuDeMauCau().length);
                    imgSuaAnhChuDe.setImageBitmap(bitmap);
                } else {
                    imgSuaAnhChuDe.setImageResource(R.drawable.image_default);
                }
            }
        });
        rvSua_CDMC.setAdapter(admin_edit_topic_sample_sentence_custom_adapter_rv);
    }

    private AlertDialog createAlertDialogEditTopicSentence(TopicSentence topicSentence) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Sửa chủ đề mẫu câu");
        builder.setMessage("Bạn có muốn cập nhật chủ đề mẫu câu này không?");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                topicSentenceHandler.updateTopicSentence(topicSentence);
                loadAllTopicSentences();
                Toast.makeText(Admin_Edit_Topic_SampleSentence.this, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
                clearInputFields();
            }
        });
        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        return builder.create();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            try {
                Bitmap selectedImage = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                imgSuaAnhChuDe.setImageBitmap(selectedImage);

                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                selectedImage.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                byte[] imageBytes = byteArrayOutputStream.toByteArray();

                // Kiểm tra nếu requestCode nằm trong phạm vi của danh sách
                if (requestCode >= 0 && requestCode < topicSentenceArrayList.size()) {
                    TopicSentence tss = topicSentenceArrayList.get(requestCode);
                    tss.setAnhChuDeMauCau(imageBytes);
                }
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Lỗi khi chọn ảnh", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private void clearInputFields() {
        edtSuaMa_CDMC.setText("");
        edtSuaTen_CDMC.setText("");
        edtSuaMoTa_CDMC.setText("");
        imgSuaAnhChuDe.setImageResource(R.drawable.image_default);
    }
}

