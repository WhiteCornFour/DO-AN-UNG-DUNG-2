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
    SampleSentence sampleSentence;
    TopicSentence topicSentence;
    ArrayAdapter<String> adapterSpinnerChuDe;
    ArrayList<TopicSentence> dsChuDeMauCau = new ArrayList<>();
    ArrayList<SampleSentence> sampleSentenceArrayList = new ArrayList<>();
    Admin_Add_SampleSentence_CustomAdapter_LV admin_add_sampleSentence_customAdapter_lv;
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

                int viTri = -1;
                for (int i = 0; i < stringArrayList.size(); i++)
                {
                    //Log.d("Kiem tra ham", exercisesCategoryHandler.searchCodeExerciseCategoryByName(stringArrayList.get(i)));
                    if (Objects.equals(topicSentenceHandler.getTopicSentenceCodeByName(stringArrayList.get(i)), ss.getMaChuDeMauCau()))
                    {
                        viTri = i;
                        Log.d("Vi tri", String.valueOf(viTri));
                        Log.d("Ten Dang Bai Tap", stringArrayList.get(viTri));
                    }
                }
                if (viTri != -1)
                {
                    spinnerChonChuDe.setSelection(viTri);
                }
            }
        });

        btnThem_MC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                maMauCau = edtMaAdd_MC.getText().toString().trim();
                String MauCau = edtTenAdd_CMC.getText().toString().trim();
                String phienDich = edtPDAdd_CMC.getText().toString().trim();
                String tinhHuong = edtTHSDAdd_MC.getText().toString().trim();
                String chuDe = spinnerChonChuDe.getSelectedItem().toString();

                String maChuDeMauCau = topicSentenceHandler.getTopicSentenceCodeByName(chuDe);
                String tenChuDeMauCau = topicSentenceHandler.getTopicSentenceNameByCode(maChuDeMauCau);

                if (!MauCau.isEmpty() && !maChuDeMauCau.isEmpty() && !tenChuDeMauCau.isEmpty()) {
                    SampleSentence sampleSentence = new SampleSentence(maMauCau, MauCau, phienDich, tinhHuong, maChuDeMauCau);

                    if (checkEnterSampleSentenceData(sampleSentence)) {
                        if (!sampleSentenceHandler.checkNameSampleSentence(MauCau)) {
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


    ArrayList<String> convertObjectToString(ArrayList<TopicSentence> topicSentencesArrayList)
    {
        ArrayList<String> stringArrayList1 = new ArrayList<>();
        for (TopicSentence tst: topicSentencesArrayList
        ) {
            String tenCDMC = tst.getTenChuDeMauCau();
            stringArrayList1.add(tenCDMC);
        }
        return stringArrayList1;
    }

    void setupSpinner() {
        ArrayList<String> dsChuDeMauCauString = topicSentenceHandler.returnNameOfTopicSentenceSpinner();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, dsChuDeMauCauString);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerChonChuDe.setAdapter(adapter);

        spinnerChonChuDe.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedCode = dsChuDeMauCauString.get(position);
                String topicName = topicSentenceHandler.getTopicSentenceNameByCode(selectedCode); // Lấy tên từ mã
                Log.d("Selected Topic", topicName);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    public boolean checkEnterSampleSentenceData(SampleSentence sampleSentence) {

        if (sampleSentence.getMaMauCau() == null || sampleSentence.getMaMauCau().trim().isEmpty()) {
            System.out.println("Mã mẫu câu không được để trống.");
            return false;
        }

        if (sampleSentence.getMauCau() == null || sampleSentence.getMauCau().trim().isEmpty()) {
            System.out.println("Tên mẫu câu không được để trống.");
            return false;
        }
        if (sampleSentence.getMauCau().length() > 50) {
            System.out.println("Tên mẫu câu không được dài hơn 50 ký tự.");
            return false;
        }

        if (sampleSentence.getPhienDich() != null && sampleSentence.getPhienDich().isEmpty()) {
            System.out.println("Vui lòng nhập phiên dịch cho mẫu câu này.");
            return false;
        }

//        if (sampleSentence.getTinhHuongSuDung() != null && sampleSentence.getTinhHuongSuDung().isEmpty()) {
//            System.out.println("Vui lòng nhập tình huống sử dụng cho mẫu câu này.");
//            return false;
//        }

        return true;
    }

    private String generateMaMauCau() {
        int randomNum = (int)(Math.random() * (999 - 10 + 1)) + 10;
        return "MC" + String.valueOf(randomNum);
    }
}
