package com.example.doanungdung2.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doanungdung2.Controller.AssignmentHandler;
import com.example.doanungdung2.Controller.ExerciseHandler;
import com.example.doanungdung2.Model.Assignment;
import com.example.doanungdung2.Model.User;
import com.example.doanungdung2.R;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

public class User_Test_Result extends AppCompatActivity {
    private static final String DB_NAME = "AppHocTiengAnh";
    private static final int DB_VERSION = 1;
    ArrayList<Assignment> assigmentArrayList = new ArrayList<>();
    User_Test_Result_CustomAdapter_RecylerView adapter_recylerView;
    AssignmentHandler assignmentHandler;
    ExerciseHandler exerciseHandler;
    RecyclerView recylerview_TestResult;
    ImageView imgBackToUserManager, imgSearchAssignment_User;
    EditText edtKeyWord;
    User user = new User();
    String maND = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_test_result);
        addControl();
        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("userFromProfileToTestListResult");
        assignmentHandler = new AssignmentHandler(User_Test_Result.this, DB_NAME, null, DB_VERSION);
        exerciseHandler = new ExerciseHandler(User_Test_Result.this, DB_NAME, null, DB_VERSION);

        String maNDFromDetail = intent.getStringExtra("maNDFromDetails");
        if (user == null)
        {
            setUpRecyclerView();
            setUpDataRecylerView(maNDFromDetail);
        }else
        {
            maND = user.getMaNguoiDung();
            setUpRecyclerView();
            setUpDataRecylerView(maND);
        }

        addEvent();
    }

//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        fragmentManager.popBackStack();
//    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        Intent intent = getIntent();
//        String maNDDetails = intent.getStringExtra("maNDFromDetails");
//        if (!maNDDetails.isEmpty())
//        {
//            setUpDataRecylerView(maNDDetails);
//        }else
//        {
//            setUpDataRecylerView(maND);
//        }
//    }

    void addControl()
    {
        imgBackToUserManager = findViewById(R.id.imgBackToUserManager);
        imgSearchAssignment_User = findViewById(R.id.imgSearchAssignment_User);
        recylerview_TestResult = findViewById(R.id.recylerview_TestResult);
        edtKeyWord = findViewById(R.id.edtKeyWord);
    }
    void addEvent()
    {
        imgBackToUserManager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        imgSearchAssignment_User.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String keyWord = edtKeyWord.getText().toString();
                if (keyWord == null || keyWord.isEmpty())
                {
                    setUpDataRecylerView(maND);
                }else {
                    String maBaiTap = exerciseHandler.checkAssignmentHaveCode(keyWord);
                    Log.d("Ma Bai Tap Tra Ve Khi Nhap Ten:", maBaiTap);
                    if (!maBaiTap.isEmpty() || maBaiTap != null)
                    {
                        assigmentArrayList = assignmentHandler.searchAssignmentByNameOrCode(maBaiTap, maND);
                        setUpDataRecylerViewBySearchData(assigmentArrayList);
                    }else {
                        assigmentArrayList = assignmentHandler.searchAssignmentByNameOrCode(keyWord, maND);
                        setUpDataRecylerViewBySearchData(assigmentArrayList);
                    }
                }
                if (assigmentArrayList.size() == 0)
                {
                    Toast.makeText(User_Test_Result.this,
                            "No matches were returned!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    void setUpRecyclerView()
    {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(User_Test_Result.this, RecyclerView.VERTICAL, false);
        //recylerview_TestResult.addItemDecoration(new DividerItemDecoration(User_Test_Result.this, DividerItemDecoration.VERTICAL));
        recylerview_TestResult.setLayoutManager(layoutManager);
        recylerview_TestResult.setItemAnimator(new DefaultItemAnimator());
        adapter_recylerView = new User_Test_Result_CustomAdapter_RecylerView(assigmentArrayList, new User_Test_Result_CustomAdapter_RecylerView.ItemClickListener() {
            @Override
            public void itemClicked(Assignment assigment, String tenBT, String maND) {
                Log.d("Ma assignment dang xem chi tiet: ", assigment.getMaBaiTap());
                Intent intent = new Intent(User_Test_Result.this, User_Test_Details.class);
                intent.putExtra("assignmentFromResults", assigment);
                intent.putExtra("tenBTFromResults", tenBT);
                intent.putExtra("maNDFromResults", maND);
                startActivity(intent);
                finish();
            }
        });
        recylerview_TestResult.setAdapter(adapter_recylerView);
    }
    void setUpDataRecylerView(String maND)
    {
        assigmentArrayList = assignmentHandler.loadDataAssignmentByUserCode(maND);
        Collections.reverse(assigmentArrayList);
        adapter_recylerView.setAssigmentArrayList(assigmentArrayList);
    }
    void setUpDataRecylerViewBySearchData(ArrayList<Assignment> newList)
    {
        Collections.reverse(newList);
        adapter_recylerView.setAssigmentArrayList(newList);
    }
}