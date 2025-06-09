package com.example.doanungdung2.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.doanungdung2.Controller.QuestionHandler;
import com.example.doanungdung2.Model.Question;
import com.example.doanungdung2.R;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Admin_Delete_Questions extends AppCompatActivity {

    ImageView imgBackDeleteToMainPage_Question, imgSearchForDelete_Question;
    EditText edtSearchForDelete_Question;
    Button btnResetForLV_Question, btnDeleteAllForLV_Question;
    ListView lvDSDelete_Question;
    Question question;

    private static final String DB_NAME = "AppHocTiengAnh";
    private static final int DB_VERSION = 1;

    QuestionHandler questionHandler;
    ArrayList<Question> questionArrayList = new ArrayList<>();
    ArrayList<Question> dataOfSearch = new ArrayList<>();
    Admin_Delete_Questions_CustomAdapter_LV admin_Delete_Questions_CustomAdapter_LV;
    boolean[] checkedStates;
    boolean[] checkedForSearch;
    String maCauHoi = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_delete_questions);
        addControl();
        questionHandler = new QuestionHandler(Admin_Delete_Questions.this, DB_NAME, null, DB_VERSION);

        Intent intent = getIntent();
        maCauHoi = intent.getStringExtra("maCauHoi");
        if (maCauHoi != null) {
            loadDataForSearch(maCauHoi);
        } else {
            loadAllDataQuestion();
        }
        addEvent();
    }

    void addControl() {
        imgBackDeleteToMainPage_Question = (ImageView) findViewById(R.id.imgBackDeleteToMainPage_Question);
        imgSearchForDelete_Question = (ImageView) findViewById(R.id.imgSerchForDelete_Question);
        edtSearchForDelete_Question = (EditText) findViewById(R.id.edtSearchForDelete_Question);
        btnResetForLV_Question = (Button) findViewById(R.id.btnResetForLV_Question);
        btnDeleteAllForLV_Question = (Button) findViewById(R.id.btnDeleteAll_Question);
        lvDSDelete_Question = (ListView) findViewById(R.id.lvDSDelete_Question);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStack();
    }

    void addEvent() {
        imgBackDeleteToMainPage_Question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnResetForLV_Question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (maCauHoi == null) {
                    loadAllDataQuestion();
                    edtSearchForDelete_Question.setText("");
                } else {
                    loadDataForSearch(maCauHoi);
                    edtSearchForDelete_Question.setText("");
                }
            }
        });
        imgSearchForDelete_Question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String maCauHoi = edtSearchForDelete_Question.getText().toString();
                dataOfSearch = questionHandler.searchQuestionByNameOrCode(maCauHoi);
                checkedForSearch = new boolean[dataOfSearch.size()];

                if (dataOfSearch.isEmpty()) {
                    Toast.makeText(Admin_Delete_Questions.this, "Không tìm thấy thông tin cần tìm", Toast.LENGTH_SHORT).show();
                } else {
                    admin_Delete_Questions_CustomAdapter_LV = new Admin_Delete_Questions_CustomAdapter_LV(Admin_Delete_Questions.this,
                            R.layout.activity_admin_delete_questions_custom_adapter_lv, dataOfSearch, checkedForSearch);
                    lvDSDelete_Question.setAdapter(admin_Delete_Questions_CustomAdapter_LV);
                }
            }
        });

        lvDSDelete_Question.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (questionArrayList.size() == 0) {
                    question = dataOfSearch.get(i);
                } else {
                    question = questionArrayList.get(i);
                }
                String maCH = question.getMaCauHoi();
                createDialog(maCH);
                return true;
            }
        });

        btnDeleteAllForLV_Question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean anyChecked = false;
                if (questionArrayList.size() != 0) {
                    for (boolean checked : checkedStates) {
                        if (checked) {
                            anyChecked = true;
                            break;
                        }
                    }
                } else {
                    for (boolean checked : checkedForSearch) {
                        if (checked) {
                            anyChecked = true;
                            break;
                        }
                    }
                }

                if (!anyChecked) {
                    Toast.makeText(Admin_Delete_Questions.this, "Hãy chọn ít nhất một câu hỏi!", Toast.LENGTH_SHORT).show();
                    return;
                }
                AlertDialog alertDialog = createAlertDialogDeleteQuestion();
                alertDialog.show();
            }
        });
    }

    void loadAllDataQuestion() {
        questionArrayList = questionHandler.loadAllDataOfQuestion();
        checkedStates = new boolean[questionArrayList.size()];
        admin_Delete_Questions_CustomAdapter_LV = new Admin_Delete_Questions_CustomAdapter_LV(Admin_Delete_Questions.this,
                R.layout.activity_admin_delete_questions_custom_adapter_lv, questionArrayList, checkedStates);
        lvDSDelete_Question.setAdapter(admin_Delete_Questions_CustomAdapter_LV);
    }

    void loadDataForSearch(String keyWord) {
        dataOfSearch = questionHandler.searchQuestionByNameOrCode(keyWord);
        checkedForSearch = new boolean[dataOfSearch.size()];
        admin_Delete_Questions_CustomAdapter_LV = new Admin_Delete_Questions_CustomAdapter_LV(Admin_Delete_Questions.this,
                R.layout.activity_admin_delete_questions_custom_adapter_lv, dataOfSearch, checkedForSearch);
        lvDSDelete_Question.setAdapter(admin_Delete_Questions_CustomAdapter_LV);
    }


    private void deleteSelectedQuestion() {
        ArrayList<Question> questionsToDelete = new ArrayList<>();

        if (questionArrayList.size() == 0) {
            for (int i = 0; i < checkedForSearch.length; i++) {
                if (checkedForSearch[i]) {
                    questionsToDelete.add(dataOfSearch.get(i));
                }
            }
        } else {
            for (int i = 0; i < checkedStates.length; i++) {
                if (checkedStates[i]) {
                    questionsToDelete.add(questionArrayList.get(i));
                }
            }
        }
        boolean Deleted = false;
        //Check co maBT hay chua
        for (Question question : questionsToDelete) {
            if (question.getMaBaiTap() != null) {
                Toast.makeText(this, "Không thể xóa câu hỏi đã có mã bài tập", Toast.LENGTH_SHORT).show();
            } else {
                questionHandler.deleteQuestion(question.getMaCauHoi());
                Deleted = true;

                if (questionArrayList.size() == 0) {
                    dataOfSearch.remove(question);
                } else {
                    questionArrayList.remove(question);
                }
            }

        }

        if (questionArrayList.size() == 0) {
            loadDataForSearch(maCauHoi);
        } else {
            checkedStates = new boolean[questionArrayList.size()];
            loadAllDataQuestion();
        }
        if (Deleted) {
            Toast.makeText(this, "Xóa câu hỏi thành công!", Toast.LENGTH_SHORT).show();
        }
    }


    void createDialog(String maCauHoi) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Xác nhận");
        builder.setMessage("Bạn có chắc muốn xóa câu hỏi này hay không?");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

                if (question.getMaBaiTap() != null) {
                    Toast.makeText(Admin_Delete_Questions.this, "Không thể xóa câu hỏi đã có mã bài tập", Toast.LENGTH_SHORT).show();
                } else {
                    questionHandler.deleteQuestion(maCauHoi);
                    if (questionArrayList.size() == 0) {
                        dataOfSearch.remove(question);
                    } else {
                        questionArrayList.remove(question);
                    }
                    admin_Delete_Questions_CustomAdapter_LV.notifyDataSetChanged();
                    Toast.makeText(Admin_Delete_Questions.this, "Xóa câu hỏi thành công!", Toast.LENGTH_SHORT).show();
                }
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

    AlertDialog createAlertDialogDeleteQuestion() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Admin_Delete_Questions.this);
        builder.setTitle("Xóa câu hỏi");
        builder.setMessage("Bạn có chắc muốn xóa các câu hỏi đã chọn?");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                deleteSelectedQuestion();
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
