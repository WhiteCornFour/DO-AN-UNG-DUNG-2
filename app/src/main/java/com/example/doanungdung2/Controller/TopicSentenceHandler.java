package com.example.doanungdung2.Controller;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import java.io.ByteArrayOutputStream;

import androidx.annotation.Nullable;

import com.example.doanungdung2.Model.TopicSentence;

import java.util.ArrayList;

public class TopicSentenceHandler extends SQLiteOpenHelper {
    private static final String DB_NAME = "AppHocTiengAnh";
    private static final int DB_VERSION = 1;

    private static final String TABLE_NAME = "ChuDeMauCau";
    private static final String maChuDeMauCau = "MaChuDeMauCau";
    private static final String tenChuDeMauCau = "TenChuDeMauCau";
    private static final String moTa = "MoTa";
    private static final String anhChuDeMauCau = "AnhChuDeMauCau";
    private static final String PATH = "/data/data/com.example.doanungdung2/database/AppHocTiengAnh.db";

    public TopicSentenceHandler(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DB_NAME, factory, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @SuppressLint("Range")
    public ArrayList<TopicSentence> loadAllDataOfTopicSentence() {
        ArrayList<TopicSentence> topicSentenceArrayList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.OPEN_READONLY);
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    TopicSentence topicSentence = new TopicSentence();
                    topicSentence.setMaChuDeMauCau(cursor.getString(cursor.getColumnIndex(maChuDeMauCau)));
                    topicSentence.setTenChuDeMauCau(cursor.getString(cursor.getColumnIndex(tenChuDeMauCau)));
                    topicSentence.setMoTa(cursor.getString(cursor.getColumnIndex(moTa)));

                    byte[] imageBytes = cursor.getBlob(cursor.getColumnIndex(anhChuDeMauCau));
                    if (imageBytes != null) {
                        topicSentence.setAnhChuDeMauCau(imageBytes);
                    }
                    topicSentenceArrayList.add(topicSentence);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        sqLiteDatabase.close();

        return topicSentenceArrayList;
    }

    public boolean checkCodeAndNameTopicSentence(String maCDMCInput, String tenCDMCInput) {
        boolean checked = false;
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + maChuDeMauCau + " != ? AND " + tenChuDeMauCau + " = ?";
        Cursor cursor = sqLiteDatabase.rawQuery(query, new String[]{maCDMCInput, tenCDMCInput});

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                checked = true;
            }
            cursor.close();
        }

        sqLiteDatabase.close();
        return checked;
    }

    @SuppressLint("Range")
    public ArrayList<TopicSentence> searchTopicByNameOrCode(String keyWord) {
        ArrayList<TopicSentence> topicSentenceArrayList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.OPEN_READONLY);

        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + maChuDeMauCau + " LIKE ? OR " + tenChuDeMauCau + " LIKE ?";
        Cursor cursor = sqLiteDatabase.rawQuery(query, new String[]{"%" + keyWord + "%", "%" + keyWord + "%"});

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    TopicSentence topicSentence = new TopicSentence();
                    topicSentence.setMaChuDeMauCau(cursor.getString(cursor.getColumnIndex(maChuDeMauCau)));
                    topicSentence.setTenChuDeMauCau(cursor.getString(cursor.getColumnIndex(tenChuDeMauCau)));
                    topicSentence.setMoTa(cursor.getString(cursor.getColumnIndex(moTa)));
                    topicSentenceArrayList.add(topicSentence);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }

        sqLiteDatabase.close();
        return topicSentenceArrayList;
    }

    @SuppressLint("Range")
    public static ArrayList<String> returnNameOfTopicSentenceSpinner() {
        ArrayList<String> TopicSentenceName = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        String query = "SELECT " + tenChuDeMauCau + " FROM " + TABLE_NAME;
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    String ts = cursor.getString(cursor.getColumnIndex(tenChuDeMauCau));
                    TopicSentenceName.add(ts);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        sqLiteDatabase.close();
        return TopicSentenceName;
    }

    @SuppressLint("Range")
    public String getTopicSentenceNameByCode(String maChuDeMauCauInput) {
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        String query = "SELECT " + tenChuDeMauCau + " FROM " + TABLE_NAME + " WHERE " +  maChuDeMauCau + " = ?";
        Cursor cursor = sqLiteDatabase.rawQuery(query, new String[]{maChuDeMauCauInput});
        String tenCDMC = "";
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                tenCDMC = cursor.getString(cursor.getColumnIndex(tenChuDeMauCau));
            }
            cursor.close();
        }
        sqLiteDatabase.close();
        return tenCDMC;
    }
    @SuppressLint("Range")
    public String getTopicSentenceCodeByName(String tenChuDeMauCauInput) {
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        String query = "SELECT " + maChuDeMauCau + " FROM " + TABLE_NAME + " WHERE " + tenChuDeMauCau + " = ?";
        Cursor cursor = sqLiteDatabase.rawQuery(query, new String[]{tenChuDeMauCauInput});
        String MaCDMC = "";
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                MaCDMC = cursor.getString(cursor.getColumnIndex(maChuDeMauCau));
            }
            cursor.close();
        }
        sqLiteDatabase.close();
        return MaCDMC;
    }
