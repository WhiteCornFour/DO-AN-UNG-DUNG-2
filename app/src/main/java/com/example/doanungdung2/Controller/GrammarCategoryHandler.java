package com.example.doanungdung2.Controller;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.ArrayAdapter;

import androidx.annotation.Nullable;

import com.example.doanungdung2.Model.GrammarCategory;

import java.util.ArrayList;
import java.util.logging.Handler;

public class GrammarCategoryHandler extends SQLiteOpenHelper {

    private static final String DB_NAME = "AppHocTiengAnh";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "DangNguPhap";
    private static final String maDangNguPhap = "MaDangNguPhap";
    private static final String tenDangNguPhap = "TenDangNguPhap";
    private static final String moTa = "MoTa";
    private static final String PATH = "/data/data/com.example.doanungdung2/database/AppHocTiengAnh.db";

    public GrammarCategoryHandler(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DB_NAME, factory, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    @SuppressLint("Range")
    public ArrayList<GrammarCategory> loadAllDataGrammarCategory()
    {
        ArrayList<GrammarCategory> grammarCategoryArrayList = new ArrayList<>();
        GrammarCategory grammarCategory;
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.OPEN_READONLY);
        String query = "Select * from " + TABLE_NAME;
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        if (cursor != null)
        {
            if (cursor.moveToFirst())
            {
                do {
                    grammarCategory = new GrammarCategory();
                    grammarCategory.setMaDangNguPhap(cursor.getString(cursor.getColumnIndex(maDangNguPhap)));
                    grammarCategory.setTenDangNguPhap(cursor.getString(cursor.getColumnIndex(tenDangNguPhap)));
                    grammarCategory.setMoTa(cursor.getString(cursor.getColumnIndex(moTa)));
                    grammarCategoryArrayList.add(grammarCategory);
                }while (cursor.moveToNext());
            }
            cursor.close();
        }
        sqLiteDatabase.close();
        return grammarCategoryArrayList;
    }
    public void addNewGrammarCategory(GrammarCategory grammarCategory)
    {
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.OPEN_READWRITE);
        String query = "Insert into " + TABLE_NAME + " (MaDangNguPhap, TenDangNguPhap, MoTa) Values (?, ?, ?)";
        sqLiteDatabase.execSQL(query, new String[]{grammarCategory.getMaDangNguPhap(), grammarCategory.getTenDangNguPhap(),
                grammarCategory.getMoTa()});
        sqLiteDatabase.close();
    }
    public boolean checkNameAndCodeOfGrammarCate(String maDangNguPhap, String tenDangNguPhap)
    {
        boolean check = false;
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.OPEN_READONLY);
        String query = "Select * from " + TABLE_NAME + " Where MaDangNguPhap = ? OR TenDangNguPhap = ?";
        Cursor cursor = sqLiteDatabase.rawQuery(query, new String[]{maDangNguPhap, tenDangNguPhap});
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
    @SuppressLint("Range")
    public ArrayList<GrammarCategory> searchByCodeOrName(String keyWord)
    {
        ArrayList<GrammarCategory> grammarCategoryArrayList = new ArrayList<>();
        GrammarCategory grammarCategory;
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.OPEN_READONLY);
        String query = "Select * From " + TABLE_NAME + " Where MaDangNguPhap = ?  OR TenDangNguPhap = ?";
        Cursor cursor = sqLiteDatabase.rawQuery(query, new String[]{keyWord});
        if (cursor != null)
        {
            if (cursor.moveToFirst())
            {
                do {
                    grammarCategory = new GrammarCategory();
                    grammarCategory.setMaDangNguPhap(cursor.getString(cursor.getColumnIndex(maDangNguPhap)));
                    grammarCategory.setTenDangNguPhap(cursor.getString(cursor.getColumnIndex(tenDangNguPhap)));
                    grammarCategory.setMoTa(cursor.getString(cursor.getColumnIndex(moTa)));
                    grammarCategoryArrayList.add(grammarCategory);
                }while (cursor.moveToNext());
            }
            cursor.close();
        }
        return grammarCategoryArrayList;
    }
    public void upDateGrammarCategory(GrammarCategory grammarCategory)
    {
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.OPEN_READWRITE);
        String query = "Update " + TABLE_NAME + " Set TenDangNguPhap = ?, MoTa = ? Where MaDangNguPhap = ?";
        sqLiteDatabase.execSQL(query, new String[]{grammarCategory.getTenDangNguPhap(), grammarCategory.getMoTa(),
                                        grammarCategory.getMaDangNguPhap()});
        sqLiteDatabase.close();
    }
    public void deleteGrammarCategory(String maDangNguPhap)
    {
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.OPEN_READWRITE);
        String query = "Delete from " + TABLE_NAME + " Where MaDangNguPhap = ?";
        sqLiteDatabase.execSQL(query, new String[]{maDangNguPhap});
        sqLiteDatabase.close();
    }
    @SuppressLint("Range")
    public String searchGrammarCodeByName(String tenDangNguPhap)
    {
        String kq = "";
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.OPEN_READWRITE);
        String query = "Select MaDangNguPhap From " +  TABLE_NAME + " Where TenDangNguPhap = ?";
        Cursor cursor = sqLiteDatabase.rawQuery(query, new String[]{tenDangNguPhap});
        if (cursor != null)
        {
            if (cursor.moveToFirst())
            {
                kq = cursor.getString(cursor.getColumnIndex(maDangNguPhap));
            }
            cursor.close();
        }
        sqLiteDatabase.close();
        return kq;
    }
}
