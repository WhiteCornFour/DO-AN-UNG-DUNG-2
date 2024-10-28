package com.example.doanungdung2.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doanungdung2.Controller.ExercisesCategoryHandler;
import com.example.doanungdung2.Controller.QuestionHandler;
import com.example.doanungdung2.Model.Dictionary;
import com.example.doanungdung2.Model.Exercise;
import com.example.doanungdung2.Model.Question;
import com.example.doanungdung2.R;

import java.util.ArrayList;

public class Admin_Questions_Exercises extends AppCompatActivity {

    private static final String DB_NAME = "AppHocTiengAnh";
    private static final int DB_VERSION = 1;
    ImageView imgBackToMainPageSCHBT;
    EditText edtSuaMaBaiTapCHBT, edtSuaTenBaiTapCHBT, edtSuaThoiGianCHBT, edtSuaMucDoBaiTapCHBT;
    TextView tvSuaSoCauCountCHBT, tvSuaSoCauCHBT, tvSuaSoCauStatusCHBT;
    RecyclerView rvSuaInCHBT, rvSuaOutCHBT;
    Button btnSuaThemToanBoCHBT, btnSuaXoaToanBoCHBT, btnLuuCHBT;
    Admin_Questions_Exercises_CustomAdapter admin_questions_exercises_In_customAdapter;
    Admin_Questions_Exercises_CustomAdapter admin_questions_exercises_Out_customAdapter;
    QuestionHandler questionHandler;
    ArrayList<Question> questionArrayListIn = new ArrayList<>();
    ArrayList<Question> questionArrayListOut = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_questions_exercises);
        addControl();
        //-------------------------------------------//
        setUpData();
        //-------------------------------------------//

        questionHandler = new QuestionHandler(Admin_Questions_Exercises.this,  DB_NAME, null, DB_VERSION);
        setUpInCHBTRecyclerView();
        loadAllAddingQuestions();
        setUpOutCHBTRecyclerView();
        loadAllNotAddingQuestions();
        setUpCountSoCau();
        addEvent();
    }

    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStack(); // Quay lại Fragment trước đó
    }

    void addControl() {
        imgBackToMainPageSCHBT = findViewById(R.id.imgBackToMainPageSCHBT);
        edtSuaMaBaiTapCHBT = findViewById(R.id.edtSuaMaBaiTapCHBT);
        edtSuaTenBaiTapCHBT = findViewById(R.id.edtSuaTenBaiTapCHBT);
        edtSuaThoiGianCHBT = findViewById(R.id.edtSuaThoiGianCHBT);
        edtSuaMucDoBaiTapCHBT = findViewById(R.id.edtSuaMucDoBaiTapCHBT);
        tvSuaSoCauCountCHBT = findViewById(R.id.tvSuaSoCauCountCHBT);
        tvSuaSoCauCHBT = findViewById(R.id.tvSuaSoCauCHBT);
        tvSuaSoCauStatusCHBT = findViewById(R.id.tvSuaSoCauStatusCHBT);
        rvSuaInCHBT = findViewById(R.id.rvSuaInCHBT);
        rvSuaOutCHBT = findViewById(R.id.rvSuaOutCHBT);
        btnSuaThemToanBoCHBT = findViewById(R.id.btnSuaThemToanBoCHBT);
        btnSuaXoaToanBoCHBT = findViewById(R.id.btnSuaXoaToanBoCHBT);
        btnLuuCHBT = findViewById(R.id.btnLuuCHBT);
    }

    void addEvent() {
        imgBackToMainPageSCHBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAlertDialogBeforeExit().show();
            }
        });

        btnSuaThemToanBoCHBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // lay danh sach cau hoi duoc chon
                ArrayList<Question> selectedQuestions = new ArrayList<>();
                for (Question question : questionArrayListOut) {
                    if (question.isChecked()) {
                        question.setChecked(false);
                        selectedQuestions.add(question);
                    }
                }

                if (selectedQuestions.isEmpty()) {
                    Toast.makeText(Admin_Questions_Exercises.this, "Chưa có câu hỏi nào được chọn !", Toast.LENGTH_SHORT).show();
                    return;
                }

                questionArrayListIn.addAll(selectedQuestions);
                questionArrayListOut.removeAll(selectedQuestions);

                setUpCountSoCau();

                //cap nhat sau khi thuc hien thay doi
                admin_questions_exercises_In_customAdapter.setQuestionsList(questionArrayListIn);
                admin_questions_exercises_Out_customAdapter.setQuestionsList(questionArrayListOut);//
            }
        });

        btnSuaXoaToanBoCHBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // lay danh sach cau hoi duoc chon
                ArrayList<Question> selectedQuestions = new ArrayList<>();
                for (Question question : questionArrayListIn) {
                    if (question.isChecked()) {
                        question.setChecked(false);
                        selectedQuestions.add(question);
                    }
                }

                if (selectedQuestions.isEmpty()) {
                    Toast.makeText(Admin_Questions_Exercises.this, "Chưa có câu hỏi nào được chọn !", Toast.LENGTH_SHORT).show();
                    return;
                }

                questionArrayListOut.addAll(selectedQuestions);
                questionArrayListIn.removeAll(selectedQuestions);

                setUpCountSoCau();

                //cap nhat sau khi thuc hien thay doi
                admin_questions_exercises_In_customAdapter.setQuestionsList(questionArrayListIn);
                admin_questions_exercises_Out_customAdapter.setQuestionsList(questionArrayListOut);
            }
        });

        btnLuuCHBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String maBaiTap = edtSuaMaBaiTapCHBT.getText().toString();
                createAlertDialogSaveQuestionsExercises(maBaiTap).show();
            }
        });

    }

    //set up checkbox, edittext
    void setUpData() {
        Intent intent = getIntent();
        Exercise exercise = (Exercise) intent.getSerializableExtra("exercise");
        edtSuaMaBaiTapCHBT.setText(exercise.getMaBaiTap());
        edtSuaTenBaiTapCHBT.setText(exercise.getTenBaiTap());
        edtSuaThoiGianCHBT.setText(exercise.getThoiGian());
        edtSuaMucDoBaiTapCHBT.setText(exercise.getMucDo());
        tvSuaSoCauCHBT.setText(String.valueOf(exercise.getSoCau()));
    }

    void loadAllAddingQuestions() {
        Intent intent = getIntent();
        Exercise exercise = (Exercise) intent.getSerializableExtra("exercise");
        String mucDo = exercise.getMucDo();
        String maDangBaiTap = exercise.getMaDangBaiTap();
        String maBaiTap = exercise.getMaBaiTap();
        questionArrayListIn.clear();
        questionArrayListIn.addAll(questionHandler.loadAllDataOfAddingQuestion(mucDo, maDangBaiTap, maBaiTap));
        admin_questions_exercises_In_customAdapter.setQuestionsList(questionArrayListIn);
    }

    void loadAllNotAddingQuestions() {
        Intent intent = getIntent();
        Exercise exercise = (Exercise) intent.getSerializableExtra("exercise");
        String mucDo = exercise.getMucDo();
        String maDangBaiTap = exercise.getMaDangBaiTap();
        questionArrayListOut.clear();
        questionArrayListOut.addAll(questionHandler.loadAllDataOfNotAddingQuestion(mucDo, maDangBaiTap));
        admin_questions_exercises_Out_customAdapter.setQuestionsList(questionArrayListOut);
    }

    void setUpInCHBTRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(Admin_Questions_Exercises.this, RecyclerView.VERTICAL, false);
        rvSuaInCHBT.addItemDecoration(new DividerItemDecoration(Admin_Questions_Exercises.this, DividerItemDecoration.VERTICAL));
        rvSuaInCHBT.setLayoutManager(layoutManager);
        rvSuaInCHBT.setItemAnimator(new DefaultItemAnimator());
        admin_questions_exercises_In_customAdapter = new Admin_Questions_Exercises_CustomAdapter(questionArrayListIn, new Admin_Questions_Exercises_CustomAdapter.ItemClickListener() {
            @Override
            public void onItemClick(Question question) {

            }
        });
        rvSuaInCHBT.setAdapter(admin_questions_exercises_In_customAdapter);
    }

    void setUpOutCHBTRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(Admin_Questions_Exercises.this, RecyclerView.VERTICAL, false);
        rvSuaOutCHBT.addItemDecoration(new DividerItemDecoration(Admin_Questions_Exercises.this, DividerItemDecoration.VERTICAL));
        rvSuaOutCHBT.setLayoutManager(layoutManager);
        rvSuaOutCHBT.setItemAnimator(new DefaultItemAnimator());
        admin_questions_exercises_Out_customAdapter = new Admin_Questions_Exercises_CustomAdapter(questionArrayListOut, new Admin_Questions_Exercises_CustomAdapter.ItemClickListener() {
            @Override
            public void onItemClick(Question question) {
            }
        });
        rvSuaOutCHBT.setAdapter(admin_questions_exercises_Out_customAdapter);
    }

    void setUpCountSoCau() {
        //dem so cau
        int currentCount = questionArrayListIn.size();
        tvSuaSoCauCountCHBT.setText(String.valueOf(currentCount));

        //lay so cau hien tai
        int requiredCount = Integer.parseInt(tvSuaSoCauCHBT.getText().toString());

        //kt du so cau hay chua
        if (currentCount == requiredCount) {
            tvSuaSoCauStatusCHBT.setText("Đã đủ số lượng câu hỏi");
            tvSuaSoCauStatusCHBT.setTextColor(getResources().getColor(R.color.green));
            btnSuaThemToanBoCHBT.setEnabled(false);
            btnSuaThemToanBoCHBT.setBackground(getDrawable(R.drawable.backgr_button_enabled));
            btnSuaThemToanBoCHBT.setTextColor(getResources().getColor(R.color.black));
            btnLuuCHBT.setEnabled(true);
            btnLuuCHBT.setBackground(getDrawable(R.drawable.backgr_admin_white));
            btnLuuCHBT.setTextColor(getResources().getColor(R.color.blue));
        } else if (currentCount > requiredCount) {
            tvSuaSoCauStatusCHBT.setText("Số lượng câu hỏi thêm vào bị vượt quá số lượng, hãy xóa bớt để Lưu.");
            tvSuaSoCauStatusCHBT.setTextColor(getResources().getColor(R.color.red));
            btnSuaThemToanBoCHBT.setEnabled(false);
            btnSuaThemToanBoCHBT.setBackground(getDrawable(R.drawable.backgr_button_enabled));
            btnSuaThemToanBoCHBT.setTextColor(getResources().getColor(R.color.black));
            btnLuuCHBT.setEnabled(false);
            btnLuuCHBT.setBackground(getDrawable(R.drawable.backgr_button_enabled));
            btnLuuCHBT.setTextColor(getResources().getColor(R.color.black));
        } else {
            tvSuaSoCauStatusCHBT.setText("Chưa đủ số lượng câu hỏi");
            tvSuaSoCauStatusCHBT.setTextColor(getResources().getColor(R.color.red));
            btnSuaThemToanBoCHBT.setEnabled(true);
            btnSuaThemToanBoCHBT.setBackground(getDrawable(R.drawable.backgr_button_blue));
            btnSuaThemToanBoCHBT.setTextColor(getResources().getColor(R.color.white));
            btnLuuCHBT.setEnabled(true);
            btnLuuCHBT.setBackground(getDrawable(R.drawable.backgr_admin_white));
            btnLuuCHBT.setTextColor(getResources().getColor(R.color.blue));
        }

        //kiem tra nut xoa
        if (currentCount > 0) {
            btnSuaXoaToanBoCHBT.setEnabled(true);
            btnSuaXoaToanBoCHBT.setBackground(getDrawable(R.drawable.backgr_button_blue));
            btnSuaXoaToanBoCHBT.setTextColor(getResources().getColor(R.color.white));
        } else {
            btnSuaXoaToanBoCHBT.setEnabled(false);
            btnSuaXoaToanBoCHBT.setBackground(getDrawable(R.drawable.backgr_button_enabled));;
            btnSuaXoaToanBoCHBT.setTextColor(getResources().getColor(R.color.black));
        }
    }

    //Alert
    private AlertDialog createAlertDialogSaveQuestionsExercises(String maBaiTap) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Cập nhật câu hỏi");
        builder.setMessage("Lưu lại chỉnh sửa hiện tại ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                // luu cau hoi vao danh sach In
                for (Question question : questionArrayListIn) {
                    question.setMaBaiTap(maBaiTap);
                    questionHandler.updateQuestions(question);
                }

                // luu cau hoi vao danh sach out
                for (Question question : questionArrayListOut) {
                    question.setMaBaiTap(null);
                    questionHandler.updateQuestions(question);
                }

                Toast.makeText(Admin_Questions_Exercises.this, "Lưu thay đổi thành công!", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        return builder.create();
    }

    private AlertDialog createAlertDialogBeforeExit() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Trở về chỉnh sửa bài tập");
        builder.setMessage("Bạn có muốn thoát không. Nếu không lưu dữ liệu chỉnh sửa sẽ không được lưu lại. Hãy đảm bảo lưu lại trước khi thoát.");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                finish();
            }
        });
        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        return builder.create();
    }


}