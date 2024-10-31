package com.example.doanungdung2.Controller;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.doanungdung2.Model.History;
import com.example.doanungdung2.Model.Question;

import java.util.ArrayList;

public class HistoryHandler extends SQLiteOpenHelper {

    private static final String DB_NAME = "AppHocTiengAnh";
    private static final int DB_VERSION = 1;

    private static final String TABLE_NAME = "LichSu";
    private static final String maLichSu = "MaLichSu";
    private static final String maTuVung = "MaTuVung";
    private static final String maNguoiDung = "MaNguoiDung";

    private static final String PATH = "/data/data/com.example.doanungdung2/database/AppHocTiengAnh.db";

    public HistoryHandler(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DB_NAME, factory, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @SuppressLint("Range")
    public ArrayList<History> loadAllDataOfHistory()
    {
        ArrayList<History> historyArrayList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        if (cursor != null)
        {
            if (cursor.moveToFirst())
            {
                do {
                    History history = new History();
                    history.setMaLichSu(cursor.getString(cursor.getColumnIndex(maLichSu)));
                    history.setMaTuVung(cursor.getString(cursor.getColumnIndex(maTuVung)));
                    history.setMaNguoiDung(cursor.getString(cursor.getColumnIndex(maNguoiDung)));
                    historyArrayList.add(history);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        sqLiteDatabase.close();
        return historyArrayList;
    }
}
