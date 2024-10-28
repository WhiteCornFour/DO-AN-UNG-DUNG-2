package com.example.doanungdung2.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.doanungdung2.R;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

public class Admin_Delete_Grammar extends AppCompatActivity {

    ImageView imgBackDeleteGrammarToMainPage, imgSerchForDeleteInNP;
    EditText edtSearchForDeleteInNP;
    Button btnResetForListViewInNP, btnDeleteAllForLVInNP;
    ListView lvDSNPTrongXoa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_delete_grammar);
        addControl();

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
    }
}