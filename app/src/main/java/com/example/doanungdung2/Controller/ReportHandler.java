package com.example.doanungdung2.Controller;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.doanungdung2.Model.Dictionary;
import com.example.doanungdung2.Model.Report;

public class ReportHandler extends SQLiteOpenHelper {

    private static final String DB_NAME = "AppHocTiengAnh";
    private static final int DB_VERSION = 1;

    private static final String TABLE_NAME = "BaoCao";
    private static final String maBaoCao = "MaBaoCao";
    private static final String noiDungBaoCao = "NoiDungBaoCao";
    private static final String ngayBaoCao = "NgayBaoCao";
    private static final String trangThaiBaoCao = "TrangThaiBaoCao";
    private static final String anhBaoCao = "AnhBaoCao";
    private static final String maNguoiDung = "MaNguoiDung";

    private static final String PATH = "/data/data/com.example.doanungdung2/database/AppHocTiengAnh.db";

    public ReportHandler(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DB_NAME, factory, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insertReport(Report report)
    {
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        String query = "INSERT INTO " + TABLE_NAME + " (maBaoCao, noiDungBaoCao, ngayBaoCao, trangThaiBaoCao ,anhBaoCao, maNguoiDung) VALUES (?, ?, ?, ?, ?, ?)";
        sqLiteDatabase.execSQL(query, new Object[]{report.getMaBaoCao(), report.getNoiDungBaoCao(), report.getNgayBaoCao(),
                report.getTrangThaiBaoCao(), report.getAnhBaoCao(), report.getMaNguoiDung()});
        sqLiteDatabase.close();
    }

}
