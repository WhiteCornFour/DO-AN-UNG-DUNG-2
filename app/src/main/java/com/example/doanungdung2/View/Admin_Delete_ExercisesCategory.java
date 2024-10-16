package com.example.doanungdung2.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.doanungdung2.Controller.ExerciseHandler;
import com.example.doanungdung2.Controller.ExercisesCategoryHandler;
import com.example.doanungdung2.Model.Exercise;
import com.example.doanungdung2.Model.ExercisesCategory;
import com.example.doanungdung2.R;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class    Admin_Delete_ExercisesCategory extends AppCompatActivity {

    private static final String DB_NAME = "AppHocTiengAnh";
    private static final int DB_VERSION = 1;
    EditText edtSearchForDeleteInDangBT;
    Button btnResetForListViewInDangBT, btnDeleteAllForLVInDangBT;
    ListView lvDSDangBTTrongXoa;
    ImageView imgSerchForDeleteInDangBT, imgBackDeleteToMainPage;

    ArrayList<ExercisesCategory> exercisesCategoryArrayList = new ArrayList<>();
    ArrayList<ExercisesCategory> searchCategoryArrayList = new ArrayList<>();
    ExercisesCategoryHandler exercisesCategoryHandler;
    ExerciseHandler exerciseHandler;
    Admin_Delete_ExercisesCategory_CustomAdapter_LV customAdapterLv;

    Exercise exercise;
    ExercisesCategory exercisesCategory;
    boolean[] checkedStates;

    boolean[] checkedForSearch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_xoa_dang_bai_tap);
        addControll();

        exercisesCategoryHandler = new ExercisesCategoryHandler(Admin_Delete_ExercisesCategory.this,
                DB_NAME, null, DB_VERSION);
        exerciseHandler = new ExerciseHandler(Admin_Delete_ExercisesCategory.this,
                DB_NAME, null, DB_VERSION);
        loadAllDataForListView();

        addEvent();
    }

    void addControll()
    {
        edtSearchForDeleteInDangBT = findViewById(R.id.edtSearchForDeleteInDangBT);
        btnDeleteAllForLVInDangBT = findViewById(R.id.btnDeleteAllForLVInDangBT);
        btnResetForListViewInDangBT = findViewById(R.id.btnResetForListViewInDangBT);
        lvDSDangBTTrongXoa = findViewById(R.id.lvDSDangBTTrongXoa);
        imgSerchForDeleteInDangBT = findViewById(R.id.imgSerchForDeleteInDangBT);
        imgBackDeleteToMainPage = findViewById(R.id.imgBackDeleteToMainPage);
    }
    void addEvent()
    {
        imgBackDeleteToMainPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Admin_Delete_ExercisesCategory.this, Admin_MainPage.class));
                finish();
            }
        });
        btnResetForListViewInDangBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadAllDataForListView();
            }
        });
        imgSerchForDeleteInDangBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String wordSearch = edtSearchForDeleteInDangBT.getText().toString();
                loadDataForSearch(wordSearch);
            }
        });
        lvDSDangBTTrongXoa.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                exercisesCategory = exercisesCategoryArrayList.get(i);
                String maDBT = exercisesCategory.getMaDangBaiTap();
                boolean result = exerciseHandler.checkExcerciseCateHaveExcercise(maDBT);
                if (!result)
                {
                    createDialog(maDBT);
                    return true;
                }else
                {
                    dialogThongBao(maDBT);
                    return false;
                }
            }
        });
        btnDeleteAllForLVInDangBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
    void loadAllDataForListView()
    {
        exercisesCategoryArrayList = exercisesCategoryHandler.loadAllDataOfExercisesCategory();
        checkedStates = new boolean[exercisesCategoryArrayList.size()];
        customAdapterLv = new Admin_Delete_ExercisesCategory_CustomAdapter_LV(Admin_Delete_ExercisesCategory.this,
                R.layout.layou_admin_delete_exercises_category_customadper_lv, exercisesCategoryArrayList, checkedStates);
        lvDSDangBTTrongXoa.setAdapter(customAdapterLv);
    }

    void loadDataForSearch(String keyWord)
    {
        searchCategoryArrayList = exercisesCategoryHandler.searchResultExercisesCategory(keyWord);
        if (searchCategoryArrayList.size() == 0)
        {
            Toast.makeText(Admin_Delete_ExercisesCategory.this,
                    "Không có kết quả phù hợp!", Toast.LENGTH_SHORT).show();
            return;
        }
        checkedForSearch = new boolean[searchCategoryArrayList.size()];
        customAdapterLv = new Admin_Delete_ExercisesCategory_CustomAdapter_LV(Admin_Delete_ExercisesCategory.this,
                R.layout.layou_admin_delete_exercises_category_customadper_lv, searchCategoryArrayList, checkedForSearch);
        lvDSDangBTTrongXoa.setAdapter(customAdapterLv);
    }
    void createDialog(String maDangBaiTap) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Xác nhận");
        builder.setMessage("Bạn có chắc muốn xóa dạng bài tập này hay không?");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                exercisesCategoryHandler.deleteAExerciseCategory(maDangBaiTap);
                // Cập nhật danh sách bằng cách xóa phần tử tương ứng
                exercisesCategoryArrayList.remove(exercisesCategory);
                // Thông báo cho adapter biết dữ liệu đã thay đổi
                customAdapterLv.notifyDataSetChanged();
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

    void dialogThongBao(String maDBT)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Xác nhận");
        builder.setMessage("Dạng bài tập hiện tại đang chứa 1 danh sách bài tập. \n" +
                "Vui lòng kiểm tra lại các bài tập liên quan đến dạng bài tập có mã: "+ maDBT);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}