package com.example.doanungdung2.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.doanungdung2.Controller.GrammarCategoryHandler;
import com.example.doanungdung2.Model.GrammarCategory;
import com.example.doanungdung2.Model.Question;
import com.example.doanungdung2.R;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

public class Admin_Add_Grammar_Category extends AppCompatActivity {
    private static final String DB_NAME = "AppHocTiengAnh";
    private static final int DB_VERSION = 1;
    GrammarCategoryHandler grammarCategoryHandler;
    GrammarCategory grammarCategory;
    ArrayList<GrammarCategory> grammarCategoryArrayList = new ArrayList<>();
    ArrayList<String> dataLV = new ArrayList<>();
    ArrayAdapter<String> adapter;
    ImageView imgBackDNP;
    EditText edtMaDNP, edtTenDNP, edtMoTaDNP;
    Button btnThemDNP;
    ListView lvDNP;
    String maDNP = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_grammar_category);
        addControl();
        grammarCategoryHandler = new GrammarCategoryHandler(Admin_Add_Grammar_Category.this, DB_NAME,
                null, DB_VERSION);
        loadAllDataGrammarCate();

        maDNP = Admin_Add_Exercise.createAutoExerciseCode("DNP");
        edtMaDNP.setText(maDNP);

        addEvent();
    }

    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStack();
    }
    void addControl()
    {
        imgBackDNP = findViewById(R.id.imgBackDNP);
        edtMaDNP = findViewById(R.id.edtMaDNP);
        edtTenDNP = findViewById(R.id.edtTenDNP);
        edtMoTaDNP = findViewById(R.id.edtMoTaDNP);
        btnThemDNP = findViewById(R.id.btnThemDNP);
        lvDNP = findViewById(R.id.lvDNP);
    }
    void addEvent()
    {
        imgBackDNP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        lvDNP.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                grammarCategory = grammarCategoryArrayList.get(i);
                edtMaDNP.setText(grammarCategory.getMaDangNguPhap());
                edtTenDNP.setText(grammarCategory.getTenDangNguPhap());
                edtMoTaDNP.setText(grammarCategory.getMoTa());
            }
        });
        btnThemDNP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tenDNP = edtTenDNP.getText().toString();
                String moTa = edtMoTaDNP.getText().toString();
                boolean check = grammarCategoryHandler.checkNameAndCodeOfGrammarCate(maDNP, tenDNP);
                if (validVluesEnter(maDNP, tenDNP, moTa))
                {
                    if (!check)
                    {
                        grammarCategory = new GrammarCategory(maDNP, tenDNP, moTa);
                        createAlertDialog(grammarCategory).show();
                    }else {
                        Toast.makeText(Admin_Add_Grammar_Category.this,
                                "Mã hoặc tên dạng ngữ pháp đã tồn tại trước đó!", Toast.LENGTH_SHORT).show();
                        resetActivity();
                        return;
                    }
                }else
                {
                    Toast.makeText(Admin_Add_Grammar_Category.this,
                            "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });
    }
    void loadAllDataGrammarCate()
    {
        grammarCategoryArrayList = grammarCategoryHandler.loadAllDataGrammarCategory();
        dataLV = Admin_GrammarCategory_Mainpage_Fragment.convertObjectToString(grammarCategoryArrayList);
        Collections.reverse(dataLV);
        adapter = new ArrayAdapter<>(Admin_Add_Grammar_Category.this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                dataLV);
        lvDNP.setAdapter(adapter);
    }
    boolean validVluesEnter(String maDNP, String tenDNP, String moTa)
    {
        if (maDNP.isEmpty() || tenDNP.isEmpty() || moTa.isEmpty())
        {
            return false;
        }
        return true;
    }
    private AlertDialog createAlertDialog(GrammarCategory grammarCategory) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Admin_Add_Grammar_Category.this);
        builder.setTitle("Thêm dạng ngữ pháp");
        builder.setMessage("Bạn có muốn thêm dạng ngữ pháp này không ?");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                grammarCategoryHandler.addNewGrammarCategory(grammarCategory);
                Toast.makeText(Admin_Add_Grammar_Category.this, "Thêm dạng ngữ pháp mới thành công!"
                        , Toast.LENGTH_SHORT).show();
                loadAllDataGrammarCate();
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
        maDNP = Admin_Add_Exercise.createAutoExerciseCode("DNP");
        edtMaDNP.setText(maDNP);
        edtTenDNP.setText("");
        edtMoTaDNP.setText("");
    }
}