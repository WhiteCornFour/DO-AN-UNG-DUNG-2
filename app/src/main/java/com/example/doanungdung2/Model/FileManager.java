package com.example.doanungdung2.Model;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileManager {
    //Hàm kiểm tra 2 file trong PATH: /data/data/filse
    //Nếu có thì bỏ qua
    //Chưa có thì tạo 2 file FirstLogin.txt và AccountStatus.txt
    public static boolean checkAndCreateFiles(Context context) {
        boolean check = false;
        // Thư mục files của ứng dụng
        File filesDir = context.getFilesDir();

        // Các file cần kiểm tra
        String[] fileNames = {"FirstLogin.txt", "AccountStatus.txt"};

        for (String fileName : fileNames) {
            File file = new File(filesDir, fileName);

            // Kiểm tra xem file có tồn tại không
            if (file.exists()) {
                //Nếu file đó đã tồn tại và có tên là FirstLogin.txt thì trả về true
                //Để có thể xử lý bỏ qua trang Firt Before Login
                //Vì trang đó chỉ chạy 1 lần duy nhất khi người dùng mới chạy app lần đầu tiên
                if (fileName.equals("FirstLogin.txt"))
                {
                    check = true;
                    return check;
                }
                //Log.d(" đã tồn tại.", fileName);
            } else {
                try {
                    // Tạo file mới
                    if (file.createNewFile()) {
                        Log.d("Tạo file thành công.", fileName);
                        // Ghi nội dung mặc định (nếu cần)
                        try (FileWriter writer = new FileWriter(file)) {
                            if (fileName.equals("FirstLogin.txt")) {
                                writer.write("Đã đăng nhập lần đầu, không chạy trang User_First_Before_Login");
                            } else if (fileName.equals("AccountStatus.txt")) {
                                //Gán status mặc định là 0 => đã đăng xuất
                                writer.write("0");
                            }
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return check;
    }
    //Hàm cập nhật lại trạng thái login và logout
    public static boolean updateAccountStatus(Context context, String fileName, String status) {
        // Thư mục files của ứng dụng
        File filesDir = context.getFilesDir();
        File file = new File(filesDir, fileName);

        // Kiểm tra xem file có tồn tại không
        if (!file.exists()) {
            System.out.println("File " + fileName + " không tồn tại.");
            return false;
        }

        // Làm rỗng nội dung file
        try (FileWriter writer = new FileWriter(file, false)) {
            // Mở file ở chế độ ghi và xóa giá trị cũ
            writer.write("");
            writer.write(status); // nhận giá trị 1:ghi nhớ đăng nhập  và 0: trạng thái đăng xuất
            //System.out.println("Dữ liệu trong file " + fileName + " đã được xóa.");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    //Hàm kiểm tra trạng thái của tài khoản hiện tại đang ở status bằng 1 hay 0
    public static String readAccountStatusFile(Context context, String fileName) {
        // Thư mục files của ứng dụng
        File filesDir = context.getFilesDir();
        File file = new File(filesDir, fileName);

        // Kiểm tra xem file có tồn tại không
        if (!file.exists()) {
            return "File " + fileName + " không tồn tại.";
        }

        // Đọc nội dung file
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "Có lỗi khi đọc file: " + e.getMessage();
        }

        return content.toString().trim(); // Loại bỏ ký tự xuống dòng cuối
    }
}
