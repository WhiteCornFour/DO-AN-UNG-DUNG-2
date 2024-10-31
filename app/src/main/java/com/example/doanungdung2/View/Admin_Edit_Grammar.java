package com.example.doanungdung2.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doanungdung2.Controller.GrammarCategoryHandler;
import com.example.doanungdung2.Controller.GrammarHandler;
import com.example.doanungdung2.Model.Grammar;
import com.example.doanungdung2.Model.GrammarCategory;
import com.example.doanungdung2.R;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

public class Admin_Edit_Grammar extends AppCompatActivity {
    private static final String DB_NAME = "AppHocTiengAnh";
    private static final int DB_VERSION = 1;
    ImageView imgBackToMainPageNP_Edit, imgSearchFor_Edit;
    EditText edtSearchNameOrCode_Grammar_Edit, edtMNP_Edit, edtTNP_Edit, edtNoiDungNP_Edit,
            edtCT_Edit, edtVD_Edit;
    Spinner spinnerDNP_Edit;
    RecyclerView recyclerViewNP_Edit;
    Button btnGrammar_Edit;
    Grammar grammar;
    Grammar grammarOld;
    GrammarCategoryHandler grammarCategoryHandler;
    ArrayList<Grammar> grammarArrayList = new ArrayList<>();
    ArrayList<GrammarCategory> grammarCategories = new ArrayList<>();
    ArrayAdapter<String> adapterSpinner;
    Admin_Add_Grammar_CustomAdapter_RecyclerView adapter;
    ArrayList<String> dataSpinner = new ArrayList<>();
    GrammarHandler grammarHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_edit_grammar);
        addControl();
        grammarHandler = new GrammarHandler(Admin_Edit_Grammar.this, DB_NAME, null, DB_VERSION);
        grammarCategoryHandler = new GrammarCategoryHandler(Admin_Edit_Grammar.this, DB_NAME, null, DB_VERSION);
        loadDataForSpinner();
        setUpRecyclerView();
        loadAllDataGrammar();
        addEvent();
    }

    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStack();
    }
    void addControl()
    {
        imgBackToMainPageNP_Edit = findViewById(R.id.imgBackToMainPageNP_Edit);
        imgSearchFor_Edit = findViewById(R.id.imgSearchFor_Edit);
        edtSearchNameOrCode_Grammar_Edit = findViewById(R.id.edtSearchNameOrCode_Grammar_Edit);
        edtMNP_Edit = findViewById(R.id.edtMNP_Edit);
        edtTNP_Edit = findViewById(R.id.edtTNP_Edit);
        edtNoiDungNP_Edit = findViewById(R.id.edtNoiDungNP_Edit);
        edtCT_Edit = findViewById(R.id.edtCT_Edit);
        edtVD_Edit = findViewById(R.id.edtVD_Edit);
        spinnerDNP_Edit = findViewById(R.id.spinnerDNP_Edit);
        btnGrammar_Edit = findViewById(R.id.btnGrammar_Edit);
        recyclerViewNP_Edit = findViewById(R.id.recyclerViewNP_Edit);
    }
    void addEvent()
    {
        imgBackToMainPageNP_Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        imgSearchFor_Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String keyWord = edtSearchNameOrCode_Grammar_Edit.getText().toString();
                if (keyWord.isEmpty())
                {
                    Toast.makeText(Admin_Edit_Grammar.this, "Vui lòng nhập mã hoặc tên ngữ pháp!",
                            Toast.LENGTH_SHORT).show();
                    loadAllDataGrammar();
                }else {
                    grammarArrayList = grammarHandler.searchByCodeOrNameGrammar(keyWord);
                    Collections.reverse(grammarCategories);
                    adapter.setGrammarArrayList(grammarArrayList);
                }
            }
        });
        btnGrammar_Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String maNP = edtMNP_Edit.getText().toString();
                String tenNP = edtTNP_Edit.getText().toString();
                String noiDung = edtNoiDungNP_Edit.getText().toString();
                String congThuc = edtCT_Edit.getText().toString();
                String viDu = edtVD_Edit.getText().toString();
                String maDNP = grammarCategoryHandler.searchGrammarCodeByName(spinnerDNP_Edit.getSelectedItem().toString());
                grammar = new Grammar(maNP, tenNP, noiDung, congThuc, viDu, maDNP);
                if (validInputValues(grammar))
                {
                    if (!compareBetweenNewAndOld(grammar, grammarOld))
                    {
                        createAlertDialog().show();
                    }else {
                        Toast.makeText(Admin_Edit_Grammar.this, "Bạn hãy nhập thông tin mới trước khi cập nhật!",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }
                }else {
                    Toast.makeText(Admin_Edit_Grammar.this, "Vui lòng kiểm tra thông tin nhập vào!",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });
    }
    ArrayList<String> convertObjectToString(ArrayList<GrammarCategory> grammarCategories)
    {
        ArrayList<String> stringArrayList = new ArrayList<>();
        for (GrammarCategory gr: grammarCategories
        ) {
            String kq = gr.getTenDangNguPhap();
            stringArrayList.add(kq);
        }
        return stringArrayList;
    }
    void loadDataForSpinner()
    {
        grammarCategories = grammarCategoryHandler.loadAllDataGrammarCategory();
        Collections.reverse(grammarCategories);
        dataSpinner = convertObjectToString(grammarCategories);
        adapterSpinner = new ArrayAdapter<>(Admin_Edit_Grammar.this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, dataSpinner);
        spinnerDNP_Edit.setAdapter(adapterSpinner);
    }
    void setUpRecyclerView()
    {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(Admin_Edit_Grammar.this);
        recyclerViewNP_Edit.addItemDecoration(new DividerItemDecoration(Admin_Edit_Grammar.this, DividerItemDecoration.VERTICAL));
        recyclerViewNP_Edit.setLayoutManager(layoutManager);
        recyclerViewNP_Edit.setItemAnimator(new DefaultItemAnimator());

        adapter = new Admin_Add_Grammar_CustomAdapter_RecyclerView(grammarArrayList, new Admin_Add_Grammar_CustomAdapter_RecyclerView.ItemClickListener() {
            @Override
            public void onItemClick(Grammar grammar) {
                edtMNP_Edit.setText(grammar.getMaNguPhap());
                edtTNP_Edit.setText(grammar.getTenNguPhap());
                edtNoiDungNP_Edit.setText((grammar.getNoiDung()));
                edtCT_Edit.setText(grammar.getCongThuc());
                edtVD_Edit.setText(grammar.getViDu());

                grammarOld = new Grammar(grammar.getMaNguPhap(), grammar.getTenNguPhap(),
                        grammar.getNoiDung(), grammar.getCongThuc(), grammar.getViDu(), grammar.getMaDangNguPhap());

                int index = -1;
                for (int i = 0; i < dataSpinner.size(); i++)
                {
                    if (Objects.equals(grammar.getMaDangNguPhap(), grammarCategoryHandler.searchGrammarCodeByName(dataSpinner.get(i))))
                    {
                        index = i;
                    }
                }
                if (index != -1)
                {
                    spinnerDNP_Edit.setSelection(index);
                }
            }
        });
        recyclerViewNP_Edit.setAdapter(adapter);
    }
    void loadAllDataGrammar()
    {
        grammarArrayList = grammarHandler.loadAllDataOfGrammar();
        adapter.setGrammarArrayList(grammarArrayList);
    }
    boolean validInputValues(Grammar grammar)
    {
        boolean check = true;
        if (grammar.getTenNguPhap().isEmpty())
            check = false;
        else if (grammar.getNoiDung().isEmpty())
            check = false;
        else if (grammar.getCongThuc().isEmpty())
            check = false;
        else if (grammar.getViDu().isEmpty())
            check = false;
        return check;
    }
    @NonNull
    private AlertDialog createAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Admin_Edit_Grammar.this);
        builder.setTitle("Sửa ngữ pháp");
        builder.setMessage("Bạn có muốn sửa ngữ pháp này không ?");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                grammarHandler.upDateGrammar(grammar);
                Toast.makeText(Admin_Edit_Grammar.this, "Sửa ngữ pháp thành công!", Toast.LENGTH_SHORT).show();
                loadAllDataGrammar();
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
        edtMNP_Edit.setText("");
        edtTNP_Edit.setText("");
        spinnerDNP_Edit.setSelection(0);
        edtNoiDungNP_Edit.setText("");
        edtCT_Edit.setText("");
        edtVD_Edit.setText("");
    }
    boolean compareBetweenNewAndOld(Grammar grammar, Grammar grammarOld)
    {
        boolean check = false;
        if (grammar.getTenNguPhap().equals(grammarOld.getTenNguPhap()) && grammar.getNoiDung().equals(grammarOld.getNoiDung())
        && grammar.getCongThuc().equals(grammarOld.getCongThuc()) && grammar.getViDu().equals(grammarOld.getViDu())
        && grammar.getMaDangNguPhap().equals(grammarOld.getMaDangNguPhap()))
            return true;
        return check;
    }
}