package com.example.doanungdung2.Model;

import android.annotation.SuppressLint;
import android.content.Context;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DatabaseHelper {
    private static final String DATABASE_NAME = "AppHocTiengAnh.db";
    @SuppressLint("SdCardPath")
    private static final String DATABASE_PATH = "/data/data/com.example.doanungdung2/database/";

    private final Context context;

    public DatabaseHelper(Context context) {
        this.context = context;
    }

    public void copyDatabaseIfNeeded() {
        String databaseDir = String.format(DATABASE_PATH, context.getPackageName());
        String databaseFilePath = databaseDir + DATABASE_NAME;

        File databaseFile = new File(databaseFilePath);

        if (!databaseFile.exists()) {
            File databaseFolder = new File(databaseDir);
            if (!databaseFolder.exists()) {
                databaseFolder.mkdirs(); // Tạo thư mục nếu chưa tồn tại
            }

            try (InputStream inputStream = context.getAssets().open(DATABASE_NAME);
                 OutputStream outputStream = new FileOutputStream(databaseFilePath)) {

                byte[] buffer = new byte[1024];
                int length;
                while ((length = inputStream.read(buffer)) > 0) {
                    outputStream.write(buffer, 0, length);
                }
                outputStream.flush();
                System.out.println("Database copied successfully.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Database already exists, skipping copy.");
        }
    }
}
