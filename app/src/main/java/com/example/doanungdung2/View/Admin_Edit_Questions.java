package com.example.doanungdung2.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
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

import com.example.doanungdung2.Controller.ExercisesCategoryHandler;
import com.example.doanungdung2.Controller.QuestionHandler;
import com.example.doanungdung2.Model.Exercise;
import com.example.doanungdung2.Model.ExercisesCategory;
import com.example.doanungdung2.Model.Question;
import com.example.doanungdung2.Model.SharedViewModel;
import com.example.doanungdung2.R;

import java.util.ArrayList;
import androidx.lifecycle.ViewModelProvider;


public class Admin_Edit_Questions extends AppCompatActivity {

    private static final String DB_NAME = "AppHocTiengAnh";
    private static final int DB_VERSION = 1;
    SharedViewModel sharedViewModel;
    EditText edtSuaCHSearch, edtSuaMaCauHoi, edtSuaNoiDungCauHoi;
    ImageView imgBackToMainPageSCH,imgSuaCHSearch;
    RecyclerView rvSuaCHSearch;
    Spinner spinnerDangBaiTapCH, spinnerMucDoCH;
    FrameLayout frameLayoutCauHoiSDBT;
    Button btnSuaCH;
    String [] dsMucDo = new String[]{"Beginner", "Starter", "Intermediate", "Proficient", "Master"};
    QuestionHandler questionHandler;
    ExercisesCategoryHandler exercisesCategoryHandler;
    ArrayList<Question> questionArrayListResult = new ArrayList<>();
    ArrayList<ExercisesCategory> dsDangBaiTap = new ArrayList<>();
    Admin_Edit_Questions_CustomAdapter admin_edit_questions_customAdapter;
    ExercisesCategory exercisesCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_edit_questions);
        addControl();

        questionHandler = new QuestionHandler(Admin_Edit_Questions.this, DB_NAME, null, DB_VERSION);
        exercisesCategoryHandler = new ExercisesCategoryHandler(Admin_Edit_Questions.this, DB_NAME, null, DB_VERSION);

        dsDangBaiTap = exercisesCategoryHandler.loadAllDataOfExercisesCategory();
        sharedViewModel = new ViewModelProvider(this).get(SharedViewModel.class);
        spinnerMucDoCHCreate();
        spinnerDangBaiTapCHCreate();
        spinnerDangBaiTapCH.setEnabled(false);

        setUpRecyclerView();
        loadAllQuestions();
        addEvent();
    }

    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStack(); // Quay lại Fragment trước đó
    }


    void addControl() {
        edtSuaCHSearch = findViewById(R.id.edtSuaCHSearch);
        edtSuaMaCauHoi = findViewById(R.id.edtSuaMaCauHoi);
        edtSuaNoiDungCauHoi = findViewById(R.id.edtSuaNoiDungCauHoi);
        imgBackToMainPageSCH = findViewById(R.id.imgBackToMainPageSCH);
        imgSuaCHSearch = findViewById(R.id.imgSuaCHSearch);
        rvSuaCHSearch = findViewById(R.id.rvSuaCHSearch);
        spinnerDangBaiTapCH = findViewById(R.id.spinnerDangBaiTapCH);
        spinnerMucDoCH = findViewById(R.id.spinnerMucDoCH);
        frameLayoutCauHoiSDBT = findViewById(R.id.frameLayoutCauHoiSDBT);
        btnSuaCH = findViewById(R.id.btnSuaCH);
    }

    void addEvent() {
        imgBackToMainPageSCH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        imgSuaCHSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String questionsSearchValue = edtSuaCHSearch.getText().toString().trim();
                if (questionsSearchValue.isEmpty()) {
                    loadAllQuestions();
                } else {
                    edtSuaCHSearch.setText("");
                }
            }
        });
        spinnerDangBaiTapCH.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

        btnSuaCH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String maCauHoi = edtSuaMaCauHoi.getText().toString().trim();

                String mucDo = spinnerMucDoCH.getSelectedItem().toString();

                String noiDungCauHoi = edtSuaNoiDungCauHoi.getText().toString().trim();

                if (maCauHoi.isEmpty() || noiDungCauHoi.isEmpty()) {
                    Toast.makeText(Admin_Edit_Questions.this, "Vui lòng chọn một câu hỏi để sửa hoặc vui lòng không để trống thông tin.", Toast.LENGTH_SHORT).show();
                    return;
                }

                String tenDangBaiTap = spinnerDangBaiTapCH.getSelectedItem().toString();
                String maDangBaiTap = exercisesCategoryHandler.getExerciseCategoryCodeByName(tenDangBaiTap);

                //Lay fragment moi
                FragmentManager fragmentManager = getSupportFragmentManager();
                Fragment fragment = fragmentManager.findFragmentById(R.id.frameLayoutCauHoiSDBT);

                //Tao chuoi lay data tu Fragment day len Activity
                //So sanh Fragmetn hien tai voi cac dang Fragment khac, neu dung thi lam
                ArrayList<String> questionData = new ArrayList<>();
                String questionData2 = "";
                if (fragment instanceof Admin_Question_Multiple_Choice_Fragment) {
                    questionData = ((Admin_Question_Multiple_Choice_Fragment) fragment).getMultipleChoiceData();
                    String cauA = questionData.get(0);
                    String cauB = questionData.get(1);
                    String cauC = questionData.get(2);
                    String cauD = questionData.get(3);
                    String dapAn = questionData.get(4);
                    if (cauA.isEmpty() || cauB.isEmpty() || cauC.isEmpty() || cauD.isEmpty())
                    {
                        Toast.makeText(Admin_Edit_Questions.this, "Vui lòng không để trống thông tin.", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    Question question = new Question();
                    question.setMaCauHoi(maCauHoi);
                    question.setNoiDungCauHoi(noiDungCauHoi);
                    question.setCauA(cauA);
                    question.setCauB(cauB);
                    question.setCauC(cauC);
                    question.setCauD(cauD);
                    question.setDapAn(dapAn);
                    question.setMucDo(mucDo);
                    question.setMaBaiTap(null);
                    question.setMaDangBaiTap(maDangBaiTap);
                    createAlertDialogEditQuestions(question).show();
                } else if (fragment instanceof Admin_Question_True_False_Fragment) {
                    questionData2 = ((Admin_Question_True_False_Fragment) fragment).getTrueFalseData();
                    if (questionData2.isEmpty())
                    {
                        Toast.makeText(Admin_Edit_Questions.this, "Vui lòng không để trống thông tin.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Question question = new Question(maCauHoi, noiDungCauHoi, null, null, null, null, questionData2, mucDo, null, maDangBaiTap);
                    createAlertDialogEditQuestions(question).show();
                } else if (fragment instanceof Admin_Question_Essay_Fragment) {
                    questionData2 = ((Admin_Question_Essay_Fragment) fragment).getEssayData();
                    if (questionData2.isEmpty())
                    {
                        Toast.makeText(Admin_Edit_Questions.this, "Vui lòng không để trống thông tin.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Question question = new Question(maCauHoi, noiDungCauHoi, null, null, null, null, questionData2, mucDo, null, maDangBaiTap);
                    createAlertDialogEditQuestions(question).show();
                }
            }
        });
    }
    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayoutCauHoiSDBT, fragment);
        fragmentTransaction.commit();
    }

    //Chuc nang
    void loadAllQuestions() {
        questionArrayListResult.clear();
        questionArrayListResult = questionHandler.loadAllDataOfQuestion();
        admin_edit_questions_customAdapter.setQuestionsList(questionArrayListResult);
    }

    //Thiết lập nút, spinner, checkbox
    void spinnerMucDoCHCreate() {
        String[] dsCauString = new String[dsMucDo.length];
        for (int i = 0; i < dsMucDo.length; i++) {
            dsCauString[i] = String.valueOf(dsMucDo[i]);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, dsCauString);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMucDoCH.setAdapter(adapter);
    }


    void spinnerDangBaiTapCHCreate() {
        ArrayList<String> dsDangBaiTapCHString = exercisesCategoryHandler.returnNameOfCategoriesSpinner();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, dsDangBaiTapCHString);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDangBaiTapCH.setAdapter(adapter);
    }

    void setUpRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(Admin_Edit_Questions.this, RecyclerView.VERTICAL, false);
        rvSuaCHSearch.addItemDecoration(new DividerItemDecoration(Admin_Edit_Questions.this, DividerItemDecoration.VERTICAL));
        rvSuaCHSearch.setLayoutManager(layoutManager);
        rvSuaCHSearch.setItemAnimator(new DefaultItemAnimator());
        admin_edit_questions_customAdapter = new Admin_Edit_Questions_CustomAdapter(questionArrayListResult, new Admin_Edit_Questions_CustomAdapter.ItemClickListener() {
            @Override
            public void onItemClick(Question question) {
                sharedViewModel.select(question);
                edtSuaMaCauHoi.setText(question.getMaCauHoi());
                edtSuaNoiDungCauHoi.setText(question.getNoiDungCauHoi());

                int index = -1;
                for (int i = 0; i < dsMucDo.length; i++) {
                    if (dsMucDo[i].equals(question.getMucDo())) {
                        Log.d("muc do spinner", String.valueOf(i));
                        index = i;
                        break;
                    }
                }
                if (index != -1) {
                    spinnerMucDoCH.setSelection(index);
                }

                String maDangBaiTap = question.getMaDangBaiTap();
                String tenDangBaiTap = exercisesCategoryHandler.getExerciseCategoryNameByCode(maDangBaiTap);
                ArrayList<String> dsDangBaiTapString = exercisesCategoryHandler.returnNameOfCategoriesSpinner();
                int index2 = dsDangBaiTapString.indexOf(tenDangBaiTap);
                if (index2 != -1) {
                    spinnerDangBaiTapCH.setSelection(index2);
                }

                String tenDBT = exercisesCategoryHandler.getExerciseCategoryNameByCode(maDangBaiTap);
                if (tenDBT.equals("Multiple Choice")) {
                    Admin_Question_Multiple_Choice_Fragment f1 = new Admin_Question_Multiple_Choice_Fragment();
                    replaceFragment(f1);
                } else if (tenDBT.equals("True/False")) {
                    Admin_Question_True_False_Fragment f2 = new Admin_Question_True_False_Fragment();
                    replaceFragment(f2);
                } else {
                    Admin_Question_Essay_Fragment f3 = new Admin_Question_Essay_Fragment();
                    replaceFragment(f3);
                }
            }
        });
        rvSuaCHSearch.setAdapter(admin_edit_questions_customAdapter );
    }

    private void clearInputFields() {
        edtSuaMaCauHoi.setText("");
        edtSuaNoiDungCauHoi.setText("");

        spinnerMucDoCH.setSelection(0);
        spinnerDangBaiTapCH.setSelection(0);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment currentFragment = fragmentManager.findFragmentById(R.id.frameLayoutCauHoiSDBT);
        if (currentFragment != null) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.remove(currentFragment);
            transaction.commit();
        }
    }
    //Gui warning dialog
    private AlertDialog createAlertDialogEditQuestions(Question question) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Admin_Edit_Questions.this);
        builder.setTitle("Edit Questions");
        builder.setMessage("Bạn có muốn cập nhật câu hỏi này không ?");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                questionHandler.updateQuestions(question);
                loadAllQuestions();
                Toast.makeText(Admin_Edit_Questions.this, "Cập nhật thành công.", Toast.LENGTH_SHORT).show();
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