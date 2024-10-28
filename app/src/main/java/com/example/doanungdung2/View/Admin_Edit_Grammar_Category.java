package com.example.doanungdung2.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doanungdung2.Controller.GrammarCategoryHandler;
import com.example.doanungdung2.Model.GrammarCategory;
import com.example.doanungdung2.R;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

public class Admin_Edit_Grammar_Category extends AppCompatActivity {
    private static final String DB_NAME = "AppHocTiengAnh";
    private static final int DB_VERSION = 1;
    ArrayList<GrammarCategory> grammarCategoryArrayList = new ArrayList<>();
    ArrayList<GrammarCategory> searchData = new ArrayList<>();
    Admin_Edit_Grammart_Category_CustomAdaper_RecylerView admin_edit_grammart_category_customAdaper_recylerView;
    GrammarCategoryHandler grammarCategoryHandler;
    GrammarCategory grammarCategory;
    GrammarCategory grammarCategoryOld;
    EditText edtSuaDNPSearch, edtSuaMaDNP, edtSuaTenDNP,edtSuaMoTaDNP;
    Button btnSuaDNP;
    RecyclerView rvSuaDNPSearch;
    ImageView imgSuaDNPSearch, imgBackToMainPageSDNP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_edit_grammar_category);
        addControl();
        grammarCategoryHandler = new GrammarCategoryHandler(Admin_Edit_Grammar_Category.this,
                DB_NAME, null, DB_VERSION);
        setupRecyclerView();
        loadAdllDataGrammarCate();
        addEvent();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStack();
    }
    void addControl() {
        edtSuaDNPSearch = (EditText) findViewById(R.id.edtSuaDNPSearch);
        edtSuaMaDNP = (EditText) findViewById(R.id.edtSuaMaDNP);
        edtSuaTenDNP = (EditText) findViewById(R.id.edtSuaTenDNP);
        edtSuaMoTaDNP = (EditText) findViewById(R.id.edtSuaMoTaDNP);
        btnSuaDNP = (Button) findViewById(R.id.btnSuaDNP);
        rvSuaDNPSearch = (RecyclerView) findViewById(R.id.rvSuaDNPSearch);
        imgSuaDNPSearch = (ImageView) findViewById(R.id.imgSuaDNPSearch);
        imgBackToMainPageSDNP = (ImageView) findViewById(R.id.imgBackToMainPageSDNP);
    }
    void addEvent()
    {
        imgBackToMainPageSDNP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        imgSuaDNPSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String keyWord = edtSuaDNPSearch.getText().toString();
                if (keyWord.isEmpty())
                {
                    loadAdllDataGrammarCate();
                }else {
                    searchData = grammarCategoryHandler.searchByCodeOrName(keyWord);
                    if (searchData.size() != 0)
                    {
                        loadDataForSearch(searchData);
                    }else {
                        Toast.makeText(Admin_Edit_Grammar_Category.this, "Không có kết quả phù hợp!",
                                Toast.LENGTH_SHORT).show();
                        edtSuaDNPSearch.setText("");
                        return;
                    }
                }
            }
        });
        btnSuaDNP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String maDNP = edtSuaMaDNP.getText().toString();
                String tenDNP = edtSuaTenDNP.getText().toString();
                String moTa = edtSuaMoTaDNP.getText().toString();
                grammarCategory = new GrammarCategory(maDNP, tenDNP, moTa);
                if (!tenDNP.isEmpty() || !moTa.isEmpty())
                {
                    if (!tenDNP.equals(grammarCategoryOld.getTenDangNguPhap()) && !moTa.equals(grammarCategoryOld.getMoTa()))
                    {
                        createAlertDialog(grammarCategory).show();
                    }else
                    {
                        Toast.makeText(Admin_Edit_Grammar_Category.this, "Bạn cần nhập thông tin mới để có thể cập nhật!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }else
                {
                    Toast.makeText(Admin_Edit_Grammar_Category.this, "Vui lòng nhập đầy đủ thông tin!",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

            }
        });
    }
    void loadAdllDataGrammarCate()
    {
        grammarCategoryArrayList = grammarCategoryHandler.loadAllDataGrammarCategory();
        admin_edit_grammart_category_customAdaper_recylerView.setGrammarCategoryArrayList(grammarCategoryArrayList);
    }
    void loadDataForSearch(ArrayList<GrammarCategory> data)
    {
        admin_edit_grammart_category_customAdaper_recylerView.setGrammarCategoryArrayList(data);
    }
    void setupRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(Admin_Edit_Grammar_Category.this, RecyclerView.VERTICAL, false);
        rvSuaDNPSearch.addItemDecoration(new DividerItemDecoration(Admin_Edit_Grammar_Category.this, DividerItemDecoration.VERTICAL));
        rvSuaDNPSearch.setLayoutManager(layoutManager);
        rvSuaDNPSearch.setItemAnimator(new DefaultItemAnimator());
        admin_edit_grammart_category_customAdaper_recylerView = new Admin_Edit_Grammart_Category_CustomAdaper_RecylerView(grammarCategoryArrayList, new Admin_Edit_Grammart_Category_CustomAdaper_RecylerView.ItemClickListener() {
            @Override
            public void onItemClick(GrammarCategory grammarCategory) {
                edtSuaMaDNP.setText(grammarCategory.getMaDangNguPhap());
                edtSuaTenDNP.setText(grammarCategory.getTenDangNguPhap());
                edtSuaMoTaDNP.setText(grammarCategory.getMoTa());
                //Để so sánh với dữ liệu cũ và mới có khác nhau không khi sửa
                grammarCategoryOld = new GrammarCategory(grammarCategory.getMaDangNguPhap(),
                        grammarCategory.getTenDangNguPhap(), grammarCategory.getMoTa());
            }
        });
        rvSuaDNPSearch.setAdapter(admin_edit_grammart_category_customAdaper_recylerView);
    }
    private AlertDialog createAlertDialog(GrammarCategory grammarCategory) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Admin_Edit_Grammar_Category.this);
        builder.setTitle("Sửa dạng ngữ pháp");
        builder.setMessage("Bạn có muốn sửa dạng ngữ pháp này không ?");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                grammarCategoryHandler.upDateGrammarCategory(grammarCategory);
                Toast.makeText(Admin_Edit_Grammar_Category.this, "Sửa dạng ngữ pháp thành công!"
                        , Toast.LENGTH_SHORT).show();
                loadAdllDataGrammarCate();
                resetActivity();
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
    void resetActivity()
    {
        edtSuaMaDNP.setText("");
        edtSuaTenDNP.setText("");
        edtSuaMoTaDNP.setText("");
    }
}