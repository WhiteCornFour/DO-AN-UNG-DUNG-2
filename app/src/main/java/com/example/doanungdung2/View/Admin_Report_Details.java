package com.example.doanungdung2.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.animation.ObjectAnimator;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doanungdung2.Controller.ReportHandler;
import com.example.doanungdung2.Controller.UserHandler;
import com.example.doanungdung2.Model.Dictionary;
import com.example.doanungdung2.Model.Report;
import com.example.doanungdung2.R;

public class Admin_Report_Details extends AppCompatActivity {
    private static final String DB_NAME = "AppHocTiengAnh";
    private static final int DB_VERSION = 1;
    ImageView imgBackToMainPageAdmin, imgAnhMinhChung_Details,
            imgRotate_Details, imgZoomIn_Details, imgZoomOut_Details;
    TextView tvMaBaoCao_Details, tvTenNguoiDung_Details, tvNgayBaoCao_Details, tvNoiDungBaoCao_Details;
    Spinner spinnerTrangThai_Details;
    Button btnXacNhan_Details;
    Report report;
    UserHandler userHandler;
    ReportHandler reportHandler;
    ArrayAdapter<String> adapter_Spinner;
    String [] dataSpiner = new String[]{"Chưa xử lý", "Đã xử lý"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_report_details);
        addControl();
        userHandler = new UserHandler(Admin_Report_Details.this, DB_NAME, null, DB_VERSION);
        reportHandler = new ReportHandler(Admin_Report_Details.this, DB_NAME, null, DB_VERSION);
        Intent intent = getIntent();
        report = (Report) intent.getSerializableExtra("ReportInfor");
        //Log.d("Ma report tu intent: ", report.getMaBaoCao());
        setUpTextView(report);
        addEvent();
    }

    private void setUpTextView(Report report) {
        tvMaBaoCao_Details.setText(report.getMaBaoCao());
        tvNgayBaoCao_Details.setText(report.getNgayBaoCao());
        tvNoiDungBaoCao_Details.setText(report.getNoiDungBaoCao());
        tvTenNguoiDung_Details.setText(userHandler.getNameOfUser(report.getMaNguoiDung()));
        setUpDataSpinner(dataSpiner);
        String trangThai = report.getTrangThaiBaoCao();

        if (trangThai.equals("Đã xử lý")) {
            spinnerTrangThai_Details.setSelection(1);
        } else if (trangThai.equals("Chưa xử lý")) {
            spinnerTrangThai_Details.setSelection(0);
        }

        byte[] anhMinhChung = report.getAnhBaoCao();
        if (anhMinhChung == null || anhMinhChung.length == 0) {
            imgAnhMinhChung_Details.setImageResource(R.drawable.no_img);
        } else {
            Bitmap bitmap = BitmapFactory.decodeByteArray(anhMinhChung, 0, anhMinhChung.length);
            imgAnhMinhChung_Details.setImageBitmap(bitmap);
        }
    }

    private void addControl() {
        imgBackToMainPageAdmin = findViewById(R.id.imgBackToMainPageAdmin);
        imgAnhMinhChung_Details = findViewById(R.id.imgAnhMinhChung_Details);
        imgRotate_Details = findViewById(R.id.imgRotate_Details);
        imgZoomIn_Details = findViewById(R.id.imgZoomIn_Details);
        imgZoomOut_Details = findViewById(R.id.imgZoomOut_Details);
        tvMaBaoCao_Details = findViewById(R.id.tvMaBaoCao_Details);
        tvTenNguoiDung_Details = findViewById(R.id.tvTenNguoiDung_Details);
        tvNgayBaoCao_Details = findViewById(R.id.tvNgayBaoCao_Details);
        tvNoiDungBaoCao_Details = findViewById(R.id.tvNoiDungBaoCao_Details);
        spinnerTrangThai_Details = findViewById(R.id.spinnerTrangThai_Details);
        btnXacNhan_Details = findViewById(R.id.btnXacNhan_Details);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStack();
    }

    private void addEvent() {
        imgBackToMainPageAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // Tạo biến lưu góc hiện tại
        final float[] currentRotation = {0};
        imgRotate_Details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Cộng thêm 90 độ vào góc hiện tại
                currentRotation[0] += 90;

                // Tạo ObjectAnimator để xoay ImageView
                ObjectAnimator rotateAnimator = ObjectAnimator.ofFloat(imgAnhMinhChung_Details, "rotation", currentRotation[0] - 90, currentRotation[0]);
                rotateAnimator.setDuration(300); // Thời gian thực hiện xoay
                rotateAnimator.start();
            }
        });
        // Khai báo biến lưu tỷ lệ zoom hiện tại
        final float[] currentScaleX = {1f}; // Tỷ lệ chiều rộng ban đầu
        final float[] currentScaleY = {1f}; // Tỷ lệ chiều cao ban đầu

        imgZoomIn_Details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Cộng thêm 0.1 vào tỷ lệ zoom mỗi lần nhấn
                currentScaleX[0] += 0.1f;
                currentScaleY[0] += 0.1f;

                // Tạo ObjectAnimator để zoom in (phóng to)
                ObjectAnimator scaleX = ObjectAnimator.ofFloat(imgAnhMinhChung_Details,
                        "scaleX", imgAnhMinhChung_Details.getScaleX(), currentScaleX[0]);
                ObjectAnimator scaleY = ObjectAnimator.ofFloat(imgAnhMinhChung_Details,
                        "scaleY", imgAnhMinhChung_Details.getScaleY(), currentScaleY[0]);

                // Cài đặt thời gian thực hiện animation
                scaleX.setDuration(300);
                scaleY.setDuration(300);

                // Chạy các animation đồng thời
                scaleX.start();
                scaleY.start();
            }
        });

        imgZoomOut_Details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Kiểm tra nếu tỷ lệ zoom đã đạt kích thước ban đầu (1.0), không cho zoom out nữa
                if (currentScaleX[0] > 1.0f && currentScaleY[0] > 1.0f) {
                    // Trừ thêm 0.1 vào tỷ lệ zoom mỗi lần nhấn
                    currentScaleX[0] -= 0.1f;
                    currentScaleY[0] -= 0.1f;

                    // Tạo ObjectAnimator để zoom out (thu nhỏ)
                    ObjectAnimator scaleX = ObjectAnimator.ofFloat(imgAnhMinhChung_Details,
                            "scaleX", imgAnhMinhChung_Details.getScaleX(), currentScaleX[0]);
                    ObjectAnimator scaleY = ObjectAnimator.ofFloat(imgAnhMinhChung_Details,
                            "scaleY", imgAnhMinhChung_Details.getScaleY(), currentScaleY[0]);

                    // Cài đặt thời gian thực hiện animation
                    scaleX.setDuration(300);
                    scaleY.setDuration(300);

                    // Chạy các animation đồng thời
                    scaleX.start();
                    scaleY.start();
                } else {
                    // Nếu tỷ lệ zoom đã bằng 1, không thực hiện zoom out
                    Log.d("ZoomOut", "Không thể thu nhỏ nữa, đã đạt kích thước ban đầu.");
                }
            }
        });
        btnXacNhan_Details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String maBaoCao = report.getMaBaoCao();
                String trangThaiBaoCao = spinnerTrangThai_Details.getSelectedItem().toString();
                createAlertDialog(trangThaiBaoCao, maBaoCao).show();
            }
        });
    }
    void setUpDataSpinner(String[] dataSpiner)
    {
        adapter_Spinner = new ArrayAdapter<>(Admin_Report_Details.this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                dataSpiner);
        spinnerTrangThai_Details.setAdapter(adapter_Spinner);
    }
    private AlertDialog createAlertDialog(String trangThaiBaoCao, String maBaoCao) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Admin_Report_Details.this);
        builder.setTitle("Cập nhật trạng thái của báo cáo");
        builder.setMessage("Bạn đã khắc phục lỗi của người dùng đã báo cáo và muốn cập nhật lại trạng thái của báo cáo này?");
        builder.setPositiveButton("Đúng", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (!maBaoCao.isEmpty() && !trangThaiBaoCao.isEmpty())
                {
                    reportHandler.updataStatusForReport(maBaoCao, trangThaiBaoCao);
                    Toast.makeText(Admin_Report_Details.this, "Đã cập nhật trạng thái của báo cáo thành công!",
                            Toast.LENGTH_SHORT).show();
                }
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