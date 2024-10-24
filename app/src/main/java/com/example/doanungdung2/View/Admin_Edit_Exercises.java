package com.example.doanungdung2.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.example.doanungdung2.Controller.ExerciseHandler;
import com.example.doanungdung2.Controller.ExercisesCategoryHandler;
import com.example.doanungdung2.Model.Exercise;
import com.example.doanungdung2.Model.ExercisesCategory;
import com.example.doanungdung2.R;

import java.util.ArrayList;

public class Admin_Edit_Exercises extends AppCompatActivity {

    private static final String DB_NAME = "AppHocTiengAnh";
    private static final int DB_VERSION = 1;

    EditText edtSuaBTSearch, edtSuaMaBT, edtSuaTenBT, edtSuaThoiGianBT, edtSuaMoTaBT;
    ImageView imgSuaBTSearch, imgBackToMainPageSBT;
    RecyclerView rvSuaBTSearch;
    Spinner spinnerSoCauBT, spinnerDangBaiTapBT;
    CheckBox cbBeginnerSBT, cbStarterSBT, cbIntermediateSBT, cbProficientSBT, cbMasterSBT;
    Button btnCapNhatCauHoiSBT, btnSuaBT;

    ExercisesCategoryHandler exercisesCategoryHandler;
    ExerciseHandler exerciseHandler;
    ArrayList<Exercise> exercisesArrayListSearchResult = new ArrayList<>();
    Admin_Edit_Exercises_CustomAdapter admin_edit_exercises_customAdapter;
    SpinnerAdapter spinnerAdapter;
    int [] dsCau = new int[]{5, 10, 15, 20, 25, 30};
    ArrayList<ExercisesCategory> dsDangBaiTap = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_edit_exercises);
        addControl();
        exerciseHandler = new ExerciseHandler(Admin_Edit_Exercises.this, DB_NAME, null, DB_VERSION);
        exercisesCategoryHandler = new ExercisesCategoryHandler(Admin_Edit_Exercises.this, DB_NAME, null, DB_VERSION);

        dsDangBaiTap = exercisesCategoryHandler.loadAllDataOfExercisesCategory();

        spinnerCauCreate();
        spinnerDBTCreate();
        spinnerDangBaiTapBT.setEnabled(false);

        setupRecyclerView();
        loadAllExercises();
        addEvent();
    }

    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStack(); // Quay lại Fragment trước đó
    }

    void addControl() {
        edtSuaBTSearch = (EditText) findViewById(R.id.edtSuaBTSearch);
        edtSuaMaBT = (EditText) findViewById(R.id.edtSuaMaBT);
        edtSuaTenBT = (EditText) findViewById(R.id.edtSuaTenBT);
        edtSuaThoiGianBT = (EditText) findViewById(R.id.edtSuaThoiGianBT);
        edtSuaMoTaBT = (EditText) findViewById(R.id.edtSuaMoTaBT);
        btnSuaBT = (Button) findViewById(R.id.btnSuaBT);
        btnCapNhatCauHoiSBT = (Button) findViewById(R.id.btnCapNhatCauHoiSBT);
        imgSuaBTSearch = (ImageView) findViewById(R.id.imgSuaBTSearch);
        imgBackToMainPageSBT = (ImageView) findViewById(R.id.imgBackToMainPageSBT);
        rvSuaBTSearch = (RecyclerView) findViewById(R.id.rvSuaBTSearch);
        spinnerSoCauBT = (Spinner) findViewById(R.id.spinnerSoCauBT);
        spinnerDangBaiTapBT = (Spinner) findViewById(R.id.spinnerDangBaiTapBT);
        cbBeginnerSBT = (CheckBox) findViewById(R.id.cbBeginnerSBT);
        cbStarterSBT = (CheckBox) findViewById(R.id.cbStarterSBT);
        cbIntermediateSBT = (CheckBox) findViewById(R.id.cbIntermediateSBT);
        cbProficientSBT= (CheckBox) findViewById(R.id.cbProficientSBT);
        cbMasterSBT = (CheckBox) findViewById(R.id.cbMasterSBT);
    }

    void addEvent() {
        imgBackToMainPageSBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        imgSuaBTSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String exercisesSearchValue = edtSuaBTSearch.getText().toString().trim();
                if (exercisesSearchValue.isEmpty()) {
                    loadAllExercises();
                } else {
                    searchExercises(exercisesSearchValue);
                    edtSuaBTSearch.setText("");
                }
            }
        });

        btnSuaBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String maBT = edtSuaMaBT.getText().toString().trim();
                String tenBT = edtSuaTenBT.getText().toString().trim();

                int selectedCauIndex = spinnerSoCauBT.getSelectedItemPosition();
                int soCau = dsCau[selectedCauIndex];

                String mucDo = getMucDo();

                String thoigianBT = edtSuaThoiGianBT.getText().toString().trim();
                String moTaBT = edtSuaMoTaBT.getText().toString().trim();

                String tendangBaiTap = spinnerDangBaiTapBT.getSelectedItem().toString();
                String maDangBaiTap = exercisesCategoryHandler.getExerciseCategoryCodeByName(tendangBaiTap);

                if (!isNumeric(thoigianBT) || Integer.parseInt(thoigianBT) < 5 || Integer.parseInt(thoigianBT) > 60) {
                    Toast.makeText(Admin_Edit_Exercises.this, "Thời gian phải là số và nằm trong khoảng 5 đến 60 phút.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (maBT.isEmpty() || tenBT.isEmpty() || thoigianBT.isEmpty() ||  moTaBT.isEmpty()) {
                    Toast.makeText(Admin_Edit_Exercises.this, "Vui lòng chọn một danh mục bài tập để sửa hoặc vui lòng không để trống thông tin.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (exerciseHandler.isExerciseNameExists(tenBT)) {
                    Toast.makeText(Admin_Edit_Exercises.this, "Tên bài tập đã tồn tại. Vui lòng chọn tên khác.", Toast.LENGTH_SHORT).show();
                    return;
                }

                Exercise exercise = new Exercise();
                exercise.setMaBaiTap(maBT);
                exercise.setTenBaiTap(tenBT);
                exercise.setSoCau(soCau);
                exercise.setMucDo(mucDo);
                exercise.setThoiGian(thoigianBT);
                exercise.setMoTa(moTaBT);
                exercise.setMaDangBaiTap(maDangBaiTap);
                createAlertDialogEditExercises(exercise).show();
            }
        });

        cbBeginnerSBT.setOnCheckedChangeListener((buttonView, isChecked) -> handleCheckBoxChange(cbBeginnerSBT, isChecked));
        cbStarterSBT.setOnCheckedChangeListener((buttonView, isChecked) -> handleCheckBoxChange(cbStarterSBT, isChecked));
        cbIntermediateSBT.setOnCheckedChangeListener((buttonView, isChecked) -> handleCheckBoxChange(cbIntermediateSBT, isChecked));
        cbProficientSBT.setOnCheckedChangeListener((buttonView, isChecked) -> handleCheckBoxChange(cbProficientSBT, isChecked));
        cbMasterSBT.setOnCheckedChangeListener((buttonView, isChecked) -> handleCheckBoxChange(cbMasterSBT, isChecked));
    }

    //Chức năng
    void loadAllExercises() {
        exercisesArrayListSearchResult.clear();
        exercisesArrayListSearchResult = exerciseHandler.loadAllDataOfExercise();
        admin_edit_exercises_customAdapter.setExercisesList(exercisesArrayListSearchResult);
    }

    private boolean isNumeric(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    void searchExercises(String searchQuery) {
        exercisesArrayListSearchResult.clear();
        exercisesArrayListSearchResult = exerciseHandler.searchExerciseByNameOrCode(searchQuery);
        admin_edit_exercises_customAdapter.setExercisesList(exercisesArrayListSearchResult);
    }

    //Thiết lập nút, spinner, checkbox
    void spinnerCauCreate() {
        String[] dsCauString = new String[dsCau.length];
        for (int i = 0; i < dsCau.length; i++) {
            dsCauString[i] = String.valueOf(dsCau[i]);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, dsCauString);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSoCauBT.setAdapter(adapter);
    }

    void spinnerDBTCreate() {
        ArrayList<String> dsDangBaiTapBTString = exercisesCategoryHandler.returnNameOfCategoriesSpinner();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, dsDangBaiTapBTString);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDangBaiTapBT.setAdapter(adapter);
    }

    void setupRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(Admin_Edit_Exercises.this, RecyclerView.VERTICAL, false);
        rvSuaBTSearch.addItemDecoration(new DividerItemDecoration(Admin_Edit_Exercises.this, DividerItemDecoration.VERTICAL));
        rvSuaBTSearch.setLayoutManager(layoutManager);
        rvSuaBTSearch.setItemAnimator(new DefaultItemAnimator());
        admin_edit_exercises_customAdapter = new Admin_Edit_Exercises_CustomAdapter(exercisesArrayListSearchResult,new Admin_Edit_Exercises_CustomAdapter.ItemClickListener() {
            @Override
            public void onItemClick(Exercise exercise) {
                edtSuaMaBT.setText(exercise.getMaBaiTap());
                edtSuaTenBT.setText(exercise.getTenBaiTap());
                edtSuaThoiGianBT.setText(exercise.getThoiGian());

                int index = -1;
                for (int i = 0; i < dsCau.length; i++) {
                    if (dsCau[i] == exercise.getSoCau()) {
                        index = i;
                        break;
                    }
                }

                if (index != -1) {
                    spinnerSoCauBT.setSelection(index);
                }

                String maDangBaiTap = exercise.getMaDangBaiTap();
                String tenDangBaiTap = exercisesCategoryHandler.getExerciseCategoryNameByCode(maDangBaiTap);
                ArrayList<String> dsDangBaiTapString = exercisesCategoryHandler.returnNameOfCategoriesSpinner();
                int index2 = dsDangBaiTapString.indexOf(tenDangBaiTap);
                if (index2 != -1) {
                    spinnerDangBaiTapBT.setSelection(index2);
                }

                cbBeginnerSBT.setChecked(exercise.getMucDo().equals("Beginner"));
                cbStarterSBT.setChecked(exercise.getMucDo().equals("Starter"));
                cbIntermediateSBT.setChecked(exercise.getMucDo().equals("Intermediate"));
                cbProficientSBT.setChecked(exercise.getMucDo().equals("Proficient"));
                cbMasterSBT.setChecked(exercise.getMucDo().equals("Master"));

                edtSuaMoTaBT.setText(exercise.getMoTa());
            }
        });
        rvSuaBTSearch.setAdapter(admin_edit_exercises_customAdapter);
    }

    String getMucDo() {
        if (cbBeginnerSBT.isChecked()) {
            return "Beginner";
        } else if (cbStarterSBT.isChecked()) {
            return "Starter";
        } else if (cbIntermediateSBT.isChecked()) {
            return "Intermediate";
        } else if (cbProficientSBT.isChecked()) {
            return "Proficient";
        } else if (cbMasterSBT.isChecked()) {
            return "Master";
        }
        return "";
    }

    private void handleCheckBoxChange(CheckBox changedCheckBox, boolean isChecked) {
        if (isChecked) {
            // chọn 1 checkbox r thì còn lại đánh false
            if (changedCheckBox != cbBeginnerSBT) cbBeginnerSBT.setChecked(false);
            if (changedCheckBox != cbStarterSBT) cbStarterSBT.setChecked(false);
            if (changedCheckBox != cbIntermediateSBT) cbIntermediateSBT.setChecked(false);
            if (changedCheckBox != cbProficientSBT) cbProficientSBT.setChecked(false);
            if (changedCheckBox != cbMasterSBT) cbMasterSBT.setChecked(false);
        }
    }
    private void clearInputFields() {
        edtSuaMaBT.setText("");
        edtSuaTenBT.setText("");
        edtSuaThoiGianBT.setText("");
        edtSuaMoTaBT.setText("");

        spinnerSoCauBT.setSelection(0);
        spinnerDangBaiTapBT.setSelection(0);

        cbBeginnerSBT.setChecked(false);
        cbStarterSBT.setChecked(false);
        cbIntermediateSBT.setChecked(false);
        cbProficientSBT.setChecked(false);
        cbMasterSBT.setChecked(false);
    }


    //Alert
//    private void showWarningDialog(CheckBox changedCheckBox, String level) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("Cảnh báo");
//        builder.setMessage("Bạn đang thay đổi mức độ bài tập sang " + level + ". Tất cả câu hỏi trong bài tập sẽ bị xóa.");
//        builder.setPositiveButton("Tiếp tục", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//            }
//        });
//        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                // Nếu người dùng hủy, bỏ chọn CheckBox
//                changedCheckBox.setChecked(false);
//                dialog.cancel();
//            }
//        });
//        builder.show();
//    }

    private AlertDialog createAlertDialogEditExercises(Exercise exercise) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Admin_Edit_Exercises.this);
        builder.setTitle("Edit Exercises");
        builder.setMessage("Bạn có muốn cập nhật bài tập này không ?");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                exerciseHandler.updateExercises(exercise);
                loadAllExercises();
                Toast.makeText(Admin_Edit_Exercises.this, "Cập nhật thành công.", Toast.LENGTH_SHORT).show();
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

}