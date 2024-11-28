package com.example.doanungdung2.Controller;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

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
    private static final String cheDoXacNhan = "CheDoXacNhan";
    private static final String maXacNhan = "MaXacNhan";
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

    public ArrayList<User> loadAllDataOfUser() {
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
                user.setCheDoXacNhan(cursor.getString(6));
                user.setMaXacNhan(cursor.getString(7));
                user.setAnhNguoiDung(cursor.getBlob(8));
                userArrayList.add(user);
            } while (cursor.moveToNext());
        }
        cursor.close();
        sqLiteDatabase.close();
        return userArrayList;
    }

    public void registerNewUserAccount(User user) {
        SQLiteDatabase sqLiteDatabase = sqLiteDatabase = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.CREATE_IF_NECESSARY);
            ContentValues values = new ContentValues();
            values.put(maNguoiDung, user.getMaNguoiDung());
            values.put(tenNguoiDung, user.getTenNguoiDung());
            values.put(taiKhoan, user.getTaiKhoan());
            values.put(matKhau, user.getMatKhau());
            values.put(soDienThoai, user.getSoDienThoai());
            values.put(email, user.getEmail());
            values.put(cheDoXacNhan, user.getCheDoXacNhan());
            values.put(maXacNhan, user.getMaXacNhan());
            values.put(anhNguoiDung, user.getAnhNguoiDung());

            sqLiteDatabase.insertWithOnConflict(TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_IGNORE);
            sqLiteDatabase.close();
    }


    //Hàm kiểm tra xem soo điện thoại người dùng nhập vào có tồn tại trong db hay ko
    public boolean checkEnterPhoneNumberMatchPhoneDB(String enterPhoneNumber) {
        SQLiteDatabase sqLiteDatabase = null;
        Cursor cursor = null;
        boolean isMatch = false;
        try {
            // Mở cơ sở dữ liệu
            sqLiteDatabase = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.OPEN_READONLY);

            // Truy vấn để kiểm tra sự tồn tại của số điện thoại
            String sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + soDienThoai + " = ?";
            cursor = sqLiteDatabase.rawQuery(sql, new String[]{enterPhoneNumber});

            // Nếu cursor trả về ít nhất một bản ghi, nghĩa là số điện thoại tồn tại trong DB
            if (cursor != null && cursor.getCount() > 0) {
                isMatch = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Đảm bảo đóng cursor và database sau khi sử dụng
            if (cursor != null) {
                cursor.close();
            }
            if (sqLiteDatabase != null) {
                sqLiteDatabase.close();
            }
        }
        return isMatch;
    }

    //Hàm trả về mật khẩu khi người dùng nhập đúng OTP
    public String returnPasswordForUser(String phoneNumber) {
        String passwordReturn = "";
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        String sql = "SELECT " + matKhau + " FROM " + TABLE_NAME + " WHERE " + soDienThoai + " = ?";
        Cursor cursor = sqLiteDatabase.rawQuery(sql, new String[]{phoneNumber});
        if (cursor != null && cursor.moveToFirst()) {
            passwordReturn = cursor.getString(0); // Lấy giá trị cột đầu tiên (mật khẩu)
        }
        return passwordReturn;
    }

    public boolean validateLogin(String account, String password) {
        SQLiteDatabase db = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.OPEN_READONLY);
        String sql = "SELECT COUNT(*) FROM " + TABLE_NAME + " WHERE " + taiKhoan + " = ? AND " + matKhau + " = ?";
        Cursor cursor = db.rawQuery(sql, new String[]{account, password});

        boolean isValid = false;
        if (cursor.moveToFirst()) {
            int count = cursor.getInt(0);
            isValid = (count > 0);
        }
        cursor.close();
        db.close();

        return isValid;
    }
    @SuppressLint("Range")
    public String getIdUserr(String account, String pass) {
        String result = "";

        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.CREATE_IF_NECESSARY);
            String sql = "SELECT maNguoiDung FROM " + TABLE_NAME + " WHERE " + taiKhoan + " = ? AND " + matKhau + " = ?";
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);


        if (cursor.moveToFirst()) {
                result = cursor.getString(cursor.getColumnIndex(maNguoiDung));
        }
        cursor.close();
        sqLiteDatabase.close();
        return result;
    }

    public User getUserInfo(String accountInput, String passwordInput) {
        User us = new User();
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.OPEN_READONLY);
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + taiKhoan + " = ? AND " + matKhau + " = ?";
        Cursor cursor = sqLiteDatabase.rawQuery(query, new String[]{accountInput, passwordInput});
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                us.setMaNguoiDung(cursor.getString(0));
                us.setTenNguoiDung(cursor.getString(1));
                us.setTaiKhoan(cursor.getString(2));
                us.setMatKhau(cursor.getString(3));
                us.setSoDienThoai(cursor.getString(4));
                us.setEmail(cursor.getString(5));
                us.setCheDoXacNhan(cursor.getString(6));
                us.setMaXacNhan(cursor.getString(7));
                us.setAnhNguoiDung(cursor.getBlob(8));
            }
            cursor.close();
        }
        sqLiteDatabase.close();
        return us;
    }
    public boolean checkPhoneAndEmailBeforeUpdate(String key)
    {
        boolean check = false;
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.OPEN_READONLY);
        String query = "Select * From " + TABLE_NAME + " Where Email = ? OR SoDienThoai = ?";
        Cursor cursor = sqLiteDatabase.rawQuery(query, new String[]{key, key});
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
    public void upDateUserInfor(User user)
    {
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.OPEN_READWRITE);
        String query = "Update " + TABLE_NAME + " Set TenNguoiDung = ?, SoDienThoai = ?, Email = ?, AnhNguoiDung = ?" +
                " Where MaNguoiDung = ?";
        sqLiteDatabase.execSQL(query, new Object[]{user.getTenNguoiDung(), user.getSoDienThoai(),
        user.getEmail(), user.getAnhNguoiDung(), user.getMaNguoiDung()});
        sqLiteDatabase.close();
    }

    public boolean checkEnterOldPasswordIsRight(String maNguoiDungInput, String oldPassword) {
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.OPEN_READWRITE);
        boolean isMatch = false;
        sqLiteDatabase = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.OPEN_READONLY);
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + maNguoiDung + " = ? AND " + matKhau + " = ?";
        Cursor cursor = sqLiteDatabase.rawQuery(sql, new String[]{maNguoiDungInput, oldPassword});

        if (cursor != null && cursor.getCount() > 0) {
            isMatch = true;
        }
        return isMatch;
    }

    public void updateUserPassword(String newPassword, String maNguoiDungInput)
    {
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.OPEN_READWRITE);
        String query = "Update " + TABLE_NAME + " Set MatKhau = ?" +
                " Where MaNguoiDung = ?";
        sqLiteDatabase.execSQL(query, new String[]{newPassword, maNguoiDungInput});
        sqLiteDatabase.close();
    }

    public void updateCheDoXacNhan(String cheDoXacNhanInput, String maNguoiDungInput) {
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.OPEN_READWRITE);
        String sql = "UPDATE " + TABLE_NAME + " SET " + cheDoXacNhan + " = ? WHERE " + maNguoiDung + " = ?";
        Log.d("UpdateStatus", "Rows affected: " + sql);
        sqLiteDatabase.execSQL(sql, new Object[]{ cheDoXacNhanInput, maNguoiDungInput});
        sqLiteDatabase.close();
    }

    public void updateUserVerificationCode(String newVerificationCode, String maNguoiDungInput)
    {
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.OPEN_READWRITE);
        String query = "Update " + TABLE_NAME + " Set MaXacNhan = ?" + " Where MaNguoiDung = ?";
        sqLiteDatabase.execSQL(query, new Object[]{newVerificationCode, maNguoiDungInput});
        Log.d("maNguoiDung and newVerificationCode: ", maNguoiDungInput + newVerificationCode);
        sqLiteDatabase.close();
    }

    @SuppressLint("Range")
    public String getNameOfUser(String maNguoiDungInput)
    {
        String kq = "";
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.OPEN_READONLY);
        String query = "Select TenNguoiDung From " + TABLE_NAME + " Where MaNguoiDung = ?";
        Cursor cursor = sqLiteDatabase.rawQuery(query, new String[]{maNguoiDungInput});
        if (cursor!=null)
        {
            if (cursor.moveToFirst())
            {
                kq = cursor.getString(cursor.getColumnIndex(tenNguoiDung));
            }
            cursor.close();
        }
        sqLiteDatabase.close();
        Log.d("kq: ", kq);
        return kq;
    }
}
