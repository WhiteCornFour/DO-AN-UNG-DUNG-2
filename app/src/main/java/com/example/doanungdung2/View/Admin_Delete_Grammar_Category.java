package com.example.doanungdung2.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.doanungdung2.Controller.GrammarCategoryHandler;
import com.example.doanungdung2.Controller.GrammarHandler;
import com.example.doanungdung2.Model.GrammarCategory;
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

public class Admin_Delete_Grammar_Category extends AppCompatActivity {
    private static final String DB_NAME = "AppHocTiengAnh";
    private static final int DB_VERSION = 1;
    ImageView imgBackDeleteToMainPageDNP, imgSerchForDeleteInDNP;
    Button btnResetForListViewInDNP, btnDeleteAllForLVInDNP;
    ListView lvDSDNPTrongXoa;
    EditText edtSearchForDeleteInDangBT;
    GrammarCategory grammarCategory;
    ArrayList<GrammarCategory> grammarCategoryArrayList = new ArrayList<>();
    Admin_Delete_Grammar_Category_CustomAdapter_LV adapter_lv;
    GrammarCategoryHandler grammarCategoryHandler;
    GrammarHandler grammarHandler;
    boolean[] checkStates;
    //boolean[] checkStatesSearch;
    String keyWord = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_delete_grammar_category);
        addControl();
        grammarCategoryHandler = new GrammarCategoryHandler(this, DB_NAME, null, DB_VERSION);
        grammarHandler = new GrammarHandler(this, DB_NAME, null, DB_VERSION);
        loadAllDataLV();
        addEvent();
    }
    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStack();
    }
    void addControl()
    {
        imgBackDeleteToMainPageDNP  = findViewById(R.id.imgBackDeleteToMainPageDNP);
        imgSerchForDeleteInDNP = findViewById(R.id.imgSerchForDeleteInDNP);
        btnResetForListViewInDNP = findViewById(R.id.btnResetForListViewInDNP);
        btnDeleteAllForLVInDNP  = findViewById(R.id.btnDeleteAllForLVInDNP);
        lvDSDNPTrongXoa = findViewById(R.id.lvDSDNPTrongXoa);
        edtSearchForDeleteInDangBT = findViewById(R.id.edtSearchForDeleteInDangBT);
    }
    void  addEvent()
    {
        imgBackDeleteToMainPageDNP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnResetForListViewInDNP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadAllDataLV();
                edtSearchForDeleteInDangBT.setText("");
            }
        });
        imgSerchForDeleteInDNP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                keyWord = edtSearchForDeleteInDangBT.getText().toString();
                if (keyWord.isEmpty())
                {
                    Toast.makeText(Admin_Delete_Grammar_Category.this, "Vui lòng nhập mã hoặc tên của dạng ngữ pháp!",
                            Toast.LENGTH_SHORT).show();
                    return;
                }else
                {
                    loadDataSearch(keyWord);
                }
            }
        });
        lvDSDNPTrongXoa.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                grammarCategory = grammarCategoryArrayList.get(i);
                String maDangNguPhap = grammarCategory.getMaDangNguPhap();
                boolean check = grammarHandler.checkGrammarCateOnGrammar(maDangNguPhap);
                if (!check)
                {
                    createDialog(maDangNguPhap);
                    return true;
                }else
                {
                    dialogThongBao(maDangNguPhap);
                    return false;
                }
            }
        });
        btnDeleteAllForLVInDNP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean anyCheck = false;
                if (grammarCategoryArrayList.size() != 0)
                {
                    for (boolean check : checkStates
                         ) {
                        if (check)
                        {
                            anyCheck = true;
                            break;
                        }
                    }
                }
