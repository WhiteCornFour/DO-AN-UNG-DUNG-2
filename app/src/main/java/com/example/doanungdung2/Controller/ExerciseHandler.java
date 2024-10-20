package com.example.doanungdung2.Controller;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.doanungdung2.Model.Exercise;

import java.util.ArrayList;

public class ExerciseHandler extends SQLiteOpenHelper {

    private static final String DB_NAME = "AppHocTiengAnh";
    private static final int DB_VERSION = 1;

    private static final String TABLE_NAME = "BaiTap";
    private static final String maBaiTap = "MaBaiTap";
    private static final String tenBaiTap = "TenBaiTap";
    private static final String soCau = "SoCau";
    private static final String mucDo = "MucDo";
    private static final String thoiGian = "ThoiGian";
    private static final String moTa = "MoTa";
    private static final String maDangBaiTap = "MaDangBaiTap";

    private static final String PATH = "/data/data/com.example.doanungdung2/database/AppHocTiengAnh.db";

    public ExerciseHandler(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DB_NAME, factory, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    public boolean checkExcerciseCateHaveExcercise(String maDangBaiTap)
    {
        boolean checkResult = false;
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        String query = "Select * From " + TABLE_NAME + " Where MaDangBaiTap " + " = ?";
        Cursor cursor = sqLiteDatabase.rawQuery(query, new String[]{maDangBaiTap});
        if (cursor != null)
        {
            if (cursor.moveToFirst())
            {
                checkResult = true;
            }
            cursor.close();
        }
        return checkResult;
    }
    @SuppressLint("Range")
    public ArrayList<Exercise> loadAllDataOfExercise()
    {
        ArrayList<Exercise> exerciseArrayList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        if (cursor != null)
        {
            if (cursor.moveToFirst())
            {
                do {
                    Exercise exercise = new Exercise();
                    exercise.setMaBaiTap(cursor.getString(cursor.getColumnIndex(maBaiTap)));
                    exercise.setTenBaiTap(cursor.getString(cursor.getColumnIndex(tenBaiTap)));
                    exercise.setSoCau(cursor.getInt(cursor.getColumnIndex(soCau)));
                    exercise.setMucDo(cursor.getString(cursor.getColumnIndex(mucDo)));
                    exercise.setThoiGian(cursor.getString(cursor.getColumnIndex(thoiGian)));
                    exercise.setMoTa(cursor.getString(cursor.getColumnIndex(moTa)));
                    exercise.setMaDangBaiTap(cursor.getString(cursor.getColumnIndex(maDangBaiTap)));
                    exerciseArrayList.add(exercise);
                }while (cursor.moveToNext());

            }
            cursor.close();
        }
        sqLiteDatabase.close();
        return exerciseArrayList;
    }
    @SuppressLint("Range")
    public ArrayList<Exercise> searchExerciseByNameOrCode(String keyWord)
    {
        ArrayList<Exercise> exerciseArrayList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + maBaiTap + " LIKE '%" + keyWord + "%'" +
                " OR " + tenBaiTap + " LIKE '%" + keyWord + "%'";
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        if (cursor != null)
        {
            if (cursor.moveToFirst())
            {
                do {
                    Exercise exercise = new Exercise();
                    exercise.setMaBaiTap(cursor.getString(cursor.getColumnIndex(maBaiTap)));
                    exercise.setTenBaiTap(cursor.getString(cursor.getColumnIndex(tenBaiTap)));
                    exercise.setSoCau(cursor.getInt(cursor.getColumnIndex(soCau)));
                    exercise.setMucDo(cursor.getString(cursor.getColumnIndex(mucDo)));
                    exercise.setThoiGian(cursor.getString(cursor.getColumnIndex(thoiGian)));
                    exercise.setMoTa(cursor.getString(cursor.getColumnIndex(moTa)));
                    exercise.setMaDangBaiTap(cursor.getString(cursor.getColumnIndex(maDangBaiTap)));
                    exerciseArrayList.add(exercise);
                }while (cursor.moveToNext());

            }
            cursor.close();
        }
        sqLiteDatabase.close();
        return exerciseArrayList;
    }
}
