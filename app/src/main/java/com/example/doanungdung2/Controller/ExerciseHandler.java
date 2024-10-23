package com.example.doanungdung2.Controller;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.doanungdung2.Model.Exercise;
import com.example.doanungdung2.Model.ExercisesCategory;

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
                " OR " + tenBaiTap + " LIKE '%" + keyWord + "%'" +
                " OR " + maDangBaiTap + " LIKE '%" + keyWord + "%'";
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

    public boolean updateExercises(Exercise e) {
        boolean updated = false;
        SQLiteDatabase sqLiteDatabase = null;
        try {
            sqLiteDatabase = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.OPEN_READWRITE);

            ContentValues contentValues = new ContentValues();
            contentValues.put(tenBaiTap, e.getTenBaiTap());
            contentValues.put(soCau, e.getTenBaiTap());
            contentValues.put(mucDo, e.getMucDo());
            contentValues.put(thoiGian, e.getThoiGian());
            contentValues.put(moTa, e.getMoTa());
            contentValues.put(maDangBaiTap, e.getMaDangBaiTap());

            int kq = sqLiteDatabase.update(TABLE_NAME, contentValues, maBaiTap + " = ?", new String[]{e.getMaBaiTap()});
            updated = kq > 0;

        } catch (SQLiteException exception) {
            exception.printStackTrace();
        } finally {
            if (sqLiteDatabase != null) {
                sqLiteDatabase.close();
            }
        }
        return updated;
    }

    public boolean isExerciseNameExists(String tenBaiTap) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Exercises WHERE tenBaiTap = ?", new String[]{tenBaiTap});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    public void deleteExerciseByCode(String maBaiTap)
    {
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        String query = "DELETE FROM " + TABLE_NAME + " WHERE maBaiTap = ?";
        sqLiteDatabase.execSQL(query, new String[]{maBaiTap});
        sqLiteDatabase.close();
    }
    public void insertNewExercise(Exercise exercise)
    {
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        String query = "INSERT INTO " + TABLE_NAME + " (maBaiTap, tenBaiTap, soCau, mucDo, thoiGian," +
                "moTa, maDangBaiTap) VALUES (?, ?, ?, ?, ?, ?, ?)";
        sqLiteDatabase.execSQL(query, new String[]{exercise.getMaBaiTap(), exercise.getTenBaiTap(),
                String.valueOf(exercise.getSoCau()), exercise.getMucDo(), exercise.getThoiGian(),
                exercise.getMoTa(), exercise.getMaDangBaiTap()});
        sqLiteDatabase.close();
    }
    public boolean checkCodeAndNameExercise(String maBaiTap, String tenBaiTap)
    {
        boolean checked = false;
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.OPEN_READONLY);
        String query = "Select * from " + TABLE_NAME + " Where maBaiTap = ? Or tenBaiTap = ?";
        Cursor cursor = sqLiteDatabase.rawQuery(query, new String[]{maBaiTap, tenBaiTap});
        if (cursor != null)
        {
            if (cursor.moveToFirst())
            {
                checked = true;
            }
        }
        return checked;
    }
}
