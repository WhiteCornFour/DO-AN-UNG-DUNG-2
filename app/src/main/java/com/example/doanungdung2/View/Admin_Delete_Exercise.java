package com.example.doanungdung2.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.doanungdung2.Controller.AssignmentHandler;
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
    AssignmentHandler assignmentHandler;
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
        assignmentHandler = new AssignmentHandler(Admin_Delete_Exercise.this, DB_NAME, null,
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
        super.onBackPressed();
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
                if (exerciseArrayList.size() != 0)
                {
                    exercise = exerciseArrayList.get(i);
                }else if (dataOfSearch.size() != 0){
                    exercise = dataOfSearch.get(i);
                }
                String maBT = exercise.getMaBaiTap();
                boolean kiemTraKhoaNgoaiTrogBaiLam = assignmentHandler.checkExistedOfExerciseCode(maBT);
                if (!kiemTraKhoaNgoaiTrogBaiLam)
                {
                    createDialog(maBT);
                }else {
                    Toast.makeText(Admin_Delete_Exercise.this,
                            "Không thể xóa mã bài tập: " + maBT +
                                    " \nBởi vì mã này đang tồn tại với vai trò là khóa ngoại của bảng bài làm. \nVui lòng kiểm tra lại các thông tin có liên quan đến bài tập này!",
                            Toast.LENGTH_LONG).show();
                }
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
                }else if (dataOfSearch.size() != 0)
                {
                    for (boolean checked : checkedForSearch) {
                        if (checked) {
                            anyChecked = true;
                            break;
                        }
                    }
                }else
                {
                    Toast.makeText(Admin_Delete_Exercise.this, "Vui lòng 1 chọn phần tử trước khi xóa!",
                            Toast.LENGTH_SHORT).show();
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
                if (exerciseArrayList.size() != 0)
                {
                    exerciseArrayList.remove(exercise);
                }else if (dataOfSearch.size() != 0)
                {
                    dataOfSearch.remove(exercise);
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
        if(exerciseArrayList.size() != 0)
        {
            for (int i = 0; i < checkedSates.length; i++) {
                if (checkedSates[i]) {
                    exerToDelete.add(exerciseArrayList.get(i));
                }
            }
        }else if (dataOfSearch.size() != 0)
        {
            for (int i = 0; i < checkedForSearch.length; i++) {
                if (checkedForSearch[i]) {
                    exerToDelete.add(dataOfSearch.get(i));
                }
            }
        }
        for (Exercise ex : exerToDelete) {
            boolean check = assignmentHandler.checkExistedOfExerciseCode(ex.getMaBaiTap());
            if (!check)
            {
                exerciseHandler.deleteExerciseByCode(ex.getMaBaiTap());
                exerciseArrayList.remove(ex);
                Toast.makeText(this, "Xóa bài tập thành công!", Toast.LENGTH_SHORT).show();
            }else
            {
                Toast.makeText(Admin_Delete_Exercise.this,
                        "Không thể xóa mã bài tập: " + ex.getMaBaiTap() +
                                " \nBởi vì mã này đang tồn tại với vai trò là khóa ngoại của bảng bài làm. \nVui lòng kiểm tra lại các thông tin có liên quan đến bài tập này!",
                        Toast.LENGTH_LONG).show();
            }
        }
        if (exerciseArrayList.size() != 0)
        {
            checkedSates = new boolean[exerciseArrayList.size()];
            loadAllDataExercise();
        }else if (dataOfSearch.size() != 0)
        {
            loadDataForSearch(maDBT);
        }
        adapter_lv.notifyDataSetChanged();
    }
}