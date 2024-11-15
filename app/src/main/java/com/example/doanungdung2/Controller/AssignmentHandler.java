package com.example.doanungdung2.Controller;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.doanungdung2.Model.Assigment;

public class AssignmentHandler extends SQLiteOpenHelper {

    private static final String DB_NAME = "AppHocTiengAnh";
    private static final int DB_VERSION = 1;

    private static final String TABLE_NAME = "BaiLam";
    private static final String maBaiLam = "MaBaiLam";
    private static final String thoiGianBatDau = "ThoiGianBatDau";
    private static final String thoiGianKetThuc = "ThoiGianKetThuc";
    private static final String tongThoiGianLamBai = "TongThoiGianLamBai";
    private static final String soLuongCauDung = "SoLuongCauDung";
    private static final String diem = "Diem";
    private static final String lanLam = "LanLam";
    private static final String maBaiTap = "MaBaiTap";
    private static final String maNguoiDung = "MaNguoiDung";

    private static final String PATH = "/data/data/com.example.doanungdung2/database/AppHocTiengAnh.db";

    public AssignmentHandler(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DB_NAME, factory, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insertAssignment(Assigment assigment) {
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.OPEN_READWRITE);

        String query = "INSERT INTO " + TABLE_NAME + " (maBaiLam, thoiGianBatDau, thoiGianKetThuc, tongThoiGianLamBai, " +
                "soLuongCauDung, diem, lanLam, maBaiTap, maNguoiDung) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        sqLiteDatabase.execSQL(query, new Object[]{
                assigment.getMaBaiLam(),
                assigment.getThoiGianBatDau(),
                assigment.getThoiGianKetThuc(),
                assigment.getTongThoiGianLamBai(),
                String.valueOf(assigment.getSoLuongCauDung()),
                String.valueOf(assigment.getDiem()),
                String.valueOf(assigment.getLanLam()),
                assigment.getMaBaiTap(),
                assigment.getMaNguoiDung()
        });

        sqLiteDatabase.close();
    }
}
