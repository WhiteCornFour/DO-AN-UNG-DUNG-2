package com.example.doanungdung2.Controller;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.doanungdung2.Model.AssignmentDetail;

import java.util.ArrayList;

public class AssignmentDetailHandler extends SQLiteOpenHelper {

    private static final String DB_NAME = "AppHocTiengAnh";
    private static final int DB_VERSION = 1;

    private static final String TABLE_NAME = "ChiTietBaiLam";
    private static final String maChiTietBaiLam = "MaChiTietBaiLam";
    private static final String cauTraLoi = "CauTraLoi";
    private static final String ketQuaCauTraLoi = "KetQuaCauTraLoi";
    private static final String maCauHoi = "MaCauHoi";
    private static final String maBaiLam = "MaBaiLam";

    private static final String PATH = "/data/data/com.example.doanungdung2/database/AppHocTiengAnh.db";

    public AssignmentDetailHandler(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DB_NAME, factory, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    @SuppressLint("Range")
    public ArrayList<AssignmentDetail> loadAssignmentDetail() {
        ArrayList<AssignmentDetail> assigmentDetailArrayList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.OPEN_READONLY);
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + maBaiLam + " = ?";
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        if (cursor != null)
        {
            if (cursor.moveToFirst()) {
                do {
                    AssignmentDetail assigmentDetail = new AssignmentDetail();
                    assigmentDetail.setMaChiTietBaiLam(cursor.getString(cursor.getColumnIndex(maChiTietBaiLam)));
                    assigmentDetail.setCauTraLoi(cursor.getString(cursor.getColumnIndex(cauTraLoi)));
                    assigmentDetail.setKetQuaCauTraLoi(cursor.getString(cursor.getColumnIndex(ketQuaCauTraLoi)));
                    assigmentDetail.setMaCauHoi(cursor.getString(cursor.getColumnIndex(maCauHoi)));
                    assigmentDetail.setMaBaiLam(cursor.getString(cursor.getColumnIndex(maBaiLam)));
                    assigmentDetailArrayList.add(assigmentDetail);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        sqLiteDatabase.close();
        return assigmentDetailArrayList;
    }

    public void insertAssignmentDetail(AssignmentDetail assigmentDetail)
    {
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.OPEN_READWRITE);

        String query = "INSERT INTO " + TABLE_NAME + " (maChiTietBaiLam, cauTraLoi, ketQuaCauTraLoi, maCauHoi, " +
                "maBailam) VALUES (?, ?, ?, ?, ?)";

        sqLiteDatabase.execSQL(query, new Object[]{
                assigmentDetail.getMaChiTietBaiLam(),
                assigmentDetail.getCauTraLoi(),
                assigmentDetail.getKetQuaCauTraLoi(),
                assigmentDetail.getMaCauHoi(),
                assigmentDetail.getMaBaiLam()
        });

        sqLiteDatabase.close();
    }
    public void upDateAssignmentDetail(String maCauHoi, String maBaiLam, String cauTraLoi, String ketQuaCauTraLoi) {
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.OPEN_READWRITE);
        String query = "Update " + TABLE_NAME + " Set cauTraLoi = ?, ketQuaCauTraLoi = ? " +
                "Where maCauHoi = ? AND maBaiLam = ?";
        sqLiteDatabase.execSQL(query, new String[]{
                cauTraLoi,
                ketQuaCauTraLoi,
                maCauHoi,
                maBaiLam
        });
        sqLiteDatabase.close();
    }

    @SuppressLint("Range")
    public String getSelectedAnswerForQuestion(String maCauHoiInput, String maBaiLamInput) {
        String answer = null;
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.OPEN_READONLY);
        String query = "SELECT " + cauTraLoi + " FROM " + TABLE_NAME + " WHERE " + maCauHoi + " = ? AND " + maBaiLam + " = ?";
        Cursor cursor = sqLiteDatabase.rawQuery(query, new String[]{maCauHoiInput, maBaiLamInput});

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                answer = cursor.getString(cursor.getColumnIndex(cauTraLoi));
            }
            cursor.close();
        }
        sqLiteDatabase.close();
        return answer;
    }

    public int countRightAnswer(String maBaiLamInput) {
        int count = 0;
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.OPEN_READONLY);
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE ketQuaCauTraLoi = 'Đúng' AND maBaiLam = ?";
        Cursor cursor = sqLiteDatabase.rawQuery(query, new String[]{maBaiLamInput});
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    count++;
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        sqLiteDatabase.close();
        return count;
    }
    @SuppressLint("Range")
    public ArrayList<AssignmentDetail> loadAssignmentDetailsForTestDetail(String maBaiLamInput) {
        ArrayList<AssignmentDetail> assigmentDetailArrayList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.OPEN_READONLY);
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + maBaiLam + " = ?";
        Cursor cursor = sqLiteDatabase.rawQuery(query, new String[]{maBaiLamInput});
        if (cursor != null)
        {
            if (cursor.moveToFirst()) {
                do {
                    AssignmentDetail assigmentDetail = new AssignmentDetail();
                    assigmentDetail.setMaChiTietBaiLam(cursor.getString(cursor.getColumnIndex(maChiTietBaiLam)));
                    assigmentDetail.setCauTraLoi(cursor.getString(cursor.getColumnIndex(cauTraLoi)));
                    assigmentDetail.setKetQuaCauTraLoi(cursor.getString(cursor.getColumnIndex(ketQuaCauTraLoi)));
                    assigmentDetail.setMaCauHoi(cursor.getString(cursor.getColumnIndex(maCauHoi)));
                    assigmentDetail.setMaBaiLam(cursor.getString(cursor.getColumnIndex(maBaiLam)));
                    assigmentDetailArrayList.add(assigmentDetail);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        sqLiteDatabase.close();
        return assigmentDetailArrayList;
    }
}
