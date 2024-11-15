package com.example.doanungdung2.Controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.doanungdung2.Model.AssigmentDetail;
import com.example.doanungdung2.Model.Grammar;
import com.example.doanungdung2.Model.Question;

public class AssignmentDetailHandler extends SQLiteOpenHelper {

    private static final String DB_NAME = "AppHocTiengAnh";
    private static final int DB_VERSION = 1;

    private static final String TABLE_NAME = "ChiTietBaiLam";
    private static final String maChiTietBaiLam = "MaChiTietBaiLam";
    private static final String cauTraLoi = "CauTraLoi";
    private static final String ketQuaCauTraLoi = "KetQuaCauTraLoi";
    private static final String maCauHoi = "MaCauHoi";
    private static final String maBaiLam = "MaBaiLam";

    private static final String PATH = "/data/data/com.example.doanungdung2/database/AppHocTiengAnh.db";

    public AssignmentDetailHandler(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DB_NAME, factory, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public void insertAssignmentDetail(AssigmentDetail assigmentDetail)
    {
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.OPEN_READWRITE);

        String query = "INSERT INTO " + TABLE_NAME + " (maChiTietBaiLam, cauTraLoi, ketQuaCauTraLoi, maCauHoi, " +
                "maBailam) VALUES (?, ?, ?, ?, ?)";

        sqLiteDatabase.execSQL(query, new Object[]{
                assigmentDetail.getMaChiTietBaiLam(),
                assigmentDetail.getCauTraLoi(),
                assigmentDetail.getKetQuaCauTraLoi(),
                assigmentDetail.getMaCauHoi(),
                assigmentDetail.getMaBaiLam()
        });

        sqLiteDatabase.close();
    }
    public void upDateAssignmentDetail(String maCauHoi, String maBaiLam, String cauTraLoi, String ketQuaCauTraLoi) {
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.OPEN_READWRITE);
        String query = "Update " + TABLE_NAME + " Set cauTraLoi = ?, ketQuaCauTraLoi = ? " +
                "Where maCauHoi = ? AND maBaiLam = ?";
        sqLiteDatabase.execSQL(query, new String[]{
                cauTraLoi,
                ketQuaCauTraLoi,
                maCauHoi,
                maBaiLam
        });
        sqLiteDatabase.close();
    }
}
