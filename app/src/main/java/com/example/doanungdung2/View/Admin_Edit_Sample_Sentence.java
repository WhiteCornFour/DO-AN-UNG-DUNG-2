package com.example.doanungdung2.View;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doanungdung2.Controller.SampleSentenceHandler;
import com.example.doanungdung2.Controller.TopicSentenceHandler;
import com.example.doanungdung2.Model.SampleSentence;
import com.example.doanungdung2.Model.TopicSentence;
import com.example.doanungdung2.R;

import java.util.ArrayList;
import java.util.List;

public class Admin_Edit_Sample_Sentence extends AppCompatActivity {

    EditText edtSuaSearch_SS, edtSuaTen_SS, edtSuaTHSD_SS, edtSuaPD_SS, edtMa_SS;
    Button btnSua_SS;
    RecyclerView rvSua_SS;
    Spinner spinnerChonChuDe_SS;
    ImageView imgBackToMainPage_SS, imgSuaSearch_SS;

    SampleSentence sampleSentence;
    SampleSentenceHandler sampleSentenceHandler;
    TopicSentenceHandler topicSentenceHandler;
    ArrayList<SampleSentence> sampleSentencesArrayListSearchResult = new ArrayList<>();
    ArrayList<TopicSentence> dsChuDeMauCau = new ArrayList<>();
    Admin_Edit_Sample_Sentence_CustomAdapter_RV admin_edit_sample_sentence_custom_adapter_rv;

