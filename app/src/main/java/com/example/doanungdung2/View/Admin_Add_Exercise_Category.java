package com.example.doanungdung2.View;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.doanungdung2.Controller.ExercisesCategoryHandler;
import com.example.doanungdung2.Model.ExercisesCategory;
import com.example.doanungdung2.R;

import java.util.ArrayList;

public class Admin_Add_Exercise_Category extends AppCompatActivity {

    ImageView imgBackDBT;
    ListView lvDBT;
    EditText edtTenDBT, edtMoTaDBT, edtMaDBT;
    Button btnThemDBT;
    ArrayList<ExercisesCategory> exercisesCategoryArrayList = new ArrayList<>();
    Admin_Custom_Add_DBT_Adapter adminCustomAddDBTAdapter;
    ExercisesCategoryHandler exercisesCategoryHandler;

    private static final String DB_NAME = "AppHocTiengAnh";
    private static final int DB_VERSION = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_exercise_category);

        addControl();
        addEvent();

        exercisesCategoryHandler = new ExercisesCategoryHandler(Admin_Add_Exercise_Category.this, DB_NAME, null, DB_VERSION);
        exercisesCategoryArrayList = exercisesCategoryHandler.loadAllDataOfExercisesCategory();
        adminCustomAddDBTAdapter = new Admin_Custom_Add_DBT_Adapter(this, R.layout.custom_admin_lv_dbt, exercisesCategoryArrayList);
        lvDBT.setAdapter(adminCustomAddDBTAdapter);

        String maDBT = generateMaDangBaiTap();
        edtMaDBT.setText(maDBT);

    }

    void addControl() {
        lvDBT = (ListView) findViewById(R.id.lvDBT);
        edtMaDBT = (EditText) findViewById(R.id.edtMaDBT);
        imgBackDBT = (ImageView)findViewById (R.id.imgBackDBT);
        edtTenDBT = (EditText) findViewById(R.id.edtTenDBT);
        edtMoTaDBT = (EditText) findViewById(R.id.edtMoTaDBT);
        btnThemDBT = (Button) findViewById(R.id.btnThemDBT);
    }

    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStack();
    }
    void addEvent()
    {
        imgBackDBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnThemDBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addExerciseCategory();
            }
        });
        lvDBT.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ExercisesCategory ec = exercisesCategoryArrayList.get(position);
                edtTenDBT.setText(ec.getTenDangBaiTap());
                edtMoTaDBT.setText(ec.getMoTa());
                edtMaDBT.setText(ec.getMaDangBaiTap());
            }
        });
    }
    private void addExerciseCategory() {
        String tenDBT = edtTenDBT.getText().toString().trim();
        String moTa = edtMoTaDBT.getText().toString().trim();
        String maDBT = edtMaDBT.getText().toString().trim();

        if (tenDBT.isEmpty() || moTa.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (exercisesCategoryHandler.searchResultExercisesCategory(tenDBT).size() > 0) {
            Toast.makeText(this, "Tên danh mục đã tồn tại!", Toast.LENGTH_SHORT).show();
            return;
        }


        ExercisesCategory ec = new ExercisesCategory();
        ec.setTenDangBaiTap(tenDBT);
        ec.setMoTa(moTa);
        ec.setMaDangBaiTap(maDBT);

        boolean isAdded = exercisesCategoryHandler.addExerciseCategory(ec);
        if (isAdded) {
            Toast.makeText(this, "Thêm thành công!", Toast.LENGTH_SHORT).show();
            exercisesCategoryArrayList.add(ec);
            adminCustomAddDBTAdapter.notifyDataSetChanged();
            ///
            edtTenDBT.setText("");
            edtMoTaDBT.setText("");
            edtMaDBT.setText(generateMaDangBaiTap());
        } else {
            Toast.makeText(this, "Thêm thất bại!", Toast.LENGTH_SHORT).show();
        }
    }
    private String generateMaDangBaiTap() {
        int randomNum = (int)(Math.random() * (999 - 10 + 1)) + 10;
        return "DBT" + String.valueOf(randomNum);
    }
}