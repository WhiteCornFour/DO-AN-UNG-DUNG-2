package com.example.doanungdung2.Controller;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.doanungdung2.Model.Dictionary;
import com.example.doanungdung2.Model.Exercise;

import java.util.ArrayList;

public class DictionaryHandler extends SQLiteOpenHelper {

    private static final String DB_NAME = "AppHocTiengAnh";
    private static final int DB_VERSION = 1;

    private static final String TABLE_NAME = "TuDien";
    private static final String maTuVung = "MaTuVung";
    private static final String tuTiengAnh = "TuTiengAnh";
    private static final String tuTiengViet = "TuTiengViet";
    private static final String gioiTuDiKem = "GioiTuDiKem";
    private static final String cachPhatAm = "CachPhatAm";
    private static final String loaiTu = "LoaiTu";
    private static final String viDu = "ViDu";

    private static final String PATH = "/data/data/com.example.doanungdung2/database/AppHocTiengAnh.db";

    public DictionaryHandler(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DB_NAME, factory, DB_VERSION );
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @SuppressLint("Range")
    public ArrayList<Dictionary> loadAllDataOfDictionary()
    {
        ArrayList<Dictionary> dictionaryArrayList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        if (cursor != null)
        {
            if (cursor.moveToFirst())
            {
                do {
                    Dictionary dictionary = new Dictionary();
                    dictionary.setMaTuVung(cursor.getString(cursor.getColumnIndex(maTuVung)));
                    dictionary.setTuTiengAnh(cursor.getString(cursor.getColumnIndex(tuTiengAnh)));
                    dictionary.setTuTiengViet(cursor.getString(cursor.getColumnIndex(tuTiengViet)));
                    dictionary.setGioiTuDiKem(cursor.getString(cursor.getColumnIndex(gioiTuDiKem)));
                    dictionary.setCachPhatAm(cursor.getString(cursor.getColumnIndex(cachPhatAm)));
                    dictionary.setLoaiTu(cursor.getString(cursor.getColumnIndex(loaiTu)));
                    dictionary.setViDu(cursor.getString(cursor.getColumnIndex(viDu)));
                    dictionaryArrayList.add(dictionary);
                }while (cursor.moveToNext());

            }
            cursor.close();
        }
        sqLiteDatabase.close();
        return dictionaryArrayList;
    }

    public void insertDictionary(Dictionary dictionary)
    {
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        String query = "INSERT INTO " + TABLE_NAME + " (maTuVung, tuTiengAnh, tuTiengViet, gioiTuDiKem, cachPhatAm," +
                "loaiTu, viDu) VALUES (?, ?, ?, ?, ?, ?, ?)";
        sqLiteDatabase.execSQL(query, new String[]{dictionary.getMaTuVung(),dictionary.getTuTiengAnh(),
                String.valueOf(dictionary.getTuTiengViet()), dictionary.getGioiTuDiKem(), dictionary.getCachPhatAm(),
                dictionary.getLoaiTu(), dictionary.getViDu()});
        sqLiteDatabase.close();
    }

    public boolean checkIfDictionaryCodeExist(String maTuVungInput) {
        boolean exists = false;
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.OPEN_READONLY);

        String query = "SELECT 1 FROM " + TABLE_NAME + " WHERE " + maTuVung + " = ?";
        Cursor cursor = sqLiteDatabase.rawQuery(query, new String[]{maTuVungInput});

        if (cursor != null && cursor.moveToFirst()) {
            exists = true;
        }

        if (cursor != null) {
            cursor.close();
        }
        sqLiteDatabase.close();

        return exists;
    }


    public boolean checkDictionaryByNameAndCode(String tuTiengAnhInput, String maTuVungInput) {
        boolean exists = false;

        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.OPEN_READONLY);
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + tuTiengAnh + " = ? AND " + maTuVung + " != ?";
        Cursor cursor = sqLiteDatabase.rawQuery(query, new String[]{tuTiengAnhInput, maTuVungInput});

        if (cursor != null && cursor.moveToFirst()) {
            exists = true;
        }

        if (cursor != null) {
            cursor.close();
        }
        sqLiteDatabase.close();

        return exists;
    }

    public boolean updateDictionary(Dictionary d) {
        boolean updated = false;
        SQLiteDatabase sqLiteDatabase = null;
        try {
            sqLiteDatabase = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.OPEN_READWRITE);

            ContentValues contentValues = new ContentValues();
            contentValues.put(tuTiengAnh, d.getTuTiengAnh());
            contentValues.put(tuTiengViet, d.getTuTiengViet());
            contentValues.put(gioiTuDiKem, d.getGioiTuDiKem());
            contentValues.put(cachPhatAm, d.getCachPhatAm());
            contentValues.put(loaiTu, d.getLoaiTu());
            contentValues.put(viDu, d.getViDu());

            int kq = sqLiteDatabase.update(TABLE_NAME, contentValues, maTuVung + " = ?", new String[]{d.getMaTuVung()});
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

    @SuppressLint("Range")
    public ArrayList<Dictionary> searchDictionaryByNameOrCode(String keyWord)
    {
        ArrayList<Dictionary> dictionaryArrayList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + maTuVung + " LIKE '%" + keyWord + "%'" +
                " OR " + tuTiengAnh + " LIKE '%" + keyWord + "%'" +
                " OR " + tuTiengViet + " LIKE '%" + keyWord + "%'";
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        if (cursor != null)
        {
            if (cursor.moveToFirst())
            {
                do {
                    Dictionary dictionary = new Dictionary();
                    dictionary.setMaTuVung(cursor.getString(cursor.getColumnIndex(maTuVung)));
                    dictionary.setTuTiengAnh(cursor.getString(cursor.getColumnIndex(tuTiengAnh)));
                    dictionary.setTuTiengViet(cursor.getString(cursor.getColumnIndex(tuTiengViet)));
                    dictionary.setGioiTuDiKem(cursor.getString(cursor.getColumnIndex(gioiTuDiKem)));
                    dictionary.setCachPhatAm(cursor.getString(cursor.getColumnIndex(cachPhatAm)));
                    dictionary.setLoaiTu(cursor.getString(cursor.getColumnIndex(loaiTu)));
                    dictionary.setViDu(cursor.getString(cursor.getColumnIndex(viDu)));
                    dictionaryArrayList.add(dictionary);
                }while (cursor.moveToNext());

            }
            cursor.close();
        }
        sqLiteDatabase.close();
        return dictionaryArrayList;
    }

    public void deleteDictionaries(ArrayList<Dictionary> dictionaries) {
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.OPEN_READWRITE);

        for (Dictionary d : dictionaries) {
            String sql = "DELETE FROM " + TABLE_NAME + " WHERE " + maTuVung + " = '" + d.getMaTuVung() + "'";
            sqLiteDatabase.execSQL(sql);
        }
        sqLiteDatabase.close();
    }

    public void deleteDictionary(Dictionary d) {
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.OPEN_READWRITE);

        String sql = "DELETE FROM " + TABLE_NAME + " WHERE " + maTuVung + " = '" + d.getMaTuVung() + "'";
        sqLiteDatabase.execSQL(sql);
        sqLiteDatabase.close();
    }
}
