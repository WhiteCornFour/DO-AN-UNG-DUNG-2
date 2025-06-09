package com.example.doanungdung2.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.doanungdung2.Controller.ExercisesCategoryHandler;
import com.example.doanungdung2.Controller.SampleSentenceHandler;
import com.example.doanungdung2.Controller.TopicSentenceHandler;
import com.example.doanungdung2.Model.ExercisesCategory;
import com.example.doanungdung2.Model.SampleSentence;
import com.example.doanungdung2.Model.TopicSentence;
import com.example.doanungdung2.R;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Objects;

public class Admin_Add_Sample_Sentence extends AppCompatActivity {
    private static final String DB_NAME = "AppHocTiengAnh";
    private static final int DB_VERSION = 1;

    ImageView imgBack_MC;
    EditText edtMaAdd_MC, edtTenAdd_CMC, edtPDAdd_CMC, edtTHSDAdd_MC;
    Spinner spinnerChonChuDe;
    Button btnLamMoi_MC, btnThem_MC;
    ListView lvDs_MC;

    SampleSentenceHandler sampleSentenceHandler;
    TopicSentenceHandler topicSentenceHandler;
    ArrayList<SampleSentence> sampleSentenceArrayList = new ArrayList<>();
    ArrayList<String> stringArrayList = new ArrayList<>();
    String maMauCau = "";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_sample_sentence);

        addControl();
        sampleSentenceHandler = new SampleSentenceHandler(Admin_Add_Sample_Sentence.this, DB_NAME, null, DB_VERSION);
        topicSentenceHandler = new TopicSentenceHandler(Admin_Add_Sample_Sentence.this, DB_NAME,null,DB_VERSION);

        setupSpinner();

        loadAllDataOfSampleSentence();

        String maMauCau = generateMaMauCau();
        edtMaAdd_MC.setText(maMauCau);
        addEvent();
    }

    void addControl() {
        imgBack_MC =  (ImageView) findViewById(R.id.imgBack_MC);
        edtMaAdd_MC = (EditText) findViewById(R.id.edtMaAdd_MC);
        edtTenAdd_CMC = (EditText) findViewById(R.id.edtTenAdd_CMC);
        edtPDAdd_CMC = (EditText) findViewById(R.id.edtPDAdd_CMC);
        edtTHSDAdd_MC = (EditText) findViewById(R.id.edtTHSDAdd_MC);
        spinnerChonChuDe = (Spinner) findViewById(R.id.spinnerChonChuDe);
        btnLamMoi_MC = (Button) findViewById(R.id.btnLamMoi_MC);
        btnThem_MC = (Button) findViewById(R.id.btnThem_MC);
        lvDs_MC = (ListView) findViewById(R.id.lvAdd_MC);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStack();
    }

    void addEvent() {
        imgBack_MC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnLamMoi_MC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Reset();
            }

        });
        lvDs_MC.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SampleSentence ss = sampleSentenceArrayList.get(position);
                edtMaAdd_MC.setText(ss.getMaMauCau());
                edtPDAdd_CMC.setText(ss.getPhienDich());
                edtTHSDAdd_MC.setText(ss.getTinhHuongSuDung());
                edtTenAdd_CMC.setText(ss.getMauCau());


                String maChuDeMauCau = ss.getMaChuDeMauCau();
                String tenChuDeMauCau = topicSentenceHandler.getTopicSentenceNameByCode(maChuDeMauCau);
                ArrayList<String> dsCDMCString = topicSentenceHandler.returnNameOfTopicsSpinner();
                int index = dsCDMCString.indexOf(tenChuDeMauCau);
                if (index != -1) {
                    spinnerChonChuDe.setSelection(index);
                }
            }
        });

        btnThem_MC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                maMauCau = edtMaAdd_MC.getText().toString().trim();
                String mauCau = edtTenAdd_CMC.getText().toString().trim();
                String phienDich = edtPDAdd_CMC.getText().toString().trim();
                String tinhHuong = edtTHSDAdd_MC.getText().toString().trim();
                String chuDe = spinnerChonChuDe.getSelectedItem().toString();

                String maChuDeMauCau = topicSentenceHandler.getTopicSentenceCodeByName(chuDe);
                String tenChuDeMauCau = topicSentenceHandler.getTopicSentenceNameByCode(maChuDeMauCau);

                if (!mauCau.isEmpty() && !maChuDeMauCau.isEmpty() && !tenChuDeMauCau.isEmpty()) {
                    SampleSentence sampleSentence = new SampleSentence(maMauCau, maChuDeMauCau, mauCau, phienDich, tinhHuong);

                    if (checkEnterSampleSentenceData(sampleSentence)) {
                        if (!sampleSentenceHandler.checkNameSampleSentence(mauCau)) {
                            if (sampleSentenceHandler.insertSampleSentence(sampleSentence)) {
                                Toast.makeText(Admin_Add_Sample_Sentence.this, "Thêm mẫu câu thành công", Toast.LENGTH_SHORT).show();
                                loadAllDataOfSampleSentence();
                                Reset();
                            } else {
                                Toast.makeText(Admin_Add_Sample_Sentence.this, "Thêm mẫu câu thất bại", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(Admin_Add_Sample_Sentence.this, "Tên mẫu câu đã tồn tại", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(Admin_Add_Sample_Sentence.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Admin_Add_Sample_Sentence.this, "Vui lòng nhập tên mẫu câu và chọn chủ đề", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

        private void Reset() {
        edtMaAdd_MC.setText(generateMaMauCau());

        edtTenAdd_CMC.setText("");
        edtPDAdd_CMC.setText("");
        edtTHSDAdd_MC.setText("");
        spinnerChonChuDe.setSelection(0);

        Toast.makeText(this, "Thông tin đã được làm mới", Toast.LENGTH_SHORT).show();
    }

    void loadAllDataOfSampleSentence() {

        sampleSentenceArrayList = sampleSentenceHandler.loadAllDataOfSampleSentence();

        boolean[] checkedStates = new boolean[sampleSentenceArrayList.size()];

        Admin_Add_SampleSentence_CustomAdapter_LV adapter =
                new Admin_Add_SampleSentence_CustomAdapter_LV(this,
                        R.layout.activity_admin_add_topic_sample_sentence_custom_adapter_lv,
                        sampleSentenceArrayList,
                        checkedStates);

        lvDs_MC.setAdapter(adapter);
    }


    void setupSpinner() {
        stringArrayList = topicSentenceHandler.returnNameOfTopicSentenceSpinner();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, stringArrayList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerChonChuDe.setAdapter(adapter);

        spinnerChonChuDe.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedName = stringArrayList.get(position);
                String topicName = topicSentenceHandler.getTopicSentenceNameByCode(selectedName); // Lấy tên từ mã
                Log.d("Selected Topic", topicName);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    public boolean checkEnterSampleSentenceData(SampleSentence sampleSentence) {

        if (sampleSentence.getMaMauCau() == null || sampleSentence.getMaMauCau().trim().isEmpty()) {
            Toast.makeText(Admin_Add_Sample_Sentence.this, "Mã mẫu câu không được để trống.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (sampleSentence.getMauCau() == null || sampleSentence.getMauCau().trim().isEmpty()) {
            Toast.makeText(Admin_Add_Sample_Sentence.this, "Tên mẫu câu không được để trống.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (sampleSentence.getMauCau().length() > 50) {
            Toast.makeText(Admin_Add_Sample_Sentence.this, "Tên mẫu câu không được dài hơn 50 ký tự.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (sampleSentence.getPhienDich() != null && sampleSentence.getPhienDich().isEmpty()) {
            Toast.makeText(Admin_Add_Sample_Sentence.this, "Vui lòng nhập phiên dịch cho mẫu câu này.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (sampleSentence.getTinhHuongSuDung() != null && sampleSentence.getTinhHuongSuDung().isEmpty()) {
            Toast.makeText(Admin_Add_Sample_Sentence.this, "Vui lòng nhập tình huống sử dụng cho mẫu câu này.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private String generateMaMauCau() {
        int randomNum = (int)(Math.random() * (999 - 10 + 1)) + 10;
        return "MC" + String.valueOf(randomNum);
    }
}
