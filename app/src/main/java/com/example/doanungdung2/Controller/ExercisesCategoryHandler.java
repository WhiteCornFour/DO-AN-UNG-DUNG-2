package com.example.doanungdung2.Controller;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class ExercisesCategoryHandler extends SQLiteOpenHelper {
    private static final String DB_NAME = "AppHocTiengAnh";
    private static final int DB_VERSION = 1;

    private static final String TABLE_NAME = "NguoiDung";
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
}
