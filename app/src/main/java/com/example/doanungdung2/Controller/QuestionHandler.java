package com.example.doanungdung2.Controller;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.doanungdung2.Model.Question;

import java.util.ArrayList;

public class QuestionHandler extends SQLiteOpenHelper {
    private static final String DB_NAME = "AppHocTiengAnh";
    private static final int DB_VERSION = 1;

    private static final String TABLE_NAME = "CauHoi";
    private static final String maCauHoi = "MaCauHoi";
    private static final String noiDungCauHoi = "NoiDungCauHoi";
    private static final String cauA = "A";
    private static final String cauB = "B";
    private static final String cauC = "C";
    private static final String cauD = "D";
    private static final String dapAn = "DapAn";
    private static final String mucDo = "MucDo";
    private static final String maBaiTap = "MaBaiTap";
    private static final String maDangBaiTap = "MaDangBaiTap";

    private static final String PATH = "/data/data/com.example.doanungdung2/database/AppHocTiengAnh.db";

    public QuestionHandler(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DB_NAME, factory, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @SuppressLint("Range")
    public ArrayList<Question> loadAllDataOfQuestion()
    {
        ArrayList<Question> questionArrayList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        if (cursor != null)
        {
            if (cursor.moveToFirst())
            {
                do {
                    Question question = new Question();
                    question.setMaCauHoi(cursor.getString(cursor.getColumnIndex(maCauHoi)));
                    question.setNoiDungCauHoi(cursor.getString(cursor.getColumnIndex(noiDungCauHoi)));
                    question.setCauA(cursor.getString(cursor.getColumnIndex(cauA)));
                    question.setCauB(cursor.getString(cursor.getColumnIndex(cauB)));
                    question.setCauC(cursor.getString(cursor.getColumnIndex(cauC)));
                    question.setCauD(cursor.getString(cursor.getColumnIndex(cauD)));
                    question.setDapAn(cursor.getString(cursor.getColumnIndex(dapAn)));
                    question.setMucDo(cursor.getString(cursor.getColumnIndex(mucDo)));
                    question.setMaBaiTap(cursor.getString(cursor.getColumnIndex(maBaiTap)));
                    question.setMaDangBaiTap(cursor.getString(cursor.getColumnIndex(maDangBaiTap)));
                    questionArrayList.add(question);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        sqLiteDatabase.close();
        return questionArrayList;
    }


}
