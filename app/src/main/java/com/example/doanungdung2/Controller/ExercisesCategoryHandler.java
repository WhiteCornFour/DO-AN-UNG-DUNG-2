package com.example.doanungdung2.Controller;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.doanungdung2.Model.Admin;
import com.example.doanungdung2.Model.ExercisesCategory;

import java.util.ArrayList;

public class ExercisesCategoryHandler extends SQLiteOpenHelper {
    private static final String DB_NAME = "AppHocTiengAnh";
    private static final int DB_VERSION = 1;

    private static final String TABLE_NAME = "DangBaiTap";
    private static final String maDangBaiTap = "MaDangBaiTap";
    private static final String tenDangBaiTap = "TenDangBaiTap";
    private static final String moTa = "MoTa";

    private static final String PATH = "/data/data/com.example.doanungdung2/database/AppHocTiengAnh.db";

    public ExercisesCategoryHandler(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DB_NAME, factory, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    @SuppressLint("Range")
    public ArrayList<ExercisesCategory> loadAllDataOfExercisesCategory()
    {
        ArrayList<ExercisesCategory> exercisesCategoryArrayList = new ArrayList<>();
        ExercisesCategory exercisesCategory;
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        if (cursor != null)
        {
            if (cursor.moveToFirst())
            {
                do {
                    exercisesCategory = new ExercisesCategory();
                    exercisesCategory.setMaDangBaiTap(cursor.getString(cursor.getColumnIndex(maDangBaiTap)));
                    exercisesCategory.setTenDangBaiTap(cursor.getString(cursor.getColumnIndex(tenDangBaiTap)));
                    exercisesCategory.setMoTa(cursor.getString(cursor.getColumnIndex(moTa)));
                    exercisesCategoryArrayList.add(exercisesCategory);
                }while (cursor.moveToNext());
            }
            cursor.close();
        }

        sqLiteDatabase.close();

        return exercisesCategoryArrayList;
    }

    @SuppressLint("Range")
    public ArrayList<ExercisesCategory> searchResultExercisesCategory(String exercisesCategorySearch) {
        ArrayList<ExercisesCategory> exercisesCategoryArrayList = new ArrayList<>();
        ExercisesCategory ec = null;
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.OPEN_READONLY);

        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + maDangBaiTap + " LIKE '%" + exercisesCategorySearch + "%'" +
                " OR " + tenDangBaiTap + " LIKE '%" + exercisesCategorySearch + "%'";
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        if (cursor != null) {
            if (cursor.moveToFirst())
            {
                do {
                    ec = new ExercisesCategory();
                    ec.setMaDangBaiTap(cursor.getString(cursor.getColumnIndex(maDangBaiTap)));
                    ec.setTenDangBaiTap(cursor.getString(cursor.getColumnIndex(tenDangBaiTap)));
                    ec.setMoTa(cursor.getString(cursor.getColumnIndex(moTa)));
                    exercisesCategoryArrayList.add(ec);
                }while (cursor.moveToNext());
            }
            cursor.close();
        }
        sqLiteDatabase.close();
        return exercisesCategoryArrayList;
    }

    public boolean updateExercisesCategory(ExercisesCategory ec) {
        boolean updated = false;
        SQLiteDatabase sqLiteDatabase = null;
        try {
            sqLiteDatabase = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.OPEN_READWRITE);

            ContentValues contentValues = new ContentValues();
            contentValues.put(tenDangBaiTap, ec.getTenDangBaiTap());
            contentValues.put(moTa, ec.getMoTa());

            int kq = sqLiteDatabase.update(TABLE_NAME, contentValues, maDangBaiTap + " = ?", new String[]{ec.getMaDangBaiTap()});
            updated = kq > 0;

        } catch (SQLiteException e) {
            e.printStackTrace();
        } finally {
            if (sqLiteDatabase != null) {
                sqLiteDatabase.close();
            }
        }
        return updated;
    }

    public void deleteAExerciseCategory(String maDangBaiTap) {
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        String query = "DELETE FROM " + TABLE_NAME + " WHERE MaDangBaiTap = ?";
        sqLiteDatabase.execSQL(query, new String[]{maDangBaiTap});
        sqLiteDatabase.close();
    }

    @SuppressLint("Range")
    public ArrayList<String> returnNameOfCategoriesSpinner() {
        ArrayList<String> exercisesCategoryNames = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        String query = "SELECT " + tenDangBaiTap + " FROM " + TABLE_NAME;
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    String TDBaiTap = cursor.getString(cursor.getColumnIndex(tenDangBaiTap));
                    exercisesCategoryNames.add(TDBaiTap);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        sqLiteDatabase.close();
        return exercisesCategoryNames;
    }

    @SuppressLint("Range")
    public String getExerciseCategoryNameByCode(String maDangBaiTap) {
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        String query = "SELECT " + tenDangBaiTap + " FROM " + TABLE_NAME + " WHERE maDangBaiTap = ?";
        Cursor cursor = sqLiteDatabase.rawQuery(query, new String[]{maDangBaiTap});
        String TDBaiTap = "";
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                TDBaiTap = cursor.getString(cursor.getColumnIndex(tenDangBaiTap));
            }
            cursor.close();
        }
        sqLiteDatabase.close();
        return TDBaiTap;
    }

    @SuppressLint("Range")
    public String getExerciseCategoryCodeByName(String tenDangBaiTap) {
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        String query = "SELECT " + maDangBaiTap + " FROM " + TABLE_NAME + " WHERE tenDangBaiTap = ?";
        Cursor cursor = sqLiteDatabase.rawQuery(query, new String[]{tenDangBaiTap});
        String MDBaiTap = "";
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                MDBaiTap = cursor.getString(cursor.getColumnIndex(maDangBaiTap));
            }
            cursor.close();
        }
        sqLiteDatabase.close();
        return MDBaiTap;

    @SuppressLint("Range")
    public String searchCodeExerciseCategoryByName(String tenDangBaiTap) {
        String result = null;
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        String query = "SELECT " + maDangBaiTap + " FROM " + TABLE_NAME + " WHERE tenDangBaiTap = ?";
        Cursor cursor = sqLiteDatabase.rawQuery(query, new String[]{tenDangBaiTap});
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                result = cursor.getString(cursor.getColumnIndex(maDangBaiTap));
            }
            cursor.close(); // Đóng cursor
        }
        sqLiteDatabase.close(); // Đóng kết nối CSDL
        return result;

    }

}

