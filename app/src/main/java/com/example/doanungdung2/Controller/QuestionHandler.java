package com.example.doanungdung2.Controller;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.doanungdung2.Model.Exercise;
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

    public boolean updateQuestions(Question q) {
        boolean updated = false;
        SQLiteDatabase sqLiteDatabase = null;
        try {
            sqLiteDatabase = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.OPEN_READWRITE);

            ContentValues contentValues = new ContentValues();
            contentValues.put(noiDungCauHoi, q.getNoiDungCauHoi());
            contentValues.put(cauA, q.getCauA());
            contentValues.put(cauB, q.getCauB());
            contentValues.put(cauC, q.getCauC());
            contentValues.put(cauD, q.getCauD());
            contentValues.put(dapAn, q.getDapAn());
            contentValues.put(mucDo, q.getMucDo());

            int kq = sqLiteDatabase.update(TABLE_NAME, contentValues, maCauHoi + " = ?", new String[]{q.getMaCauHoi()});
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
    public boolean checkQuestionCode(String maCauHoi, String noiDungCauHoi)
    {
        boolean check = false;
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.OPEN_READONLY);
        String query = "Select * From " + TABLE_NAME + " Where MaCauHoi = ? OR NoiDungCauHoi = ?";
        Cursor cursor = sqLiteDatabase.rawQuery(query, new String[]{maCauHoi, noiDungCauHoi});
        if (cursor != null)
        {
            if (cursor.moveToFirst())
            {
                check = true;
            }
        }
        return check;
    }
    public void addNewQuestion(Question question)
    {
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.OPEN_READWRITE);
        String query = "Insert Into " + TABLE_NAME + " (maCauHoi, noiDungCauHoi, A, B, " +
                "C, D, dapAn, mucDo, maBaiTap, maDangBaiTap) Values (?, ?, ?, ?, ? ,?, ?, ?, ?, ?)";
        sqLiteDatabase.execSQL(query, new String[]{question.getMaCauHoi(), question.getNoiDungCauHoi(),
            question.getCauA(), question.getCauB(), question.getCauC(), question.getCauD(), question.getDapAn(),
            question.getMucDo(), question.getMaBaiTap(), question.getMaDangBaiTap()});
        sqLiteDatabase.close();
    }
}