// edit
    @SuppressLint("Range")
    public ArrayList<TopicSentence> searchResultTopicSentence(String topicSentenceSearch) {
        ArrayList<TopicSentence> topicSentenceArrayList = new ArrayList<>();
        TopicSentence ts = null;
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.OPEN_READONLY);
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + maChuDeMauCau + " LIKE '%" + topicSentenceSearch + "%'" +
                " OR " + tenChuDeMauCau + " LIKE '%" + topicSentenceSearch + "%'";
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    ts = new TopicSentence();
                    ts.setMaChuDeMauCau(cursor.getString(cursor.getColumnIndex(maChuDeMauCau)));
                    ts.setTenChuDeMauCau(cursor.getString(cursor.getColumnIndex(tenChuDeMauCau)));
                    ts.setMoTa(cursor.getString(cursor.getColumnIndex(moTa)));

                    byte[] anhChuDe = cursor.getBlob(cursor.getColumnIndex("AnhChuDeMauCau"));
                    ts.setAnhChuDeMauCau(anhChuDe);

                    topicSentenceArrayList.add(ts);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        sqLiteDatabase.close();
        return topicSentenceArrayList;
    }

    public boolean updateTopicSentence(TopicSentence topicSentence) {
        boolean updated = false;
        SQLiteDatabase sqLiteDatabase = null;
        try {
            sqLiteDatabase = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.OPEN_READWRITE);

            ContentValues contentValues = new ContentValues();
            contentValues.put(tenChuDeMauCau, topicSentence.getTenChuDeMauCau());
            contentValues.put(moTa, topicSentence.getMoTa());

            if (topicSentence.getAnhChuDeMauCau() != null) {
                contentValues.put("AnhChuDeMauCau", topicSentence.getAnhChuDeMauCau());
            }

            int result = sqLiteDatabase.update(TABLE_NAME, contentValues, maChuDeMauCau + " = ?", new String[]{String.valueOf(topicSentence.getMaChuDeMauCau())});
            updated = result > 0;

        } catch (SQLiteException e) {
            e.printStackTrace();
        } finally {
            if (sqLiteDatabase != null) {
                sqLiteDatabase.close();
            }
        }
        return updated;
    }

    public void deleteTopicSentence(String maChuDeMauCau) {
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.OPEN_READWRITE);
        sqLiteDatabase.execSQL("DELETE FROM " + TABLE_NAME + " WHERE " + this.maChuDeMauCau + " = ?", new String[]{maChuDeMauCau});
        sqLiteDatabase.close();
    }

    @SuppressLint("Range")
    public ArrayList<String> returnNameOfTopicsSpinner() {
        ArrayList<String> topicNames = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.OPEN_READONLY);
        String query = "SELECT " + tenChuDeMauCau + " FROM " + TABLE_NAME;
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    String topicName = cursor.getString(cursor.getColumnIndex(tenChuDeMauCau));
                    topicNames.add(topicName);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        sqLiteDatabase.close();
        return topicNames;
    }


    public boolean addTopicSentence(TopicSentence topicSentence, Bitmap image) {
        boolean added = false;
        SQLiteDatabase sqLiteDatabase = null;
        try {
            sqLiteDatabase = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.OPEN_READWRITE);
            ContentValues contentValues = new ContentValues();
            contentValues.put(maChuDeMauCau, topicSentence.getMaChuDeMauCau());
            contentValues.put(tenChuDeMauCau, topicSentence.getTenChuDeMauCau());
            contentValues.put(moTa, topicSentence.getMoTa());

            if (image != null) {
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                image.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] imageBytes = stream.toByteArray();
                contentValues.put(anhChuDeMauCau, imageBytes);
            }

            long result = sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
            added = result != -1;


        } catch (SQLiteException e) {
            e.printStackTrace();
        } finally {
            if (sqLiteDatabase != null) {
                sqLiteDatabase.close();
            }
        }
        return added;
    }
}
