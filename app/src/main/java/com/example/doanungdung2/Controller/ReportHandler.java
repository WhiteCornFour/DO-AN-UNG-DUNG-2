package com.example.doanungdung2.Controller;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.doanungdung2.Model.Dictionary;
import com.example.doanungdung2.Model.Report;

import java.util.ArrayList;

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
    @SuppressLint("Range")
    public ArrayList<Report> loadAllDataReport()
    {
        ArrayList<Report> reportArrayList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.OPEN_READONLY);
        String query = "Select * from " + TABLE_NAME;
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        if (cursor != null)
        {
            if (cursor.moveToFirst())
            {
                do {
                    Report report = new Report();
                    report.setMaBaoCao(cursor.getString(cursor.getColumnIndex(maBaoCao)));
                    report.setNoiDungBaoCao(cursor.getString(cursor.getColumnIndex(noiDungBaoCao)));
                    report.setNgayBaoCao(cursor.getString(cursor.getColumnIndex(ngayBaoCao)));
                    report.setTrangThaiBaoCao(cursor.getString(cursor.getColumnIndex(trangThaiBaoCao)));
                    report.setAnhBaoCao(cursor.getBlob(cursor.getColumnIndex(anhBaoCao)));
                    report.setMaNguoiDung(cursor.getString(cursor.getColumnIndex(maNguoiDung)));
                    reportArrayList.add(report);
                }while (cursor.moveToNext());
            }
            cursor.close();
        }
        sqLiteDatabase.close();
        return reportArrayList;
    }
    @SuppressLint("Range")
    public ArrayList<Report> loadDataByReportStatus(String status)
    {
        ArrayList<Report> reportArrayList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.OPEN_READONLY);
        String query = "Select * from " + TABLE_NAME + " Where TrangThaiBaoCao = ?";
        Cursor cursor = sqLiteDatabase.rawQuery(query, new String[]{status});
        if (cursor != null)
        {
            if (cursor.moveToFirst())
            {
                do {
                    Report report = new Report();
                    report.setMaBaoCao(cursor.getString(cursor.getColumnIndex(maBaoCao)));
                    report.setNoiDungBaoCao(cursor.getString(cursor.getColumnIndex(noiDungBaoCao)));
                    report.setNgayBaoCao(cursor.getString(cursor.getColumnIndex(ngayBaoCao)));
                    report.setTrangThaiBaoCao(cursor.getString(cursor.getColumnIndex(trangThaiBaoCao)));
                    report.setAnhBaoCao(cursor.getBlob(cursor.getColumnIndex(anhBaoCao)));
                    report.setMaNguoiDung(cursor.getString(cursor.getColumnIndex(maNguoiDung)));
                    reportArrayList.add(report);
                }while (cursor.moveToNext());
            }
            cursor.close();
        }
        sqLiteDatabase.close();
        return reportArrayList;
    }
    @SuppressLint("Range")
    public ArrayList<Report> loadDataSearchByKeyWord(String keyWord, String status)
    {
        ArrayList<Report> reportArrayList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.OPEN_READONLY);
        Cursor cursor = null;
        if (status != null && !status.isEmpty())
        {
            String query = "Select * from " + TABLE_NAME + " Where NgayBaoCao Like '%" + keyWord + "%'" +
                    " AND TrangThaiBaoCao = ?";
            cursor = sqLiteDatabase.rawQuery(query, new String[]{status});
        }else
        {
            String query = "Select * from " + TABLE_NAME + " Where NgayBaoCao Like '%" + keyWord + "%'";
            cursor = sqLiteDatabase.rawQuery(query, null);
        }
        if (cursor != null)
        {
            if (cursor.moveToFirst())
            {
                do {
                    Report report = new Report();
                    report.setMaBaoCao(cursor.getString(cursor.getColumnIndex(maBaoCao)));
                    report.setNoiDungBaoCao(cursor.getString(cursor.getColumnIndex(noiDungBaoCao)));
                    report.setNgayBaoCao(cursor.getString(cursor.getColumnIndex(ngayBaoCao)));
                    report.setTrangThaiBaoCao(cursor.getString(cursor.getColumnIndex(trangThaiBaoCao)));
                    report.setAnhBaoCao(cursor.getBlob(cursor.getColumnIndex(anhBaoCao)));
                    report.setMaNguoiDung(cursor.getString(cursor.getColumnIndex(maNguoiDung)));
                    reportArrayList.add(report);
                }while (cursor.moveToNext());
            }
            cursor.close();
        }
        sqLiteDatabase.close();
        return reportArrayList;
    }
    public void updataStatusForReport(String maBaoCaoInput, String trangThaiBaoCaoInput)
    {
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.OPEN_READWRITE);
        String query = "UPDATE " + TABLE_NAME + " SET TrangThaiBaoCao = ? WHERE MaBaoCao = ?";
        sqLiteDatabase.execSQL(query, new Object[]{trangThaiBaoCaoInput, maBaoCaoInput});
        sqLiteDatabase.close();
    }
}
