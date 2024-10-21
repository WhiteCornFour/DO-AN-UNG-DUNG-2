package com.example.doanungdung2.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.doanungdung2.Controller.ExerciseHandler;
import com.example.doanungdung2.Model.Exercise;
import com.example.doanungdung2.Model.ExercisesCategory;
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

public class Admin_Delete_Exercise extends AppCompatActivity {

    private static final String DB_NAME = "AppHocTiengAnh";
    private static final int DB_VERSION = 1;
    ExerciseHandler exerciseHandler;
    ArrayList<Exercise> exerciseArrayList = new ArrayList<>();
    ArrayList<Exercise> dataOfSearch = new ArrayList<>();
    Admin_Delete_Exercise_CustomAdapter_LV adapter_lv;
    ImageView imgBackDeleteExerciseToMainPage, imgSerchForDeleteInBT;
    EditText edtSearchForDeleteInBT;
    Button btnResetForListViewInBT, btnDeleteAllForLVInBT;
    ListView lvDSBTTrongXoa;
    Exercise exercise;
    boolean[] checkedSates;
    boolean[] checkedForSearch;
    String maDBT = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_delete_exercise);
        addControl();
        exerciseHandler = new ExerciseHandler(Admin_Delete_Exercise.this, DB_NAME, null,
                DB_VERSION);
        //Kiểm tra có intent nào được gửi từ activity xóa dạng bài tập hay không
        //Nếu có Mã Dạng BT được gửi thì tìm kiếm theo MaDBT đó, và load đúng dữ liệu
        Intent intent = getIntent();
        maDBT = intent.getStringExtra("maDBT");
        if (maDBT != null)
        {
            loadDataForSearch(maDBT);
        }else
        {
            loadAllDataExercise();
        }
        addEvent();
    }
    void addControl()
    {
        imgBackDeleteExerciseToMainPage = findViewById(R.id.imgBackDeleteExerciseToMainPage);
        imgSerchForDeleteInBT = findViewById(R.id.imgSerchForDeleteInBT);
        edtSearchForDeleteInBT = findViewById(R.id.edtSearchForDeleteInBT);
        btnResetForListViewInBT = findViewById(R.id.btnResetForListViewInBT);
        btnDeleteAllForLVInBT = findViewById(R.id.btnDeleteAllForLVInBT);
        lvDSBTTrongXoa = findViewById(R.id.lvDSBTTrongXoa);
    }

    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStack();
    }

    void addEvent()
    {
        imgBackDeleteExerciseToMainPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnResetForListViewInBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //kiểm tra dữ liệu đang nằm ở Arraylist nào
                if (maDBT == null)
                {
                    loadAllDataExercise();
                    edtSearchForDeleteInBT.setText("");
                }else
                {
                    loadDataForSearch(maDBT);
                    edtSearchForDeleteInBT.setText("");
                }
            }
        });
        imgSerchForDeleteInBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String maBT = edtSearchForDeleteInBT.getText().toString();
                dataOfSearch = exerciseHandler.searchExerciseByNameOrCode(maBT);
                checkedForSearch = new boolean[dataOfSearch.size()];
                adapter_lv = new Admin_Delete_Exercise_CustomAdapter_LV(Admin_Delete_Exercise.this,
                        R.layout.layout_admin_delete_exercise_customadapter_lv, dataOfSearch, checkedForSearch);
                lvDSBTTrongXoa.setAdapter(adapter_lv);
            }
        });
        lvDSBTTrongXoa.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Kiểm ta array có rỗng hay không, nếu rỗng thì hiển thị array cho chức năng search
                if (exerciseArrayList.size() == 0)
                {
                    exercise = dataOfSearch.get(i);
                }else {
                    exercise = exerciseArrayList.get(i);
                }
                String maBT = exercise.getMaBaiTap();
                createDialog(maBT);
                return true;
            }
        });
        btnDeleteAllForLVInBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Kiểm tra xem có ít nhất một checkbox được chọn hay không
                boolean anyChecked = false;
                // Kiểm tra check box của exerciseArrayList được chọn hay của dataOfSearch
                if (exerciseArrayList.size() != 0)
                {
                    for (boolean checked : checkedSates) {
                        if (checked) {
                            anyChecked = true;
                            break;
                        }
                    }
                }else
                {
                    for (boolean checked : checkedForSearch) {
                        if (checked) {
                            anyChecked = true;
                            break;
                        }
                    }
                }

                if (!anyChecked) {
                    Toast.makeText(Admin_Delete_Exercise.this,
                            "Hãy chọn ít nhất một bài tập!", Toast.LENGTH_SHORT).show();
                    return;
                }
                AlertDialog alertDialog = createAlertDialogDeleteExercise();
                alertDialog.show();
            }
        });
    }
    void loadAllDataExercise()
    {
        exerciseArrayList = exerciseHandler.loadAllDataOfExercise();
        checkedSates = new boolean[exerciseArrayList.size()];
        adapter_lv = new Admin_Delete_Exercise_CustomAdapter_LV(Admin_Delete_Exercise.this,
                R.layout.layout_admin_delete_exercise_customadapter_lv, exerciseArrayList, checkedSates);
        lvDSBTTrongXoa.setAdapter(adapter_lv);
    }
    void loadDataForSearch(String keyWord)
    {
        dataOfSearch = exerciseHandler.searchExerciseByNameOrCode(keyWord);
        checkedForSearch = new boolean[dataOfSearch.size()];
        adapter_lv = new Admin_Delete_Exercise_CustomAdapter_LV(Admin_Delete_Exercise.this,
                R.layout.layout_admin_delete_exercise_customadapter_lv, dataOfSearch, checkedForSearch);
        lvDSBTTrongXoa.setAdapter(adapter_lv);
    }
    void createDialog(String maBaiTap) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Xác nhận");
        builder.setMessage("Bạn có chắc muốn xóa bài tập này hay không?");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                exerciseHandler.deleteExerciseByCode(maBaiTap);
                // Cập nhật danh sách bằng cách xóa phần tử tương ứng
                if (exerciseArrayList.size() == 0)
                {
                    dataOfSearch.remove(exercise);
                }else
                {
                    exerciseArrayList.remove(exercise);
                }
                // Thông báo cho adapter biết dữ liệu đã thay đổi
                adapter_lv.notifyDataSetChanged();
                Toast.makeText(Admin_Delete_Exercise.this
                        , "Xóa bài tập thành công!", Toast.LENGTH_SHORT).show();
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
    AlertDialog createAlertDialogDeleteExercise() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Admin_Delete_Exercise.this);
        builder.setTitle("Xóa bài tập");
        builder.setMessage("Bạn có chắc muốn xóa các bài tập đã chọn?");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                deleteSelectedExercise();
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
    private void deleteSelectedExercise() {
        ArrayList<Exercise> exerToDelete = new ArrayList<>();
        if(exerciseArrayList.size() == 0)
        {
            for (int i = 0; i < checkedForSearch.length; i++) {
                if (checkedForSearch[i]) {
                    exerToDelete.add(dataOfSearch.get(i));
                }
            }
        }else
        {
            for (int i = 0; i < checkedSates.length; i++) {
                if (checkedSates[i]) {
                    exerToDelete.add(exerciseArrayList.get(i));
                }
            }
        }
        for (Exercise ex : exerToDelete) {
            exerciseHandler.deleteExerciseByCode(ex.getMaBaiTap());
            exerciseArrayList.remove(ex);
        }
        if (exerciseArrayList.size() == 0)
        {
            loadDataForSearch(maDBT);
        }else
        {
            checkedSates = new boolean[exerciseArrayList.size()];
            loadAllDataExercise();
        }

        Toast.makeText(this, "Xóa bài tập thành công!", Toast.LENGTH_SHORT).show();
    }
}