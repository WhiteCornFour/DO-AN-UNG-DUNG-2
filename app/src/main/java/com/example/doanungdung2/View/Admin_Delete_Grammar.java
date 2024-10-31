package com.example.doanungdung2.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.doanungdung2.Controller.GrammarHandler;
import com.example.doanungdung2.Model.Grammar;
import com.example.doanungdung2.R;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Admin_Delete_Grammar extends AppCompatActivity {
    private static final String DB_NAME = "AppHocTiengAnh";
    private static final int DB_VERSION = 1;
    ImageView imgBackDeleteGrammarToMainPage, imgSerchForDeleteInNP;
    EditText edtSearchForDeleteInNP;
    Button btnResetForListViewInNP, btnDeleteAllForLVInNP;
    ListView lvDSNPTrongXoa;
    GrammarHandler grammarHandler;
    ArrayList<Grammar> grammarArrayList = new ArrayList<>();
    ArrayList<Grammar> searchData = new ArrayList<>();
    Admin_Delete_Grammar_CustomAdapter_LV adapter_lv;
    Grammar grammar;
    boolean[] checkStates;
    boolean[] checkStateSearch;
    String maDNP = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_delete_grammar);
        addControl();
        grammarHandler = new GrammarHandler(this, DB_NAME, null, DB_VERSION);
        Intent intent = getIntent();
        maDNP = intent.getStringExtra("maDNP");
        if (maDNP == null)
        {
            loadAllDataLV();
        }else
        {
            loadDataForSearch(maDNP);
            Log.d("maDNP", maDNP);
        }
        addEvent();
    }

    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStack();
    }
    void addControl()
    {
        imgBackDeleteGrammarToMainPage = findViewById(R.id.imgBackDeleteGrammarToMainPage);
        imgSerchForDeleteInNP = findViewById(R.id.imgSerchForDeleteInNP);
        edtSearchForDeleteInNP = findViewById(R.id.edtSearchForDeleteInNP);
        btnResetForListViewInNP = findViewById(R.id.btnResetForListViewInNP);
        btnDeleteAllForLVInNP = findViewById(R.id.btnDeleteAllForLVInNP);
        lvDSNPTrongXoa = findViewById(R.id.lvDSNPTrongXoa);
    }
    void addEvent()
    {
        imgBackDeleteGrammarToMainPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnResetForListViewInNP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadAllDataLV();
                Toast.makeText(Admin_Delete_Grammar.this, "Đã cập nhật thông tin!", Toast.LENGTH_SHORT).show();
            }
        });
        imgSerchForDeleteInNP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String keyWord = edtSearchForDeleteInNP.getText().toString();
                if (keyWord.isEmpty())
                {
                    Toast.makeText(Admin_Delete_Grammar.this, "Vui lòng nhập tên hoặc mã ngữ pháp!", Toast.LENGTH_SHORT).show();
                }else {
                    loadDataForSearch(keyWord);
                }
            }
        });
        lvDSNPTrongXoa.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (grammarArrayList.size() != 0)
                {
                    grammar = grammarArrayList.get(i);
                }else {
                    grammar = searchData.get(i);
                }
                String maNP = grammar.getMaNguPhap();
                createDialog(maNP);
                return true;
            }
        });
        btnDeleteAllForLVInNP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean check = false;
                if (grammarArrayList.size() != 0)
                {
                    for (boolean c: checkStates
                         ) {
                        if(c)
                        {
                            check = true;
                            break;
                        }
                    }
                }else {
                    for (boolean s: checkStateSearch
                         ) {
                        if (s)
                        {
                            check = true;
                            break;
                        }
                    }
                }

                if (!check)
                {
                    Toast.makeText(Admin_Delete_Grammar.this, "Vui lòng chọn ít nhất một ngữ pháp!",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                AlertDialog alertDialog = createAlertDialogDeleteGrammar();
                alertDialog.show();
            }
        });
    }
    void loadAllDataLV()
    {
        grammarArrayList = grammarHandler.loadAllDataOfGrammar();
        checkStates = new boolean[grammarArrayList.size()];
        adapter_lv = new Admin_Delete_Grammar_CustomAdapter_LV(this,
                R.layout.layout_admin_delete_grammar_customadapter_lv, grammarArrayList, checkStates);
        lvDSNPTrongXoa.setAdapter(adapter_lv);
    }
    void loadDataForSearch(String keyWord)
    {
        searchData = grammarHandler.searchByCodeOrNameGrammar(keyWord);
        checkStateSearch = new boolean[searchData.size()];
        adapter_lv = new Admin_Delete_Grammar_CustomAdapter_LV(this,
                R.layout.layout_admin_delete_grammar_customadapter_lv, searchData, checkStateSearch);
        lvDSNPTrongXoa.setAdapter(adapter_lv);
    }
    void createDialog(String maNguPhap) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Xác nhận");
        builder.setMessage("Bạn có chắc muốn xóa ngữ pháp này hay không?");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                grammarHandler.deleteGrammar(maNguPhap);
                // Cập nhật danh sách bằng cách xóa phần tử tương ứng
                if (grammarArrayList.size() != 0)
                {
                    grammarArrayList.remove(grammar);
                }else
                {
                    searchData.remove(grammar);
                }
                // Thông báo cho adapter biết dữ liệu đã thay đổi
                adapter_lv.notifyDataSetChanged();
                Toast.makeText(Admin_Delete_Grammar.this
                        , "Xóa ngữ pháp thành công!", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    AlertDialog createAlertDialogDeleteGrammar() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Admin_Delete_Grammar.this);
        builder.setTitle("Xóa ngữ pháp");
        builder.setMessage("Bạn có chắc muốn xóa các ngữ pháp đã chọn?");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                deleteSelectedGrammar();
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
    void deleteSelectedGrammar()
    {
        ArrayList<Grammar> listToDelete = new ArrayList<>();
        if (grammarArrayList.size() != 0)
        {
            for (int i = 0; i < checkStates.length; i ++)
            {
                if (checkStates[i])
                    listToDelete.add(grammarArrayList.get(i));
            }
        }else {
            for (int i = 0; i < checkStateSearch.length; i++)
            {
                if (checkStateSearch[i])
                    listToDelete.add(searchData.get(i));
            }
        }
        for (Grammar gr: listToDelete
             ) {
            grammarHandler.deleteGrammar(gr.getMaNguPhap());
            grammarArrayList.remove(gr);
        }
        if (grammarArrayList.size() != 0)
        {
            loadAllDataLV();
            checkStates = new boolean[grammarArrayList.size()];
        }else
        {
            loadDataForSearch(maDNP);
        }
    }
}