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
    public String returnPasswordForUser(String phoneNumber)
    {
        String passwordReturn = "";
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        String sql = "SELECT " + matKhau + " FROM " + TABLE_NAME + " WHERE " + soDienThoai + " = ?";
        Cursor cursor = sqLiteDatabase.rawQuery(sql, new String[]{phoneNumber});
        if (cursor != null && cursor.moveToFirst()) {
            passwordReturn = cursor.getString(0); // Lấy giá trị cột đầu tiên (mật khẩu)
        }
        return  passwordReturn;
    }

}
