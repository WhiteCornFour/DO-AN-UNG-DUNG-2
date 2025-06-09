package com.example.doanungdung2.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doanungdung2.Controller.ReportHandler;
import com.example.doanungdung2.Model.Assignment;
import com.example.doanungdung2.Model.Exercise;
import com.example.doanungdung2.Model.Report;
import com.example.doanungdung2.Model.User;
import com.example.doanungdung2.R;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;

public class User_Report extends AppCompatActivity {
    private static final String DB_NAME = "AppHocTiengAnh";
    private static final int DB_VERSION = 1;
    ImageView imgBackToProfileFromReport;
    EditText edtReportContent;
    TextView tvImportImageReportStatus;
    Button btnInsertPictureReport, btnSendingReport;
    ReportHandler reportHandler;
    User user = new User();
    Bitmap selectedBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_report);
        addControl();
        user = getIntentUser();
        reportHandler = new ReportHandler(User_Report.this, DB_NAME, null, DB_VERSION);

        addEvent();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStack();
    }

    //thực hiện sau khi thực hiện thêm ảnh report
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            try {
                Bitmap bitmap = android.provider.MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);

                tvImportImageReportStatus.setText("Image has been imported!");
                tvImportImageReportStatus.setTextColor(getResources().getColor(R.color.green));

                this.selectedBitmap = bitmap;
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "Failed to load image!", Toast.LENGTH_SHORT).show();
            }
        }
    }


    void addControl() {
        imgBackToProfileFromReport = findViewById(R.id.imgBackToProfileFromReport);
        edtReportContent = findViewById(R.id.edtReportContent);
        tvImportImageReportStatus = findViewById(R.id.tvImportImageReportStatus);
        btnInsertPictureReport = findViewById(R.id.btnInsertPictureReport);
        btnSendingReport = findViewById(R.id.btnSendingReport);
    }

    void addEvent() {
        imgBackToProfileFromReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(User_Report.this, User_Profile.class);
                intent.putExtra("userBackFromPageToProfile", user);
                startActivity(intent);
                finish();
            }
        });

        btnInsertPictureReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 1);
            }
        });

        btnSendingReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String maBaoCao = Admin_Add_Exercise.createAutoExerciseCode("BC");
                String noiDungBaoCao = edtReportContent.getText().toString().trim();
                String ngayBaoCao = String.valueOf(LocalDateTime.now());
                String trangThaiBaoCao = "Chưa xử lý";
                byte[] anhBaoCao = null;
                if (selectedBitmap != null) {
                    anhBaoCao = getBytesFromBitmap(selectedBitmap);
                }
                String maNguoiDung = user.getMaNguoiDung();

                if(noiDungBaoCao.isEmpty()) {
                    Toast.makeText(User_Report.this, "Please write something before submit!", Toast.LENGTH_SHORT).show();
                    return;
                }

                Report report = new Report();
                report.setMaBaoCao(maBaoCao);
                report.setNoiDungBaoCao(noiDungBaoCao);
                report.setNgayBaoCao(ngayBaoCao);
                report.setTrangThaiBaoCao(trangThaiBaoCao);
                report.setAnhBaoCao(anhBaoCao);
                report.setMaNguoiDung(maNguoiDung);
                showReportConfirmDialog(report);
            }
        });
    }

    public Bitmap getBitmapFromImageView(ImageView imageView) {
        Drawable drawable = imageView.getDrawable();
        Bitmap bitmap = null;

        if (drawable instanceof BitmapDrawable) {
            bitmap = ((BitmapDrawable) drawable).getBitmap();
        } else {
            int width = drawable.getIntrinsicWidth();
            int height = drawable.getIntrinsicHeight();
            bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
        }
        return bitmap;
    }

    public byte[] getBytesFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    private User getIntentUser() {
        Intent intent = getIntent();
        User userIntent = (User) intent.getSerializableExtra("userFromProfileToReport");
        return userIntent;
    }

    private void showReportConfirmDialog(Report report) {
        ConstraintLayout successConstraintLayout = findViewById(R.id.takingReportConstraintLayout);
        View view = LayoutInflater.from(User_Report.this).inflate(R.layout.custom_submit_report_dialog, successConstraintLayout);
        Button sendReport = view.findViewById(R.id.btnSendReport);
        Button comeBackReport = view.findViewById(R.id.btnComebackReport);

        AlertDialog.Builder builder = new AlertDialog.Builder(User_Report.this);
        builder.setView(view);
        final AlertDialog alertDialog = builder.create();

        sendReport.findViewById(R.id.btnSendReport).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                reportHandler.insertReport(report);
                clearInputFields();
                Toast.makeText(User_Report.this, "Thank you for helping us enhance and develop the app!", Toast.LENGTH_SHORT).show();
            }
        });

        comeBackReport.findViewById(R.id.btnComebackReport).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        if (alertDialog.getWindow() != null) {
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        alertDialog.show();
    }

    private void clearInputFields() {
        edtReportContent.setText("");
        tvImportImageReportStatus.setText("You haven't inserted any picture yet.");
        tvImportImageReportStatus.setTextColor(getResources().getColor(R.color.red_pastel));
    }
}