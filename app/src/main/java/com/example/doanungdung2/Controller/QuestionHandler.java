package com.example.doanungdung2.Controller;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

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
    @SuppressLint("Range")
    public ArrayList<Question> searchQuestionByNameOrCode(String keyWord) {
        ArrayList<Question> questionArrayList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + maCauHoi + " LIKE ? OR " + noiDungCauHoi + " LIKE ?";
        Cursor cursor = sqLiteDatabase.rawQuery(query, new String[]{"%" + keyWord + "%", "%" + keyWord + "%"});
        if (cursor != null && cursor.moveToFirst()) {
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
            cursor.close();
        }
        sqLiteDatabase.close();
        return questionArrayList;
    }

//    public void deleteQuestion(String maCauHoi) {
//        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.CREATE_IF_NECESSARY);
//        sqLiteDatabase.delete(TABLE_NAME, this.maCauHoi + "=?", new String[]{maCauHoi});
//        sqLiteDatabase.close();
//    }

    public void deleteQuestion(String maCauHoi) {
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.OPEN_READWRITE);
        int rowsAffected = sqLiteDatabase.delete(TABLE_NAME, this.maCauHoi + "=?", new String[]{maCauHoi});
        if (rowsAffected > 0) {
            Log.d("db", "Xoa cau hoi voi ma: " + maCauHoi);
        } else {
            Log.d("db", "Khong tim thay cau hoi voi ma: " + maCauHoi);
        }
        if (sqLiteDatabase != null && sqLiteDatabase.isOpen()) {
            sqLiteDatabase.close();
        }
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
            contentValues.put(maBaiTap, q.getMaBaiTap());
            contentValues.put(maDangBaiTap, q.getMaDangBaiTap());

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

    @SuppressLint("Range")
    public ArrayList<Question> loadAllDataOfNotAddingQuestion(String mucDoCHBT, String maDangBaiTapCHBT) {
        ArrayList<Question> questionArrayList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.CREATE_IF_NECESSARY);

        //Khong null va cung muc do
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + maBaiTap + " IS NULL AND " + mucDo + " = ?" + " AND " + maDangBaiTap + " = ?";

        Cursor cursor = sqLiteDatabase.rawQuery(query, new String[]{mucDoCHBT, maDangBaiTapCHBT});

        if (cursor != null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
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

    @SuppressLint("Range")
    public ArrayList<Question> loadAllDataOfAddingQuestion(String mucDoCHBT, String maDangBaiTapCHBT, String maBaiTapCHBT) {
        ArrayList<Question> questionArrayList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.CREATE_IF_NECESSARY);

        //Khong null va cung muc do
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + maBaiTap + " IS NOT NULL AND " + mucDo + " = ?" + " AND " + maDangBaiTap + " = ?" + " AND " + maBaiTap + " = ?";

        Cursor cursor = sqLiteDatabase.rawQuery(query, new String[]{mucDoCHBT, maDangBaiTapCHBT, maBaiTapCHBT});

        if (cursor != null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
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

    @SuppressLint("Range")
    public ArrayList<Question> loadAllDataOfMatchQuestionByExerciseCode(String maBaiTapInput, String mucDoInput) {
        ArrayList<Question> questionArrayList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.OPEN_READONLY);

        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + maBaiTap + " = ? AND " + mucDo + " = ?";

        Cursor cursor = sqLiteDatabase.rawQuery(query, new String[]{maBaiTapInput, mucDoInput});

        if (cursor != null) {
            if (cursor.moveToFirst()) {
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
    public boolean checkAnswerQuestion(String maCauHoi, String dapAn)
    {
        boolean check = false;
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.OPEN_READONLY);
        String query = "Select * From " + TABLE_NAME + " Where maCauHoi = ? AND dapAn = ?";
        Cursor cursor = sqLiteDatabase.rawQuery(query, new String[]{maCauHoi, dapAn});
        if(cursor != null)
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
    public Question searchInforAboutAQuesByCode(String maCauHoiInput) {
        Question question = new Question();
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.OPEN_READONLY);

        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + maCauHoi + " = ?";

        Cursor cursor = sqLiteDatabase.rawQuery(query, new String[]{maCauHoiInput});

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
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
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        sqLiteDatabase.close();
        return question;
    }
}
