package com.example.doanungdung2.Model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;

public class TopicSentence {

    String maChuDeMauCau;
    String tenChuDeMauCau;
    String moTa;
    byte[] anhChuDeMauCau;


    public TopicSentence() {
    }

    public TopicSentence(String maChuDeMauCau, String tenChuDeMauCau, String moTa, byte[] anhChuDeMauCau) {
        this.maChuDeMauCau = maChuDeMauCau;
        this.tenChuDeMauCau = tenChuDeMauCau;
        this.moTa = moTa;
        this.anhChuDeMauCau = anhChuDeMauCau;
    }

    public void setTenChuDeMauCau(String tenChuDeMauCau) {
        this.tenChuDeMauCau = tenChuDeMauCau;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public void setMaChuDeMauCau(String maChuDeMauCau) {
        this.maChuDeMauCau = maChuDeMauCau;
    }

//    public void setAnhChuDeMauCau(Bitmap anhChuDeMauCau) {
//        if (anhChuDeMauCau != null) {
//            this.anhChuDeMauCau = convertBitmapToByteArray(anhChuDeMauCau);
//        } else {
//            this.anhChuDeMauCau = null;
//        }
//    }

    public String getTenChuDeMauCau() {
        return tenChuDeMauCau;
    }

    public String getMoTa() {
        return moTa;
    }

    public String getMaChuDeMauCau() {
        return maChuDeMauCau;
    }

    public byte[] getAnhChuDeMauCau() {
        return anhChuDeMauCau;
    }

    public void setAnhChuDeMauCau(byte[] anhChuDeMauCau) {
        this.anhChuDeMauCau = anhChuDeMauCau;
    }

//    public Bitmap getAnhChuDeMauCau() {
//        return convertByteArrayToBitmap(anhChuDeMauCau);
//    }

//    private Bitmap convertByteArrayToBitmap(byte[] byteArray) {
//        if (byteArray != null && byteArray.length > 0) {
//            return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
//        } else {
//            return null;
//        }
//    }
//    private byte[] convertBitmapToByteArray(Bitmap bitmap) {
//        if (bitmap != null) {
//            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
//            return byteArrayOutputStream.toByteArray();
//        } else {
//            return null;
//        }
//    }

}
