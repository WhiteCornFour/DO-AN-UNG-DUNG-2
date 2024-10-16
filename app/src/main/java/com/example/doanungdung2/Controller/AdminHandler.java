package com.example.doanungdung2.Controller;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.doanungdung2.Model.Admin;
import com.example.doanungdung2.Model.User;

import java.util.ArrayList;

public class AdminHandler extends SQLiteOpenHelper {
    private static final String DB_NAME = "AppHocTiengAnh";
    private static final int DB_VERSION = 1;

    private static final String TABLE_NAME = "QuanTriVien";
    private static final String maAdmin = "MaAdmin";
    private static final String tenAdmin = "TenAdmin";
    private static final String taiKhoan = "TaiKhoanAdmin";
    private static final String matKhau = "MatKhauAdmin";
    private static final String email = "EmailAdmin";
    private static final String PATH = "/data/data/com.example.doanungdung2/database/AppHocTiengAnh.db";

    public AdminHandler(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DB_NAME, factory, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public boolean validateLoginAdmin(String account, String password) {
        SQLiteDatabase db = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.OPEN_READONLY);
        String sql = "SELECT COUNT(*) FROM " + TABLE_NAME + " WHERE " + taiKhoan + " = ? AND " + matKhau + " = ?";
        Cursor cursor = db.rawQuery(sql, new String[]{account, password});

        boolean isValid = false;
        if (cursor.moveToFirst()) {
            int count = cursor.getInt(0);
            isValid = (count > 0);
        }
        Log.d("SQL_COUNT_RESULT", sql);
        cursor.close();
        db.close();
        return isValid;
    }

    @SuppressLint("Range")
    public ArrayList<Admin> getNameAndEmailOfAdmin(String account, String pass)
    {
        ArrayList<Admin> result = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        String sql = "SELECT tenAdmin, EmailAdmin FROM " + TABLE_NAME + " WHERE " + taiKhoan + " = ? AND " + matKhau + " = ?";
        Cursor cursor = sqLiteDatabase.rawQuery(sql, new String[]{account, pass});

        if (cursor.moveToFirst()) {
            Admin ad = new Admin();
            ad.setTenAdmin(cursor.getString(cursor.getColumnIndex(tenAdmin)));
            ad.setEmail(cursor.getString(cursor.getColumnIndex(email)));
            result.add(ad);
        }

        cursor.close();
        sqLiteDatabase.close();

        //Log.d("Name admin", result);
        return result;
    }




}