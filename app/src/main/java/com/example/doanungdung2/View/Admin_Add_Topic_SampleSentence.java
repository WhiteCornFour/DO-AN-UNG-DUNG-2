package com.example.doanungdung2.View;

import android.os.Bundle;
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

import java.util.ArrayList;

public class Admin_Add_Topic_SampleSentence extends AppCompatActivity {

    ImageView imgBackCDMC;
    ListView lvAddCDMC;
    EditText edtTenAddCDMC, edtMoTaAddCDMC, edtMaAddCDMC;
    Button btnThemCDMC, btnLamMoi;
    ArrayList<TopicSentence> topicSentenceArrayList = new ArrayList<>();
    Admin_Add_Topic_SampleSentence_CustomAdapter_LV admin_Add_Topic_SampleSentence_CustomAdapter_LV;
    TopicSentenceHandler topicSentenceHandler;

    private static final String DB_NAME = "AppHocTiengAnh";
    private static final int DB_VERSION = 1;

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
            }
        });

        btnLamMoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Reset();
            }
        });
    }

    private void Reset() {
        if (edtTenAddCDMC.getText().toString().trim().isEmpty()
                && edtMoTaAddCDMC.getText().toString().trim().isEmpty()) {

            Toast.makeText(this, "Không có thông tin để làm mới", Toast.LENGTH_SHORT).show();
        } else {
            edtTenAddCDMC.setText("");
            edtMoTaAddCDMC.setText("");
            edtMaAddCDMC.setText(generateMaChuDeMauCau());
        }
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

        TopicSentence mc = new TopicSentence();
        mc.setTenChuDeMauCau(tenCDMC);
        mc.setMoTa(moTa);
        mc.setMaChuDeMauCau(maCDMC);

        boolean isAdded = topicSentenceHandler.addTopicSentence(mc);
        if (isAdded) {
            Toast.makeText(this, "Thêm thành công!", Toast.LENGTH_SHORT).show();
            topicSentenceArrayList.add(mc);
            admin_Add_Topic_SampleSentence_CustomAdapter_LV.notifyDataSetChanged();

            edtTenAddCDMC.setText("");
            edtMoTaAddCDMC.setText("");
            edtMaAddCDMC.setText(generateMaChuDeMauCau());
        } else {
            Toast.makeText(this, "Thêm thất bại!", Toast.LENGTH_SHORT).show();
        }
    }

    private String generateMaChuDeMauCau() {
        int randomNum = (int)(Math.random() * (999 - 10 + 1)) + 10;
        return "CDMC" + String.valueOf(randomNum);
    }
}