    private static final String DB_NAME = "AppHocTiengAnh";
    private static final int DB_VERSION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_edit_sample_sentence);

        addControl();

        sampleSentenceHandler = new SampleSentenceHandler(
                Admin_Edit_Sample_Sentence.this, DB_NAME, null, DB_VERSION);
        topicSentenceHandler = new TopicSentenceHandler(
                Admin_Edit_Sample_Sentence.this, DB_NAME, null, DB_VERSION);

        setupSpinner();
        setupRecyclerView();
        loadAllSampleSentences();
        addEvent();
    }

    void addControl() {
        edtSuaSearch_SS = findViewById(R.id.edtSuaSearch_SS);
        edtMa_SS = findViewById(R.id.edtMa_SS);
        edtSuaTen_SS = findViewById(R.id.edtTen_SS);
        edtSuaPD_SS = findViewById(R.id.edtPD_SS);
        edtSuaTHSD_SS = findViewById(R.id.edtTHSD_SS);
        btnSua_SS = findViewById(R.id.btnSua_SS);
        rvSua_SS = findViewById(R.id.rvSuaSearch_SS);
        imgBackToMainPage_SS = findViewById(R.id.imgBackToMainPage_SS);
        imgSuaSearch_SS = findViewById(R.id.imgSuaSearch_SS);
        spinnerChonChuDe_SS = findViewById(R.id.spinnerChonChuDe_SS);
    }

    void setupSpinner() {
        ArrayList<String> dsChuDeMauCauString = topicSentenceHandler.returnNameOfTopicSentenceSpinner();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, dsChuDeMauCauString);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerChonChuDe_SS.setAdapter(adapter);
    }

    void addEvent() {
        imgBackToMainPage_SS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        imgSuaSearch_SS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sampleSentenceSearch = edtSuaSearch_SS.getText().toString().trim();
                if (sampleSentenceSearch.isEmpty()) {
                    loadAllSampleSentences();
                } else {
                    searchSampleSentences(sampleSentenceSearch);
                }
            }
        });

        btnSua_SS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String PDSS = edtSuaPD_SS.getText().toString().trim();
                String tenSS = edtSuaTen_SS.getText().toString().trim();
                String THSDSS = edtSuaTHSD_SS.getText().toString().trim();
                String maSS = edtMa_SS.getText().toString().trim();

                String tenChuDeMauCau = spinnerChonChuDe_SS.getSelectedItem().toString();
                Log.d("ten CDMC", tenChuDeMauCau);
                String maChuDeMauCau = topicSentenceHandler.getTopicSentenceCodeByName(tenChuDeMauCau);


                if (tenSS.isEmpty() || PDSS.isEmpty()) {
                    Toast.makeText(Admin_Edit_Sample_Sentence.this, "Vui lòng không để trống thông tin.", Toast.LENGTH_SHORT).show();
                    return;
                }

                SampleSentence sampleSentence = new SampleSentence();
                sampleSentence.setMaMauCau(maSS);
                sampleSentence.setMauCau(tenSS);
                sampleSentence.setPhienDich(PDSS);
                sampleSentence.setTinhHuongSuDung(THSDSS);
                sampleSentence.setMaChuDeMauCau(maChuDeMauCau);

                createAlertDialogEditSampleSentence(sampleSentence).show();
            }
        });
    }

    void loadAllSampleSentences() {
        sampleSentencesArrayListSearchResult.clear();
        sampleSentencesArrayListSearchResult = sampleSentenceHandler.loadAllDataOfSampleSentence();
        admin_edit_sample_sentence_custom_adapter_rv.setSampleSentenceList(sampleSentencesArrayListSearchResult);
        admin_edit_sample_sentence_custom_adapter_rv.notifyDataSetChanged();
    }

    void searchSampleSentences(String searchQuery) {
        sampleSentencesArrayListSearchResult.clear();
        sampleSentencesArrayListSearchResult = sampleSentenceHandler.searchResultSampleSentence(searchQuery);
        admin_edit_sample_sentence_custom_adapter_rv.setSampleSentenceList(sampleSentencesArrayListSearchResult);
    }

    void setupRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rvSua_SS.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        rvSua_SS.setLayoutManager(layoutManager);
        rvSua_SS.setItemAnimator(new DefaultItemAnimator());

        admin_edit_sample_sentence_custom_adapter_rv = new Admin_Edit_Sample_Sentence_CustomAdapter_RV(sampleSentencesArrayListSearchResult, new Admin_Edit_Sample_Sentence_CustomAdapter_RV.ItemClickListener() {
            @Override
            public void onItemClick(SampleSentence sampleSentence) {
                edtSuaTen_SS.setText(sampleSentence.getMauCau());
                edtSuaTHSD_SS.setText(sampleSentence.getTinhHuongSuDung());
                edtMa_SS.setText(sampleSentence.getMaMauCau());
                edtSuaPD_SS.setText(sampleSentence.getPhienDich());
//
                String maChuDeMauCau = sampleSentence.getMaChuDeMauCau();
                String tenChuDeMauCau = topicSentenceHandler.getTopicSentenceNameByCode(maChuDeMauCau);
                Log.d("DEBUG", "tenChuDeMauCau: " + tenChuDeMauCau);
                ArrayList<String> dsChuDeMauCauString = topicSentenceHandler.returnNameOfTopicsSpinner();
                int index2 = dsChuDeMauCauString.indexOf(tenChuDeMauCau);
                if (index2 != -1) {
                    spinnerChonChuDe_SS.setSelection(index2);
                }
            }
        });
        rvSua_SS.setAdapter(admin_edit_sample_sentence_custom_adapter_rv);
    }

    private AlertDialog createAlertDialogEditSampleSentence(SampleSentence sampleSentence) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Sửa mẫu câu");
        builder.setMessage("Bạn có muốn cập nhật mẫu câu này không?");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                sampleSentenceHandler.updateSampleSentence(sampleSentence);
                loadAllSampleSentences();
                Toast.makeText(Admin_Edit_Sample_Sentence.this, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
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

    private void clearInputFields() {
        edtMa_SS.setText("");
        edtSuaTen_SS.setText("");
        edtSuaPD_SS.setText("");
        edtSuaTHSD_SS.setText("");
        spinnerChonChuDe_SS.setSelection(0);
    }
}
