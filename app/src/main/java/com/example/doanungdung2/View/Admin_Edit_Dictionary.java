package com.example.doanungdung2.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doanungdung2.Controller.DictionaryHandler;
import com.example.doanungdung2.Model.Dictionary;
import com.example.doanungdung2.R;

import java.util.ArrayList;

public class Admin_Edit_Dictionary extends AppCompatActivity {

    private static final String DB_NAME = "AppHocTiengAnh";
    private static final int DB_VERSION = 1;
    ImageView imgBackToMainPageSTD, imgSuaTDSearch;
    EditText edtSuaMaTuVungTD, edtSuaTuTATD, edtSuaTuTVTD, edtSuaCachPhatAmTD, edtSuaGioiTuDiKemTD, edtSuaViDuSDTD, edtSuaTDSearch;
    Spinner spinnerSuaLoaiTuTD;
    Button btnSuaTV;
    RecyclerView rvSuaTuDienHienCo;
    TextView tvSuaTuVungCount;
    String[] dsLoaiTu = new String[]{"Danh từ (n)", "Động từ (v)", "Tính từ (adj)", "Trạng từ (adv)", "Đại từ (pron)", "Giới từ (prep)", "Liên từ (conj)", "Thán từ (interj)", "Mạo từ (art)"};
    ArrayList<Dictionary> dictionaryArrayListResult = new ArrayList<>();
    Admin_Add_Dictionary_Custom_Adapter admin_add_dictionary_custom_adapter;
    DictionaryHandler dictionaryHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_edit_dictionary);
        addControl();
        dictionaryHandler = new DictionaryHandler(Admin_Edit_Dictionary.this, DB_NAME, null, DB_VERSION);
        //------------------------------------//
        spinnerLoaiTuCreate();

        setupRecyclerView();
        loadAllDictionary();

        int soTu = dictionaryArrayListResult.size();
        tvSuaTuVungCount.setText(soTu + " từ ");
        addEvent();
    }

    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStack(); // Quay lại Fragment trước đó
    }

    void addControl() {
        imgBackToMainPageSTD = findViewById(R.id.imgBackToMainPageSTD);
        imgSuaTDSearch = findViewById(R.id.imgSuaTDSearch);
        edtSuaTDSearch = findViewById(R.id.edtSuaTDSearch);
        edtSuaMaTuVungTD = findViewById(R.id.edtSuaMaTuVungTD);
        edtSuaTuTATD = findViewById(R.id.edtSuaTuTATD);
        edtSuaTuTVTD = findViewById(R.id.edtSuaTuTVTD);
        edtSuaCachPhatAmTD = findViewById(R.id.edtSuaCachPhatAmTD);
        edtSuaGioiTuDiKemTD = findViewById(R.id.edtSuaGioiTuDiKemTD);
        edtSuaViDuSDTD = findViewById(R.id.edtSuaViDuSDTD);
        spinnerSuaLoaiTuTD = findViewById(R.id.spinnerSuaLoaiTuTD);
        btnSuaTV = findViewById(R.id.btnSuaTV);
        rvSuaTuDienHienCo = findViewById(R.id.rvSuaTuDienHienCo);
        tvSuaTuVungCount = findViewById(R.id.tvSuaTuVungCount);
    }

    void addEvent() {
        imgBackToMainPageSTD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        imgSuaTDSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dictionarySearchValue = edtSuaTDSearch.getText().toString().trim();
                if (dictionarySearchValue.isEmpty()) {
                    loadAllDictionary();
                } else {
                    searchDictionary(dictionarySearchValue);
                    edtSuaTDSearch.setText("");
                }
            }
        });

        btnSuaTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String maTD = edtSuaMaTuVungTD.getText().toString().trim();
                String tuTATD = edtSuaTuTATD.getText().toString().trim();
                String tuTVTD = edtSuaTuTVTD.getText().toString().trim();
                String gioiTuTD = edtSuaGioiTuDiKemTD.getText().toString().trim();
                String cachPhatAmTD = edtSuaCachPhatAmTD.getText().toString().trim();
                String loaiTuTD = spinnerSuaLoaiTuTD.getSelectedItem().toString();
                String viDu = edtSuaViDuSDTD.getText().toString().trim();

                if (maTD.isEmpty() || tuTATD.isEmpty() || tuTVTD.isEmpty() ||  gioiTuTD.isEmpty() || cachPhatAmTD.isEmpty() || loaiTuTD.isEmpty() || viDu.isEmpty()) {
                    Toast.makeText(Admin_Edit_Dictionary.this, "Điền đầy đủ thông tin trước khi thêm.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (dictionaryHandler.checkDictionaryByNameAndCode(tuTATD, maTD)) {
                    Toast.makeText(Admin_Edit_Dictionary.this, "Từ tiếng Anh này đã tồn tại. Kiểm tra lại", Toast.LENGTH_SHORT).show();
                    return;
                }

                Dictionary dictionary = new Dictionary();
                dictionary.setMaTuVung(maTD);
                dictionary.setTuTiengAnh(tuTATD);
                dictionary.setTuTiengViet(tuTVTD);
                dictionary.setGioiTuDiKem(gioiTuTD);
                dictionary.setCachPhatAm(cachPhatAmTD);
                dictionary.setLoaiTu(loaiTuTD);
                dictionary.setViDu(viDu);
                createAlertDialogEditDictionary(dictionary).show();
            }
        });
    }

    void loadAllDictionary() {
        dictionaryArrayListResult.clear();
        dictionaryArrayListResult = dictionaryHandler.loadAllDataOfDictionary();
        admin_add_dictionary_custom_adapter.setDictionaryList(dictionaryArrayListResult);
    }

    void searchDictionary(String searchQuery) {
        dictionaryArrayListResult.clear();
        dictionaryArrayListResult = dictionaryHandler.searchDictionaryByNameOrCode(searchQuery);
        admin_add_dictionary_custom_adapter.setDictionaryList(dictionaryArrayListResult);
    }

    void spinnerLoaiTuCreate() {
        String[] dsLoaiTuString = new String[dsLoaiTu.length];
        for (int i = 0; i < dsLoaiTu.length; i++) {
            dsLoaiTuString[i] = String.valueOf(dsLoaiTu[i]);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, dsLoaiTuString);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSuaLoaiTuTD.setAdapter(adapter);
    }

    void setupRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(Admin_Edit_Dictionary.this, RecyclerView.VERTICAL, false);
        rvSuaTuDienHienCo.addItemDecoration(new DividerItemDecoration(Admin_Edit_Dictionary.this, DividerItemDecoration.VERTICAL));
        rvSuaTuDienHienCo.setLayoutManager(layoutManager);
        rvSuaTuDienHienCo.setItemAnimator(new DefaultItemAnimator());
        admin_add_dictionary_custom_adapter = new Admin_Add_Dictionary_Custom_Adapter(dictionaryArrayListResult, new Admin_Add_Dictionary_Custom_Adapter.ItemClickListener() {
            @Override
            public void onItemClick(Dictionary dictionary) {
                edtSuaMaTuVungTD.setText(dictionary.getMaTuVung());
                edtSuaTuTATD.setText(dictionary.getTuTiengAnh());
                edtSuaTuTVTD.setText(dictionary.getTuTiengViet());
                edtSuaGioiTuDiKemTD.setText(dictionary.getGioiTuDiKem());
                edtSuaCachPhatAmTD.setText(dictionary.getCachPhatAm());
                edtSuaViDuSDTD.setText(dictionary.getViDu());

                int index = -1;
                for (int i = 0; i < dsLoaiTu.length; i++) {
                    if (dsLoaiTu[i].equals(dictionary.getLoaiTu())) {
                        index = i;
                        break;
                    }
                }
                if (index != -1) {
                    spinnerSuaLoaiTuTD.setSelection(index);
                }
            }
        });
        rvSuaTuDienHienCo.setAdapter(admin_add_dictionary_custom_adapter);
    }

    private void clearInputFields() {
        edtSuaMaTuVungTD.setText("");
        edtSuaTuTATD.setText("");
        edtSuaTuTVTD.setText("");
        edtSuaGioiTuDiKemTD.setText("");
        edtSuaCachPhatAmTD.setText("");
        edtSuaViDuSDTD.setText("");

        spinnerSuaLoaiTuTD.setSelection(0);
    }

    private AlertDialog createAlertDialogEditDictionary(Dictionary dictionary) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Admin_Edit_Dictionary.this);
        builder.setTitle("Edit Dictionary");
        builder.setMessage("Bạn có muốn sửa từ vựng này không ?");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dictionaryHandler.updateDictionary(dictionary);
                loadAllDictionary();
                Toast.makeText(Admin_Edit_Dictionary.this, "Sửa thành công.", Toast.LENGTH_SHORT).show();
                clearInputFields();
                int soTu = dictionaryArrayListResult.size();
                tvSuaTuVungCount.setText(soTu + " từ ");
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
}