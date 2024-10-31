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
import com.example.doanungdung2.Model.Exercise;
import com.example.doanungdung2.Model.Grammar;
import com.example.doanungdung2.Model.GrammarCategory;
import com.example.doanungdung2.R;

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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

public class Admin_Add_Grammar extends AppCompatActivity {
    private static final String DB_NAME = "AppHocTiengAnh";
    private static final int DB_VERSION = 1;
    ImageView imgBackToMainPageNP;
    EditText edtMNP_Add, edtTNP_Add, edtNoiDungNP_Add, edtCT_Add, edtVD_Add;
    Spinner spinnerDNP_Add;
    Button btnNP_Add;
    RecyclerView recyclerViewNP_Add;
    Grammar grammar;
    Grammar grammarOld;
    GrammarCategoryHandler grammarCategoryHandler;
    ArrayList<Grammar> grammarArrayList = new ArrayList<>();
    ArrayList<GrammarCategory> grammarCategories = new ArrayList<>();
    ArrayAdapter<String> adapterSpinner;
    Admin_Add_Grammar_CustomAdapter_RecyclerView adapter;
    ArrayList<String> dataSpinner = new ArrayList<>();
    GrammarHandler grammarHandler;

    String maNP = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_grammar);
        addControl();
        grammarHandler = new GrammarHandler(Admin_Add_Grammar.this, DB_NAME, null, DB_VERSION);
        grammarCategoryHandler = new GrammarCategoryHandler(Admin_Add_Grammar.this, DB_NAME, null, DB_VERSION);
        loadDataForSpinner();
        setUpRecyclerView();

        loadAllDataGrammar();
        maNP = Admin_Add_Exercise.createAutoExerciseCode("NP");
        edtMNP_Add.setText(maNP);
        addEvent();
    }

    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStack();
    }

    void addControl()
    {
        imgBackToMainPageNP = findViewById(R.id.imgBackToMainPageNP);
        edtMNP_Add = findViewById(R.id.edtMNP_Add);
        edtTNP_Add = findViewById(R.id.edtTNP_Add);
        edtNoiDungNP_Add = findViewById(R.id.edtNoiDungNP_Add);
        edtCT_Add = findViewById(R.id.edtCT_Add);
        edtVD_Add = findViewById(R.id.edtVD_Add);
        spinnerDNP_Add = findViewById(R.id.spinnerDNP_Add);
        btnNP_Add = findViewById(R.id.btnNP_Add);
        recyclerViewNP_Add = findViewById(R.id.recyclerViewNP_Add);
    }
    void addEvent()
    {
        imgBackToMainPageNP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnNP_Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tenNP = edtTNP_Add.getText().toString();
                String dangNP = grammarCategoryHandler.searchGrammarCodeByName(spinnerDNP_Add.getSelectedItem().toString());
                String noiDung = edtNoiDungNP_Add.getText().toString();
                String congThuc = edtCT_Add.getText().toString();
                String viDu = edtVD_Add.getText().toString();
                grammar = new Grammar(maNP, tenNP, noiDung, congThuc, viDu, dangNP);
                //Kiểm tra thông tin người dùng nhập vào
                if (validInputValues(grammar))
                {
                    //Kiểm tra mã hoặc ten của ngữ pháp mới có tồn tại trước đó hay chưa
                    if (!grammarHandler.checkNameAndGrammarCode(tenNP, maNP))
                    {
                        createAlertDialog().show();
                    }else
                    {
                        Toast.makeText(Admin_Add_Grammar.this,
                                "Mã hoặc tên của ngữ pháp này đã tồn tại!", Toast.LENGTH_SHORT).show();
                        resetActivity();
                        return;
                    }
                }
                else {
                    Toast.makeText(Admin_Add_Grammar.this,
                            "Vui lòng kiểm tra lại thông tin của bạn!", Toast.LENGTH_SHORT).show();
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
        adapterSpinner = new ArrayAdapter<>(Admin_Add_Grammar.this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, dataSpinner);
        spinnerDNP_Add.setAdapter(adapterSpinner);
    }
    void setUpRecyclerView()
    {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(Admin_Add_Grammar.this);
        recyclerViewNP_Add.addItemDecoration(new DividerItemDecoration(Admin_Add_Grammar.this, DividerItemDecoration.VERTICAL));
        recyclerViewNP_Add.setLayoutManager(layoutManager);
        recyclerViewNP_Add.setItemAnimator(new DefaultItemAnimator());

        adapter = new Admin_Add_Grammar_CustomAdapter_RecyclerView(grammarArrayList, new Admin_Add_Grammar_CustomAdapter_RecyclerView.ItemClickListener() {
            @Override
            public void onItemClick(Grammar grammar) {
                edtMNP_Add.setText(grammar.getMaNguPhap());
                edtTNP_Add.setText(grammar.getTenNguPhap());
                edtNoiDungNP_Add.setText((grammar.getNoiDung()));
                edtCT_Add.setText(grammar.getCongThuc());
                edtVD_Add.setText(grammar.getViDu());

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
                    spinnerDNP_Add.setSelection(index);
                }
            }
        });
        recyclerViewNP_Add.setAdapter(adapter);
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
    void resetActivity()
    {
        maNP = Admin_Add_Exercise.createAutoExerciseCode("NP");
        edtMNP_Add.setText(maNP);
        edtTNP_Add.setText("");
        spinnerDNP_Add.setSelection(0);
        edtNoiDungNP_Add.setText("");
        edtCT_Add.setText("");
        edtVD_Add.setText("");
    }
    @NonNull
    private AlertDialog createAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Admin_Add_Grammar.this);
        builder.setTitle("Thêm ngữ pháp");
        builder.setMessage("Bạn có muốn thêm ngữ pháp này không ?");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                grammarHandler.addNewGrammar(grammar);
                Toast.makeText(Admin_Add_Grammar.this, "Thêm ngữ pháp mới thành công!", Toast.LENGTH_SHORT).show();
                resetActivity();
                loadAllDataGrammar();
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