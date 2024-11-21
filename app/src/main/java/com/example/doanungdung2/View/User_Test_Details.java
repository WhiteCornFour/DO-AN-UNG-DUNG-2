package com.example.doanungdung2.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doanungdung2.Controller.AssignmentDetailHandler;
import com.example.doanungdung2.Model.Assignment;
import com.example.doanungdung2.Model.AssignmentDetail;
import com.example.doanungdung2.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class User_Test_Details extends AppCompatActivity {
    private static final String DB_NAME = "AppHocTiengAnh";
    private static final int DB_VERSION = 1;
    User_Test_Details_CustomAdapter_RecylerView adapter_recylerView;
    ImageView imgBackToUserManager_TestDT;
    TextView tvTenBT_TestList_TestDT, tvLL_TestList_TestDT, tvNgayLam_TestList_TestDT,
            tvTongThoiGian_TestList_TestDT, tvDiem_TestList_TestDT;
    RecyclerView recylerViewTestDetails;
    AssignmentDetailHandler assignmentDetailHandler;
    ArrayList<AssignmentDetail> assigmentDetailArrayList = new ArrayList<>();
    String maND = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_test_details);
        addControl();
        assignmentDetailHandler = new AssignmentDetailHandler(User_Test_Details.this, DB_NAME, null, DB_VERSION);
        Intent intent = getIntent();
        Assignment assigment = (Assignment) intent.getSerializableExtra("assignmentFromResults");
        String tenBT = intent.getStringExtra("tenBTFromResults");
        maND = intent.getStringExtra("maNDFromResults");
        setUpDataToShowTextView(assigment, tenBT, maND);
        setUpRecyclerView(assigment);
        addEvent();
    }

    private void setUpDataToShowTextView(Assignment assigment, String tenBT, String maND) {
        tvTenBT_TestList_TestDT.setText(tenBT);
        tvLL_TestList_TestDT.setText("Do assignment the " + String.valueOf(assigment.getLanLam()) + "th time");
        tvNgayLam_TestList_TestDT.setText(assigment.getThoiGianBatDau());
        tvTongThoiGian_TestList_TestDT.setText(assigment.getTongThoiGianLamBai());
        tvDiem_TestList_TestDT.setText(String.valueOf(assigment.getDiem()) + " points");
    }

    private void addEvent() {
        imgBackToUserManager_TestDT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (maND != null && !maND.isEmpty())
                {
                    Intent intent = new Intent(User_Test_Details.this, User_Test_Result.class);
                    intent.putExtra("maNDFromDetails", maND);
                    startActivity(intent);
                }
                finish();
            }
        });
    }

    private void addControl() {
        imgBackToUserManager_TestDT = findViewById(R.id.imgBackToUserManager_TestDT);
        tvTenBT_TestList_TestDT = findViewById(R.id.tvTenBT_TestList_TestDT);
        tvLL_TestList_TestDT = findViewById(R.id.tvLL_TestList_TestDT);
        tvNgayLam_TestList_TestDT = findViewById(R.id.tvNgayLam_TestList_TestDT);
        tvTongThoiGian_TestList_TestDT = findViewById(R.id.tvTongThoiGian_TestList_TestDT);
        tvDiem_TestList_TestDT = findViewById(R.id.tvDiem_TestList_TestDT);
        recylerViewTestDetails = findViewById(R.id.recylerViewTestDetails);
    }
    void setUpRecyclerView(Assignment assigment)
    {
        assigmentDetailArrayList = assignmentDetailHandler.loadAssignmentDetailsForTestDetail(assigment.getMaBaiLam());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(User_Test_Details.this, RecyclerView.VERTICAL, false);
        recylerViewTestDetails.setLayoutManager(layoutManager);
        recylerViewTestDetails.setItemAnimator(new DefaultItemAnimator());
        adapter_recylerView = new User_Test_Details_CustomAdapter_RecylerView(assigmentDetailArrayList);
        recylerViewTestDetails.setAdapter(adapter_recylerView);
    }
}