//                else
//                {
//                    Log.d("Nhanh duoi", "true");
//                    for (boolean check1 : checkStatesSearch
//                         ) {
//                        if (check1)
//                        {
//                            Log.d("Check", String.valueOf(true));
//                            anyCheck = true;
//                            break;
//                        }
//                    }
//                }

                if (!anyCheck)
                {
                    //Log.d("So luong phan tu trong dataOfSearch", String.valueOf(searchDataLV.size()));
                    Toast.makeText(Admin_Delete_Grammar_Category.this,
                            "Vui lòng chọn ít nhất một dạng ngữ pháp!", Toast.LENGTH_SHORT).show();
                    return;
                }
                AlertDialog alertDialog = createAlertDialogDeleteAGrammarCate();
                alertDialog.show();
            }
        });
    }
    void createDialog(String maDangNguPhap) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Xác nhận");
        builder.setMessage("Bạn có chắc muốn xóa dạng ngữ pháp này hay không?");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                grammarCategoryHandler.deleteGrammarCategory(maDangNguPhap);
                // Cập nhật danh sách bằng cách xóa phần tử tương ứng
                grammarCategoryArrayList.remove(grammarCategory);
                // Thông báo cho adapter biết dữ liệu đã thay đổi
                adapter_lv.notifyDataSetChanged();
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
    void dialogThongBao(String maDangNguPhap)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Thông báo");
        builder.setMessage("Dạng ngữ pháp hiện tại đang chứa 1 danh sách ngữ pháp. \n" +
                "Vui lòng kiểm tra lại các ngữ pháp liên quan đến dạng ngữ pháp có mã: "+ maDangNguPhap);
        builder.setPositiveButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("Chuyển tiếp", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
//                Intent intent = new Intent(Admin_Delete_ExercisesCategory.this, Admin_Delete_Exercise.class);
//                intent.putExtra("maDBT", maDBT);
//                startActivity(intent);
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    void loadAllDataLV()
    {
        grammarCategoryArrayList = grammarCategoryHandler.loadAllDataGrammarCategory();
        checkStates = new boolean[grammarCategoryArrayList.size()];
        adapter_lv = new Admin_Delete_Grammar_Category_CustomAdapter_LV(
                Admin_Delete_Grammar_Category.this,
                R.layout.layout_admin_delete_grammar_category_customadapter_lv, grammarCategoryArrayList, checkStates);
        lvDSDNPTrongXoa.setAdapter(adapter_lv);
    }
    void loadDataSearch(String keyWord)
    {
        grammarCategoryArrayList = grammarCategoryHandler.searchByCodeOrName(keyWord);
        if (grammarCategoryArrayList != null)
        {
            checkStates = new boolean[grammarCategoryArrayList.size()];
            adapter_lv = new Admin_Delete_Grammar_Category_CustomAdapter_LV(Admin_Delete_Grammar_Category.this,
                    R.layout.layout_admin_delete_grammar_category_customadapter_lv, grammarCategoryArrayList, checkStates);
            lvDSDNPTrongXoa.setAdapter(adapter_lv);
        }else {
            Toast.makeText(this, "Không tìm thấy dữ liệu phù hợp!", Toast.LENGTH_SHORT).show();
            return;
        }
    }
    private AlertDialog createAlertDialogDeleteAGrammarCate() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Admin_Delete_Grammar_Category.this);
        builder.setTitle("Xóa dạng ngữ pháp");
        builder.setMessage("Bạn có chắc chắn muốn xóa dạng ngữ pháp này không?");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                deleteMultipleGrammarCate();
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
    void deleteMultipleGrammarCate()
    {
        ArrayList<GrammarCategory> dataDelete = new ArrayList<>();
        if (grammarCategoryArrayList.size() != 0)
        {
            for (int i = 0; i < checkStates.length; i++)
            {
                if (checkStates[i])
                    dataDelete.add(grammarCategoryArrayList.get(i));
            }
        }
//        else {
//            Log.d("So luong phan tu trong dataOfSearch", String.valueOf(searchDataLV.size()));
//            for (int i = 0; i < checkStatesSearch.length; i++)
//            {
//                if (checkStatesSearch[i])
//                    dataDelete.add(searchDataLV.get(i));
//            }
//        }

        for (GrammarCategory gr: dataDelete)
        {
            boolean rs = grammarHandler.checkGrammarCateOnGrammar(gr.getMaDangNguPhap());
            if (!rs)
            {
                grammarCategoryHandler.deleteGrammarCategory(gr.getMaDangNguPhap());
            }else {
                dialogThongBao(gr.getMaDangNguPhap());
            }
        }
        if (grammarCategoryArrayList.size() != 0)
        {
            checkStates = new boolean[grammarCategoryArrayList.size()];
            loadAllDataLV();
        }
//        else {
//            loadDataSearch(keyWord);
//            checkStatesSearch = new boolean[searchDataLV.size()];
//        }
    }
}
