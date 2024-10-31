package com.example.doanungdung2.Controller;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

import com.example.doanungdung2.Model.SampleSentence;

import java.util.ArrayList;

public class SampleSentenceHandler extends SQLiteOpenHelper {
    private static final String DB_NAME = "AppHocTiengAnh";
    private static final int DB_VERSION = 1;

    private static final String TABLE_NAME = "MauCau";
    private static final String maMauCau = "MaMauCau";
    private static final String maChuDeMauCau = "MaChuDeMauCau";
    private static final String MauCau = "MauCau";
    private static final String phienDich = "PhienDich";
    private static final String tinhHuongSuDung = "TinhHuongSuDung";

    private static final String PATH = "/data/data/com.example.doanungdung2/database/AppHocTiengAnh.db";

    public SampleSentenceHandler(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DB_NAME, factory, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean checkSampleSentenceHaveTopic(String maChuDeMauCau) {
        boolean checkResult = false;
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + maChuDeMauCau + " = ?";
        Cursor cursor = sqLiteDatabase.rawQuery(query, new String[]{maChuDeMauCau});

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                checkResult = true;
            }
            cursor.close();
        }

        return checkResult;
    }

    @SuppressLint("Range")
    public ArrayList<SampleSentence> loadAllDataOfSampleSentence() {
        ArrayList<SampleSentence> sampleSentenceArrayList = new ArrayList<>();
        SQLiteDatabase db = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    SampleSentence sampleSentence = new SampleSentence();
                    sampleSentence.setMaMauCau(cursor.getString(cursor.getColumnIndex(maMauCau)));
                    sampleSentence.setMauCau(cursor.getString(cursor.getColumnIndex(MauCau)));
                    sampleSentence.setPhienDich(cursor.getString(cursor.getColumnIndex(phienDich)));
                    sampleSentence.setTinhHuongSuDung(cursor.getString(cursor.getColumnIndex(tinhHuongSuDung)));
                    sampleSentence.setMaChuDeMauCau(cursor.getString(cursor.getColumnIndex(maChuDeMauCau)));
                    sampleSentenceArrayList.add(sampleSentence);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        db.close();
        return sampleSentenceArrayList;
    }

    @SuppressLint("Range")
    public ArrayList<SampleSentence> searchResultSampleSentence(String sampleSentenceSearch) {
        ArrayList<SampleSentence> sampleSentenceArrayList = new ArrayList<>();
        SampleSentence ss = null;
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.OPEN_READONLY);

        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + maMauCau + " LIKE '%" + sampleSentenceSearch + "%'" +
                " OR " + phienDich + " LIKE '%" + sampleSentenceSearch + "%'";

        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    ss = new SampleSentence();
                    ss.setMaMauCau(cursor.getString(cursor.getColumnIndex(maMauCau)));
                    ss.setMaChuDeMauCau(cursor.getString(cursor.getColumnIndex(maChuDeMauCau)));
                    ss.setMauCau(cursor.getString(cursor.getColumnIndex(MauCau)));
                    ss.setPhienDich(cursor.getString(cursor.getColumnIndex(phienDich)));
                    ss.setTinhHuongSuDung(cursor.getString(cursor.getColumnIndex(tinhHuongSuDung)));

                    sampleSentenceArrayList.add(ss);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        sqLiteDatabase.close();
        return sampleSentenceArrayList;
    }


    public boolean checkCodeAndNameSampleSentence(String maMauCau, String tenMauCau) {
        boolean checked = false;
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE maMauCau = ? OR MauCau = ?";
        Cursor cursor = sqLiteDatabase.rawQuery(query, new String[]{maMauCau, MauCau});

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                checked = true;
            }
            cursor.close();
        }

        sqLiteDatabase.close();
        return checked;
    }

    public boolean checkNameSampleSentence(String tenMauCau) {
        boolean checked = false;
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.CREATE_IF_NECESSARY);

        String query = "SELECT * FROM " + TABLE_NAME + " WHERE MauCau = ?";
        Cursor cursor = sqLiteDatabase.rawQuery(query, new String[]{tenMauCau});

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                checked = true;
            }
            cursor.close();
        }

        sqLiteDatabase.close();
        return checked;
    }


    public boolean insertSampleSentence(SampleSentence sampleSentence) {
        boolean added = false;
        SQLiteDatabase db = null;
        try {
            db = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.OPEN_READWRITE);
            ContentValues values = new ContentValues();
            values.put(maMauCau, sampleSentence.getMaMauCau());
            values.put(maChuDeMauCau, sampleSentence.getMaChuDeMauCau());
            values.put(MauCau, sampleSentence.getMauCau());
            values.put(phienDich, sampleSentence.getPhienDich());
            values.put(tinhHuongSuDung, sampleSentence.getTinhHuongSuDung());

            long result = db.insert(TABLE_NAME, null, values);
            added = result != -1;
        } catch (SQLiteException e) {
            e.printStackTrace();
        } finally {
            if (db != null) {
                db.close();
            }
        }
        return added;
    }

    public boolean updateSampleSentence(SampleSentence sampleSentence) {
        boolean updated = false;
        SQLiteDatabase db = null;
        try {
            db = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.OPEN_READWRITE);

            ContentValues values = new ContentValues();
            values.put(MauCau, sampleSentence.getMauCau());
            values.put(phienDich, sampleSentence.getPhienDich());
            values.put(tinhHuongSuDung, sampleSentence.getTinhHuongSuDung());
            values.put(maChuDeMauCau, sampleSentence.getMaChuDeMauCau());
            int result = db.update(TABLE_NAME, values, maMauCau + " = ?", new String[]{sampleSentence.getMaMauCau()});
            updated = result > 0;
        } catch (SQLiteException e) {
            e.printStackTrace();
        } finally {
            if (db != null) {
                db.close();
            }
        }
        return updated;
    }

    public void deleteSampleSentence(String maMauCau) {
        SQLiteDatabase db = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.OPEN_READWRITE);
        db.execSQL("DELETE FROM " + TABLE_NAME + " WHERE " + this.maMauCau + " = ?", new String[]{maMauCau});
        db.close();
    }

    @SuppressLint("Range")
    public ArrayList<SampleSentence> searchSampleSentenceByKeyword(String keyword) {
        ArrayList<SampleSentence> sampleSentenceList = new ArrayList<>();
        SQLiteDatabase db = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.OPEN_READONLY);
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + maMauCau + " LIKE ? OR " + MauCau + " LIKE ?";
        Cursor cursor = db.rawQuery(query, new String[]{"%" + keyword + "%", "%" + keyword + "%"});

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    SampleSentence sampleSentence = new SampleSentence();
                    sampleSentence.setMaMauCau(cursor.getString(cursor.getColumnIndex(maMauCau)));
                    sampleSentence.setMaChuDeMauCau(cursor.getString(cursor.getColumnIndex(maChuDeMauCau)));
                    sampleSentence.setMauCau(cursor.getString(cursor.getColumnIndex(MauCau)));
                    sampleSentence.setPhienDich(cursor.getString(cursor.getColumnIndex(phienDich)));
                    sampleSentence.setTinhHuongSuDung(cursor.getString(cursor.getColumnIndex(tinhHuongSuDung)));
                    sampleSentenceList.add(sampleSentence);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        db.close();
        return sampleSentenceList;
    }
}
