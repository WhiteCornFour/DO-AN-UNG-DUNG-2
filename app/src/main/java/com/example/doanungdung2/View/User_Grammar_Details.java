package com.example.doanungdung2.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.doanungdung2.Controller.GrammarHandler;
import com.example.doanungdung2.Model.Grammar;
import com.example.doanungdung2.R;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class User_Grammar_Details extends AppCompatActivity {

    private static final String DB_NAME = "AppHocTiengAnh";
    private static final int DB_VERSION = 1;
    Grammar grammar;
    ArrayList<Grammar> grammarArrayList = new ArrayList<>();
    GrammarHandler grammarHandler;
    ImageView backToGrammarMainPage_User;
    TextView tvTenNP_User, tvCTGrammar_User, tvNDGrammar_User, tvVDGrammar_User;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_grammar_details);
        addControl();
        grammarHandler = new GrammarHandler(this, DB_NAME, null, DB_VERSION);
        Intent intent = getIntent();
        String maNP = intent.getStringExtra("maNP");
        if (maNP == null || maNP.isEmpty())
        {
            Toast.makeText(this, "Thông tin ngữ pháp chưa được cập nhật!", Toast.LENGTH_SHORT).show();
        }else {
            Log.d("maNP", maNP);
            grammarArrayList = grammarHandler.searchByCodeOrNameGrammar(maNP);
            grammar = grammarArrayList.get(0);
            tvTenNP_User.setText(grammar.getTenNguPhap());
            tvCTGrammar_User.setText(grammar.getCongThuc());
            tvNDGrammar_User.setText(grammar.getNoiDung());
            tvVDGrammar_User.setText(grammar.getViDu());
        }
        addEvent();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStack();
    }

    void addControl()
    {
        backToGrammarMainPage_User = findViewById(R.id.backToGrammarMainPage_User);
        tvTenNP_User = findViewById(R.id.tvTenNP_User);
        tvCTGrammar_User = findViewById(R.id.tvCTGrammar_User);
        tvNDGrammar_User = findViewById(R.id.tvNDGrammar_User);
        tvVDGrammar_User = findViewById(R.id.tvVDGrammar_User);
    }
    void addEvent()
    {
        backToGrammarMainPage_User.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}