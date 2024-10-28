package com.example.doanungdung2.Controller;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.doanungdung2.Model.Grammar;
import com.example.doanungdung2.Model.GrammarCategory;

import java.util.ArrayList;

public class GrammarHandler extends SQLiteOpenHelper {
    private static final String DB_NAME = "AppHocTiengAnh";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "NguPhap";
    private static final String maNguPhap = "MaNguPhap";
    private static final String tenNguPhap = "TenNguPhap";
    private static final String noiDung = "NoiDung";
    private static final String congThuc = "CongThuc";
    private static final String viDu = "ViDu";
    private static final String maDangNguPhap = "MaDangNguPhap";
    private static final String PATH = "/data/data/com.example.doanungdung2/database/AppHocTiengAnh.db";

    public GrammarHandler(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    public boolean checkGrammarCateOnGrammar(String maDangNguPhap)
    {
        boolean check = false;
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.OPEN_READONLY);
        String query = "Select * from " + TABLE_NAME + " Where MaDangNguPhap = ?";
        Cursor cursor = sqLiteDatabase.rawQuery(query, new String[]{maDangNguPhap});
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
    public ArrayList<Grammar> loadAllDataOfGrammar()
    {
        ArrayList<Grammar> grammarArrayList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.OPEN_READONLY);
        String query = "Select * From " + TABLE_NAME;
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        if (cursor != null)
        {
            if (cursor.moveToFirst())
            {
                do {
                    Grammar grammar = new Grammar();
                    grammar.setMaNguPhap(cursor.getString(cursor.getColumnIndex(maNguPhap)));
                    grammar.setTenNguPhap(cursor.getString(cursor.getColumnIndex(tenNguPhap)));
                    grammar.setNoiDung(cursor.getString(cursor.getColumnIndex(noiDung)));
                    grammar.setCongThuc(cursor.getString(cursor.getColumnIndex(congThuc)));
                    grammar.setViDu(cursor.getString(cursor.getColumnIndex(viDu)));
                    grammar.setMaDangNguPhap(cursor.getString(cursor.getColumnIndex(maDangNguPhap)));
                    grammarArrayList.add(grammar);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        sqLiteDatabase.close();
        return grammarArrayList;
    }
    public boolean checkNameAndGrammarCode(String tenNguPhap, String maNguPhap)
    {
        boolean check = false;
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.OPEN_READONLY);
        String query = "Select *  from " + TABLE_NAME + " Where TenNguPhap = ? Or MaNguPhap = ?";
        Cursor cursor = sqLiteDatabase.rawQuery(query, new String[]{tenNguPhap, maNguPhap});
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
    public void addNewGrammar(Grammar grammar)
    {
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.OPEN_READWRITE);
        String query = "Insert into " + TABLE_NAME + " ( MaNguPhap, TenNguPhap, NoiDung, CongThuc, ViDu, MaDangNguPhap) Values" +
                " ( ?, ?, ?, ?, ?, ?)";
        sqLiteDatabase.execSQL(query, new Object[]{
                grammar.getMaNguPhap(),
                grammar.getTenNguPhap(),
                grammar.getNoiDung(),
                grammar.getCongThuc(),
                grammar.getViDu(),
                grammar.getMaDangNguPhap()});
        sqLiteDatabase.close();
    }
    @SuppressLint("Range")
    public ArrayList<Grammar> searchByCodeOrNameGrammar(String keyWord) {
        ArrayList<Grammar> grammarArrayList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = null;
        Cursor cursor = null;
        sqLiteDatabase = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.OPEN_READONLY);
        String sql = "SELECT * FROM " + TABLE_NAME +
                " WHERE " + maNguPhap + " LIKE ? OR " + tenNguPhap + " LIKE ?";

        // Sử dụng "%" + keyWord + "%" để tìm kiếm chuỗi có chứa từ khóa
        String[] selectionArgs = new String[]{"%" + keyWord + "%", "%" + keyWord + "%"};
        cursor = sqLiteDatabase.rawQuery(sql, selectionArgs);

        if (cursor.moveToFirst()) {
            do {
                Grammar grammar = new Grammar();
                grammar.setMaNguPhap(cursor.getString(cursor.getColumnIndex(maNguPhap)));
                grammar.setTenNguPhap(cursor.getString(cursor.getColumnIndex(tenNguPhap)));
                grammar.setNoiDung(cursor.getString(cursor.getColumnIndex(noiDung)));
                grammar.setCongThuc(cursor.getString(cursor.getColumnIndex(congThuc)));
                grammar.setViDu(cursor.getString(cursor.getColumnIndex(viDu)));
                grammar.setMaDangNguPhap(cursor.getString(cursor.getColumnIndex(maDangNguPhap)));

                grammarArrayList.add(grammar);
            } while (cursor.moveToNext());
        }
        return grammarArrayList;
    }
    public void upDateGrammar(Grammar grammar) {
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.OPEN_READWRITE);
        String query = "Update " + TABLE_NAME + " Set TenNguPhap = ?, NoiDung = ?, CongThuc = ?, ViDu = ?, MaDangNguPhap = ? " +
                "Where MaNguPhap = ?";
        sqLiteDatabase.execSQL(query, new String[]{
                grammar.getTenNguPhap(),
                grammar.getNoiDung(),
                grammar.getCongThuc(),
                grammar.getViDu(),
                grammar.getMaDangNguPhap(),
                grammar.getMaNguPhap()  // Tham số cho điều kiện WHERE
        });
        sqLiteDatabase.close();
    }
}
