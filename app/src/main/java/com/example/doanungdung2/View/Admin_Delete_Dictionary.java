package com.example.doanungdung2.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doanungdung2.Controller.DictionaryHandler;
import com.example.doanungdung2.Model.Dictionary;
import com.example.doanungdung2.R;

import java.util.ArrayList;

public class Admin_Delete_Dictionary extends AppCompatActivity {

    private static final String DB_NAME = "AppHocTiengAnh";
    private static final int DB_VERSION = 1;
    ImageView imgBackToMainPageXTD, imgXoaTDSearch;
    EditText edtXoaTDSearch;
    Button btnChonAllTD, btnHuyChonAllTD, btnXoaAllTD;
    RecyclerView rvXoaTuDienHienCo;
    TextView tvXoaTuVungCount;
    ArrayList<Dictionary> dictionaryArrayListResult = new ArrayList<>();
    Admin_Delete_Dictionary_Custom_Adapter admin_delete_dictionary_custom_adapter;
    DictionaryHandler dictionaryHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_delete_dictionary);
        addControl();
        dictionaryHandler = new DictionaryHandler(Admin_Delete_Dictionary.this, DB_NAME, null, DB_VERSION);
        //------------------------------------------//
        setupRecyclerView();
        loadAllDictionary();

        int soTu = dictionaryArrayListResult.size();
        tvXoaTuVungCount.setText(soTu + " từ ");
        addEvent();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStack(); // Quay lại Fragment trước đó
    }

    void addControl() {
        imgBackToMainPageXTD = findViewById(R.id.imgBackToMainPageXTD);
        imgXoaTDSearch = findViewById(R.id.imgXoaTDSearch);
        edtXoaTDSearch = findViewById(R.id.edtXoaTDSearch);
        btnChonAllTD = findViewById(R.id.btnChonAllTD);
        btnXoaAllTD = findViewById(R.id.btnXoaAllTD);
        btnHuyChonAllTD = findViewById(R.id.btnHuyChonAllTD);
        tvXoaTuVungCount = findViewById(R.id.tvXoaTuVungCount);
        rvXoaTuDienHienCo = findViewById(R.id.rvXoaTuDienHienCo);
    }

    void addEvent() {
        imgBackToMainPageXTD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        imgXoaTDSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dictionarySearchValue = edtXoaTDSearch.getText().toString().trim();
                if (dictionarySearchValue.isEmpty()) {
                    loadAllDictionary();
                } else {
                    searchDictionary(dictionarySearchValue);
                    edtXoaTDSearch.setText("");
                }
            }
        });

        btnChonAllTD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectAllCheckboxes();
            }
        });

        btnHuyChonAllTD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deselectAllCheckboxes();
            }
        });

        btnXoaAllTD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Dictionary> itemsToDelete = new ArrayList<>();
                for (Dictionary dictionary : dictionaryArrayListResult) {
                    if (dictionary.isChecked()) {
                        itemsToDelete.add(dictionary);
                    }
                }
                if (itemsToDelete.isEmpty()) {
                    Toast.makeText(Admin_Delete_Dictionary.this, "Vui lòng chọn ít nhất một từ để xóa", Toast.LENGTH_SHORT).show();
                    return;
                }
                createAlertDialogDeleteDictionaries(itemsToDelete).show();
            }
        });
    }

    void selectAllCheckboxes() {
        for (Dictionary dictionary : dictionaryArrayListResult) {
            dictionary.setChecked(true);
        }
        admin_delete_dictionary_custom_adapter.notifyDataSetChanged();
    }

    void deselectAllCheckboxes() {
        for (Dictionary dictionary : dictionaryArrayListResult) {
            dictionary.setChecked(false);
        }
        admin_delete_dictionary_custom_adapter.notifyDataSetChanged();
    }

    void loadAllDictionary() {
        dictionaryArrayListResult.clear();
        dictionaryArrayListResult = dictionaryHandler.loadAllDataOfDictionary();
        admin_delete_dictionary_custom_adapter.setDictionaryList(dictionaryArrayListResult);
        updateDeleteButtonColor();
    }

    void searchDictionary(String searchQuery) {
        dictionaryArrayListResult.clear();
        dictionaryArrayListResult = dictionaryHandler.searchDictionaryByNameOrCode(searchQuery);
        admin_delete_dictionary_custom_adapter.setDictionaryList(dictionaryArrayListResult);
    }

    void setupRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(Admin_Delete_Dictionary.this, RecyclerView.VERTICAL, false);
        rvXoaTuDienHienCo.addItemDecoration(new DividerItemDecoration(Admin_Delete_Dictionary.this, DividerItemDecoration.VERTICAL));
        rvXoaTuDienHienCo.setLayoutManager(layoutManager);
        rvXoaTuDienHienCo.setItemAnimator(new DefaultItemAnimator());
        admin_delete_dictionary_custom_adapter = new Admin_Delete_Dictionary_Custom_Adapter(dictionaryArrayListResult, new Admin_Add_Dictionary_Custom_Adapter.ItemClickListener() {
            @Override
            public void onItemClick(Dictionary dictionary) {
                openDeleteDictionaryDialog(Gravity.CENTER, dictionary);
            }
        });

        rvXoaTuDienHienCo.setAdapter(admin_delete_dictionary_custom_adapter);
    }

    void updateDeleteButtonColor() {
        if (dictionaryArrayListResult.isEmpty()) {
            btnHuyChonAllTD.setEnabled(false);
            btnHuyChonAllTD.setBackground(getDrawable(R.drawable.backgr_button_enabled));
            btnHuyChonAllTD.setTextColor(getResources().getColor(R.color.black));
            btnChonAllTD.setEnabled(false);
            btnChonAllTD.setBackground(getDrawable(R.drawable.backgr_button_enabled));
            btnChonAllTD.setTextColor(getResources().getColor(R.color.black));
            btnXoaAllTD.setEnabled(false);
            btnXoaAllTD.setBackground(getDrawable(R.drawable.backgr_button_enabled));
            btnXoaAllTD.setTextColor(getResources().getColor(R.color.black));
        } else {
            btnHuyChonAllTD.setEnabled(true);
            btnHuyChonAllTD.setBackground(getDrawable(R.drawable.backgr_admin_white));
            btnHuyChonAllTD.setTextColor(getResources().getColor(R.color.blue));
            btnChonAllTD.setEnabled(true);
            btnChonAllTD.setBackground(getDrawable(R.drawable.backgr_admin_white));
            btnChonAllTD.setTextColor(getResources().getColor(R.color.blue));
            btnXoaAllTD.setEnabled(true);
            btnXoaAllTD.setBackground(getDrawable(R.drawable.backgr_admin_white));
            btnXoaAllTD.setTextColor(getResources().getColor(R.color.blue));
        }
    }

    //Dialog
    private AlertDialog createAlertDialogDeleteDictionaries(ArrayList<Dictionary> itemsToDelete) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Admin_Delete_Dictionary.this);
        builder.setTitle("Delete Dictionary");
        builder.setMessage("Bạn có muốn xóa " + itemsToDelete.size() + " từ vựng này không ?");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dictionaryHandler.deleteDictionaries(itemsToDelete);
                dictionaryArrayListResult.removeAll(itemsToDelete);
                admin_delete_dictionary_custom_adapter.setDictionaryList(dictionaryArrayListResult);
                Toast.makeText(Admin_Delete_Dictionary.this, "Đã xóa các từ đã chọn", Toast.LENGTH_SHORT).show();
                int soTu = dictionaryArrayListResult.size();
                tvXoaTuVungCount.setText(soTu + " từ ");
                updateDeleteButtonColor();
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

    private AlertDialog createAlertDialogDeleteDictionary(Dictionary dictionary) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Admin_Delete_Dictionary.this);
        builder.setTitle("Delete Dictionary");
        builder.setMessage("Bạn có muốn xóa từ vựng này không ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dictionaryHandler.deleteDictionary(dictionary);
                loadAllDictionary();
                int soTu = dictionaryArrayListResult.size();
                tvXoaTuVungCount.setText(soTu + " từ ");
                Toast.makeText(Admin_Delete_Dictionary.this, "Xóa từ thành công!", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        return builder.create();
    }

    private void openDeleteDictionaryDialog(int gravity, Dictionary dictionary) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog_admin_detail_dictionary);

        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttributes = window.getAttributes();

        windowAttributes.gravity = gravity;
        window.setAttributes(windowAttributes);

        if (Gravity.BOTTOM == gravity) {
            dialog.setCancelable(true);
        } else {
            dialog.setCancelable(false);
        }

        EditText edtMaTuVungXTD, edtTuTiengAnhXTD, edtTuTiengVietXTD, edtGioiTuXTD, edtCachPhatAmXTD, edtLoaiTuXTD;
        Button btnConfirmXTD, btnCancelXTD;

        edtMaTuVungXTD = dialog.findViewById(R.id.edtMaTuVungXTD);
        edtTuTiengAnhXTD = dialog.findViewById(R.id.edtTuTiengAnhXTD);
        edtTuTiengVietXTD = dialog.findViewById(R.id.edtTuTiengVietXTD);
        edtGioiTuXTD = dialog.findViewById(R.id.edtGioiTuXTD);
        edtCachPhatAmXTD = dialog.findViewById(R.id.edtCachPhatAmXTD);
        edtLoaiTuXTD = dialog.findViewById(R.id.edtLoaiTuXTD);
        btnConfirmXTD = dialog.findViewById(R.id.btnConfirmXTD);
        btnCancelXTD = dialog.findViewById(R.id.btnCancelXTD);

        edtMaTuVungXTD.setText(dictionary.getMaTuVung());
        edtTuTiengAnhXTD.setText(dictionary.getTuTiengAnh());
        edtTuTiengVietXTD.setText(dictionary.getTuTiengViet());
        edtGioiTuXTD.setText(dictionary.getGioiTuDiKem());
        edtCachPhatAmXTD.setText(dictionary.getCachPhatAm());
        edtLoaiTuXTD.setText(dictionary.getLoaiTu());

       btnCancelXTD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

       btnConfirmXTD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAlertDialogDeleteDictionary(dictionary).show();
                dialog.dismiss();
            }
        });

        dialog.show();
    }

}