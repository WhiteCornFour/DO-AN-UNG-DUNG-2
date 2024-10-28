package com.example.doanungdung2.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.doanungdung2.Controller.ExercisesCategoryHandler;
import com.example.doanungdung2.Model.Exercise;
import com.example.doanungdung2.Model.ExercisesCategory;
import com.example.doanungdung2.R;

import java.util.ArrayList;

public class Admin_Edit_ExercisesCategory extends AppCompatActivity {

    private static final String DB_NAME = "AppHocTiengAnh";
    private static final int DB_VERSION = 1;
    EditText edtSuaDBTSearch, edtSuaMaDBT, edtSuaTenDBT,edtSuaMoTaDBT;
    Button btnSuaDBT;
    RecyclerView rvSuaDBTSearch;
    ImageView imgSuaDBTSearch, imgBackToMainPageSDBT;
    ExercisesCategoryHandler exercisesCategoryHandler;
    ArrayList<ExercisesCategory> exercisesCategoryArrayListSearchResult = new ArrayList<>();
    Admin_Edit_ExercisesCategory_CustomAdapter edit_exercisesCategory_customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_edit_exercises_category);
        addControl();

        exercisesCategoryHandler = new ExercisesCategoryHandler(Admin_Edit_ExercisesCategory.this, DB_NAME, null, DB_VERSION);

        setupRecyclerView();
        loadAllExercisesCategories();
        addEvent();
    }

    void addControl() {
        edtSuaDBTSearch = (EditText) findViewById(R.id.edtSuaDBTSearch);
        edtSuaMaDBT = (EditText) findViewById(R.id.edtSuaMaDBT);
        edtSuaTenDBT = (EditText) findViewById(R.id.edtSuaTenDBT);
        edtSuaMoTaDBT = (EditText) findViewById(R.id.edtSuaMoTaDBT);
        btnSuaDBT = (Button) findViewById(R.id.btnSuaDBT);
        rvSuaDBTSearch = (RecyclerView) findViewById(R.id.rvSuaDBTSearch);
        imgSuaDBTSearch = (ImageView) findViewById(R.id.imgSuaDBTSearch);
        imgBackToMainPageSDBT = (ImageView) findViewById(R.id.imgBackToMainPageSDBT);
    }
    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStack(); // Quay lại Fragment trước đó
    }
    void addEvent() {
        imgBackToMainPageSDBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(Admin_Edit_ExercisesCategory.this, Admin_MainPage.class));
                finish();
            }
        });
        imgSuaDBTSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String exercisesCategorySearch = edtSuaDBTSearch.getText().toString().trim();
                if (exercisesCategorySearch.isEmpty()) {
                    edtSuaDBTSearch.setText("");
                    loadAllExercisesCategories();
                } else {
                    searchExercisesCategories(exercisesCategorySearch);
                }
            }
        });

        btnSuaDBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String maDBT = edtSuaMaDBT.getText().toString().trim();
                String tenDBT = edtSuaTenDBT.getText().toString().trim();
                String moTaDBT = edtSuaMoTaDBT.getText().toString().trim();

                if (maDBT.isEmpty() || tenDBT.isEmpty() || moTaDBT.isEmpty()) {
                    Toast.makeText(Admin_Edit_ExercisesCategory.this, "Vui lòng chọn một danh mục bài tập để sửa hoặc vui lòng không để trống thông tin.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (exercisesCategoryHandler.checkExercisesCategoryByNameAndCode(tenDBT, maDBT)) {
                    Toast.makeText(Admin_Edit_ExercisesCategory.this, "Tên dạng bài tập đã tồn tại. Vui lòng chọn tên khác.", Toast.LENGTH_SHORT).show();
                    return;
                }

                ExercisesCategory exercisesCategory = new ExercisesCategory();
                exercisesCategory.setMaDangBaiTap(maDBT);
                exercisesCategory.setTenDangBaiTap(tenDBT);
                exercisesCategory.setMoTa(moTaDBT);
                createAlertDialogEditExercisesCategory(exercisesCategory).show();
            }
        });

    }

    void loadAllExercisesCategories() {
        exercisesCategoryArrayListSearchResult.clear();
        exercisesCategoryArrayListSearchResult = exercisesCategoryHandler.loadAllDataOfExercisesCategory();
        edit_exercisesCategory_customAdapter.setExercisesCategoryList(exercisesCategoryArrayListSearchResult);
    }


    void searchExercisesCategories(String searchQuery) {
        exercisesCategoryArrayListSearchResult.clear();
        exercisesCategoryArrayListSearchResult = exercisesCategoryHandler.searchResultExercisesCategory(searchQuery);
        edit_exercisesCategory_customAdapter.setExercisesCategoryList(exercisesCategoryArrayListSearchResult);
    }

    void setupRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(Admin_Edit_ExercisesCategory.this, RecyclerView.VERTICAL, false);
        rvSuaDBTSearch.addItemDecoration(new DividerItemDecoration(Admin_Edit_ExercisesCategory.this, DividerItemDecoration.VERTICAL));
        rvSuaDBTSearch.setLayoutManager(layoutManager);
        rvSuaDBTSearch.setItemAnimator(new DefaultItemAnimator());
        edit_exercisesCategory_customAdapter = new Admin_Edit_ExercisesCategory_CustomAdapter(exercisesCategoryArrayListSearchResult, new Admin_Edit_ExercisesCategory_CustomAdapter.ItemClickListener() {
            @Override
            public void onItemClick(ExercisesCategory exercisesCategory) {
                // Update EditText with clicked item's data
                edtSuaMaDBT.setText(exercisesCategory.getMaDangBaiTap());
                edtSuaTenDBT.setText(exercisesCategory.getTenDangBaiTap());
                edtSuaMoTaDBT.setText(exercisesCategory.getMoTa());
            }
        });
        rvSuaDBTSearch.setAdapter(edit_exercisesCategory_customAdapter);
    }

    private AlertDialog createAlertDialogEditExercisesCategory(ExercisesCategory exercisesCategory) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Admin_Edit_ExercisesCategory.this);
        builder.setTitle("Edit Exercises Category");
        builder.setMessage("Bạn có muốn cập nhật dạng bài tập này không ?");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                exercisesCategoryHandler.updateExercisesCategory(exercisesCategory);
                loadAllExercisesCategories();
                Toast.makeText(Admin_Edit_ExercisesCategory.this, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
                clearInputFields();
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

    private void clearInputFields() {
        edtSuaMaDBT.setText("");
        edtSuaTenDBT.setText("");
        edtSuaMoTaDBT.setText("");
    }


}