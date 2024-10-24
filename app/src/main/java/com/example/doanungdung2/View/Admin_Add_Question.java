package com.example.doanungdung2.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.doanungdung2.Controller.ExercisesCategoryHandler;
import com.example.doanungdung2.Controller.QuestionHandler;
import com.example.doanungdung2.Model.ExercisesCategory;
import com.example.doanungdung2.Model.Question;
import com.example.doanungdung2.R;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class Admin_Add_Question extends AppCompatActivity {
    private static final String DB_NAME = "AppHocTiengAnh";
    private static final int DB_VERSION = 1;
    ImageView imgBackToMainPageTCH;
    EditText edtThemMaCauHoi, edtThemNoiDungCauHoi;
    Spinner spinnerDangBaiTapThemCH, spinnerMucDoThemCH;
    Button btnThemCH;
    FrameLayout frameLayoutCauHoiTDBT;
    String [] dsMucDo = new String[]{"Beginner", "Starter", "Intermediate", "Proficient", "Master"};
    QuestionHandler questionHandler;
    ExercisesCategoryHandler exercisesCategoryHandler;
    ArrayList<Question> questionArrayListResult = new ArrayList<>();
    ArrayList<ExercisesCategory> dsDangBaiTap = new ArrayList<>();
    ExercisesCategory exercisesCategory;
    String maCauHoi = "";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_question);
        addControl();

        questionHandler = new QuestionHandler(Admin_Add_Question.this, DB_NAME, null, DB_VERSION);
        exercisesCategoryHandler = new ExercisesCategoryHandler(Admin_Add_Question.this, DB_NAME, null, DB_VERSION);

        dsDangBaiTap = exercisesCategoryHandler.loadAllDataOfExercisesCategory();

        maCauHoi = Admin_Add_Exercise.createAutoExerciseCode("CH");
        edtThemMaCauHoi.setText(maCauHoi);
        spinnerMucDoCHCreate();

        spinnerDangBaiTapCHCreate();

        addEvent();
    }
    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStack();
    }
    void addControl()
    {
        imgBackToMainPageTCH = findViewById(R.id.imgBackToMainPageTCH);
        edtThemMaCauHoi = findViewById(R.id.edtThemMaCauHoi);
        edtThemNoiDungCauHoi = findViewById(R.id.edtThemNoiDungCauHoi);
        spinnerDangBaiTapThemCH = findViewById(R.id.spinnerDangBaiTapThemCH);
        spinnerMucDoThemCH = findViewById(R.id.spinnerMucDoThemCH);
        btnThemCH = findViewById(R.id.btnThemCH);
        frameLayoutCauHoiTDBT = findViewById(R.id.frameLayoutCauHoiTDBT);
    }
    void addEvent()
    {
        imgBackToMainPageTCH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        spinnerDangBaiTapThemCH.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                exercisesCategory = dsDangBaiTap.get(i);
                String tenDBT = exercisesCategory.getTenDangBaiTap();
                String tracNghiem = "Multiple Choice";
                String dungSai = "True/False";
                if (tenDBT.equals(tracNghiem))
                {
                    Admin_Question_Multiple_Choice_Fragment f1 = new Admin_Question_Multiple_Choice_Fragment();
                    replaceFragment(f1);
                }else if (tenDBT.equals(dungSai))
                {
                    Admin_Question_True_False_Fragment f2 = new Admin_Question_True_False_Fragment();
                    replaceFragment(f2);
                }else
                {
                    Admin_Question_Essay_Fragment f3 = new Admin_Question_Essay_Fragment();
                    replaceFragment(f3);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        btnThemCH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mucDo = spinnerMucDoThemCH.getSelectedItem().toString();
                String noiDungCH = edtThemNoiDungCauHoi.getText().toString();
                String tenDangBaiTap = spinnerDangBaiTapThemCH.getSelectedItem().toString();
                String maDangBaiTap = exercisesCategoryHandler.getExerciseCategoryCodeByName(tenDangBaiTap);
                boolean checkCode = questionHandler.checkQuestionCode(maCauHoi, noiDungCH);
                if (maCauHoi.isEmpty() || noiDungCH.isEmpty())
                {
                    Toast.makeText(Admin_Add_Question.this, "Vui lòng nhập đầy đủ thông tin!",
                            Toast.LENGTH_SHORT).show();
                    return;
                }else
                {
                    if (checkCode)
                    {
                        Toast.makeText(Admin_Add_Question.this,
                                "Mã câu hỏi hoặc nội dung câu hỏi này đã tồn tại!", Toast.LENGTH_SHORT).show();
                        maCauHoi = Admin_Add_Exercise.createAutoExerciseCode("CH");
                        edtThemMaCauHoi.setText(maCauHoi);
                        edtThemNoiDungCauHoi.setText("");
                        return;
                    }else
                    {
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        Fragment fragment = fragmentManager.findFragmentById(R.id.frameLayoutCauHoiTDBT);
                        ArrayList<String> data = new ArrayList<>();
                        String dapAnDung = "";
                        if (fragment instanceof Admin_Question_Multiple_Choice_Fragment)
                        {
                            data = ((Admin_Question_Multiple_Choice_Fragment) fragment).getMultipleChoiceData();
                            if(data == null)
                            {
                                Log.d("Không nhận được data trả về từ fragment!", null);

                            }else {
                                String cauA = data.get(0);
                                String cauB = data.get(1);
                                String cauC = data.get(2);
                                String cauD = data.get(3);
                                String dapAn = data.get(4);
                                Question question = new Question(maCauHoi, noiDungCH, cauA, cauB, cauC,
                                        cauD, dapAn, mucDo, null, maDangBaiTap);
                                createAlertDialogEditQuestions(question).show();
                            }
                        }else if (fragment instanceof Admin_Question_Essay_Fragment)
                        {
                            dapAnDung = ((Admin_Question_Essay_Fragment) fragment).getEssayData();
                            if (dapAnDung.isEmpty())
                            {
                                Log.d("Không nhận được data trả về từ fragment!", null);
                            }else {
                                Question question = new Question(maCauHoi, noiDungCH, null, null, null,
                                        null, dapAnDung, mucDo, null, maDangBaiTap);
                                createAlertDialogEditQuestions(question).show();
                            }
                        }else if (fragment instanceof Admin_Question_True_False_Fragment)
                        {
                            dapAnDung = ((Admin_Question_True_False_Fragment) fragment).getTrueFalseData();
                            if (dapAnDung.isEmpty())
                            {
                                Log.d("Không nhận được data trả về từ fragment!", null);
                            }else {
                                Question question = new Question(maCauHoi, noiDungCH, null, null, null,
                                        null, dapAnDung, mucDo, null, maDangBaiTap);
                                createAlertDialogEditQuestions(question).show();
                            }
                        }
                    }
                }
            }
        });
    }
    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayoutCauHoiTDBT, fragment);
        fragmentTransaction.commit();
    }
    void spinnerMucDoCHCreate() {
        String[] dsCauString = new String[dsMucDo.length];
        for (int i = 0; i < dsMucDo.length; i++) {
            dsCauString[i] = String.valueOf(dsMucDo[i]);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, dsCauString);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMucDoThemCH.setAdapter(adapter);
    }
    void spinnerDangBaiTapCHCreate() {
        ArrayList<String> dsDangBaiTapCHString = exercisesCategoryHandler.returnNameOfCategoriesSpinner();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, dsDangBaiTapCHString);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDangBaiTapThemCH.setAdapter(adapter);
    }
    void resetActivity()
    {
        maCauHoi = Admin_Add_Exercise.createAutoExerciseCode("CH");
        edtThemMaCauHoi.setText(maCauHoi);
        edtThemNoiDungCauHoi.setText("");
        spinnerDangBaiTapThemCH.setSelection(0);
        spinnerMucDoThemCH.setSelection(0);
        Admin_Question_Multiple_Choice_Fragment fm = new Admin_Question_Multiple_Choice_Fragment();
        replaceFragment(fm);
    }
    private AlertDialog createAlertDialogEditQuestions(Question question) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Admin_Add_Question.this);
        builder.setTitle("Thêm câu hỏi");
        builder.setMessage("Bạn có muốn thêm câu hỏi này không ?");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                questionHandler.addNewQuestion(question);
                Toast.makeText(Admin_Add_Question.this, "Thêm câu hỏi mới thành công!"
                        , Toast.LENGTH_SHORT).show();
                resetActivity();
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