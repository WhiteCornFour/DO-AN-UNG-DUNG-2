package com.example.doanungdung2.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doanungdung2.Controller.ExerciseHandler;
import com.example.doanungdung2.Controller.ExercisesCategoryHandler;
import com.example.doanungdung2.Model.Exercise;
import com.example.doanungdung2.Model.ExercisesCategory;
import com.example.doanungdung2.R;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class Admin_Add_Exercise extends AppCompatActivity {

    private static final String DB_NAME = "AppHocTiengAnh";
    private static final int DB_VERSION = 1;
    ImageView imgBackFromAddExerciseToMainPage;
    EditText edtMaBT_AddExercise, edtTenBT_AddExercise, edtThoiGianLamBai_AddExercise, edtMoTaBT_AddExercise;
    Spinner spinnerSoCau_AddExercise, spinnerDangBT_AddExercise;
    CheckBox cbBeginner_AddExercise, cbStarter_AddExercise, cbIntermediate_AddExercise,
            cbProficient_AddExercise, cbMaster_AddExercise;
    Button btnXacNhan_AddExercise;
    RecyclerView rycAdAddExercise;
    Admin_Add_Exercise_CustomAdapter_RecylView admin_add_exercise_customAdapter_recylView;
    ExerciseHandler exerciseHandler;
    Exercise exercise;
    ExercisesCategoryHandler exercisesCategoryHandler;
    ArrayList<Exercise> exerciseArrayList = new ArrayList<>();
    ArrayAdapter<String> adapterSpinner;
    ArrayAdapter<String> adapterSpinnerTenDBT;
    ArrayList<String> stringArrayList = new ArrayList<>();
    String[] dsSoCau = new String[]{"5", "10", "15", "20", "25", "30"};
    String maBaiTap = "";
    String mucDo = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_exercise);
        addControl();
        exerciseHandler = new ExerciseHandler(Admin_Add_Exercise.this, DB_NAME, null, DB_VERSION);
        exercisesCategoryHandler = new ExercisesCategoryHandler(Admin_Add_Exercise.this, DB_NAME, null,
                DB_VERSION);
        //Cấu hình cho recycler view
        setUpRecyclerView();

        setUpAdapterSpinnerSoCau();

        setUpAdapterSpinnerTenDBT();

        //load data cho recycler view
        loadAllDataExercise();

        maBaiTap = createAutoExerciseCode("BT");
        edtMaBT_AddExercise.setText(maBaiTap);

        addEvent();
    }

    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStack();
    }
    void addControl()
    {
        imgBackFromAddExerciseToMainPage = findViewById(R.id.imgBackFromAddExerciseToMainPage);
        edtMaBT_AddExercise = findViewById(R.id.edtMaBT_AddExercise);
        edtTenBT_AddExercise = findViewById(R.id.edtTenBT_AddExercise);
        edtThoiGianLamBai_AddExercise = findViewById(R.id.edtThoiGianLamBai_AddExercise);
        edtMoTaBT_AddExercise = findViewById(R.id.edtMoTaBT_AddExercise);
        spinnerSoCau_AddExercise = findViewById(R.id.spinnerSoCau_AddExercise);
        spinnerDangBT_AddExercise = findViewById(R.id.spinnerDangBT_AddExercise);
        cbBeginner_AddExercise = findViewById(R.id.cbBeginner_AddExercise);
        cbStarter_AddExercise = findViewById(R.id.cbStarter_AddExercise);
        cbIntermediate_AddExercise = findViewById(R.id.cbIntermediate_AddExercise);
        cbProficient_AddExercise = findViewById(R.id.cbProficient_AddExercise);
        cbMaster_AddExercise = findViewById(R.id.cbMaster_AddExercise);
        btnXacNhan_AddExercise = findViewById(R.id.btnXacNhan_AddExercise);
        rycAdAddExercise = findViewById(R.id.rycAdAddExercise);
    }
    void addEvent()
    {
        imgBackFromAddExerciseToMainPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnXacNhan_AddExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tenBaiTap = edtTenBT_AddExercise.getText().toString();
                int soCau = Integer.parseInt(spinnerSoCau_AddExercise.getSelectedItem().toString());
                String thoiGian = edtThoiGianLamBai_AddExercise.getText().toString();
                String moTa = edtMoTaBT_AddExercise.getText().toString();
                String tenDangBT = spinnerDangBT_AddExercise.getSelectedItem().toString();
                String maDangBT = exercisesCategoryHandler.getExerciseCategoryCodeByName(tenDangBT);
                exercise = new Exercise(maBaiTap, tenBaiTap, soCau, mucDo, thoiGian, moTa, maDangBT);
                //Kiểm tra trước khi insert
                if(checkEnterExerciseData(exercise))
                {
                    //Kiểm tra tên hoặc mã bài tập có bị trùng hay không
                    if(!exerciseHandler.checkCodeAndNameExercise(maBaiTap, tenBaiTap))
                    {
                        createAlertDialogAddExercises(exercise).show();
                        return;
                    }else {
                        Toast.makeText(Admin_Add_Exercise.this, "Tên hoặc mã bài tập đã tồn tại!",
                                Toast.LENGTH_SHORT).show();
                        resetActivity();
                        return;
                    }
                }else {
                    Toast.makeText(Admin_Add_Exercise.this, "Đảm bảo rằng bạn đã nhập đầy đủ thông tin",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });
        cbBeginner_AddExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cbBeginner_AddExercise.isChecked())
                {
                    cbBeginner_AddExercise.setEnabled(true);
                    cbBeginner_AddExercise.setChecked(true);
                    cbStarter_AddExercise.setEnabled(false);
                    cbIntermediate_AddExercise.setEnabled(false);
                    cbProficient_AddExercise.setEnabled(false);
                    cbMaster_AddExercise.setEnabled(false);
                    mucDo = "Beginner";
                }else {
                    cbBeginner_AddExercise.setChecked(false);
                    cbStarter_AddExercise.setEnabled(true);
                    cbIntermediate_AddExercise.setEnabled(true);
                    cbProficient_AddExercise.setEnabled(true);
                    cbMaster_AddExercise.setEnabled(true);
                    mucDo = "";
                }
            }
        });
        cbStarter_AddExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cbStarter_AddExercise.isChecked())
                {
                    cbStarter_AddExercise.setEnabled(true);
                    cbStarter_AddExercise.setChecked(true);
                    cbBeginner_AddExercise.setEnabled(false);
                    cbIntermediate_AddExercise.setEnabled(false);
                    cbProficient_AddExercise.setEnabled(false);
                    cbMaster_AddExercise.setEnabled(false);
                    mucDo = "Starter";
                }else {
                    cbStarter_AddExercise.setChecked(false);
                    cbBeginner_AddExercise.setEnabled(true);
                    cbIntermediate_AddExercise.setEnabled(true);
                    cbProficient_AddExercise.setEnabled(true);
                    cbMaster_AddExercise.setEnabled(true);
                    mucDo = "";
                }
            }
        });
        cbIntermediate_AddExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cbIntermediate_AddExercise.isChecked())
                {
                    cbIntermediate_AddExercise.setEnabled(true);
                    cbIntermediate_AddExercise.setChecked(true);
                    cbBeginner_AddExercise.setEnabled(false);
                    cbStarter_AddExercise.setEnabled(false);
                    cbProficient_AddExercise.setEnabled(false);
                    cbMaster_AddExercise.setEnabled(false);
                    mucDo = "Intermediate";
                }else {
                    cbIntermediate_AddExercise.setChecked(false);
                    cbBeginner_AddExercise.setEnabled(true);
                    cbStarter_AddExercise.setEnabled(true);
                    cbProficient_AddExercise.setEnabled(true);
                    cbMaster_AddExercise.setEnabled(true);
                    mucDo = "";
                }
            }
        });
        cbProficient_AddExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cbProficient_AddExercise.isChecked())
                {
                    cbProficient_AddExercise.setEnabled(true);
                    cbProficient_AddExercise.setChecked(true);
                    cbBeginner_AddExercise.setEnabled(false);
                    cbStarter_AddExercise.setEnabled(false);
                    cbIntermediate_AddExercise.setEnabled(false);
                    cbMaster_AddExercise.setEnabled(false);
                    mucDo = "Proficient";
                }else {
                    cbProficient_AddExercise.setChecked(false);
                    cbBeginner_AddExercise.setEnabled(true);
                    cbStarter_AddExercise.setEnabled(true);
                    cbIntermediate_AddExercise.setEnabled(true);
                    cbMaster_AddExercise.setEnabled(true);
                    mucDo = "";
                }
            }
        });
        cbMaster_AddExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cbMaster_AddExercise.isChecked())
                {
                    cbMaster_AddExercise.setEnabled(true);
                    cbMaster_AddExercise.setChecked(true);
                    cbBeginner_AddExercise.setEnabled(false);
                    cbStarter_AddExercise.setEnabled(false);
                    cbIntermediate_AddExercise.setEnabled(false);
                    cbProficient_AddExercise.setEnabled(false);
                    mucDo = "Master";
                }else {
                    cbMaster_AddExercise.setChecked(false);
                    cbBeginner_AddExercise.setEnabled(true);
                    cbStarter_AddExercise.setEnabled(true);
                    cbIntermediate_AddExercise.setEnabled(true);
                    cbProficient_AddExercise.setEnabled(true);
                    mucDo = "";
                }
            }
        });
    }
    void loadAllDataExercise()
    {
        exerciseArrayList = exerciseHandler.loadAllDataOfExercise();
        admin_add_exercise_customAdapter_recylView.setExerciseList(exerciseArrayList);
    }
    void setUpAdapterSpinnerSoCau()
    {
        adapterSpinner = new ArrayAdapter<>(Admin_Add_Exercise.this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, dsSoCau);
        spinnerSoCau_AddExercise.setAdapter(adapterSpinner);
    }
    void setUpAdapterSpinnerTenDBT()
    {
        stringArrayList = convertObjectToString(exercisesCategoryHandler.loadAllDataOfExercisesCategory());
        adapterSpinnerTenDBT = new ArrayAdapter<>(Admin_Add_Exercise.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                stringArrayList);
        spinnerDangBT_AddExercise.setAdapter(adapterSpinnerTenDBT);
    }
    void setUpRecyclerView()
    {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(Admin_Add_Exercise.this);
        rycAdAddExercise.addItemDecoration(new DividerItemDecoration(Admin_Add_Exercise.this, DividerItemDecoration.VERTICAL));
        rycAdAddExercise.setLayoutManager(layoutManager);
        rycAdAddExercise.setItemAnimator(new DefaultItemAnimator());

        admin_add_exercise_customAdapter_recylView = new Admin_Add_Exercise_CustomAdapter_RecylView(exerciseArrayList, new Admin_Add_Exercise_CustomAdapter_RecylView.ItemClickListener() {
            @Override
            public void onItemClick(Exercise exercise) {
                edtMaBT_AddExercise.setText(exercise.getMaBaiTap());
                edtTenBT_AddExercise.setText(exercise.getTenBaiTap());
                edtMoTaBT_AddExercise.setText(exercise.getMoTa());
                edtThoiGianLamBai_AddExercise.setText(exercise.getThoiGian());

                cbBeginner_AddExercise.setChecked(exercise.getMucDo().equals("Beginner"));
                cbStarter_AddExercise.setChecked(exercise.getMucDo().equals("Starter"));
                cbIntermediate_AddExercise.setChecked(exercise.getMucDo().equals("Intermediate"));
                cbProficient_AddExercise.setChecked(exercise.getMucDo().equals("Proficient"));
                cbMaster_AddExercise.setChecked(exercise.getMucDo().equals("Master"));

                //Kiểm tra vị trí phần tử được chọn trong recycler để lấy số câu hiển thị lên spinner
                int index = -1;
                for (int i = 0; i < dsSoCau.length; i++)
                {
                    if (Objects.equals(dsSoCau[i], String.valueOf(exercise.getSoCau())))
                    {
                        index = i;
                        break;
                    }
                }
                if (index != -1)
                {
                    spinnerSoCau_AddExercise.setSelection(index);
                }

                //Kiểm tra vị trí phần tử được chọn trong recycler để lấy dạng bài tập hiển thị lên spinner
                int viTri = -1;
                for (int i = 0; i < stringArrayList.size(); i++)
                {
                    //Log.d("Kiem tra ham", exercisesCategoryHandler.searchCodeExerciseCategoryByName(stringArrayList.get(i)));
                    if (Objects.equals(exercisesCategoryHandler.searchCodeExerciseCategoryByName(stringArrayList.get(i)), exercise.getMaDangBaiTap()))
                    {
                        viTri = i;
//                        Log.d("Vi tri", String.valueOf(viTri));
//                        Log.d("Ten Dang Bai Tap", stringArrayList.get(viTri));
                    }
                }
                if (viTri != -1)
                {
                    spinnerDangBT_AddExercise.setSelection(viTri);
                }
            }
        });
        rycAdAddExercise.setAdapter(admin_add_exercise_customAdapter_recylView);
    }

    ArrayList<String> convertObjectToString(ArrayList<ExercisesCategory> exercisesCategoryArrayList)
    {
        ArrayList<String> stringArrayList1 = new ArrayList<>();
        for (ExercisesCategory exercate: exercisesCategoryArrayList
             ) {
            String tenDangBaiTap = exercate.getTenDangBaiTap();
            stringArrayList1.add(tenDangBaiTap);
        }
        return stringArrayList1;
    }
    public static String createAutoExerciseCode(String kyTuDau)
    {
        Random random = new Random();
        // Tạo chuỗi số ngẫu nhiên 9 chữ số
        StringBuilder code = new StringBuilder(kyTuDau);
        for (int i = 0; i < 9; i++) {
            int digit = random.nextInt(10); // Tạo số ngẫu nhiên từ 0 đến 9
            code.append(digit);
        }
        return code.toString();
    }
    public boolean checkEnterExerciseData(Exercise exercise) {
        if (exercise.getMaBaiTap() == null || exercise.getMaBaiTap().trim().isEmpty()) {
            System.out.println("Mã bài tập không được để trống.");
            return false;
        }
        if (exercise.getTenBaiTap() == null || exercise.getTenBaiTap().trim().isEmpty()) {
            System.out.println("Tên bài tập không được để trống.");
            return false;
        }
        if (exercise.getTenBaiTap().length() >50) {
            System.out.println("Tên bài tập không được dài hơn 100 ký tự.");
            return false;
        }
        if (exercise.getMucDo() == null) {
            System.out.println("Bạn hãy chọn một mức độ cho bài tập");
            return false;
        }
        if (exercise.getThoiGian().isEmpty() || exercise.getThoiGian() == null) {
            System.out.println("Vui lòng nhập thời gian làm bài");
            return false;
        }
        if (exercise.getMoTa() != null && exercise.getMoTa().isEmpty()) {
            System.out.println("Vui lòng nhập mô tả cho bài tập này");
            return false;
        }
        // Nếu tất cả đều hợp lệ
        return true;
    }
    void resetActivity()
    {
        edtMaBT_AddExercise.setText(createAutoExerciseCode("BT"));
        edtTenBT_AddExercise.setText("");
        edtMoTaBT_AddExercise.setText("");
        edtThoiGianLamBai_AddExercise.setText("");
        spinnerDangBT_AddExercise.setSelection(0);
        spinnerSoCau_AddExercise.setSelection(0);
        cbBeginner_AddExercise.setChecked(true);
        cbStarter_AddExercise.setChecked(false);
        cbMaster_AddExercise.setChecked(false);
        cbProficient_AddExercise.setChecked(false);
        cbIntermediate_AddExercise.setChecked(false);
    }

    //Alert
    private AlertDialog createAlertDialogAddExercises(Exercise exercise) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Admin_Add_Exercise.this);
        builder.setTitle("Add Exercises");
        builder.setMessage("Bạn có muốn thêm bài tập này không ? Nếu có sẽ chuyển bạn đến trang để thêm câu hỏi vào bài tập này ngay.");
        builder.setPositiveButton("Có và Chuyển Tiếp", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                exerciseHandler.insertNewExercise(exercise);
                Toast.makeText(Admin_Add_Exercise.this, "Thêm bài tập thành công!",
                        Toast.LENGTH_SHORT).show();
                Log.d("DEBUG", "Mức độ: " + exercise.getMucDo());
                Log.d("DEBUG", "Dạng bài tập: " + exercise.getMaDangBaiTap());
                loadAllDataExercise();
                resetActivity();
                Intent intent = new Intent(Admin_Add_Exercise.this, Admin_Questions_Exercises.class);
                intent.putExtra("exercise", exercise);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        return builder.create();
    }
}