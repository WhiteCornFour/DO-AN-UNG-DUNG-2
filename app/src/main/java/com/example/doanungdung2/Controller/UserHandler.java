package com.example.doanungdung2.Controller;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.doanungdung2.Model.User;

import java.util.ArrayList;

public class UserHandler extends SQLiteOpenHelper {
    private static final String DB_NAME = "AppHocTiengAnh";
    private static final int DB_VERSION = 1;

    private static final String TABLE_NAME = "NguoiDung";
    private static final String maNguoiDung = "MaNguoiDung";
    private static final String tenNguoiDung = "TenNguoiDung";
    private static final String taiKhoan = "TaiKhoan";
    private static final String matKhau = "MatKhau";
    private static final String soDienThoai = "SoDienThoai";
    private static final String email = "Email";
    private static final String anhNguoiDung = "AnhNguoiDung";
    private static final String PATH = "/data/data/com.example.doanungdung2/database/AppHocTiengAnh.db";

    public UserHandler(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DB_NAME, factory, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public ArrayList<User> loadAllDataOfUser()
    {
        ArrayList<User> userArrayList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        Cursor cursor = sqLiteDatabase.rawQuery("Select * from " + TABLE_NAME, null);
        if (cursor.moveToFirst()) {
            do {
                User user = new User();
                user.setMaNguoiDung(cursor.getString(0));
                user.setTenNguoiDung(cursor.getString(1));
                user.setTaiKhoan(cursor.getString(2));
                user.setMatKhau(cursor.getString(3));
                user.setSoDienThoai(cursor.getString(4));
                user.setEmail(cursor.getString(5));
                userArrayList.add(user);
            } while (cursor.moveToNext());
        }
        cursor.close();
        sqLiteDatabase.close();
        return userArrayList;
    }

    public void registerNewUserAccount(User user)
    {
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        String sql = "INSERT OR IGNORE INTO " + TABLE_NAME + " ("
                + maNguoiDung + ", " + tenNguoiDung + ", "+ taiKhoan +", "+ matKhau +", "+ soDienThoai +", "+ email + ") " +
                "Values "
                + "('" + user.getMaNguoiDung() + "','" + user.getTenNguoiDung() + "', '"+ user.getTaiKhoan() +"','"+ user.getMatKhau() +"', '"+ user.getSoDienThoai() +"', '"+ user.getEmail() + "')";
        sqLiteDatabase.execSQL(sql);
        sqLiteDatabase.close();
    }


}
