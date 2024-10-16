package com.example.doanungdung2.Model;

public class ExercisesCategory {
    String maDangBaiTap;
    String tenDangBaiTap;
    String moTa;

    public String getMaDangBaiTap() {
        return maDangBaiTap;
    }

    public void setMaDangBaiTap(String maDangBaiTap) {
        this.maDangBaiTap = maDangBaiTap;
    }

    public String getTenDangBaiTap() {
        return tenDangBaiTap;
    }

    public void setTenDangBaiTap(String tenDangBaiTap) {
        this.tenDangBaiTap = tenDangBaiTap;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public ExercisesCategory(String maDangBaiTap, String tenDangBaiTap, String moTa) {
        this.maDangBaiTap = maDangBaiTap;
        this.tenDangBaiTap = tenDangBaiTap;
        this.moTa = moTa;
    }

    public ExercisesCategory() {
    }
}
