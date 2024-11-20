package com.example.doanungdung2.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.doanungdung2.Controller.SampleSentenceHandler;
import com.example.doanungdung2.Model.SampleSentence;
import com.example.doanungdung2.R;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Admin_Delete_SampleSentence extends AppCompatActivity {

    private static final String DB_NAME = "AppHocTiengAnh";
    private static final int DB_VERSION = 1;
    SampleSentenceHandler sampleSentenceHandler;
    ArrayList<SampleSentence> sampleSentenceArrayList = new ArrayList<>();
    ArrayList<SampleSentence> dataOfSearch = new ArrayList<>();
    Admin_Delete_SampleSentence_CustomAdapter_LV adapter_lv;
    ImageView imgBackDeleteToMainPage, imgSearchForDelete;
    EditText edtSearchForDelete;
    Button btnResetForListView, btnDeleteAll;
    ListView lvDSDelete;
    SampleSentence sampleSentence;
    boolean[] checkedStates;
    boolean[] checkedForSearch;
    String sentenceCode = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_delete_sample_sentence);
        addControl();
        sampleSentenceHandler = new SampleSentenceHandler(Admin_Delete_SampleSentence.this, DB_NAME, null, DB_VERSION); // Khởi tạo SampleSentenceHandler

        Intent intent = getIntent();
        sentenceCode = intent.getStringExtra("machude");
        if (sentenceCode != null) {
            loadDataForSearch(sentenceCode);
        } else {
            loadAllDataSampleSentence();
        }
        addEvent();
    }

    void addControl() {
        imgBackDeleteToMainPage = findViewById(R.id.imgBackDeleteToMainPage_CDMC);
        imgSearchForDelete = findViewById(R.id.imgSearchForDelete_MC);
        edtSearchForDelete = findViewById(R.id.edtSearchForDelete_MC);
        btnResetForListView = findViewById(R.id.btnResetForLV_MC);
        btnDeleteAll = findViewById(R.id.btnDeleteAll_MC);
        lvDSDelete = findViewById(R.id.lvDSDelete_MC);
    }

    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStack();
    }

    void addEvent() {

        imgBackDeleteToMainPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnResetForListView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sentenceCode == null) {
                    loadAllDataSampleSentence();
                    edtSearchForDelete.setText("");
                } else {
                    loadDataForSearch(sentenceCode);
                    edtSearchForDelete.setText("");
                }
            }
        });
        imgSearchForDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String keyword = edtSearchForDelete.getText().toString();
                dataOfSearch = sampleSentenceHandler.searchSampleSentenceByKeyword(keyword);

                checkedForSearch = new boolean[dataOfSearch.size()];
                adapter_lv = new Admin_Delete_SampleSentence_CustomAdapter_LV(Admin_Delete_SampleSentence.this,
                        R.layout.activity_admin_delete_sample_sentence_custom_adapter_lv, dataOfSearch, checkedForSearch);
                lvDSDelete.setAdapter(adapter_lv);
            }
        });
        lvDSDelete.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (sampleSentenceArrayList.size() != 0) {

                    sampleSentence = sampleSentenceArrayList.get(i);
                } else if (dataOfSearch.size() != 0){
                    sampleSentence = dataOfSearch.get(i);
                }
                String code = sampleSentence.getMaMauCau();
                createDialog(code);
                return true;
            }
        });
        btnDeleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean anyChecked = false;
                if (sampleSentenceArrayList.size() != 0) {
                    for (boolean checked : checkedStates) {
                        if (checked) {
                            anyChecked = true;
                            break;
                        }
                    }
                } else if (dataOfSearch.size() != 0){
                    for (boolean checked : checkedForSearch) {
                        if (checked) {
                            anyChecked = true;
                            break;
                        }
                    }
                }else {
                    Toast.makeText(Admin_Delete_SampleSentence.this, "Vui lòng chọn 1 phần tử trước khi xóa!",
                            Toast.LENGTH_SHORT).show();
                }

                if (!anyChecked) {
                    Toast.makeText(Admin_Delete_SampleSentence.this, "Hãy chọn ít nhất một mẫu câu!", Toast.LENGTH_SHORT).show();
                    return;
                }
                AlertDialog alertDialog = createAlertDialogDeleteSampleSentence();
                alertDialog.show();
            }
        });
    }

    void loadAllDataSampleSentence() {
        sampleSentenceArrayList = sampleSentenceHandler.loadAllDataOfSampleSentence();
        checkedStates = new boolean[sampleSentenceArrayList.size()];
        adapter_lv = new Admin_Delete_SampleSentence_CustomAdapter_LV(Admin_Delete_SampleSentence.this, R.layout.activity_admin_delete_sample_sentence_custom_adapter_lv, sampleSentenceArrayList, checkedStates);
        lvDSDelete.setAdapter(adapter_lv);
    }

    void loadDataForSearch(String keyWord) {
        dataOfSearch = sampleSentenceHandler.searchSampleSentenceByKeyword(keyWord);
        checkedForSearch = new boolean[dataOfSearch.size()];
        adapter_lv = new Admin_Delete_SampleSentence_CustomAdapter_LV(Admin_Delete_SampleSentence.this, R.layout.activity_admin_delete_sample_sentence_custom_adapter_lv, dataOfSearch, checkedForSearch);
        lvDSDelete.setAdapter(adapter_lv);
    }

    void createDialog(String code) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Xác nhận");
        builder.setMessage("Bạn có chắc muốn xóa mẫu câu này hay không?");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                sampleSentenceHandler.deleteSampleSentence(code);
                if (sampleSentenceArrayList.size() != 0) {
                    sampleSentenceArrayList.remove(sampleSentence);
                } else if (dataOfSearch.size() != 0){
                    dataOfSearch.remove(sampleSentence);
                }
                adapter_lv.notifyDataSetChanged();
                Toast.makeText(Admin_Delete_SampleSentence.this, "Xóa mẫu câu thành công!", Toast.LENGTH_SHORT).show(); // Hiển thị thông báo
            }
        });
        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    AlertDialog createAlertDialogDeleteSampleSentence() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Xác nhận");
        builder.setMessage("Bạn có chắc muốn xóa tất cả mẫu câu được chọn không?");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                for (int i = 0; i < checkedStates.length; i++) {
                    if (checkedStates[i]) {
                        sampleSentenceHandler.deleteSampleSentence(sampleSentenceArrayList.get(i).getMaMauCau());
                    }
                }
                loadAllDataSampleSentence();
                Toast.makeText(Admin_Delete_SampleSentence.this, "Đã xóa tất cả mẫu câu được chọn!", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        return builder.create();
    }
}
