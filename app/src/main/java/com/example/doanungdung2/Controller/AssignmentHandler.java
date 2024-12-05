package com.example.doanungdung2.Controller;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.doanungdung2.Model.Assignment;

import java.util.ArrayList;

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

    public void insertAssignment(Assignment assigment) {
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

    public int countTimeDoTest(String maBaiTap, String maNguoiDung) {
        int max = 0;
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.OPEN_READONLY);
        String query = "SELECT MAX(lanLam) FROM " + TABLE_NAME + " WHERE maBaiTap = ? AND maNguoiDung = ?";
        Cursor cursor = sqLiteDatabase.rawQuery(query, new String[]{maBaiTap, maNguoiDung});
        if (cursor != null) {
            if (cursor.moveToFirst()) { // Kiểm tra có kết quả hay không
                max = cursor.getInt(0) + 1; // Lấy giá trị MAX(lanLam)
                if (cursor.isNull(0)) { // Trường hợp MAX(lanLam) là NULL
                    max = 1;
                }
            }
        }
        cursor.close();
        sqLiteDatabase.close();
        return max;
    }

    public void updateAssignmentPoint(String thoiGianKetThucInput, String tongThoiGianLamBaiInput,
                                      int soLuongCauDungInput, float diemInput, String maBaiLamInput,
                                      String maBaiTapInput ,String maNguoiDungInput) {
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.OPEN_READWRITE);
        String query = "Update " + TABLE_NAME + " SET thoiGianKetThuc = ?, tongThoiGianLamBai = ?, soLuongCauDung = ?, diem = ? " +
                "WHERE maBaiLam = ? AND maBaiTap = ? AND maNguoiDung = ?";
        sqLiteDatabase.execSQL(query, new String[]{thoiGianKetThucInput,
                tongThoiGianLamBaiInput, String.valueOf(soLuongCauDungInput), String.valueOf(diemInput),
        maBaiLamInput, maBaiTapInput, maNguoiDungInput});
        sqLiteDatabase.close();
    }
    @SuppressLint("Range")
    public ArrayList<Assignment> loadDataAssignmentByUserCode(String maNguoiDungInput)
    {
        ArrayList<Assignment> assigmentArrayList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.OPEN_READONLY);
        String query = "Select * from " + TABLE_NAME + " Where MaNguoiDung = ?";
        Cursor cursor = sqLiteDatabase.rawQuery(query, new String[]{maNguoiDungInput});
        if (cursor != null)
        {
            if (cursor.moveToFirst())
            {
                do {
                    Assignment assigment = new Assignment();
                    assigment.setMaBaiLam(cursor.getString(cursor.getColumnIndex(maBaiLam)));
                    assigment.setThoiGianBatDau(cursor.getString(cursor.getColumnIndex(thoiGianBatDau)));
                    assigment.setThoiGianKetThuc(cursor.getString(cursor.getColumnIndex(thoiGianKetThuc)));
                    assigment.setTongThoiGianLamBai(cursor.getString(cursor.getColumnIndex(tongThoiGianLamBai)));
                    assigment.setSoLuongCauDung(Integer.parseInt(cursor.getString(cursor.getColumnIndex(soLuongCauDung))));
                    assigment.setDiem(Float.parseFloat(cursor.getString(cursor.getColumnIndex(diem))));
                    assigment.setLanLam(Integer.parseInt(cursor.getString(cursor.getColumnIndex(lanLam))));
                    assigment.setMaBaiTap(cursor.getString(cursor.getColumnIndex(maBaiTap)));
                    assigment.setMaNguoiDung(cursor.getString(cursor.getColumnIndex(maNguoiDung)));
                    assigmentArrayList.add(assigment);
                }while (cursor.moveToNext());
            }
            cursor.close();
        }
        sqLiteDatabase.close();
        return assigmentArrayList;
    }
    @SuppressLint("Range")
    public ArrayList<Assignment> searchAssignmentByNameOrCode(String keyWord, String maNguoiDungInput)
    {
        ArrayList<Assignment> assigmentArrayList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        String sql = "SELECT * FROM " + TABLE_NAME +
                " WHERE (" + thoiGianBatDau + " LIKE ? " +
                " OR " + diem + " LIKE ? " +
                " OR " + maBaiTap + " LIKE ? ) " +
                " AND MaNguoiDung = ?";

        String keywordPattern = "%" + keyWord + "%"; // Mẫu tìm kiếm
        String[] selectionArgs = new String[]{keywordPattern, keywordPattern, keywordPattern, maNguoiDungInput};
        Cursor cursor = sqLiteDatabase.rawQuery(sql, selectionArgs);
        if (cursor != null)
        {
            if (cursor.moveToFirst())
            {
                do {
                    Assignment assigment = new Assignment();
                    assigment.setMaBaiLam(cursor.getString(cursor.getColumnIndex(maBaiLam)));
                    assigment.setThoiGianBatDau(cursor.getString(cursor.getColumnIndex(thoiGianBatDau)));
                    assigment.setThoiGianKetThuc(cursor.getString(cursor.getColumnIndex(thoiGianKetThuc)));
                    assigment.setTongThoiGianLamBai(cursor.getString(cursor.getColumnIndex(tongThoiGianLamBai)));
                    assigment.setSoLuongCauDung(Integer.parseInt(cursor.getString(cursor.getColumnIndex(soLuongCauDung))));
                    assigment.setDiem(Float.parseFloat(cursor.getString(cursor.getColumnIndex(diem))));
                    assigment.setLanLam(Integer.parseInt(cursor.getString(cursor.getColumnIndex(lanLam))));
                    assigment.setMaBaiTap(cursor.getString(cursor.getColumnIndex(maBaiTap)));
                    assigment.setMaNguoiDung(cursor.getString(cursor.getColumnIndex(maNguoiDung)));
                    assigmentArrayList.add(assigment);
                }while (cursor.moveToNext());

            }
            cursor.close();
        }
        sqLiteDatabase.close();
        return assigmentArrayList;
    }

    @SuppressLint("Range")
    public Assignment loadAssignmentResult(String maBaiLamInput) {
        Assignment assignment = new Assignment();
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.OPEN_READONLY);
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE maBaiLam = ?";
        Cursor cursor = sqLiteDatabase.rawQuery(query, new String[]{maBaiLamInput});
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                assignment.setMaBaiLam(cursor.getString(cursor.getColumnIndex(maBaiLam)));
                assignment.setThoiGianBatDau(cursor.getString(cursor.getColumnIndex(thoiGianBatDau)));
                assignment.setThoiGianKetThuc(cursor.getString(cursor.getColumnIndex(thoiGianKetThuc)));
                assignment.setTongThoiGianLamBai(cursor.getString(cursor.getColumnIndex(tongThoiGianLamBai)));
                assignment.setSoLuongCauDung(cursor.getInt(cursor.getColumnIndex(soLuongCauDung)));
                assignment.setDiem(cursor.getFloat(cursor.getColumnIndex(diem)));
                assignment.setLanLam(cursor.getInt(cursor.getColumnIndex(lanLam)));
                assignment.setMaBaiTap(cursor.getString(cursor.getColumnIndex(maBaiTap)));
                assignment.setMaNguoiDung(cursor.getString(cursor.getColumnIndex(maNguoiDung)));
            }
            cursor.close();
        }
        sqLiteDatabase.close();
        return assignment;
    }
    public boolean updateStatusQuiz(String maNguoiDungInput, String maBaiTapInput)
    {
        boolean check = false;
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.OPEN_READWRITE);
        String  query = "Select * from " + TABLE_NAME + " Where MaNguoiDung = ? AND MaBaiTap = ?";
        Cursor cursor = sqLiteDatabase.rawQuery(query, new String[]{maNguoiDungInput, maBaiTapInput});
        if (cursor != null)
        {
            if (cursor.moveToFirst())
            {
                check = true;
            }
            cursor.close();
        }
        sqLiteDatabase.close();
        return check;
    }
}
